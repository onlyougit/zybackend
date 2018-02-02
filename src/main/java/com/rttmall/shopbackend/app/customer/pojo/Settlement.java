package com.rttmall.shopbackend.app.customer.pojo;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

public class Settlement {
    private Integer id;

    private Integer customerId;

    private Date createTime;

    private Integer dailyTradeNumber;

    private BigDecimal sumDailyServiceCharge;

    private BigDecimal sumDailyServiceChargeCost;

    private BigDecimal dailyRebate;

    private Integer status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getDailyTradeNumber() {
        return dailyTradeNumber;
    }

    public void setDailyTradeNumber(Integer dailyTradeNumber) {
        this.dailyTradeNumber = dailyTradeNumber;
    }

    public BigDecimal getSumDailyServiceCharge() {
        return sumDailyServiceCharge;
    }

    public void setSumDailyServiceCharge(BigDecimal sumDailyServiceCharge) {
        this.sumDailyServiceCharge = sumDailyServiceCharge;
    }

    public BigDecimal getSumDailyServiceChargeCost() {
        return sumDailyServiceChargeCost;
    }

    public void setSumDailyServiceChargeCost(BigDecimal sumDailyServiceChargeCost) {
        this.sumDailyServiceChargeCost = sumDailyServiceChargeCost;
    }

    public BigDecimal getDailyRebate() {
        return dailyRebate;
    }

    public void setDailyRebate(BigDecimal dailyRebate) {
        this.dailyRebate = dailyRebate;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}