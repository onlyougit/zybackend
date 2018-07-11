package com.rttmall.shopbackend.app.agent.service.impl;

import com.github.pagehelper.PageHelper;
import com.rttmall.shopbackend.app.agent.mapper.BusinessCustomMapper;
import com.rttmall.shopbackend.app.agent.mapper.BusinessMapper;
import com.rttmall.shopbackend.app.agent.pojo.Business;
import com.rttmall.shopbackend.app.agent.pojo.BusinessCustom;
import com.rttmall.shopbackend.app.agent.service.BusinessService;
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

import java.util.*;


public class BusinessServiceImpl implements BusinessService {

	@Value("#{configProperties['user.default.pwd']}")
	private String defaultPw;

	@Autowired
	private BusinessCustomMapper businessCustomMapper;
	@Autowired
	private BusinessMapper businessMapper;
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private UserCustomMapper userCustomMapper;

	@Override
	public Map queryBusiness(BusinessCustom businessCustom, Pagination grid) {
		Map map = new HashMap();
		PageHelper.startPage(grid.getPageIndex()+1, grid.getPageSize());
		List<BusinessCustom> businessCustomList = businessCustomMapper.queryBusiness(businessCustom);
		PageBean<BusinessCustom> pb = new PageBean(businessCustomList);
		map.put("data", pb.getList());
		map.put("total", pb.getTotal());
		return map;
	}

	@Override
	public Business editQueryBusiness(Integer id) {
		return businessMapper.selectByPrimaryKey(id);
	}

	@Override
	public void updateBusiness(Business business) {
		businessMapper.updateByPrimaryKeySelective(business);
	}

	@Override
	public void insertBusiness(BusinessCustom businessCustom) {
		MD5 md5 = new MD5();
		//判断简称唯一
		businessCustom.setShortName(businessCustom.getShortName().toUpperCase());
		int counts = businessCustomMapper.countByShortName(businessCustom.getShortName());
		if(counts > 0){
			throw new ServiceException(Constants.SHORT_NAME_REPEAT);
		}
		//先生成登入后台用户
		int count = userCustomMapper.isExistUser(businessCustom.getUserName());
		if (count != 0) {
			throw new ServiceException(Constants.USERNAME_EXIST);
		}
		User user = new User();
		user.setUserName(businessCustom.getUserName());
		user.setUserPw(md5.getMD5Str(businessCustom.getUserName() + defaultPw));
		user.setCreateTime(new Date());
		user.setStatus(Status.getEnums(Status.VALID.getCode()));
		userMapper.insertSelective(user);
		//分配一个营业部角色
		List<RoleCustom> roleCustoms = new ArrayList<>();
		RoleCustom roleCustom = new RoleCustom();
		roleCustom.setRoleId(7);
		roleCustom.setUser(user);
		roleCustoms.add(roleCustom);
		userCustomMapper.insertUserRole(roleCustoms);
		Business business = new Business();
		BeanUtils.copyProperties(businessCustom,business);
		business.setLoginId(user.getUserId());
		businessMapper.insertSelective(business);
	}

	@Override
	public BusinessCustom updateBusinessPw(Integer id) {
		MD5 md5 = new MD5();
		Business business = businessMapper.selectByPrimaryKey(id);
		User user = userMapper.selectByPrimaryKey(business.getLoginId());
		User user2 = new User();
		user2.setUserPw(md5.getMD5Str(user.getUserName() + defaultPw));
		user2.setUserId(user.getUserId());
		userMapper.updateByPrimaryKeySelective(user2);
		BusinessCustom businessCustom = new BusinessCustom();
		businessCustom.setUserName(user.getUserName());
		businessCustom.setResetPw(defaultPw);
		return businessCustom;
	}


}
