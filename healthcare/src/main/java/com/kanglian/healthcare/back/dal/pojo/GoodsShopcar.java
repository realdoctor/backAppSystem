package com.kanglian.healthcare.back.dal.pojo;

import java.util.Date;
import com.alibaba.fastjson.annotation.JSONField;
import com.easyway.business.framework.json.annotion.JsonData;
import com.easyway.business.framework.pojo.BasePojo;

public class GoodsShopcar extends BasePojo {
	private static final long serialVersionUID = 1L;
	@JSONField(name="goodsShopcarId")
	private Integer id;
	private Integer userId;
	private Integer goodsId;
	private Integer num;
	private String status;
	private Date addTime;
	private Date updateTime;
	@JsonData(field="goodsShopcarId")
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Integer getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(Integer goodsId) {
		this.goodsId = goodsId;
	}
	public Integer getNum() {
		return num;
	}
	public void setNum(Integer num) {
		this.num = num;
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
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
	/**
	 * 扩展字段
	 */
	private String name;
    private Integer store;
    private Double cost;
	private String bigPic;
    private String smallPic;
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Integer getStore() {
        return store;
    }
    public void setStore(Integer store) {
        this.store = store;
    }
    public Double getCost() {
        return cost;
    }
    public void setCost(Double cost) {
        this.cost = cost;
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
}
