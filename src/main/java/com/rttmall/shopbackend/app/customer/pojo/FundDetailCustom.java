package com.rttmall.shopbackend.app.customer.pojo;

import com.rttmall.shopbackend.app.agent.pojo.Agent;
import com.rttmall.shopbackend.app.agent.pojo.Business;
import com.rttmall.shopbackend.enums.FlowWay;

import java.util.Date;

public class FundDetailCustom extends FundDetail{

	private Agent agent;
	
	private Business business;
	
	private Customer customer;
	
	private FlowWay flowWayEnum;
	
	private Date startDateTime;
	
	private Date endDateTime;

	private Integer loginId;

	public void setLoginId(Integer loginId) {
		this.loginId = loginId;
	}

	public Integer getLoginId() {

		return loginId;
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

	public FlowWay getFlowWayEnum() {
		return flowWayEnum;
	}

	public void setFlowWayEnum(FlowWay flowWayEnum) {
		this.flowWayEnum = flowWayEnum;
	}
	
	
}
