package com.realdoctor.back.web;

import com.easyway.business.framework.springmvc.controller.CrudController;
import org.springframework.stereotype.Controller;
import com.realdoctor.back.service.BaseinfoBo;
import com.realdoctor.back.dal.pojo.Baseinfo;

@Controller
public class BaseinfoController extends CrudController<Baseinfo,BaseinfoBo> {

}
