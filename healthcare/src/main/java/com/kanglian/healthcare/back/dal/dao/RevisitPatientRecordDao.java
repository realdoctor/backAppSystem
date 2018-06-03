package com.kanglian.healthcare.back.dal.dao;

import java.util.List;
import java.util.Map;
import com.easyway.business.framework.mybatis.query.ConditionQuery;
import com.kanglian.healthcare.back.common.NewCrudDao;
import com.kanglian.healthcare.back.dal.pojo.PatientRecord;

/**
 * 复诊
 * 
 * @author xl.liu
 */
public interface RevisitPatientRecordDao extends NewCrudDao<PatientRecord> {

    /**
     * 复诊患者诊断列表
     * 
     * @return
     */
    public List<Map<String, String>> findDiagList(ConditionQuery query);
}
