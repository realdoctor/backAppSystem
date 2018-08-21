package com.kanglian.healthcare.back.dal.pojo;

import java.util.List;
import com.alibaba.fastjson.annotation.JSONField;
import com.easyway.business.framework.pojo.BasePojo;

public class HospitalDeptCategory extends BasePojo {
    private static final long serialVersionUID = 1L;
    @JSONField(serialize=false)
    private Integer deptId;
    private String deptName;
    private String deptCode;
    private List<HospitalDeptCategoryItem> deptList;
    public List<HospitalDeptCategoryItem> getDeptList() {
        return deptList;
    }
    public void setDeptList(List<HospitalDeptCategoryItem> deptList) {
        this.deptList = deptList;
    }
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
}
