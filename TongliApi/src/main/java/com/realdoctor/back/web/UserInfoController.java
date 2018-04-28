package com.realdoctor.back.web;

import com.easyway.business.framework.springmvc.controller.CrudController;
import com.realdoctor.back.dal.pojo.UserInfo;
import org.springframework.stereotype.Controller;
import com.realdoctor.back.service.UserInfoBo;

@Controller
public class UserInfoController extends CrudController<UserInfo,UserInfoBo> {

}
