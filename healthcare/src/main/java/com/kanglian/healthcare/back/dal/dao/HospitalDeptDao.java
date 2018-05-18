package com.kanglian.healthcare.back.dal.dao;

import java.util.List;
import com.easyway.business.framework.dao.CrudDao;
import com.easyway.business.framework.mybatis.query.ConditionQuery;
import com.kanglian.healthcare.back.dal.pojo.HospitalDept;

public interface HospitalDeptDao extends CrudDao<HospitalDept> {

    /**
     * 按医院、科室、疾病获取本院医生
     * 
     * @param hospitalId
     * @param deptName
     * @return
     */
    public List<HospitalDept> findDeptDoctor(ConditionQuery query);
}
