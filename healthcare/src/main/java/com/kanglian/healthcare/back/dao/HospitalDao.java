package com.kanglian.healthcare.back.dao;

import java.util.List;
import com.easyway.business.framework.dao.CrudDao;
import com.easyway.business.framework.mybatis.query.ConditionQuery;
import com.kanglian.healthcare.back.pojo.Hospital;

public interface HospitalDao extends CrudDao<Hospital> {
    /**
     * 搜索医院、医生、科室、疾病
     * 
     * @return 医院 | 医生
     */
    public List<Hospital> queryHospitalAndDoctor(ConditionQuery query);
    
    public Hospital getByCode(String hospitalCode);
}
