package com.realdoctor.back.web;

import com.easyway.business.framework.springmvc.controller.CrudController;
import org.springframework.stereotype.Controller;
import com.realdoctor.back.service.PatientDiagBo;
import com.realdoctor.back.dal.pojo.PatientDiag;

@Controller
public class PatientDiagController extends CrudController<PatientDiag,PatientDiagBo> {

}
