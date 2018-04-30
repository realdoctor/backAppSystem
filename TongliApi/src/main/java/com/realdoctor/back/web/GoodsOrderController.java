package com.realdoctor.back.web;

import com.easyway.business.framework.springmvc.controller.CrudController;
import org.springframework.stereotype.Controller;
import com.realdoctor.back.dal.pojo.GoodsOrder;
import com.realdoctor.back.service.GoodsOrderBo;

@Controller
public class GoodsOrderController extends CrudController<GoodsOrder,GoodsOrderBo> {

}
