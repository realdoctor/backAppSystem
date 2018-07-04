package com.kanglian.healthcare.back.web;

import com.easyway.business.framework.springmvc.controller.CrudController;
import org.springframework.stereotype.Controller;
import com.kanglian.healthcare.back.service.PaymentOrderItemBo;
import com.kanglian.healthcare.back.dal.pojo.PaymentOrderItem;

@Controller
public class PaymentOrderItemController extends CrudController<PaymentOrderItem,PaymentOrderItemBo> {

}
