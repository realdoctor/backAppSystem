package com.kanglian.healthcare.back.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.easyway.business.framework.mybatis.annotion.SingleValue;
import com.easyway.business.framework.pojo.Grid;
import com.easyway.business.framework.springmvc.controller.CrudController;
import com.easyway.business.framework.springmvc.result.ResultBody;
import com.easyway.business.framework.springmvc.result.ResultUtil;
import com.kanglian.healthcare.authorization.annotation.CurrentUser;
import com.kanglian.healthcare.back.dal.pojo.User;
import com.kanglian.healthcare.back.service.HospitalGuahaoLogBo;
import com.kanglian.healthcare.back.service.UserBo;
import com.kanglian.healthcare.exception.InvalidParamException;

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
    public ResultBody myGuahaoOrder(@CurrentUser User user, GuahaoLogQuery query) throws Exception {
        if (user.getUserId() == null) {
            throw new InvalidParamException("userId");
        }
        return ResultUtil.success(hospitalGuahaoLogBo.myPatientOrder(query));
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
