package com.kanglian.healthcare.back.dal.pojo;

import java.util.Date;
import com.alibaba.fastjson.annotation.JSONField;
import com.easyway.business.framework.json.annotion.NotJsonData;
import com.easyway.business.framework.pojo.BasePojo;
import com.easyway.business.framework.util.DateUtil;

public class User extends BasePojo {
	private static final long serialVersionUID = 1L;
	private Long userId;
	private String userName;
	@JSONField(serialize=false)
	private String pwd;
	@JSONField(serialize=false)
	private String payPwd;
	private String realName;
	private String email;
	private String mobilePhone;
	@JSONField(serialize=false)
	private Date addTime;
	@JSONField(serialize=false)
	private Date lastUpdateDtime;
	private Date lastLoginDtime;
    /**
     * 扩展字段
     */
    private transient String verifyCode;
    private Integer roleId;
    @NotJsonData
    public String getVerifyCode() {
        return verifyCode;
    }
    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }
    public Integer getRoleId() {
        return roleId;
    }
    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	@NotJsonData
	public String getPayPwd() {
		return payPwd;
	}
	public void setPayPwd(String payPwd) {
		this.payPwd = payPwd;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getMobilePhone() {
		return mobilePhone;
	}
	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
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
    public Date getLastLoginDtime() {
        if(lastLoginDtime == null)
            lastLoginDtime = DateUtil.currentDate();
        return lastLoginDtime;
    }
    public void setLastLoginDtime(Date lastLoginDtime) {
        this.lastLoginDtime = lastLoginDtime;
    }
}
