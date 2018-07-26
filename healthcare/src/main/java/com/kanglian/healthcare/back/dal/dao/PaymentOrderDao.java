package com.kanglian.healthcare.back.dal.dao;

import java.util.List;
import com.easyway.business.framework.dao.CrudDao;
import com.kanglian.healthcare.back.dal.pojo.PaymentOrder;

public interface PaymentOrderDao extends CrudDao<PaymentOrder> {

    public PaymentOrder getByOrderNo(String orderNo);
    
    public List<PaymentOrder> getByGoodsId(String goodsId);
}
