package com.kanglian.healthcare.back.dal.dao;

import java.util.List;
import com.kanglian.healthcare.back.common.NewCrudDao;
import com.kanglian.healthcare.back.dal.pojo.UploadContent;

public interface UploadContentDao extends NewCrudDao<UploadContent> {

    public List<UploadContent> getByPubId(String orderId);

    public void deleteByPubId(String orderId);

    public int updateByPubId(UploadContent content);
    
    public int deleteContent(int[] arr);
}
