package com.realdoctor.back.dal.dao;

import com.realdoctor.back.dal.pojo.GoodsShopcar;
import com.easyway.business.framework.dao.CrudDao;

public interface GoodsShopcarDao extends CrudDao<GoodsShopcar> {

    public void clearCart(int userId);
}
