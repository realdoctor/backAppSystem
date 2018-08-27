package com.kanglian.healthcare.back.web;

import java.util.Date;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.alibaba.fastjson.JSONObject;
import com.easyway.business.framework.json.JsonClothProcessor;
import com.easyway.business.framework.mybatis.annotion.SingleValue;
import com.easyway.business.framework.mybatis.query.ConditionQuery;
import com.easyway.business.framework.mybatis.query.condition.WithoutValueCondition;
import com.easyway.business.framework.pojo.Grid;
import com.easyway.business.framework.springmvc.controller.CrudController;
import com.easyway.business.framework.springmvc.result.ResultBody;
import com.easyway.business.framework.springmvc.result.ResultUtil;
import com.easyway.business.framework.util.DateUtil;
import com.easyway.business.framework.util.StringUtil;
import com.kanglian.healthcare.authorization.annotation.Authorization;
import com.kanglian.healthcare.back.dal.pojo.Hospital;
import com.kanglian.healthcare.back.dal.pojo.HospitalDept;
import com.kanglian.healthcare.back.dal.pojo.HospitalGuahaoLog;
import com.kanglian.healthcare.back.service.HospitalBo;
import com.kanglian.healthcare.back.service.HospitalDeptBo;
import com.kanglian.healthcare.back.service.HospitalGuahaoLogBo;
import com.kanglian.healthcare.exception.InvalidParamException;

/**
 * 医院挂号
 * 
 * @author xl.liu
 */
@RestController
@RequestMapping(value = "/guahao")
public class HospitalGuahaoController extends CrudController<Hospital, HospitalBo> {

    @Autowired
    private HospitalDeptBo      hospitalDeptBo;
    @Autowired
    private HospitalGuahaoLogBo hospitalGuahaoLogBo;

    /**
     * 【按专家预约】按医院、科室、获取本院医生列表
     * 
     * @param query
     * @return
     * @throws Exception
     */
    @GetMapping("/hospital/orderExpert")
    public ResultBody orderExpert(HospitalGuohaoQuery query) throws Exception {
        if (StringUtil.isEmpty(query.getHospitalId())) {
            throw new InvalidParamException("hospitalId");
        }
        if (StringUtil.isEmpty(query.getDeptCode())) {
            throw new InvalidParamException("deptCode");
        }
        ConditionQuery conditionQuery = query.buildConditionQuery();
        List<HospitalDept> orderZhuanjiaList = hospitalDeptBo.findDeptDoctor(conditionQuery);
        return ResultUtil.success(orderZhuanjiaList);
    }

    /**
     * 【按日期预约】按医院、科室、日期获取本院医生值班列表
     * 
     * @param query
     * @return
     * @throws Exception
     */
    @GetMapping("/hospital/orderDateExpert")
    public ResultBody orderDateExpert(HospitalGuohaoQuery query) throws Exception {
        if (StringUtil.isEmpty(query.getHospitalId())) {
            throw new InvalidParamException("hospitalId");
        }
        if (StringUtil.isEmpty(query.getDeptCode())) {
            throw new InvalidParamException("deptCode");
        }
        if (StringUtil.isEmpty(query.getOrderDay())) {
            throw new InvalidParamException("orderDay");
        }
        ConditionQuery conditionQuery = query.buildConditionQuery();
        List<HospitalDept> orderDateExpertList = hospitalDeptBo.findOrderDateDoctor(conditionQuery);
        List<JSONObject> resultList =
                ResultUtil.wearCloth(orderDateExpertList, new JsonClothProcessor() {

                    @Override
                    public JSONObject wearCloth(Object pojo, JSONObject jsonObject) {
                        HospitalDept hospitalDept = (HospitalDept) pojo;
                        try {
                            HospitalGuahaoLog hospitalGuahaoLog = new HospitalGuahaoLog();
                            hospitalGuahaoLog.setHospitalId(hospitalDept.getHospitalId() + "");
                            hospitalGuahaoLog.setDeptId(hospitalDept.getDeptId() + "");
                            hospitalGuahaoLog.setDoctorCode(hospitalDept.getDoctorCode());
                            List<HospitalGuahaoLog> guahaoOrderList =
                                    hospitalGuahaoLogBo.getGuahaoLog(hospitalGuahaoLog);
                            if (guahaoOrderList != null
                                    && (guahaoOrderList.size() >= hospitalDept.getOrderNum())) {
                                jsonObject.put("orderFlag", "1");// 约满标识（0|正常，1|约满）
                            }
                        } catch (Exception e) {
                            // TODO: handle exception
                        }
                        return jsonObject;
                    }

                });
        return ResultUtil.success(resultList);
    }

    /**
     * 【按日期预约】-获取医生值班日
     * 
     * @param query
     * @return
     * @throws Exception
     */
    @GetMapping("/hospital/orderDate")
    public ResultBody orderDate(OrderDateQuery query) throws Exception {
        if (StringUtil.isEmpty(query.getHospitalId())) {
            throw new InvalidParamException("hospitalId");
        }
        if (StringUtil.isEmpty(query.getDeptCode())) {
            throw new InvalidParamException("deptCode");
        }
        ConditionQuery conditionQuery = query.buildConditionQuery();
        List<Map<String, String>> orderDateList = hospitalDeptBo.findDoctorDutyDay(conditionQuery);
        return ResultUtil.success(orderDateList);
    }

    /**
     * 预约挂号
     * 
     * @param query
     * @return
     * @throws Exception
     */
    @Authorization
    @PostMapping("/fastorder")
    public ResultBody fastorder(@RequestBody HospitalGuahaoLog hospitalGuahaoLog) throws Exception {
        if (hospitalGuahaoLog.getUserId() == null) {
            throw new InvalidParamException("userId");
        }
        if (StringUtil.isEmpty(hospitalGuahaoLog.getHospitalId())) {
            throw new InvalidParamException("hospitalId");
        }
        if (StringUtil.isEmpty(hospitalGuahaoLog.getDeptId())) {
            throw new InvalidParamException("deptId");
        }
        if (StringUtil.isEmpty(hospitalGuahaoLog.getDoctorCode())) {
            throw new InvalidParamException("doctorCode");
        }
        if (StringUtil.isNotEmpty(hospitalGuahaoLog.getOrderDay())) {
            hospitalGuahaoLog.setOrderDay(
                    DateUtil.ymdFormat(new Date(Long.valueOf(hospitalGuahaoLog.getOrderDay()))));
        }
        hospitalGuahaoLog.setAddTime(DateUtil.currentDate());
        hospitalGuahaoLogBo.fastorder(hospitalGuahaoLog);
        return ResultUtil.success();
    }

    /**
     * 获取医生排班日期
     * 
     * @author xl.liu
     */
    public static class OrderDateQuery extends Grid {
        // 医院id
        private String hospitalId;
        // 科室编码
        private String deptCode;
        // 科室名称
        private String deptName;
        // 医生编码
        private String doctorCode;

        @SingleValue(tableAlias = "t", column = "hospital_id", equal = "=")
        public String getHospitalId() {
            return hospitalId;
        }

        public void setHospitalId(String hospitalId) {
            this.hospitalId = hospitalId;
        }

        @SingleValue(tableAlias = "t2", column = "dept_name", equal = "like")
        public String getDeptName() {
            return StringUtil.isNotBlank(deptName) ? ("%" + deptName + "%") : null;
        }

        public void setDeptName(String deptName) {
            this.deptName = deptName;
        }

        @SingleValue(tableAlias = "t2", column = "dept_code", equal = "=")
        public String getDeptCode() {
            return deptCode;
        }

        public void setDeptCode(String deptCode) {
            this.deptCode = deptCode;
        }

        @SingleValue(tableAlias = "t", column = "doctor_code", equal = "=")
        public String getDoctorCode() {
            return doctorCode;
        }

        public void setDoctorCode(String doctorCode) {
            this.doctorCode = doctorCode;
        }
    }

    /**
     * 按专家、日期预约查询
     * 
     * @author xl.liu
     */
    public static class HospitalGuohaoQuery extends Grid {
        // 医院id
        private String hospitalId;
        // 科室编码
        private String deptCode;
        // 科室名称
        private String deptName;
        // 医生编码
        private String doctorCode;
        // 预约日
        private String orderDay;

        @SingleValue(tableAlias = "t", column = "hospital_id", equal = "=")
        public String getHospitalId() {
            return hospitalId;
        }

        public void setHospitalId(String hospitalId) {
            this.hospitalId = hospitalId;
        }

        @SingleValue(tableAlias = "t", column = "dept_name", equal = "like")
        public String getDeptName() {
            // TODO：后改为科室码来匹配
            return StringUtil.isNotBlank(deptName) ? ("%" + deptName + "%") : null;
        }

        public void setDeptName(String deptName) {
            this.deptName = deptName;
        }

        @SingleValue(tableAlias = "t", column = "dept_code", equal = "=")
        public String getDeptCode() {
            return deptCode;
        }

        public void setDeptCode(String deptCode) {
            this.deptCode = deptCode;
        }

        @SingleValue(tableAlias = "t1", column = "doctor_code", equal = "=")
        public String getDoctorCode() {
            return doctorCode;
        }

        public void setDoctorCode(String doctorCode) {
            this.doctorCode = doctorCode;
        }

        public String getOrderDay() {
            return orderDay;
        }

        public void setOrderDay(String orderDay) {
            this.orderDay = orderDay;
        }

        @Override
        public ConditionQuery buildConditionQuery() {
            ConditionQuery query = super.buildConditionQuery();
            if (StringUtil.isNotBlank(orderDay)) {
                StringBuffer buff = new StringBuffer();
                buff.append(" date_format(t2.duty_dtime, '%m-%d')='" + orderDay + "' ");
                query.addWithoutValueCondition(new WithoutValueCondition(buff.toString()));
            }
            return query;
        }

    }

}
