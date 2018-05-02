package com.realdoctor.back.web;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.easyway.business.framework.springmvc.controller.CrudController;
import com.easyway.business.framework.springmvc.result.ResultBody;
import com.easyway.business.framework.springmvc.result.ResultUtil;
import com.realdoctor.back.constants.Constant;
import com.realdoctor.back.dal.pojo.User;
import com.realdoctor.back.dal.pojo.UserInfo;
import com.realdoctor.back.service.UserInfoBo;
import com.realdoctor.util.CacheManager;

@RestController
@RequestMapping(value = "/userInfo")
public class UserInfoController extends CrudController<UserInfo, UserInfoBo> {

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
        JSONObject jsonObject = userInfo.toJSONObject();
        try {
            // 民族
            jsonObject.put("nationalityName",
                           ((Map) CacheManager.get(Constant.STD_NATIONALITY)).get(userInfo.getNationalityCode()));
            // 身份证类型
            jsonObject.put("idTypeName",
                           ((Map) CacheManager.get(Constant.STD_PERSON_ID_TYPE)).get(userInfo.getIdTypeCode()));
            // 婚姻状况
            jsonObject.put("marriageName",
                           ((Map) CacheManager.get(Constant.STD_MARRIAGE)).get(userInfo.getMarriageCode()));
            // 性别
            jsonObject.put("sexName", ((Map) CacheManager.get(Constant.STD_SEX)).get(userInfo.getSexCode()));
            // 血型
            jsonObject.put("aboName", ((Map) CacheManager.get(Constant.STD_BLOOD_TYPE)).get(userInfo.getAboCode()));
            // RH血型
            jsonObject.put("rhName", ((Map) CacheManager.get(Constant.STD_RH_RESULT)).get(userInfo.getRhCode()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResultUtil.success(jsonObject);
    }

}
