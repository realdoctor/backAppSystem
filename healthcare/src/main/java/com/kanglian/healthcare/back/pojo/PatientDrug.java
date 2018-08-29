package com.kanglian.healthcare.back.pojo;

import java.util.Date;
import com.alibaba.fastjson.annotation.JSONField;
import com.easyway.business.framework.pojo.BasePojo;

public class PatientDrug extends BasePojo {
	private static final long serialVersionUID = 1L;
	@JSONField(serialize=false)
	private Long id;
	private Integer patientDiagId;
	private String drugName;
	private String drugCode;
    private String drugStdName;
	private String drugStdCode;
	@JSONField(serialize=false)
	private String notes;
	@JSONField(serialize=false)
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
	public String getDrugCode() {
        return drugCode;
    }
    public void setDrugCode(String drugCode) {
        this.drugCode = drugCode;
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
