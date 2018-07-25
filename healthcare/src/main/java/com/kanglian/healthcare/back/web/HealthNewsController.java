package com.kanglian.healthcare.back.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.alibaba.fastjson.JSONObject;
import com.easyway.business.framework.json.JsonClothProcessor;
import com.easyway.business.framework.pojo.Grid;
import com.easyway.business.framework.springmvc.controller.CrudController;
import com.easyway.business.framework.springmvc.result.ResultBody;
import com.easyway.business.framework.springmvc.result.ResultUtil;
import com.easyway.business.framework.util.CollectionUtil;
import com.easyway.business.framework.util.StringUtil;
import com.kanglian.healthcare.back.dal.pojo.HealthNews;
import com.kanglian.healthcare.back.service.HealthNewsBo;
import com.kanglian.healthcare.back.service.HealthNewsFocusBo;
import com.kanglian.healthcare.back.service.NewsAdBo;
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
    @Autowired
    private NewsAdBo newsAdBo;
    
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

            List<String> focusNewsList = getNewsIdsByUserId(query.getUserId());

            private List<String> getNewsIdsByUserId(String userId) {
                if (StringUtils.isEmpty(userId)) {
                    return null;
                }

                List<Map<String, Object>> focusNewsList =
                        healthNewsFocusBo.getListByUserId(Integer.valueOf(query.getUserId()));
                List<String> newsIdList = new ArrayList<String>();
                for (Map<String, Object> newsFocus : focusNewsList) {
                    newsIdList.add(newsFocus.get("newsId") + "");
                }
                return newsIdList;
            }
            
            @Override
            public JSONObject wearCloth(Object pojo, JSONObject jsonObject) {
                HealthNews healthNews = (HealthNews) pojo;
                try {
                    if (StringUtil.isEmpty(query.getUserId())) {
                        jsonObject.put("focusFlag", "0");
                    } else {
                        if (CollectionUtil.isEmpty(focusNewsList)) {
                            jsonObject.put("focusFlag", "0");
                        } else {
                            if (healthNews.getNewsId() != null
                                    && focusNewsList.contains(healthNews.getNewsId() + "")) {
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

    @GetMapping("/ad/list")
    public ResultBody newsAd() throws Exception {
        return ResultUtil.success(newsAdBo.queryAll());
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
