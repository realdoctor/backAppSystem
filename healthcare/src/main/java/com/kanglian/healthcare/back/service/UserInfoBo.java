package com.kanglian.healthcare.back.service;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.alibaba.fastjson.JSONObject;
import com.easyway.business.framework.bo.CrudBo;
import com.github.pagehelper.util.StringUtil;
import com.kanglian.healthcare.back.constant.Constants;
import com.kanglian.healthcare.back.dao.UserInfoDao;
import com.kanglian.healthcare.back.pojo.User;
import com.kanglian.healthcare.back.pojo.UserInfo;
import com.kanglian.healthcare.exception.DBException;
import com.kanglian.healthcare.util.PropConfig;
import com.kanglian.healthcare.util.RedisCacheManager;
import com.kanglian.healthcare.util.ValidateUtil;

@Service
public class UserInfoBo extends CrudBo<UserInfo, UserInfoDao> {

    @Autowired
    private RedisCacheManager   redisCacheManager;
    
    /**
     * 获取个人基本信息
     * 
     * @param user
     * @return
     */
    public UserInfo getUserInfo(User user) {
        try {
            return this.dao.getUserInfo(user);
        } catch (Exception ex) {
            throw new DBException(ex);
        }
    }

    /**
     * 更新用户关系，同步用户关联
     * 
     * @param user
     * @return
     */
    public int updateRelationship(User user) {
        try {
            return this.dao.updateRelationship(user);
        } catch (Exception ex) {
            throw new DBException(ex);
        }
    }
    
    /**
     * 重组用户基本信息
     * 
     * @param userInfo
     * @return
     */
    @SuppressWarnings("rawtypes")
    public JSONObject reformUserInfo(UserInfo userInfo) {
        JSONObject jsonObject = userInfo.toJSONObject();
        try {
            // 民族
            jsonObject.put("nationalityName",
                    ((Map) redisCacheManager.getCacheObject(Constants.STD_NATIONALITY))
                            .get(userInfo.getNationalityCode()));
            // 婚姻状况
            jsonObject.put("marriageName",
                    ((Map) redisCacheManager.getCacheObject(Constants.STD_MARRIAGE))
                            .get(userInfo.getMarriageCode()));
            // 性别
            jsonObject.put("sexName", ((Map) redisCacheManager.getCacheObject(Constants.STD_SEX))
                    .get(userInfo.getSexCode()));
            // 血型
            jsonObject.put("aboName",
                    ((Map) redisCacheManager.getCacheObject(Constants.STD_BLOOD_TYPE))
                            .get(userInfo.getAboCode()));
            // RH血型
            jsonObject.put("rhName",
                    ((Map) redisCacheManager.getCacheObject(Constants.STD_RH_RESULT))
                            .get(userInfo.getRhCode()));
            // 手机号
            jsonObject.put("mobilePhone", userInfo.getMobilePhone());
            // 证件类型id
            if (StringUtil.isNotEmpty(userInfo.getIdNo())) {
                jsonObject.put("typeId", userInfo.getTypeId());
                jsonObject.put("idNo", ValidateUtil.hideIdCard(userInfo.getIdNo()));
            } else {
                jsonObject.put("typeId", "");
                jsonObject.put("idNo", "");
            }
            // 用户头像
            String domainUrl = PropConfig.getInstance().getPropertyValue(Constants.STATIC_URL);
            jsonObject.put("originalImageUrl", "");
            jsonObject.put("imageUrl", "");
            if (StringUtil.isNotEmpty(userInfo.getOriginalImageUrl())) {
                jsonObject.put("originalImageUrl",
                        domainUrl.concat(userInfo.getOriginalImageUrl()));
            }
            if (StringUtil.isNotEmpty(userInfo.getImageUrl())) {
                jsonObject.put("imageUrl", domainUrl.concat(userInfo.getImageUrl()));
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
        return jsonObject;
    }
 }
