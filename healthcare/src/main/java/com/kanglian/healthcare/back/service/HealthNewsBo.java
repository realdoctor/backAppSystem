package com.kanglian.healthcare.back.service;

import com.easyway.business.framework.bo.CrudBo;
import org.springframework.stereotype.Service;
import com.kanglian.healthcare.back.dal.pojo.HealthNews;
import com.kanglian.healthcare.back.dal.dao.HealthNewsDao;

@Service
public class HealthNewsBo extends CrudBo<HealthNews,HealthNewsDao> {

}
