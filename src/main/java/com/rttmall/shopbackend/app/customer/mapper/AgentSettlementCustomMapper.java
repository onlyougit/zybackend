package com.rttmall.shopbackend.app.customer.mapper;

import java.util.List;

import com.rttmall.shopbackend.app.customer.pojo.AgentSettlementCustom;


public interface AgentSettlementCustomMapper {

	List<AgentSettlementCustom> queryAgentSettlement(
			AgentSettlementCustom agentSettlementCustom);

	List<AgentSettlementCustom> queryAgentSettlementBusiness(
			AgentSettlementCustom agentSettlementCustom);
}