package com.kanglian.healthcare.back.constants;

import com.kanglian.healthcare.util.PropConfig;

public final class AlipayConfig {

    // 商户appid
    public static String APPID             = "2018042660061222";
    // 私钥 pkcs8格式的
    public static String APP_PRIVATE_KEY   =
            "MIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQC7jNcuwQlD0lxgvnYZs81m/3btwxUpJaYcV1KhxbdN1XJaQnetX+S707hoCuxm0gOxZ3GJmxk1Lw30+HwozIRnTSclDuatqu+53DQawxcCWxePh/JVHmOjq1cyWbySAJLlgZGtvxx5G+iLNIapkrdqvBe5APpmciVTR/lt+dVZ1DvOhBUc/boaurR01KLp5SO5hn2eGAQMOo+d0AzHdsuBNUzWbIgm2tVaCdIhhbbBAkYcXTNHtC9EeNBj58vjC7UvRnk8K5ctCyeFTl/bNzVfoF1VgAQyIz3ee1ibCl1S3wHRKO+yXmSMWhIIfLp8SiJTzUTPIor274hOl8btIx4XAgMBAAECggEBAJmnHXQUej6yTTSFVcRg5lsH2KuaM+/uBFUbxXr7vN1rjbAc3GeSNJcdMesrZ9olHRluojoWc2R7FHgqS64xQBhRwUw/FQYbESO4IIf8ZRFtB7RzsDnEF+WzLlD16+PBzJdyu6MY+37NOosxkVHD/bjYOKc9AeiEg8fjI5rSH9TjAJE6RAqzyC8+iW/rd+NMnd9GdZRZqYxblMp9CWXe663IyAOPKExN8MmGq7NAeT2rd81wM6nmu1iI+YzzFqclE8t2q6meBrcnH+VIgAGWmkKCKGmUkBIBBl63lIJTdNEDyCMlndMyj+g0mIFWGLC58h64VWNjw9VWi3tpP+6fYmECgYEA4ndVZQKnSA1rScsevNHApUo70Ok+eK1+H9KjsGjkny4Dgprs1yGJsFMd3HPjq1SUR0r+DpbeiPD00uiq+PIz3OuIRUVdbnJhGBk3pSO4k4H95MPlnPA4H3P1qgPqWncYu/HaEBjUT4TCCFIIBak3AnWq8ZBOcgYCfsJe2mTB9WUCgYEA1AJH0YMPu5jnA8JYz4ALAintGsZwAuvSzWgupmReBgts4n1z7MZtQE1bMwXbqHM3QKFPk1XBbX71oPSH5Gs/MxnK3uv7y9M4TUUasKzSNJRSJVwo0sCu3pCs6o/W/VNQNp65CouiXV/pqV9EPEIxdDzosJpZKihbLDX5+KTWe8sCgYA/EFAdYtHGhMYdtIyItDhbTwK9OFaHMHjwz615kJwpn5ymoBHYDqTTDzI/k54Qterct4tr8KNjqwXiPZxu2rOKj6RQ9fIAsaVMGqmeOQZLPWXAcLTeMR7P03DTuWdnbcbbf4vj86YbxrJpsr49JVQGQVIrFq5hnj7PobzcQDaq6QKBgQDJJjDF07fFodjtdCOXHEFVmDniQYxc+SpUwRghvvCGg6iXa7flWo6lh7+TtWcnd8lghKxfeem/3clnIRsd2bNo5Hva7cNFK+4rQfmEzzPRtzgJQltpOm+vs0yccETmrXlZmVavbrRqK2oizMSM97C8PF350TfwC8IVPFEjXCIz6QKBgQC8HAKMTI9SrujmNmBe9ouFxPRVsAFbY3+svnGLoIvIWDYZZovweacKpoBKze7ON+ZVvVATp1vVPM6cUO6o4JumSXGOuKi00kjwVaO87lyqHR5odcB0WwybYcA49dpxdrWz38YElfjQcXpLoVOdOV1ykvsiMPOYHbKN0A7QD4kPAg==";
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
