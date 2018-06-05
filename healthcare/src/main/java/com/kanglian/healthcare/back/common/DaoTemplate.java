package com.kanglian.healthcare.back.common;

import java.util.List;
import com.easyway.business.framework.pojo.Grid;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.kanglian.healthcare.exception.DBException;

/**
 * DAO执行模板类
 * 
 * @author xl.liu
 */
public final class DaoTemplate<T> {
    /**
     * 执行DAO
     * 
     * @param executor
     * @throws Exception
     */
    public static void execute(IDaoExecutor executor) throws Exception {
        try {
            executor.execute();
        } catch (DBException ex) {
            throw ex;
        } catch (Exception ex) {
            throw ex;
        }
    }

    /**
     * 执行DAO，返回一条数据
     * 
     * @param executor
     * @return
     * @throws Exception
     */
    public static <T> T selectOne(IDaoExecutor executor) throws Exception {
        try {
            return executor.<T>selectOne();
        } catch (DBException ex) {
            throw ex;
        } catch (Exception ex) {
            throw ex;
        }
    }

    /**
     * 执行DAO，返回数据列表
     * 
     * @param executor
     * @return
     * @throws Exception
     */
    public static <T> List<T> selectList(IDaoExecutor executor) throws Exception {
        try {
            return executor.<T>selectList();
        } catch (DBException ex) {
            throw ex;
        } catch (Exception ex) {
            throw ex;
        }
    }

    /**
     * 执行DAO，返回分页列表
     * 
     * @param executor
     * @throws DBException
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public static Grid pagingList(IDaoExecutor executor, int pageNum, int pageSize)
            throws DBException {
        try {
            PageHelper.startPage(pageNum, pageSize);
            List newsList = executor.selectList();
            PageInfo page = new PageInfo(newsList);
            final Grid grid = new Grid();
            grid.setPageNum(page.getPageNum());
            grid.setPageSize(page.getPageSize());
            grid.setPages(page.getPages());
            grid.setTotal((int) page.getTotal());
            grid.setList(page.getList());
            return grid;
        } catch (Exception ex) {
            throw new DBException(ex);
        }
    }
}
