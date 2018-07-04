package com.kanglian.healthcare.back.web;

import com.easyway.business.framework.springmvc.controller.CrudController;
import com.kanglian.healthcare.back.dal.pojo.PaymentOrder;
import org.springframework.stereotype.Controller;
import com.kanglian.healthcare.back.service.PaymentOrderBo;

@Controller
public class PaymentOrderController extends CrudController<PaymentOrder,PaymentOrderBo> {

}
