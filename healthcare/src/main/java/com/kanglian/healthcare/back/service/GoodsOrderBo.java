package com.kanglian.healthcare.back.service;

import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.alibaba.fastjson.JSON;
import com.easyway.business.framework.bo.CrudBo;
import com.easyway.business.framework.util.DateUtil;
import com.kanglian.healthcare.back.constants.PaymentStatus;
import com.kanglian.healthcare.back.dal.cond.Order;
import com.kanglian.healthcare.back.dal.cond.PaymentOrder;
import com.kanglian.healthcare.back.dal.dao.GoodsOrderDao;
import com.kanglian.healthcare.back.dal.dao.GoodsOrderItemDao;
import com.kanglian.healthcare.back.dal.pojo.GoodsOrder;
import com.kanglian.healthcare.back.dal.pojo.GoodsOrderItem;
import com.kanglian.healthcare.exception.DBException;

@Service
public class GoodsOrderBo extends CrudBo<GoodsOrder, GoodsOrderDao> {

    private static final Logger logger = LoggerFactory.getLogger(GoodsOrderBo.class);

    @Autowired
    private GoodsOrderItemDao   goodsOrderItemDao;

    /**
     * 创建订单信息
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public Map<String, Object> createGoodsOrder(PaymentOrder paymentOrder) {
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
            logger.debug("==========订单信息[订单id：{}，订单号：{}]", goodsOrderId, paymentOrder.getOrderNo());
            logger.debug("==========订单详情：{}", JSON.toJSONString(paymentOrder.getGoodsList()));
            // 2、订单详情
            for (Order order : paymentOrder.getGoodsList()) {
                GoodsOrderItem goodsItem = new GoodsOrderItem();
                goodsItem.setGoodsOrderId(goodsOrderId);
                goodsItem.setGoodsId(order.getGoodsId());
                goodsItem.setGoodsNum(order.getGoodsNum());
                goodsItem.setGoodsPrice(order.getGoodsPrice());
                goodsItem.setAddTime(DateUtil.currentDate());
                goodsOrderItemDao.save(goodsItem);
            }
            retMap.put("orderId", goodsOrderId);
            retMap.put("orderNo", goodsOrder.getOrderNo());
            return retMap;
        } catch (Exception ex) {
            throw new DBException(ex);
        }
    }

    /**
     * 判断该订单号已支付
     * @param orderNo
     * @return
     */
    public boolean orderPaySuccess(String orderNo) {
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
    
    public GoodsOrder findGoodsOrder(String orderNo) {
        try {
            return this.dao.findGoodsOrder(orderNo);
        } catch (Exception ex) {
            throw new DBException(ex);
        }
    }
}
