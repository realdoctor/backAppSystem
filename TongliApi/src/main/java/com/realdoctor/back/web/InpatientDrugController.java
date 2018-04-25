package com.realdoctor.back.web;

import com.easyway.business.framework.springmvc.controller.CrudController;
import org.springframework.stereotype.Controller;
import com.realdoctor.back.dal.pojo.InpatientDrug;
import com.realdoctor.back.service.InpatientDrugBo;

@Controller
public class InpatientDrugController extends CrudController<InpatientDrug,InpatientDrugBo> {

}
