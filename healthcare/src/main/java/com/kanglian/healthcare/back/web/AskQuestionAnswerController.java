package com.kanglian.healthcare.back.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.alibaba.fastjson.JSONObject;
import com.easyway.business.framework.json.JsonClothProcessor;
import com.easyway.business.framework.mybatis.query.ConditionQuery;
import com.easyway.business.framework.mybatis.query.condition.SingleValueCondition;
import com.easyway.business.framework.mybatis.query.condition.WithoutValueCondition;
import com.easyway.business.framework.pojo.Grid;
import com.easyway.business.framework.springmvc.controller.CrudController;
import com.easyway.business.framework.springmvc.result.ResultBody;
import com.easyway.business.framework.springmvc.result.ResultUtil;
import com.easyway.business.framework.util.DateUtil;
import com.easyway.business.framework.util.StringUtil;
import com.kanglian.healthcare.authorization.annotation.Authorization;
import com.kanglian.healthcare.back.dal.pojo.AskQuestionAnswer;
import com.kanglian.healthcare.back.dal.pojo.User;
import com.kanglian.healthcare.back.dal.pojo.UserInfo;
import com.kanglian.healthcare.back.service.AskQuestionAnswerBo;
import com.kanglian.healthcare.back.service.UserInfoBo;
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

    @Autowired
    private UserInfoBo userInfoBo;
    
    /**
     * 上传病历问题列表
     * 
     * @param query
     * @return
     * @throws Exception
     */
    @GetMapping("/reply/list")
    public ResultBody list(AskQuestionQuery query) throws Exception {
        if (StringUtil.isEmpty(query.getUserId())) {
            throw new InvalidParamException("userId");
        }
        
        return super.list(query, new JsonClothProcessor() {

            @Override
            public JSONObject wearCloth(Object pojo, JSONObject jsonObject) {
                AskQuestionAnswer askQuestionAnswer = (AskQuestionAnswer)pojo;
                try {
                    User user = new User();
                    user.setUserId(Long.valueOf(askQuestionAnswer.getUserId()));
                    UserInfo userInfo = userInfoBo.getUserInfo(user);
                    jsonObject.put("userInfo", userInfoBo.reformUserInfo(userInfo));
                } catch (Exception e) {
                    // TODO: handle exception
                }
                return jsonObject;
            }
            
        });
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
     * 患者医生回复问诊
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
            return ResultUtil.error("请求回复不存在");
        }
        if (askQuestionAnswer != null) {
            askQuestionAnswer.setAnswer(content);
            if (StringUtil.isNotEmpty(content)) {
                askQuestionAnswer.setLastUpdateDtime(DateUtil.currentDate());
                this.bo.update(askQuestionAnswer);
            } else {
                // 医生未回答三天后退款。如果医生回复了，患者发问第二次问题，上一次的问题才结束
                if (askQuestionAnswer.getLastUpdateDtime() != null
                        && StringUtil.isNotEmpty(askQuestionAnswer.getAnswer())) {
                    askQuestionAnswer.setLastUpdateDtime(DateUtil.currentDate());
                    this.bo.update(askQuestionAnswer);
                }
                
                // 三次问诊机会已用完，则更新为已结束。
                if (StringUtil.isNotEmpty(query.getRetryNum()) && "0".equals(query.getRetryNum())) {
                    askQuestionAnswer.setStatus("2");
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
        
        return ResultUtil.success(this.bo.getListByMessageId(messageId));
    }
    
//    /**
//     * 回复内容详情
//     * 
//     * @param messageId
//     * @return
//     * @throws Exception
//     */
//    @GetMapping("/reply/info")
//    public ResultBody info(String messageId) throws Exception {
//        if (StringUtil.isEmpty(messageId)) {
//            throw new InvalidParamException("messageId");
//        }
//        
//        final List<AskQuestionAnswer> resultList = this.bo.getListByMessageId(messageId);
//        if (CollectionUtil.isNotEmpty(resultList)) {
//            AskQuestionAnswer entity = resultList.get(0);// 获取最新一条，未回答三天后打款
//            if (StringUtil.isEmpty(entity.getAnswer()) 
//                    && entity.getLastUpdateDtime() == null) {
//                // 默认点开第一次就算应答
//                entity.setLastUpdateDtime(DateUtil.currentDate());
//                this.bo.update(entity);
//            }
//        }
//        
//        List<JSONObject> jsonObj = ResultUtil.wearCloth(resultList, new JsonClothProcessor() {
//
//            private AskQuestionAnswer maxQuestionQuestionAnswer = Collections.max(resultList);
//
//            @Override
//            public JSONObject wearCloth(Object pojo, JSONObject jsonObject) {
//                try {
//                    // 取同一，主要是为了。在回复时更新
//                    if (maxQuestionQuestionAnswer.getId() != null) {
//                        jsonObject.put("questionId", maxQuestionQuestionAnswer.getId());
//                    }
//                } catch (Exception e) {
//                    // TODO: handle exception
//                }
//                return jsonObject;
//            }
//
//        });
//        return ResultUtil.success(jsonObj);
//    }
    
    public static class AskQuestionQuery extends Grid {

        private String userId;
        private String roleId;
        private String questionId;
        private String content;
        // 1|进行中，2|已完结
        private String type;
        private String retryNum;

        public String getRetryNum() {
            return retryNum;
        }

        public void setRetryNum(String retryNum) {
            this.retryNum = retryNum;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getRoleId() {
            return roleId;
        }

        public void setRoleId(String roleId) {
            this.roleId = roleId;
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

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        @Override
        public ConditionQuery buildConditionQuery() {
            ConditionQuery query = super.buildConditionQuery();
            if ("1".equals(getRoleId())) {// 医生1
                query.addSingleValueCondition(new SingleValueCondition("to_user", getUserId()));
            } else {// 普通用户0
                query.addSingleValueCondition(new SingleValueCondition("user_id", getUserId()));
            }
            if ("2".equals(getType())) {// 已结束
                query.addParam("type", "2");
                query.addWithoutValueCondition(
                        new WithoutValueCondition(" datediff(now(),t.add_time)>3 or t.status=2 "));
            } else if ("1".equals(getType())) {// 进行中
                query.addParam("type", "1");
                query.addWithoutValueCondition(new WithoutValueCondition(
                        " datediff(now(),t.add_time)<=3 and t.status=1 "));
            }
            return query;
        }
    }
}
