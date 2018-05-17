package com.kanglian.healthcare.back.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.easyway.business.framework.common.annotation.PerformanceClass;
import com.easyway.business.framework.mybatis.annotion.SingleValue;
import com.easyway.business.framework.mybatis.query.ConditionQuery;
import com.easyway.business.framework.mybatis.query.condition.WithoutValueCondition;
import com.easyway.business.framework.pojo.Grid;
import com.easyway.business.framework.springmvc.controller.CrudController;
import com.easyway.business.framework.springmvc.result.ResultBody;
import com.easyway.business.framework.springmvc.result.ResultUtil;
import com.easyway.business.framework.util.StringUtil;
import com.kanglian.healthcare.back.dal.pojo.HospitalAddress;
import com.kanglian.healthcare.back.service.HospitalAddressBo;

/**
 * 医院挂号
 * 
 * @author xl.liu
 */
@RestController
@RequestMapping(value = "/guahao")
public class HospitalGuahaoController
        extends CrudController<HospitalAddress, HospitalAddressBo> {

    /**
     * 医院列表
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
     * 按医院、医生、科室、疾病等
     * @param searchstr
     * @return
     * @throws Exception
     */
    @PerformanceClass
    @GetMapping("/search")
    public ResultBody search(GuahaoSearchQuery query) throws Exception {
        ConditionQuery conditionQuery1 = query.buildConditionQuery();
        // 按医院查
        List<HospitalAddress> hospitalList = this.bo.queryForList(conditionQuery1);
        // 按医生查
        query.appendQueryParam("searchNew", "doctor");
        ConditionQuery conditionQuery2 = query.buildConditionQuery();
        List<HospitalAddress> doctorList = this.bo.queryForList(conditionQuery2);
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("hospitalList", hospitalList);
        resultMap.put("doctorList", doctorList);
        return ResultUtil.success(resultMap);
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
                if (query.getParamMap().get("searchNew") != null) {// 按医生查
                    String queryStringSql = " (instr('" + searchstr + "', t2.doctor_name) > 0) ";
                    query.addWithoutValueCondition(new WithoutValueCondition(queryStringSql));
                } else {// 按医院查
                    String queryStringSql = " (instr('" + searchstr + "', t1.hospital_name) > 0) ";
                    query.addWithoutValueCondition(new WithoutValueCondition(queryStringSql));
                }
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
