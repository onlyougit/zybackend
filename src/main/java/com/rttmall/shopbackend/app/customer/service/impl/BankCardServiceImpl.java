package com.rttmall.shopbackend.app.customer.service.impl;

import com.github.pagehelper.PageHelper;
import com.rttmall.shopbackend.app.customer.mapper.BankCardCustomMapper;
import com.rttmall.shopbackend.app.customer.mapper.BankCardMapper;
import com.rttmall.shopbackend.app.customer.mapper.CustomerCustomMapper;
import com.rttmall.shopbackend.app.customer.pojo.BankCard;
import com.rttmall.shopbackend.app.customer.pojo.BankCardCustom;
import com.rttmall.shopbackend.app.customer.pojo.Customer;
import com.rttmall.shopbackend.app.customer.service.BankCardService;
import com.rttmall.shopbackend.exception.ServiceException;
import com.rttmall.shopbackend.pojo.PageBean;
import com.rttmall.shopbackend.pojo.Pagination;
import com.rttmall.shopbackend.utils.Constants;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class BankCardServiceImpl implements BankCardService {

	@Autowired
	private CustomerCustomMapper customerCustomMapper;
	@Autowired
	private BankCardCustomMapper bankCardCustomMapper;
	@Autowired
	private BankCardMapper bankCardMapper;

	@Override
	public Map queryBankCard(BankCardCustom bankCardCustom, Pagination grid) {
		Map map = new HashMap();
		PageHelper.startPage(grid.getPageIndex()+1, grid.getPageSize());
		List<BankCardCustom> bankCardCustomList = bankCardCustomMapper.queryBankCard(bankCardCustom);
		PageBean<BankCardCustom> pb = new PageBean(bankCardCustomList);
		map.put("data", pb.getList());
		map.put("total", pb.getTotal());
		return map;
	}

	@Override
	public Map queryBankCardBusiness(BankCardCustom bankCardCustom,
			Pagination grid) {
		Map map = new HashMap();
		PageHelper.startPage(grid.getPageIndex()+1, grid.getPageSize());
		List<BankCardCustom> bankCardCustomList = bankCardCustomMapper.queryBankCardBusiness(bankCardCustom);
		PageBean<BankCardCustom> pb = new PageBean(bankCardCustomList);
		map.put("data", pb.getList());
		map.put("total", pb.getTotal());
		return map;
	}

	@Override
	public Map queryBankCardAgent(BankCardCustom bankCardCustom, Pagination grid) {
		Map map = new HashMap();
		PageHelper.startPage(grid.getPageIndex()+1, grid.getPageSize());
		List<BankCardCustom> bankCardCustomList = bankCardCustomMapper.queryBankCardAgent(bankCardCustom);
		PageBean<BankCardCustom> pb = new PageBean(bankCardCustomList);
		map.put("data", pb.getList());
		map.put("total", pb.getTotal());
		return map;
	}
	@Override
	public void updateBankcardStatus(BankCard bankCard) {
		bankCardMapper.updateByPrimaryKeySelective(bankCard);
	}

	@Override
	public void insertBankcard(BankCardCustom bankCardCustom) {
		Customer customer = customerCustomMapper.queryByPhoneAndRealName(bankCardCustom.getCustomer().getCustomerPhone(),bankCardCustom.getCustomer().getCustomerRealName());
		if(null == customer){
			throw new ServiceException(Constants.CUSTOMER_NOT_EXIST);
		}
		bankCardCustom.setCustomerId(customer.getId());
		BankCard bankCard = new BankCard();
		BeanUtils.copyProperties(bankCardCustom, bankCard);
		bankCardMapper.insertSelective(bankCardCustom);
	}

	@Override
	public void deleteBankcard(Integer id) {
		bankCardMapper.deleteByPrimaryKey(id);
	}
}
