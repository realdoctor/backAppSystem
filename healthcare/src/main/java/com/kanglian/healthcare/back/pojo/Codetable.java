package com.kanglian.healthcare.back.pojo;

import com.easyway.business.framework.pojo.BasePojo;

public class Codetable extends BasePojo {
	private static final long serialVersionUID = 1L;
	private String section;
	private String name;
	private String code;
	private String description;
	private String codeNotes;
	public String getSection() {
		return section;
	}
	public void setSection(String section) {
		this.section = section;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getCodeNotes() {
		return codeNotes;
	}
	public void setCodeNotes(String codeNotes) {
		this.codeNotes = codeNotes;
	}
}
