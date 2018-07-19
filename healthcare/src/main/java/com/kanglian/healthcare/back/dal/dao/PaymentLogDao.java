package com.kanglian.healthcare.back.dal.dao;

import com.kanglian.healthcare.back.common.NewCrudDao;
import com.kanglian.healthcare.back.dal.pojo.PaymentLog;

public interface PaymentLogDao extends NewCrudDao<PaymentLog> {
    
    public PaymentLog getByOrderNo(String orderNo);
}
