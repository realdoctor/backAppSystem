package com.kanglian.healthcare.back.constants;

import com.kanglian.healthcare.util.PropConfig;

public final class AlipayConfig {

    // 商户appid
    public static String APPID             = "2018042660061222";
    // 私钥 pkcs8格式的
    public static String APP_PRIVATE_KEY   = "";
    // 服务器异步通知页面路径 需http://或者https://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static String NOTIFY_URL        = "http://47.98.156.204:8088/pay/alipay/reCallBack";
    // 页面跳转同步通知页面路径 需http://或者https://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问 商户可以自定义同步跳转地址
    public static String RETURN_URL        = "http://47.98.156.204:8088/pay/alipay_success.html";
    // 请求网关地址
    public static String PAY_URL           = "https://openapi.alipay.com/gateway.do";
    // 编码
    public static String CHARSET           = "UTF-8";
    // 返回格式
    public static String FORMAT            = "json";
    // 支付宝公钥
    public static String ALIPAY_PUBLIC_KEY =
            "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAic2jfS8lrShSHaNnwjD+8M4uBuZ4W8JmvHuzxZRYE0rTWxrV3zX1XOYHeUdtnN76Nh5DZVoU9fYZFmbPAHMF9Cxv819k0vq0uYUSwHPCT3lUNUcCoun/IONAaG5w3cU81a3zWLXbkcsu+JiO5SwgyzWVyIZkeeNY0jEG4HOxMYfwJJx+5bnuB638rELDpITr6wpGaZgpd7WkqRI0+kwpPUcmJYC0bN1mxEk8+INjqvOmkUPz8f32TClupuu7lQ1r8r7iofYcQNax9clli7pcxcqrBC3eS4Y1hYKUaZcCQIEtzzo0qjypaipqgSNIRm9gH5H+RCdiJwDQCAeG1phEDQIDAQAB";
    // 日志记录目录
    public static String LOG_PATH          = "/home/logs";
    // RSA2
    public static String SIGNTYPE          = "RSA2";

    public static String getAppid() {
        return PropConfig.getInstance().getPropertyValue("appid");
    }

    public static String getPayUrl() {
        return PropConfig.getInstance().getPropertyValue("pay_url");
    }

    public static String getAppPrivateKey() {
        return PropConfig.getInstance().getPropertyValue("app_private_key");
    }

    public static String getAlipayPublicKey() {
        return PropConfig.getInstance().getPropertyValue("alipay_public_key");
    }

    public static String getNotifyUrl() {
        return PropConfig.getInstance().getPropertyValue("notify_url");
    }

    public static String getReturnUrl() {
        return PropConfig.getInstance().getPropertyValue("return_url");
    }
}
