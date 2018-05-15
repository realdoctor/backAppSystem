package com.kanglian.healthcare.back.web;

import java.util.HashMap;
import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.easyway.business.framework.mybatis.annotion.SingleValue;
import com.easyway.business.framework.pojo.Grid;
import com.easyway.business.framework.springmvc.controller.CrudController;
import com.easyway.business.framework.springmvc.result.ResultBody;
import com.easyway.business.framework.springmvc.result.ResultUtil;
import com.easyway.business.framework.util.StringUtil;
import com.kanglian.healthcare.authorization.annotation.Authorization;
import com.kanglian.healthcare.back.dal.pojo.PatientRecord;
import com.kanglian.healthcare.back.service.PatientRecordBo;
import com.kanglian.healthcare.util.ValidateUtil;

@Authorization
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
        String mobilePhone = query.getMobilePhone();
        Integer clientNum = query.getClientNum();
        if (StringUtil.isBlank(mobilePhone)) {
            return ResultUtil.error("手机号不能为空！");
        }
        if (!ValidateUtil.isPhone(mobilePhone)) {
            return ResultUtil.error("请输入正确的11位手机号！");
        }
        query.setPageSize(0);// 不分页
        Grid grid = this.bo.queryFrontList(query);
        int cnt = grid.getTotal();

        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("refresh", true);
        resultMap.put("total", cnt);
        // 判断是否要客户端刷新数据
        if (clientNum != null && clientNum >= cnt) {
            resultMap.put("refresh", false);
        }
        resultMap.put("list", grid.getList());
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
