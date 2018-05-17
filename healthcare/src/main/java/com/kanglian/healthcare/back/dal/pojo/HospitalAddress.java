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
	private String lng;
	private String lat;
    private String hospitalName;
	private String hospitalLevel;
	private String doctorName;
    @JSONField(name="goodAt")
    private String field;
    private String doctorIntro;
    private String positional;
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
    public String getLng() {
        return lng;
    }
    public void setLng(String lng) {
        this.lng = lng;
    }
    public String getLat() {
        return lat;
    }
    public void setLat(String lat) {
        this.lat = lat;
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
    public String getDoctorName() {
        return doctorName;
    }
    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }
    public String getField() {
        return field;
    }
    public void setField(String field) {
        this.field = field;
    }
    public String getDoctorIntro() {
        return doctorIntro;
    }
    public void setDoctorIntro(String doctorIntro) {
        this.doctorIntro = doctorIntro;
    }
    public String getPositional() {
        return positional;
    }
    public void setPositional(String positional) {
        this.positional = positional;
    }
	public String getMarkNum() {
        return markNum;
    }
    public void setMarkNum(String markNum) {
        this.markNum = markNum;
    }
}
