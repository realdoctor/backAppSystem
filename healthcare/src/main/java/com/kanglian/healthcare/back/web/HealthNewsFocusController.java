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
import com.kanglian.healthcare.authorization.annotation.Authorization;
import com.kanglian.healthcare.authorization.annotation.CurrentUser;
import com.kanglian.healthcare.back.pojo.HealthNewsFocus;
import com.kanglian.healthcare.back.pojo.User;
import com.kanglian.healthcare.back.service.HealthNewsFocusBo;
import com.kanglian.healthcare.exception.InvalidParamException;

@Authorization
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
    public ResultBody newsFocusList(@CurrentUser User user, NewsFocusQuery query) throws Exception {
        query.setUserId(String.valueOf(user.getUserId()));
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
    public ResultBody newsFocus(@CurrentUser User user, @RequestBody HealthNewsFocus healthNewsFocus) throws Exception {
        healthNewsFocus.setUserId(user.getUserId().intValue());
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
    public ResultBody newsFocusOff(@CurrentUser User user, @RequestBody HealthNewsFocus healthNewsFocus) throws Exception {
        healthNewsFocus.setUserId(user.getUserId().intValue());
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
