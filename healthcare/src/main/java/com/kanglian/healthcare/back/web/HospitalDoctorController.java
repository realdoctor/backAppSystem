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
import com.kanglian.healthcare.back.dal.pojo.HospitalDoctor;
import com.kanglian.healthcare.back.dal.pojo.User;
import com.kanglian.healthcare.back.service.HospitalDoctorBo;
import com.kanglian.healthcare.back.service.HospitalGuahaoLogBo;
import com.kanglian.healthcare.back.service.UserDoctorBo;
import com.kanglian.healthcare.util.PropConfig;

/**
 * 医生相关
 * 
 * @author xl.liu
 */
@RestController
@RequestMapping(value = "/doctor")
public class HospitalDoctorController extends CrudController<HospitalDoctor, HospitalDoctorBo> {

    @Autowired
    private UserDoctorBo        userDoctorBo;
    @Autowired
    private HospitalGuahaoLogBo hospitalGuahaoLogBo;

    /**
     * 医生一览
     * 
     * @param query
     * @return
     * @throws Exception
     */
    @GetMapping("/list")
    public ResultBody list(UserQuery query) throws Exception {
        return ResultUtil.success(this.userDoctorBo.frontList(query));
    }

    @GetMapping("/getDoctorInfo")
    public ResultBody getDoctorInfo(UserQuery query) throws Exception {
        return ResultUtil.success(this.userDoctorBo.getDoctorInfo(query.buildConditionQuery()));
    }
    
    /**
     * 病人预约一览
     * 
     * @param user
     * @return
     * @throws Exception
     */
    @Authorization
    @GetMapping("/patient/list")
    public ResultBody myPatientOrder(@CurrentUser User user, GuahaoLogQuery query)
            throws Exception {
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

    public static class UserQuery extends Grid {

        private String userId;
        private String doctorCode;
        
        @SingleValue(column = "user_id", equal = "=")
        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        @SingleValue(column = "doctor_code", equal = "=")
        public String getDoctorCode() {
            return doctorCode;
        }

        public void setDoctorCode(String doctorCode) {
            this.doctorCode = doctorCode;
        }
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

        @SingleValue(tableAlias = "t1", column = "orderDay", equal = "=")
        public String getOrderDay() {
            return orderDay;
        }

        public void setOrderDay(String orderDay) {
            this.orderDay = orderDay;
        }
    }
}
