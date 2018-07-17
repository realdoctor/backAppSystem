package com.kanglian.healthcare.back.dal.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.kanglian.healthcare.back.common.NewCrudDao;
import com.kanglian.healthcare.back.dal.pojo.UploadContent;

public interface UploadContentDao extends NewCrudDao<UploadContent> {

    public List<UploadContent> getByPubId(String orderId);

    public void deleteByPubId(String orderId);

    public int updateByPubId(UploadContent content);
    
    public UploadContent getByUserIdAndType(@Param("userId") Integer userId, @Param("type") Integer type);
    
    public int updateByUserIdAndType(UploadContent content);
}
