package com.kanglian.healthcare.back.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.easyway.business.framework.mybatis.annotion.SingleValue;
import com.easyway.business.framework.mybatis.query.ConditionQuery;
import com.easyway.business.framework.mybatis.query.condition.WithoutValueCondition;
import com.easyway.business.framework.pojo.Grid;
import com.easyway.business.framework.springmvc.controller.CrudController;
import com.easyway.business.framework.springmvc.result.ResultBody;
import com.easyway.business.framework.springmvc.result.ResultUtil;
import com.easyway.business.framework.util.StringUtil;
import com.kanglian.healthcare.back.dal.pojo.Hospital;
import com.kanglian.healthcare.back.service.HospitalBo;

/**
 * 医院搜索
 * 
 * @author xl.liu
 */
@RestController
public class HospitalSearchController extends CrudController<Hospital, HospitalBo> {

    /**
     * 按条件医院、医生、科室、疾病搜索（已迁移到solr）
     * 
     * @param searchstr
     * @return
     * @throws Exception
     */
    @GetMapping("/guahao/search")
    public ResultBody search(GuahaoSearchQuery query) throws Exception {
        return ResultUtil.success(this.bo.queryHospitalAndDoctor(query));
    }
    
    /**
     * 按条件搜索
     * 
     * @author xl.liu
     */
    public static class GuahaoSearchQuery extends Grid {
        private String searchstr;
        // 1按医院、医生、科室、疾病搜索医院
        // 2按医院、医生、科室、疾病搜索医生
        // 3按医院、医生、科室、搜索医生
        private String tag;
        // 医生职称
        private String positional;
        // 科室
        private String deptName;
        
        /**
         * 1）综合排序 3）预约量排序
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
            
            if(StringUtil.isNotBlank(cityName)) {
                query.addWithoutValueCondition(new WithoutValueCondition(" (instr('"+cityName+"', t.province) > 0) or (instr('"+cityName+"', t.city) > 0) "));
            }
            
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
