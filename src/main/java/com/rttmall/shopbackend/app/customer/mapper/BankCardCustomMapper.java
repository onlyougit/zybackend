package com.rttmall.shopbackend.app.customer.mapper;

import java.util.List;

import com.rttmall.shopbackend.app.customer.pojo.BankCard;
import com.rttmall.shopbackend.app.customer.pojo.BankCardCustom;


public interface BankCardCustomMapper {

	BankCard queryByBankCardId(String bankCardId);

	List<BankCardCustom> queryBankCard(BankCardCustom bankCardCustom);

	List<BankCardCustom> queryBankCardBusiness(BankCardCustom bankCardCustom);

	List<BankCardCustom> queryBankCardAgent(BankCardCustom bankCardCustom);
}