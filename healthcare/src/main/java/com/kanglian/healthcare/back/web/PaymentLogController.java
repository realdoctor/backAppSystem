package com.kanglian.healthcare.back.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.alibaba.fastjson.JSONObject;
import com.easyway.business.framework.json.JsonClothProcessor;
import com.easyway.business.framework.mybatis.annotion.SingleValue;
import com.easyway.business.framework.mybatis.query.ConditionQuery;
import com.easyway.business.framework.pojo.Grid;
import com.easyway.business.framework.springmvc.controller.CrudController;
import com.easyway.business.framework.springmvc.result.ResultBody;
import com.easyway.business.framework.util.StringUtil;
import com.kanglian.healthcare.authorization.annotation.Authorization;
import com.kanglian.healthcare.authorization.annotation.CurrentUser;
import com.kanglian.healthcare.back.constant.ApiMapping;
import com.kanglian.healthcare.back.constant.Constants;
import com.kanglian.healthcare.back.pojo.PaymentLog;
import com.kanglian.healthcare.back.pojo.User;
import com.kanglian.healthcare.back.service.PaymentLogBo;
import com.kanglian.healthcare.exception.InvalidParamException;

@Authorization
@RestController
public class PaymentLogController extends CrudController<PaymentLog, PaymentLogBo> {

    @GetMapping(ApiMapping.ACCOUNT_PAYMENT_LIST)
    public ResultBody list(@CurrentUser User user, PaymentIncomeQuery query) throws Exception {
        if (StringUtil.isEmpty(query.getUserId())) {
            throw new InvalidParamException("userId");
        }
        return super.list(query, new JsonClothProcessor() {

            final String domainUrl = Constants.getStaticUrl();

            @Override
            public JSONObject wearCloth(Object pojo, JSONObject jsonObject) {
                PaymentLog entity = (PaymentLog) pojo;
                try {
                    if (StringUtil.isNotEmpty(entity.getUserPicUrl())) {
                        jsonObject.put("userPicUrl", domainUrl.concat(entity.getUserPicUrl()));
                    }
                    if (StringUtil.isNotEmpty(entity.getToUserPicUrl())) {
                        jsonObject.put("toUserPicUrl", domainUrl.concat(entity.getToUserPicUrl()));
                    }
                } catch (Exception e) {
                    // TODO: handle exception
                }
                return jsonObject;
            }

        });
    }

    public static class PaymentIncomeQuery extends Grid {
        private String userId;
        // 1=支出，2=收入，0=退款
        private String payFlag;

        @SingleValue(column = "user_id", equal = "=")
        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        @SingleValue(column = "pay_flag", equal = "=")
        public String getPayFlag() {
            return payFlag;
        }

        public void setPayFlag(String payFlag) {
            this.payFlag = payFlag;
        }

        @Override
        public ConditionQuery buildConditionQuery() {
            ConditionQuery query = super.buildConditionQuery();
            return query;
        }

    }
}
