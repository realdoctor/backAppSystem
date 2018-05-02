package com.realdoctor.back.web;

import com.easyway.business.framework.springmvc.controller.CrudController;
import org.springframework.stereotype.Controller;
import com.realdoctor.back.service.PatientDrugBo;
import com.realdoctor.back.dal.pojo.PatientDrug;

@Controller
public class PatientDrugController extends CrudController<PatientDrug,PatientDrugBo> {

}
