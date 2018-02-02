package com.rttmall.shopbackend.app.customer.pojo;

import java.util.Date;

import com.rttmall.shopbackend.app.agent.pojo.Agent;
import com.rttmall.shopbackend.app.agent.pojo.Business;
import com.rttmall.shopbackend.enums.CustomerStatus;

public class CustomerCustom extends Customer{

	private Agent agent;
	
	private Business business;

	private Fund fund;
	
	private CustomerStatus customerStatusEnum;

	private Date startDateTime;
	
	private Date endDateTime;
	
	private Integer loginId;
	
	
	
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

	public Fund getFund() {
		return fund;
	}

	public void setFund(Fund fund) {
		this.fund = fund;
	}

	public CustomerStatus getCustomerStatusEnum() {
		return customerStatusEnum;
	}

	public void setCustomerStatusEnum(CustomerStatus customerStatusEnum) {
		this.customerStatusEnum = customerStatusEnum;
	}
	
	
}
