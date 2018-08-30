package com.kanglian.healthcare.back.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.easyway.business.framework.mybatis.annotion.SingleValue;
import com.easyway.business.framework.pojo.Grid;
import com.easyway.business.framework.springmvc.controller.CrudController;
import com.easyway.business.framework.springmvc.result.ResultBody;
import com.kanglian.healthcare.authorization.annotation.Authorization;
import com.kanglian.healthcare.back.constant.ApiMapping;
import com.kanglian.healthcare.back.pojo.PatientDrug;
import com.kanglian.healthcare.back.service.PatientDrugBo;

/**
 * 患者用药列表
 * 
 * @author xl.liu
 */
@Authorization
@RestController
public class PatientDrugController extends CrudController<PatientDrug, PatientDrugBo> {

    /**
     * 患者病历用药列表
     * 
     * @param query
     * @return
     * @throws Exception
     */
    @GetMapping(ApiMapping.PATIENT_DRUG_LIST)
    public ResultBody list(PatientDrugQuery query) throws Exception {
        return super.list(query);
    }

    public static class PatientDrugQuery extends Grid {

        // 诊断信息id
        private String patientDiagId;

        @SingleValue(column = "patient_diag_id", equal = "=")
        public String getPatientDiagId() {
            return patientDiagId;
        }

        public void setPatientDiagId(String patientDiagId) {
            this.patientDiagId = patientDiagId;
        }

    }
}
