package com.rttmall.shopbackend.app.customer.service.impl;

import com.github.pagehelper.PageHelper;
import com.rttmall.shopbackend.app.customer.mapper.FundDetailCustomMapper;
import com.rttmall.shopbackend.app.customer.pojo.FundDetailCustom;
import com.rttmall.shopbackend.app.customer.service.FundDetailService;
import com.rttmall.shopbackend.pojo.PageBean;
import com.rttmall.shopbackend.pojo.Pagination;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class FundDetailServiceImpl implements FundDetailService {

	@Autowired
	private FundDetailCustomMapper fundDetailCustomMapper;
	
	@Override
	public Map queryFundDetail(FundDetailCustom fundDetailCustom,
			Pagination grid) {
        Map map = new HashMap();
        PageHelper.startPage(grid.getPageIndex()+1, grid.getPageSize());
        List<FundDetailCustom> fundDetailCustomList = fundDetailCustomMapper.queryFundDetail(fundDetailCustom);
        PageBean<FundDetailCustom> pb = new PageBean(fundDetailCustomList);
        map.put("data", pb.getList());
        map.put("total", pb.getTotal());
        return map;
	}

	@Override
	public Map queryFundDetailBusiness(FundDetailCustom fundDetailCustom, Pagination grid) {
		Map map = new HashMap();
		PageHelper.startPage(grid.getPageIndex()+1, grid.getPageSize());
		List<FundDetailCustom> fundDetailCustomList = fundDetailCustomMapper.queryFundDetailBusiness(fundDetailCustom);
		PageBean<FundDetailCustom> pb = new PageBean(fundDetailCustomList);
		map.put("data", pb.getList());
		map.put("total", pb.getTotal());
		return map;
	}

	@Override
	public Map queryFundDetailAgent(FundDetailCustom fundDetailCustom, Pagination grid) {
		Map map = new HashMap();
		PageHelper.startPage(grid.getPageIndex()+1, grid.getPageSize());
		List<FundDetailCustom> fundDetailCustomList = fundDetailCustomMapper.queryFundDetailAgent(fundDetailCustom);
		PageBean<FundDetailCustom> pb = new PageBean(fundDetailCustomList);
		map.put("data", pb.getList());
		map.put("total", pb.getTotal());
		return map;
	}

	@Override
	public List<FundDetailCustom> queryFundDetailExcel(FundDetailCustom fundDetailCustom) {
		List<FundDetailCustom> fundDetailCustomList = fundDetailCustomMapper.queryFundDetail(fundDetailCustom);
		return fundDetailCustomList;
	}

	@Override
	public List<FundDetailCustom> queryFundDetailBusinessExcel(
			FundDetailCustom fundDetailCustom) {
		List<FundDetailCustom> fundDetailCustomList = fundDetailCustomMapper.queryFundDetailBusiness(fundDetailCustom);
		return fundDetailCustomList;
	}

	@Override
	public List<FundDetailCustom> queryFundDetailAgentExcel(
			FundDetailCustom fundDetailCustom) {
		List<FundDetailCustom> fundDetailCustomList = fundDetailCustomMapper.queryFundDetailAgent(fundDetailCustom);
		return fundDetailCustomList;
	}
}
