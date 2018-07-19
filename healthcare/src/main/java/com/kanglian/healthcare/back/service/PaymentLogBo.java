package com.kanglian.healthcare.back.service;

import org.springframework.stereotype.Service;
import com.kanglian.healthcare.back.common.NewCrudBo;
import com.kanglian.healthcare.back.dal.dao.PaymentLogDao;
import com.kanglian.healthcare.back.dal.pojo.PaymentLog;
import com.kanglian.healthcare.exception.DBException;

@Service
public class PaymentLogBo extends NewCrudBo<PaymentLog,PaymentLogDao> {

    public PaymentLog getByOrderNo(String orderNo) {
        try {
            return this.dao.getByOrderNo(orderNo);
        } catch (Exception ex) {
            throw new DBException(ex);
        }
    }
}
