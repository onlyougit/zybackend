package com.rttmall.shopbackend.app.agent.controller;

import com.alibaba.fastjson.JSON;
import com.rttmall.shopbackend.app.agent.pojo.Business;
import com.rttmall.shopbackend.app.agent.pojo.BusinessCustom;
import com.rttmall.shopbackend.app.agent.service.BusinessService;
import com.rttmall.shopbackend.app.service.SessionService;
import com.rttmall.shopbackend.pojo.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
@RequestMapping("business")
public class BusinessController {
	

	@Autowired
	private BusinessService businessService;

    @Autowired
    private SessionService sessionService;

	@RequestMapping(method = RequestMethod.GET)
	public String findBusiness() {
		return "app/agent/businessList";
	}
	@RequestMapping(value = "editBusinessPage")
	public String editBusinessPage() {
		return "app/agent/businessEdit";
	}
	@RequestMapping(value = "addBusinessPage")
	public String addBusinessPage() {
		return "app/agent/businessAdd";
	}

	@RequestMapping(value = "/queryBusiness")
	public @ResponseBody Map queryBusiness(
			@RequestParam(defaultValue = "") String json, Pagination grid) {
		BusinessCustom businessCustom = JSON.parseObject(json, BusinessCustom.class);
		if(null == businessCustom){
			businessCustom = new BusinessCustom();
		}
		Map map = businessService.queryBusiness(businessCustom,grid);
		return map;
	}
	
	@RequestMapping(value = "/editQueryBusiness")
	public
	@ResponseBody
	Business editQueryBusiness(Integer id) {
		return businessService.editQueryBusiness(id);
	}
	
	@RequestMapping(value = "/updateBusinessSubmit", method = RequestMethod.POST)
	public
	@ResponseBody
	void updateBusinessSubmit(Business business) throws Exception {
		businessService.updateBusiness(business);
	}
	@RequestMapping(value = "/insertBusinessSubmit", method = RequestMethod.POST)
	public
	@ResponseBody
	void insertBusinessSubmit(BusinessCustom businessCustom) throws Exception {
		businessService.insertBusiness(businessCustom);
	}
	@RequestMapping(value = "/updateBusinessPw")
	public
	@ResponseBody
	BusinessCustom updateBusinessPw(Integer id) {
		return businessService.updateBusinessPw(id);
	}
}
