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
     * Dao执行模板
     */
    private DaoTemplate daoTemplate;

    /**
     * 构造器
     */
    public NewCrudBo() {
        daoTemplate = new DaoTemplate();
    }

    /**
     * 获取Dao
     * 
     * @return
     */
    public Dao getDao() {
        return this.dao;
    }

    /**
     * 获取Dao执行模板对象
     * 
     * @return
     */
    public DaoTemplate getDaoTemplate() {
        return this.daoTemplate;
    }

    /**
     * 分页列表
     * 
     * @param grid
     * @return
     */
    public Grid frontList(final Grid grid) {
        return getDaoTemplate().pagingList(grid, new IDaoExecutor<T>() {

            @Override
            public List<T> execute(ConditionQuery query) throws Exception {
                return getDao().frontList(query);
            }

        });
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
    protected Page queryAndSetPage(final Page page, final ConditionQuery query) {
        final Dao dao = this.dao;
        com.github.pagehelper.Page<T> list = PageHelper
                .startPage(page.getPageNum(), page.getPageSize()).doSelectPage(new ISelect() {
                    @Override
                    public void doSelect() {
                        query.addParam("pageSize", 0);// 取消掉原来的分页
                        dao.query(query);
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

    public interface IDaoExecutor<T> {
        /**
         * 执行操作
         * 
         * @throws Exception
         */
        List<T> execute(ConditionQuery query) throws Exception;
    }

    /**
     * DAO执行模板类
     * 
     * @author xl.liu
     */
    public class DaoTemplate {
        /**
         * 执行DAO
         * 
         * @param executor
         * @throws DBException
         */
        public Grid pagingList(final Grid grid, IDaoExecutor<T> executor) throws DBException {
            try {
                PageHelper.startPage(grid.getPageNum(), grid.getPageSize());
                ConditionQuery query = grid.buildConditionQuery();
                query.addParam("pageSize", 0);// 取消分页，采用分页插件
                List<T> newsList = executor.execute(query);
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
    }
}
