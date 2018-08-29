package com.kanglian.healthcare.back.common;

/**
 * 订单对象
 * 
 * @author xl.liu
 */
public class PaymentOrderItem {

    // 商品Id
    private Integer goodsId;
    // 商品数量
    private Integer goodsNum;
    // 商品价格
    private Double  goodsPrice;

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public Integer getGoodsNum() {
        return goodsNum;
    }

    public void setGoodsNum(Integer goodsNum) {
        this.goodsNum = goodsNum;
    }

    public Double getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(Double goodsPrice) {
        this.goodsPrice = goodsPrice;
    }
}
