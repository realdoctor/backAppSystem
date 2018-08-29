package com.kanglian.healthcare.back.pojo;

import com.easyway.business.framework.pojo.BasePojo;

public class HealthNewsTags extends BasePojo {
	private static final long serialVersionUID = 1L;
	private Integer tagId;
	private Integer newsId;
	private String newsTag;
	public Integer getTagId() {
		return tagId;
	}
	public void setTagId(Integer tagId) {
		this.tagId = tagId;
	}
	public Integer getNewsId() {
		return newsId;
	}
	public void setNewsId(Integer newsId) {
		this.newsId = newsId;
	}
	public String getNewsTag() {
		return newsTag;
	}
	public void setNewsTag(String newsTag) {
		this.newsTag = newsTag;
	}
}
