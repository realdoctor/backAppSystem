package com.kanglian.healthcare.back.dal.pojo;

import com.alibaba.fastjson.annotation.JSONField;
import com.easyway.business.framework.pojo.BasePojo;

public class HospitalDoctor extends BasePojo {
	private static final long serialVersionUID = 1L;
	private Integer doctorId;
	private Integer deptId;
	private Integer hospitalId;
	private String doctorLicense;
	private String doctorName;
	private String doctorCode;
	@JSONField(name="goodAt")
	private String field;
	private String doctorIntro;
	private String positional;
	public Integer getDoctorId() {
		return doctorId;
	}
	public void setDoctorId(Integer doctorId) {
		this.doctorId = doctorId;
	}
	public Integer getDeptId() {
		return deptId;
	}
	public void setDeptId(Integer deptId) {
		this.deptId = deptId;
	}
	public Integer getHospitalId() {
		return hospitalId;
	}
	public void setHospitalId(Integer hospitalId) {
		this.hospitalId = hospitalId;
	}
	public String getDoctorLicense() {
		return doctorLicense;
	}
	public void setDoctorLicense(String doctorLicense) {
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
