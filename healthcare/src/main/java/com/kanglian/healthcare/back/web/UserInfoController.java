package com.kanglian.healthcare.back.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.alibaba.fastjson.JSONObject;
import com.easyway.business.framework.json.JsonClothProcessor;
import com.easyway.business.framework.mybatis.annotion.SingleValue;
import com.easyway.business.framework.mybatis.query.ConditionQuery;
import com.easyway.business.framework.mybatis.query.condition.WithoutValueCondition;
import com.easyway.business.framework.pojo.Grid;
import com.easyway.business.framework.springmvc.controller.CrudController;
import com.easyway.business.framework.springmvc.result.ResultBody;
import com.easyway.business.framework.springmvc.result.ResultUtil;
import com.easyway.business.framework.util.StringUtil;
import com.kanglian.healthcare.authorization.annotation.Authorization;
import com.kanglian.healthcare.authorization.annotation.CurrentUser;
import com.kanglian.healthcare.back.constant.ApiMapping;
import com.kanglian.healthcare.back.constant.Constants;
import com.kanglian.healthcare.back.pojo.User;
import com.kanglian.healthcare.back.pojo.UserInfo;
import com.kanglian.healthcare.back.service.HospitalGuahaoLogBo;
import com.kanglian.healthcare.back.service.UserInfoBo;
import com.kanglian.healthcare.exception.InvalidParamException;

@Authorization
@RestController
public class UserInfoController extends CrudController<UserInfo, UserInfoBo> {

    @Autowired
    private HospitalGuahaoLogBo hospitalGuahaoLogBo;

    /**
     * 用户基本信息
     * 
     * @param query
     * @return
     * @throws Exception
     */
    @GetMapping(ApiMapping.USER_INFO)
    public ResultBody getUserInfo(User user) throws Exception {
        String mobilePhone = user.getMobilePhone();
        if (StringUtil.isEmpty(mobilePhone)) {
            throw new InvalidParamException("mobilePhone");
        }
        UserInfo userInfo = this.bo.getUserInfo(user);
        if (userInfo == null) {
            return ResultUtil.error("获取用户信息失败");
        }
        return ResultUtil.success(this.bo.reformUserInfo(userInfo));
    }

    /**
     * 我的预约-患者预约记录
     * 
     * @param user
     * @return
     * @throws Exception
     */
    @GetMapping(ApiMapping.USER_MYGUAHAO_ORDER)
    public ResultBody myGuahaoOrder(@CurrentUser User user, MyGuahaoOrderQuery query) throws Exception {
        if (StringUtil.isEmpty(query.getUserId())) {
            throw new InvalidParamException("userId");
        }
        return ResultUtil.success(hospitalGuahaoLogBo.myDoctorOrder(query),
                new JsonClothProcessor() {

                    @Override
                    public JSONObject wearCloth(Object pojo, JSONObject jsonObject) {
                        try {
                            if (StringUtil.isNotEmpty(jsonObject.getString("imageUrl"))) {
                                jsonObject.put("imageUrl",
                                        Constants.getStaticUrl().concat(jsonObject.getString("imageUrl")));
                            }
                        } catch (Exception e) {
                            // TODO: handle exception
                        }
                        return jsonObject;
                    }

                });
    }

    /**
     * 我的预约-医生被预约记录
     * 
     * @param user
     * @return
     * @throws Exception
     */
    @Authorization
    @GetMapping(ApiMapping.USER_MYPATIENT_ORDER)
    public ResultBody myPatientOrder(@CurrentUser User user, MyPatientOrderQuery query)
            throws Exception {
        if (StringUtil.isEmpty(query.getUserId())) {
            throw new InvalidParamException("userId");
        }
        return ResultUtil.success(hospitalGuahaoLogBo.myPatientOrder(query),
                new JsonClothProcessor() {

                    @Override
                    public JSONObject wearCloth(Object pojo, JSONObject jsonObject) {
                        try {
                            if (StringUtil.isNotEmpty(jsonObject.getString("imageUrl"))) {
                                jsonObject.put("imageUrl",
                                        Constants.getStaticUrl().concat(jsonObject.getString("imageUrl")));
                            }
                        } catch (Exception e) {
                            // TODO: handle exception
                        }
                        return jsonObject;
                    }

                });
    }

    public static class MyGuahaoOrderQuery extends Grid {

        private String userId;
        private String orderDay;

        @SingleValue(column = "user_id", equal = "=")
        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        @SingleValue(tableAlias = "t", column = "order_day", equal = "=")
        public String getOrderDay() {
            return orderDay;
        }

        public void setOrderDay(String orderDay) {
            this.orderDay = orderDay;
        }
    }
    
    public static class MyPatientOrderQuery extends Grid {

        private String userId;
        private String orderDay;

        @SingleValue(column = "user_id", equal = "=")
        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getOrderDay() {
            return orderDay;
        }

        public void setOrderDay(String orderDay) {
            this.orderDay = orderDay;
        }
        
        @Override
        public ConditionQuery buildConditionQuery() {
            ConditionQuery query = super.buildConditionQuery();
            if (StringUtil.isNotBlank(orderDay)) {
                StringBuffer buff = new StringBuffer();
                buff.append(" date_format(t1.order_day, '%m-%d')='" + orderDay + "' ");
                query.addWithoutValueCondition(new WithoutValueCondition(buff.toString()));
            }
            return query;
        }
    }
}
