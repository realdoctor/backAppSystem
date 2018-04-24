package com.realdoctor.back.inpatient.web;

import com.easyway.business.framework.springmvc.controller.CrudController;
import org.springframework.stereotype.Controller;
import com.realdoctor.back.inpatient.pojo.Inpatient;
import com.realdoctor.back.inpatient.bo.InpatientBo;

@Controller
public class InpatientController extends CrudController<Inpatient,InpatientBo> {

}
