package com.kanglian.healthcare.back.service;

import com.kanglian.healthcare.back.dal.pojo.NewsAd;
import com.easyway.business.framework.bo.CrudBo;
import org.springframework.stereotype.Service;
import com.kanglian.healthcare.back.dal.dao.NewsAdDao;

@Service
public class NewsAdBo extends CrudBo<NewsAd,NewsAdDao> {

}
