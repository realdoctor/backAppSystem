package com.kanglian.healthcare.back.service;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.easyway.business.framework.bo.CrudBo;
import com.easyway.business.framework.util.DateUtil;
import com.kanglian.healthcare.back.constants.PaymentStatus;
import com.kanglian.healthcare.back.dal.cond.PaymentOrderT;
import com.kanglian.healthcare.back.dal.dao.PaymentIncomeLogDao;
import com.kanglian.healthcare.back.dal.dao.PaymentOrderDao;
import com.kanglian.healthcare.back.dal.dao.PaymentOrderItemDao;
import com.kanglian.healthcare.back.dal.pojo.PaymentIncomeLog;
import com.kanglian.healthcare.back.dal.pojo.PaymentOrder;
import com.kanglian.healthcare.back.dal.pojo.PaymentOrderItem;
import com.kanglian.healthcare.exception.DBException;

@Service
public class PaymentOrderBo extends CrudBo<PaymentOrder, PaymentOrderDao> {

    @Autowired
    private PaymentOrderItemDao paymentOrderItemDao;
//    @Autowired
//    private AccountDao          accountDao;
//    @Autowired
//    private AccountLogDao       accountLogDao;
    @Autowired
    private PaymentIncomeLogDao paymentIncomeLogDao;
    
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
            paymentOrder.setPayPrice(paymentOrderT.getPayAmount());
            paymentOrder.setPayTime(DateUtil.currentDate());
            paymentOrder.setPayStatus(PaymentStatus.PAYMENT_WAIT_BUYER_PAY);
            paymentOrder.setAddTime(DateUtil.currentDate());
            this.dao.save(paymentOrder);

            // 插入订单明细
            Integer orderId = paymentOrder.getOrderId();
            PaymentOrderItem paymentOrderItem = new PaymentOrderItem();
            paymentOrderItem.setOrderId(orderId);
            paymentOrderItem.setPrice(paymentOrder.getPayPrice());
            paymentOrderItem.setNum(1);
            paymentOrderItem.setToUser(Integer.valueOf(paymentOrderT.getToUserId()));
            paymentOrderItem.setAddTime(DateUtil.currentDate());
            paymentOrderItemDao.save(paymentOrderItem);

            // 用户支出明细
            PaymentIncomeLog paymentIncomeLog = new PaymentIncomeLog();
            paymentIncomeLog.setOrderNo(paymentOrder.getOrderNo());
            paymentIncomeLog.setUserId(paymentOrder.getUserId());
            paymentIncomeLog.setToUser(paymentOrderItem.getToUser());
            paymentIncomeLog.setType("支付宝");
            paymentIncomeLog.setMark("2");
            paymentIncomeLog.setMoney(paymentOrder.getPayPrice());
            paymentIncomeLog.setStatus(PaymentStatus.PAYMENT_WAIT_BUYER_PAY);
            paymentIncomeLog.setMessage(
                    "您[" + DateUtil.getCurrentDate() + "]支出[" + paymentIncomeLog.getMoney() + "]元");
            paymentIncomeLog.setAddTime(paymentOrder.getAddTime());
            paymentIncomeLogDao.save(paymentIncomeLog);
            
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
            Map<String, Object> resultMap = getByOrderNo(paymentOrderT.getOrderNo());
            if (resultMap != null) {
                // 更新订单状态，支付成功
                PaymentOrder paymentOrder = new PaymentOrder();
                paymentOrder.setOrderId((int) resultMap.get("orderId"));
                paymentOrder.setPayStatus(PaymentStatus.PAYMENT_TRADE_SUCCESS);
                this.dao.update(paymentOrder);

                Integer userId = Integer.valueOf(resultMap.get("userId") + "");
                Integer toUserId = Integer.valueOf(resultMap.get("toUserId") + "");
                String orderNo = resultMap.get("orderNo") + "";
                Double money = Double.valueOf(resultMap.get("payPrice") + "");
//                // 插入支出用户记录
//                PaymentIncomeLog paymentIncomeLog1 = new PaymentIncomeLog();
//                paymentIncomeLog1.setOrderNo(orderNo);
//                paymentIncomeLog1.setUserId(userId);
//                paymentIncomeLog1.setToUser(toUserId);
//                paymentIncomeLog1.setMark("2");// 支出
//                paymentIncomeLog1.setMoney(money);
//                paymentIncomeLog1
//                        .setMessage("您[" + DateUtil.getCurrentDate() + "]支出[" + money + "]元");
//                paymentIncomeLog1.setLastUpdateDtime(DateUtil.currentDate());
//                paymentIncomeLogDao.save(paymentIncomeLog1);
                
                // 更新支出用户记录
                PaymentIncomeLog paymentIncomeLog = paymentIncomeLogDao.getByOrderNo(orderNo);
                if (paymentIncomeLog != null) {
                    paymentIncomeLog.setStatus(PaymentStatus.PAYMENT_TRADE_SUCCESS);
                    paymentIncomeLog.setLastUpdateDtime(DateUtil.currentDate());
                    paymentIncomeLogDao.update(paymentIncomeLog);
                }
                
                // 插入收款账户记录
                PaymentIncomeLog paymentIncomeLog2 = new PaymentIncomeLog();
                paymentIncomeLog2.setOrderNo(orderNo);
                paymentIncomeLog2.setUserId(toUserId);
                paymentIncomeLog2.setToUser(userId);
                paymentIncomeLog2.setMark("1");// 收入
                paymentIncomeLog2.setMoney(money);
                paymentIncomeLog2
                        .setMessage("您[" + DateUtil.getCurrentDate() + "]收入[" + money + "]元");
                paymentIncomeLog2.setAddTime(DateUtil.currentDate());
                paymentIncomeLogDao.save(paymentIncomeLog2);
            }
        } catch (Exception ex) {
            throw new DBException(ex);
        }
    }
    
    /**
     * 订单号获取订单
     * 
     * @param orderNo
     * @return
     */
    public Map<String, Object> getByOrderNo(String orderNo) {
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
        Map<String, Object> orderMap = getByOrderNo(orderNo);
        if (orderMap != null && orderMap.get("payStatus") != null) {
            return PaymentStatus.PAYMENT_TRADE_SUCCESS.equals(orderMap.get("payStatus"));
        }
        return false;
    }
}
