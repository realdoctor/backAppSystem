package com.kanglian.healthcare.back.dal.pojo;

import java.util.Date;
import com.alibaba.fastjson.annotation.JSONField;
import com.easyway.business.framework.json.annotion.JsonData;
import com.easyway.business.framework.pojo.BasePojo;

public class GoodsOrderItem extends BasePojo {
	private static final long serialVersionUID = 1L;
	@JSONField(name="goodsOrderItemId")
	private Long id;
	private Integer goodsOrderId;
	private Integer goodsId;
	private Integer goodsNum;
	private Double goodsPrice;
	private Date addTime;
	
	/**
     * 扩展字段
     */
	private String name;
	@JSONField(name="goodsDescription")
    private String description;
    private String bigPic;
    private String smallPic;
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
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
    
	@JsonData(field="goodsOrderItemId")
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Integer getGoodsOrderId() {
		return goodsOrderId;
	}
	public void setGoodsOrderId(Integer goodsOrderId) {
		this.goodsOrderId = goodsOrderId;
	}
	public Integer getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(Integer goodsId) {
		this.goodsId = goodsId;
	}
	public Integer getGoodsNum() {
		return goodsNum;
	}
	public void setGoodsNum(Integer goodsNum) {
		this.goodsNum = goodsNum;
	}
	public Double getGoodsPrice() {
		return goodsPrice;
	}
	public void setGoodsPrice(Double goodsPrice) {
		this.goodsPrice = goodsPrice;
	}
	public Date getAddTime() {
		return addTime;
	}
	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}
}
