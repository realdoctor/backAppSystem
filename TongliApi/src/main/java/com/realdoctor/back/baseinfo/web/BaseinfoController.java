package com.realdoctor.back.baseinfo.web;

import com.easyway.business.framework.springmvc.controller.CrudController;
import org.springframework.stereotype.Controller;
import com.realdoctor.back.baseinfo.bo.BaseinfoBo;
import com.realdoctor.back.baseinfo.pojo.Baseinfo;

@Controller
public class BaseinfoController extends CrudController<Baseinfo,BaseinfoBo> {

}
