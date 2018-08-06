package com.kanglian.healthcare.back.dal.dao;

import com.kanglian.healthcare.back.dal.pojo.Hospital;
import java.util.List;
import com.easyway.business.framework.dao.CrudDao;
import com.easyway.business.framework.mybatis.query.ConditionQuery;

public interface HospitalDao extends CrudDao<Hospital> {
    /**
     * 搜索医院、医生、科室、疾病
     * 
     * @return 医院 | 医生
     */
    public List<Hospital> queryForHospitalAndDoctor(ConditionQuery query);
    
    public Hospital getByCode(String hospitalCode);
}
