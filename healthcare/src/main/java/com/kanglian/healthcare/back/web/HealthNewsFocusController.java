package com.kanglian.healthcare.back.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.easyway.business.framework.mybatis.annotion.SingleValue;
import com.easyway.business.framework.pojo.Grid;
import com.easyway.business.framework.springmvc.controller.CrudController;
import com.easyway.business.framework.springmvc.result.ResultBody;
import com.easyway.business.framework.springmvc.result.ResultUtil;
import com.easyway.business.framework.util.DateUtil;
import com.kanglian.healthcare.back.dal.pojo.HealthNewsFocus;
import com.kanglian.healthcare.back.service.HealthNewsFocusBo;
import com.kanglian.healthcare.exception.InvalidParamException;

@RestController
@RequestMapping(value = "/healthnews")
public class HealthNewsFocusController extends CrudController<HealthNewsFocus, HealthNewsFocusBo> {

    /**
     * 我的关注一览
     * 
     * @param query
     * @return
     * @throws Exception
     */
    @GetMapping("/myFocusList")
    public ResultBody myNewsFocusList(NewsFocusQuery query) throws Exception {
        if (query.getUserId() == null) {
            throw new InvalidParamException("userId");
        }
        return super.list(query);
    }

    /**
     * 资讯关注
     * 
     * @param newsId
     * @return
     * @throws Exception
     */
    @PostMapping("/focus")
    public ResultBody newsFocus(@RequestBody HealthNewsFocus healthNewsFocus) throws Exception {
        if (healthNewsFocus.getUserId() == null) {
            throw new InvalidParamException("userId");
        }
        if (healthNewsFocus.getNewsId() == null) {
            throw new InvalidParamException("newsId");
        }
        if (this.bo.get(healthNewsFocus) == null) {
            healthNewsFocus.setAddTime(DateUtil.currentDate());
            this.bo.save(healthNewsFocus);
        }
        return ResultUtil.success();
    }

    /**
     * 取消资讯关注
     * 
     * @param newsId
     * @return
     * @throws Exception
     */
    @PostMapping("/focus/off")
    public ResultBody newsFocusOff(@RequestBody HealthNewsFocus healthNewsFocus) throws Exception {
        if (healthNewsFocus.getUserId() == null) {
            throw new InvalidParamException("userId");
        }
        if (healthNewsFocus.getNewsId() == null) {
            throw new InvalidParamException("newsId");
        }
        this.bo.delete(healthNewsFocus);
        return ResultUtil.success();
    }
    
    public static class NewsFocusQuery extends Grid {
        private String userId;

        @SingleValue(column = "user_id", equal = "=")
        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }
    }
}
