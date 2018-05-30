package com.kanglian.healthcare.back.web;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.alibaba.fastjson.JSONObject;
import com.easyway.business.framework.common.annotation.PerformanceClass;
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
import com.kanglian.healthcare.back.dal.pojo.HospitalAddress;
import com.kanglian.healthcare.back.dal.pojo.HospitalDept;
import com.kanglian.healthcare.back.dal.pojo.HospitalGuahaoLog;
import com.kanglian.healthcare.back.service.HospitalAddressBo;
import com.kanglian.healthcare.back.service.HospitalDeptBo;
import com.kanglian.healthcare.back.service.HospitalDeptCategoryBo;
import com.kanglian.healthcare.back.service.HospitalGuahaoLogBo;
import com.kanglian.healthcare.exception.InvalidParamException;

/**
 * 医院挂号
 * 
 * @author xl.liu
 */
@RestController
@RequestMapping(value = "/guahao")
public class HospitalGuahaoController
        extends CrudController<HospitalAddress, HospitalAddressBo> {

    @Autowired
    private HospitalDeptCategoryBo hospitalDeptCategoryBo;
    @Autowired
    private HospitalDeptBo hospitalDeptBo;
    @Autowired
    private HospitalGuahaoLogBo hospitalGuahaoLogBo;
    
    /**
     * 医院一览
     * 
     * @param query
     * @return
     * @throws Exception
     */
    @PerformanceClass
    @GetMapping("/hospital")
    public ResultBody list(HospitalGuahaoQuery query) throws Exception {
        return super.list(query);
    }

    /**
     * 医疗机构科室分类列表
     * 
     * @return
     * @throws Exception
     */
    @GetMapping("/hospital/deptCategory")
    public ResultBody hospitalDeptCategory() throws Exception {
        return ResultUtil.success(hospitalDeptCategoryBo.getHospitalDeptList());
    }
    
    /**
     * 搜索医院、医生、科室、疾病
     * 
     * @param searchstr
     * @return 医院 | 医生
     * @throws Exception
     */
    @PerformanceClass
    @GetMapping("/search")
    public ResultBody search(GuahaoSearchQuery query) throws Exception {
        ConditionQuery conditionQuery1 = query.buildConditionQuery();
        // 按医院查
        List<HospitalAddress> hospitalList = this.bo.queryForHospitalAndDoctor(conditionQuery1);
        // 按医生查
        query.appendQueryParam("searchOpt", "doctor");
        ConditionQuery conditionQuery2 = query.buildConditionQuery();
        List<HospitalAddress> doctorList = this.bo.queryForHospitalAndDoctor(conditionQuery2);
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("hospitalList", hospitalList);
        resultMap.put("doctorList", doctorList);
        return ResultUtil.success(resultMap);
    }
    
    /**
     * 按医院、科室、获取本院医生-按专家预约
     * 
     * @param query
     * @return
     * @throws Exception
     */
    @PerformanceClass
    @GetMapping("/hospital/orderExpert")
    public ResultBody orderExpert(HospitalZhuanjiaQuery query) throws Exception {
        if (StringUtil.isEmpty(query.getHospitalId())) {
            throw new InvalidParamException("hospitalId");
        }
        if (StringUtil.isEmpty(query.getDeptName())) {
            throw new InvalidParamException("deptName");
        }
        ConditionQuery conditionQuery = query.buildConditionQuery();
        List<HospitalDept> orderZhuanjiaList = hospitalDeptBo.findDeptDoctor(conditionQuery);
        return ResultUtil.success(orderZhuanjiaList);
    }
    
    /**
     * 工作日列表-按日期预约
     * 
     * @param query
     * @return
     * @throws Exception
     */
    @PerformanceClass
    @GetMapping("/hospital/orderDate")
    public ResultBody orderDate(HospitalZhuanjiaQuery query) throws Exception {
        if (StringUtil.isEmpty(query.getHospitalId())) {
            throw new InvalidParamException("hospitalId");
        }
        if (StringUtil.isEmpty(query.getDeptName())) {
            throw new InvalidParamException("deptName");
        }
        ConditionQuery conditionQuery = query.buildConditionQuery();
        List<Map<String, String>> orderDateList = hospitalDeptBo.findWorkingDay(conditionQuery);
        return ResultUtil.success(orderDateList);
    }
    
    /**
     * 按医院、科室、获取本院医生-按日期预约
     * 
     * @param query
     * @return
     * @throws Exception
     */
    @PerformanceClass
    @GetMapping("/hospital/orderDateExpert")
    public ResultBody orderDateExpert(HospitalZhuanjiaQuery query) throws Exception {
        if (StringUtil.isEmpty(query.getHospitalId())) {
            throw new InvalidParamException("hospitalId");
        }
        if (StringUtil.isEmpty(query.getDeptName())) {
            throw new InvalidParamException("deptName");
        }
        if (StringUtil.isEmpty(query.getOrderDay())) {
            throw new InvalidParamException("orderDay");
        }
        ConditionQuery conditionQuery = query.buildConditionQuery();
        List<HospitalDept> orderDateExpertList = hospitalDeptBo.findOrderDateDoctor(conditionQuery);
        List<JSONObject> resultList = ResultUtil.wearCloth(orderDateExpertList, new JsonClothProcessor() {

            @Override
            public JSONObject wearCloth(Object pojo, JSONObject jsonObject) {
                HospitalDept hospitalDept = (HospitalDept)pojo;
                try {
                    HospitalGuahaoLog hospitalGuahaoLog = new HospitalGuahaoLog();
                    hospitalGuahaoLog.setHospitalId(hospitalDept.getHospitalId()+"");
                    hospitalGuahaoLog.setDeptId(hospitalDept.getDeptId()+"");
                    hospitalGuahaoLog.setDoctorCode(hospitalDept.getDoctorCode());
                    List<HospitalGuahaoLog> guahaoOrderList = hospitalGuahaoLogBo.getGuahaoLog(hospitalGuahaoLog);
                    if (guahaoOrderList != null
                            && (guahaoOrderList.size() >= hospitalDept.getOrderNum())) {
                        jsonObject.put("orderFlag", "1");
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
     * 预约挂号
     * 
     * @param query
     * @return
     * @throws Exception
     */
    @Authorization
    @PerformanceClass
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
            hospitalGuahaoLog.setOrderDay(DateUtil.ymdFormat(new Date(Long.valueOf(hospitalGuahaoLog.getOrderDay()))));
        }
        hospitalGuahaoLog.setAddTime(DateUtil.currentDate());
        hospitalGuahaoLogBo.save(hospitalGuahaoLog);
        return ResultUtil.success();
    }
    
    public static class HospitalZhuanjiaQuery extends Grid {
        // 医院id
        private String hospitalId;
        // 科室名称
        private String deptName;
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
            return StringUtil.isNotBlank(deptName) ? ("%" + deptName + "%") : null;
        }

        public void setDeptName(String deptName) {
            this.deptName = deptName;
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
                buff.append(" date_format(t2.duty_dtime, '%m-%d')='"+orderDay+"' ");
                query.addWithoutValueCondition(new WithoutValueCondition(buff.toString()));
            }
            return query;
        }
        
    }
    
    public static class GuahaoSearchQuery extends HospitalGuahaoQuery {
        private String searchstr;
        // 医生职称
        private String positional;

        public String getSearchstr() {
            return searchstr;
        }

        public void setSearchstr(String searchstr) {
            this.searchstr = searchstr;
        }
        
        @SingleValue(tableAlias="t2", column = "positional", equal = "=")
        public String getPositional() {
            return positional;
        }
        public void setPositional(String positional) {
            this.positional = positional;
        }
        
        @Override
        public ConditionQuery buildConditionQuery() {
            ConditionQuery query = super.buildConditionQuery();
            if (StringUtil.isNotBlank(searchstr)) {
                searchstr = searchstr.replaceAll("['\"<>#&]", "");
                StringBuffer buff = new StringBuffer();
                buff.append(" (instr(t1.dept_name, '"+searchstr+"') > 0) or (instr(t2.field, '"+searchstr+"') > 0) ");
                if (query.getParamMap().get("searchOpt") != null) {// 按医生查
                    buff.append(" or ");
                    buff.append(" (t2.doctor_name LIKE '%"+searchstr+"%') ");
                } else {// 按医院查
                    buff.append(" or ");
                    buff.append(" (t1.hospital_name LIKE '%"+searchstr+"%') ");
                }
                query.addWithoutValueCondition(new WithoutValueCondition(buff.toString()));
            }
            return query;
        }
    }
    
    public static class HospitalGuahaoQuery extends Grid {
        /**
         * 1）综合排序
         * 3）预约量排序
         */
        private String sortstr;
        /**
         * 筛选字段
         */
        // 三级甲等
        private String hospitalLevel;
        // 城市名称
        private String cityName;
        
        @SingleValue(tableAlias="t1", column = "hospital_level", equal = "=")
        public String getHospitalLevel() {
            return hospitalLevel;
        }
        public void setHospitalLevel(String hospitalLevel) {
            this.hospitalLevel = hospitalLevel;
        }
        public String getCityName() {
            return cityName;
        }
        public void setCityName(String cityName) {
            this.cityName = cityName;
        }
        public String getSortstr() {
            return sortstr;
        }
        public void setSortstr(String sortstr) {
            this.sortstr = sortstr;
        }
        @Override
        public ConditionQuery buildConditionQuery() {
            if(StringUtil.isNotEmpty(sortstr)) {
                // 综合排序
                if ("1".equals(sortstr)) {
                    this.setSortname("city");
                    this.setSortorder("desc");
                } else if ("3".equals(sortstr)) {// 预约量排序
                    this.setSortname("mark_num");
                    this.setSortorder("desc");
                }
            }
            ConditionQuery query = super.buildConditionQuery();
            if(StringUtil.isNotBlank(cityName)) {
                query.addWithoutValueCondition(new WithoutValueCondition(" (instr('"+cityName+"', t.province) > 0) or (instr('"+cityName+"', t.city) > 0) "));
            }
            return query;
        }

    }
}
