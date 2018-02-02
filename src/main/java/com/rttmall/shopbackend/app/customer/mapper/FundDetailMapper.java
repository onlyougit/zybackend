package com.rttmall.shopbackend.app.customer.mapper;

import com.rttmall.shopbackend.app.customer.pojo.FundDetail;

public interface FundDetailMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(FundDetail record);

    int insertSelective(FundDetail record);

    FundDetail selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(FundDetail record);

    int updateByPrimaryKey(FundDetail record);
}