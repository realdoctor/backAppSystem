package com.kanglian.healthcare.back.constant;

import java.util.Map;

public class AlipayNotifyResponse {

    private String              sign;
    private Map<String, String> alipay_trade_app_pay_response;

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public Map<String, String> getAlipay_trade_app_pay_response() {
        return alipay_trade_app_pay_response;
    }

    public void setAlipay_trade_app_pay_response(
            Map<String, String> alipay_trade_app_pay_response) {
        this.alipay_trade_app_pay_response = alipay_trade_app_pay_response;
    }

}
