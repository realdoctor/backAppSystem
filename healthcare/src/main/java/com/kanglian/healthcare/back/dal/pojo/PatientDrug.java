package com.kanglian.healthcare.back.dal.pojo;

import java.util.Date;
import com.easyway.business.framework.pojo.BasePojo;

public class PatientDrug extends BasePojo {
	private static final long serialVersionUID = 1L;
	private Long id;
	private Integer patientDiagId;
	private String drugName;
	private String drugStdName;
	private String drugStdCode;
	private String notes;
	private Date lastUpdateDtime;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Integer getPatientDiagId() {
		return patientDiagId;
	}
	public void setPatientDiagId(Integer patientDiagId) {
		this.patientDiagId = patientDiagId;
	}
	public String getDrugName() {
		return drugName;
	}
	public void setDrugName(String drugName) {
		this.drugName = drugName;
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
