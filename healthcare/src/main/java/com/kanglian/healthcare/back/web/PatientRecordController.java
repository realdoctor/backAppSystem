package com.kanglian.healthcare.back.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.easyway.business.framework.mybatis.annotion.SingleValue;
import com.easyway.business.framework.mybatis.query.ConditionQuery;
import com.easyway.business.framework.pojo.Grid;
import com.easyway.business.framework.springmvc.controller.CrudController;
import com.easyway.business.framework.springmvc.result.ResultBody;
import com.easyway.business.framework.springmvc.result.ResultUtil;
import com.easyway.business.framework.util.StringUtil;
import com.kanglian.healthcare.authorization.annotation.Authorization;
import com.kanglian.healthcare.back.constant.ApiMapping;
import com.kanglian.healthcare.back.pojo.PatientRecord;
import com.kanglian.healthcare.back.service.PatientRecordBo;
import com.kanglian.healthcare.exception.InvalidParamException;

/**
 * 病历归档
 * 
 * @author xl.liu
 */
@Authorization
@RestController
public class PatientRecordController extends CrudController<PatientRecord, PatientRecordBo> {

    /**
     * 患者病历列表
     * 
     * @param query
     * @return
     * @throws Exception
     */
    @GetMapping(ApiMapping.PATIENT_LIST)
    public ResultBody list(PatientQuery query) throws Exception {
        String mobilePhone = query.getMobilePhone();
        Integer clientNum = query.getClientNum();
        if (StringUtil.isBlank(mobilePhone)) {
            throw new InvalidParamException("mobilePhone");
        }
        ConditionQuery conditionQuery = query.buildConditionQuery();
        List<PatientRecord> newsList = bo.frontList(conditionQuery);
        int cnt = newsList.size();

        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("refresh", true);
        resultMap.put("total", cnt);
        // 判断是否要客户端刷新数据
        if (clientNum != null && clientNum >= cnt) {
            resultMap.put("refresh", false);
        }
        resultMap.put("list", newsList);
        return ResultUtil.success(resultMap);
    }

    public static class PatientQuery extends Grid {

        private String  mobilePhone;
        private String  patientId;
        private String  beginDate;
        private String  endDate;
        // 客户端缓存的数据，判断是否要刷新
        private Integer clientNum;

        public Integer getClientNum() {
            return clientNum;
        }

        public void setClientNum(Integer clientNum) {
            this.clientNum = clientNum;
        }

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
