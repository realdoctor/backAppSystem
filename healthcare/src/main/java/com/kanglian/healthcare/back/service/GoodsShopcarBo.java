package com.kanglian.healthcare.back.service;

import org.springframework.stereotype.Service;

import com.easyway.business.framework.bo.CrudBo;
import com.kanglian.healthcare.back.dal.dao.GoodsShopcarDao;
import com.kanglian.healthcare.back.dal.pojo.GoodsShopcar;
import com.kanglian.healthcare.exception.DBException;

@Service
public class GoodsShopcarBo extends CrudBo<GoodsShopcar, GoodsShopcarDao> {

    public void clearCart(int userId) {
        try {
            this.dao.clearCart(userId);
        } catch (Exception ex) {
            throw new DBException(ex);
        }
    }

    public void deleteByIds(String[] ids) {
        try {
            this.dao.deleteByIds(ids);
        } catch (Exception ex) {
            throw new DBException(ex);
        }
    }
}
