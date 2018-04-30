package com.realdoctor.back.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.easyway.business.framework.springmvc.controller.CrudController;
import com.easyway.business.framework.springmvc.result.ResultBody;
import com.easyway.business.framework.springmvc.result.ResultUtil;
import com.realdoctor.back.dal.pojo.User;
import com.realdoctor.back.dal.pojo.UserInfo;
import com.realdoctor.back.service.UserInfoBo;

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
    public ResultBody getUserInfo(User user) throws Exception {
        UserInfo userInfo = this.bo.getUserInfo(user);
        return ResultUtil.success(userInfo);
    }

}
