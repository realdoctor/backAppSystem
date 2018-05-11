package com.kanglian.healthcare.back.web;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.easyway.business.framework.mybatis.annotion.SingleValue;
import com.easyway.business.framework.pojo.Grid;
import com.easyway.business.framework.springmvc.controller.CrudController;
import com.easyway.business.framework.springmvc.result.ResultBody;
import com.easyway.business.framework.springmvc.result.ResultUtil;
import com.easyway.business.framework.util.StringUtil;
import com.kanglian.healthcare.back.dal.pojo.NoticeMessage;
import com.kanglian.healthcare.back.service.NoticeMessageBo;
import com.kanglian.healthcare.back.service.UserBo;

/**
 * 消息提醒
 * 
 * @author xl.liu
 */
@RestController
@RequestMapping(value = "/message")
public class NoticeMessageController extends CrudController<NoticeMessage, NoticeMessageBo> {

    @Autowired
    private UserBo userBo;
    /**
     * 最近消息提醒一览
     * 
     * @param query
     * @return
     * @throws Exception
     */
    @GetMapping
    public ResultBody list(NoticeMessageQuery query) throws Exception {
        if (StringUtil.isEmpty(query.getUserId())) {
            return ResultUtil.error("用户未登录！");
        }
        if (!userBo.ifExist(Long.valueOf(query.getUserId()))) {
            return ResultUtil.error("用户不存在");
        }
        return ResultUtil.success(this.bo.query(query));
    }

    /**
     * 消息提醒列表
     * 
     * @param query
     * @return
     * @throws Exception
     */
    @GetMapping("/{userId}")
    public ResultBody noticeMessageList(@PathVariable("userId") String userId) throws Exception {
        if (StringUtil.isEmpty(userId)) {
            return ResultUtil.error("用户未登录！");
        }
        if (!userBo.ifExist(Long.valueOf(userId))) {
            return ResultUtil.error("用户不存在");
        }
        
        List<NoticeMessage> list = this.bo.queryForList(Long.valueOf(userId));
        return ResultUtil.success(list);
    }

    public static class NoticeMessageQuery extends Grid {

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
