package com.kanglian.healthcare.back.service;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.alibaba.fastjson.JSON;
import com.easyway.business.framework.bo.CrudBo;
import com.easyway.business.framework.util.DateUtil;
import com.easyway.business.framework.util.StringUtil;
import com.kanglian.healthcare.back.common.CommonOrder;
import com.kanglian.healthcare.back.constant.Constants;
import com.kanglian.healthcare.back.constant.FromType;
import com.kanglian.healthcare.back.constant.PaymentStatus;
import com.kanglian.healthcare.back.constant.PaymentType;
import com.kanglian.healthcare.back.dao.PaymentLogDao;
import com.kanglian.healthcare.back.dao.PaymentOrderDao;
import com.kanglian.healthcare.back.dao.PaymentOrderItemDao;
import com.kanglian.healthcare.back.pojo.AskQuestionAnswer;
import com.kanglian.healthcare.back.pojo.PaymentLog;
import com.kanglian.healthcare.back.pojo.PaymentOrder;
import com.kanglian.healthcare.back.pojo.PaymentOrderItem;
import com.kanglian.healthcare.exception.BizException;
import com.kanglian.healthcare.exception.DBException;
import com.kanglian.healthcare.util.JsonUtil;
import com.kanglian.healthcare.util.LogUtil;

@Service
public class PaymentOrderBo extends CrudBo<PaymentOrder, PaymentOrderDao> {

    /** logger */
    private final static Logger logger = LoggerFactory.getLogger(LogUtil.LOG_NAME_PAYMENT);
    
//    @Autowired
//    private AccountDao          accountDao;
//    @Autowired
//    private AccountLogDao       accountLogDao;
    @Autowired
    private PaymentOrderItemDao paymentOrderItemDao;
    @Autowired
    private PaymentLogDao paymentLogDao;
    
    /**
     * 写入用户支付订单
     * 
     * @param paymentOrder
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public void createPaymentOrder(final CommonOrder baseOrder) throws Exception {
        try {
            // 创建订单
            PaymentOrder paymentOrder = new PaymentOrder();
            paymentOrder.setOrderNo(baseOrder.getOrderNo());
            paymentOrder.setUserId(Integer.valueOf(baseOrder.getUserId()));
            if (StringUtil.isNotEmpty(baseOrder.getToUserId())) {
                paymentOrder.setToUser(Integer.valueOf(baseOrder.getToUserId()));
            }
            paymentOrder.setPayType(PaymentType.getValue(baseOrder.getType()));
            paymentOrder.setPayPrice(baseOrder.getPayAmount());
            paymentOrder.setPayTime(DateUtil.currentDate());
            paymentOrder.setPayStatus(PaymentStatus.PAYMENT_WAIT_BUYER_PAY);
            paymentOrder.setAddTime(DateUtil.currentDate());
            this.dao.save(paymentOrder);
            
            // 订单明细
            PaymentOrderItem paymentOrderItem = new PaymentOrderItem();
            paymentOrderItem.setOrderId(paymentOrder.getOrderId()+"");
            paymentOrderItem.setGoodsId(baseOrder.getGoodsId());
            paymentOrderItem.setNum(1);
            paymentOrderItem.setPrice(paymentOrder.getPayPrice());
            paymentOrderItem.setAddTime(DateUtil.currentDate());
            paymentOrderItemDao.save(paymentOrderItem);
            logger.info("==========支付订单信息[订单id：{}，订单号：{}]", paymentOrder.getOrderId(), paymentOrder.getOrderNo());
            logger.info("==========支付订单详情：{}", JsonUtil.object2Json(paymentOrderItem));
            
            // 用户支出明细
            PaymentLog paymentLog = new PaymentLog();
            paymentLog.setOrderNo(paymentOrder.getOrderNo());
            paymentLog.setUserId(paymentOrder.getUserId());
            paymentLog.setToUser(paymentOrder.getToUser());
            paymentLog.setType(paymentOrder.getPayType());// 支付类型
            paymentLog.setFrom(FromType.getName(baseOrder.getFrom()));// 支付来源
            paymentLog.setMark(Constants.MARK_PAY);
            paymentLog.setMoney(paymentOrder.getPayPrice());
            paymentLog.setStatus(PaymentStatus.PAYMENT_WAIT_BUYER_PAY);
            paymentLog.setMessage(
                    "您" + DateUtil.getCurrentDate() + "【"+paymentLog.getFrom()+"】支付（" + paymentLog.getMoney() + "）元");
            paymentLog.setAddTime(paymentOrder.getAddTime());
            paymentLogDao.save(paymentLog);
            logger.info("==========用户支出明细：{}", JsonUtil.object2Json(paymentLog));
            
//            /**
//             * 如果使用账户自扣费，非支付宝微信支付
//             */
//            if (paymentOrderT.getType() != null
//                    && PaymentType.SPAY.getName().equals(paymentOrderT.getType())) {// 写入资金操作日志
//                Account fromAccount = accountDao.getByUserId(paymentOrder.getUserId());
//                Account toAccount = accountDao.getByUserId(paymentOrderItem.getToUser());
//                if (fromAccount != null && toAccount != null) {
//                    AccountLog fromAccountLog = new AccountLog();
//                    fromAccountLog.setUserId(fromAccount.getUserId());
//                    fromAccountLog.setType(OperateType.FREEZE.getName());
//                    fromAccountLog.setTotal(fromAccount.getTotal());
//                    fromAccountLog.setMoney(paymentOrder.getPayPrice());
//                    fromAccountLog
//                            .setUseMoney(fromAccount.getUseMoney() - paymentOrder.getPayPrice());
//                    fromAccountLog.setNoUseMoney(
//                            fromAccount.getNoUseMoney() + paymentOrder.getPayPrice());
//                    fromAccountLog.setCollection(fromAccount.getCollection());
//                    fromAccountLog.setToUser(paymentOrderItem.getToUser());
//                    fromAccountLog.setRemark("待付款，冻结=" + fromAccountLog.getMoney());
//                    fromAccountLog.setAddTime(DateUtil.currentDate());
//                    accountLogDao.save(fromAccountLog);
//
//                    AccountLog toAccountLog = new AccountLog();
//                    toAccountLog.setUserId(paymentOrderItem.getToUser());
//                    toAccountLog.setType(OperateType.WAIT_INTEREST.getName());
//                    toAccountLog.setTotal(toAccount.getTotal() + paymentOrder.getPayPrice());
//                    toAccountLog.setMoney(paymentOrder.getPayPrice());
//                    toAccountLog.setUseMoney(toAccount.getUseMoney());
//                    toAccountLog
//                            .setNoUseMoney(toAccount.getNoUseMoney() + paymentOrder.getPayPrice());
//                    toAccountLog.setCollection(toAccount.getCollection());
//                    toAccountLog.setRemark("待收款，冻结=" + toAccountLog.getMoney());
//                    toAccountLog.setAddTime(DateUtil.currentDate());
//                    accountLogDao.save(toAccountLog);
//                }
//            }
        } catch (Exception ex) {
            throw new BizException(ex);
        }
    }
    
    /**
     * 更新订单状态，写入消费明细记录
     * 
     * @param paymentIncomeLog
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public void updatePaymentOrderAndLog(final CommonOrder baseOrder) throws Exception {
        try {
            PaymentOrder paymentOrder = getByOrderNo(baseOrder.getOrderNo());
            if (paymentOrder != null) {
                Integer userId = paymentOrder.getUserId();
                Integer toUserId = paymentOrder.getToUser();
                String orderNo = paymentOrder.getOrderNo();
                Double money = paymentOrder.getPayPrice();
                
                // 更新订单状态，支付成功
                paymentOrder.setPayStatus(PaymentStatus.PAYMENT_TRADE_SUCCESS);
                paymentOrder.setLastUpdateDtime(DateUtil.currentDate());
                this.dao.update(paymentOrder);
                
                // 更新日志--用户支付记录
                List<PaymentLog> paymentLogList = paymentLogDao.getByOrderNo(orderNo);
                PaymentLog paymentLog1 = null;
                if (paymentLogList != null) {
                    paymentLog1 = paymentLogList.get(0);
                    paymentLog1.setStatus(PaymentStatus.PAYMENT_TRADE_SUCCESS);
                    paymentLog1.setLastUpdateDtime(DateUtil.currentDate());
                    paymentLogDao.update(paymentLog1);
                }
                
                // 判断是否有支付对象，写入日志--账户收款记录
                if (userId != null && toUserId != null) {
                    PaymentLog paymentLog2 = new PaymentLog();
                    paymentLog2.setOrderNo(orderNo);
                    paymentLog2.setUserId(toUserId);
                    paymentLog2.setToUser(userId);
                    paymentLog2.setMark(Constants.MARK_INCOME);// 收入
                    paymentLog2.setMoney(money);
                    if (paymentLog1 != null) {
                        paymentLog2.setType(paymentLog1.getType());
                        paymentLog2.setFrom(paymentLog1.getFrom());
                    }
                    paymentLog2.setMessage("您" + DateUtil.getCurrentDate() + "【"+paymentLog2.getFrom()+"】收入（" + money + "）元");
                    paymentLog2.setAddTime(DateUtil.currentDate());
                    paymentLogDao.save(paymentLog2);
                }
            }
        } catch (Exception ex) {
            throw new BizException(ex);
        }
    }
    
    /**
     * 在线复诊，超过三天未回复。进行退款....
     * 
     * @param askQuestionAnswer
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public void refundAskQuestion(AskQuestionAnswer askQuestionAnswer) throws Exception {
        if (askQuestionAnswer == null) {
            return;
        }
        
        // messageId为交易订单号
        LogUtil.getTaskLogger().info("===============在线问诊，进行退款。questionId={}，messageId={}", new Object[] {askQuestionAnswer.getId(), askQuestionAnswer.getMessageId()});
        try {
            final String orderNo = askQuestionAnswer.getMessageId();
            // 1、更新支付记录状态，交易关闭
            PaymentOrder paymentOrder = getByOrderNo(orderNo);
            LogUtil.getTaskLogger().info("===============在线问诊，退款单。paymentOrder={}", JSON.toJSONString(paymentOrder));
            if (paymentOrder != null) {
                paymentOrder.setPayStatus(PaymentStatus.PAYMENT_TRADE_CLOSE);
                paymentOrder.setLastUpdateDtime(DateUtil.currentDate());
                this.update(paymentOrder);
            }
            // 2、更新账户退款记录
            List<PaymentLog> paymentLogList = paymentLogDao.getByOrderNo(orderNo);
            LogUtil.getTaskLogger().info("===============在线问诊，收支明细记录。paymentLog={}", JSON.toJSONString(paymentLogList));
            for (PaymentLog paymentLog : paymentLogList) {
                paymentLog.setMark("3");// 1=收入，2=支出，3=退回
                paymentLog.setLastUpdateDtime(DateUtil.currentDate());
                paymentLogDao.update(paymentLog);
            }
//            // 3、调用支付宝退款
//            String returnStr = AlipayRefundUtil.alipayRefundRequest(paymentOrder.getOrderNo(), null, paymentOrder.getPayPrice());
//            LogUtil.getTaskLogger().info("===============在线问诊，调用支付宝退款。returnStr={}", returnStr);
//            if (!"success".equals(returnStr)) {
//                // 支付宝退款不成功，回滚事务。
//                throw new RuntimeException("支付宝退款不成功，回滚事务。");
//            }
        } catch (Exception ex) {
            throw new BizException(ex);
        }
    }
    
    /**
     * 订单号获取订单
     * 
     * @param orderNo
     * @return
     */
    public PaymentOrder getByOrderNo(String orderNo) {
        try {
            return this.dao.getByOrderNo(orderNo);
        } catch (Exception ex) {
            throw new DBException(ex);
        }
    }
    
    /**
     * 订单状态
     * 
     * @param orderNo
     * @return
     */
    public boolean orderPayStatus(String orderNo) {
        PaymentOrder paymentOrder = getByOrderNo(orderNo);
        if (paymentOrder != null) {
            return PaymentStatus.PAYMENT_TRADE_SUCCESS.equals(paymentOrder.getPayStatus());
        }
        return false;
    }
}
