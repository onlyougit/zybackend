package com.rttmall.shopbackend.app.customer.pojo;

import com.rttmall.shopbackend.app.agent.pojo.Agent;
import com.rttmall.shopbackend.app.agent.pojo.Business;
import com.rttmall.shopbackend.enums.BankCardStatus;

public class BankCardCustom extends BankCard{

	private Agent agent;

    private Business business;

    private Customer customer;

    private Integer loginId;
    
    private BankCardStatus bankCardStatusEnum;

    private String userName;
    
    
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public BankCardStatus getBankCardStatusEnum() {
		return bankCardStatusEnum;
	}

	public void setBankCardStatusEnum(BankCardStatus bankCardStatusEnum) {
		this.bankCardStatusEnum = bankCardStatusEnum;
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

    
}
