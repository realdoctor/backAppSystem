package com.realdoctor.back.dal.dao;

import com.easyway.business.framework.dao.CrudDao;
import com.realdoctor.back.dal.pojo.User;

public interface UserDao extends CrudDao<User> {

    public User login(User user);

    public User queryUser(String mobilePhone);
}
