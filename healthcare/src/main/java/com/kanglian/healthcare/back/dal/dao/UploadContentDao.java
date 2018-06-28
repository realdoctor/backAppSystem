package com.kanglian.healthcare.back.dal.dao;

import java.util.List;
import com.kanglian.healthcare.back.common.NewCrudDao;
import com.kanglian.healthcare.back.dal.pojo.UploadContent;

public interface UploadContentDao extends NewCrudDao<UploadContent> {

    public List<UploadContent> getByUUId(String orderId);

    public void deleteByUUId(String orderId);

    public int updateByUUId(UploadContent content);
}
