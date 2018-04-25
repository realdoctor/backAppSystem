package com.realdoctor.back.dal.pojo;

import java.util.Date;
import com.easyway.business.framework.pojo.BasePojo;
import java.math.BigInteger;

public class OutpatientDiag extends BasePojo {
	private static final long serialVersionUID = 1L;
	private BigInteger id;
	private String diagnosisId;
	private String outpatFormNo;
	private String orgCode;
	private String outpatDiagName;
	private String outpatDiagCode;
	private Date diagDate;
	private String diagTypeCode;
	private Date lastUpdateDtime;
	public BigInteger getId() {
		return id;
	}
	public void setId(BigInteger id) {
		this.id = id;
	}
	public String getDiagnosisId() {
		return diagnosisId;
	}
	public void setDiagnosisId(String diagnosisId) {
		this.diagnosisId = diagnosisId;
	}
	public String getOutpatFormNo() {
		return outpatFormNo;
	}
	public void setOutpatFormNo(String outpatFormNo) {
		this.outpatFormNo = outpatFormNo;
	}
	public String getOrgCode() {
		return orgCode;
	}
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	public String getOutpatDiagName() {
		return outpatDiagName;
	}
	public void setOutpatDiagName(String outpatDiagName) {
		this.outpatDiagName = outpatDiagName;
	}
	public String getOutpatDiagCode() {
		return outpatDiagCode;
	}
	public void setOutpatDiagCode(String outpatDiagCode) {
		this.outpatDiagCode = outpatDiagCode;
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
