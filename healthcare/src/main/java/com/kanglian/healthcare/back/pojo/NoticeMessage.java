package com.kanglian.healthcare.back.pojo;

import java.util.Date;
import java.util.List;
import com.alibaba.fastjson.annotation.JSONField;
import com.easyway.business.framework.json.annotion.JsonData;
import com.easyway.business.framework.pojo.BasePojo;

public class NoticeMessage extends BasePojo {
    private static final long    serialVersionUID = 1L;
    @JSONField(name = "noticeMessageId")
    private Long                 id;
    private String               noticeTypeId;
    private String               noticeType;
    private String               userId;
    private String               content;
    private Date                 addTime;
    private Date                 lastUpdateDtime;
    // 多少分钟、小时前
    private String               beforeTimeStr;

    // 一对多映射
    @JSONField(serialize=false)
    private List<NoticeDiag>     noticeDiagList;
    @JSONField(serialize=false)
    private List<NoticeDiagDrug> noticeDiagDrugList;
    @JSONField(serialize=false)
    private List<NoticeComment>  noticeCommentList;

    public List<NoticeDiag> getNoticeDiagList() {
        return noticeDiagList;
    }

    public void setNoticeDiagList(List<NoticeDiag> noticeDiagList) {
        this.noticeDiagList = noticeDiagList;
    }

    public List<NoticeDiagDrug> getNoticeDiagDrugList() {
        return noticeDiagDrugList;
    }

    public void setNoticeDiagDrugList(List<NoticeDiagDrug> noticeDiagDrugList) {
        this.noticeDiagDrugList = noticeDiagDrugList;
    }

    public List<NoticeComment> getNoticeCommentList() {
        return noticeCommentList;
    }

    public void setNoticeCommentList(List<NoticeComment> noticeCommentList) {
        this.noticeCommentList = noticeCommentList;
    }

    @JsonData(field = "noticeMessageId")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNoticeTypeId() {
        return noticeTypeId;
    }

    public void setNoticeTypeId(String noticeTypeId) {
        this.noticeTypeId = noticeTypeId;
    }

    public String getNoticeType() {
        return noticeType;
    }

    public void setNoticeType(String noticeType) {
        this.noticeType = noticeType;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }
    
    public Date getLastUpdateDtime() {
        return lastUpdateDtime;
    }

    public void setLastUpdateDtime(Date lastUpdateDtime) {
        this.lastUpdateDtime = lastUpdateDtime;
    }
    
    public String getBeforeTimeStr() {
        return beforeTimeStr;
    }

    public void setBeforeTimeStr(String beforeTimeStr) {
        this.beforeTimeStr = beforeTimeStr;
    }
}
