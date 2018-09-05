package com.kanglian.healthcare.back.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.alibaba.fastjson.JSONObject;
import com.easyway.business.framework.json.JsonClothProcessor;
import com.easyway.business.framework.mybatis.annotion.SingleValue;
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
import com.kanglian.healthcare.back.constant.ApiMapping;
import com.kanglian.healthcare.back.constant.Constants;
import com.kanglian.healthcare.back.constant.OperateStatus;
import com.kanglian.healthcare.back.pojo.AskQuestionAnswer;
import com.kanglian.healthcare.back.pojo.PushModel;
import com.kanglian.healthcare.back.pojo.User;
import com.kanglian.healthcare.back.service.AskQuestionAnswerBo;
import com.kanglian.healthcare.back.service.PushService;
import com.kanglian.healthcare.back.service.UserBo;
import com.kanglian.healthcare.exception.InvalidParamException;

/**
 * 我的复诊-图文问诊
 * 
 * @author xl.liu
 */
@Authorization
@RestController
public class AskQuestionAnswerController extends CrudController<AskQuestionAnswer, AskQuestionAnswerBo> {

    @Autowired
    private UserBo       userBo;
    @Autowired
    private PushService jPushService;
    
    /**
     * 我的复诊-患者病历列表[患者端]
     * 
     * @param query
     * @return
     * @throws Exception
     */
    @GetMapping(ApiMapping.ASKQUESTION_REPLY_DOCTORLIST)
    public ResultBody doctorReplyList(AskQuestionQuery query) throws Exception {
        if (StringUtil.isEmpty(query.getUserId())) {
            throw new InvalidParamException("userId");
        }
        if (StringUtil.isEmpty(query.getStatus())) {
            throw new InvalidParamException("status");
        }
        query.setRoleId("0");
        logger.info("[我的复诊-患者]患者管理列表，roleId=" + query.getRoleId());
        return super.list(query, new JsonClothProcessor() {

            @Override
            public JSONObject wearCloth(Object pojo, JSONObject jsonObject) {
                AskQuestionAnswer askQuestionAnswer = (AskQuestionAnswer)pojo;
                try {
                    if (StringUtil.isNotEmpty(askQuestionAnswer.getPatientImageUrl())) {
                        jsonObject.put("patientImageUrl", Constants.getStaticUrl().concat(askQuestionAnswer.getPatientImageUrl()));
                    }
                    if (StringUtil.isNotEmpty(askQuestionAnswer.getDoctorImageUrl())) {
                        jsonObject.put("doctorImageUrl", Constants.getStaticUrl().concat(askQuestionAnswer.getDoctorImageUrl()));
                    }
                } catch (Exception e) {
                    // TODO: handle exception
                }
                return jsonObject;
            }
            
        });
    }
    
    /**
     * 我的复诊-患者管理列表[医生端]
     * 
     * @param query
     * @return
     * @throws Exception
     */
    @GetMapping(ApiMapping.ASKQUESTION_REPLY_PATIENTLIST)
    public ResultBody patientReplyList(AskQuestionQuery query) throws Exception {
        if (StringUtil.isEmpty(query.getUserId())) {
            throw new InvalidParamException("userId");
        }
        query.setRoleId("1");
        logger.info("[我的复诊-医生]患者管理列表，roleId=" + query.getRoleId());
        return ResultUtil.success(this.bo.frontList(query), new JsonClothProcessor() {

            @Override
            public JSONObject wearCloth(Object pojo, JSONObject jsonObject) {
                AskQuestionAnswer askQuestionAnswer = (AskQuestionAnswer)pojo;
                try {
                    if (StringUtil.isNotEmpty(askQuestionAnswer.getPatientImageUrl())) {
                        jsonObject.put("patientImageUrl", Constants.getStaticUrl().concat(askQuestionAnswer.getPatientImageUrl()));
                    }
                    if (StringUtil.isNotEmpty(askQuestionAnswer.getDoctorImageUrl())) {
                        jsonObject.put("doctorImageUrl", Constants.getStaticUrl().concat(askQuestionAnswer.getDoctorImageUrl()));
                    }
                } catch (Exception e) {
                    // TODO: handle exception
                }
                return jsonObject;
            }
            
        });
    }
    
    /**
     * 图文问诊回复
     * 
     * @param messageId
     * @return
     * @throws Exception
     */
    @PostMapping(ApiMapping.ASKQUESTION_REPLY)
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
        if (StringUtil.isNotEmpty(content)) {
            askQuestionAnswer.setAnswer(content);
            askQuestionAnswer.setLastUpdateDtime(DateUtil.currentDate());
            this.bo.update(askQuestionAnswer);
            try {
                User u = userBo.get(Long.valueOf(userId));
                PushModel pushModel = new PushModel();
                pushModel.setTitle("复诊");
                pushModel.setContent(u.getRealName() + "回复了【"+askQuestionAnswer.getTitle()+"】问题。");
                pushModel.addParam(Constants.TAG_ID, Constants.TAG_PATIENT_ID);
                pushModel.addAlias(askQuestionAnswer.getUserId()+"");
                jPushService.pushToAll(pushModel);
                logger.info("======================"+pushModel.getContent()+"患者用户userId="+askQuestionAnswer.getUserId());
            } catch (Exception e) {
                // TODO: handle exception
                logger.info("【回复问题】极光推送异常", e);
            }
        } else {
            // 医生未回答三天后退款
            // 如果医生回复了，患者查看记录。计时3天到期时间取[更新时间]，否则取[创建时间]进行判断
            // 患者发问第二次问题，上一次的问题就结束
            if (askQuestionAnswer.getLastUpdateDtime() != null
                    && StringUtil.isNotEmpty(askQuestionAnswer.getAnswer())) {
                askQuestionAnswer.setLastUpdateDtime(DateUtil.currentDate());
                this.bo.update(askQuestionAnswer);
            }
            
            // 三次问诊机会已用完，则更新为已结束。
            if (StringUtil.isNotEmpty(query.getRetryNum()) && "0".equals(query.getRetryNum())) {
                askQuestionAnswer.setStatus(OperateStatus.STRING_STATUS_FINISH);
                askQuestionAnswer.setLastUpdateDtime(DateUtil.currentDate());
                this.bo.update(askQuestionAnswer);
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
    @GetMapping(ApiMapping.ASKQUESTION_REPLY_INFO)
    public ResultBody info(String messageId) throws Exception {
        if (StringUtil.isEmpty(messageId)) {
            throw new InvalidParamException("messageId");
        }
        
        return ResultUtil.success(this.bo.getListByMessageId(messageId));
    }
    
    public static class AskQuestionQuery extends Grid {

        private String userId;
        private String roleId;
        // 1|进行中，2|已完结
        private String status;
        private String searchstr;
        private String questionId;
        private String content;
        private String retryNum;

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
        
        @SingleValue(tableAlias="t", column = "status", equal = "=")
        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getSearchstr() {
            return searchstr;
        }

        public void setSearchstr(String searchstr) {
            this.searchstr = searchstr;
        }
        
        public String getRetryNum() {
            return retryNum;
        }

        public void setRetryNum(String retryNum) {
            this.retryNum = retryNum;
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

        @Override
        public ConditionQuery buildConditionQuery() {
            ConditionQuery query = super.buildConditionQuery();
            if ("1".equals(getRoleId())) {// 医生1
                query.addParam("roleId", "1");
                query.addSingleValueCondition(new SingleValueCondition("to_user", getUserId()));
                if (StringUtil.isNotEmpty(getSearchstr())) {
                    query.addWithoutValueCondition(new WithoutValueCondition("u1.real_name LIKE '%"+getSearchstr()+"%'"));
                }
            } else {// 普通用户0
                query.addParam("roleId", "0");
                query.addSingleValueCondition(new SingleValueCondition("user_id", getUserId()));
            }
            return query;
        }
    }
}
