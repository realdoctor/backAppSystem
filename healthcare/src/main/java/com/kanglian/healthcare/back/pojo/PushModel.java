package com.kanglian.healthcare.back.pojo;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import com.easyway.business.framework.pojo.ToString;

public class PushModel extends ToString {

    /**
     * 
     */
    private static final long   serialVersionUID = 1L;

    /**
     * 推送标题
     */
    private String              title;

    /**
     * 推送内容
     */
    private String              content;

    /**
     * 跳转地址
     */
    private String              url;

    private List<String>        tags             = new LinkedList<String>();

    private List<String>        alias            = new LinkedList<String>();

    private List<String>        regIds           = new LinkedList<String>();

    /**
     * 透传字段
     */
    private Map<String, String> extrasMap        = new LinkedHashMap<String, String>();

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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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

    public void addTags(String tag) {
        tags.add(tag);
    }

    public void addAllTags(List<String> tags) {
        tags.addAll(tags);
    }

    public void addAlias(String alias) {
        this.alias.add(alias);
    }

    public void addAllAlias(List<String> alias) {
        this.alias.addAll(alias);
    }

    public void addRegIds(String regIds) {
        this.regIds.add(regIds);
    }

    public void addAllRegIds(List<String> regIds) {
        this.regIds.addAll(regIds);
    }

    public void addParam(String key, String value) {
        this.extrasMap.put(key, value);
    }

    public void addAllParam(Map<String, String> map) {
        this.extrasMap.putAll(map);
    }
}
