package com.kanglian.healthcare.back.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.easyway.business.framework.bo.CrudBo;
import com.easyway.business.framework.util.StringUtil;
import com.kanglian.healthcare.authorization.token.impl.RedisTokenManager;
import com.kanglian.healthcare.back.dal.dao.UserDao;
import com.kanglian.healthcare.back.dal.pojo.User;
import com.kanglian.healthcare.exception.InvalidOperationException;

@Service
public class RedisTokenBo extends CrudBo<User, UserDao> {

    @Autowired
    private RedisTokenManager   redisTokenManager;
    private final static String UNDERLINE = "_";

    public void createRelationship(String mobilePhone, Long userId, String token) {
        try {
            redisTokenManager.createRelationship(mobilePhone,
                    StringUtil.join(new Object[] {userId, token}, UNDERLINE));
        } catch (Exception ex) {
            throw new InvalidOperationException(ex);
        }
    }

    public String getKey(Long userId, String token) {
        try {
            return redisTokenManager
                    .getKey(StringUtil.join(new Object[] {userId, token}, UNDERLINE));
        } catch (Exception ex) {
            throw new InvalidOperationException(ex);
        }
    }

    public void delRelationshipByToken(Long userId, String token) {
        try {
            redisTokenManager.delRelationshipByToken(
                    StringUtil.join(new Object[] {userId, token}, UNDERLINE));
        } catch (Exception ex) {
            throw new InvalidOperationException(ex);
        }
    }
}
