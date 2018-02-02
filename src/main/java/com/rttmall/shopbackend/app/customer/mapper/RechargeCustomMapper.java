package com.rttmall.shopbackend.app.customer.mapper;

import com.rttmall.shopbackend.app.customer.pojo.RechargeCustom;

import java.util.List;

public interface RechargeCustomMapper {
    List<RechargeCustom> queryRecharge(RechargeCustom rechargeCustom);

    List<RechargeCustom> queryRechargeBusiness(RechargeCustom rechargeCustom);

    List<RechargeCustom> queryRechargeAgent(RechargeCustom rechargeCustom);
}