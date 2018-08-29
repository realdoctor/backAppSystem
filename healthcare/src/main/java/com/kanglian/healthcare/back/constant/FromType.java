package com.kanglian.healthcare.back.constant;

/**
 * 支付来源
 * 
 * @author xl.liu
 */
public enum FromType {

    /**
     * 1，聊天-复诊支付
     */
    CHAT("聊天支付", "1"), 
    /**
     * 2，问诊-复诊支付
     */
    QUESTION("问诊支付", "2"),
    /**
     * 3，视频支付-患者教育
     */
    VIDEO("视频支付", "3"),
    /**
     * 4，图文支付-患者教育
     */
    IMAGE_TEXT("图文支付", "4"),
    /**
     * 5，资讯支付
     */
    INFORMATION("资讯支付", "5"),
    /**
     * 6，商城支付
     */
    GOODS("商城支付", "6");

    // 成员变量
    private String name;
    private String value;

    // 构造方法
    private FromType(String name, String value) {
        this.name = name;
        this.value = value;
    }

    // 普通方法
    public static String getName(String value) {
        for (FromType c : FromType.values()) {
            if (c.getValue().equals(value)) {
                return c.name;
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
