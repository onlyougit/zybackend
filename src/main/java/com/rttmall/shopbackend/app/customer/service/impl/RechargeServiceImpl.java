package com.rttmall.shopbackend.app.customer.service.impl;

import com.github.pagehelper.PageHelper;
import com.rttmall.shopbackend.app.customer.mapper.*;
import com.rttmall.shopbackend.app.customer.pojo.*;
import com.rttmall.shopbackend.app.customer.service.RechargeService;
import com.rttmall.shopbackend.enums.FlowWay;
import com.rttmall.shopbackend.enums.FundRemark;
import com.rttmall.shopbackend.pojo.PageBean;
import com.rttmall.shopbackend.pojo.Pagination;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class RechargeServiceImpl implements RechargeService {

	@Autowired
	private RechargeCustomMapper rechargeCustomMapper;
	@Autowired
	private RechargeMapper rechargeMapper;
	@Autowired
	private CustomerCustomMapper customerCustomMapper;
	@Autowired
	private FundCustomMapper fundCustomMapper;
	@Autowired
	private FundMapper fundMapper;
	@Autowired
	FundDetailMapper fundDetailMapper;


	@Override
	public Map queryRecharge(RechargeCustom rechargeCustom, Pagination grid) {
		Map map = new HashMap();
		PageHelper.startPage(grid.getPageIndex()+1, grid.getPageSize());
		List<RechargeCustom> rechargeCustomList = rechargeCustomMapper.queryRecharge(rechargeCustom);
		PageBean<RechargeCustom> pb = new PageBean(rechargeCustomList);
		map.put("data", pb.getList());
		map.put("total", pb.getTotal());
		return map;
	}

	@Override
	public Map queryRechargeBusiness(RechargeCustom rechargeCustom, Pagination grid) {
		Map map = new HashMap();
		PageHelper.startPage(grid.getPageIndex()+1, grid.getPageSize());
		List<RechargeCustom> rechargeCustomList = rechargeCustomMapper.queryRechargeBusiness(rechargeCustom);
		PageBean<RechargeCustom> pb = new PageBean(rechargeCustomList);
		map.put("data", pb.getList());
		map.put("total", pb.getTotal());
		return map;
	}

	@Override
	public Map queryRechargeAgent(RechargeCustom rechargeCustom, Pagination grid) {
		Map map = new HashMap();
		PageHelper.startPage(grid.getPageIndex()+1, grid.getPageSize());
		List<RechargeCustom> rechargeCustomList = rechargeCustomMapper.queryRechargeAgent(rechargeCustom);
		PageBean<RechargeCustom> pb = new PageBean(rechargeCustomList);
		map.put("data", pb.getList());
		map.put("total", pb.getTotal());
		return map;
	}

	@Override
	public void insertRecharge(RechargeCustom rechargeCustom) {
		Customer customer = customerCustomMapper.queryByCustomerPhone(rechargeCustom.getMobile());
		rechargeCustom.setCustomerId(customer.getId());
		Recharge recharge = new Recharge();
		BeanUtils.copyProperties(rechargeCustom,recharge);
		rechargeMapper.insertSelective(recharge);
		//更改资金表余额
		Fund fund = fundCustomMapper.queryByCustomerId(customer.getId());
		Fund fund2 = new Fund();
		fund2.setId(fund.getId());
		fund2.setBalance(fund.getBalance().add(recharge.getRechargeAmount()).setScale(2, BigDecimal.ROUND_HALF_UP));
		fundMapper.updateByPrimaryKeySelective(fund2);
		//资金明细
		FundDetail fundDetail = new FundDetail();
		fundDetail.setChangeAmount(recharge.getRechargeAmount());
		fundDetail.setChangeTime(new Date());
		fundDetail.setCustomerId(customer.getId());
		fundDetail.setChargeAmount(fund2.getBalance());
		fundDetail.setFlowWay(FlowWay.INCOME.getCode());
		fundDetail.setRemark(FundRemark.RECHARGE.getText());
		fundDetailMapper.insertSelective(fundDetail);
	}

	@Override
	public List<RechargeCustom> queryRechargeExcel(RechargeCustom rechargeCustom) {
		List<RechargeCustom> rechargeCustomList = rechargeCustomMapper.queryRecharge(rechargeCustom);
		return rechargeCustomList;
	}

	@Override
	public List<RechargeCustom> queryRechargeBusinessExcel(
			RechargeCustom rechargeCustom) {
		List<RechargeCustom> rechargeCustomList = rechargeCustomMapper.queryRechargeBusiness(rechargeCustom);
		return rechargeCustomList;
	}

	@Override
	public List<RechargeCustom> queryRechargeAgentExcel(
			RechargeCustom rechargeCustom) {
		List<RechargeCustom> rechargeCustomList = rechargeCustomMapper.queryRechargeAgent(rechargeCustom);
		return rechargeCustomList;
	}
}
