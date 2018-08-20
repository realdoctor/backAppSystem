package com.kanglian.healthcare.back.service;

import org.springframework.stereotype.Service;
import com.easyway.business.framework.mybatis.query.ConditionQuery;
import com.easyway.business.framework.mybatis.query.condition.SingleValueCondition;
import com.kanglian.healthcare.back.common.DaoExecutorAdapter;
import com.kanglian.healthcare.back.common.DaoTemplate;
import com.kanglian.healthcare.back.common.NewCrudBo;
import com.kanglian.healthcare.back.dal.dao.UserDoctorDao;
import com.kanglian.healthcare.back.dal.pojo.UserDoctor;

@Service
public class UserDoctorBo extends NewCrudBo<UserDoctor, UserDoctorDao> {

    /**
     * 获取医生相关信息
     * 
     * @param userId
     * @return
     */
    public UserDoctor getDoctorInfo(final ConditionQuery query) {
        return DaoTemplate.select(new DaoExecutorAdapter() {

            @SuppressWarnings("unchecked")
            @Override
            public UserDoctor select() throws Exception {
                return getDao().getDoctorInfo(query);
            }

        });
    }

    public UserDoctor getDoctorInfoById(final Integer userId) {
        return DaoTemplate.select(new DaoExecutorAdapter() {

            @SuppressWarnings("unchecked")
            @Override
            public UserDoctor select() throws Exception {
                ConditionQuery query = new ConditionQuery();
                query.addSingleValueCondition(new SingleValueCondition("user_id", userId));
                return getDao().getDoctorInfo(query);
            }

        });
    }
}
