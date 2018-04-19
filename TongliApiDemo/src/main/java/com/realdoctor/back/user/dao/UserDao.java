package com.realdoctor.back.user.dao;

import com.realdoctor.back.user.pojo.User;
import com.easyway.business.framework.dao.CrudDao;

public interface UserDao extends CrudDao<User> {

    /**
     * 用户登录
     * 
     * @param user
     * @return
     */
    public User login(User user);
}
