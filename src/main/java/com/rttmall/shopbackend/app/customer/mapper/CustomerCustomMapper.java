package com.rttmall.shopbackend.app.customer.mapper;

import com.rttmall.shopbackend.app.customer.pojo.Customer;
import com.rttmall.shopbackend.app.customer.pojo.CustomerCustom;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface CustomerCustomMapper {

	List<CustomerCustom> queryHeadCustomer(
			CustomerCustom customerCustom);

	List<CustomerCustom> queryAgentCustomer(CustomerCustom customerCustom);

	List<CustomerCustom> queryBusinessCustomer(CustomerCustom customerCustom);

	Customer queryByCustomerPhone(String mobile);

	Customer queryByPhoneAndRealName(@Param("customerPhone")String customerPhone,
			@Param("customerRealName")String customerRealName);

	Customer queryByFundId(Integer id);
}