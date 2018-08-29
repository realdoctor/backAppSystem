package com.kanglian.healthcare.back.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.easyway.business.framework.bo.CrudBo;
import com.kanglian.healthcare.back.dao.GoodsOrderItemDao;
import com.kanglian.healthcare.back.pojo.GoodsOrderItem;
import com.kanglian.healthcare.common.DaoExecutorAdapter;
import com.kanglian.healthcare.common.DaoTemplate;

@Service
public class GoodsOrderItemBo extends CrudBo<GoodsOrderItem, GoodsOrderItemDao> {

    /**
     * 订单号获取订单明细
     * 
     * @param orderNo
     * @return
     */
    public List<GoodsOrderItem> getGoodsOrderDetailByOrderNo(final String orderNo) {
        return DaoTemplate.selectList(new DaoExecutorAdapter() {

            @SuppressWarnings("unchecked")
            @Override
            public List<GoodsOrderItem> selectList() throws Exception {
                return getDao().getGoodsOrderDetailByOrderNo(orderNo);
            }

        });
    }

    /**
     * 订单id获取订单明细
     * 
     * @param goodsOrderId
     * @return
     */
    public List<GoodsOrderItem> getGoodsOrderDetail(final String goodsOrderId) {
        return DaoTemplate.selectList(new DaoExecutorAdapter() {

            @SuppressWarnings("unchecked")
            @Override
            public List<GoodsOrderItem> selectList() throws Exception {
                return getDao().getGoodsOrderDetail(Integer.valueOf(goodsOrderId));
            }
            
        });
    }
}
