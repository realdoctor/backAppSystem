package com.kanglian.healthcare.back.dal.dao;

import com.kanglian.healthcare.back.dal.pojo.HospitalAddress;
import java.util.List;
import com.easyway.business.framework.dao.CrudDao;
import com.easyway.business.framework.mybatis.query.ConditionQuery;

public interface HospitalAddressDao extends CrudDao<HospitalAddress> {
    /**
     * 按条件查询
     * 
     * @return
     */
    public List<HospitalAddress> queryForList(ConditionQuery query);
}
