package com.realdoctor.back.codetable.web;

import com.easyway.business.framework.springmvc.controller.CrudController;
import org.springframework.stereotype.Controller;
import com.realdoctor.back.codetable.bo.CodetableBo;
import com.realdoctor.back.codetable.pojo.Codetable;

@Controller
public class CodetableController extends CrudController<Codetable,CodetableBo> {

}
