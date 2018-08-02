package com.kanglian.healthcare.back.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.easyway.business.framework.mybatis.annotion.SingleValue;
import com.easyway.business.framework.pojo.Grid;
import com.easyway.business.framework.springmvc.controller.CrudController;
import com.easyway.business.framework.springmvc.result.ResultBody;
import com.easyway.business.framework.springmvc.result.ResultUtil;
import com.easyway.business.framework.util.DateUtil;
import com.kanglian.healthcare.authorization.annotation.Authorization;
import com.kanglian.healthcare.authorization.annotation.CurrentUser;
import com.kanglian.healthcare.back.dal.cond.ApList;
import com.kanglian.healthcare.back.dal.pojo.GoodsShopcar;
import com.kanglian.healthcare.back.dal.pojo.User;
import com.kanglian.healthcare.back.service.GoodsShopcarBo;
import com.kanglian.healthcare.exception.InvalidParamException;

/**
 * 购物车
 * 
 * @author xl.liu
 */
@Authorization
@RestController
@RequestMapping(value = "/goods/cart")
public class GoodsShopcarController extends CrudController<GoodsShopcar, GoodsShopcarBo> {

    /**
     * 购物车列表
     * 
     * @param query
     * @return
     * @throws Exception
     */
    @GetMapping("/list")
    public ResultBody list(@CurrentUser User user, ShopcarQuery query) throws Exception {
        if (user.getUserId() == null) {
            throw new InvalidParamException("userId");
        }
        query.setUserId(String.valueOf(user.getUserId()));
        return ResultUtil.success(this.bo.query(query));
    }

    /**
     * 添加到购物车
     * 
     * @param query
     * @return
     * @throws Exception
     */
    @PostMapping("/addCartItem")
    public ResultBody addCartItem(@CurrentUser User user, @RequestBody GoodsShopcar cart) throws Exception {
        Integer goodsId = cart.getGoodsId();
        Integer num = cart.getNum();
        if (user.getUserId() == null) {
            throw new InvalidParamException("userId");
        }
        if (goodsId == null) {
            throw new InvalidParamException("goodsId");
        }
        if (num == null) {
            cart.setNum(1);
        }
        Integer userId = user.getUserId().intValue();
        cart.setUserId(userId);
        cart.setAddTime(DateUtil.currentDate());
        // 判断是否同一样商品
        GoodsShopcar goodsShopcar = this.bo.findGoodsShopcar(userId, goodsId);
        if (goodsShopcar != null) {// 说明已添加过
            goodsShopcar.setNum(goodsShopcar.getNum() + cart.getNum());
            goodsShopcar.setUpdateTime(DateUtil.currentDate());
            this.bo.update(goodsShopcar);
        } else {
            this.bo.save(cart);
        }
        
        return ResultUtil.success();
    }
    
    /**
     * 删除购物车
     * 
     * @param cond
     * @return
     * @throws Exception
     */
    @PostMapping("/deleteCartItem")
    public ResultBody deleteCartItem(@RequestBody ApList cond) throws Exception {
        this.bo.deleteByIds(cond.getIds());
        return ResultUtil.success();
    }
    
    /**
     * 清空购物车
     * 
     * @param query
     * @return
     * @throws Exception
     */
    @PostMapping("/clearCart")
    public ResultBody clearCartItems(@CurrentUser User user) throws Exception {
        if (user.getUserId() == null) {
            throw new InvalidParamException("userId");
        }
        this.bo.clearCart(user.getUserId().intValue());
        return ResultUtil.success();
    }

    public static class ShopcarQuery extends Grid {

        private String userId;

        @SingleValue(column = "user_id", equal = "=")
        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }
    }
}
