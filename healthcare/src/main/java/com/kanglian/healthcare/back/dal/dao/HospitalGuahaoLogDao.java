package com.kanglian.healthcare.back.dal.dao;

import com.kanglian.healthcare.back.dal.pojo.HospitalGuahaoLog;
import java.util.List;
import java.util.Map;
import com.easyway.business.framework.dao.CrudDao;
import com.easyway.business.framework.mybatis.query.ConditionQuery;

public interface HospitalGuahaoLogDao extends CrudDao<HospitalGuahaoLog> {

    /**
     * 获取某医院科室医生挂号单列表
     * 
     * @param hospitalGuahaoLog
     * @return
     */
    public List<HospitalGuahaoLog> getGuahaoLog(HospitalGuahaoLog hospitalGuahaoLog);

    /**
     * 我的预约
     * 
     * @param userId
     * @return
     */
    public List<Map<String, Object>> myGuahaoOrder(ConditionQuery query);
    
    /**
     * 医生的被预约
     * 
     * @param userId
     * @return
     */
    public List<Map<String, Object>> myPatientOrder(ConditionQuery query);
}
