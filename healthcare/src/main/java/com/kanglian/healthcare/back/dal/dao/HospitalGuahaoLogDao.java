package com.kanglian.healthcare.back.dal.dao;

import com.kanglian.healthcare.back.dal.pojo.HospitalGuahaoLog;
import java.util.List;
import java.util.Map;
import com.easyway.business.framework.dao.CrudDao;

public interface HospitalGuahaoLogDao extends CrudDao<HospitalGuahaoLog> {

    /**
     * 获取挂号单列表
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
    public List<Map<String, String>> myGuahaoOrder(String userId);
}
