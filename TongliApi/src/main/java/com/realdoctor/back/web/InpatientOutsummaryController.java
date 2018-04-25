package com.realdoctor.back.web;

import com.easyway.business.framework.springmvc.controller.CrudController;
import org.springframework.stereotype.Controller;
import com.realdoctor.back.service.InpatientOutsummaryBo;
import com.realdoctor.back.dal.pojo.InpatientOutsummary;

@Controller
public class InpatientOutsummaryController extends CrudController<InpatientOutsummary,InpatientOutsummaryBo> {

}
