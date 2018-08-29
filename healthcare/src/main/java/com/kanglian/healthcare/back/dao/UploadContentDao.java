package com.kanglian.healthcare.back.dao;

import java.util.List;
import com.kanglian.healthcare.back.pojo.UploadContent;
import com.kanglian.healthcare.common.NewCrudDao;

public interface UploadContentDao extends NewCrudDao<UploadContent> {

    public List<UploadContent> getByPubId(String orderId);

    public void deleteByPubId(String orderId);

    public int updateByPubId(UploadContent content);
    
    public int deleteContent(int[] arr);
}
