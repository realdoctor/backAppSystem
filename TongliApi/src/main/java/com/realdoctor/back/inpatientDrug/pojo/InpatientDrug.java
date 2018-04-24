package com.realdoctor.back.inpatientDrug.pojo;

import java.util.Date;
import com.easyway.business.framework.pojo.BasePojo;
import java.math.BigInteger;

public class InpatientDrug extends BasePojo {
	private static final long serialVersionUID = 1L;
	private BigInteger id;
	private String inpatFormNo;
	private String orgCode;
	private String cnMedicineTypeCode;
	private String drugName;
	private String drugFormCode;
	private Integer drugUsingDays;
	private String drugUsingFreq;
	private String drugDoseUnit;
	private Double drugPerDose;
	private Double drugTotalDose;
	private String drugRouteCode;
	private Date drugStopDtime;
	private String drugLocalCode;
	private String drugStdName;
	private String drugStdCode;
	private String drugTotalUnit;
	private String spec;
	private String groupNo;
	private Date drugStartDtime;
	private Date dispensingDtime;
	private String drugTypeCode;
	private Double dddValue;
	private String antibacterialFlag;
	private String notes;
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
	public String getCnMedicineTypeCode() {
		return cnMedicineTypeCode;
	}
	public void setCnMedicineTypeCode(String cnMedicineTypeCode) {
		this.cnMedicineTypeCode = cnMedicineTypeCode;
	}
	public String getDrugName() {
		return drugName;
	}
	public void setDrugName(String drugName) {
		this.drugName = drugName;
	}
	public String getDrugFormCode() {
		return drugFormCode;
	}
	public void setDrugFormCode(String drugFormCode) {
		this.drugFormCode = drugFormCode;
	}
	public Integer getDrugUsingDays() {
		return drugUsingDays;
	}
	public void setDrugUsingDays(Integer drugUsingDays) {
		this.drugUsingDays = drugUsingDays;
	}
	public String getDrugUsingFreq() {
		return drugUsingFreq;
	}
	public void setDrugUsingFreq(String drugUsingFreq) {
		this.drugUsingFreq = drugUsingFreq;
	}
	public String getDrugDoseUnit() {
		return drugDoseUnit;
	}
	public void setDrugDoseUnit(String drugDoseUnit) {
		this.drugDoseUnit = drugDoseUnit;
	}
	public Double getDrugPerDose() {
		return drugPerDose;
	}
	public void setDrugPerDose(Double drugPerDose) {
		this.drugPerDose = drugPerDose;
	}
	public Double getDrugTotalDose() {
		return drugTotalDose;
	}
	public void setDrugTotalDose(Double drugTotalDose) {
		this.drugTotalDose = drugTotalDose;
	}
	public String getDrugRouteCode() {
		return drugRouteCode;
	}
	public void setDrugRouteCode(String drugRouteCode) {
		this.drugRouteCode = drugRouteCode;
	}
	public Date getDrugStopDtime() {
		return drugStopDtime;
	}
	public void setDrugStopDtime(Date drugStopDtime) {
		this.drugStopDtime = drugStopDtime;
	}
	public String getDrugLocalCode() {
		return drugLocalCode;
	}
	public void setDrugLocalCode(String drugLocalCode) {
		this.drugLocalCode = drugLocalCode;
	}
	public String getDrugStdName() {
		return drugStdName;
	}
	public void setDrugStdName(String drugStdName) {
		this.drugStdName = drugStdName;
	}
	public String getDrugStdCode() {
		return drugStdCode;
	}
	public void setDrugStdCode(String drugStdCode) {
		this.drugStdCode = drugStdCode;
	}
	public String getDrugTotalUnit() {
		return drugTotalUnit;
	}
	public void setDrugTotalUnit(String drugTotalUnit) {
		this.drugTotalUnit = drugTotalUnit;
	}
	public String getSpec() {
		return spec;
	}
	public void setSpec(String spec) {
		this.spec = spec;
	}
	public String getGroupNo() {
		return groupNo;
	}
	public void setGroupNo(String groupNo) {
		this.groupNo = groupNo;
	}
	public Date getDrugStartDtime() {
		return drugStartDtime;
	}
	public void setDrugStartDtime(Date drugStartDtime) {
		this.drugStartDtime = drugStartDtime;
	}
	public Date getDispensingDtime() {
		return dispensingDtime;
	}
	public void setDispensingDtime(Date dispensingDtime) {
		this.dispensingDtime = dispensingDtime;
	}
	public String getDrugTypeCode() {
		return drugTypeCode;
	}
	public void setDrugTypeCode(String drugTypeCode) {
		this.drugTypeCode = drugTypeCode;
	}
	public Double getDddValue() {
		return dddValue;
	}
	public void setDddValue(Double dddValue) {
		this.dddValue = dddValue;
	}
	public String getAntibacterialFlag() {
		return antibacterialFlag;
	}
	public void setAntibacterialFlag(String antibacterialFlag) {
		this.antibacterialFlag = antibacterialFlag;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	public Date getLastUpdateDtime() {
		return lastUpdateDtime;
	}
	public void setLastUpdateDtime(Date lastUpdateDtime) {
		this.lastUpdateDtime = lastUpdateDtime;
	}
}
