package com.kanglian.healthcare.back.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.easyway.business.framework.bo.CrudBo;
import com.easyway.business.framework.util.DateUtil;
import com.easyway.business.framework.util.StringUtil;
import com.kanglian.healthcare.authorization.token.impl.RedisTokenManager;
import com.kanglian.healthcare.back.dal.dao.UserDao;
import com.kanglian.healthcare.back.dal.dao.UserInfoDao;
import com.kanglian.healthcare.back.dal.dao.UserRoleDao;
import com.kanglian.healthcare.back.dal.pojo.User;
import com.kanglian.healthcare.back.dal.pojo.UserRole;
import com.kanglian.healthcare.exception.DBException;
import com.kanglian.healthcare.exception.InvalidOperationException;
import com.kanglian.healthcare.util.MD5Util;

@Service
public class UserBo extends CrudBo<User, UserDao> {

    @Autowired
    private UserInfoDao userInfoDao;
    @Autowired
    private UserRoleDao userRoleDao;
    
    public User login(User user) {
        try {
            return this.dao.login(user);
        } catch (Exception ex) {
            throw new DBException(ex);
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public void regist(User user) {
        if (user == null) {
            throw new InvalidOperationException("user");
        }
        try {
            // 密码加密
            user.setPwd(MD5Util.encrypt(user.getPwd()));
            user.setAddTime(DateUtil.currentDate());
            this.dao.save(user);
            UserRole rserRole = new UserRole();
            rserRole.setUserId(user.getUserId().intValue());
            rserRole.setRoleId(0);
            userRoleDao.save(rserRole);
        } catch (Exception ex) {
            throw new DBException(ex);
        }
    }
    
    public User queryUser(String mobilePhone) {
        try {
            return this.dao.queryUser(mobilePhone);
        } catch (Exception ex) {
            throw new DBException(ex);
        }
    }
    
    public boolean ifExist(String mobilePhone) {
        try {
            User user = this.dao.queryUser(mobilePhone);
            return user != null ? true : false;
        } catch (Exception ex) {
            throw new DBException(ex);
        }
    }

    public boolean ifExist(Long userId) {
        try {
            User user = this.dao.get(userId);
            return user != null ? true : false;
        } catch (Exception ex) {
            throw new DBException(ex);
        }
    }
    
    /**
     * 实名认证，关联用户信息
     * 
     * @param user
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public boolean certification(User user) {
        try {
            User userT = this.dao.queryUser(user.getMobilePhone());
            if(userT != null) {
                userT.setRealName(user.getRealName());
                userT.setIdNo(user.getIdNo());
                userT.setLastUpdateDtime(DateUtil.currentDate());
                this.dao.update(userT);
                // 关联用户信息
                userInfoDao.updateRelationship(userT);
                return true;
            }
            return false;
        } catch (Exception ex) {
            throw new DBException(ex);
        }
    }
    
    /**
     * 
     */
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
