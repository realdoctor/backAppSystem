package com.kanglian.healthcare.back.constants;

/**
 * 上传类型：1，视频、2，图片、3，文件
 * 
 * @author xl.liu
 */
public enum UploadType {

    /**
     * 视频type=1
     */
    VIDEOS("视频", 1),
    /**
     * 图片type=2
     */
    IMAGES("图片", 2),
    /**
     * 文件type=3
     */
    FILES("文件", 3);

    // 成员变量
    private String name;
    private int    value;

    // 构造方法
    private UploadType(String name, int value) {
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

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

}
