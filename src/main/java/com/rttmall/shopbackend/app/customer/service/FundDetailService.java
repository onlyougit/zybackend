package com.rttmall.shopbackend.app.customer.service;

import com.rttmall.shopbackend.app.customer.pojo.FundDetailCustom;
import com.rttmall.shopbackend.pojo.Pagination;

import java.util.List;
import java.util.Map;


public interface FundDetailService {

	Map queryFundDetail(FundDetailCustom fundDetailCustom, Pagination grid);

	Map queryFundDetailBusiness(FundDetailCustom fundDetailCustom, Pagination grid);

	Map queryFundDetailAgent(FundDetailCustom fundDetailCustom, Pagination grid);

	List<FundDetailCustom> queryFundDetailExcel(FundDetailCustom fundDetailCustom);

	List<FundDetailCustom> queryFundDetailBusinessExcel(
			FundDetailCustom fundDetailCustom);

	List<FundDetailCustom> queryFundDetailAgentExcel(
			FundDetailCustom fundDetailCustom);
}
