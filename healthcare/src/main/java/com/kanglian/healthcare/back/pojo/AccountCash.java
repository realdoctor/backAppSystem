package com.kanglian.healthcare.back.pojo;

import java.util.Date;
import com.easyway.business.framework.pojo.BasePojo;

public class AccountCash extends BasePojo {
	private static final long serialVersionUID = 1L;
	private Long id;
	private Integer userId;
	private String type;
	private Boolean status;
	private String account;
	private String bank;
	private String branch;
	private String alipayid;
	private String wxid;
	private Double money;
	private Double credited;
	private Double fee;
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
	public Boolean getStatus() {
		return status;
	}
	public void setStatus(Boolean status) {
		this.status = status;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getBank() {
		return bank;
	}
	public void setBank(String bank) {
		this.bank = bank;
	}
	public String getBranch() {
		return branch;
	}
	public void setBranch(String branch) {
		this.branch = branch;
	}
	public String getAlipayid() {
		return alipayid;
	}
	public void setAlipayid(String alipayid) {
		this.alipayid = alipayid;
	}
	public String getWxid() {
		return wxid;
	}
	public void setWxid(String wxid) {
		this.wxid = wxid;
	}
	public Double getMoney() {
		return money;
	}
	public void setMoney(Double money) {
		this.money = money;
	}
	public Double getCredited() {
		return credited;
	}
	public void setCredited(Double credited) {
		this.credited = credited;
	}
	public Double getFee() {
		return fee;
	}
	public void setFee(Double fee) {
		this.fee = fee;
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
