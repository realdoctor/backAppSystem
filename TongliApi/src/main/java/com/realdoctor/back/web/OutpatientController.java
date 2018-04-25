package com.realdoctor.back.web;

import org.springframework.web.bind.annotation.GetMapping;
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
@RestController(value = "/patient")
public class OutpatientController extends CrudController<Outpatient, OutpatientBo> {

    @GetMapping
    public ResultBody list(OutpatientQuery query) throws Exception {
        return ResultUtil.success(this.bo.queryFrontList(query));
    }

    public static class OutpatientQuery extends Grid {

        private String beginDate;
        private String endDate;
        private String mobilePhone;

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

        @SingleValue(column = "tel_no", equal = "=")
        public String getMobilePhone() {
            return mobilePhone;
        }

        public void setMobilePhone(String mobilePhone) {
            this.mobilePhone = mobilePhone;
        }

    }
}
