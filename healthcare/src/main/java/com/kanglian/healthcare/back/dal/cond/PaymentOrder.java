package com.kanglian.healthcare.back.dal.cond;

import java.util.List;

/**
 * 支付订单
 * 
 * @author xl.liu
 */
public class PaymentOrder {

    // 交易总额
    private String      totalAmount;
    // 订单列表
    private List<Order> goodsList;

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public List<Order> getGoodsList() {
        return goodsList;
    }

    public void setGoodsList(List<Order> goodsList) {
        this.goodsList = goodsList;
    }

}
