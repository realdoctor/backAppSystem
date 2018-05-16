package com.kanglian.healthcare.back.dal.pojo;

import com.alibaba.fastjson.annotation.JSONField;
import com.easyway.business.framework.pojo.BasePojo;

public class NoticeComment extends BasePojo {
	private static final long serialVersionUID = 1L;
	@JSONField(name="noticeCommentId")
	private Long id;
	private String noticeMessageId;
	@JSONField(serialize=false)
	private String sentUser;
	@JSONField(serialize=false)
	private String receiveUser;
	@JSONField(name="stdCode")
	private String content;
	@JSONField(name="stdName")
	private String replyContent;
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
	public String getSentUser() {
		return sentUser;
	}
	public void setSentUser(String sentUser) {
		this.sentUser = sentUser;
	}
	public String getReceiveUser() {
		return receiveUser;
	}
	public void setReceiveUser(String receiveUser) {
		this.receiveUser = receiveUser;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getReplyContent() {
        return replyContent;
    }
    public void setReplyContent(String replyContent) {
        this.replyContent = replyContent;
    }
}
