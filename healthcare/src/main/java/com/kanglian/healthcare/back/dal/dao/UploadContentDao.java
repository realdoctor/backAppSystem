package com.kanglian.healthcare.back.dal.dao;

import com.kanglian.healthcare.back.dal.pojo.UploadContent;
import java.util.List;
import com.easyway.business.framework.dao.CrudDao;

public interface UploadContentDao extends CrudDao<UploadContent> {

    public List<UploadContent> getByUUId(String orderId);

    public void deleteByUUId(String orderId);

    public int updateByUUId(UploadContent content);
}
