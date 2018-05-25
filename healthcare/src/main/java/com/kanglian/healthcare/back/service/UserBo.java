package com.kanglian.healthcare.back.service;

import org.springframework.stereotype.Service;
import com.easyway.business.framework.bo.CrudBo;
import com.easyway.business.framework.util.DateUtil;
import com.kanglian.healthcare.back.dal.dao.UserDao;
import com.kanglian.healthcare.back.dal.pojo.User;
import com.kanglian.healthcare.exception.DBException;

@Service
public class UserBo extends CrudBo<User, UserDao> {

    public User login(User user) {
        try {
            return this.dao.login(user);
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
     * 实名认证
     * 
     * @param user
     * @return
     */
    public boolean certification(User user) {
        try {
            User userT = this.dao.queryUser(user.getMobilePhone());
            if(userT != null) {
                userT.setRealName(user.getRealName());
                userT.setIdNo(user.getIdNo());
                userT.setLastUpdateDtime(DateUtil.currentDate());
                this.dao.update(userT);
                return true;
            }
            return false;
        } catch (Exception ex) {
            throw new DBException(ex);
        }
    }
}
