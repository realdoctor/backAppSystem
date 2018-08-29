package com.kanglian.healthcare.back.common;

import java.util.List;

/**
 * 支付订单
 * 
 * @author xl.liu
 */
public class PaymentOrder {

    // 用户Id
    private String                 userId;
    // 订单号
    private String                 orderNo;
    // 交易总额
    private Double                 totalAmount;
    // 订单列表
    private List<PaymentOrderItem> goodsList;
    // 支付类型（支付类型 ：支付宝支付|alipay，微信支付|wxpay..）
    private String                 type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public List<PaymentOrderItem> getGoodsList() {
        return goodsList;
    }

    public void setGoodsList(List<PaymentOrderItem> goodsList) {
        this.goodsList = goodsList;
    }

}
