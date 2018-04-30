package com.realdoctor.back.dal.pojo;

import com.easyway.business.framework.pojo.BasePojo;

public class PatientDrug extends BasePojo {

    private static final long serialVersionUID = 1L;
    private String            orgCode;
    private String            patientId;
    private String            drugName;
    private String            drugStdName;
    private String            drugStdCode;

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
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

    public static long getSerialversionuid() {
        return serialVersionUID;
    }
}
