package com.rttmall.shopbackend.app.agent.service;

import com.rttmall.shopbackend.app.agent.pojo.Agent;
import com.rttmall.shopbackend.app.agent.pojo.AgentCustom;
import com.rttmall.shopbackend.pojo.Pagination;

import java.util.Map;

public interface AgentService {

	Map queryHeadAgent(AgentCustom agentCustom, Pagination grid);

	Agent editQueryAgent(Integer id);

	void updateAgent(Agent agent);

	void insertAgent(AgentCustom agentCustom,Integer userId);

	Map queryBusinessAgent(AgentCustom agentCustom, Pagination grid);

	void addTemplateAccount(Agent agent);

	void updateAgentStatus(Integer id, Integer flag);

	AgentCustom updateAgentPw(Integer id);

	void updateTemplateAccount(Agent agent);

	String queryAgentLink(int userId);

	void updateAgentByBusiness(Agent agent);
}
