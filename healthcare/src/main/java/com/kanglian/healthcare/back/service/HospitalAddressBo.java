package com.kanglian.healthcare.back.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.easyway.business.framework.bo.CrudBo;
import com.easyway.business.framework.mybatis.query.ConditionQuery;
import com.easyway.business.framework.pojo.Grid;
import com.kanglian.healthcare.back.common.DaoExecutorAdapter;
import com.kanglian.healthcare.back.common.DaoTemplate;
import com.kanglian.healthcare.back.dal.dao.HospitalAddressDao;
import com.kanglian.healthcare.back.dal.pojo.HospitalAddress;
import com.kanglian.healthcare.exception.DBException;

@Service
public class HospitalAddressBo extends CrudBo<HospitalAddress, HospitalAddressDao> {

    /**
     * 搜索医院、医生、科室、疾病
     * 
     * @return 医院 | 医生
     */
    public List<HospitalAddress> queryForHospitalAndDoctor(ConditionQuery query) {
        try {
            return dao.queryForHospitalAndDoctor(query);
        } catch (Exception ex) {
            throw new DBException(ex);
        }
    }
    
    /**
     * 分页搜索医院、医生、科室、疾病
     * 
     * @return 医院 | 医生
     */
    public Grid queryForHospitalAndDoctor(final Grid grid) {
        return DaoTemplate.pagingList(new DaoExecutorAdapter() {

            @Override
            @SuppressWarnings("unchecked")
            public List<HospitalAddress> selectList() throws Exception {
                ConditionQuery query = grid.buildConditionQuery();
                return getDao().queryForHospitalAndDoctor(query);
            }

        }, grid.getPageNum(), grid.getPageSize());
    }
}
