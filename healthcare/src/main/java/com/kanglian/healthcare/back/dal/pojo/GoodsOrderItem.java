package com.kanglian.healthcare.back.dal.pojo;

import java.util.Date;
import com.easyway.business.framework.pojo.BasePojo;
import com.fasterxml.jackson.annotation.JsonProperty;

public class GoodsOrderItem extends BasePojo {
	private static final long serialVersionUID = 1L;
	@JsonProperty("goodsOrderItemId")
	private Long id;
	private Integer goodsOrderId;
	private Integer goodsId;
	private Integer goodsNum;
	private Double goodsPrice;
	private Date addTime;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Integer getGoodsOrderId() {
		return goodsOrderId;
	}
	public void setGoodsOrderId(Integer goodsOrderId) {
		this.goodsOrderId = goodsOrderId;
	}
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
	public Date getAddTime() {
		return addTime;
	}
	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}
}
