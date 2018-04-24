package com.realdoctor.back.outpatient.pojo;

import java.util.Date;
import com.easyway.business.framework.pojo.BasePojo;
import java.math.BigInteger;

public class Outpatient extends BasePojo {
	private static final long serialVersionUID = 1L;
	private BigInteger id;
	private String patientId;
	private String orgCode;
	private String outpatFormNo;
	private String visitOrgName;
	private String visitOrgCode;
	private String visitDeptName;
	private Date visitDtime;
	private String referralMark;
	private String healthServiceDemand;
	private String healthProblemEval;
	private String treatmentPlan;
	private String otherMedicalTreatment;
	private String respDoctorName;
	private Integer sbp;
	private Integer dbp;
	private String deptCode;
	private String doctorCode;
	private String doctorTitleCode;
	private String regType;
	private String secTypeCode;
	private String secNo;
	private String isLocalMark;
	private String stdDeptCode;
	private String consultQuestion;
	private Date lastUpdateDtime;
	public BigInteger getId() {
		return id;
	}
	public void setId(BigInteger id) {
		this.id = id;
	}
	public String getPatientId() {
		return patientId;
	}
	public void setPatientId(String patientId) {
		this.patientId = patientId;
	}
	public String getOrgCode() {
		return orgCode;
	}
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	public String getOutpatFormNo() {
		return outpatFormNo;
	}
	public void setOutpatFormNo(String outpatFormNo) {
		this.outpatFormNo = outpatFormNo;
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
	public String getReferralMark() {
		return referralMark;
	}
	public void setReferralMark(String referralMark) {
		this.referralMark = referralMark;
	}
	public String getHealthServiceDemand() {
		return healthServiceDemand;
	}
	public void setHealthServiceDemand(String healthServiceDemand) {
		this.healthServiceDemand = healthServiceDemand;
	}
	public String getHealthProblemEval() {
		return healthProblemEval;
	}
	public void setHealthProblemEval(String healthProblemEval) {
		this.healthProblemEval = healthProblemEval;
	}
	public String getTreatmentPlan() {
		return treatmentPlan;
	}
	public void setTreatmentPlan(String treatmentPlan) {
		this.treatmentPlan = treatmentPlan;
	}
	public String getOtherMedicalTreatment() {
		return otherMedicalTreatment;
	}
	public void setOtherMedicalTreatment(String otherMedicalTreatment) {
		this.otherMedicalTreatment = otherMedicalTreatment;
	}
	public String getRespDoctorName() {
		return respDoctorName;
	}
	public void setRespDoctorName(String respDoctorName) {
		this.respDoctorName = respDoctorName;
	}
	public Integer getSbp() {
		return sbp;
	}
	public void setSbp(Integer sbp) {
		this.sbp = sbp;
	}
	public Integer getDbp() {
		return dbp;
	}
	public void setDbp(Integer dbp) {
		this.dbp = dbp;
	}
	public String getDeptCode() {
		return deptCode;
	}
	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}
	public String getDoctorCode() {
		return doctorCode;
	}
	public void setDoctorCode(String doctorCode) {
		this.doctorCode = doctorCode;
	}
	public String getDoctorTitleCode() {
		return doctorTitleCode;
	}
	public void setDoctorTitleCode(String doctorTitleCode) {
		this.doctorTitleCode = doctorTitleCode;
	}
	public String getRegType() {
		return regType;
	}
	public void setRegType(String regType) {
		this.regType = regType;
	}
	public String getSecTypeCode() {
		return secTypeCode;
	}
	public void setSecTypeCode(String secTypeCode) {
		this.secTypeCode = secTypeCode;
	}
	public String getSecNo() {
		return secNo;
	}
	public void setSecNo(String secNo) {
		this.secNo = secNo;
	}
	public String getIsLocalMark() {
		return isLocalMark;
	}
	public void setIsLocalMark(String isLocalMark) {
		this.isLocalMark = isLocalMark;
	}
	public String getStdDeptCode() {
		return stdDeptCode;
	}
	public void setStdDeptCode(String stdDeptCode) {
		this.stdDeptCode = stdDeptCode;
	}
	public String getConsultQuestion() {
		return consultQuestion;
	}
	public void setConsultQuestion(String consultQuestion) {
		this.consultQuestion = consultQuestion;
	}
	public Date getLastUpdateDtime() {
		return lastUpdateDtime;
	}
	public void setLastUpdateDtime(Date lastUpdateDtime) {
		this.lastUpdateDtime = lastUpdateDtime;
	}
}
