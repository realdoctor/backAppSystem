package com.kanglian.healthcare.back.common;

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
    // 商品id
    private String goodsId;
    // 支付类型
    private String type;
    // 支付来源(支付来源：1，聊天支付；2，问诊支付；3，视频支付；4，图文支付；5，资讯支付)
    private String from;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }
    
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

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }
}
