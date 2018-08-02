package com.kanglian.healthcare.back.web;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.kanglian.healthcare.back.service.HospitalDeptCategoryBo;

/**
 * 医院相关
 * 
 * @author xl.liu
 */
@RestController
public class HospitalController extends CrudController<Hospital, HospitalBo> {

    @Autowired
    private HospitalDeptCategoryBo hospitalDeptCategoryBo;

    /**
     * 医院一览
     * 
     * @param query
     * @return
     * @throws Exception
     */
    @GetMapping("/guahao/hospital")
    public ResultBody list(HospitalQuery query) throws Exception {
        return super.list(query);
    }

    /**
     * 科室分类列表
     * 
     * @return
     * @throws Exception
     */
    @GetMapping("/guahao/hospital/deptCategory")
    public ResultBody hospitalDeptCategory() throws Exception {
        return ResultUtil.success(hospitalDeptCategoryBo.getHospitalDeptList());
    }

    public static class HospitalQuery extends Grid {
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

        @SingleValue(tableAlias = "t1", column = "hospital_level", equal = "=")
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
            if (StringUtil.isNotEmpty(sortstr)) {
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
            if (StringUtil.isNotBlank(cityName)) {
                query.addWithoutValueCondition(new WithoutValueCondition(" (instr('" + cityName
                        + "', t.province) > 0) or (instr('" + cityName + "', t.city) > 0) "));
            }
            return query;
        }

    }

}
