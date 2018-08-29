package com.kanglian.healthcare.back.constant;

/**
 * 支付方式
 * 
 * @author xl.liu
 */
public enum PaymentType {
    /**
     * 支付宝支付
     */
    ALIPAY("alipay", "支付宝"),
    /**
     * 微信支付
     */
    WXPAY("wxpay", "微信"),
    /**
     * 医保支付
     */
    YBPAY("ybpay", "医保"),
    /**
     * 账户余额支付
     */
    SPAY("spay", "账户");

    // 成员变量
    private String name;
    private String value;

    // 构造方法
    private PaymentType(String name, String value) {
        this.name = name;
        this.value = value;
    }

    // 普通方法
    public static String getValue(String name) {
        for (PaymentType c : PaymentType.values()) {
            if (c.getName().equals(name)) {
                return c.value;
            }
        }
        return null;
    }
    
    // get set 方法
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
