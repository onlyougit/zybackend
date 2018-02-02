package com.rttmall.shopbackend.app.customer.controller;

import com.alibaba.fastjson.JSON;
import com.rttmall.shopbackend.app.customer.pojo.BankCard;
import com.rttmall.shopbackend.app.customer.pojo.BankCardCustom;
import com.rttmall.shopbackend.app.customer.service.BankCardService;
import com.rttmall.shopbackend.app.service.SessionService;
import com.rttmall.shopbackend.enums.BankCardStatus;
import com.rttmall.shopbackend.pojo.Pagination;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("bankcard")
public class BankCardController {
	

	@Autowired
	private BankCardService bankCardService;
	@Autowired
	private SessionService sessionService;
    
	@RequestMapping(method = RequestMethod.GET)
	public String findBankcard() {
		return "app/bankcard/bankcardList";
	}
	@RequestMapping(value = "/findBankcardAdmin")
	public String findBankcardAdmin() {
		return "app/bankcard/bankcardAdminList";
	}
	@RequestMapping(value = "/findBankcardAgent")
	public String finBankcardAgent() {
		return "app/bankcard/bankcardAgentList";
	}
	@RequestMapping(value = "/addBankcardPage")
	public String addBankcardPage() {
		return "app/bankcard/bankcardAdd";
	}
	@RequestMapping(value = "/auditBankcardPage")
	public String auditBankcardPage() {
		return "app/bankcard/bankcardAudit";
	}

	@RequestMapping(value = "/queryBankCard")
	public @ResponseBody Map queryBankCard(
			@RequestParam(defaultValue = "") String json, Pagination grid) {
		BankCardCustom bankCardCustom = JSON.parseObject(json, BankCardCustom.class);
		if(null == bankCardCustom){
			bankCardCustom = new BankCardCustom();
		}
		Map map = bankCardService.queryBankCard(bankCardCustom,grid);
		return map;
	}
	@RequestMapping(value = "/queryBankCardBusiness")
	public @ResponseBody Map queryBankCardBusiness(
			@RequestParam(defaultValue = "") String json, Pagination grid,HttpSession session) {
		int userId = sessionService.getUserId(session);
		BankCardCustom bankCardCustom = JSON.parseObject(json, BankCardCustom.class);
		if(null == bankCardCustom){
			bankCardCustom = new BankCardCustom();
		}
		bankCardCustom.setLoginId(userId);
		Map map = bankCardService.queryBankCardBusiness(bankCardCustom,grid);
		return map;
	}
	@RequestMapping(value = "/queryBankCardAgent")
	public @ResponseBody Map queryBankCardAgent(
			@RequestParam(defaultValue = "") String json, Pagination grid,HttpSession session) {
		int userId = sessionService.getUserId(session);
		BankCardCustom bankCardCustom = JSON.parseObject(json, BankCardCustom.class);
		if(null == bankCardCustom){
			bankCardCustom = new BankCardCustom();
		}
		bankCardCustom.setLoginId(userId);
		Map map = bankCardService.queryBankCardAgent(bankCardCustom,grid);
		return map;
	}

	@RequestMapping(value = "/insertBankcardSubmit", method = RequestMethod.POST)
	public
	@ResponseBody
	void insertBankcardSubmit(BankCardCustom bankCardCustom) throws Exception {
		bankCardCustom.setAuditStatus(BankCardStatus.WAITAUDIT.ordinal());
		bankCardService.insertBankcard(bankCardCustom);
	}
	@RequestMapping(value = "/auditSubmit", method = RequestMethod.POST)
	public
	@ResponseBody
	void auditSubmit(BankCard bankCard,HttpSession session) throws Exception {
		int userId = sessionService.getUserId(session);
		bankCard.setOperationTime(new Date());
		bankCard.setOperator(userId);
		bankCardService.updateBankcardStatus(bankCard);
	}
	@RequestMapping(value = "/removeBankcard")
	public
	@ResponseBody
	void removeBankcard(Integer id) throws Exception {
		bankCardService.deleteBankcard(id);
	}
	
	@RequestMapping("/getBankCardStatus")
    public
    @ResponseBody
    List getBankCardStatus() {
        List list = new ArrayList<>();
        for (BankCardStatus e : BankCardStatus.values()) {
        	if(e.getCode()!=0){
                list.add(e);
        	}
        }
        return list;
   }
	@RequestMapping("/getAllBankCardStatus")
    public
    @ResponseBody
    List getAllBankCardStatus() {
        List list = new ArrayList<>();
        for (BankCardStatus e : BankCardStatus.values()) {
           list.add(e);
        }
        return list;
   }
}
