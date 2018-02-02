package com.rttmall.shopbackend.app.agent.pojo;

import java.math.BigDecimal;

public class Business {
    private Integer id;

    private Integer loginId;

    private String businessName;

    private BigDecimal serviceChargeStandard;

    private BigDecimal serviceChargeCost;

    private Integer bondStandard;

    private String shortName;

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

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
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

    public Integer getBondStandard() {
        return bondStandard;
    }

    public void setBondStandard(Integer bondStandard) {
        this.bondStandard = bondStandard;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }
}