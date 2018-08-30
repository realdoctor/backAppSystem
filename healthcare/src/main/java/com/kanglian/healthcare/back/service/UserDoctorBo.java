package com.kanglian.healthcare.back.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.easyway.business.framework.mybatis.query.ConditionQuery;
import com.easyway.business.framework.mybatis.query.condition.SingleValueCondition;
import com.kanglian.healthcare.back.dao.UserDoctorDao;
import com.kanglian.healthcare.back.pojo.UserDoctor;
import com.kanglian.healthcare.common.DaoExecutorAdapter;
import com.kanglian.healthcare.common.DaoTemplate;
import com.kanglian.healthcare.common.NewCrudBo;

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

    public UserDoctor getDoctorInfoById(final Integer doctorUserId) {
        return DaoTemplate.select(new DaoExecutorAdapter() {

            @SuppressWarnings("unchecked")
            @Override
            public UserDoctor select() throws Exception {
                ConditionQuery query = new ConditionQuery();
                query.addSingleValueCondition(new SingleValueCondition("user_id", doctorUserId));
                return getDao().getDoctorInfo(query);
            }

        });
    }
    
    public UserDoctor getDoctorInfoByName(final String hospitalName, final String doctorName) {
        return DaoTemplate.select(new DaoExecutorAdapter() {

            @SuppressWarnings("unchecked")
            @Override
            public UserDoctor select() throws Exception {
                ConditionQuery query = new ConditionQuery();
                query.addSingleValueCondition(
                        new SingleValueCondition("t4", "hospital_name", "=", hospitalName));
                query.addSingleValueCondition(
                        new SingleValueCondition("t2", "doctor_name", "=", doctorName));
                List<UserDoctor> list = getDao().getDoctorInfoByName(query);
                if (list != null && list.size() > 0) {
                    return list.get(0);
                }
                return null;
            }

        });
    }
}
