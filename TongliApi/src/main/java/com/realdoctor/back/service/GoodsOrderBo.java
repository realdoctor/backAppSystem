package com.realdoctor.back.service;

import com.realdoctor.back.dal.dao.GoodsOrderDao;
import com.realdoctor.back.dal.pojo.GoodsOrder;
import com.easyway.business.framework.bo.CrudBo;
import org.springframework.stereotype.Service;

@Service
public class GoodsOrderBo extends CrudBo<GoodsOrder,GoodsOrderDao> {

}
