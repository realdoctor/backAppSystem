package com.realdoctor.back.dal.pojo;

import java.util.Date;
import com.easyway.business.framework.pojo.BasePojo;

public class GoodsPic extends BasePojo {
	private static final long serialVersionUID = 1L;
	private Long id;
	private Integer goodsId;
	private String bigPic;
	private String smallPic;
	private Date addTime;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Integer getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(Integer goodsId) {
		this.goodsId = goodsId;
	}
	public String getBigPic() {
		return bigPic;
	}
	public void setBigPic(String bigPic) {
		this.bigPic = bigPic;
	}
	public String getSmallPic() {
		return smallPic;
	}
	public void setSmallPic(String smallPic) {
		this.smallPic = smallPic;
	}
	public Date getAddTime() {
		return addTime;
	}
	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}
}
