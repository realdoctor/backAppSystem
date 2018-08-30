package com.kanglian.healthcare.back.dao;

import java.util.List;
import com.easyway.business.framework.mybatis.query.ConditionQuery;
import com.kanglian.healthcare.back.pojo.UserDoctor;
import com.kanglian.healthcare.common.NewCrudDao;

public interface UserDoctorDao extends NewCrudDao<UserDoctor> {

    /**
     * 获取医生详情
     * 
     * @param userId
     * @return
     */
    public UserDoctor getDoctorInfo(ConditionQuery query);
    
    public List<UserDoctor> getDoctorInfoByName(ConditionQuery query);
}
