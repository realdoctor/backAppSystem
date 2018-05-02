package com.realdoctor.back.listener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.realdoctor.back.constants.Constant;
import com.realdoctor.back.dal.pojo.Codetable;
import com.realdoctor.back.service.CodetableBo;
import com.realdoctor.util.CacheManager;

@Component
public class InitInfoListener {

    @Autowired
    private CodetableBo codetableBo;

    @PostConstruct
    public void init() {
//        // 卡类型代码
//        List<Codetable> mapList = codetableBo.findSectionList(Constant.STD_CARD_TYPE);
//        CacheManager.set(Constant.STD_CARD_TYPE, convertList2Map(mapList));
//
//        // 生理性别代码
//        mapList = codetableBo.findSectionList(Constant.STD_SEX);
//        CacheManager.set(Constant.STD_SEX, convertList2Map(mapList));
//
//        // 身份证件类别代码
//        mapList = codetableBo.findSectionList(Constant.STD_PERSON_ID_TYPE);
//        CacheManager.set(Constant.STD_PERSON_ID_TYPE, convertList2Map(mapList));
//
//        // 民族类别代码
//        mapList = codetableBo.findSectionList(Constant.STD_NATIONALITY);
//        CacheManager.set(Constant.STD_NATIONALITY, convertList2Map(mapList));
//
//        // ABO血型代码
//        mapList = codetableBo.findSectionList(Constant.STD_BLOOD_TYPE);
//        CacheManager.set(Constant.STD_BLOOD_TYPE, convertList2Map(mapList));
//
//        // Rh（D）血型代码
//        mapList = codetableBo.findSectionList(Constant.STD_RH_RESULT);
//        CacheManager.set(Constant.STD_RH_RESULT, convertList2Map(mapList));
//
//        // 学历代码
//        mapList = codetableBo.findSectionList(Constant.STD_EDUCATION);
//        CacheManager.set(Constant.STD_EDUCATION, convertList2Map(mapList));
//
//        // 职业分类与代码
//        mapList = codetableBo.findSectionList(Constant.STD_OCCUPATION);
//        CacheManager.set(Constant.STD_OCCUPATION, convertList2Map(mapList));
//
//        // 婚姻状况代码
//        mapList = codetableBo.findSectionList(Constant.STD_MARRIAGE);
//        CacheManager.set(Constant.STD_MARRIAGE, convertList2Map(mapList));
//
//        // 过敏源代码
//        mapList = codetableBo.findSectionList(Constant.STD_ALLERGIC_SOURCE);
//        CacheManager.set(Constant.STD_ALLERGIC_SOURCE, convertList2Map(mapList));
//
//        // 既往常见疾病种类代码
//        mapList = codetableBo.findSectionList(Constant.STD_PAST_COMMON_DISEASE);
//        CacheManager.set(Constant.STD_PAST_COMMON_DISEASE, convertList2Map(mapList));
//
//        // 家族疾病史代码
//        mapList = codetableBo.findSectionList(Constant.STD_FAMILY_HISTORY_DISEASES);
//        CacheManager.set(Constant.STD_FAMILY_HISTORY_DISEASES, convertList2Map(mapList));
//
//        // 中药使用类别代码
//        mapList = codetableBo.findSectionList(Constant.STD_CHINESE_MEDICINE_TYPE);
//        CacheManager.set(Constant.STD_CHINESE_MEDICINE_TYPE, convertList2Map(mapList));
//
//        // 中药使用类别代码
//        mapList = codetableBo.findSectionList(Constant.STD_CHINESE_MEDICINE_SYMPTOMS);
//        CacheManager.set(Constant.STD_CHINESE_MEDICINE_SYMPTOMS, convertList2Map(mapList));
//
//        // 中医病证分类与代码
//        mapList = codetableBo.findSectionList(Constant.STD_CHINESE_MEDICINE_SYMPTOMS);
//        CacheManager.set(Constant.STD_CHINESE_MEDICINE_SYMPTOMS, convertList2Map(mapList));
//
//        // 用药途径代码
//        mapList = codetableBo.findSectionList(Constant.STD_USE_MEDICINE_WAY);
//        CacheManager.set(Constant.STD_USE_MEDICINE_WAY, convertList2Map(mapList));
//
//        // 中华人民共和国行政区划代码
//        mapList = codetableBo.findSectionList(Constant.STD_ADMINISTRATIVE_DIVISION);
//        CacheManager.set(Constant.STD_ADMINISTRATIVE_DIVISION, convertList2Map(mapList));
//
//        // 世界各国和地区名称代码
//        mapList = codetableBo.findSectionList(Constant.STD_COUNTRY);
//        CacheManager.set(Constant.STD_COUNTRY, convertList2Map(mapList));
//
//        // 入院途径代码
//        mapList = codetableBo.findSectionList(Constant.STD_IN_PATH);
//        CacheManager.set(Constant.STD_IN_PATH, convertList2Map(mapList));
//
//        // 疾病诊断（ICD10）
//        mapList = codetableBo.findSectionList(Constant.STD_ICD10);
//        CacheManager.set(Constant.STD_ICD10, convertList2Map(mapList));
//
//        // 药物剂型代码
//        mapList = codetableBo.findSectionList(Constant.STD_MEDICINE_FORM);
//        CacheManager.set(Constant.STD_MEDICINE_FORM, convertList2Map(mapList));
//        
//        // 药物使用频率
//        mapList = codetableBo.findSectionList(Constant.STD_MEDICATION_USE_FREQUENCY);
//        CacheManager.set(Constant.STD_MEDICATION_USE_FREQUENCY, convertList2Map(mapList));
    }
    
    private Map<String, Object> convertList2Map(List<Codetable> mapList) {
        if (mapList == null) return null;
        Map<String, Object> resultMap = new HashMap<String, Object>();
        for (Codetable entry : mapList) {
            resultMap.put(entry.getCode(), entry.getDescription());
        }
        return resultMap;
    }
}
