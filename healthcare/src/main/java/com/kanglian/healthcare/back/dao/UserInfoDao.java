package com.kanglian.healthcare.back.dao;

import com.easyway.business.framework.dao.CrudDao;
import com.kanglian.healthcare.back.pojo.User;
import com.kanglian.healthcare.back.pojo.UserInfo;

public interface UserInfoDao extends CrudDao<UserInfo> {

    /**
     * 用户基本信息
     * 
     * @param user
     * @return
     */
    public UserInfo getUserInfo(User user);
    
    /**
     * 同步用户关联，挂关系
     * 
     * @param user
     * @return
     */
    public int updateRelationship(User user);
}
