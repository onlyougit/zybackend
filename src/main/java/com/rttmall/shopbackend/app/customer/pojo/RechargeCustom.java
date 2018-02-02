package com.rttmall.shopbackend.app.customer.pojo;

import com.rttmall.shopbackend.app.agent.pojo.Agent;
import com.rttmall.shopbackend.app.agent.pojo.Business;
import com.rttmall.shopbackend.enums.RechargeWay;

import java.util.Date;

/**
 * Created by wangweibin on 2017/12/24.
 */
public class RechargeCustom extends Recharge{
    private Agent agent;

    private Business business;

    private Customer customer;

    private Integer loginId;

    private RechargeWay rechargeWayEnum;

    private Date startDateTime;

    private Date endDateTime;

    private String mobile;

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getMobile() {

        return mobile;
    }

    public void setStartDateTime(Date startDateTime) {
        this.startDateTime = startDateTime;
    }

    public void setEndDateTime(Date endDateTime) {
        this.endDateTime = endDateTime;
    }

    public Date getStartDateTime() {

        return startDateTime;
    }

    public Date getEndDateTime() {
        return endDateTime;
    }

    public void setRechargeWayEnum(RechargeWay rechargeWayEnum) {
        this.rechargeWayEnum = rechargeWayEnum;
    }

    public RechargeWay getRechargeWayEnum() {

        return rechargeWayEnum;
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

    public void setLoginId(Integer loginId) {
        this.loginId = loginId;
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

    public Integer getLoginId() {
        return loginId;
    }
}
