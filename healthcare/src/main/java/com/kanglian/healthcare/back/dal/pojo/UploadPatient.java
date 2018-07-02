package com.kanglian.healthcare.back.dal.pojo;

import java.util.Date;
import com.easyway.business.framework.pojo.BasePojo;

public class UploadPatient extends BasePojo {
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String pubId;
	private Integer userId;
	private Integer receiveUserId;
	private String content;
	private String src;
	private String remark;
	private Date addTime;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getPubId() {
		return pubId;
	}
	public void setPubId(String pubId) {
		this.pubId = pubId;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Integer getReceiveUserId() {
		return receiveUserId;
	}
	public void setReceiveUserId(Integer receiveUserId) {
		this.receiveUserId = receiveUserId;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getSrc() {
		return src;
	}
	public void setSrc(String src) {
		this.src = src;
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
}
