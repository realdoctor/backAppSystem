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
import com.github.pagehelper.util.StringUtil;
import com.kanglian.healthcare.back.pojo.HospitalDoctor;
import com.kanglian.healthcare.back.service.HospitalDoctorBo;
import com.kanglian.healthcare.back.service.UserDoctorBo;
import com.kanglian.healthcare.exception.InvalidParamException;

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

    /**
     * 医生详情
     * 
     * @param query
     * @return
     * @throws Exception
     */
    @GetMapping("/getDoctorInfo")
    public ResultBody getDoctorInfo(UserQuery query) throws Exception {
        if (StringUtil.isEmpty(query.getUserId())) {
            throw new InvalidParamException("userId");
        }
        return ResultUtil.success(this.userDoctorBo.getDoctorInfo(query.buildConditionQuery()));
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

        @SingleValue(tableAlias="t2", column = "doctor_code", equal = "=")
        public String getDoctorCode() {
            return doctorCode;
        }

        public void setDoctorCode(String doctorCode) {
            this.doctorCode = doctorCode;
        }
    }
    
}
