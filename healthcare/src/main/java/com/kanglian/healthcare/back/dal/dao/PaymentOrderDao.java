package com.kanglian.healthcare.back.dal.dao;

import com.easyway.business.framework.dao.CrudDao;
import com.kanglian.healthcare.back.dal.pojo.PaymentOrder;

public interface PaymentOrderDao extends CrudDao<PaymentOrder> {

    public PaymentOrder getByOrderNo(String orderNo);
}
