package com.kanglian.healthcare.back.dal.pojo;

import java.util.Date;
import com.alibaba.fastjson.annotation.JSONField;
import com.easyway.business.framework.pojo.BasePojo;

public class AskQuestionAnswer extends BasePojo implements Comparable<AskQuestionAnswer> {
	private static final long serialVersionUID = 1L;
	@JSONField(name="questionId")
	private Long id;
	private String messageId;
	private Integer userId;
	private Integer toUser;
	private String question;
	private String answer;
	private String status;
    private Date addTime;
	private Date lastUpdateDtime;
	private String src;
	private String remark;
    private String patientRealName;
    private String doctorRealName;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getMessageId() {
		return messageId;
	}
	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Integer getToUser() {
		return toUser;
	}
	public void setToUser(Integer toUser) {
		this.toUser = toUser;
	}
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
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
	public String getSrc() {
        return src;
    }
    public void setSrc(String src) {
        this.src = src;
    }
    public String getRemark() {
        return remark;
    }
    public void setRemark(String remark) {
        this.remark = remark;
    }
	public String getPatientRealName() {
        return patientRealName;
    }
    public void setPatientRealName(String patientRealName) {
        this.patientRealName = patientRealName;
    }
    public String getDoctorRealName() {
        return doctorRealName;
    }
    public void setDoctorRealName(String doctorRealName) {
        this.doctorRealName = doctorRealName;
    }
    @Override
    public int compareTo(AskQuestionAnswer o) {
        return id.compareTo(o.getId());
    }
}
