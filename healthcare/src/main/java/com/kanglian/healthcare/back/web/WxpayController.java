package com.kanglian.healthcare.back.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 微信支付
 * 
 * @author xl.liu
 */
@Controller
@RequestMapping(value = "/pay/wxpay")
public class WxpayController extends BaseController {

    private final static Logger logger = LoggerFactory.getLogger(WxpayController.class);
}
