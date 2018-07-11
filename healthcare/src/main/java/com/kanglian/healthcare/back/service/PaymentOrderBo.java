package com.kanglian.healthcare.back.service;

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
}
