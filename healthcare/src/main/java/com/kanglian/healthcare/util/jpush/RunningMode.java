package com.kanglian.healthcare.util.jpush;

public enum RunningMode {

    DEV("开发环境"), PRODUCT("生产环境");

    // 成员变量
    private String name;

    // 构造方法
    private RunningMode(String name) {
        this.name = name;
    }

    public static RunningMode getValue(String runningMode) {
        for (RunningMode c : RunningMode.values()) {
            if (c.name().equalsIgnoreCase(runningMode)) {
                return c;
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
}
