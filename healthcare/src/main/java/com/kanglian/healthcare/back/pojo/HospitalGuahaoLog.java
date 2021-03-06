package com.kanglian.healthcare.back.pojo;

import java.util.Date;
import com.easyway.business.framework.pojo.BasePojo;

public class HospitalGuahaoLog extends BasePojo {
	private static final long serialVersionUID = 1L;
	private Long id;
	private Long userId;
	private String hospitalId;
	private String deptId;
	private String doctorCode;
	private String orderDay;
	private String hospitalDoctorDutyId;
	private Date addTime;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getHospitalId() {
		return hospitalId;
	}
	public void setHospitalId(String hospitalId) {
		this.hospitalId = hospitalId;
	}
	public String getDeptId() {
		return deptId;
	}
	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}
	public String getDoctorCode() {
		return doctorCode;
	}
	public void setDoctorCode(String doctorCode) {
		this.doctorCode = doctorCode;
	}
	public String getOrderDay() {
		return orderDay;
	}
	public void setOrderDay(String orderDay) {
		this.orderDay = orderDay;
	}
	public String getHospitalDoctorDutyId() {
		return hospitalDoctorDutyId;
	}
	public void setHospitalDoctorDutyId(String hospitalDoctorDutyId) {
		this.hospitalDoctorDutyId = hospitalDoctorDutyId;
	}
	public Date getAddTime() {
		return addTime;
	}
	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}
}
