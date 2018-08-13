package com.kanglian.healthcare.back.service;

import org.springframework.stereotype.Service;
import com.easyway.business.framework.mybatis.query.ConditionQuery;
import com.easyway.business.framework.mybatis.query.condition.SingleValueCondition;
import com.kanglian.healthcare.back.common.NewCrudBo;
import com.kanglian.healthcare.back.dal.dao.UserDoctorDao;
import com.kanglian.healthcare.back.dal.pojo.UserDoctor;
import com.kanglian.healthcare.exception.DBException;

@Service
public class UserDoctorBo extends NewCrudBo<UserDoctor, UserDoctorDao> {

    /**
     * 获取医生相关信息
     * 
     * @param userId
     * @return
     */
    public UserDoctor getDoctorInfo(ConditionQuery query) {
        try {
            return this.dao.getDoctorInfo(query);
        } catch (Exception ex) {
            throw new DBException(ex);
        }
    }
    
    public UserDoctor getDoctorInfoById(Integer userId) {
        try {
            ConditionQuery query = new ConditionQuery();
            query.addSingleValueCondition(new SingleValueCondition("user_id", userId));
            return this.dao.getDoctorInfo(query);
        } catch (Exception ex) {
            throw new DBException(ex);
        }
    }
}
