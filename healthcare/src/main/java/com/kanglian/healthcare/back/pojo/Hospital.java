package com.kanglian.healthcare.back.pojo;

import com.alibaba.fastjson.annotation.JSONField;
import com.easyway.business.framework.pojo.BasePojo;

public class Hospital extends BasePojo {
	private static final long serialVersionUID = 1L;
	private Integer hospitalId;
	private String hospitalName;
	private String hospitalCode;
	private String hospitalLevel;
	@JSONField(serialize=false)
	private String province;
	@JSONField(serialize=false)
	private String city;
	private String lng;
	private String lat;
	/**
	 * 医院接诊量
	 */
	@JSONField(name="receiveNum")
	private Integer appointmentNum;
	/**
	 * 扩展字段
	 */
	private String deptName;
	private String doctorName;
    @JSONField(name="goodAt")
    private String field;
    private String doctorIntro;
    private String positional;
    
    public Integer getHospitalId() {
		return hospitalId;
	}
	public void setHospitalId(Integer hospitalId) {
		this.hospitalId = hospitalId;
	}
	public String getHospitalName() {
		return hospitalName;
	}
	public void setHospitalName(String hospitalName) {
		this.hospitalName = hospitalName;
	}
	public String getHospitalCode() {
		return hospitalCode;
	}
	public void setHospitalCode(String hospitalCode) {
		this.hospitalCode = hospitalCode;
	}
	public String getHospitalLevel() {
		return hospitalLevel;
	}
	public void setHospitalLevel(String hospitalLevel) {
		this.hospitalLevel = hospitalLevel;
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
	public Integer getAppointmentNum() {
		return appointmentNum;
	}
	public void setAppointmentNum(Integer appointmentNum) {
		this.appointmentNum = appointmentNum;
	}
	
	/**
	 * 
	 */
	public String getDeptName() {
        return deptName;
    }
    public void setDeptName(String deptName) {
        this.deptName = deptName;
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
}
