package com.kanglian.healthcare.back.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.easyway.business.framework.bo.CrudBo;
import com.easyway.business.framework.util.DateUtil;
import com.easyway.business.framework.util.StringUtil;
import com.kanglian.healthcare.back.constants.Constants;
import com.kanglian.healthcare.back.constants.FromType;
import com.kanglian.healthcare.back.constants.PaymentStatus;
import com.kanglian.healthcare.back.constants.PaymentType;
import com.kanglian.healthcare.back.dal.cond.PaymentOrderT;
import com.kanglian.healthcare.back.dal.dao.PaymentLogDao;
import com.kanglian.healthcare.back.dal.dao.PaymentOrderDao;
import com.kanglian.healthcare.back.dal.dao.PaymentOrderItemDao;
import com.kanglian.healthcare.back.dal.pojo.AskQuestionAnswer;
import com.kanglian.healthcare.back.dal.pojo.PaymentLog;
import com.kanglian.healthcare.back.dal.pojo.PaymentOrder;
import com.kanglian.healthcare.back.dal.pojo.PaymentOrderItem;
import com.kanglian.healthcare.exception.DBException;

@Service
public class PaymentOrderBo extends CrudBo<PaymentOrder, PaymentOrderDao> {

    /** logger */
    private final static Logger logger = LoggerFactory.getLogger(PaymentOrderBo.class);
    
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
    public void createPaymentOrderAndLog(PaymentOrderT paymentOrderT) {
        try {
            // 创建订单
            PaymentOrder paymentOrder = new PaymentOrder();
            paymentOrder.setOrderNo(paymentOrderT.getOrderNo());
            paymentOrder.setUserId(Integer.valueOf(paymentOrderT.getUserId()));
            if (StringUtil.isNotEmpty(paymentOrderT.getToUserId())) {
                paymentOrder.setToUser(Integer.valueOf(paymentOrderT.getToUserId()));
            }
            paymentOrder.setPayPrice(paymentOrderT.getPayAmount());
            paymentOrder.setPayTime(DateUtil.currentDate());
            paymentOrder.setPayStatus(PaymentStatus.PAYMENT_WAIT_BUYER_PAY);
            paymentOrder.setAddTime(DateUtil.currentDate());
            this.dao.save(paymentOrder);
            
            // 订单明细
            PaymentOrderItem paymentOrderItem = new PaymentOrderItem();
            paymentOrderItem.setOrderId(paymentOrder.getOrderId()+"");
            paymentOrderItem.setGoodsId(paymentOrderT.getGoodsId());
            paymentOrderItem.setNum(1);
            paymentOrderItem.setPrice(paymentOrder.getPayPrice());
            paymentOrderItem.setAddTime(DateUtil.currentDate());
            paymentOrderItemDao.save(paymentOrderItem);

            // 用户支出明细
            PaymentLog paymentLog = new PaymentLog();
            paymentLog.setOrderNo(paymentOrder.getOrderNo());
            paymentLog.setUserId(paymentOrder.getUserId());
            paymentLog.setToUser(paymentOrder.getToUser());
            paymentLog.setType(PaymentType.getValue(paymentOrderT.getType()));// 支付类型
            paymentLog.setFrom(FromType.getName(paymentOrderT.getFrom()));// 支付来源
            paymentLog.setMark(Constants.MARK_PAY);
            paymentLog.setMoney(paymentOrder.getPayPrice());
            paymentLog.setStatus(PaymentStatus.PAYMENT_WAIT_BUYER_PAY);
            paymentLog.setMessage(
                    "您" + DateUtil.getCurrentDate() + "【"+paymentLog.getFrom()+"】支付（" + paymentLog.getMoney() + "）元");
            paymentLog.setAddTime(paymentOrder.getAddTime());
            paymentLogDao.save(paymentLog);
            
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
            throw new DBException(ex);
        }
    }
    
    /**
     * 更新订单状态，写入消费明细记录
     * 
     * @param paymentIncomeLog
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public void updatePaymentOrderAndLog(final PaymentOrderT paymentOrderT) {
        try {
            PaymentOrder paymentOrder = getByOrderNo(paymentOrderT.getOrderNo());
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
                PaymentLog paymentLog1 = paymentLogDao.getByOrderNo(orderNo);
                if (paymentLog1 != null) {
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
            throw new DBException(ex);
        }
    }
    
    /**
     * 在线复诊，超过三天未回复。进行退款....
     * 
     * @param askQuestionAnswer
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public void refundAskQuestion(AskQuestionAnswer askQuestionAnswer) {
        if (askQuestionAnswer == null) {
            return;
        }
        logger.info("===============在线复诊，进行退款");
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
