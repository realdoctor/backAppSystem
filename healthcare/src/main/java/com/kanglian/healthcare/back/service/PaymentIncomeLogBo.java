package com.kanglian.healthcare.back.service;

import org.springframework.stereotype.Service;
import com.kanglian.healthcare.back.common.NewCrudBo;
import com.kanglian.healthcare.back.dal.dao.PaymentIncomeLogDao;
import com.kanglian.healthcare.back.dal.pojo.PaymentIncomeLog;
import com.kanglian.healthcare.exception.DBException;

@Service
public class PaymentIncomeLogBo extends NewCrudBo<PaymentIncomeLog,PaymentIncomeLogDao> {

    public PaymentIncomeLog getByOrderNo(String orderNo) {
        try {
            return this.dao.getByOrderNo(orderNo);
        } catch (Exception ex) {
            throw new DBException(ex);
        }
    }
}
