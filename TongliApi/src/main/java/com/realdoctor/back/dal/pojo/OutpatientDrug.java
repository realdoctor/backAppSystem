package com.realdoctor.back.dal.pojo;

import java.util.Date;
import com.easyway.business.framework.pojo.BasePojo;
import java.math.BigInteger;

public class OutpatientDrug extends BasePojo {
	private static final long serialVersionUID = 1L;
	private BigInteger id;
	private String orgCode;
	private String outpatFormNo;
	private String drugName;
	private String drugStdName;
	private String drugStdCode;
	private String notes;
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
	public String getOutpatFormNo() {
		return outpatFormNo;
	}
	public void setOutpatFormNo(String outpatFormNo) {
		this.outpatFormNo = outpatFormNo;
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
