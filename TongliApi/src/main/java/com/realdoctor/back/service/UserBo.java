package com.realdoctor.back.service;

import com.realdoctor.back.dal.dao.UserDao;
import com.easyway.business.framework.bo.CrudBo;
import org.springframework.stereotype.Service;
import com.realdoctor.back.dal.pojo.User;
import com.realdoctor.exception.DBException;

@Service
public class UserBo extends CrudBo<User,UserDao> {

    public User login(User user) {
        try {
            return this.dao.login(user);
        } catch (Exception ex) {
            throw new DBException(ex);
        }
    }

    public boolean ifExist(String mobilePhone){
        try {
            User user = this.dao.queryUser(mobilePhone);
            return user != null ? true : false;
        } catch (Exception ex) {
            throw new DBException(ex);
        }
    }
}
