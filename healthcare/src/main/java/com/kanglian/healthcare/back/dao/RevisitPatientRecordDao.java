package com.kanglian.healthcare.back.dao;

import java.util.List;
import java.util.Map;
import com.easyway.business.framework.mybatis.query.ConditionQuery;
import com.kanglian.healthcare.back.pojo.PatientRecord;
import com.kanglian.healthcare.common.NewCrudDao;

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
