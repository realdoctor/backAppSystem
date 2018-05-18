package com.kanglian.healthcare.back.dal.dao;

import com.kanglian.healthcare.back.dal.pojo.HospitalDeptCategory;
import java.util.List;
import com.easyway.business.framework.dao.CrudDao;

public interface HospitalDeptCategoryDao extends CrudDao<HospitalDeptCategory> {

    /**
     * 查询医疗机构各科室分类表
     * 
     * @param sid
     * @return
     */
    public List<HospitalDeptCategory> getHospitalDeptList();
    
    public List<HospitalDeptCategory> getHospitalDeptCategory(Integer sid);
}
