package com.rttmall.shopbackend.app.customer.service;

import com.rttmall.shopbackend.app.customer.pojo.Customer;
import com.rttmall.shopbackend.app.customer.pojo.CustomerCustom;
import com.rttmall.shopbackend.pojo.Pagination;

import java.util.List;
import java.util.Map;


public interface CustomerService {

	Map queryHeadCustomer(CustomerCustom customerCustom, Pagination grid);

	void editCustomer(Customer customer);

	List findAllAgent();

	List findAllBusiness();

	Map queryAgentCustomer(CustomerCustom customerCustom, Pagination grid);

	Map queryBusinessCustomer(CustomerCustom customerCustom, Pagination grid);

	List<CustomerCustom> queryCustomerExcel(CustomerCustom customerCustom);

	List<CustomerCustom> queryCustomerBusinessExcel(
			CustomerCustom customerCustom);

	List<CustomerCustom> queryCustomerAgentExcel(CustomerCustom customerCustom);
}
