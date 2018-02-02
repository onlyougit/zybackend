package com.rttmall.shopbackend.app.customer.service.impl;

import com.github.pagehelper.PageHelper;
import com.rttmall.shopbackend.app.customer.mapper.CustomerCustomMapper;
import com.rttmall.shopbackend.app.customer.mapper.FundCustomMapper;
import com.rttmall.shopbackend.app.customer.mapper.FundMapper;
import com.rttmall.shopbackend.app.customer.pojo.Customer;
import com.rttmall.shopbackend.app.customer.pojo.Fund;
import com.rttmall.shopbackend.app.customer.pojo.FundCustom;
import com.rttmall.shopbackend.app.customer.service.FundService;
import com.rttmall.shopbackend.exception.ServiceException;
import com.rttmall.shopbackend.pojo.PageBean;
import com.rttmall.shopbackend.pojo.Pagination;
import com.rttmall.shopbackend.pojo.Result;
import com.rttmall.shopbackend.pojo.Summary;
import com.rttmall.shopbackend.utils.Constants;
import com.rttmall.shopbackend.utils.HttpsUtil;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class FundServiceImpl implements FundService {

	public static final String sa = "GCB";
	public static final String sapass = "888888";
	@Autowired
	private FundCustomMapper fundCustomMapper;
	@Autowired
	private CustomerCustomMapper customerCustomMapper;
	@Autowired
	private FundMapper fundMapper;
	

	@Override
	public Map queryFund(FundCustom fundCustom, Pagination grid) {
		Map map = new HashMap();
		PageHelper.startPage(grid.getPageIndex()+1, grid.getPageSize());
		List<FundCustom> fundCustomList = fundCustomMapper.queryFund(fundCustom);
		PageBean<FundCustom> pb = new PageBean(fundCustomList);
		map.put("data", pb.getList());
		map.put("total", pb.getTotal());
		return map;
	}

	@Override
	public Map queryFundBusiness(FundCustom fundCustom, Pagination grid) {
		Map map = new HashMap();
		PageHelper.startPage(grid.getPageIndex()+1, grid.getPageSize());
		List<FundCustom> fundCustomList = fundCustomMapper.queryFundBusiness(fundCustom);
		PageBean<FundCustom> pb = new PageBean(fundCustomList);
		map.put("data", pb.getList());
		map.put("total", pb.getTotal());
		return map;
	}

	@Override
	public Map queryFundAgent(FundCustom fundCustom, Pagination grid) {
		Map map = new HashMap();
		PageHelper.startPage(grid.getPageIndex()+1, grid.getPageSize());
		List<FundCustom> fundCustomList = fundCustomMapper.queryFundAgent(fundCustom);
		PageBean<FundCustom> pb = new PageBean(fundCustomList);
		map.put("data", pb.getList());
		map.put("total", pb.getTotal());
		return map;
	}

	@Override
	public void flushBalance(Integer id) {
		//查询账号
		Customer customer = customerCustomMapper.queryByFundId(id);
		Result result = HttpsUtil.queryaccount(customer.getCustomerName(),sa,sapass);
		if(!"0".equals(result.getError().getCode())){
			throw new ServiceException(Constants.BALANCE_QUERY_FAILED);
		}
		Summary summary = result.getSummary();
		Fund fund = new Fund();
		fund.setId(id);
		fund.setDepositBalance(summary.getBalance());
		fund.setFlushTime(new Date());
		fundMapper.updateByPrimaryKeySelective(fund);
	}

	@Override
	public List<FundCustom> queryFundExcel(FundCustom fundCustom) {
		List<FundCustom> fundCustomList = fundCustomMapper.queryFund(fundCustom);
		return fundCustomList;
	}

	@Override
	public List<FundCustom> queryFundBusinessExcel(FundCustom fundCustom) {
		List<FundCustom> fundCustomList = fundCustomMapper.queryFundBusiness(fundCustom);
		return fundCustomList;
	}

	@Override
	public List<FundCustom> queryFundAgentExcel(FundCustom fundCustom) {
		List<FundCustom> fundCustomList = fundCustomMapper.queryFundAgent(fundCustom);
		return fundCustomList;
	}
}
