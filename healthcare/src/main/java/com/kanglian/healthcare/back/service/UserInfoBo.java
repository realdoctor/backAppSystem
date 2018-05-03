package com.kanglian.healthcare.back.service;

import org.springframework.stereotype.Service;

import com.easyway.business.framework.bo.CrudBo;
import com.kanglian.healthcare.back.dal.dao.UserInfoDao;
import com.kanglian.healthcare.back.dal.pojo.User;
import com.kanglian.healthcare.back.dal.pojo.UserInfo;
import com.kanglian.healthcare.exception.DBException;

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
