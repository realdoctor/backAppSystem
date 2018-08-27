package com.kanglian.healthcare.back.service;

import java.util.List;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.easyway.business.framework.bo.CrudBo;
import com.kanglian.healthcare.back.common.DaoExecutorAdapter;
import com.kanglian.healthcare.back.common.DaoTemplate;
import com.kanglian.healthcare.back.dal.dao.HospitalDeptCategoryDao;
import com.kanglian.healthcare.back.dal.pojo.HospitalDeptCategory;
import com.kanglian.healthcare.back.dal.pojo.HospitalDeptCategoryItem;

@Service
public class HospitalDeptCategoryBo extends CrudBo<HospitalDeptCategory, HospitalDeptCategoryDao> {
    
    @Autowired
    private HospitalDeptCategoryItemBo hospitalDeptCategoryItemBo;
    
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
    
    public void testUpdateDeptCode() {
        List<HospitalDeptCategory> deptCategoryList = this.queryAll();
        for (HospitalDeptCategory deptCategory : deptCategoryList) {
            String sss = RandomStringUtils.randomAlphanumeric(16);
            deptCategory.setDeptCode("DEPT"+sss);
            this.dao.update(deptCategory);
        }

        List<HospitalDeptCategoryItem> deptCategoryItemList = hospitalDeptCategoryItemBo.queryAll();
        for (HospitalDeptCategoryItem deptCategoryItem : deptCategoryItemList) {
            int retryNum = 0;
            do {
                try {
                    String sss = RandomStringUtils.randomAlphanumeric(16);
                    deptCategoryItem.setDeptCodeNew("DE4rO-" + sss);
                    this.hospitalDeptCategoryItemBo.updateNew(deptCategoryItem);
                    retryNum = 1;
                } catch (Exception e) {
                    retryNum = 0;
                }
            } while (retryNum <= 0);
        }
    }
}
