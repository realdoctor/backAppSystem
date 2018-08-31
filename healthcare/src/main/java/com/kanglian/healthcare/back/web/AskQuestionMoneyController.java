package com.kanglian.healthcare.back.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.easyway.business.framework.springmvc.controller.CrudController;
import com.easyway.business.framework.springmvc.result.ResultBody;
import com.easyway.business.framework.springmvc.result.ResultUtil;
import com.easyway.business.framework.util.DateUtil;
import com.github.pagehelper.util.StringUtil;
import com.kanglian.healthcare.authorization.annotation.Authorization;
import com.kanglian.healthcare.authorization.annotation.CurrentUser;
import com.kanglian.healthcare.back.constant.ApiMapping;
import com.kanglian.healthcare.back.pojo.AskQuestionMoney;
import com.kanglian.healthcare.back.pojo.User;
import com.kanglian.healthcare.back.service.AskQuestionMoneyBo;
import com.kanglian.healthcare.exception.InvalidParamException;
import com.kanglian.healthcare.util.NumberUtil;

/**
 * 寻医问诊收费配置
 * 
 * @author xl.liu
 */
@Authorization
@RestController
public class AskQuestionMoneyController extends CrudController<AskQuestionMoney, AskQuestionMoneyBo> {

    /**
     * 获取问诊收费配置
     * 
     * @param user
     * @param askMoneyQuery
     * @return
     * @throws Exception
     */
    @GetMapping(ApiMapping.ASKQUESTION_GETMONEY)
    public ResultBody getAskQuestionMoney(@CurrentUser User user, String userId)
            throws Exception {
        if (StringUtil.isEmpty(userId)) {
            throw new InvalidParamException("doctorUserId");
        }
        return ResultUtil.success(this.bo.get(Long.valueOf(userId)));
    }

    /**
     * 设置问诊收费配置
     * 
     * @param user
     * @param askMoneyQuery
     * @return
     * @throws Exception
     */
    @PostMapping(ApiMapping.ASKQUESTION_SETMONEY)
    public ResultBody setAskQuestionMoney(@CurrentUser User user, @RequestBody AskQuestionMoney query)
            throws Exception {
        Double chatMoney = query.getChatMoney();
        Double questionMoney = query.getQuestionMoney();
        if (chatMoney == null) {
            throw new InvalidParamException("chatMoney");
        }
        if (questionMoney == null) {
            throw new InvalidParamException("questionMoney");
        }
        if (!NumberUtil.checkPrice(String.valueOf(chatMoney))) {
            return ResultUtil.error("聊天收费价格不正确");
        }
        if (!NumberUtil.checkPrice(String.valueOf(questionMoney))) {
            return ResultUtil.error("问题资讯收费价格不正确");
        }
        AskQuestionMoney askQuestionMoney = this.bo.get(user.getUserId());
        if (askQuestionMoney != null) {
            askQuestionMoney.setChatMoney(chatMoney);
            askQuestionMoney.setQuestionMoney(questionMoney);
            askQuestionMoney.setLastUpdateDtime(DateUtil.currentDate());
            this.bo.update(askQuestionMoney);
        } else {
            query.setUserId(user.getUserId());
            query.setAddTime(DateUtil.currentDate());
            this.bo.save(query);
        }
        return ResultUtil.success();
    }
}
