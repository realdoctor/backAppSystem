package com.kanglian.healthcare.back.dal.cond;

public class BaseBody {

    // 用户Id
    private String userId;
    // 手机号码
    private String mobilePhone;
    // 身份证Id
    private String idNo;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getIdNo() {
        return idNo;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }
}
