package com.kanglian.healthcare.back.common;

import java.util.List;
import com.easyway.business.framework.bo.CrudBo;
import com.easyway.business.framework.mybatis.query.ConditionQuery;
import com.easyway.business.framework.pojo.BasePojo;
import com.easyway.business.framework.pojo.Grid;
import com.easyway.business.framework.pojo.Page;
import com.github.pagehelper.ISelect;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.kanglian.healthcare.exception.DBException;

/**
 * @param <T>
 * @param <Dao>
 */
public class NewCrudBo<T extends BasePojo, Dao extends NewCrudDao<T>> extends CrudBo<T, Dao> {

    /**
     * 分页列表
     * 
     * @param grid
     * @return
     */
    public Grid frontList(final Grid grid) {
        try {
            PageHelper.startPage(grid.getPageNum(), grid.getPageSize());
            ConditionQuery query = grid.buildConditionQuery();
            query.addParam("pageSize", 0);// 取消掉分页，采用分页插件
            List<T> newsList = dao.frontList(query);
            PageInfo<T> page = new PageInfo<T>(newsList);
            grid.setPageNum(page.getPageNum());
            grid.setPageSize(page.getPageSize());
            grid.setPages(page.getPages());
            grid.setTotal((int) page.getTotal());
            grid.setList(page.getList());
            return new Grid(grid);// 防止脏数据入侵
        } catch (Exception ex) {
            throw new DBException(ex);
        }
    }

    /**
     * 根据条件查询数据库记录列表
     * 
     * @param query
     * @return
     */
    public List<T> frontList(ConditionQuery query) {
        try {
            if (query != null) {
                query.addParam("pageSize", 0);
            }
            return this.dao.frontList(query);
        } catch (Exception ex) {
            throw new DBException(ex);
        }
    }

    /**
     * 重写原来的分页，采用分页插件
     * 
     * @DESC PageHelper的优点是，分页和Mapper.xml完全解耦。实现方式是以插件的形式，对Mybatis执行的流程进行了强化，添加了总数count和limit查询
     */
    @Override
    public Page queryAndSetPage(final Page page, final ConditionQuery query) {
        final Dao dao = this.dao;
        com.github.pagehelper.Page<T> page1 = PageHelper
                .startPage(page.getPageNum(), page.getPageSize()).doSelectPage(new ISelect() {
                    @Override
                    public void doSelect() {
                        query.addParam("pageSize", 0);// 取消掉原来的分页
                        dao.query(query);
                    }
                });
        final Grid grid = new Grid();
        grid.setPageNum(page1.getPageNum());
        grid.setPageSize(page1.getPageSize());
        grid.setPages(page1.getPages());
        grid.setTotal((int) page1.getTotal());
        grid.setList(page1.getResult());
        return grid;
    }
}
