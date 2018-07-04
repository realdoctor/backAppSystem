package com.kanglian.healthcare.back.service;

import com.kanglian.healthcare.back.dal.pojo.PaymentOrder;
import com.easyway.business.framework.bo.CrudBo;
import org.springframework.stereotype.Service;
import com.kanglian.healthcare.back.dal.dao.PaymentOrderDao;

@Service
public class PaymentOrderBo extends CrudBo<PaymentOrder,PaymentOrderDao> {

}
