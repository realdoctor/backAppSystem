package com.realdoctor.back.web;

import com.easyway.business.framework.springmvc.controller.CrudController;
import org.springframework.stereotype.Controller;
import com.realdoctor.back.dal.pojo.GoodsCategory;
import com.realdoctor.back.service.GoodsCategoryBo;

@Controller
public class GoodsCategoryController extends CrudController<GoodsCategory,GoodsCategoryBo> {

}
