package com.kanglian.healthcare.back.pojo;

import java.util.Date;
import java.util.List;
import com.alibaba.fastjson.annotation.JSONField;
import com.easyway.business.framework.pojo.BasePojo;

public class PatientRecord extends BasePojo {
	private static final long serialVersionUID = 1L;
	@JSONField(name="patientRecordId")
	private Long id;
	private String orgCode;
	private String patientId;
	private String visitOrgName;
	private String visitOrgCode;
	private String visitDeptName;
	private String visitDeptCode;
    private Date visitDtime;
	private String visitWay;
	private String respDoctorName;
	private String doctorCode;
	private Date lastUpdateDtime;

    /** 处方列表 */
	private List<PatientDrug> drugList;
	public List<PatientDrug> getDrugList() {
        return drugList;
    }
    public void setDrugList(List<PatientDrug> drugList) {
        this.drugList = drugList;
    }
    
    public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
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
	public String getVisitOrgName() {
		return visitOrgName;
	}
	public void setVisitOrgName(String visitOrgName) {
		this.visitOrgName = visitOrgName;
	}
	public String getVisitOrgCode() {
		return visitOrgCode;
	}
	public void setVisitOrgCode(String visitOrgCode) {
		this.visitOrgCode = visitOrgCode;
	}
	public String getVisitDeptName() {
		return visitDeptName;
	}
	public void setVisitDeptName(String visitDeptName) {
		this.visitDeptName = visitDeptName;
	}
	public String getVisitDeptCode() {
        return visitDeptCode;
    }
    public void setVisitDeptCode(String visitDeptCode) {
        this.visitDeptCode = visitDeptCode;
    }
	public Date getVisitDtime() {
		return visitDtime;
	}
	public void setVisitDtime(Date visitDtime) {
		this.visitDtime = visitDtime;
	}
	public String getVisitWay() {
		return visitWay;
	}
	public void setVisitWay(String visitWay) {
		this.visitWay = visitWay;
	}
	public String getRespDoctorName() {
		return respDoctorName;
	}
	public void setRespDoctorName(String respDoctorName) {
		this.respDoctorName = respDoctorName;
	}
	public String getDoctorCode() {
		return doctorCode;
	}
	public void setDoctorCode(String doctorCode) {
		this.doctorCode = doctorCode;
	}
	public Date getLastUpdateDtime() {
		return lastUpdateDtime;
	}
	public void setLastUpdateDtime(Date lastUpdateDtime) {
		this.lastUpdateDtime = lastUpdateDtime;
	}
	
	/**
	 * 扩展字段
	 */
	private Integer patientUserId;
    private Integer doctorUserId;
    private Integer patientDiagId;
    private String mobilePhone;
    private String  diagCode;
    private String  diagName;
    private Integer hospitalId;
    private String deptCode;
    private String deptName;
    private String patientRealName;
    private String doctorRealName;
    private String imageUrl;
    
    public Integer getPatientUserId() {
        return patientUserId;
    }
    public void setPatientUserId(Integer patientUserId) {
        this.patientUserId = patientUserId;
    }
    public Integer getDoctorUserId() {
        return doctorUserId;
    }
    public void setDoctorUserId(Integer doctorUserId) {
        this.doctorUserId = doctorUserId;
    }
    public String getPatientRealName() {
        return patientRealName;
    }
    public void setPatientRealName(String patientRealName) {
        this.patientRealName = patientRealName;
    }
    public String getDoctorRealName() {
        return doctorRealName;
    }
    public void setDoctorRealName(String doctorRealName) {
        this.doctorRealName = doctorRealName;
    }
    
    public Integer getPatientDiagId() {
        return patientDiagId;
    }
    public void setPatientDiagId(Integer patientDiagId) {
        this.patientDiagId = patientDiagId;
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
    
    public String getMobilePhone() {
        return mobilePhone;
    }
    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }
    
    public String getImageUrl() {
        return imageUrl;
    }
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    
    public Integer getHospitalId() {
        return hospitalId;
    }
    public void setHospitalId(Integer hospitalId) {
        this.hospitalId = hospitalId;
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
}
