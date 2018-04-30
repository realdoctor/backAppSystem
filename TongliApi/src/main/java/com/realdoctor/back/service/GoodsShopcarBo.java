package com.realdoctor.back.service;

import org.springframework.stereotype.Service;

import com.easyway.business.framework.bo.CrudBo;
import com.realdoctor.back.dal.dao.GoodsShopcarDao;
import com.realdoctor.back.dal.pojo.GoodsShopcar;
import com.realdoctor.exception.DBException;

@Service
public class GoodsShopcarBo extends CrudBo<GoodsShopcar, GoodsShopcarDao> {

    public void clearCart(int userId) {
        try {
            this.dao.clearCart(userId);
            ;
        } catch (Exception ex) {
            throw new DBException(ex);
        }
    }
}
