package com.rttmall.shopbackend.app.customer.mapper;

import com.rttmall.shopbackend.app.customer.pojo.Fund;
import com.rttmall.shopbackend.app.customer.pojo.FundCustom;

import java.util.List;

public interface FundCustomMapper {
    List<FundCustom> queryFund(FundCustom fundCustom);

    List<FundCustom> queryFundBusiness(FundCustom fundCustom);

    List<FundCustom> queryFundAgent(FundCustom fundCustom);

    Fund queryByCustomerId(Integer id);
}