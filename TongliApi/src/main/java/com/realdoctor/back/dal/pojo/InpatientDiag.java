package com.realdoctor.back.dal.pojo;

import java.util.Date;
import com.easyway.business.framework.pojo.BasePojo;
import java.math.BigInteger;

public class InpatientDiag extends BasePojo {
	private static final long serialVersionUID = 1L;
	private BigInteger id;
	private String orgCode;
	private String inpatFormNo;
	private String inDiagCode;
	private String inDiagName;
	private Date diagDate;
	private String diagTypeCode;
	private Date lastUpdateDtime;
	public BigInteger getId() {
		return id;
	}
	public void setId(BigInteger id) {
		this.id = id;
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
	public String getInDiagCode() {
		return inDiagCode;
	}
	public void setInDiagCode(String inDiagCode) {
		this.inDiagCode = inDiagCode;
	}
	public String getInDiagName() {
		return inDiagName;
	}
	public void setInDiagName(String inDiagName) {
		this.inDiagName = inDiagName;
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
