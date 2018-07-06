package com.kanglian.healthcare.back.web;

import java.util.Collections;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.alibaba.fastjson.JSONObject;
import com.easyway.business.framework.json.JsonClothProcessor;
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

    /**
     * 回复列表
     * 
     * @param query
     * @return
     * @throws Exception
     */
    @GetMapping("/reply/list")
    public ResultBody list(UserQuery query) throws Exception {
        if (StringUtil.isEmpty(query.getUserId())) {
            throw new InvalidParamException("userId");
        }
        return super.list(query);
    }
    
//    /**
//     * 回复内容
//     * 
//     * @param messageId
//     * @return
//     * @throws Exception
//     */
//    @PostMapping("/reply")
//    public ResultBody reply(@RequestBody AskQuestionQuery query) throws Exception {
//        String userId = query.getUserId();
//        String roleId = query.getRoleId();
//        String questionId = query.getMessageId();
//        String content = query.getContent();
//        if (StringUtil.isEmpty(userId)) {
//            throw new InvalidParamException("userId");
//        }
//        if (StringUtil.isEmpty(roleId)) {
//            throw new InvalidParamException("roleId");
//        }
//        if (StringUtil.isEmpty(content)) {
//            throw new InvalidParamException("content");
//        }
//        if (StringUtil.isEmpty(questionId)) {
//            throw new InvalidParamException("questionId");
//        }
//
//        AskQuestionAnswer askQuestionAnswer = this.bo.get(Long.valueOf(questionId));
//        if (askQuestionAnswer == null) {
//            return ResultUtil.error("请求回复非法");
//        }
//        if ("1".equals(roleId)) {// 医生回复，更新列表
//            if (askQuestionAnswer != null) {
//                askQuestionAnswer.setAnswer(content);
//                askQuestionAnswer.setLastUpdateDtime(DateUtil.currentDate());
//                this.bo.update(askQuestionAnswer);
//            }
//        } else {
//            // 患者回复，插入新问题
//            AskQuestionAnswer newAskQuestionAnswer = new AskQuestionAnswer();
//            newAskQuestionAnswer.setUserId(Integer.valueOf(userId));
//            newAskQuestionAnswer.setMessageId(askQuestionAnswer.getMessageId());
//            newAskQuestionAnswer.setToUser(askQuestionAnswer.getToUser());
//            newAskQuestionAnswer.setQuestion(content);
//            newAskQuestionAnswer.setAddTime(DateUtil.currentDate());
//            this.bo.save(newAskQuestionAnswer);
//        }
//        return ResultUtil.success();
//    }
    
    /**
     * 医生回复内容
     * 
     * @param messageId
     * @return
     * @throws Exception
     */
    @PostMapping("/reply")
    public ResultBody reply(@RequestBody AskQuestionQuery query) throws Exception {
        String userId = query.getUserId();
        String questionId = query.getQuestionId();
        String content = query.getContent();
        if (StringUtil.isEmpty(userId)) {
            throw new InvalidParamException("userId");
        }
        if (StringUtil.isEmpty(questionId)) {
            throw new InvalidParamException("questionId");
        }

        AskQuestionAnswer askQuestionAnswer = this.bo.get(Long.valueOf(questionId));
        if (askQuestionAnswer == null) {
            return ResultUtil.error("请求回复非法");
        }
        if (askQuestionAnswer != null) {
            askQuestionAnswer.setAnswer(content);
            askQuestionAnswer.setStatus("0");
            if (StringUtil.isNotEmpty(content)) {
                askQuestionAnswer.setLastUpdateDtime(DateUtil.currentDate());
                this.bo.update(askQuestionAnswer);
            } else {
                if (askQuestionAnswer.getLastUpdateDtime() == null) {// 点开第一次默认算回复，未回答三天后打款
                    askQuestionAnswer.setLastUpdateDtime(DateUtil.currentDate());
                    this.bo.update(askQuestionAnswer);
                }
            }
        }
        return ResultUtil.success();
    }
    
    /**
     * 回复内容详情
     * 
     * @param messageId
     * @return
     * @throws Exception
     */
    @GetMapping("/reply/info")
    public ResultBody info(String messageId) throws Exception {
        if (StringUtil.isEmpty(messageId)) {
            throw new InvalidParamException("messageId");
        }
        
        final List<AskQuestionAnswer> resultList = this.bo.getListByUserId(messageId);
        if (CollectionUtil.isNotEmpty(resultList)) {
            AskQuestionAnswer entity = resultList.get(0);// 获取最新一条，未回答三天后打款
            if (StringUtil.isEmpty(entity.getAnswer()) 
                    && entity.getLastUpdateDtime() == null) {
                // 默认点开第一次就算应答
                entity.setLastUpdateDtime(DateUtil.currentDate());
                this.bo.update(entity);
            }
        }
        
        List<JSONObject> jsonObj = ResultUtil.wearCloth(resultList, new JsonClothProcessor() {

            private AskQuestionAnswer maxQuestionQuestionAnswer = Collections.max(resultList);

            @Override
            public JSONObject wearCloth(Object pojo, JSONObject jsonObject) {
                try {
                    // 取同一，主要是为了。在回复时更新
                    if (maxQuestionQuestionAnswer.getId() != null) {
                        jsonObject.put("questionId", maxQuestionQuestionAnswer.getId());
                    }
                } catch (Exception e) {
                    // TODO: handle exception
                }
                return jsonObject;
            }

        });
        return ResultUtil.success(jsonObj);
    }
    
    public static class AskQuestionQuery extends Grid {

        private String userId;
        private String questionId;
        private String content;

        @SingleValue(column = "user_id", equal = "=")
        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getQuestionId() {
            return questionId;
        }

        public void setQuestionId(String questionId) {
            this.questionId = questionId;
        }
        
        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }
}
