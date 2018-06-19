package com.kanglian.healthcare.back.service;

import com.kanglian.healthcare.back.dal.dao.HealthNewsFocusDao;
import com.easyway.business.framework.bo.CrudBo;
import com.easyway.business.framework.mybatis.query.ConditionQuery;
import com.easyway.business.framework.mybatis.query.condition.SingleValueCondition;
import java.util.List;
import org.springframework.stereotype.Service;
import com.kanglian.healthcare.back.dal.pojo.HealthNewsFocus;

@Service
public class HealthNewsFocusBo extends CrudBo<HealthNewsFocus, HealthNewsFocusDao> {

    /**
     * 根据userId获取关注列表
     * 
     * @param userId
     * @return
     */
    public List<HealthNewsFocus> getListByUserId(Integer userId) {
        ConditionQuery query = new ConditionQuery();
        query.addSingleValueCondition(new SingleValueCondition("user_id", userId));
        return this.dao.query(query);
    }
}
