package com.realdoctor.back.outpatient.web;

import org.springframework.web.bind.annotation.RestController;

import com.easyway.business.framework.springmvc.controller.CrudController;
import com.realdoctor.back.outpatient.bo.OutpatientBo;
import com.realdoctor.back.outpatient.pojo.Outpatient;

/**
 * 医疗服务-门诊摘要信息
 * 
 * @author xl.liu
 */
@RestController
public class OutpatientController extends CrudController<Outpatient, OutpatientBo> {

}
