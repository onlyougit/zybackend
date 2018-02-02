package com.rttmall.shopbackend.app.customer.mapper;

import com.rttmall.shopbackend.app.customer.pojo.FundDetailCustom;

import java.util.List;

public interface FundDetailCustomMapper {

	List<FundDetailCustom> queryFundDetail(FundDetailCustom fundDetailCustom);

	List<FundDetailCustom> queryFundDetailBusiness(FundDetailCustom fundDetailCustom);

	List<FundDetailCustom> queryFundDetailAgent(FundDetailCustom fundDetailCustom);
}