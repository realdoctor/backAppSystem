package com.realdoctor.back.service;

import com.realdoctor.back.dal.dao.GoodsDao;
import com.easyway.business.framework.bo.CrudBo;
import org.springframework.stereotype.Service;
import com.realdoctor.back.dal.pojo.Goods;

@Service
public class GoodsBo extends CrudBo<Goods,GoodsDao> {

}
