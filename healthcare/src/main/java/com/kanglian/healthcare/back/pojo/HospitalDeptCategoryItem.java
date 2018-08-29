package com.kanglian.healthcare.back.pojo;

import com.alibaba.fastjson.annotation.JSONField;
import com.easyway.business.framework.pojo.BasePojo;

public class HospitalDeptCategoryItem extends BasePojo {
	private static final long serialVersionUID = 1L;
	private String deptName;
	private String deptCode;
	@JSONField(serialize=false)
	private String deptCodeNew;
    @JSONField(serialize=false)
	private Integer parentId;
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
	public String getDeptCodeNew() {
        return deptCodeNew;
    }
    public void setDeptCodeNew(String deptCodeNew) {
        this.deptCodeNew = deptCodeNew;
    }
	public Integer getParentId() {
		return parentId;
	}
	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}
}
