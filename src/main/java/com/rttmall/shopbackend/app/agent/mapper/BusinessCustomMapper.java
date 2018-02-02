package com.rttmall.shopbackend.app.agent.mapper;

import com.rttmall.shopbackend.app.agent.pojo.Business;
import com.rttmall.shopbackend.app.agent.pojo.BusinessCustom;

import java.util.List;

public interface BusinessCustomMapper {

	List findAllBusiness();

	List<BusinessCustom> queryBusiness(BusinessCustom businessCustom);

	Business queryByLoginId(Integer loginId);
}