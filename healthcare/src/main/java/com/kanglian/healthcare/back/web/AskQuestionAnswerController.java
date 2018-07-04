package com.kanglian.healthcare.back.web;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.easyway.business.framework.mybatis.annotion.SingleValue;
import com.easyway.business.framework.pojo.Grid;
import com.easyway.business.framework.springmvc.controller.CrudController;
import com.easyway.business.framework.springmvc.result.ResultBody;
import com.easyway.business.framework.springmvc.result.ResultUtil;
import com.easyway.business.framework.util.CollectionUtil;
import com.easyway.business.framework.util.DateUtil;
import com.easyway.business.framework.util.StringUtil;
import com.kanglian.healthcare.authorization.annotation.Authorization;
import com.kanglian.healthcare.back.dal.pojo.AskQuestionAnswer;
import com.kanglian.healthcare.back.service.AskQuestionAnswerBo;
import com.kanglian.healthcare.back.web.UserController.UserQuery;
import com.kanglian.healthcare.exception.InvalidParamException;

/**
 * 寻医问诊问题与解答
 * 
 * @author xl.liu
 */
@Authorization
@RestController
@RequestMapping(value = "/askQuestion")
public class AskQuestionAnswerController extends CrudController<AskQuestionAnswer, AskQuestionAnswerBo> {

    @GetMapping("/reply/list")
    public ResultBody list(UserQuery query) throws Exception {
        if (StringUtil.isEmpty(query.getUserId())) {
            throw new InvalidParamException("userId");
        }
        return super.list(query);
    }
    
    @GetMapping("/reply/info")
    public ResultBody info(String messageId) throws Exception {
        if (StringUtil.isEmpty(messageId)) {
            throw new InvalidParamException("messageId");
        }
        
        List<AskQuestionAnswer> resultList = this.bo.getListByUserId(messageId);
        if (CollectionUtil.isNotEmpty(resultList)) {
            AskQuestionAnswer entity = resultList.get(0);// 获取最新一条，未回答三天后打款
            if (StringUtil.isEmpty(entity.getAnswer()) 
                    && entity.getLastUpdateDtime() == null) {
                // 默认点开第一次就算应答
                entity.setLastUpdateDtime(DateUtil.currentDate());
                this.bo.update(entity);
            }
        }
        return ResultUtil.success(resultList);
    }
    
    public static class AskQuestionQuery extends Grid {

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
