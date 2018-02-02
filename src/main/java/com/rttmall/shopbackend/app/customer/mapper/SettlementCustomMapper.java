package com.rttmall.shopbackend.app.customer.mapper;

import com.rttmall.shopbackend.app.customer.pojo.SettlementCustom;

import java.util.List;


public interface SettlementCustomMapper {

	List<SettlementCustom> querySettlementByAgentId(SettlementCustom settlementCustom);

	List<SettlementCustom> querySettlement(SettlementCustom settlementCustom);

	List<SettlementCustom> querySettlementBusiness(
			SettlementCustom settlementCustom);

	List<SettlementCustom> querySettlementAgent(
			SettlementCustom settlementCustom);
}