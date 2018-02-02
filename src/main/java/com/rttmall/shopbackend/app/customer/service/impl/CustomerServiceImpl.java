package com.rttmall.shopbackend.app.customer.service.impl;

import com.github.pagehelper.PageHelper;
import com.rttmall.shopbackend.app.agent.mapper.AgentCustomMapper;
import com.rttmall.shopbackend.app.agent.mapper.BusinessCustomMapper;
import com.rttmall.shopbackend.app.customer.mapper.CustomerCustomMapper;
import com.rttmall.shopbackend.app.customer.mapper.CustomerMapper;
import com.rttmall.shopbackend.app.customer.pojo.Customer;
import com.rttmall.shopbackend.app.customer.pojo.CustomerCustom;
import com.rttmall.shopbackend.app.customer.service.CustomerService;
import com.rttmall.shopbackend.pojo.PageBean;
import com.rttmall.shopbackend.pojo.Pagination;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class CustomerServiceImpl implements CustomerService {

	@Autowired
	private CustomerCustomMapper customerCustomMapper;
	@Autowired
	private CustomerMapper customerMapper;
	@Autowired
	private BusinessCustomMapper businessCustomMapper;
	@Autowired
	private AgentCustomMapper agentCustomMapper;
	
	@Override
	public Map queryHeadCustomer(CustomerCustom customerCustom, Pagination grid) {
        Map map = new HashMap();
        PageHelper.startPage(grid.getPageIndex()+1, grid.getPageSize());
        List<CustomerCustom> customerCustomList = customerCustomMapper.queryHeadCustomer(customerCustom);
        PageBean<CustomerCustom> pb = new PageBean(customerCustomList);
        map.put("data", pb.getList());
        map.put("total", pb.getTotal());
        return map;
	}
	@Override
	public Map queryBusinessCustomer(CustomerCustom customerCustom,
			Pagination grid) {
        Map map = new HashMap();
        PageHelper.startPage(grid.getPageIndex()+1, grid.getPageSize());
        List<CustomerCustom> customerCustomList = customerCustomMapper.queryBusinessCustomer(customerCustom);
        PageBean<CustomerCustom> pb = new PageBean(customerCustomList);
        map.put("data", pb.getList());
        map.put("total", pb.getTotal());
        return map;
	}

	@Override
	public Map queryAgentCustomer(CustomerCustom customerCustom, Pagination grid) {
        Map map = new HashMap();
        PageHelper.startPage(grid.getPageIndex()+1, grid.getPageSize());
        List<CustomerCustom> customerCustomList = customerCustomMapper.queryAgentCustomer(customerCustom);
        PageBean<CustomerCustom> pb = new PageBean(customerCustomList);
        map.put("data", pb.getList());
        map.put("total", pb.getTotal());
        return map;
	}

	@Override
	public List<CustomerCustom> queryCustomerExcel(CustomerCustom customerCustom) {
		List<CustomerCustom> customerCustomList = customerCustomMapper.queryHeadCustomer(customerCustom);
		return customerCustomList;
	}

	@Override
	public List<CustomerCustom> queryCustomerBusinessExcel(
			CustomerCustom customerCustom) {
		List<CustomerCustom> customerCustomList = customerCustomMapper.queryBusinessCustomer(customerCustom);
		return customerCustomList;
	}
	@Override
	public List<CustomerCustom> queryCustomerAgentExcel(
			CustomerCustom customerCustom) {
		List<CustomerCustom> customerCustomList = customerCustomMapper.queryAgentCustomer(customerCustom);
		return customerCustomList;
	}
	@Override
	public void editCustomer(Customer customer) {
		customerMapper.updateByPrimaryKeySelective(customer);
	}
	@Override
	public List findAllAgent() {
		return agentCustomMapper.findAllAgent();
	}
	@Override
	public List findAllBusiness() {
		return businessCustomMapper.findAllBusiness();
	}
}
