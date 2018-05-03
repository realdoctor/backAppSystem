package com.kanglian.healthcare.back.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.easyway.business.framework.mybatis.annotion.SingleValue;
import com.easyway.business.framework.pojo.Grid;
import com.easyway.business.framework.springmvc.controller.CrudController;
import com.easyway.business.framework.springmvc.result.ResultBody;
import com.kanglian.healthcare.back.dal.pojo.Goods;
import com.kanglian.healthcare.back.service.GoodsBo;

/**
 * 商品列表
 * 
 * @author xl.liu
 */
@RestController
@RequestMapping(value = "/goods")
public class GoodsController extends CrudController<Goods, GoodsBo> {

    @GetMapping
    public ResultBody list(GoodsQuery query) throws Exception {
        return super.list(query);
    }

    public static class GoodsQuery extends Grid {

        // 商品分类
        private String categoryId;

        @SingleValue(column = "category_id", equal = "=")
        public String getCategoryId() {
            return categoryId;
        }

        public void setCategoryId(String categoryId) {
            this.categoryId = categoryId;
        }
    }
}
