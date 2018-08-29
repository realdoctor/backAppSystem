package com.kanglian.healthcare.back.pojo;

import com.easyway.business.framework.pojo.BasePojo;

public class Account extends BasePojo {
	private static final long serialVersionUID = 1L;
	private Long id;
	private Integer userId;
	private Double total;
	private Double useMoney;
	private Double noUseMoney;
	private Double collection;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Double getTotal() {
		return total;
	}
	public void setTotal(Double total) {
		this.total = total;
	}
	public Double getUseMoney() {
		return useMoney;
	}
	public void setUseMoney(Double useMoney) {
		this.useMoney = useMoney;
	}
	public Double getNoUseMoney() {
		return noUseMoney;
	}
	public void setNoUseMoney(Double noUseMoney) {
		this.noUseMoney = noUseMoney;
	}
	public Double getCollection() {
		return collection;
	}
	public void setCollection(Double collection) {
		this.collection = collection;
	}
}
