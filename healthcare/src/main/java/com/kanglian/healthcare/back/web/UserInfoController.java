package com.kanglian.healthcare.back.web;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.alibaba.fastjson.JSONObject;
import com.easyway.business.framework.springmvc.controller.CrudController;
import com.easyway.business.framework.springmvc.result.ResultBody;
import com.easyway.business.framework.springmvc.result.ResultUtil;
import com.kanglian.healthcare.back.constants.Constants;
import com.kanglian.healthcare.back.dal.pojo.User;
import com.kanglian.healthcare.back.dal.pojo.UserInfo;
import com.kanglian.healthcare.back.service.UserInfoBo;
import com.kanglian.healthcare.util.RedisCache;

@RestController
@RequestMapping(value = "/userInfo")
public class UserInfoController extends CrudController<UserInfo, UserInfoBo> {

    @Autowired
    private RedisCache redisCache;

    /**
     * 用户基本信息
     * 
     * @param query
     * @return
     * @throws Exception
     */
    @GetMapping
    @SuppressWarnings("rawtypes")
    public ResultBody fetchUserInfo(User user) throws Exception {
        final UserInfo userInfo = this.bo.getUserInfo(user);
        if (userInfo == null) {
            return ResultUtil.error("获取用户基本信息失败");
        }
        JSONObject jsonObject = userInfo.toJSONObject();
        try {
            // 民族
            jsonObject.put("nationalityName",
                    ((Map) redisCache.getCacheObject(Constants.STD_NATIONALITY))
                            .get(userInfo.getNationalityCode()));
            // 身份证类型
            jsonObject.put("idTypeName",
                    ((Map) redisCache.getCacheObject(Constants.STD_PERSON_ID_TYPE))
                            .get(userInfo.getIdTypeCode()));
            // 婚姻状况
            jsonObject.put("marriageName", ((Map) redisCache.getCacheObject(Constants.STD_MARRIAGE))
                    .get(userInfo.getMarriageCode()));
            // 性别
            jsonObject.put("sexName",
                    ((Map) redisCache.getCacheObject(Constants.STD_SEX)).get(userInfo.getSexCode()));
            // 血型
            jsonObject.put("aboName", ((Map) redisCache.getCacheObject(Constants.STD_BLOOD_TYPE))
                    .get(userInfo.getAboCode()));
            // RH血型
            jsonObject.put("rhName", ((Map) redisCache.getCacheObject(Constants.STD_RH_RESULT))
                    .get(userInfo.getRhCode()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResultUtil.success(jsonObject);
    }

}
