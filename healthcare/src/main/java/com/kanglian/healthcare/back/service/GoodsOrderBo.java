package com.kanglian.healthcare.back.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.easyway.business.framework.bo.CrudBo;
import com.easyway.business.framework.util.DateUtil;
import com.kanglian.healthcare.back.constants.Constants;
import com.kanglian.healthcare.back.constants.FromType;
import com.kanglian.healthcare.back.constants.PaymentStatus;
import com.kanglian.healthcare.back.constants.PaymentType;
import com.kanglian.healthcare.back.dal.cond.PaymentOrderItem;
import com.kanglian.healthcare.back.dal.cond.PaymentOrder;
import com.kanglian.healthcare.back.dal.dao.GoodsDao;
import com.kanglian.healthcare.back.dal.dao.GoodsOrderDao;
import com.kanglian.healthcare.back.dal.dao.GoodsOrderItemDao;
import com.kanglian.healthcare.back.dal.dao.PaymentLogDao;
import com.kanglian.healthcare.back.dal.pojo.Goods;
import com.kanglian.healthcare.back.dal.pojo.GoodsOrder;
import com.kanglian.healthcare.back.dal.pojo.GoodsOrderItem;
import com.kanglian.healthcare.back.dal.pojo.PaymentLog;
import com.kanglian.healthcare.exception.DBException;
import com.kanglian.healthcare.util.JsonUtil;
import com.kanglian.healthcare.util.NumberUtil;

@Service
public class GoodsOrderBo extends CrudBo<GoodsOrder, GoodsOrderDao> {

    private static final Logger logger = LoggerFactory.getLogger(Constants.LOG_NAME_PAYMENT);

    @Autowired
    private GoodsDao            goodsDao;
    @Autowired
    private GoodsOrderItemDao   goodsOrderItemDao;
    @Autowired
    private PaymentLogDao       paymentLogDao;

    /**
     * 判断支付的总金额是否匹配，防止被修改
     * 
     * @param paymentOrder
     * @return
     */
    public boolean reviewPaymentOrder(final PaymentOrder paymentOrder) {
        Double totalAmount = paymentOrder.getTotalAmount();
        Double calcPayMoney = 0d;
        for (PaymentOrderItem order : paymentOrder.getGoodsList()) {
            Integer goodsId = order.getGoodsId();
            Integer goodsNum = order.getGoodsNum();
            if (goodsNum == null || goodsNum == 0)
                goodsNum = 1;
            Goods goods = goodsDao.get(Long.valueOf(goodsId));
            if (goods != null) {
                Double cost = goods.getCost();
                if (cost == null)
                    cost = 0d;
                calcPayMoney += (cost * goodsNum);
            }
        }
        int retval = NumberUtil.valueOf(totalAmount).compareTo(NumberUtil.valueOf(calcPayMoney));
        return retval == 0 ? true : false;
    }

    /**
     * 创建订单信息
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public Map<String, Object> createGoodsOrder(final PaymentOrder paymentOrder) {
        Map<String, Object> retMap = new HashMap<String, Object>();
        try {
            // 1、订单信息
            GoodsOrder goodsOrder = new GoodsOrder();
            goodsOrder.setUserId(Integer.valueOf(paymentOrder.getUserId()));
            goodsOrder.setOrderNo(paymentOrder.getOrderNo());
            goodsOrder.setPayPrice(paymentOrder.getTotalAmount());
            goodsOrder.setPayTime(DateUtil.currentDate());
            goodsOrder.setTradeStatus(PaymentStatus.PAYMENT_WAIT_BUYER_PAY);
            goodsOrder.setAddTime(DateUtil.currentDate());
            this.dao.save(goodsOrder);
            final Integer goodsOrderId = goodsOrder.getId();
            logger.info("==========[商城]订单信息[订单id：{}，订单号：{}]", goodsOrderId, paymentOrder.getOrderNo());
            logger.info("==========[商城]订单详情：{}", JsonUtil.object2Json(paymentOrder.getGoodsList()));
            // 2、订单详情
            for (PaymentOrderItem order : paymentOrder.getGoodsList()) {
                GoodsOrderItem goodsItem = new GoodsOrderItem();
                goodsItem.setGoodsOrderId(goodsOrderId);
                goodsItem.setGoodsId(order.getGoodsId());
                goodsItem.setGoodsNum(order.getGoodsNum());
                goodsItem.setGoodsPrice(order.getGoodsPrice());
                goodsItem.setAddTime(DateUtil.currentDate());
                goodsOrderItemDao.save(goodsItem);

                // 订单减库存
                Integer goodsId = order.getGoodsId();
                Goods goods = goodsDao.get(Long.valueOf(goodsId));
                if (goods != null) {
                    goods.setFreezeStore(goods.getFreezeStore() + order.getGoodsNum());
                    goods.setStore(goods.getStore() - order.getGoodsNum());
                    goods.setUpdateTime(DateUtil.currentDate());
                    goodsDao.update(goods);
                }
            }
            // 用户支出明细
            PaymentLog paymentLog = new PaymentLog();
            paymentLog.setOrderNo(goodsOrder.getOrderNo());
            paymentLog.setUserId(goodsOrder.getUserId());
            paymentLog.setType(PaymentType.getValue(paymentOrder.getType()));// 支付类型
            paymentLog.setFrom(FromType.getName("6"));// 支付来源
            paymentLog.setMark(Constants.MARK_PAY);
            paymentLog.setMoney(goodsOrder.getPayPrice());
            paymentLog.setStatus(PaymentStatus.PAYMENT_WAIT_BUYER_PAY);
            paymentLog.setMessage(
                    "您" + DateUtil.getCurrentDate() + "【"+paymentLog.getFrom()+"】支付（" + paymentLog.getMoney() + "）元");
            paymentLog.setAddTime(goodsOrder.getAddTime());
            paymentLogDao.save(paymentLog);
            logger.info("==========[商城]用户支出明细：{}", JsonUtil.object2Json(paymentLog));
            retMap.put("userId", goodsOrder.getUserId());
            retMap.put("orderId", goodsOrder.getId());
            retMap.put("orderNo", goodsOrder.getOrderNo());
            return retMap;
        } catch (Exception ex) {
            throw new DBException(ex);
        }
    }

    /**
     * 支付宝回调业务处理
     * 
     * @param order
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public void orderPaySuccess(final GoodsOrder order) {
        // 更新订单状态
        updateOrderStatus(order);
        // 减冻结库存
        List<GoodsOrderItem> orderItemList =
                goodsOrderItemDao.getGoodsOrderDetailByOrderNo(order.getOrderNo());
        for (GoodsOrderItem orderItem : orderItemList) {
            Integer goodsId = orderItem.getGoodsId();
            Goods goods = goodsDao.get(Long.valueOf(goodsId));
            if (goods != null) {
                goods.setFreezeStore(goods.getFreezeStore() - orderItem.getGoodsNum());
                goods.setUpdateTime(DateUtil.currentDate());
                goodsDao.update(goods);
            }
        }
    }
    
    /**
     * 判断该订单号交易状态：已支付
     * 
     * @param orderNo
     * @return
     */
    public boolean orderPayStatus(String orderNo) {
        GoodsOrder goodsOrder = findGoodsOrder(orderNo);
        if (goodsOrder != null && goodsOrder.getTradeStatus() != null) {
            return goodsOrder.getTradeStatus().equals(PaymentStatus.PAYMENT_TRADE_SUCCESS);
        }
        return false;
    }

    /**
     * 更新订单状态
     */
    public void updateOrderStatus(GoodsOrder order) {
        try {
            this.dao.updateOrderStatus(order);
        } catch (Exception ex) {
            throw new DBException(ex);
        }
    }

    /**
     * 订单号找订单
     * 
     * @param orderNo
     * @return
     */
    public GoodsOrder findGoodsOrder(String orderNo) {
        try {
            return this.dao.findGoodsOrder(orderNo);
        } catch (Exception ex) {
            throw new DBException(ex);
        }
    }
    
    /**
     * 我的订单列表
     * 
     * @param userId
     * @return
     */
    public List<GoodsOrder> myGoodsOrderList(String userId){
        try {
            return this.dao.myGoodsOrderList(Integer.valueOf(userId));
        } catch (Exception ex) {
            throw new DBException(ex);
        }
    }
    
    /**
     * 取消订单
     * 
     * @param goodsOrderId
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public void cancelOrder(final String goodsOrderId) {
        try {
            GoodsOrder goodsOrder = this.dao.get(Integer.valueOf(goodsOrderId));
            if (goodsOrder != null) {
                goodsOrder.setTradeStatus(PaymentStatus.PAYMENT_TRADE_CLOSE);
                goodsOrder.setUpdateTime(DateUtil.currentDate());
                this.dao.update(goodsOrder);
                // 商品库存返库
                List<GoodsOrderItem> orderItemList =
                        goodsOrderItemDao.getGoodsOrderDetailByOrderNo(goodsOrder.getOrderNo());
                for (GoodsOrderItem orderItem : orderItemList) {
                    Integer goodsId = orderItem.getGoodsId();
                    Goods goods = goodsDao.get(goodsId.longValue());
                    goods.setStore(goods.getStore() + orderItem.getGoodsNum());
                    goods.setFreezeStore(goods.getFreezeStore() - orderItem.getGoodsNum());
                    goods.setUpdateTime(DateUtil.currentDate());
                    goodsDao.update(goods);
                }
            }
        } catch (Exception ex) {
            throw new DBException(ex);
        }
    }
}
