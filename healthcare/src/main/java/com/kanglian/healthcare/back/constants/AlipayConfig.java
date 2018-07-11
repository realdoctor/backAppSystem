package com.kanglian.healthcare.back.constants;

import com.kanglian.healthcare.util.PropConfig;

public final class AlipayConfig {

    // 商户appid
    public static String APPID             = "2018042660061222";
    // 私钥 pkcs8格式的
    public static String APP_PRIVATE_KEY   =
            "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQDBVfbnz6+tnyHmjpAoTkAzGIkhNNJWa8TXCdMrD1vwvRqe7Vz3Cq8bLJjJvijpJiQfBPhcGMwvRRrzSBQDm5kfggmXo1/qMUkwmNdG0FQBZSolBN0p/+/mJ+d35L2HNM8OqDTs25U8w1kpSL51Mfqkh18aXsFr4Z1N9mBDvGLJc+3oYGPl+KaSdNSG+6lz1+sv3HmEkdeozY03tTZWH34VTVIEMvrtsPFKFAe8SyAnUgVe4ojn8za/LGtPp9bIJNf4q+uUmk3APmsv9thquNQG1Z8nF1z++C06BjDElvPHCCf6/Y8fQoYdACo1fPRIO6UWhZ4HQIuMJk1AtqXVheQxAgMBAAECggEAYl/egXehGYQmWM1jts6SRi2O4TGicUzm3NBn/boT+zwNxp5atm1eSkJ3//ddk7zPlmUOBrardsX//5hGbsuGr4yYWutLi7vGVQ8AQ00P4aHbPvjpnExZgk/9rHjco2aQkRGv1HGqzz9LKAGWv8VNltg24REj9EPZqejadLzvuzb4Zyg1Snp4AJrs5MKTOiG7451aBM69ceJuYPDF1VeW5lcM9Cw/ZszmpalldQxH5VZKcgdCgxr65AJtz67c5Ujko3so0gJ8tTyBturQR3AMLKGyaIkIR9Pk8JJb39qN5Eu67ILPPVCZz+J8EBlnlvztpSATx9j7w+PcNHkHHqM1bQKBgQD/LDxVwCA4p47ySvVZFMJ3aj0cAw8/UUdMss18p+lc69uILooBAN83cjnU4arHm7U+IIfQv9RBMtcDXk1f5Ttf9WqmUa6iJoRJMG1r7clMBCQitp54rwlV8U8Ml/BNPpTTlRzJ4vbTocZJWjZaqBQrkCDzzVC+MmFo8mxUGcclJwKBgQDB9mlAP9dR+SeKPqtplZ7WR1YhgpQ+AuFhxb4iROG8bUEnPq2IK0qIe/+bZtKy47XmDF9l6WuY436AaHZVqOo8sNhy0IaDvJnzqkYM2wVKXDTWM/ezEvBkufo15g0laNUbqgcIpxbw3g48YCC3m75jm24P6CREy7gxjjDN9nhy5wKBgHJ4jbiKL+o6gM4BAn+r6HFZn4BpfvWQqMOnkrdRPEcLJ7i36AD7OyfMflUZx1s0ZuIwBb2H0NhOzlYcf+l9FF2/4tAp5IWpcb7DktLQIXWQhgVslzrcivua/TNRFeQRJwKj6f5HayMVd7aAWdHhhOrmBWJZwNcSEtDZr+8qgUQRAoGAVU6jC/cIJK2IjmzxxwZfTa8iWqqruHpb1jy6WygggFctCK2wdOtVZKHk0dxvXu532i4m5xO6lwu3X5KSOSqJmxfMmvLLtrUEoP6KwKyzrvjO5EcG8mOqByMkwP/lWEa7M+ecIFoc5ONFL5i3ywlcu0JpLeXTzc4rvN/k5zE32JECgYEA6NjMM3fISaW+y0xMwXT/3pbnvs+HPLVSiWhnyHgGSq1ocxayRIxqnmxDDXPBz+2OkRbSe8UN2197n2EzX0KxKqrud0RxduwxZscs88yQLZwVCyvem3UM88eTdTnsc5iiliMJfqlLi0p+gOGG56rwedVfYyOl6KZRC11MiLGFLeY=";
    // 服务器异步通知页面路径 需http://或者https://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static String NOTIFY_URL        = "http://47.98.156.204/healthapi/pay/alipay/notifyCallBack";
    // 页面跳转同步通知页面路径 需http://或者https://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问 商户可以自定义同步跳转地址
    public static String RETURN_URL        = "http://47.98.156.204/healthapi/pay/alipay_success.html";
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
