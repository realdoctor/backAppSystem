package com.kanglian.healthcare.back.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.easyway.business.framework.mybatis.annotion.SingleValue;
import com.easyway.business.framework.pojo.Grid;
import com.easyway.business.framework.springmvc.controller.CrudController;
import com.easyway.business.framework.springmvc.result.ResultBody;
import com.easyway.business.framework.springmvc.result.ResultUtil;
import com.kanglian.healthcare.back.dal.pojo.PatientRecord;
import com.kanglian.healthcare.back.service.PatientRecordBo;

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
        query.setPageSize(0);// 不分页
        Grid grid = this.bo.queryFrontList(query);
        return ResultUtil.success(grid.getList());
    }

    public static class PatientQuery extends Grid {

        private String mobilePhone;
        private String patientId;
        private String beginDate;
        private String endDate;

        @SingleValue(column = "mobile_phone", equal = "=")
        public String getMobilePhone() {
            return mobilePhone;
        }
        
        public void setMobilePhone(String mobilePhone) {
            this.mobilePhone = mobilePhone;
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
