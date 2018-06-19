package com.kanglian.healthcare.back.web;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.alibaba.fastjson.JSONObject;
import com.easyway.business.framework.json.JsonClothProcessor;
import com.easyway.business.framework.pojo.Grid;
import com.easyway.business.framework.springmvc.controller.CrudController;
import com.easyway.business.framework.springmvc.result.ResultBody;
import com.easyway.business.framework.springmvc.result.ResultUtil;
import com.easyway.business.framework.util.StringUtil;
import com.kanglian.healthcare.back.dal.pojo.HealthNews;
import com.kanglian.healthcare.back.dal.pojo.HealthNewsFocus;
import com.kanglian.healthcare.back.service.HealthNewsBo;
import com.kanglian.healthcare.back.service.HealthNewsFocusBo;
import com.kanglian.healthcare.exception.InvalidParamException;

/**
 * 健康资讯
 * 
 * @author xl.liu
 */
@RestController
@RequestMapping(value = "/healthnews")
public class HealthNewsController extends CrudController<HealthNews, HealthNewsBo> {

    @Autowired
    private HealthNewsFocusBo healthNewsFocusBo;
    
    /**
     * 资讯一览
     * 
     * @param query
     * @return
     * @throws Exception
     */
    @GetMapping
    public ResultBody list(final BaseQuery query) throws Exception {
        return super.list(query, new JsonClothProcessor() {

            @Override
            public JSONObject wearCloth(Object pojo, JSONObject jsonObject) {
                HealthNews healthNews = (HealthNews) pojo;
                try {
                    if (StringUtil.isBlank(query.getUserId())) {
                        jsonObject.put("focusFlag", "0");
                    } else {
                        List<HealthNewsFocus> focusNewsList = healthNewsFocusBo
                                .getListByUserId(Integer.valueOf(query.getUserId()));
                        jsonObject.put("focusFlag", "0");
                        for (HealthNewsFocus newsFocus : focusNewsList) {
                            if (newsFocus.getUserId() != null
                                    && newsFocus.getNewsId() == healthNews.getNewsId()) {
                                jsonObject.put("focusFlag", "1");
                            } else {
                                jsonObject.put("focusFlag", "0");
                            }
                        }
                    }
                } catch (Exception e) {
                    // TODO: handle exception
                }
                return jsonObject;
            }

        });
    }

    /**
     * 资讯详情
     * 
     * @param newsId
     * @return
     * @throws Exception
     */
    @GetMapping("/info")
    public ResultBody newsInfo(String newsId) throws Exception {
        if (StringUtil.isEmpty(newsId)) {
            throw new InvalidParamException("newsId");
        }
        return ResultUtil.success(this.bo.get(Integer.valueOf(newsId)));
    }

    public static class BaseQuery extends Grid {
        private String userId;

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }
    }
}
