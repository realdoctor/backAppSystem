package com.kanglian.healthcare.back.web;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import com.easyway.business.framework.springmvc.handler.CustomDateEditor;

public class BaseController {

    /**
     * 获取请求属性封装为Map类型
     * 
     * @param request
     * @return
     */
    @SuppressWarnings("rawtypes")
    protected Map<String, Object> getRequestMap(HttpServletRequest request) {
        Map<String, Object> conditions = new HashMap<String, Object>();
        Map map = request.getParameterMap();
        for (Object o : map.keySet()) {
            String key = (String) o;
            conditions.put(key, ((String[]) map.get(key))[0]);
        }
        return conditions;
    }

    @InitBinder
    protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) throws Exception {
        binder.registerCustomEditor(Date.class, new CustomDateEditor(null, true));
    }
}
