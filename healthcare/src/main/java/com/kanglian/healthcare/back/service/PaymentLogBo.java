package com.kanglian.healthcare.back.service;

import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;
import com.kanglian.healthcare.back.dao.PaymentLogDao;
import com.kanglian.healthcare.back.pojo.PaymentLog;
import com.kanglian.healthcare.common.NewCrudBo;
import com.kanglian.healthcare.exception.DBException;

@Service
public class PaymentLogBo extends NewCrudBo<PaymentLog,PaymentLogDao> {

    public List<PaymentLog> getByOrderNo(String orderNo) {
        try {
            return this.dao.getByOrderNo(orderNo);
        } catch (Exception ex) {
            throw new DBException(ex);
        }
    }
    
    /**
     * 统计支出、收入金额
     * 
     * @param userId
     * @return
     */
    public Map<String, Object> sumPayment(Integer userId){
        try {
            return this.dao.sumPayment(userId);
        } catch (Exception ex) {
            throw new DBException(ex);
        }
    }
}
