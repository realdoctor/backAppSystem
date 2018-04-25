package com.realdoctor.back.web;

import com.easyway.business.framework.springmvc.controller.CrudController;
import org.springframework.stereotype.Controller;
import com.realdoctor.back.service.OutpatientDiagBo;
import com.realdoctor.back.dal.pojo.OutpatientDiag;

@Controller
public class OutpatientDiagController extends CrudController<OutpatientDiag,OutpatientDiagBo> {

}
