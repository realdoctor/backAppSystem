package com.kanglian.healthcare.back.pojo;

import com.alibaba.fastjson.annotation.JSONField;
import com.easyway.business.framework.pojo.BasePojo;

public class NoticeDiagDrug extends BasePojo {
	private static final long serialVersionUID = 1L;
	@JSONField(name="noticeDiagDrugId")
	private Long id;
	private String noticeMessageId;
	@JSONField(name="stdCode")
	private String drugCode;
	@JSONField(name="stdName")
	private String drugName;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNoticeMessageId() {
		return noticeMessageId;
	}
	public void setNoticeMessageId(String noticeMessageId) {
		this.noticeMessageId = noticeMessageId;
	}
	public String getDrugCode() {
		return drugCode;
	}
	public void setDrugCode(String drugCode) {
		this.drugCode = drugCode;
	}
	public String getDrugName() {
		return drugName;
	}
	public void setDrugName(String drugName) {
		this.drugName = drugName;
	}
}
