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
import com.github.pagehelper.util.StringUtil;
import com.kanglian.healthcare.authorization.annotation.Authorization;
import com.kanglian.healthcare.authorization.annotation.CurrentUser;
import com.kanglian.healthcare.back.constants.Constants;
import com.kanglian.healthcare.back.dal.pojo.User;
import com.kanglian.healthcare.back.dal.pojo.UserInfo;
import com.kanglian.healthcare.back.service.HospitalGuahaoLogBo;
import com.kanglian.healthcare.back.service.UserInfoBo;
import com.kanglian.healthcare.exception.InvalidParamException;
import com.kanglian.healthcare.util.PropConfig;
import com.kanglian.healthcare.util.RedisCacheManager;
import com.kanglian.healthcare.util.ValidateUtil;

@Authorization
@RestController
@RequestMapping(value = "/user")
public class UserInfoController extends CrudController<UserInfo, UserInfoBo> {

    @Autowired
    private RedisCacheManager   redisCacheManager;
    @Autowired
    private HospitalGuahaoLogBo hospitalGuahaoLogBo;

    /**
     * 用户基本信息
     * 
     * @param query
     * @return
     * @throws Exception
     */
    @GetMapping("/info")
    @SuppressWarnings("rawtypes")
    public ResultBody getUserInfo(User user) throws Exception {
        String mobilePhone = user.getMobilePhone();
        if (StringUtil.isEmpty(mobilePhone)) {
            throw new InvalidParamException("mobilePhone");
        }
        UserInfo userInfo = this.bo.getUserInfo(user);
        if (userInfo == null) {
            return ResultUtil.error("获取用户信息失败");
        }
        JSONObject jsonObject = userInfo.toJSONObject();
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
        return ResultUtil.success(jsonObject);
    }

    /**
     * 我的预约
     * 
     * @param user
     * @return
     * @throws Exception
     */
    @GetMapping("/myGuahaoOrder")
    public ResultBody myGuahaoOrder(@CurrentUser User user) throws Exception {
        return ResultUtil.success(hospitalGuahaoLogBo.myDoctorOrder(user.getUserId().intValue()));
    }
}
