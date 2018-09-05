package com.kanglian.healthcare.back.constant;

public enum CashType {

    /**
     * 冻结
     */
    FREEZE("freeze", "冻结"), 
    /**
     * 待收金额
     */
    WAIT_INTEREST("wait_interest", "待收金额"),
    /**
     * 扣费
     */
    INVEST("invest", "扣费"),
    /**
     * 充值
     */
    RECHARGE("recharge", "充值"),
    /**
     * 提现申请
     */
    CASH_FROST("cash_frost", "提现申请"),
    /**
     * 提现成功
     */
    CASH_SUCCESS("cash_success", "提现成功");

    // 成员变量
    private String name;
    private String value;

    // 构造方法
    private CashType(String name, String value) {
        this.name = name;
        this.value = value;
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
