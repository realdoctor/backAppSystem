package com.kanglian.healthcare.back.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.easyway.business.framework.mybatis.annotion.SingleValue;
import com.easyway.business.framework.mybatis.query.ConditionQuery;
import com.easyway.business.framework.pojo.Grid;
import com.easyway.business.framework.springmvc.controller.CrudController;
import com.easyway.business.framework.springmvc.result.ResultBody;
import com.easyway.business.framework.springmvc.result.ResultUtil;
import com.easyway.business.framework.util.StringUtil;
import com.kanglian.healthcare.authorization.annotation.Authorization;
import com.kanglian.healthcare.back.dal.pojo.PatientRecord;
import com.kanglian.healthcare.back.service.RevisitPatientRecordBo;
import com.kanglian.healthcare.exception.InvalidParamException;

/**
 * 在线复诊
 * 
 * @author xl.liu
 */
@Authorization
@RestController
@RequestMapping(value = "/patient/revisit")
public class PatientRecordRevisitController
        extends CrudController<PatientRecord, RevisitPatientRecordBo> {

    /**
     * 患者复诊病历列表
     * 
     * @param query
     * @return
     * @throws Exception
     */
    @GetMapping("/list")
    public ResultBody list(RevisitPatientQuery query) throws Exception {
        return ResultUtil.success(this.bo.frontList(query));
    }

    /**
     * 患者复诊病历诊断列表
     * 
     * @param query
     * @return
     * @throws Exception
     */
    @GetMapping("/diagList")
    public ResultBody diagList(RevisitPatientQuery query) throws Exception {
        String mobilePhone = query.getMobilePhone();
        if (StringUtil.isBlank(mobilePhone)) {
            throw new InvalidParamException("mobilePhone");
        }

        ConditionQuery conditionQuery = query.buildConditionQuery();
        return ResultUtil.success(this.bo.findDiagList(conditionQuery));
    }

    public static class RevisitPatientQuery extends Grid {
        
        private String userId;
        private String mobilePhone;
        private String patientId;
        private String doctorCode;
        private String diagCode;
        private String beginDate;
        private String endDate;
        private String sortstr;

        @SingleValue(tableAlias = "t", column = "user_id", equal = "=")
        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }
        
        @SingleValue(tableAlias = "t", column = "mobile_phone", equal = "=")
        public String getMobilePhone() {
            return mobilePhone;
        }

        public void setMobilePhone(String mobilePhone) {
            this.mobilePhone = mobilePhone;
        }

        @SingleValue(tableAlias = "t0", column = "doctor_code", equal = "=")
        public String getDoctorCode() {
            return doctorCode;
        }

        public void setDoctorCode(String doctorCode) {
            this.doctorCode = doctorCode;
        }
        
        @SingleValue(column = "patient_id", equal = "=")
        public String getPatientId() {
            return patientId;
        }

        public void setPatientId(String patientId) {
            this.patientId = patientId;
        }

        @SingleValue(tableAlias = "t0", column = "diag_code", equal = "=")
        public String getDiagCode() {
            return diagCode;
        }

        public void setDiagCode(String diagCode) {
            this.diagCode = diagCode;
        }

        @SingleValue(tableAlias = "t0", column = "visit_dtime", equal = ">=")
        public String getBeginDate() {
            return beginDate;
        }

        public void setBeginDate(String beginDate) {
            this.beginDate = beginDate;
        }

        @SingleValue(tableAlias = "t0", column = "visit_dtime", equal = "<=")
        public String getEndDate() {
            return endDate;
        }

        public void setEndDate(String endDate) {
            this.endDate = endDate;
        }

        public String getSortstr() {
            return sortstr;
        }

        public void setSortstr(String sortstr) {
            this.sortstr = sortstr;
        }

        @Override
        public ConditionQuery buildConditionQuery() {
            if (StringUtil.isNotEmpty(sortstr)) {
                if ("1".equals(sortstr)) {
                    this.setSortname("t0.visit_dtime");
                    this.setSortorder("asc");
                }
            }
            return super.buildConditionQuery();
        }

    }
}
