package com.rttmall.shopbackend.app.agent.mapper;

import com.rttmall.shopbackend.app.agent.pojo.Agent;
import com.rttmall.shopbackend.app.agent.pojo.AgentCustom;

import java.util.List;


public interface AgentCustomMapper {

	List findAllAgent();

	List<AgentCustom> queryHeadAgent(AgentCustom agentCustom);

	List<AgentCustom> queryBusinessAgent(AgentCustom agentCustom);

	Agent queryAgentLinkByLoginId(int userId);
}