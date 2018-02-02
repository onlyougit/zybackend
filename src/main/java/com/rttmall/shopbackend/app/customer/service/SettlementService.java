package com.rttmall.shopbackend.app.customer.service;

import java.util.List;
import java.util.Map;
import com.rttmall.shopbackend.app.customer.pojo.SettlementCustom;
import com.rttmall.shopbackend.pojo.Pagination;

public interface SettlementService {

	Map querySettlement(SettlementCustom settlementCustom, Pagination grid);

	Map querySettlementAgent(SettlementCustom settlementCustom, Pagination grid);

	Map querySettlementBusiness(SettlementCustom settlementCustom,
			Pagination grid);

	List<SettlementCustom> querySettlementExcel(
			SettlementCustom settlementCustom);

	List<SettlementCustom> querySettlementBusinessExcel(
			SettlementCustom settlementCustom);

	List<SettlementCustom> querySettlementAgentExcel(
			SettlementCustom settlementCustom);

}
