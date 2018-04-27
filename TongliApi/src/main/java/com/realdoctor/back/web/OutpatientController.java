package com.realdoctor.back.web;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.easyway.business.framework.json.JsonClothProcessor;
import com.easyway.business.framework.mybatis.annotion.SingleValue;
import com.easyway.business.framework.pojo.Grid;
import com.easyway.business.framework.springmvc.controller.CrudController;
import com.easyway.business.framework.springmvc.result.ResultBody;
import com.easyway.business.framework.springmvc.result.ResultUtil;
import com.realdoctor.back.constants.Constant;
import com.realdoctor.back.dal.exo.OutpatientInfo;
import com.realdoctor.back.dal.pojo.Outpatient;
import com.realdoctor.back.service.OutpatientBo;
import com.realdoctor.util.CacheManager;

/**
 * 医疗服务-门诊摘要信息
 * 
 * @author xl.liu
 */
@RestController
@RequestMapping(value = "/patient")
public class OutpatientController extends CrudController<Outpatient, OutpatientBo> {

    @GetMapping
    public ResultBody list(OutpatientQuery query) throws Exception {
        return ResultUtil.success(this.bo.queryFrontList(query), new JsonClothProcessor(){

            @Override
            @SuppressWarnings("rawtypes")
            public JSONObject wearCloth(Object pojo, JSONObject jsonObject) {
                final OutpatientInfo info = (OutpatientInfo) pojo;
                try {
                    // 民族
                    jsonObject.put("nationalityName", ((Map) CacheManager.get(Constant.STD_NATIONALITY)).get(info.getNationalityCode()));
                    // 身份证类型
                    jsonObject.put("idTypeName", ((Map) CacheManager.get(Constant.STD_PERSON_ID_TYPE)).get(info.getIdTypeCode()));
                    // 婚姻状况
                    jsonObject.put("marriageName", ((Map) CacheManager.get(Constant.STD_MARRIAGE)).get(info.getMarriageCode()));
                    // 性别
                    jsonObject.put("sexName", ((Map) CacheManager.get(Constant.STD_SEX)).get(info.getSexCode()));
                    // 血型
                    jsonObject.put("aboName", ((Map) CacheManager.get(Constant.STD_BLOOD_TYPE)).get(info.getAboCode()));
                    // RH血型
                    jsonObject.put("rhName", ((Map) CacheManager.get(Constant.STD_RH_RESULT)).get(info.getRhCode()));
                    // 药物剂型名称
                    jsonObject.put("drugFormName", ((Map) CacheManager.get(Constant.STD_MEDICINE_FORM)).get(info.getDrugFormCode()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return jsonObject;
            }
            
        });
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
