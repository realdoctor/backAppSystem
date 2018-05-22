package com.kanglian.healthcare.back.dal.pojo;

import java.util.Date;
import com.alibaba.fastjson.annotation.JSONField;
import com.easyway.business.framework.pojo.BasePojo;

public class HospitalDept extends BasePojo {
	private static final long serialVersionUID = 1L;
	private Integer deptId;
	private String deptName;
	private String deptCode;
	private Integer hospitalId;
	private String hospitalName;
	private String hospitalCode;
	private String hospitalLevel;
	private String doctorCode;
    private String doctorName;
    @JSONField(name="goodAt")
    private String field;
    private String doctorIntro;
    private String positional;
    // 接诊量
    private Integer receiveNum;
    // 星期几
    private String week;
    // 日常计划
    private String plan;
    @JSONField(name="worktime")
    private Date lastUpdateDtime;
    private String worktimeWeek;
    public Integer getDeptId() {
		return deptId;
	}
	public void setDeptId(Integer deptId) {
		this.deptId = deptId;
	}
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	public String getDeptCode() {
		return deptCode;
	}
	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}
	public Integer getHospitalId() {
		return hospitalId;
	}
	public void setHospitalId(Integer hospitalId) {
		this.hospitalId = hospitalId;
	}
	public String getHospitalName() {
		return hospitalName;
	}
	public void setHospitalName(String hospitalName) {
		this.hospitalName = hospitalName;
	}
	public String getHospitalCode() {
		return hospitalCode;
	}
	public void setHospitalCode(String hospitalCode) {
		this.hospitalCode = hospitalCode;
	}
	public String getHospitalLevel() {
		return hospitalLevel;
	}
	public void setHospitalLevel(String hospitalLevel) {
		this.hospitalLevel = hospitalLevel;
	}
	public String getDoctorName() {
        return doctorName;
    }
    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }
    public String getDoctorCode() {
        return doctorCode;
    }
    public void setDoctorCode(String doctorCode) {
        this.doctorCode = doctorCode;
    }
    public String getField() {
        return field;
    }
    public void setField(String field) {
        this.field = field;
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
    public Integer getReceiveNum() {
        return receiveNum;
    }
    public void setReceiveNum(Integer receiveNum) {
        this.receiveNum = receiveNum;
    }
    public String getWeek() {
        return week;
    }
    public void setWeek(String week) {
        this.week = week;
    }
    public String getPlan() {
        return plan;
    }
    public void setPlan(String plan) {
        this.plan = plan;
    }
    public Date getLastUpdateDtime() {
        return lastUpdateDtime;
    }
    public void setLastUpdateDtime(Date lastUpdateDtime) {
        this.lastUpdateDtime = lastUpdateDtime;
    }
    public String getWorktimeWeek() {
        return worktimeWeek;
    }
    public void setWorktimeWeek(String worktimeWeek) {
        this.worktimeWeek = worktimeWeek;
    }
}
