package com.rttmall.shopbackend.app.customer.service;

import com.rttmall.shopbackend.app.customer.pojo.BankCard;
import com.rttmall.shopbackend.app.customer.pojo.BankCardCustom;
import com.rttmall.shopbackend.pojo.Pagination;

import java.util.Map;


public interface BankCardService {

	Map queryBankCard(BankCardCustom bankCardCustom, Pagination grid);

	Map queryBankCardBusiness(BankCardCustom bankCardCustom, Pagination grid);

	Map queryBankCardAgent(BankCardCustom bankCardCustom, Pagination grid);

	void updateBankcardStatus(BankCard bankCard);

	void insertBankcard(BankCardCustom bankCardCustom);

	void deleteBankcard(Integer id);
}
