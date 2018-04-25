package com.realdoctor.back.dal.pojo;

import java.util.Date;
import com.easyway.business.framework.pojo.BasePojo;
import java.math.BigInteger;

public class InpatientInrecord extends BasePojo {
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
	private String representorRelationship;
	private String representorName;
	private Date representDtime;
	private String authorId;
	private String authorName;
	private Date authorDtime;
	private String authenticatorId;
	private String authenticatorName;
	private Date authenticatorDtime;
	private String inChargeDoctorId;
	private String inChargeDoctorName;
	private Date inChargeDoctorDtime;
	private String inrecordTypeCode;
	private Date admissionDtime;
	private Date deathDtime;
	private String inDeptCode;
	private String inDeptName;
	private String bedNo;
	private String chiefComplaints;
	private String currentDisease;
	private String diseaseHistory;
	private String personalHistory;
	private String marriageHistory;
	private String mensesHistory;
	private String familyHistory;
	private String specSituation;
	private String assistExamResult;
	private String firstDiagnosis;
	private String firstDiagnosisIcd;
	private String inpatDiagName;
	private String inDiagnosisIcd;
	private String inConditionDescr;
	private String treatRescueCourse;
	private String outCondition;
	private String outDiagnosisName;
	private String outDiagnosisCode;
	private String outOrder;
	private String treatmentResult;
	private String deathCause;
	private String deathDiagnosis;
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
	public String getRepresentorRelationship() {
		return representorRelationship;
	}
	public void setRepresentorRelationship(String representorRelationship) {
		this.representorRelationship = representorRelationship;
	}
	public String getRepresentorName() {
		return representorName;
	}
	public void setRepresentorName(String representorName) {
		this.representorName = representorName;
	}
	public Date getRepresentDtime() {
		return representDtime;
	}
	public void setRepresentDtime(Date representDtime) {
		this.representDtime = representDtime;
	}
	public String getAuthorId() {
		return authorId;
	}
	public void setAuthorId(String authorId) {
		this.authorId = authorId;
	}
	public String getAuthorName() {
		return authorName;
	}
	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}
	public Date getAuthorDtime() {
		return authorDtime;
	}
	public void setAuthorDtime(Date authorDtime) {
		this.authorDtime = authorDtime;
	}
	public String getAuthenticatorId() {
		return authenticatorId;
	}
	public void setAuthenticatorId(String authenticatorId) {
		this.authenticatorId = authenticatorId;
	}
	public String getAuthenticatorName() {
		return authenticatorName;
	}
	public void setAuthenticatorName(String authenticatorName) {
		this.authenticatorName = authenticatorName;
	}
	public Date getAuthenticatorDtime() {
		return authenticatorDtime;
	}
	public void setAuthenticatorDtime(Date authenticatorDtime) {
		this.authenticatorDtime = authenticatorDtime;
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
	public String getInrecordTypeCode() {
		return inrecordTypeCode;
	}
	public void setInrecordTypeCode(String inrecordTypeCode) {
		this.inrecordTypeCode = inrecordTypeCode;
	}
	public Date getAdmissionDtime() {
		return admissionDtime;
	}
	public void setAdmissionDtime(Date admissionDtime) {
		this.admissionDtime = admissionDtime;
	}
	public Date getDeathDtime() {
		return deathDtime;
	}
	public void setDeathDtime(Date deathDtime) {
		this.deathDtime = deathDtime;
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
	public String getChiefComplaints() {
		return chiefComplaints;
	}
	public void setChiefComplaints(String chiefComplaints) {
		this.chiefComplaints = chiefComplaints;
	}
	public String getCurrentDisease() {
		return currentDisease;
	}
	public void setCurrentDisease(String currentDisease) {
		this.currentDisease = currentDisease;
	}
	public String getDiseaseHistory() {
		return diseaseHistory;
	}
	public void setDiseaseHistory(String diseaseHistory) {
		this.diseaseHistory = diseaseHistory;
	}
	public String getPersonalHistory() {
		return personalHistory;
	}
	public void setPersonalHistory(String personalHistory) {
		this.personalHistory = personalHistory;
	}
	public String getMarriageHistory() {
		return marriageHistory;
	}
	public void setMarriageHistory(String marriageHistory) {
		this.marriageHistory = marriageHistory;
	}
	public String getMensesHistory() {
		return mensesHistory;
	}
	public void setMensesHistory(String mensesHistory) {
		this.mensesHistory = mensesHistory;
	}
	public String getFamilyHistory() {
		return familyHistory;
	}
	public void setFamilyHistory(String familyHistory) {
		this.familyHistory = familyHistory;
	}
	public String getSpecSituation() {
		return specSituation;
	}
	public void setSpecSituation(String specSituation) {
		this.specSituation = specSituation;
	}
	public String getAssistExamResult() {
		return assistExamResult;
	}
	public void setAssistExamResult(String assistExamResult) {
		this.assistExamResult = assistExamResult;
	}
	public String getFirstDiagnosis() {
		return firstDiagnosis;
	}
	public void setFirstDiagnosis(String firstDiagnosis) {
		this.firstDiagnosis = firstDiagnosis;
	}
	public String getFirstDiagnosisIcd() {
		return firstDiagnosisIcd;
	}
	public void setFirstDiagnosisIcd(String firstDiagnosisIcd) {
		this.firstDiagnosisIcd = firstDiagnosisIcd;
	}
	public String getInpatDiagName() {
		return inpatDiagName;
	}
	public void setInpatDiagName(String inpatDiagName) {
		this.inpatDiagName = inpatDiagName;
	}
	public String getInDiagnosisIcd() {
		return inDiagnosisIcd;
	}
	public void setInDiagnosisIcd(String inDiagnosisIcd) {
		this.inDiagnosisIcd = inDiagnosisIcd;
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
	public String getOutDiagnosisName() {
		return outDiagnosisName;
	}
	public void setOutDiagnosisName(String outDiagnosisName) {
		this.outDiagnosisName = outDiagnosisName;
	}
	public String getOutDiagnosisCode() {
		return outDiagnosisCode;
	}
	public void setOutDiagnosisCode(String outDiagnosisCode) {
		this.outDiagnosisCode = outDiagnosisCode;
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
	public String getDeathCause() {
		return deathCause;
	}
	public void setDeathCause(String deathCause) {
		this.deathCause = deathCause;
	}
	public String getDeathDiagnosis() {
		return deathDiagnosis;
	}
	public void setDeathDiagnosis(String deathDiagnosis) {
		this.deathDiagnosis = deathDiagnosis;
	}
	public Date getLastUpdateDtime() {
		return lastUpdateDtime;
	}
	public void setLastUpdateDtime(Date lastUpdateDtime) {
		this.lastUpdateDtime = lastUpdateDtime;
	}
}
