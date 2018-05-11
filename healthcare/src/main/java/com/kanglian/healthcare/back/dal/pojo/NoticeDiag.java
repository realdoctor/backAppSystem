package com.kanglian.healthcare.back.dal.pojo;

import com.alibaba.fastjson.annotation.JSONField;
import com.easyway.business.framework.pojo.BasePojo;

public class NoticeDiag extends BasePojo {
	private static final long serialVersionUID = 1L;
	@JSONField(name="noticeDiagId")
	private Long id;
	private String noticeMessageId;
	private String diagCode;
	private String diagName;
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
	public String getDiagCode() {
		return diagCode;
	}
	public void setDiagCode(String diagCode) {
		this.diagCode = diagCode;
	}
	public String getDiagName() {
		return diagName;
	}
	public void setDiagName(String diagName) {
		this.diagName = diagName;
	}
}
