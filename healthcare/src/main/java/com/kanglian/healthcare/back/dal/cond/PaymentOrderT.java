package com.kanglian.healthcare.back.dal.cond;

/**
 * 支付订单
 * 
 * @author xl.liu
 */
public class PaymentOrderT {

    // 付费用户Id
    private String userId;
    // 支付给用户Id
    private String toUserId;
    // 订单号
    private String orderNo;
    // 支付金额
    private Double payAmount;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getToUserId() {
        return toUserId;
    }

    public void setToUserId(String toUserId) {
        this.toUserId = toUserId;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Double getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(Double payAmount) {
        this.payAmount = payAmount;
    }

}
