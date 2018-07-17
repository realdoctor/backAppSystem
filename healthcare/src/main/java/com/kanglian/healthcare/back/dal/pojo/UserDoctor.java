package com.kanglian.healthcare.back.dal.pojo;

import com.easyway.business.framework.pojo.BasePojo;

public class UserDoctor extends BasePojo {
    private static final long serialVersionUID = 1L;
    // 用户Id
    private Integer           userId;
    private Integer           hospitalId;
    private String            hospitalCode;
    // 医院名称
    private String            hospitalName;
    private String            deptCode;
    // 科室名称
    private String            deptName;
    // 医院等级
    private String            hospitalLevel;
    // 医生码
    private String            doctorCode;
    // 医生名字
    private String            doctorName;
    // 医生简介
    private String            doctorIntro;
    // 医生职称
    private String            positional;
    public Integer getUserId() {
        return userId;
    }
    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    public Integer getHospitalId() {
        return hospitalId;
    }
    public void setHospitalId(Integer hospitalId) {
        this.hospitalId = hospitalId;
    }
    public String getHospitalCode() {
        return hospitalCode;
    }
    public void setHospitalCode(String hospitalCode) {
        this.hospitalCode = hospitalCode;
    }
    public String getHospitalName() {
        return hospitalName;
    }
    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }
    public String getDeptCode() {
        return deptCode;
    }
    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode;
    }
    public String getDeptName() {
        return deptName;
    }
    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }
    public String getHospitalLevel() {
        return hospitalLevel;
    }
    public void setHospitalLevel(String hospitalLevel) {
        this.hospitalLevel = hospitalLevel;
    }
    public String getDoctorCode() {
        return doctorCode;
    }
    public void setDoctorCode(String doctorCode) {
        this.doctorCode = doctorCode;
    }
    public String getDoctorName() {
        return doctorName;
    }
    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }
    public String getDoctorIntro() {
        return doctorIntro;
    }
    public void setDoctorIntro(String doctorIntro) {
        this.doctorIntro = doctorIntro;
    }
    public String getPositional() {
        return positional;
    }
    public void setPositional(String positional) {
        this.positional = positional;
    }

}
