package com.kanglian.healthcare.back.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.easyway.business.framework.mybatis.annotion.SingleValue;
import com.easyway.business.framework.mybatis.query.ConditionQuery;
import com.easyway.business.framework.mybatis.query.condition.WithoutValueCondition;
import com.easyway.business.framework.pojo.Grid;
import com.easyway.business.framework.springmvc.controller.CrudController;
import com.easyway.business.framework.springmvc.result.ResultBody;
import com.easyway.business.framework.util.StringUtil;
import com.kanglian.healthcare.back.constant.ApiMapping;
import com.kanglian.healthcare.back.pojo.Goods;
import com.kanglian.healthcare.back.service.GoodsBo;

/**
 * 商品列表
 * 
 * @author xl.liu
 */
@RestController
public class GoodsController extends CrudController<Goods, GoodsBo> {

    /**
     * 商品一览
     * 
     * @param query
     * @return
     * @throws Exception
     */
    @GetMapping(ApiMapping.GOODS_LIST)
    public ResultBody list(GoodsQuery query) throws Exception {
        return super.list(query);
    }

    /**
     * 商品搜索（已迁移到Solr）
     * 
     * @param query
     * @return
     * @throws Exception
     */
    @GetMapping("/goods/search")
    public ResultBody search(GoodsQuery query) throws Exception {
        return super.list(query);
    }
    
    public static class GoodsQuery extends Grid {

        // 商品分类
        private String categoryId;
        private String searchstr;

        @SingleValue(column = "category_id", equal = "=")
        public String getCategoryId() {
            return categoryId;
        }

        public void setCategoryId(String categoryId) {
            this.categoryId = categoryId;
        }
        
        public String getSearchstr() {
            return searchstr;
        }

        public void setSearchstr(String searchstr) {
            this.searchstr = searchstr;
        }

        @Override
        public ConditionQuery buildConditionQuery() {
            ConditionQuery query = super.buildConditionQuery();
            if (StringUtil.isNotBlank(searchstr)) {
                searchstr = searchstr.replaceAll("['\"<>#&]", "");
                StringBuffer buff = new StringBuffer();
                buff.append(" (instr(t.name, '"+searchstr+"') > 0) or (instr(t.description, '"+searchstr+"') > 0) ");
                query.addWithoutValueCondition(new WithoutValueCondition(buff.toString()));
            }
            return query;
        }
        
    }
}
