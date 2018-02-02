package com.rttmall.shopbackend.app.customer.service.impl;

import com.github.pagehelper.PageHelper;
import com.rttmall.shopbackend.app.agent.mapper.AgentCustomMapper;
import com.rttmall.shopbackend.app.agent.pojo.Agent;
import com.rttmall.shopbackend.app.customer.mapper.AgentSettlementCustomMapper;
import com.rttmall.shopbackend.app.customer.mapper.SettlementCustomMapper;
import com.rttmall.shopbackend.app.customer.pojo.AgentSettlementCustom;
import com.rttmall.shopbackend.app.customer.pojo.SettlementCustom;
import com.rttmall.shopbackend.app.customer.service.AgentSettlementService;
import com.rttmall.shopbackend.pojo.PageBean;
import com.rttmall.shopbackend.pojo.Pagination;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class AgentSettlementServiceImpl implements AgentSettlementService {

	@Autowired
	private AgentSettlementCustomMapper agentSettlementCustomMapper;
	@Autowired
	private SettlementCustomMapper settlementCustomMapper;
	@Autowired
	private AgentCustomMapper agentCustomMapper;
	

	@Override
	public Map queryAgentSettlement(AgentSettlementCustom agentSettlementCustom, Pagination grid) {
		Map map = new HashMap();
		PageHelper.startPage(grid.getPageIndex()+1, grid.getPageSize());
		List<AgentSettlementCustom> agentSettlementCustomList = agentSettlementCustomMapper.queryAgentSettlement(agentSettlementCustom);
		PageBean<AgentSettlementCustom> pb = new PageBean(agentSettlementCustomList);
		map.put("data", pb.getList());
		map.put("total", pb.getTotal());
		return map;
	}

	@Override
	public Map querySettlement(SettlementCustom settlementCustom,Pagination grid) {
		Map map = new HashMap();
		PageHelper.startPage(grid.getPageIndex()+1, grid.getPageSize());
		List<SettlementCustom> settlementCustomList = settlementCustomMapper.querySettlementByAgentId(settlementCustom);
		PageBean<AgentSettlementCustom> pb = new PageBean(settlementCustomList);
		map.put("data", pb.getList());
		map.put("total", pb.getTotal());
		return map;
	}

	@Override
	public Map queryAgentSettlementBusiness(
			AgentSettlementCustom agentSettlementCustom, Pagination grid) {
		Map map = new HashMap();
		PageHelper.startPage(grid.getPageIndex()+1, grid.getPageSize());
		List<AgentSettlementCustom> agentSettlementCustomList = agentSettlementCustomMapper.queryAgentSettlementBusiness(agentSettlementCustom);
		PageBean<AgentSettlementCustom> pb = new PageBean(agentSettlementCustomList);
		map.put("data", pb.getList());
		map.put("total", pb.getTotal());
		return map;
	}


	@Override
	public List<AgentSettlementCustom> queryAgentSettlementExcel(AgentSettlementCustom agentSettlementCustom) {
		List<AgentSettlementCustom> agentSettlementCustomList = agentSettlementCustomMapper.queryAgentSettlement(agentSettlementCustom);
		return agentSettlementCustomList;
	}

	@Override
	public List<AgentSettlementCustom> queryAgentSettlementBusinessExcel(AgentSettlementCustom agentSettlementCustom) {
		List<AgentSettlementCustom> agentSettlementCustomList = agentSettlementCustomMapper.queryAgentSettlementBusiness(agentSettlementCustom);
		return agentSettlementCustomList;
	}
}
