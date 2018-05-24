package com.kanglian.healthcare.back.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.easyway.business.framework.pojo.Grid;
import com.easyway.business.framework.springmvc.controller.CrudController;
import com.easyway.business.framework.springmvc.result.ResultBody;
import com.easyway.business.framework.springmvc.result.ResultUtil;
import com.easyway.business.framework.util.StringUtil;
import com.kanglian.healthcare.back.dal.pojo.HealthNews;
import com.kanglian.healthcare.back.service.HealthNewsBo;
import com.kanglian.healthcare.exception.InvalidParamException;

/**
 * 健康资讯
 * 
 * @author xl.liu
 */
@RestController
@RequestMapping(value = "/healthnews")
public class HealthNewsController extends CrudController<HealthNews, HealthNewsBo> {

    @GetMapping
    public ResultBody list(BaseQuery query) throws Exception {
        return super.list(query);
    }
    
    @GetMapping("/info")
    public ResultBody newsInfo(String newsId) throws Exception {
        if (StringUtil.isEmpty(newsId)) {
            throw new InvalidParamException("newsId");
        }
        return ResultUtil.success(this.bo.get(Integer.valueOf(newsId)));
    }
    
    public static class BaseQuery extends Grid {
        
    }
}
