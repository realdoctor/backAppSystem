package com.realdoctor.back.web;

import org.springframework.web.bind.annotation.RestController;

import com.easyway.business.framework.springmvc.controller.CrudController;
import com.realdoctor.back.dal.pojo.Outpatient;
import com.realdoctor.back.service.OutpatientBo;

/**
 * 医疗服务-门诊摘要信息
 * 
 * @author xl.liu
 */
@RestController
public class OutpatientController extends CrudController<Outpatient, OutpatientBo> {

}
