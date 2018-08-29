package com.kanglian.healthcare.back.service;

import com.easyway.business.framework.bo.CrudBo;
import org.springframework.stereotype.Service;
import com.kanglian.healthcare.back.dao.HospitalDeptCategoryItemDao;
import com.kanglian.healthcare.back.pojo.HospitalDeptCategoryItem;
import com.kanglian.healthcare.common.DaoExecutorAdapter;
import com.kanglian.healthcare.common.DaoTemplate;

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
