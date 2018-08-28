package com.kanglian.healthcare.back.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.easyway.business.framework.pojo.QueryPojo;
import com.easyway.business.framework.springmvc.result.ResultBody;
import com.easyway.business.framework.springmvc.result.ResultUtil;
import com.easyway.business.framework.util.StringUtil;
import com.kanglian.healthcare.authorization.annotation.Authorization;
import com.kanglian.healthcare.authorization.annotation.CurrentUser;
import com.kanglian.healthcare.back.constants.Constants;
import com.kanglian.healthcare.back.dal.pojo.User;
import com.kanglian.healthcare.back.jpush.JPushService;
import com.kanglian.healthcare.back.jpush.PushModel;
import com.kanglian.healthcare.exception.InvalidParamException;
import com.kanglian.healthcare.util.LogUtil;

/**
 * 极光推送
 * 
 * @author xl.liu
 */
@Controller
@RequestMapping(value = "/push")
public class PushController extends BaseController {

    @Autowired
    private JPushService jPushService;

    @Authorization
    @RequestMapping(value = "/pushmsg", method = RequestMethod.POST)
    public @ResponseBody ResultBody pushmsg(@CurrentUser User user, @RequestBody PushmsgQuery query) throws Exception {
        String receiveId = query.getReceiveId();
        if (StringUtil.isEmpty(receiveId) || "null".equals(receiveId)) {
            throw new InvalidParamException("receiveId");
        }

        String content = query.getContent();
        if (StringUtil.isEmpty(content) || "null".equals(content)) {
            throw new InvalidParamException("content");
        }

        PushModel pushModel = new PushModel();
        pushModel.setTitle("即时聊天");
        pushModel.setContent(content);
        pushModel.addAlias(receiveId);
        pushModel.addParam(Constants.TAG_ID, Constants.TAG_CHAT_ID);
        jPushService.pushToAndroid(pushModel);
        LogUtil.getMessageLogger().info("【即时聊天】发送用户userId={}，接收用户receiveUserId={}, 内容={}",
                new Object[] {user.getUserId(), receiveId, content});
        return ResultUtil.success();
    }

    public static class PushmsgQuery extends QueryPojo {
        private String receiveId;
        private String content;
        public String getReceiveId() {
            return receiveId;
        }
        public void setReceiveId(String receiveId) {
            this.receiveId = receiveId;
        }
        public String getContent() {
            return content;
        }
        public void setContent(String content) {
            this.content = content;
        }
    }
}
