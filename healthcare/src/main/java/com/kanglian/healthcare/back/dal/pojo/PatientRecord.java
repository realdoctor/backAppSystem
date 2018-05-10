package com.kanglian.healthcare.back.dal.pojo;

import java.util.Date;
import com.easyway.business.framework.pojo.BasePojo;

public class PatientRecord extends BasePojo {
	private static final long serialVersionUID = 1L;
	private Long id;
	private String orgCode;
	private String patientId;
	private String visitOrgName;
	private String visitOrgCode;
	private String visitDeptName;
	private Date visitDtime;
	private String visitWay;
	private String respDoctorName;
	private String doctorCode;
	private Date lastUpdateDtime;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getOrgCode() {
		return orgCode;
	}
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
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
	public String getVisitOrgCode() {
		return visitOrgCode;
	}
	public void setVisitOrgCode(String visitOrgCode) {
		this.visitOrgCode = visitOrgCode;
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
	public String getVisitWay() {
		return visitWay;
	}
	public void setVisitWay(String visitWay) {
		this.visitWay = visitWay;
	}
	public String getRespDoctorName() {
		return respDoctorName;
	}
	public void setRespDoctorName(String respDoctorName) {
		this.respDoctorName = respDoctorName;
	}
	public String getDoctorCode() {
		return doctorCode;
	}
	public void setDoctorCode(String doctorCode) {
		this.doctorCode = doctorCode;
	}
	public Date getLastUpdateDtime() {
		return lastUpdateDtime;
	}
	public void setLastUpdateDtime(Date lastUpdateDtime) {
		this.lastUpdateDtime = lastUpdateDtime;
	}
	
	/**
	 * 扩展字段
	 */
    private Integer patientDiagId;
    private String  diagCode;
    private String  diagName;
    
    public Integer getPatientDiagId() {
        return patientDiagId;
    }
    
    public void setPatientDiagId(Integer patientDiagId) {
        this.patientDiagId = patientDiagId;
    }
    
    public String getDiagCode() {
        return diagCode;
    }
    
    public void setDiagCode(String diagCode) {
        this.diagCode = diagCode;
    }
    
    public String getDiagName() {
        return diagName;
    }
    
    public void setDiagName(String diagName) {
        this.diagName = diagName;
    }
}
