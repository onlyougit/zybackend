package com.rttmall.shopbackend.app.customer.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageHelper;
import com.rttmall.shopbackend.app.customer.mapper.SettlementCustomMapper;
import com.rttmall.shopbackend.app.customer.pojo.AgentSettlementCustom;
import com.rttmall.shopbackend.app.customer.pojo.SettlementCustom;
import com.rttmall.shopbackend.app.customer.service.SettlementService;
import com.rttmall.shopbackend.pojo.PageBean;
import com.rttmall.shopbackend.pojo.Pagination;

import org.springframework.beans.factory.annotation.Autowired;


public class SettlementServiceImpl implements SettlementService {

	@Autowired
	private SettlementCustomMapper settlementCustomMapper;
	@Override
	public Map querySettlement(SettlementCustom settlementCustom,
			Pagination grid) {
		Map map = new HashMap();
		PageHelper.startPage(grid.getPageIndex()+1, grid.getPageSize());
		List<SettlementCustom> settlementCustomList = settlementCustomMapper.querySettlement(settlementCustom);
		PageBean<SettlementCustom> pb = new PageBean(settlementCustomList);
		map.put("data", pb.getList());
		map.put("total", pb.getTotal());
		return map;
	}
	
	@Override
	public Map querySettlementBusiness(SettlementCustom settlementCustom,
			Pagination grid) {
		Map map = new HashMap();
		PageHelper.startPage(grid.getPageIndex()+1, grid.getPageSize());
		List<SettlementCustom> settlementCustomList = settlementCustomMapper.querySettlementBusiness(settlementCustom);
		PageBean<AgentSettlementCustom> pb = new PageBean(settlementCustomList);
		map.put("data", pb.getList());
		map.put("total", pb.getTotal());
		return map;
	}

	@Override
	public Map querySettlementAgent(SettlementCustom settlementCustom, Pagination grid) {
		Map map = new HashMap();
		PageHelper.startPage(grid.getPageIndex()+1, grid.getPageSize());
		List<SettlementCustom> settlementCustomList = settlementCustomMapper.querySettlementAgent(settlementCustom);
		PageBean<AgentSettlementCustom> pb = new PageBean(settlementCustomList);
		map.put("data", pb.getList());
		map.put("total", pb.getTotal());
		return map;
	}

	@Override
	public List<SettlementCustom> querySettlementExcel(
			SettlementCustom settlementCustom) {
		List<SettlementCustom> settlementCustomList = settlementCustomMapper.querySettlement(settlementCustom);
		return settlementCustomList;
	}

	@Override
	public List<SettlementCustom> querySettlementBusinessExcel(
			SettlementCustom settlementCustom) {
		List<SettlementCustom> settlementCustomList = settlementCustomMapper.querySettlementBusiness(settlementCustom);
		return settlementCustomList;
	}

	@Override
	public List<SettlementCustom> querySettlementAgentExcel(
			SettlementCustom settlementCustom) {
		List<SettlementCustom> settlementCustomList = settlementCustomMapper.querySettlementAgent(settlementCustom);
		return settlementCustomList;
	}

}
