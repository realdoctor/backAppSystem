package com.realdoctor.back.dal.pojo;

import java.util.Date;

import com.easyway.business.framework.pojo.BasePojo;

/**
 * 病历卡
 * 
 * @author xl.liu
 */
public class MedicalRecord extends BasePojo {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    // 机构编码
    private String            orgCode;
    // 病人ID
    private String            patientId;
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
}
