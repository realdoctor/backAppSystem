package com.realdoctor.back.dal.pojo;

import java.util.Date;
import com.easyway.business.framework.pojo.BasePojo;

public class Goods extends BasePojo {
	private static final long serialVersionUID = 1L;
	private Long id;
	private String name;
	private Integer store;
	private Integer freezeStore;
	private Date shelvesTime;
	private Integer categoryId;
	private String description;
	private Double cost;
	private Double marketPrice;
	private String attribute;
	private String status;
	private String addUser;
	private Date addTime;
	private Date updateTime;
	private String remark;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getStore() {
		return store;
	}
	public void setStore(Integer store) {
		this.store = store;
	}
	public Integer getFreezeStore() {
		return freezeStore;
	}
	public void setFreezeStore(Integer freezeStore) {
		this.freezeStore = freezeStore;
	}
	public Date getShelvesTime() {
		return shelvesTime;
	}
	public void setShelvesTime(Date shelvesTime) {
		this.shelvesTime = shelvesTime;
	}
	public Integer getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Double getCost() {
		return cost;
	}
	public void setCost(Double cost) {
		this.cost = cost;
	}
	public Double getMarketPrice() {
		return marketPrice;
	}
	public void setMarketPrice(Double marketPrice) {
		this.marketPrice = marketPrice;
	}
	public String getAttribute() {
		return attribute;
	}
	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getAddUser() {
		return addUser;
	}
	public void setAddUser(String addUser) {
		this.addUser = addUser;
	}
	public Date getAddTime() {
		return addTime;
	}
	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	/**
	 * 扩展字段
	 */
	private String bigPic;
	private String smallPic;
    public String getBigPic() {
        return bigPic;
    }
    public void setBigPic(String bigPic) {
        this.bigPic = bigPic;
    }
    public String getSmallPic() {
        return smallPic;
    }
    public void setSmallPic(String smallPic) {
        this.smallPic = smallPic;
    }
}
