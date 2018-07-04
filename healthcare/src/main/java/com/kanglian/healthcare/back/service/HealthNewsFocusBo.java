package com.kanglian.healthcare.back.service;

import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;
import com.kanglian.healthcare.back.common.NewCrudBo;
import com.kanglian.healthcare.back.dal.dao.HealthNewsFocusDao;
import com.kanglian.healthcare.back.dal.pojo.HealthNewsFocus;
import com.kanglian.healthcare.exception.DBException;

@Service
public class HealthNewsFocusBo extends NewCrudBo<HealthNewsFocus, HealthNewsFocusDao> {

    /**
     * 根据userId获取关注列表
     * 
     * @param userId
     * @return
     */
    public List<Map<String, Object>> getListByUserId(Integer userId) {
        if (userId == null)
            return null;
        try {
            return this.dao.getNewsIdsByUserId(userId);
        } catch (Exception ex) {
            throw new DBException(ex);
        }
    }
}
