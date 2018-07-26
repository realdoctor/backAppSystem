package com.kanglian.healthcare.back.dal.dao;

import java.util.List;
import com.kanglian.healthcare.back.common.NewCrudDao;
import com.kanglian.healthcare.back.dal.pojo.PaymentLog;

public interface PaymentLogDao extends NewCrudDao<PaymentLog> {
    
    public List<PaymentLog> getByOrderNo(String orderNo);
}
