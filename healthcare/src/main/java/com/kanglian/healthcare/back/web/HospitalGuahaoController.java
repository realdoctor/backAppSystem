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
    @GetMapping("/search")
    public ResultBody search(GuahaoSearchQuery query) throws Exception {
        return ResultUtil.success(this.bo.queryForHospitalAndDoctor(query));
    }
    
    /**
     * 按医院、科室、获取本院医生-按专家预约列表
     * 
     * @param query
     * @return
     * @throws Exception
     */
    @GetMapping("/hospital/orderExpert")
    public ResultBody orderExpert(HospitalYuyuaQuery query) throws Exception {
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
     * 按日期预约-医生工作日排班列表
     * 
     * @param query
     * @return
     * @throws Exception
     */
    @GetMapping("/hospital/orderDate")
    public ResultBody orderDate(DoctorOrderDateQuery query) throws Exception {
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
     * 按医院、科室、获取本院医生-按日期预约列表
     * 
     * @param query
     * @return
     * @throws Exception
     */
    @GetMapping("/hospital/orderDateExpert")
    public ResultBody orderDateExpert(HospitalYuyuaQuery query) throws Exception {
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
            hospitalGuahaoLog.setOrderDay(DateUtil.ymdFormat(new Date(Long.valueOf(hospitalGuahaoLog.getOrderDay()))));
        }
        hospitalGuahaoLog.setAddTime(DateUtil.currentDate());
        hospitalGuahaoLogBo.fastorder(hospitalGuahaoLog);
        return ResultUtil.success();
    }
    
    /**
     * 医生可预约日期
     * 
     * @author xl.liu
     */
    public static class DoctorOrderDateQuery extends Grid {
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
        
        @SingleValue(tableAlias = "t", column = "dept_code", equal = "=")
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
    
    public static class HospitalYuyuaQuery extends Grid {
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
            // TODO：后改为科室码来匹配
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
                    this.setSortname("appointment_num");
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
    
    /**
     * 按条件搜索（已迁移到solr）
     * 
     * @author xl.liu
     */
    public static class GuahaoSearchQuery extends HospitalGuahaoQuery {
        private String searchstr;
        // 1按医院、医生、科室、疾病搜索医院
        // 2按医院、医生、科室、疾病搜索医生
        // 3按医院、医生、科室、搜索医生
        private String tag;
        // 医生职称
        private String positional;
        // 科室
        private String deptName;
        
        public String getSearchstr() {
            return searchstr;
        }

        public void setSearchstr(String searchstr) {
            this.searchstr = searchstr;
        }
        
        public String getTag() {
            return tag;
        }

        public void setTag(String tag) {
            this.tag = tag;
        }
        
        @SingleValue(tableAlias="t1", column = "dept_name", equal = "=")
        public String getDeptName() {
            return deptName;
        }

        public void setDeptName(String deptName) {
            this.deptName = deptName;
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
                buff.append("(");
                buff.append(" (instr(t1.dept_name, '"+searchstr+"') > 0) or (instr(t2.field, '"+searchstr+"') > 0) ");
                if ("1".equals(getTag())) {// 按医院查
                    buff.append(" or ");
                    buff.append(" (t1.hospital_name LIKE '%"+searchstr+"%') ");
                    query.addParam("tag", "1");
                } else if("2".equals(getTag()) || "3".equals(getTag())) {// 按医生查
                    buff.append(" or ");
                    buff.append(" (t2.doctor_name LIKE '%"+searchstr+"%') ");
                    query.addParam("tag", "2");
                }
                buff.append(")");
                query.addWithoutValueCondition(new WithoutValueCondition(buff.toString()));
            }
            return query;
        }
    }
}
