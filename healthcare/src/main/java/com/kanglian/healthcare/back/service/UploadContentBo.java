package com.kanglian.healthcare.back.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.kanglian.healthcare.back.common.NewCrudBo;
import com.kanglian.healthcare.back.dal.dao.UploadContentDao;
import com.kanglian.healthcare.back.dal.pojo.UploadContent;
import com.kanglian.healthcare.exception.DBException;

@Service
public class UploadContentBo extends NewCrudBo<UploadContent, UploadContentDao> {

    public List<UploadContent> getByUUId(String orderId) {
        try {
            return this.dao.getByUUId(orderId);
        } catch (Exception ex) {
            throw new DBException(ex);
        }
    }

    public void deleteByUUId(String orderId) {
        try {
            this.dao.deleteByUUId(orderId);
        } catch (Exception ex) {
            throw new DBException(ex);
        }
    }

    public int updateByUUId(UploadContent content) {
        try {
            return this.dao.updateByUUId(content);
        } catch (Exception ex) {
            throw new DBException(ex);
        }
    }

}
