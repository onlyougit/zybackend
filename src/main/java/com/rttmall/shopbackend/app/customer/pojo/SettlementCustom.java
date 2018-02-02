package com.rttmall.shopbackend.app.customer.pojo;

import java.util.Date;

import com.rttmall.shopbackend.app.agent.pojo.Agent;
import com.rttmall.shopbackend.app.agent.pojo.Business;
import com.rttmall.shopbackend.enums.SettlementStatus;

public class SettlementCustom extends Settlement{

	private SettlementStatus settlementStatusEnum;

    private Agent agent;

    private Business business;
    
    private Customer customer;
    
    private Integer loginId;

	private Date startDateTime;
	
	private Date endDateTime;
    
	
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

	public Integer getLoginId() {
		return loginId;
	}

	public void setLoginId(Integer loginId) {
		this.loginId = loginId;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public SettlementStatus getSettlementStatusEnum() {
		return settlementStatusEnum;
	}

	public void setSettlementStatusEnum(SettlementStatus settlementStatusEnum) {
		this.settlementStatusEnum = settlementStatusEnum;
	}
	
}
