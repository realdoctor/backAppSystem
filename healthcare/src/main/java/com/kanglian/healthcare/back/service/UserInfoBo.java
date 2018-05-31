package com.kanglian.healthcare.back.service;

import org.springframework.stereotype.Service;
import com.easyway.business.framework.bo.CrudBo;
import com.kanglian.healthcare.back.dal.dao.UserInfoDao;
import com.kanglian.healthcare.back.dal.pojo.User;
import com.kanglian.healthcare.back.dal.pojo.UserInfo;
import com.kanglian.healthcare.exception.DBException;

@Service
public class UserInfoBo extends CrudBo<UserInfo, UserInfoDao> {

    /**
     * 获取个人基本信息
     * 
     * @param user
     * @return
     */
    public UserInfo getUserInfo(User user) {
        try {
            return this.dao.getUserInfo(user);
        } catch (Exception ex) {
            throw new DBException(ex);
        }
    }

    /**
     * 更新用户关系，同步用户关联
     * 
     * @param user
     * @return
     */
    public int updateRelationship(User user) {
        try {
            return this.dao.updateRelationship(user);
        } catch (Exception ex) {
            throw new DBException(ex);
        }
    }
}
