package com.realdoctor.back.web;

import com.easyway.business.framework.springmvc.controller.CrudController;
import org.springframework.stereotype.Controller;
import com.realdoctor.back.service.OutpatientDrugBo;
import com.realdoctor.back.dal.pojo.OutpatientDrug;

@Controller
public class OutpatientDrugController extends CrudController<OutpatientDrug,OutpatientDrugBo> {

}
