package com.rttmall.shopbackend.app.agent.pojo;

import java.math.BigDecimal;

public class Agent {
    private Integer id;

    private Integer loginId;

    private Integer businessId;

    private String agentName;

    private String agentPhone;

    private BigDecimal serviceChargeStandard;

    private BigDecimal serviceChargeCost;

    private String templateAccount;

    private Integer bondStandard;

    private Integer changePerson;

    private Integer status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getLoginId() {
        return loginId;
    }

    public void setLoginId(Integer loginId) {
        this.loginId = loginId;
    }

    public Integer getBusinessId() {
        return businessId;
    }

    public void setBusinessId(Integer businessId) {
        this.businessId = businessId;
    }

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    public String getAgentPhone() {
        return agentPhone;
    }

    public void setAgentPhone(String agentPhone) {
        this.agentPhone = agentPhone;
    }

    public BigDecimal getServiceChargeStandard() {
        return serviceChargeStandard;
    }

    public void setServiceChargeStandard(BigDecimal serviceChargeStandard) {
        this.serviceChargeStandard = serviceChargeStandard;
    }

    public BigDecimal getServiceChargeCost() {
        return serviceChargeCost;
    }

    public void setServiceChargeCost(BigDecimal serviceChargeCost) {
        this.serviceChargeCost = serviceChargeCost;
    }

    public String getTemplateAccount() {
        return templateAccount;
    }

    public void setTemplateAccount(String templateAccount) {
        this.templateAccount = templateAccount;
    }

    public Integer getBondStandard() {
        return bondStandard;
    }

    public void setBondStandard(Integer bondStandard) {
        this.bondStandard = bondStandard;
    }

    public Integer getChangePerson() {
        return changePerson;
    }

    public void setChangePerson(Integer changePerson) {
        this.changePerson = changePerson;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}