package com.kanglian.healthcare.util;

import java.util.HashMap;
import java.util.Map;
import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.PropertyPreFilter;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;

/**
 * JSON操作工具类
 * 
 * @author xl.liu
 */
public class JsonUtil {
    /**
     * Bean对象转JSON
     * 
     * @param object
     * @param dataFormatString
     * @return
     */
    public static String beanToJson(Object object, String dataFormatString) {
        if (object != null) {
            if (StringUtils.isEmpty(dataFormatString)) {
                return JSONObject.toJSONString(object, new SerializerFeature[] {SerializerFeature.WriteDateUseDateFormat});
            }
            return JSON.toJSONStringWithDateFormat(object, dataFormatString);
        } else {
            return null;
        }
    }

    /**
     * Object对象转JSON
     * 
     * @param object
     * @return
     */
    public static String object2Json(Object object) {
        if (object != null) {
            return JSON.toJSONString(object, new SerializerFeature[] {SerializerFeature.WriteDateUseDateFormat});
        } else {
            return null;
        }
    }

    /**
     * Bean对象转JSON
     * 
     * @param object
     * @return
     */
    public static String beanToJson(Object object) {
        if (object != null) {
            return JSON.toJSONString(object, new SerializerFeature[] {SerializerFeature.WriteDateUseDateFormat});
        } else {
            return null;
        }
    }

    /**
     * String转JSON字符串
     * 
     * @param key
     * @param value
     * @return
     */
    public static String stringToJson(String key, String value) {
        if (StringUtils.isEmpty(key) || StringUtils.isEmpty(value)) {
            return null;
        }
        Map<String, String> map = new HashMap<String, String>();
        map.put(key, value);
        return beanToJson(map, null);
    }

    /**
     * 将json字符串转换成对象
     * 
     * @param json
     * @param clazz
     * @return
     */
    public static <T> T jsonToBean(String json, Class<T> clazz) {
        if (StringUtils.isEmpty(json) || clazz == null) {
            return null;
        }
        return JSON.parseObject(json, clazz);
    }

    /**
     * json字符串转map
     * 
     * @param json
     * @return
     */
    @SuppressWarnings("unchecked")
    public static Map<String, Object> jsonToMap(String json) {
        if (StringUtils.isEmpty(json)) {
            return null;
        }
        return JSON.parseObject(json, Map.class);
    }

    /**
     * 过滤实体中的字段
     * 
     * @param src 需要过滤的对象，如 list,entity
     * @param clazz 实体的class
     * @param args 需要的字段，使用逗号分隔，如：time,desc
     * @return
     */
    public static String filterFieldsJson(Object src, Class<?> clazz, String... args) {
        PropertyPreFilter filter = new SimplePropertyPreFilter(clazz, args);
        return JSON.toJSONString(src, filter);
    }
}
