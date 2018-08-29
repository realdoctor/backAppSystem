package com.kanglian.healthcare.back.pojo;

import java.util.Date;
import com.easyway.business.framework.pojo.BasePojo;

public class AlipayNotifyLog extends BasePojo {
	private static final long serialVersionUID = 1L;
	private Long id;
	private String userId;
	private String orderNo;
	private String notifyString;
	private Date addTime;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
	public String getNotifyString() {
		return notifyString;
	}
	public void setNotifyString(String notifyString) {
		this.notifyString = notifyString;
	}
	public Date getAddTime() {
		return addTime;
	}
	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}
}
