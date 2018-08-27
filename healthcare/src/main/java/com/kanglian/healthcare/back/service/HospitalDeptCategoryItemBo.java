package com.kanglian.healthcare.back.service;

import com.easyway.business.framework.bo.CrudBo;
import org.springframework.stereotype.Service;
import com.kanglian.healthcare.back.common.DaoExecutorAdapter;
import com.kanglian.healthcare.back.common.DaoTemplate;
import com.kanglian.healthcare.back.dal.dao.HospitalDeptCategoryItemDao;
import com.kanglian.healthcare.back.dal.pojo.HospitalDeptCategoryItem;

@Service
public class HospitalDeptCategoryItemBo extends CrudBo<HospitalDeptCategoryItem, HospitalDeptCategoryItemDao> {
    public void updateNew(final HospitalDeptCategoryItem pojo) {
        DaoTemplate.execute(new DaoExecutorAdapter() {

            @Override
            public void execute() throws Exception {
                getDao().updateNew(pojo);
            }

        });
    }
}
