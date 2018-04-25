package com.realdoctor.back.dal.vo;

import java.util.Date;
import com.easyway.business.framework.pojo.BasePojo;

/**
 * 门诊病人信息
 * 
 * @author xl.liu
 */
public class OutpatientInfoVo extends BasePojo {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    // 病人ID
    private String            patientId;
    // 姓名
    private String            name;
    // 性别
    private String            sexCode;
    // 出生日期
    private Date              birthDate;
    // 身份证类型
    private String            idTypeCode;
    // 证件号码
    private String            idNo;
    // 电话号码
    private String            telNo;
    // 国籍
    private String            nationalityCode;
    // 血型
    private String            aboCode;
    // RH血型
    private String            rhCode;
    // 婚姻状况
    private String            marriageCode;
    // 过敏标识
    private String            drugAllergyMark;
    // 就诊日期时间
    private Date              visitDtime;
    // 就诊机构名称
    private String            visitOrgName;
    // 就诊科室名称
    private String            visitDeptName;
    // 门诊诊断代码
    private String            outpatDiagCode;
    // 门诊诊断名称
    private String            outpatDiagName;
    // 责任医师姓名
    private String            respDoctorName;
    // 药物名称
    private String            drugName;
    // 药物剂型代码
    private String            drugFormCode;
    // 药物标准编码
    private String            drugStdCode;
    // 药物标准名称
    private String            drugStdName;
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
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
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
    
    public String getIdTypeCode() {
        return idTypeCode;
    }
    
    public void setIdTypeCode(String idTypeCode) {
        this.idTypeCode = idTypeCode;
    }
    
    public String getIdNo() {
        return idNo;
    }
    
    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }
    
    public String getTelNo() {
        return telNo;
    }
    
    public void setTelNo(String telNo) {
        this.telNo = telNo;
    }
    
    public String getNationalityCode() {
        return nationalityCode;
    }
    
    public void setNationalityCode(String nationalityCode) {
        this.nationalityCode = nationalityCode;
    }
    
    public String getAboCode() {
        return aboCode;
    }
    
    public void setAboCode(String aboCode) {
        this.aboCode = aboCode;
    }
    
    public String getRhCode() {
        return rhCode;
    }
    
    public void setRhCode(String rhCode) {
        this.rhCode = rhCode;
    }
    
    public String getMarriageCode() {
        return marriageCode;
    }
    
    public void setMarriageCode(String marriageCode) {
        this.marriageCode = marriageCode;
    }
    
    public String getDrugAllergyMark() {
        return drugAllergyMark;
    }
    
    public void setDrugAllergyMark(String drugAllergyMark) {
        this.drugAllergyMark = drugAllergyMark;
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
    
    public String getOutpatDiagCode() {
        return outpatDiagCode;
    }
    
    public void setOutpatDiagCode(String outpatDiagCode) {
        this.outpatDiagCode = outpatDiagCode;
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
    
    public String getDrugFormCode() {
        return drugFormCode;
    }
    
    public void setDrugFormCode(String drugFormCode) {
        this.drugFormCode = drugFormCode;
    }
    
    public String getDrugStdCode() {
        return drugStdCode;
    }
    
    public void setDrugStdCode(String drugStdCode) {
        this.drugStdCode = drugStdCode;
    }
    
    public String getDrugStdName() {
        return drugStdName;
    }
    
    public void setDrugStdName(String drugStdName) {
        this.drugStdName = drugStdName;
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
