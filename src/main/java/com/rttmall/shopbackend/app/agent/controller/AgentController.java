package com.rttmall.shopbackend.app.agent.controller;

import com.alibaba.fastjson.JSON;
import com.rttmall.shopbackend.app.agent.pojo.Agent;
import com.rttmall.shopbackend.app.agent.pojo.AgentCustom;
import com.rttmall.shopbackend.app.agent.service.AgentService;
import com.rttmall.shopbackend.app.service.SessionService;
import com.rttmall.shopbackend.enums.AgentStatus;
import com.rttmall.shopbackend.pojo.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
@RequestMapping("agent")
public class AgentController {
	

	@Autowired
	private AgentService agentService;

    @Autowired
    private SessionService sessionService;

	@RequestMapping(method = RequestMethod.GET)
	public String findAgent() {
		return "app/agent/agentAdminList";
	}
	@RequestMapping(value = "agentBusinessList")
	public String agentBusinessList() {
		return "app/agent/agentBusinessList";
	}
	@RequestMapping(value = "agentHeadList")
	public String agentHeadList() {
		return "app/agent/agentHeadList";
	}
	@RequestMapping(value = "businessEditAgentPage")
	public String businessEditAgentPage() {
		return "app/agent/agentBusinessEdit";
	}
	@RequestMapping(value = "editAgentPage")
	public String editAgentPage() {
		return "app/agent/agentEdit";
	}
	@RequestMapping(value = "addAgentPage")
	public String addAgentPage() {
		return "app/agent/agentAdd";
	}
	@RequestMapping(value = "agentLinkPage")
	public String agentLinkPage() {
		return "app/agent/agentLink";
	}

	@RequestMapping(value = "/queryHeadAgent")
	public @ResponseBody Map queryHeadAgent(
			@RequestParam(defaultValue = "") String json, Pagination grid) {
		AgentCustom agentCustom = JSON.parseObject(json, AgentCustom.class);
		if(null == agentCustom){
			agentCustom = new AgentCustom();
		}
		Map map = agentService.queryHeadAgent(agentCustom,grid);
		return map;
	}

	@RequestMapping(value = "/queryBusinessAgent")
	public @ResponseBody Map queryBusinessAgent(
			@RequestParam(defaultValue = "") String json, Pagination grid,HttpSession session) {
		int userId = sessionService.getUserId(session);
		AgentCustom agentCustom = JSON.parseObject(json, AgentCustom.class);
		if(null == agentCustom){
			agentCustom = new AgentCustom();
		}
		agentCustom.setLoginId(userId);
		Map map = agentService.queryBusinessAgent(agentCustom,grid);
		return map;
	}
	@RequestMapping(value = "/editQueryAgent")
	public
	@ResponseBody
	Agent editQueryAgent(Integer id) {
		return agentService.editQueryAgent(id);
	}
	@RequestMapping(value = "/queryAgentLink")
	public
	@ResponseBody
	AgentCustom queryAgentLink(HttpSession session) {
		int userId = sessionService.getUserId(session);
		return agentService.queryAgentLink(userId);
	}
	
	@RequestMapping(value = "/updateAgentSubmit", method = RequestMethod.POST)
	public
	@ResponseBody
	void updateAgentSubmit(Agent agent,HttpSession session) throws Exception {
		int userId = sessionService.getUserId(session);
		agent.setChangePerson(userId);
		agentService.updateAgent(agent);
	}

	@RequestMapping(value = "/businessUpdateAgentSubmit", method = RequestMethod.POST)
	public
	@ResponseBody
	void businessUpdateAgentSubmit(Agent agent,HttpSession session) throws Exception {
		int userId = sessionService.getUserId(session);
		agent.setChangePerson(userId);
		agentService.updateAgentByBusiness(agent);
	}
	@RequestMapping(value = "/insertAgentSubmit", method = RequestMethod.POST)
	public
	@ResponseBody
	void insertAgentSubmit(AgentCustom agentCustom,HttpSession session) throws Exception {
		int userId = sessionService.getUserId(session);
		agentCustom.setStatus(AgentStatus.INVALID.ordinal());
		agentService.insertAgent(agentCustom,userId);
	}
	@RequestMapping("/addTemplateAccount")
	public @ResponseBody void addTemplateAccount(String templateAccount,String agentId,HttpSession session){
		Agent agent = new Agent();
		int userId = sessionService.getUserId(session);
		agent.setId(Integer.parseInt(agentId));
		agent.setTemplateAccount(templateAccount);
		agentService.addTemplateAccount(agent);
	}
	@RequestMapping("/updateTemplateAccount")
	public @ResponseBody void updateTemplateAccount(String templateAccount,String agentId,HttpSession session){
		Agent agent = new Agent();
		int userId = sessionService.getUserId(session);
		agent.setId(Integer.parseInt(agentId));
		agent.setTemplateAccount(templateAccount);
		agentService.updateTemplateAccount(agent);
	}
	@RequestMapping(value = "/updateAgentStatus")
	public
	@ResponseBody
	void updateAgentStatus(Integer id,Integer flag) {
		agentService.updateAgentStatus(id,flag);
	}
	@RequestMapping(value = "/updateAgentPw")
	public
	@ResponseBody
	AgentCustom updateAgentPw(Integer id) {
		return agentService.updateAgentPw(id);
	}

}
