package com.kanglian.healthcare.back.pojo;

import java.util.Date;
import com.easyway.business.framework.pojo.BasePojo;

public class AccountLog extends BasePojo {
	private static final long serialVersionUID = 1L;
	private Long id;
	private Integer userId;
	private String type;
	private Double total;
	private Double money;
	private Double useMoney;
	private Double noUseMoney;
	private Double collection;
	private Integer toUser;
	private String remark;
	private Date addTime;
	private Date lastUpdateDtime;
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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Double getTotal() {
		return total;
	}
	public void setTotal(Double total) {
		this.total = total;
	}
	public Double getMoney() {
		return money;
	}
	public void setMoney(Double money) {
		this.money = money;
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
	public Integer getToUser() {
		return toUser;
	}
	public void setToUser(Integer toUser) {
		this.toUser = toUser;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Date getAddTime() {
		return addTime;
	}
	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}
	public Date getLastUpdateDtime() {
		return lastUpdateDtime;
	}
	public void setLastUpdateDtime(Date lastUpdateDtime) {
		this.lastUpdateDtime = lastUpdateDtime;
	}
}
