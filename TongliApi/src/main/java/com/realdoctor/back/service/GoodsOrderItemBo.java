package com.realdoctor.back.service;

import com.realdoctor.back.dal.pojo.GoodsOrderItem;
import com.easyway.business.framework.bo.CrudBo;
import org.springframework.stereotype.Service;
import com.realdoctor.back.dal.dao.GoodsOrderItemDao;

@Service
public class GoodsOrderItemBo extends CrudBo<GoodsOrderItem,GoodsOrderItemDao> {

}
