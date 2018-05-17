package com.kanglian.healthcare.back.dal.pojo;

import com.alibaba.fastjson.annotation.JSONField;
import com.easyway.business.framework.pojo.BasePojo;

public class HospitalRegisterDoctor extends BasePojo {
	private static final long serialVersionUID = 1L;
	private Integer doctorLicense;
	private String doctorName;
	private String doctorCode;
	private Integer deptId;
	private String deptCode;
	@JSONField(name="goodAt")
	private String field;
	private String doctorIntro;
	private String positional;
	private String hospitalName;
	private String hospitalLevel;
    public Integer getDoctorLicense() {
		return doctorLicense;
	}
	public void setDoctorLicense(Integer doctorLicense) {
		this.doctorLicense = doctorLicense;
	}
	public String getDoctorName() {
		return doctorName;
	}
	public void setDoctorName(String doctorName) {
		this.doctorName = doctorName;
	}
	public String getDoctorCode() {
		return doctorCode;
	}
	public void setDoctorCode(String doctorCode) {
		this.doctorCode = doctorCode;
	}
	public Integer getDeptId() {
		return deptId;
	}
	public void setDeptId(Integer deptId) {
		this.deptId = deptId;
	}
	public String getDeptCode() {
		return deptCode;
	}
	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
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
}
