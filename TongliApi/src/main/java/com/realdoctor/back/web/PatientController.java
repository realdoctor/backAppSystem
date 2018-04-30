package com.realdoctor.back.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.easyway.business.framework.mybatis.annotion.SingleValue;
import com.easyway.business.framework.pojo.Grid;
import com.easyway.business.framework.springmvc.controller.CrudController;
import com.easyway.business.framework.springmvc.result.ResultBody;
import com.easyway.business.framework.springmvc.result.ResultUtil;
import com.realdoctor.back.dal.pojo.Outpatient;
import com.realdoctor.back.service.OutpatientBo;

/**
 * 医疗服务-门诊摘要信息
 * 
 * @author xl.liu
 */
@RestController
@RequestMapping(value = "/patient")
public class PatientController extends CrudController<Outpatient, OutpatientBo> {

    /**
     * 患者病历列表
     * 
     * @param query
     * @return
     * @throws Exception
     */
    @GetMapping("/medicalRecord")
    public ResultBody list(OutpatientQuery query) throws Exception {
        return ResultUtil.success(this.bo.queryFrontList(query));
    }

    public static class OutpatientQuery extends Grid {

        private String orgCode;
        private String patientId;
        private String beginDate;
        private String endDate;

        @SingleValue(column = "org_code", equal = "=")
        public String getOrgCode() {
            return orgCode;
        }

        public void setOrgCode(String orgCode) {
            this.orgCode = orgCode;
        }

        @SingleValue(column = "patient_id", equal = "=")
        public String getPatientId() {
            return patientId;
        }

        public void setPatientId(String patientId) {
            this.patientId = patientId;
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

    }
}
