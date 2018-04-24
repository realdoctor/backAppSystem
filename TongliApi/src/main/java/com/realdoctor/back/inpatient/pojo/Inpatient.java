package com.realdoctor.back.inpatient.pojo;

import java.util.Date;
import com.easyway.business.framework.pojo.BasePojo;
import java.math.BigInteger;

public class Inpatient extends BasePojo {
	private static final long serialVersionUID = 1L;
	private BigInteger id;
	private String patientId;
	private String orgCode;
	private String inpatFormNo;
	private String caseNo;
	private String inHospitalTimes;
	private String inDeptName;
	private Date inDtime;
	private String inReasonCode;
	private String inpatInfectivityMark;
	private String inpatIllStateCode;
	private String otherMedicalTreatment;
	private String referralMark;
	private Date dischargeDate;
	private String deathReasonCode;
	private Date deathDtime;
	private String secTypeCode;
	private String secNo;
	private String isLocalMark;
	private String inDeptCode;
	private String inBed;
	private String outDeptName;
	private String outDeptCode;
	private String outBed;
	private String deathMark;
	private String stdOutDeptCode;
	private String stdInDeptCode;
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
	public String getInpatFormNo() {
		return inpatFormNo;
	}
	public void setInpatFormNo(String inpatFormNo) {
		this.inpatFormNo = inpatFormNo;
	}
	public String getCaseNo() {
		return caseNo;
	}
	public void setCaseNo(String caseNo) {
		this.caseNo = caseNo;
	}
	public String getInHospitalTimes() {
		return inHospitalTimes;
	}
	public void setInHospitalTimes(String inHospitalTimes) {
		this.inHospitalTimes = inHospitalTimes;
	}
	public String getInDeptName() {
		return inDeptName;
	}
	public void setInDeptName(String inDeptName) {
		this.inDeptName = inDeptName;
	}
	public Date getInDtime() {
		return inDtime;
	}
	public void setInDtime(Date inDtime) {
		this.inDtime = inDtime;
	}
	public String getInReasonCode() {
		return inReasonCode;
	}
	public void setInReasonCode(String inReasonCode) {
		this.inReasonCode = inReasonCode;
	}
	public String getInpatInfectivityMark() {
		return inpatInfectivityMark;
	}
	public void setInpatInfectivityMark(String inpatInfectivityMark) {
		this.inpatInfectivityMark = inpatInfectivityMark;
	}
	public String getInpatIllStateCode() {
		return inpatIllStateCode;
	}
	public void setInpatIllStateCode(String inpatIllStateCode) {
		this.inpatIllStateCode = inpatIllStateCode;
	}
	public String getOtherMedicalTreatment() {
		return otherMedicalTreatment;
	}
	public void setOtherMedicalTreatment(String otherMedicalTreatment) {
		this.otherMedicalTreatment = otherMedicalTreatment;
	}
	public String getReferralMark() {
		return referralMark;
	}
	public void setReferralMark(String referralMark) {
		this.referralMark = referralMark;
	}
	public Date getDischargeDate() {
		return dischargeDate;
	}
	public void setDischargeDate(Date dischargeDate) {
		this.dischargeDate = dischargeDate;
	}
	public String getDeathReasonCode() {
		return deathReasonCode;
	}
	public void setDeathReasonCode(String deathReasonCode) {
		this.deathReasonCode = deathReasonCode;
	}
	public Date getDeathDtime() {
		return deathDtime;
	}
	public void setDeathDtime(Date deathDtime) {
		this.deathDtime = deathDtime;
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
	public String getInDeptCode() {
		return inDeptCode;
	}
	public void setInDeptCode(String inDeptCode) {
		this.inDeptCode = inDeptCode;
	}
	public String getInBed() {
		return inBed;
	}
	public void setInBed(String inBed) {
		this.inBed = inBed;
	}
	public String getOutDeptName() {
		return outDeptName;
	}
	public void setOutDeptName(String outDeptName) {
		this.outDeptName = outDeptName;
	}
	public String getOutDeptCode() {
		return outDeptCode;
	}
	public void setOutDeptCode(String outDeptCode) {
		this.outDeptCode = outDeptCode;
	}
	public String getOutBed() {
		return outBed;
	}
	public void setOutBed(String outBed) {
		this.outBed = outBed;
	}
	public String getDeathMark() {
		return deathMark;
	}
	public void setDeathMark(String deathMark) {
		this.deathMark = deathMark;
	}
	public String getStdOutDeptCode() {
		return stdOutDeptCode;
	}
	public void setStdOutDeptCode(String stdOutDeptCode) {
		this.stdOutDeptCode = stdOutDeptCode;
	}
	public String getStdInDeptCode() {
		return stdInDeptCode;
	}
	public void setStdInDeptCode(String stdInDeptCode) {
		this.stdInDeptCode = stdInDeptCode;
	}
	public Date getLastUpdateDtime() {
		return lastUpdateDtime;
	}
	public void setLastUpdateDtime(Date lastUpdateDtime) {
		this.lastUpdateDtime = lastUpdateDtime;
	}
}
