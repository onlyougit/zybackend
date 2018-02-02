package com.rttmall.shopbackend.app.customer.pojo;

import com.rttmall.shopbackend.app.agent.pojo.Agent;
import com.rttmall.shopbackend.app.agent.pojo.Business;

/**
 * Created by wangweibin on 2017/12/24.
 */
public class FundCustom extends Fund {
    private Agent agent;

    private Business business;

    private Customer customer;

    private Integer loginId;

    public void setLoginId(Integer loginId) {
        this.loginId = loginId;
    }

    public Integer getLoginId() {

        return loginId;
    }

    public void setAgent(Agent agent) {
        this.agent = agent;
    }

    public void setBusiness(Business business) {
        this.business = business;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Agent getAgent() {

        return agent;
    }

    public Business getBusiness() {
        return business;
    }

    public Customer getCustomer() {
        return customer;
    }
}
