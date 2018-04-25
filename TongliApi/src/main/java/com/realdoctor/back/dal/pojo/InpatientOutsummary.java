package com.realdoctor.back.dal.pojo;

import java.util.Date;
import com.easyway.business.framework.pojo.BasePojo;
import java.math.BigInteger;

public class InpatientOutsummary extends BasePojo {
	private static final long serialVersionUID = 1L;
	private BigInteger id;
	private String inpatFormNo;
	private String orgCode;
	private String sexCode;
	private Date birthDate;
	private Integer age;
	private String address;
	private String employerName;
	private String birthAddress;
	private String nationalityCode;
	private String occupationCode;
	private String marriageCode;
	private String residentDoctorId;
	private String residentDoctorName;
	private Date residentDoctorDtime;
	private String inChargeDoctorId;
	private String inChargeDoctorName;
	private Date inChargeDoctorDtime;
	private String outsummaryTypeCode;
	private Date admissionDtime;
	private Date dischargeDtime;
	private String inDeptCode;
	private String inDeptName;
	private String bedNo;
	private Integer inHospitalDays;
	private String inDiagnosisIcd;
	private String inpatDiagName;
	private String inConditionDescr;
	private String treatRescueCourse;
	private String outCondition;
	private String outDiagnosisCode;
	private String outDiagnosisName;
	private String outOrder;
	private String treatmentResult;
	private Date lastUpdateDtime;
	public BigInteger getId() {
		return id;
	}
	public void setId(BigInteger id) {
		this.id = id;
	}
	public String getInpatFormNo() {
		return inpatFormNo;
	}
	public void setInpatFormNo(String inpatFormNo) {
		this.inpatFormNo = inpatFormNo;
	}
	public String getOrgCode() {
		return orgCode;
	}
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	public String getSexCode() {
		return sexCode;
	}
	public void setSexCode(String sexCode) {
		this.sexCode = sexCode;
	}
	public Date getBirthDate() {
		return birthDate;
	}
	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getEmployerName() {
		return employerName;
	}
	public void setEmployerName(String employerName) {
		this.employerName = employerName;
	}
	public String getBirthAddress() {
		return birthAddress;
	}
	public void setBirthAddress(String birthAddress) {
		this.birthAddress = birthAddress;
	}
	public String getNationalityCode() {
		return nationalityCode;
	}
	public void setNationalityCode(String nationalityCode) {
		this.nationalityCode = nationalityCode;
	}
	public String getOccupationCode() {
		return occupationCode;
	}
	public void setOccupationCode(String occupationCode) {
		this.occupationCode = occupationCode;
	}
	public String getMarriageCode() {
		return marriageCode;
	}
	public void setMarriageCode(String marriageCode) {
		this.marriageCode = marriageCode;
	}
	public String getResidentDoctorId() {
		return residentDoctorId;
	}
	public void setResidentDoctorId(String residentDoctorId) {
		this.residentDoctorId = residentDoctorId;
	}
	public String getResidentDoctorName() {
		return residentDoctorName;
	}
	public void setResidentDoctorName(String residentDoctorName) {
		this.residentDoctorName = residentDoctorName;
	}
	public Date getResidentDoctorDtime() {
		return residentDoctorDtime;
	}
	public void setResidentDoctorDtime(Date residentDoctorDtime) {
		this.residentDoctorDtime = residentDoctorDtime;
	}
	public String getInChargeDoctorId() {
		return inChargeDoctorId;
	}
	public void setInChargeDoctorId(String inChargeDoctorId) {
		this.inChargeDoctorId = inChargeDoctorId;
	}
	public String getInChargeDoctorName() {
		return inChargeDoctorName;
	}
	public void setInChargeDoctorName(String inChargeDoctorName) {
		this.inChargeDoctorName = inChargeDoctorName;
	}
	public Date getInChargeDoctorDtime() {
		return inChargeDoctorDtime;
	}
	public void setInChargeDoctorDtime(Date inChargeDoctorDtime) {
		this.inChargeDoctorDtime = inChargeDoctorDtime;
	}
	public String getOutsummaryTypeCode() {
		return outsummaryTypeCode;
	}
	public void setOutsummaryTypeCode(String outsummaryTypeCode) {
		this.outsummaryTypeCode = outsummaryTypeCode;
	}
	public Date getAdmissionDtime() {
		return admissionDtime;
	}
	public void setAdmissionDtime(Date admissionDtime) {
		this.admissionDtime = admissionDtime;
	}
	public Date getDischargeDtime() {
		return dischargeDtime;
	}
	public void setDischargeDtime(Date dischargeDtime) {
		this.dischargeDtime = dischargeDtime;
	}
	public String getInDeptCode() {
		return inDeptCode;
	}
	public void setInDeptCode(String inDeptCode) {
		this.inDeptCode = inDeptCode;
	}
	public String getInDeptName() {
		return inDeptName;
	}
	public void setInDeptName(String inDeptName) {
		this.inDeptName = inDeptName;
	}
	public String getBedNo() {
		return bedNo;
	}
	public void setBedNo(String bedNo) {
		this.bedNo = bedNo;
	}
	public Integer getInHospitalDays() {
		return inHospitalDays;
	}
	public void setInHospitalDays(Integer inHospitalDays) {
		this.inHospitalDays = inHospitalDays;
	}
	public String getInDiagnosisIcd() {
		return inDiagnosisIcd;
	}
	public void setInDiagnosisIcd(String inDiagnosisIcd) {
		this.inDiagnosisIcd = inDiagnosisIcd;
	}
	public String getInpatDiagName() {
		return inpatDiagName;
	}
	public void setInpatDiagName(String inpatDiagName) {
		this.inpatDiagName = inpatDiagName;
	}
	public String getInConditionDescr() {
		return inConditionDescr;
	}
	public void setInConditionDescr(String inConditionDescr) {
		this.inConditionDescr = inConditionDescr;
	}
	public String getTreatRescueCourse() {
		return treatRescueCourse;
	}
	public void setTreatRescueCourse(String treatRescueCourse) {
		this.treatRescueCourse = treatRescueCourse;
	}
	public String getOutCondition() {
		return outCondition;
	}
	public void setOutCondition(String outCondition) {
		this.outCondition = outCondition;
	}
	public String getOutDiagnosisCode() {
		return outDiagnosisCode;
	}
	public void setOutDiagnosisCode(String outDiagnosisCode) {
		this.outDiagnosisCode = outDiagnosisCode;
	}
	public String getOutDiagnosisName() {
		return outDiagnosisName;
	}
	public void setOutDiagnosisName(String outDiagnosisName) {
		this.outDiagnosisName = outDiagnosisName;
	}
	public String getOutOrder() {
		return outOrder;
	}
	public void setOutOrder(String outOrder) {
		this.outOrder = outOrder;
	}
	public String getTreatmentResult() {
		return treatmentResult;
	}
	public void setTreatmentResult(String treatmentResult) {
		this.treatmentResult = treatmentResult;
	}
	public Date getLastUpdateDtime() {
		return lastUpdateDtime;
	}
	public void setLastUpdateDtime(Date lastUpdateDtime) {
		this.lastUpdateDtime = lastUpdateDtime;
	}
}
