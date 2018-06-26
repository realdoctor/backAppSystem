package com.kanglian.healthcare.back.dal.pojo;

import java.util.Date;
import com.easyway.business.framework.pojo.BasePojo;

public class UserIdentify extends BasePojo {
	private static final long serialVersionUID = 1L;
	private Long id;
	private Integer userId;
	private String typeId;
	private String idNo;
	private String name;
	private Integer status;
	private String litpic;
	private Date verifyTime;
	private Integer verifyUser;
	private String verifyRemark;
	private Date addTime;
	private String realName;
    private String mobilePhone;
	public String getRealName() {
        return realName;
    }
    public void setRealName(String realName) {
        this.realName = realName;
    }
    public String getMobilePhone() {
        return mobilePhone;
    }
    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }
    public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getTypeId() {
		return typeId;
	}
	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}
	public String getIdNo() {
		return idNo;
	}
	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getLitpic() {
		return litpic;
	}
	public void setLitpic(String litpic) {
		this.litpic = litpic;
	}
	public Date getVerifyTime() {
		return verifyTime;
	}
	public void setVerifyTime(Date verifyTime) {
		this.verifyTime = verifyTime;
	}
	public Integer getVerifyUser() {
		return verifyUser;
	}
	public void setVerifyUser(Integer verifyUser) {
		this.verifyUser = verifyUser;
	}
	public String getVerifyRemark() {
		return verifyRemark;
	}
	public void setVerifyRemark(String verifyRemark) {
		this.verifyRemark = verifyRemark;
	}
	public Date getAddTime() {
		return addTime;
	}
	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}
}
