package com.kanglian.healthcare.back.common;

import java.util.List;

import com.easyway.business.framework.bo.CrudBo;
import com.easyway.business.framework.mybatis.query.ConditionQuery;
import com.easyway.business.framework.pojo.BasePojo;
import com.easyway.business.framework.pojo.Grid;

/**
 * 
 * @param <T>
 * @param <Dao>
 */
public class Crud2Bo<T extends BasePojo, Dao extends Crud2Dao<T>> extends CrudBo<T, Dao> {

    /**
     * 列表展示
     * 
     * @param grid
     * @return
     */
    public Grid queryFrontList(final Grid grid) {
        ConditionQuery query = grid.buildConditionQuery();
        int cnt = dao.frontListCnt(query);
        grid.setTotal(cnt);
        if (cnt > 0) {
            List<T> newsList = dao.frontList(query);
            grid.setList(newsList);
        }
        return new Grid(grid);// 防止脏数据入侵
    }

    public List<T> queryForList(Grid query) {
        return dao.query(query.buildConditionQuery());
    }

}
