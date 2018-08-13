package com.kanglian.healthcare.back.dal.pojo;

import java.util.Date;
import com.alibaba.fastjson.annotation.JSONField;
import com.easyway.business.framework.pojo.BasePojo;

public class AskQuestionMoney extends BasePojo {
	private static final long serialVersionUID = 1L;
	private Long userId;
	private Double chatMoney;
	private Double questionMoney;
	@JSONField(serialize=false)
	private Date addTime;
	@JSONField(serialize=false)
	private Date lastUpdateDtime;
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Double getChatMoney() {
		return chatMoney;
	}
	public void setChatMoney(Double chatMoney) {
		this.chatMoney = chatMoney;
	}
	public Double getQuestionMoney() {
		return questionMoney;
	}
	public void setQuestionMoney(Double questionMoney) {
		this.questionMoney = questionMoney;
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
