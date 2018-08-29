package com.kanglian.healthcare.back.dao;

import java.util.List;
import com.kanglian.healthcare.back.pojo.PaymentLog;
import com.kanglian.healthcare.common.NewCrudDao;

public interface PaymentLogDao extends NewCrudDao<PaymentLog> {
    
    public List<PaymentLog> getByOrderNo(String orderNo);
}
