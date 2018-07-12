package com.kanglian.healthcare.back.dal.dao;

import com.kanglian.healthcare.back.dal.pojo.PaymentOrder;
import java.util.Map;
import com.easyway.business.framework.dao.CrudDao;

public interface PaymentOrderDao extends CrudDao<PaymentOrder> {

    public Map<String, Object> getByOrderNo(String orderNo);
}
