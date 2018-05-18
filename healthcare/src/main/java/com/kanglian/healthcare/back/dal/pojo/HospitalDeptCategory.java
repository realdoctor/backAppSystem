package com.kanglian.healthcare.back.dal.pojo;

import java.util.List;
import com.alibaba.fastjson.annotation.JSONField;
import com.easyway.business.framework.pojo.BasePojo;

public class HospitalDeptCategory extends BasePojo {
	private static final long serialVersionUID = 1L;
	@JSONField(serialize=false)
	private Integer id;
	private String deptName;
	private String deptCode;
	@JSONField(serialize=false)
	private Integer parentId;
	private List<HospitalDeptCategory> deptList;
	public List<HospitalDeptCategory> getDeptList() {
        return deptList;
    }
    public void setDeptList(List<HospitalDeptCategory> deptList) {
        this.deptList = deptList;
    }
    public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
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
	public Integer getParentId() {
		return parentId;
	}
	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}
}
