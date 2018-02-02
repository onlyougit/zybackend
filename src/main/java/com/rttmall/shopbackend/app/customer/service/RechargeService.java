package com.rttmall.shopbackend.app.customer.service;

import com.rttmall.shopbackend.app.customer.pojo.RechargeCustom;
import com.rttmall.shopbackend.pojo.Pagination;

import java.util.List;
import java.util.Map;

public interface RechargeService {

	Map queryRecharge(RechargeCustom rechargeCustom, Pagination grid);

	Map queryRechargeBusiness(RechargeCustom rechargeCustom, Pagination grid);

	Map queryRechargeAgent(RechargeCustom rechargeCustom, Pagination grid);

	void insertRecharge(RechargeCustom rechargeCustom);

	List<RechargeCustom> queryRechargeExcel(RechargeCustom rechargeCustom);

	List<RechargeCustom> queryRechargeBusinessExcel(
			RechargeCustom rechargeCustom);

	List<RechargeCustom> queryRechargeAgentExcel(RechargeCustom rechargeCustom);
}
