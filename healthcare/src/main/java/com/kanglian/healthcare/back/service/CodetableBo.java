package com.kanglian.healthcare.back.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.easyway.business.framework.bo.CrudBo;
import com.kanglian.healthcare.back.constant.Constants;
import com.kanglian.healthcare.back.dao.CodetableDao;
import com.kanglian.healthcare.back.pojo.Codetable;
import com.kanglian.healthcare.exception.DBException;
import com.kanglian.healthcare.util.RedisCacheManager;

@Service
public class CodetableBo extends CrudBo<Codetable, CodetableDao> {

    @Autowired
    private RedisCacheManager redisCacheManager;

    public List<Codetable> findSectionList(String name) {
        try {
            return this.dao.findSectionList(name);
        } catch (Exception ex) {
            throw new DBException(ex);
        }
    }

    public void initCacheData() {
        try {
            // 卡类型代码
            List<Codetable> mapList = findSectionList(Constants.STD_CARD_TYPE);
            set(Constants.STD_CARD_TYPE, convertList2Map(mapList));

            // 生理性别代码
            mapList = findSectionList(Constants.STD_SEX);
            set(Constants.STD_SEX, convertList2Map(mapList));

            // 身份证件类别代码
            mapList = findSectionList(Constants.STD_PERSON_ID_TYPE);
            set(Constants.STD_PERSON_ID_TYPE, convertList2Map(mapList));

            // 民族类别代码
            mapList = findSectionList(Constants.STD_NATIONALITY);
            set(Constants.STD_NATIONALITY, convertList2Map(mapList));

            // ABO血型代码
            mapList = findSectionList(Constants.STD_BLOOD_TYPE);
            set(Constants.STD_BLOOD_TYPE, convertList2Map(mapList));

            // Rh（D）血型代码
            mapList = findSectionList(Constants.STD_RH_RESULT);
            set(Constants.STD_RH_RESULT, convertList2Map(mapList));

            // 学历代码
            mapList = findSectionList(Constants.STD_EDUCATION);
            set(Constants.STD_EDUCATION, convertList2Map(mapList));

            // 职业分类与代码
            mapList = findSectionList(Constants.STD_OCCUPATION);
            set(Constants.STD_OCCUPATION, convertList2Map(mapList));

            // 婚姻状况代码
            mapList = findSectionList(Constants.STD_MARRIAGE);
            set(Constants.STD_MARRIAGE, convertList2Map(mapList));

            // 过敏源代码
            mapList = findSectionList(Constants.STD_ALLERGIC_SOURCE);
            set(Constants.STD_ALLERGIC_SOURCE, convertList2Map(mapList));

            // 既往常见疾病种类代码
            mapList = findSectionList(Constants.STD_PAST_COMMON_DISEASE);
            set(Constants.STD_PAST_COMMON_DISEASE, convertList2Map(mapList));

            // 家族疾病史代码
            mapList = findSectionList(Constants.STD_FAMILY_HISTORY_DISEASES);
            set(Constants.STD_FAMILY_HISTORY_DISEASES, convertList2Map(mapList));

            // 中药使用类别代码
            // mapList = findSectionList(Constant.STD_CHINESE_MEDICINE_TYPE);
            // set(Constant.STD_CHINESE_MEDICINE_TYPE, convertList2Map(mapList));

            // 中药使用类别代码
            // mapList = findSectionList(Constant.STD_CHINESE_MEDICINE_SYMPTOMS);
            // set(Constant.STD_CHINESE_MEDICINE_SYMPTOMS, convertList2Map(mapList));

            // 中医病证分类与代码
            // mapList = findSectionList(Constant.STD_CHINESE_MEDICINE_SYMPTOMS);
            // set(Constant.STD_CHINESE_MEDICINE_SYMPTOMS, convertList2Map(mapList));

            // 用药途径代码
            // mapList = findSectionList(Constant.STD_USE_MEDICINE_WAY);
            // set(Constant.STD_USE_MEDICINE_WAY, convertList2Map(mapList));

            // 中华人民共和国行政区划代码
            mapList = findSectionList(Constants.STD_ADMINISTRATIVE_DIVISION);
            set(Constants.STD_ADMINISTRATIVE_DIVISION, convertList2Map(mapList));

            // 世界各国和地区名称代码
            mapList = findSectionList(Constants.STD_COUNTRY);
            set(Constants.STD_COUNTRY, convertList2Map(mapList));

            // 入院途径代码
            // mapList = findSectionList(Constant.STD_IN_PATH);
            // set(Constant.STD_IN_PATH, convertList2Map(mapList));

            // 疾病诊断（ICD10）
            mapList = findSectionList(Constants.STD_ICD10);
            set(Constants.STD_ICD10, convertList2Map(mapList));

            // 药物剂型代码
            // mapList = findSectionList(Constant.STD_MEDICINE_FORM);
            // set(Constant.STD_MEDICINE_FORM, convertList2Map(mapList));

            // 药物使用频率
            // mapList = findSectionList(Constant.STD_MEDICATION_USE_FREQUENCY);
            // set(Constant.STD_MEDICATION_USE_FREQUENCY, convertList2Map(mapList));
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    private void set(String key, Object value) {
        redisCacheManager.setCacheObject(key, value);
    }

    private Map<String, Object> convertList2Map(List<Codetable> mapList) {
        if (mapList == null)
            return null;
        Map<String, Object> resultMap = new HashMap<String, Object>();
        for (Codetable entry : mapList) {
            resultMap.put(entry.getCode(), entry.getDescription());
        }
        return resultMap;
    }
}
