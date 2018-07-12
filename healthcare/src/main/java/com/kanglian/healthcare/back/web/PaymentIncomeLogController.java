package com.kanglian.healthcare.back.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.alibaba.fastjson.JSONObject;
import com.easyway.business.framework.json.JsonClothProcessor;
import com.easyway.business.framework.mybatis.annotion.SingleValue;
import com.easyway.business.framework.pojo.Grid;
import com.easyway.business.framework.springmvc.controller.CrudController;
import com.easyway.business.framework.springmvc.result.ResultBody;
import com.easyway.business.framework.util.StringUtil;
import com.kanglian.healthcare.authorization.annotation.Authorization;
import com.kanglian.healthcare.back.constants.Constants;
import com.kanglian.healthcare.back.dal.pojo.PaymentIncomeLog;
import com.kanglian.healthcare.back.service.PaymentIncomeLogBo;
import com.kanglian.healthcare.exception.InvalidParamException;
import com.kanglian.healthcare.util.PropConfig;

@Authorization
@RestController
@RequestMapping(value = "/account")
public class PaymentIncomeLogController
        extends CrudController<PaymentIncomeLog, PaymentIncomeLogBo> {

    @GetMapping("/payment/list")
    public ResultBody list(PaymentIncomeQuery query) throws Exception {
        if (StringUtil.isEmpty(query.getUserId())) {
            throw new InvalidParamException("userId");
        }
        return super.list(query, new JsonClothProcessor() {

            String domainUrl = PropConfig.getInstance().getPropertyValue(Constants.STATIC_URL);

            @Override
            public JSONObject wearCloth(Object pojo, JSONObject jsonObject) {
                PaymentIncomeLog entity = (PaymentIncomeLog) pojo;
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
        private String mark;

        @SingleValue(column = "user_id", equal = "=")
        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        @SingleValue(column = "mark", equal = "=")
        public String getMark() {
            return mark;
        }

        public void setMark(String mark) {
            this.mark = mark;
        }
    }
}
