package com.kanglian.healthcare.back.dal.pojo;

import com.alibaba.fastjson.annotation.JSONField;
import com.easyway.business.framework.pojo.BasePojo;

public class HospitalAddress extends BasePojo {
	private static final long serialVersionUID = 1L;
	private Integer hospitalId;
	@JSONField(serialize=false)
	private String province;
	@JSONField(serialize=false)
	private String city;
	private String hospitalName;
	private String hospitalLevel;
    private String markNum;
    public Integer getHospitalId() {
		return hospitalId;
	}
	public void setHospitalId(Integer hospitalId) {
		this.hospitalId = hospitalId;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getHospitalName() {
		return hospitalName;
	}
	public void setHospitalName(String hospitalName) {
		this.hospitalName = hospitalName;
	}
    public String getHospitalLevel() {
        return hospitalLevel;
    }
    public void setHospitalLevel(String hospitalLevel) {
        this.hospitalLevel = hospitalLevel;
    }
	public String getMarkNum() {
        return markNum;
    }
    public void setMarkNum(String markNum) {
        this.markNum = markNum;
    }
}
