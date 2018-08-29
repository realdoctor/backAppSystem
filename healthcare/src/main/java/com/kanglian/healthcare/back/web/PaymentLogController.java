package com.kanglian.healthcare.back.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
import com.easyway.business.framework.util.StringUtil;
import com.kanglian.healthcare.authorization.annotation.Authorization;
import com.kanglian.healthcare.authorization.annotation.CurrentUser;
import com.kanglian.healthcare.back.constant.Constants;
import com.kanglian.healthcare.back.pojo.PaymentLog;
import com.kanglian.healthcare.back.pojo.User;
import com.kanglian.healthcare.back.service.PaymentLogBo;
import com.kanglian.healthcare.exception.InvalidParamException;
import com.kanglian.healthcare.util.PropConfig;

@Authorization
@RestController
@RequestMapping(value = "/account")
public class PaymentLogController
        extends CrudController<PaymentLog, PaymentLogBo> {

    @GetMapping("/payment/list")
    public ResultBody list(@CurrentUser User user, PaymentIncomeQuery query) throws Exception {
        if (StringUtil.isEmpty(query.getUserId())) {
            throw new InvalidParamException("userId");
        }
        query.setUserId(user.getUserId()+"");
        query.setRoleId(user.getRoleId()+"");
        return super.list(query, new JsonClothProcessor() {

            String domainUrl = PropConfig.getInstance().getPropertyValue(Constants.STATIC_URL);

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
        private String roleId;
        private String mark;

//        @SingleValue(column = "user_id", equal = "=")
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
        
        @SingleValue(column = "mark", equal = "=")
        public String getMark() {
            return mark;
        }

        public void setMark(String mark) {
            this.mark = mark;
        }

        @Override
        public ConditionQuery buildConditionQuery() {
            ConditionQuery query = super.buildConditionQuery();
            if (StringUtil.isNotBlank(roleId)) {
                if ("1".equals(roleId)) {// 医生用户，查看收入
                    query.addWithoutValueCondition(new WithoutValueCondition(
                            " t.user_id = " + userId + " OR (t.to_user = " + userId + " AND t.mark !=2)"));
                } else {
                    query.addSingleValueCondition(new SingleValueCondition("user_id", userId));
                }
            }

            return query;
        }
        
    }
}
