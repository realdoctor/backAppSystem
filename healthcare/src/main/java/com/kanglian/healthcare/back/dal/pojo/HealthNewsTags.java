package com.kanglian.healthcare.back.dal.pojo;

import com.easyway.business.framework.pojo.BasePojo;

public class HealthNewsTags extends BasePojo {
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Integer newsTypeId;
	private String newsTag;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getNewsTypeId() {
		return newsTypeId;
	}
	public void setNewsTypeId(Integer newsTypeId) {
		this.newsTypeId = newsTypeId;
	}
	public String getNewsTag() {
		return newsTag;
	}
	public void setNewsTag(String newsTag) {
		this.newsTag = newsTag;
	}
}
