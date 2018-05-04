package com.kanglian.healthcare.back.dal.dao;

import com.easyway.business.framework.dao.CrudDao;
import com.kanglian.healthcare.back.dal.pojo.GoodsShopcar;

public interface GoodsShopcarDao extends CrudDao<GoodsShopcar> {

    public void clearCart(int userId);
    
    public void deleteByIds(String[] ids);
}
