package com.kanglian.healthcare.back.service;

import com.kanglian.healthcare.exception.DBException;
import com.easyway.business.framework.bo.CrudBo;
import com.easyway.business.framework.mybatis.query.ConditionQuery;
import com.easyway.business.framework.pojo.Grid;
import java.util.List;
import org.springframework.stereotype.Service;
import com.kanglian.healthcare.back.dao.HospitalDao;
import com.kanglian.healthcare.back.pojo.Hospital;
import com.kanglian.healthcare.common.DaoExecutorAdapter;
import com.kanglian.healthcare.common.DaoTemplate;

@Service
public class HospitalBo extends CrudBo<Hospital,HospitalDao> {

    /**
     * 搜索医院、医生、科室、疾病
     * 
     * @return 医院 | 医生
     */
    public List<Hospital> queryHospitalAndDoctor(ConditionQuery query) {
        try {
            return dao.queryHospitalAndDoctor(query);
        } catch (Exception ex) {
            throw new DBException(ex);
        }
    }
    
    /**
     * 分页搜索医院、医生、科室、疾病
     * 
     * @return 医院 | 医生
     */
    public Grid queryHospitalAndDoctor(final Grid grid) {
        return DaoTemplate.pagingList(new DaoExecutorAdapter() {

            @Override
            @SuppressWarnings("unchecked")
            public List<Hospital> selectList() throws Exception {
                ConditionQuery query = grid.buildConditionQuery();
                return getDao().queryHospitalAndDoctor(query);
            }

        }, grid.getPageNum(), grid.getPageSize());
    }
    
    public Hospital getByCode(String hospitalCode) {
        try {
            return dao.getByCode(hospitalCode);
        } catch (Exception ex) {
            throw new DBException(ex);
        }
    }
}
