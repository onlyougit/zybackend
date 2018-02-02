package com.rttmall.shopbackend.app.agent.service.impl;

import com.github.pagehelper.PageHelper;
import com.rttmall.shopbackend.app.agent.mapper.AgentCustomMapper;
import com.rttmall.shopbackend.app.agent.mapper.AgentMapper;
import com.rttmall.shopbackend.app.agent.mapper.BusinessCustomMapper;
import com.rttmall.shopbackend.app.agent.mapper.BusinessMapper;
import com.rttmall.shopbackend.app.agent.pojo.Agent;
import com.rttmall.shopbackend.app.agent.pojo.AgentCustom;
import com.rttmall.shopbackend.app.agent.pojo.Business;
import com.rttmall.shopbackend.app.agent.service.AgentService;
import com.rttmall.shopbackend.enums.Identity;
import com.rttmall.shopbackend.enums.Status;
import com.rttmall.shopbackend.exception.ServiceException;
import com.rttmall.shopbackend.pojo.PageBean;
import com.rttmall.shopbackend.pojo.Pagination;
import com.rttmall.shopbackend.sys.mapper.UserCustomMapper;
import com.rttmall.shopbackend.sys.mapper.UserMapper;
import com.rttmall.shopbackend.sys.pojo.RoleCustom;
import com.rttmall.shopbackend.sys.pojo.User;
import com.rttmall.shopbackend.utils.Constants;
import com.rttmall.shopbackend.utils.MD5;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;

import java.util.*;


public class AgentServiceImpl implements AgentService {

	@Value("#{configProperties['agent.default.pwd']}")
	private String defaultPw;
	@Value("#{configProperties['agent.extension.link']}")
	private String agentLink;

	@Autowired
	private AgentCustomMapper agentCustomMapper;
	@Autowired
	private AgentMapper agentMapper;
	@Autowired
	private BusinessCustomMapper businessCustomMapper;
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private UserCustomMapper userCustomMapper;
	@Autowired
	private BusinessMapper businessMapper;


	@Override
	public Map queryHeadAgent(AgentCustom agentCustom, Pagination grid) {
		Map map = new HashMap();
		PageHelper.startPage(grid.getPageIndex()+1, grid.getPageSize());
		List<AgentCustom> agentCustomList = agentCustomMapper.queryHeadAgent(agentCustom);
		agentCustomList.forEach(w->{
			if(w.getAgentStatusEnum().getCode()==1){
				w.setAgentExtensionLink(agentLink+w.getId());
			}
		});
		PageBean<AgentCustom> pb = new PageBean(agentCustomList);
		map.put("data", pb.getList());
		map.put("total", pb.getTotal());
		return map;
	}

	@Override
	public Map queryBusinessAgent(AgentCustom agentCustom, Pagination grid) {
		Map map = new HashMap();
		PageHelper.startPage(grid.getPageIndex()+1, grid.getPageSize());
		List<AgentCustom> agentCustomList = agentCustomMapper.queryBusinessAgent(agentCustom);
		agentCustomList.forEach(w->{
			if(w.getAgentStatusEnum().getCode()==1){
				w.setAgentExtensionLink(agentLink+w.getId());
			}
		});
		PageBean<AgentCustom> pb = new PageBean(agentCustomList);
		map.put("data", pb.getList());
		map.put("total", pb.getTotal());
		return map;
	}
	@Override
	public Agent editQueryAgent(Integer id) {
		return agentMapper.selectByPrimaryKey(id);
	}

	@Override
	public void updateAgent(Agent agent) {
		Agent agent1 = agentMapper.selectByPrimaryKey(agent.getId());
		//查看属于哪个营业部
		Business business = businessMapper.selectByPrimaryKey(agent1.getBusinessId());
		//手续费标准 >= 营业部的手续费成本， 保证金标准 >=营业部的保证金标准
		if(agent.getServiceChargeStandard().compareTo(business.getServiceChargeCost())<0||
				agent.getBondStandard().compareTo(business.getBondStandard())<0){
			throw new ServiceException(Constants.AGENT_ADD_EDIT_ERROR);
		}
		agentMapper.updateByPrimaryKeySelective(agent);
	}

	@Override
	public void updateAgentByBusiness(Agent agent) {
		Agent agent1 = agentMapper.selectByPrimaryKey(agent.getId());
		//手续费标准 >= 手续费成本
		if(agent1.getServiceChargeStandard().compareTo(agent.getServiceChargeCost())<0){
			throw new ServiceException(Constants.AGENT_ADD_EDIT_ERROR);
		}
		agentMapper.updateByPrimaryKeySelective(agent);
	}
	@Override
	public void insertAgent(AgentCustom agentCustom,Integer userId) {
		MD5 md5 = new MD5();
		//查看属于哪个营业部
		Business business = businessCustomMapper.queryByLoginId(userId);
		//手续费标准 >=手续费成本；手续费标准 >= 营业部的手续费成本； 保证金标准 >=营业部的保证金标准
		if(agentCustom.getServiceChargeStandard().compareTo(business.getServiceChargeCost())<0||
				agentCustom.getBondStandard().compareTo(business.getBondStandard())<0 ||
				agentCustom.getServiceChargeStandard().compareTo(agentCustom.getServiceChargeCost())<0){
			throw new ServiceException(Constants.AGENT_ADD_EDIT_ERROR);
		}
		//自动构建用户名
		String shortName = business.getShortName();
		//查询营业部角色下以简称开头的用户最大的userName
		String maxUserName = userCustomMapper.queryMaxUserNameByRole(shortName+"-",Identity.AGENT.getCode());
		Integer number;
		if(StringUtils.isEmpty(maxUserName)){
			number = 10001;
		}else{
			number = Integer.parseInt(maxUserName)+1;
		}
		String userName = (shortName+"-"+number);
		//先生成登入后台用户
		User user = new User();
		user.setUserName(userName);
		user.setUserPw(md5.getMD5Str(userName + defaultPw));
		user.setCreateTime(new Date());
		user.setStatus(Status.getEnums(Status.INVALID.getCode()));
		userMapper.insertSelective(user);
		//分配一个营业部角色
		List<RoleCustom> roleCustoms = new ArrayList<>();
		RoleCustom roleCustom = new RoleCustom();
		roleCustom.setRoleId(8);
		roleCustom.setUser(user);
		roleCustoms.add(roleCustom);
		userCustomMapper.insertUserRole(roleCustoms);
		Agent agent = new Agent();
		BeanUtils.copyProperties(agentCustom,agent);
		agent.setBusinessId(business.getId());
		agent.setLoginId(user.getUserId());
		agentMapper.insertSelective(agent);
	}

	@Override
	public void addTemplateAccount(Agent agent) {
		//只能添加一次，判断一下
		Agent agent2 = agentMapper.selectByPrimaryKey(agent.getId());
		if(StringUtils.isEmpty(agent2.getTemplateAccount())){
			agentMapper.updateByPrimaryKeySelective(agent);
		}else{
			throw new ServiceException(Constants.TEMPLATE_ACCOUNT_ADD_ONCE);
		}
	}
	@Override
	public void updateTemplateAccount(Agent agent) {
		agentMapper.updateByPrimaryKeySelective(agent);
	}

	@Override
	public String queryAgentLink(int userId) {
		Agent agent = agentCustomMapper.queryAgentLinkByLoginId(userId);
		if(agent.getStatus()==1){
			return agentLink+agent.getId();
		}
		return "";
	}

	@Override
	public void updateAgentStatus(Integer id, Integer flag) {
		//判断是否已经添加了模版账号
		Agent agentx = agentMapper.selectByPrimaryKey(id);
		if(StringUtils.isEmpty(agentx.getTemplateAccount())){
			throw new ServiceException(Constants.TEMPLATE_ACCOUNT_NOT_EXIST);
		}
		Agent agent = new Agent();
		agent.setId(id);
		agent.setStatus(flag);
		User user = new User();
		if(flag==0){
			user.setStatus(Status.getEnums(Status.INVALID.getCode()));
		}else{
			user.setStatus(Status.getEnums(Status.VALID.getCode()));
		}
		Agent agent2 = agentMapper.selectByPrimaryKey(id);
		user.setUserId(agent2.getLoginId());
		userMapper.updateByPrimaryKeySelective(user);
		agentMapper.updateByPrimaryKeySelective(agent);
	}

	@Override
	public AgentCustom updateAgentPw(Integer id) {
		MD5 md5 = new MD5();
		Agent agent = agentMapper.selectByPrimaryKey(id);
		User user = userMapper.selectByPrimaryKey(agent.getLoginId());
		User user2 = new User();
		user2.setUserPw(md5.getMD5Str(user.getUserName() + defaultPw));
		user2.setUserId(user.getUserId());
		userMapper.updateByPrimaryKeySelective(user2);
		//查询登录账号
		AgentCustom agentCustom = new AgentCustom();
		agentCustom.setUserName(user.getUserName());
		agentCustom.setResetPw(defaultPw);
		return agentCustom;
	}
}
