package com.kanglian.healthcare.back.dao;

import java.util.List;
import com.easyway.business.framework.dao.CrudDao;
import com.kanglian.healthcare.back.pojo.GoodsOrderItem;

public interface GoodsOrderItemDao extends CrudDao<GoodsOrderItem> {

    /**
     * 订单号获取订单明细
     * 
     * @param orderNo
     * @return
     */
    public List<GoodsOrderItem> getGoodsOrderDetailByOrderNo(String orderNo);

    /**
     * 订单id获取订单明细
     * 
     * @param goodsOrderId
     * @return
     */
    public List<GoodsOrderItem> getGoodsOrderDetail(Integer goodsOrderId);
}
