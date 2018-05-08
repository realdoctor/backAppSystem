package com.kanglian.healthcare.back.dal.dao;

import com.easyway.business.framework.dao.CrudDao;
import com.kanglian.healthcare.back.dal.pojo.GoodsOrder;

public interface GoodsOrderDao extends CrudDao<GoodsOrder> {

    /**
     * 更新订单状态
     * 
     * @param order
     */
    public void updateOrderStatus(GoodsOrder order);

    /**
     * 根据订单号获取订单
     * 
     * @param orderNo
     * @return
     */
    public GoodsOrder findGoodsOrder(String orderNo);
}
