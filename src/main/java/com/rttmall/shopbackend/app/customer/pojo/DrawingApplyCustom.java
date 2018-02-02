package com.rttmall.shopbackend.app.customer.pojo;

import com.rttmall.shopbackend.app.agent.pojo.Agent;
import com.rttmall.shopbackend.app.agent.pojo.Business;
import com.rttmall.shopbackend.enums.DrawingApplyStatus;

import java.util.Date;

/**
 * Created by wangweibin on 2017/12/25.
 */
public class DrawingApplyCustom extends DrawingApply{
    private Agent agent;

    private Business business;

    private Customer customer;

    private Integer loginId;

    private Date startDateTime;

    private Date endDateTime;
    
    private DrawingApplyStatus drawingApplyStatusEnum;
    
    private String userName;
    
    
    

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Agent getAgent() {
		return agent;
	}

	public void setAgent(Agent agent) {
		this.agent = agent;
	}

	public Business getBusiness() {
		return business;
	}

	public void setBusiness(Business business) {
		this.business = business;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Integer getLoginId() {
		return loginId;
	}

	public void setLoginId(Integer loginId) {
		this.loginId = loginId;
	}

	public Date getStartDateTime() {
		return startDateTime;
	}

	public void setStartDateTime(Date startDateTime) {
		this.startDateTime = startDateTime;
	}

	public Date getEndDateTime() {
		return endDateTime;
	}

	public void setEndDateTime(Date endDateTime) {
		this.endDateTime = endDateTime;
	}

	public DrawingApplyStatus getDrawingApplyStatusEnum() {
		return drawingApplyStatusEnum;
	}

	public void setDrawingApplyStatusEnum(DrawingApplyStatus drawingApplyStatusEnum) {
		this.drawingApplyStatusEnum = drawingApplyStatusEnum;
	}
    
    
}
