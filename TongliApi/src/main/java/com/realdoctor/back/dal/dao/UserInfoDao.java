package com.realdoctor.back.dal.dao;

import com.realdoctor.back.dal.pojo.User;
import com.realdoctor.back.dal.pojo.UserInfo;
import com.easyway.business.framework.dao.CrudDao;

public interface UserInfoDao extends CrudDao<UserInfo> {

    /**
     * 用户基本信息
     * 
     * @param user
     * @return
     */
    public UserInfo getUserInfo(User user);
}
