package com.kanglian.healthcare.back.dal.pojo;

import com.easyway.business.framework.pojo.BasePojo;

public class UserDoctor extends BasePojo {
	private static final long serialVersionUID = 1L;
	private Integer userId;
	private String doctorCode;
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getDoctorCode() {
		return doctorCode;
	}
	public void setDoctorCode(String doctorCode) {
		this.doctorCode = doctorCode;
	}
}
