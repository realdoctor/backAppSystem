package com.realdoctor.back.web;

import com.easyway.business.framework.springmvc.controller.CrudController;
import org.springframework.stereotype.Controller;
import com.realdoctor.back.dal.pojo.Inpatient;
import com.realdoctor.back.service.InpatientBo;

@Controller
public class InpatientController extends CrudController<Inpatient,InpatientBo> {

}
