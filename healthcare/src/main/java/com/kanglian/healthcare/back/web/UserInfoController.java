package com.kanglian.healthcare.back.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.easyway.business.framework.springmvc.controller.CrudController;
import com.easyway.business.framework.springmvc.result.ResultBody;
import com.easyway.business.framework.springmvc.result.ResultUtil;
import com.github.pagehelper.util.StringUtil;
import com.kanglian.healthcare.authorization.annotation.Authorization;
import com.kanglian.healthcare.authorization.annotation.CurrentUser;
import com.kanglian.healthcare.back.dal.pojo.User;
import com.kanglian.healthcare.back.dal.pojo.UserInfo;
import com.kanglian.healthcare.back.service.HospitalGuahaoLogBo;
import com.kanglian.healthcare.back.service.UserInfoBo;
import com.kanglian.healthcare.exception.InvalidParamException;

@Authorization
@RestController
@RequestMapping(value = "/user")
public class UserInfoController extends CrudController<UserInfo, UserInfoBo> {

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
    public ResultBody getUserInfo(User user) throws Exception {
        String mobilePhone = user.getMobilePhone();
        if (StringUtil.isEmpty(mobilePhone)) {
            throw new InvalidParamException("mobilePhone");
        }
        UserInfo userInfo = this.bo.getUserInfo(user);
        if (userInfo == null) {
            return ResultUtil.error("获取用户信息失败");
        }
        return ResultUtil.success(this.bo.reformUserInfo(userInfo));
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
