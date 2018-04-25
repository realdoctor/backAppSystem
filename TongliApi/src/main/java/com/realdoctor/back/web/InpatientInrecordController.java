package com.realdoctor.back.web;

import com.easyway.business.framework.springmvc.controller.CrudController;
import org.springframework.stereotype.Controller;
import com.realdoctor.back.dal.pojo.InpatientInrecord;
import com.realdoctor.back.service.InpatientInrecordBo;

@Controller
public class InpatientInrecordController extends CrudController<InpatientInrecord,InpatientInrecordBo> {

}
