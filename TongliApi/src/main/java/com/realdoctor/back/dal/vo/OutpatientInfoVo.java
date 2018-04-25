package com.realdoctor.back.dal.vo;

import java.util.Date;
import com.easyway.business.framework.pojo.BasePojo;

/**
 * 门诊病人信息
 * 
 * @author xl.liu
 */
public class OutpatientInfoVo extends BasePojo {

    private static final long serialVersionUID = 1L;
    // 病人ID
    private String            patientId;
    // 就诊日期时间
    private Date              visitDtime;
    // 就诊机构名称
    private String            visitOrgName;
    // 就诊科室名称
    private String            visitDeptName;
    // 门诊诊断名称
    private String            outpatDiagName;
    // 责任医师姓名
    private String            respDoctorName;
    // 药物名称
    private String            drugName;
    // 用药天数
    private Integer           drugUsingDays;
    // 药物使用频率
    private String            drugUsingFreq;
    // 药物使用剂量单位
    private String            drugDoseUnit;
    // 药物使用次剂量
    private Double            drugPerDose;
    // 药物使用-总剂量单位
    private String            drugTotalUnit;
    // 规格
    private String            spec;
    // 用药停止日期时间
    private Date              drugStopDtime;
    // 开始时间
    private Date              drugStartDtime;
    // 发药时间
    private Date              dispensingDtime;
    // DDD值/最小剂量
    private Double            dddValue;
    // 抗菌药物标志(0.否/FALSE,1.是/TRUE)
    private String            antibacterialFlag;
    // 备注
    private String            notes;
    
    public String getPatientId() {
        return patientId;
    }
    
    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }
    
    public Date getVisitDtime() {
        return visitDtime;
    }
    
    public void setVisitDtime(Date visitDtime) {
        this.visitDtime = visitDtime;
    }
    
    public String getVisitOrgName() {
        return visitOrgName;
    }
    
    public void setVisitOrgName(String visitOrgName) {
        this.visitOrgName = visitOrgName;
    }
    
    public String getVisitDeptName() {
        return visitDeptName;
    }
    
    public void setVisitDeptName(String visitDeptName) {
        this.visitDeptName = visitDeptName;
    }
    
    public String getOutpatDiagName() {
        return outpatDiagName;
    }
    
    public void setOutpatDiagName(String outpatDiagName) {
        this.outpatDiagName = outpatDiagName;
    }
    
    public String getRespDoctorName() {
        return respDoctorName;
    }
    
    public void setRespDoctorName(String respDoctorName) {
        this.respDoctorName = respDoctorName;
    }
    
    public String getDrugName() {
        return drugName;
    }
    
    public void setDrugName(String drugName) {
        this.drugName = drugName;
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
    
    public Date getDrugStopDtime() {
        return drugStopDtime;
    }
    
    public void setDrugStopDtime(Date drugStopDtime) {
        this.drugStopDtime = drugStopDtime;
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

}
