package com.kanglian.healthcare.back.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.easyway.business.framework.bo.CrudBo;
import com.kanglian.healthcare.back.common.DaoExecutorAdapter;
import com.kanglian.healthcare.back.common.DaoTemplate;
import com.kanglian.healthcare.back.dal.dao.HospitalDeptCategoryDao;
import com.kanglian.healthcare.back.dal.pojo.HospitalDeptCategory;

@Service
public class HospitalDeptCategoryBo extends CrudBo<HospitalDeptCategory, HospitalDeptCategoryDao> {
    /**
     * 查询医疗机构各科室分类表
     * 
     * @param sid
     * @return
     */
    public List<HospitalDeptCategory> getHospitalDeptList() {
        return DaoTemplate.selectList(new DaoExecutorAdapter() {

            @SuppressWarnings("unchecked")
            @Override
            public List<HospitalDeptCategory> selectList() throws Exception {
                return getDao().getHospitalDeptList();
            }

        });
    }

    public List<HospitalDeptCategory> getHospitalDeptItemList(final Integer sid) {
        return DaoTemplate.selectList(new DaoExecutorAdapter() {

            @SuppressWarnings("unchecked")
            @Override
            public List<HospitalDeptCategory> selectList() throws Exception {
                return getDao().getHospitalDeptItemList(sid);
            }

        });
    }
}
