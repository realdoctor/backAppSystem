package com.realdoctor.back.dal.pojo;

import java.util.Date;
import com.easyway.business.framework.pojo.BasePojo;
import java.math.BigInteger;

public class PatientDiag extends BasePojo {
	private static final long serialVersionUID = 1L;
	private BigInteger id;
	private Integer patientRecordId;
	private String diagCode;
	private String diagName;
	private Date diagDate;
	private String diagTypeCode;
	private Date lastUpdateDtime;
	public BigInteger getId() {
		return id;
	}
	public void setId(BigInteger id) {
		this.id = id;
	}
	public Integer getPatientRecordId() {
		return patientRecordId;
	}
	public void setPatientRecordId(Integer patientRecordId) {
		this.patientRecordId = patientRecordId;
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
	public Date getDiagDate() {
		return diagDate;
	}
	public void setDiagDate(Date diagDate) {
		this.diagDate = diagDate;
	}
	public String getDiagTypeCode() {
		return diagTypeCode;
	}
	public void setDiagTypeCode(String diagTypeCode) {
		this.diagTypeCode = diagTypeCode;
	}
	public Date getLastUpdateDtime() {
		return lastUpdateDtime;
	}
	public void setLastUpdateDtime(Date lastUpdateDtime) {
		this.lastUpdateDtime = lastUpdateDtime;
	}
}
