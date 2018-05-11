package com.kanglian.healthcare.back.dal.pojo;

import com.easyway.business.framework.pojo.BasePojo;
import com.fasterxml.jackson.annotation.JsonProperty;

public class NoticeComment extends BasePojo {
	private static final long serialVersionUID = 1L;
	@JsonProperty("noticeCommentId")
	private Long id;
	private String noticeMessageId;
	private Integer sentUser;
	private Integer receiveUser;
	private String content;
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
	public Integer getSentUser() {
		return sentUser;
	}
	public void setSentUser(Integer sentUser) {
		this.sentUser = sentUser;
	}
	public Integer getReceiveUser() {
		return receiveUser;
	}
	public void setReceiveUser(Integer receiveUser) {
		this.receiveUser = receiveUser;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
}
