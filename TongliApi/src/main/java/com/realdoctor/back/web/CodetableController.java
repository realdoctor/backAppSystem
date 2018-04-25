package com.realdoctor.back.web;

import com.easyway.business.framework.springmvc.controller.CrudController;
import org.springframework.stereotype.Controller;
import com.realdoctor.back.dal.pojo.Codetable;
import com.realdoctor.back.service.CodetableBo;

@Controller
public class CodetableController extends CrudController<Codetable,CodetableBo> {

}
