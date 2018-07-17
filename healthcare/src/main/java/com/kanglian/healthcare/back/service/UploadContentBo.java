package com.kanglian.healthcare.back.service;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.easyway.business.framework.util.DateUtil;
import com.kanglian.healthcare.back.common.NewCrudBo;
import com.kanglian.healthcare.back.dal.dao.HealthNewsDao;
import com.kanglian.healthcare.back.dal.dao.UploadContentDao;
import com.kanglian.healthcare.back.dal.dao.UserDao;
import com.kanglian.healthcare.back.dal.pojo.HealthNews;
import com.kanglian.healthcare.back.dal.pojo.UploadContent;
import com.kanglian.healthcare.exception.DBException;

@Service
public class UploadContentBo extends NewCrudBo<UploadContent, UploadContentDao> {

    @Autowired
    private UserDao userDao;
    @Autowired
    private HealthNewsDao healthNewsDao;
    
    public List<UploadContent> getByPubId(String pubId) {
        try {
            return this.dao.getByPubId(pubId);
        } catch (Exception ex) {
            throw new DBException(ex);
        }
    }

    public void deleteByPubId(String pubId) {
        try {
            this.dao.deleteByPubId(pubId);
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
    
    public UploadContent getByUserIdAndType(Integer userId, Integer type) {
        try {
            return this.dao.getByUserIdAndType(userId, type);
        } catch (Exception ex) {
            throw new DBException(ex);
        }
    }

    public int updateByUserIdAndType(UploadContent content) {
        try {
            return this.dao.updateByUserIdAndType(content);
        } catch (Exception ex) {
            throw new DBException(ex);
        }
    }
    
    /**
     * 保存上传资讯
     * 
     * @param uploadContent
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public void saveUploadContent(final UploadContent uploadContent) {
        try {
            this.dao.save(uploadContent);
            Map<String, Object> retMap = userDao.getDoctorInfoByUserId(uploadContent.getUserId());
            if (retMap != null) {
                HealthNews healthNews = new HealthNews();
                healthNews.setUserId(uploadContent.getUserId());
                healthNews.setPubId(uploadContent.getPubId());
                healthNews.setCreateDate(DateUtil.currentDate());
                if (uploadContent.getType() == 1) {// 视频
                    healthNews.setNewsTypeId(9);
                    healthNews.setNewsName("视频");
                    healthNews.setPhotoAddress(uploadContent.getPic());
                } else {// 图文
                    healthNews.setNewsTypeId(8);
                    healthNews.setNewsName("图文");
                    healthNews.setPhotoAddress(uploadContent.getSrc());
                }
                healthNews.setPrice(uploadContent.getPrice());
                healthNews.setPutOn(1);
                healthNews.setCommend(1);
                healthNews.setArticle(uploadContent.getContent());
                healthNews.setNewsAuthor(retMap.get("doctorName") + "");
                healthNews.setAuthorProfer(retMap.get("positional") + "");
                healthNews.setAuthorHos(retMap.get("hospitalName") + "");
                healthNews.setAuthorDept(retMap.get("deptName") + "");
                healthNewsDao.save(healthNews);
            }
        } catch (Exception ex) {
            throw new DBException(ex);
        }
    }
}
