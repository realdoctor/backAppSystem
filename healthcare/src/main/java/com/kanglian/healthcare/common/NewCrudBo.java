package com.kanglian.healthcare.common;

import java.util.List;
import com.easyway.business.framework.bo.CrudBo;
import com.easyway.business.framework.mybatis.query.ConditionQuery;
import com.easyway.business.framework.pojo.BasePojo;
import com.easyway.business.framework.pojo.Grid;
import com.easyway.business.framework.pojo.Page;
import com.github.pagehelper.ISelect;
import com.github.pagehelper.PageHelper;
import com.kanglian.healthcare.exception.DBException;

/**
 * @param <T>
 * @param <Dao>
 */
public class NewCrudBo<T extends BasePojo, Dao extends NewCrudDao<T>> extends CrudBo<T, Dao> {

    /**
     * 分页查询列表
     * 
     * @param grid
     * @return
     */
    public Grid frontList(final Grid grid) {
        return DaoTemplate.pagingList(new DaoExecutorAdapter() {

            @Override
            @SuppressWarnings("unchecked")
            public List<T> selectList() throws Exception {
                ConditionQuery query = grid.buildConditionQuery();
                query.addParam("pageSize", 0);// 采用分页插件
                return getDao().frontList(query);
            }

        }, grid.getPageNum(), grid.getPageSize());
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
                query.addParam("pageSize", 0);// 不分页
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
    protected Page queryAndSetPage(final Page page, final ConditionQuery query) {
        com.github.pagehelper.Page<T> list = PageHelper
                .startPage(page.getPageNum(), page.getPageSize()).doSelectPage(new ISelect() {
                    @Override
                    public void doSelect() {
                        query.addParam("pageSize", 0);// 取消掉原来的分页
                        getDao().query(query);
                    }
                });
        final Grid grid = new Grid();
        grid.setPageNum(list.getPageNum());
        grid.setPageSize(list.getPageSize());
        grid.setPages(list.getPages());
        grid.setTotal((int) list.getTotal());
        grid.setList(list.getResult());
        return grid;
    }

}
