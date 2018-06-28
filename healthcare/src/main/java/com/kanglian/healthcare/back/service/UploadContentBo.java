package com.kanglian.healthcare.back.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.kanglian.healthcare.back.common.NewCrudBo;
import com.kanglian.healthcare.back.dal.dao.UploadContentDao;
import com.kanglian.healthcare.back.dal.pojo.UploadContent;
import com.kanglian.healthcare.exception.DBException;

@Service
public class UploadContentBo extends NewCrudBo<UploadContent, UploadContentDao> {

    public List<UploadContent> getByPubId(String orderId) {
        try {
            return this.dao.getByPubId(orderId);
        } catch (Exception ex) {
            throw new DBException(ex);
        }
    }

    public void deleteByPubId(String orderId) {
        try {
            this.dao.deleteByPubId(orderId);
        } catch (Exception ex) {
            throw new DBException(ex);
        }
    }

    public int updateByPubId(UploadContent content) {
        try {
            return this.dao.updateByPubId(content);
        } catch (Exception ex) {
            throw new DBException(ex);
        }
    }

}
