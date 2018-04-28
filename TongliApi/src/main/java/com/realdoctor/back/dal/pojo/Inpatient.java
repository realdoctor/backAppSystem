package com.realdoctor.back.dal.pojo;

import java.util.Date;
import com.easyway.business.framework.pojo.BasePojo;
import java.math.BigInteger;

public class Inpatient extends BasePojo {
	private static final long serialVersionUID = 1L;
	private BigInteger id;
	private String orgCode;
	private String inpatFormNo;
	private String patientId;
	private String visitOrgName;
	private String visitDeptName;
	private Date visitDtime;
	private String respDoctorName;
	private Date lastUpdateDtime;
	public BigInteger getId() {
		return id;
	}
	public void setId(BigInteger id) {
		this.id = id;
	}
	public String getOrgCode() {
		return orgCode;
	}
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	public String getInpatFormNo() {
		return inpatFormNo;
	}
	public void setInpatFormNo(String inpatFormNo) {
		this.inpatFormNo = inpatFormNo;
	}
	public String getPatientId() {
		return patientId;
	}
	public void setPatientId(String patientId) {
		this.patientId = patientId;
	}
	public String getVisitOrgName() {
		return visitOrgName;
	}
	public void setVisitOrgName(String visitOrgName) {
		this.visitOrgName = visitOrgName;
	}
	public String getVisitDeptName() {
		return visitDeptName;
	}
	public void setVisitDeptName(String visitDeptName) {
		this.visitDeptName = visitDeptName;
	}
	public Date getVisitDtime() {
		return visitDtime;
	}
	public void setVisitDtime(Date visitDtime) {
		this.visitDtime = visitDtime;
	}
	public String getRespDoctorName() {
		return respDoctorName;
	}
	public void setRespDoctorName(String respDoctorName) {
		this.respDoctorName = respDoctorName;
	}
	public Date getLastUpdateDtime() {
		return lastUpdateDtime;
	}
	public void setLastUpdateDtime(Date lastUpdateDtime) {
		this.lastUpdateDtime = lastUpdateDtime;
	}
}
