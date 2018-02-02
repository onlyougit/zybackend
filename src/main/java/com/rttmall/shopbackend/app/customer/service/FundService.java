package com.rttmall.shopbackend.app.customer.service;

import com.rttmall.shopbackend.app.customer.pojo.FundCustom;
import com.rttmall.shopbackend.pojo.Pagination;

import java.util.List;
import java.util.Map;

public interface FundService {

	Map queryFund(FundCustom fundCustom, Pagination grid);

	Map queryFundBusiness(FundCustom fundCustom, Pagination grid);

	Map queryFundAgent(FundCustom fundCustom, Pagination grid);

	void flushBalance(Integer id);

	List<FundCustom> queryFundExcel(FundCustom fundCustom);

	List<FundCustom> queryFundBusinessExcel(FundCustom fundCustom);

	List<FundCustom> queryFundAgentExcel(FundCustom fundCustom);
}
