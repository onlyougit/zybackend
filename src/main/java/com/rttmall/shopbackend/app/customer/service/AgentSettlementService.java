package com.rttmall.shopbackend.app.customer.service;

import com.rttmall.shopbackend.app.customer.pojo.AgentSettlementCustom;
import com.rttmall.shopbackend.app.customer.pojo.SettlementCustom;
import com.rttmall.shopbackend.pojo.Pagination;

import java.util.List;
import java.util.Map;

public interface AgentSettlementService {

	Map queryAgentSettlement(AgentSettlementCustom agentSettlementCustom,
			Pagination grid);

	Map querySettlement(SettlementCustom settlementCustom, Pagination grid);

	Map queryAgentSettlementBusiness(
			AgentSettlementCustom agentSettlementCustom, Pagination grid);

	List<AgentSettlementCustom> queryAgentSettlementExcel(AgentSettlementCustom agentSettlementCustom);

	List<AgentSettlementCustom> queryAgentSettlementBusinessExcel(AgentSettlementCustom agentSettlementCustom);
}
