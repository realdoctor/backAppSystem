package com.realdoctor.back.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.easyway.business.framework.mybatis.annotion.SingleValue;
import com.easyway.business.framework.pojo.Grid;
import com.easyway.business.framework.springmvc.controller.CrudController;
import com.easyway.business.framework.springmvc.result.ResultBody;
import com.easyway.business.framework.springmvc.result.ResultUtil;
import com.realdoctor.back.dal.pojo.PatientRecord;
import com.realdoctor.back.service.PatientRecordBo;

@RestController
@RequestMapping(value = "/patient")
public class PatientRecordController extends CrudController<PatientRecord, PatientRecordBo> {

    /**
     * 患者病历列表
     * 
     * @param query
     * @return
     * @throws Exception
     */
    @GetMapping
    public ResultBody list(PatientQuery query) throws Exception {
        return ResultUtil.success(this.bo.queryFrontList(query));
    }

    public static class PatientQuery extends Grid {

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
