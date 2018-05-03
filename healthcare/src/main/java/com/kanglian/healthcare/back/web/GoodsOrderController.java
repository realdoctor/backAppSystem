package com.kanglian.healthcare.back.web;

import com.easyway.business.framework.springmvc.controller.CrudController;
import com.kanglian.healthcare.back.dal.pojo.GoodsOrder;
import com.kanglian.healthcare.back.service.GoodsOrderBo;

import org.springframework.stereotype.Controller;

@Controller
public class GoodsOrderController extends CrudController<GoodsOrder,GoodsOrderBo> {

}
