package com.kanglian.healthcare.back.dal.pojo;

import java.util.Date;
import com.easyway.business.framework.pojo.BasePojo;

public class UploadPatient extends BasePojo {
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String messageId;
	private Integer questionId;
    private Integer userId;
    private String title;
    private String content;
	private String src;
	private String remark;
	private Date addTime;
	// 问题内容
	private String question;
	// 回答内容
	private String answer;
    // 患者名字
	private String patientRealName;
	// 医生名字
	private String doctorRealName;
    public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
    public String getMessageId() {
        return messageId;
    }
    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }
    public Integer getQuestionId() {
        return questionId;
    }
    public void setQuestionId(Integer questionId) {
        this.questionId = questionId;
    }
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
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
	public Date getAddTime() {
		return addTime;
	}
	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}
	/**
	 * 
	 * @return
	 */
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
}
