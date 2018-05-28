package com.kanglian.healthcare.back.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.easyway.business.framework.mybatis.annotion.SingleValue;
import com.easyway.business.framework.pojo.Grid;
import com.easyway.business.framework.springmvc.controller.CrudController;
import com.easyway.business.framework.springmvc.result.ResultBody;
import com.easyway.business.framework.springmvc.result.ResultUtil;
import com.easyway.business.framework.util.StringUtil;
import com.kanglian.healthcare.authorization.annotation.Authorization;
import com.kanglian.healthcare.back.dal.pojo.GoodsOrder;
import com.kanglian.healthcare.back.service.GoodsOrderBo;
import com.kanglian.healthcare.back.service.GoodsOrderItemBo;
import com.kanglian.healthcare.exception.InvalidParamException;

@Authorization
@RestController
@RequestMapping(value = "/goods/order")
public class GoodsOrderController extends CrudController<GoodsOrder, GoodsOrderBo> {

    @Autowired
    private GoodsOrderItemBo goodsOrderItemBo;

    /**
     * 我的订单列表
     * 
     * @param user
     * @return
     * @throws Exception
     */
    @GetMapping("/orderList")
    public ResultBody list(GoodsOrderQuery query) throws Exception {
        if (StringUtil.isEmpty(query.getUserId())) {
            return ResultUtil.error("用户未登录！");
        }
        return super.list(query);
    }
    
    /**
     * 我的订单明细
     * 
     * @param user
     * @return
     * @throws Exception
     */
    @GetMapping("/orderDetail")
    public ResultBody myGoodsOrderDetail(@RequestParam("goodsOrderId") String goodsOrderId)
            throws Exception {
        if (StringUtil.isEmpty(goodsOrderId)) {
            throw new InvalidParamException("goodsOrderId");
        }
        return ResultUtil.success(goodsOrderItemBo.getGoodsOrderDetail(goodsOrderId));
    }
    
    public static class GoodsOrderQuery extends Grid {

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
