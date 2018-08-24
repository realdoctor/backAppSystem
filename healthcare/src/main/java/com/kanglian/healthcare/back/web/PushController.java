package com.kanglian.healthcare.back.web;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
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
    public @ResponseBody ResultBody pushmsg(@CurrentUser User user, HttpServletRequest request) throws Exception {
        String receiveUserId = request.getParameter("receiveUserId");
        if (StringUtil.isEmpty(receiveUserId) || "null".equals(receiveUserId)) {
            throw new InvalidParamException("receiveUserId");
        }

        String content = request.getParameter("content");
        if (StringUtil.isEmpty(content) || "null".equals(receiveUserId)) {
            throw new InvalidParamException("content");
        }

        PushModel pushModel = new PushModel();
        pushModel.setTitle("即时聊天");
        pushModel.setContent(content);
        pushModel.addParam(Constants.TAG_ID, Constants.TAG_DOCTOR_ID);
        pushModel.addAlias(receiveUserId);
        jPushService.pushToAndroid(pushModel);
        LogUtil.getMessageLogger().info("【即时聊天】发送用户userId={}，接收用户receiveUserId={}, 内容={}",
                new Object[] {user.getUserId(), receiveUserId, content});
        return ResultUtil.success();
    }

}
