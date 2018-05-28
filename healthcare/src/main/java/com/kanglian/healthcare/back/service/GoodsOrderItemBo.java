package com.kanglian.healthcare.back.service;

import com.easyway.business.framework.bo.CrudBo;
import com.kanglian.healthcare.back.dal.dao.GoodsOrderItemDao;
import com.kanglian.healthcare.back.dal.pojo.GoodsOrderItem;
import com.kanglian.healthcare.exception.DBException;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class GoodsOrderItemBo extends CrudBo<GoodsOrderItem, GoodsOrderItemDao> {

    /**
     * 订单号获取订单明细
     * 
     * @param orderNo
     * @return
     */
    public List<GoodsOrderItem> getGoodsOrderDetailByOrderNo(String orderNo) {
        try {
            return this.dao.getGoodsOrderDetailByOrderNo(orderNo);
        } catch (Exception ex) {
            throw new DBException(ex);
        }
    }
    
    /**
     * 订单id获取订单明细
     * 
     * @param goodsOrderId
     * @return
     */
    public List<GoodsOrderItem> getGoodsOrderDetail(String goodsOrderId){
        try {
            return this.dao.getGoodsOrderDetail(Integer.valueOf(goodsOrderId));
        } catch (Exception ex) {
            throw new DBException(ex);
        }
    }
}
