package com.kanglian.healthcare.back.dal.dao;

import com.easyway.business.framework.mybatis.query.ConditionQuery;
import com.kanglian.healthcare.back.common.NewCrudDao;
import com.kanglian.healthcare.back.dal.pojo.UserDoctor;

public interface UserDoctorDao extends NewCrudDao<UserDoctor> {

    /**
     * 获取医生详情
     * 
     * @param userId
     * @return
     */
    public UserDoctor getDoctorInfo(ConditionQuery query);
}
