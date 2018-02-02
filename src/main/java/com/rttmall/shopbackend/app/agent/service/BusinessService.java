package com.rttmall.shopbackend.app.agent.service;

import com.rttmall.shopbackend.app.agent.pojo.Business;
import com.rttmall.shopbackend.app.agent.pojo.BusinessCustom;
import com.rttmall.shopbackend.pojo.Pagination;

import java.util.Map;

public interface BusinessService {

	Map queryBusiness(BusinessCustom businessCustom, Pagination grid);

	Business editQueryBusiness(Integer id);

	void updateBusiness(Business business);

	void insertBusiness(BusinessCustom businessCustom);

	BusinessCustom updateBusinessPw(Integer id);
}
