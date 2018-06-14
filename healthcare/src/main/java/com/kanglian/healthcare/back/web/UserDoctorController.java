package com.kanglian.healthcare.back.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.alibaba.fastjson.JSONObject;
import com.easyway.business.framework.json.JsonClothProcessor;
import com.easyway.business.framework.mybatis.annotion.SingleValue;
import com.easyway.business.framework.pojo.Grid;
import com.easyway.business.framework.springmvc.controller.CrudController;
import com.easyway.business.framework.springmvc.result.ResultBody;
import com.easyway.business.framework.springmvc.result.ResultUtil;
import com.github.pagehelper.util.StringUtil;
import com.kanglian.healthcare.authorization.annotation.Authorization;
import com.kanglian.healthcare.authorization.annotation.CurrentUser;
import com.kanglian.healthcare.back.constants.Constants;
import com.kanglian.healthcare.back.dal.pojo.User;
import com.kanglian.healthcare.back.service.HospitalGuahaoLogBo;
import com.kanglian.healthcare.back.service.UserBo;
import com.kanglian.healthcare.util.PropConfig;

@Authorization
@RestController
@RequestMapping(value = "/user/doctor")
public class UserDoctorController extends CrudController<User, UserBo> {

    @Autowired
    private HospitalGuahaoLogBo hospitalGuahaoLogBo;
    
    /**
     * 医生的预约（病人一览）
     * 
     * @param user
     * @return
     * @throws Exception
     */
    @GetMapping("/myPatientOrder")
    public ResultBody myPatientOrder(@CurrentUser User user, GuahaoLogQuery query) throws Exception {
        return ResultUtil.success(hospitalGuahaoLogBo.myPatientOrder(query),
                new JsonClothProcessor() {

                    @Override
                    public JSONObject wearCloth(Object pojo, JSONObject jsonObject) {
                        String domainUrl =
                                PropConfig.getInstance().getPropertyValue(Constants.STATIC_URL);
                        try {
                            if (StringUtil.isNotEmpty(jsonObject.getString("imageUrl"))) {
                                jsonObject.put("imageUrl",
                                        domainUrl.concat(jsonObject.getString("imageUrl")));
                            }
                        } catch (Exception e) {
                            // TODO: handle exception
                        }
                        return jsonObject;
                    }

                });
    }

    public static class GuahaoLogQuery extends Grid {

        private String userId;
        private String orderDay;

        @SingleValue(column = "user_id", equal = "=")
        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        @SingleValue(tableAlias="t1", column = "orderDay", equal = "=")
        public String getOrderDay() {
            return orderDay;
        }

        public void setOrderDay(String orderDay) {
            this.orderDay = orderDay;
        }
    }
}
