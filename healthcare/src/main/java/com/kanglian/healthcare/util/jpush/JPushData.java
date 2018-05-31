package com.kanglian.healthcare.util.jpush;

import java.util.List;
import java.util.Map;

public class JPushData {

    private String              title;    // 推送标题

    private String              content;  // 推荐内容

    private List<String>        tags;     // 推送分类标签(一次推送最多20个)

    private List<String>        alias;    // 推送别名(一次推送最多1000个)

    private List<String>        regIds;   // 极光推送的设备唯一性标识(一次推送最多1000个)

    private Map<String, String> extrasMap; // 透传字段，不会显示在通知栏。用户可以通过此字段来做一些定制需求，如特定的key传要指定跳转的页面（value）

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public List<String> getAlias() {
        return alias;
    }

    public void setAlias(List<String> alias) {
        this.alias = alias;
    }

    public List<String> getRegIds() {
        return regIds;
    }

    public void setRegIds(List<String> regIds) {
        this.regIds = regIds;
    }

    public Map<String, String> getExtrasMap() {
        return extrasMap;
    }

    public void setExtrasMap(Map<String, String> extrasMap) {
        this.extrasMap = extrasMap;
    }
}
