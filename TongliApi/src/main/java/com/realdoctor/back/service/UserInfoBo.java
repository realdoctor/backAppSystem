package com.realdoctor.back.service;

import org.springframework.stereotype.Service;

import com.easyway.business.framework.bo.CrudBo;
import com.realdoctor.back.dal.dao.UserInfoDao;
import com.realdoctor.back.dal.pojo.User;
import com.realdoctor.back.dal.pojo.UserInfo;
import com.realdoctor.exception.DBException;

@Service
public class UserInfoBo extends CrudBo<UserInfo, UserInfoDao> {

    public UserInfo getUserInfo(User user) {
        try {
            return this.dao.getUserInfo(user);
        } catch (Exception ex) {
            throw new DBException(ex);
        }
    }
}
