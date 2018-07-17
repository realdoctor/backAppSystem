package com.kanglian.healthcare.back.service;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.easyway.business.framework.bo.CrudBo;
import com.easyway.business.framework.util.DateUtil;
import com.easyway.business.framework.util.StringUtil;
import com.kanglian.healthcare.authorization.token.impl.RedisTokenManager;
import com.kanglian.healthcare.authorization.util.TokenUtil;
import com.kanglian.healthcare.back.constants.UploadType;
import com.kanglian.healthcare.back.dal.dao.UploadContentDao;
import com.kanglian.healthcare.back.dal.dao.UserDao;
import com.kanglian.healthcare.back.dal.dao.UserIdentifyDao;
import com.kanglian.healthcare.back.dal.dao.UserInfoDao;
import com.kanglian.healthcare.back.dal.dao.UserRoleDao;
import com.kanglian.healthcare.back.dal.pojo.UploadContent;
import com.kanglian.healthcare.back.dal.pojo.User;
import com.kanglian.healthcare.back.dal.pojo.UserIdentify;
import com.kanglian.healthcare.back.dal.pojo.UserRole;
import com.kanglian.healthcare.exception.DBException;
import com.kanglian.healthcare.exception.InvalidOperationException;
import com.kanglian.healthcare.util.JsonUtil;
import com.kanglian.healthcare.util.MD5Util;

@Service
public class UserBo extends CrudBo<User, UserDao> {

    @Autowired
    private UserInfoDao userInfoDao;
    @Autowired
    private UserRoleDao userRoleDao;
    @Autowired
    private UserIdentifyDao userIdentifyDao;
    @Autowired
    private UploadContentDao uploadContentDao;
    
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
     * 获取用户上传的病历路径
     * 
     * @param userId
     * @return
     */
    public String getUserDataUrl(Integer userId) {
        try {
            UploadContent uploadContent =
                    uploadContentDao.getByUserIdAndType(userId, UploadType.FILES.getValue());
            if (uploadContent != null) {
                return uploadContent.getSrc();
            }
            return null;
        } catch (Exception ex) {
            throw new DBException(ex);
        }
    }
    
    /**
     * 获取认证信息
     * 
     * @param userId
     * @return
     */
    public Map<String, Object> getIdentifyInfo(Integer userId) {
        try {
            return this.dao.getIdentifyInfo(userId);
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
    public boolean certification(UserIdentify userIdentify) {
        if (userIdentify == null) {
            throw new InvalidOperationException("userIdentify");
        }
        try {
            User userT = this.dao.queryUser(userIdentify.getMobilePhone());
            if(userT != null) {
                userT.setIdNo(userIdentify.getIdNo());
                userT.setRealName(userIdentify.getRealName());
                userT.setLastUpdateDtime(DateUtil.currentDate());
                this.dao.update(userT);
                
                Integer userId = userT.getUserId().intValue();
                // 用户认证信息
                UserIdentify userIdentifyT = userIdentifyDao.getByUserId(userId);
                if (userIdentifyT != null) {
                    userIdentifyT.setUserId(userId);
                    userIdentifyT.setStatus(0);
                    userIdentifyT.setAddTime(DateUtil.currentDate());
                    userIdentifyT.setName("用户重新认证，时间="+DateUtil.getCurrentTime());
                    userIdentifyDao.update(userIdentifyT);
                } else {
                    userIdentify.setUserId(userId);
                    userIdentify.setStatus(0);
                    userIdentify.setAddTime(DateUtil.currentDate());
                    userIdentifyDao.save(userIdentify);
                }
                
                // 关联用户信息，挂载病历。
                // 如果证件类型挂载不上，再用手机号
                int ss = userInfoDao.updateRelationship(userT);
                if (ss < 1) {
                    User newUser = new User();
                    newUser.setMobilePhone(userT.getMobilePhone());
                    userInfoDao.updateRelationship(newUser);
                }
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

    public String createRelationship(User user) {
        try {
            // 生成TOKEN
            String accessToken = TokenUtil.generToken(JsonUtil.beanToJson(user));
            redisTokenManager.createRelationship(user.getMobilePhone(),
                    StringUtil.join(new Object[] {user.getUserId(), accessToken}, UNDERLINE));
            return accessToken;
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
