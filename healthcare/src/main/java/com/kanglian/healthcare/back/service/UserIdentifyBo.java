package com.kanglian.healthcare.back.service;

import com.easyway.business.framework.bo.CrudBo;
import com.kanglian.healthcare.back.dao.UserIdentifyDao;
import com.kanglian.healthcare.back.pojo.UserIdentify;
import com.kanglian.healthcare.exception.DBException;
import org.springframework.stereotype.Service;

@Service
public class UserIdentifyBo extends CrudBo<UserIdentify, UserIdentifyDao> {

    public UserIdentify getByUserId(Integer userId) {
        try {
            return this.dao.getByUserId(userId);
        } catch (Exception ex) {
            throw new DBException(ex);
        }
    }
}
