package com.kanglian.healthcare.back.dal.pojo;

import java.util.Date;
import java.util.List;
import com.easyway.business.framework.pojo.BasePojo;

public class HealthNews extends BasePojo {
	private static final long serialVersionUID = 1L;
	private Integer newsId;
	private String newsName;
	private Integer newsTypeId;
	private Integer userId;
	private String newsAuthor;
	private String authorProfer;
	private String authorHos;
	private String authorDept;
	private String article;
	private Double price;
	private Integer putOn;
	private Integer commend;
	private String photoAddress;
	private Integer viewedTime;
	private Date addTime;
	private Date lastUpdateDtime;
    /**
	 * 扩展字段
	 */
	private String newsType;
    private List<HealthNewsTags> tagsList;
    public String getNewsType() {
        return newsType;
    }
    public void setNewsType(String newsType) {
        this.newsType = newsType;
    }
    public List<HealthNewsTags> getTagsList() {
        return tagsList;
    }
    public void setTagsList(List<HealthNewsTags> tagsList) {
        this.tagsList = tagsList;
    }
	public Integer getNewsId() {
		return newsId;
	}
	public void setNewsId(Integer newsId) {
		this.newsId = newsId;
	}
	public String getNewsName() {
		return newsName;
	}
	public void setNewsName(String newsName) {
		this.newsName = newsName;
	}
	public Integer getNewsTypeId() {
		return newsTypeId;
	}
	public void setNewsTypeId(Integer newsTypeId) {
		this.newsTypeId = newsTypeId;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getNewsAuthor() {
		return newsAuthor;
	}
	public void setNewsAuthor(String newsAuthor) {
		this.newsAuthor = newsAuthor;
	}
	public String getAuthorProfer() {
		return authorProfer;
	}
	public void setAuthorProfer(String authorProfer) {
		this.authorProfer = authorProfer;
	}
	public String getAuthorHos() {
		return authorHos;
	}
	public void setAuthorHos(String authorHos) {
		this.authorHos = authorHos;
	}
	public String getAuthorDept() {
		return authorDept;
	}
	public void setAuthorDept(String authorDept) {
		this.authorDept = authorDept;
	}
	public String getArticle() {
		return article;
	}
	public void setArticle(String article) {
		this.article = article;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public Integer getPutOn() {
		return putOn;
	}
	public void setPutOn(Integer putOn) {
		this.putOn = putOn;
	}
	public Integer getCommend() {
		return commend;
	}
	public void setCommend(Integer commend) {
		this.commend = commend;
	}
	public String getPhotoAddress() {
		return photoAddress;
	}
	public void setPhotoAddress(String photoAddress) {
		this.photoAddress = photoAddress;
	}
	public Integer getViewedTime() {
		return viewedTime;
	}
	public void setViewedTime(Integer viewedTime) {
		this.viewedTime = viewedTime;
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
