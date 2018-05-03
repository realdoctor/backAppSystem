package com.realdoctor.back.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.easyway.business.framework.mybatis.annotion.SingleValue;
import com.easyway.business.framework.pojo.Grid;
import com.easyway.business.framework.springmvc.controller.CrudController;
import com.easyway.business.framework.springmvc.result.ResultBody;
import com.easyway.business.framework.springmvc.result.ResultUtil;
import com.easyway.business.framework.util.DateUtil;
import com.realdoctor.back.dal.pojo.GoodsShopcar;
import com.realdoctor.back.service.GoodsShopcarBo;

/**
 * 购物车
 * 
 * @author xl.liu
 */
@RestController
@RequestMapping(value = "/cart")
public class GoodsShopcarController extends CrudController<GoodsShopcar, GoodsShopcarBo> {

    /**
     * 购物车列表
     * 
     * @param query
     * @return
     * @throws Exception
     */
    @GetMapping
    public ResultBody list(ShopcarQuery query) throws Exception {
        return super.list(query);
    }

    /**
     * 添加到购物车
     * 
     * @param query
     * @return
     * @throws Exception
     */
    @PostMapping("/addCartItem")
    public ResultBody addCartItem(@RequestBody GoodsShopcar cart) throws Exception {
        Integer userId = cart.getUserId();
        Integer goodsId = cart.getGoodsId();
        Integer num = cart.getNum();
        if (userId == null) {
            return ResultUtil.error("用户未登录！");
        }
        if (goodsId == null) {
            return ResultUtil.error("加入购物车失败！");
        }
        if (num == null) {
            cart.setNum(1);
        }
        cart.setAddTime(DateUtil.currentDate());
        this.bo.save(cart);
        return ResultUtil.success();
    }
    
    /**
     * 删除购物车单个商品
     * 
     * @param query
     * @return
     * @throws Exception
     */
    @PostMapping("/deleteCartItem")
    public ResultBody deleteCartItem(@RequestBody GoodsShopcar cart) throws Exception {
        GoodsShopcar newcart = this.bo.get(cart);
        if(newcart == null){
            return ResultUtil.error("删除商品不存在！");
        }
        this.bo.delete(newcart);
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
    public ResultBody clearCartItems(@RequestParam String userId) throws Exception {
        if (userId == null) {
            return ResultUtil.error("用户不存在！");
        }
        this.bo.clearCart(Integer.valueOf(userId));
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
