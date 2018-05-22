package com.kanglian.healthcare.back.dal.dao;

import java.util.List;
import java.util.Map;
import com.easyway.business.framework.dao.CrudDao;
import com.easyway.business.framework.mybatis.query.ConditionQuery;
import com.kanglian.healthcare.back.dal.pojo.HospitalDept;

public interface HospitalDeptDao extends CrudDao<HospitalDept> {

    /**
     * 按医院、科室、疾病获取本院医生[按专家预约]
     * 
     * @param query
     * @return
     */
    public List<HospitalDept> findDeptDoctor(ConditionQuery query);

    /**
     * 按医院、科室、疾病获取本院医生[按日期预约]
     * 
     * @param query
     * @return
     */
    public List<HospitalDept> findRoutineWorkDoctor(ConditionQuery query);
    
    /**
     * 工作日列表[按日期预约]
     * 
     * @param query
     * @return
     */
    public List<Map<String, String>> findWorkingDay(ConditionQuery query);
    
}
