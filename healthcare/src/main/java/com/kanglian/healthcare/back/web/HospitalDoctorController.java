package com.kanglian.healthcare.back.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.easyway.business.framework.mybatis.annotion.SingleValue;
import com.easyway.business.framework.pojo.Grid;
import com.easyway.business.framework.springmvc.controller.CrudController;
import com.easyway.business.framework.springmvc.result.ResultBody;
import com.easyway.business.framework.springmvc.result.ResultUtil;
import com.github.pagehelper.util.StringUtil;
import com.kanglian.healthcare.back.constant.ApiMapping;
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
public class HospitalDoctorController extends CrudController<HospitalDoctor, HospitalDoctorBo> {

    @Autowired
    private UserDoctorBo userDoctorBo;

    /**
     * 医生一览
     * 
     * @param query
     * @return
     * @throws Exception
     */
    @GetMapping(ApiMapping.DOCTOR_LIST)
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
    @GetMapping(ApiMapping.DOCTOR_GETINFO)
    public ResultBody getDoctorInfo(UserQuery query, @RequestParam(required=false, value="flag") String flag,
            @RequestParam(required=false, value="hospitalName") String hospitalName,
            @RequestParam(required=false, value="doctorName") String doctorName)
            throws Exception {
        if ("1".equals(flag)) {
            if (StringUtil.isEmpty(hospitalName)) {
                throw new InvalidParamException("hospitalName");
            }
            if (StringUtil.isEmpty(hospitalName)) {
                throw new InvalidParamException("doctorName");
            }
            return ResultUtil
                    .success(this.userDoctorBo.getDoctorInfoByName(hospitalName, doctorName));
        } else {
            if (StringUtil.isEmpty(query.getUserId())) {
                throw new InvalidParamException("userId");
            }
            return ResultUtil.success(this.userDoctorBo.getDoctorInfo(query.buildConditionQuery()));
        }
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

        @SingleValue(tableAlias = "t2", column = "doctor_code", equal = "=")
        public String getDoctorCode() {
            return doctorCode;
        }

        public void setDoctorCode(String doctorCode) {
            this.doctorCode = doctorCode;
        }

    }

}
