package com.rttmall.shopbackend.app.customer.service.impl;

import com.github.pagehelper.PageHelper;
import com.rttmall.shopbackend.app.customer.mapper.*;
import com.rttmall.shopbackend.app.customer.pojo.*;
import com.rttmall.shopbackend.app.customer.service.DrawingApplyService;
import com.rttmall.shopbackend.enums.DrawingApplyStatus;
import com.rttmall.shopbackend.enums.FlowWay;
import com.rttmall.shopbackend.enums.FundRemark;
import com.rttmall.shopbackend.exception.ServiceException;
import com.rttmall.shopbackend.pojo.PageBean;
import com.rttmall.shopbackend.pojo.Pagination;
import com.rttmall.shopbackend.utils.Constants;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class DrawingApplyServiceImpl implements DrawingApplyService {

	@Autowired
	private CustomerCustomMapper customerCustomMapper;
	@Autowired
	private FundCustomMapper fundCustomMapper;
	@Autowired
	private FundMapper fundMapper;
	@Autowired
	private DrawingApplyCustomMapper drawingApplyCustomMapper;
	@Autowired
	private DrawingApplyMapper drawingApplyMapper;
	@Autowired
	private FundDetailMapper fundDetailMapper;
	@Autowired
	private BankCardCustomMapper bankCardCustomMapper;


	@Override
	public Map queryDrawing(DrawingApplyCustom drawingApplyCustom,
			Pagination grid) {
		Map map = new HashMap();
		PageHelper.startPage(grid.getPageIndex()+1, grid.getPageSize());
		List<DrawingApplyCustom> rechargeCustomList = drawingApplyCustomMapper.queryDrawing(drawingApplyCustom);
		PageBean<DrawingApplyCustom> pb = new PageBean(rechargeCustomList);
		map.put("data", pb.getList());
		map.put("total", pb.getTotal());
		return map;
	}

	@Override
	public Map queryDrawingBusiness(DrawingApplyCustom drawingApplyCustom,
			Pagination grid) {
		Map map = new HashMap();
		PageHelper.startPage(grid.getPageIndex()+1, grid.getPageSize());
		List<DrawingApplyCustom> rechargeCustomList = drawingApplyCustomMapper.queryDrawingBusiness(drawingApplyCustom);
		PageBean<DrawingApplyCustom> pb = new PageBean(rechargeCustomList);
		map.put("data", pb.getList());
		map.put("total", pb.getTotal());
		return map;
	}

	@Override
	public Map queryDrawingAgent(DrawingApplyCustom drawingApplyCustom,
			Pagination grid) {
		Map map = new HashMap();
		PageHelper.startPage(grid.getPageIndex()+1, grid.getPageSize());
		List<DrawingApplyCustom> rechargeCustomList = drawingApplyCustomMapper.queryDrawingAgent(drawingApplyCustom);
		PageBean<DrawingApplyCustom> pb = new PageBean(rechargeCustomList);
		map.put("data", pb.getList());
		map.put("total", pb.getTotal());
		return map;
	}

	@Override
	public void insertDrawing(DrawingApplyCustom drawingApplyCustom) {
		//查询客户
		Customer customer = customerCustomMapper.queryByPhoneAndRealName(drawingApplyCustom.getCustomer().getCustomerPhone(),drawingApplyCustom.getCustomer().getCustomerRealName());
		if(null == customer){
			throw new ServiceException(Constants.CUSTOMER_NOT_EXIST);
		}
		drawingApplyCustom.setCustomerId(customer.getId());
		//查询银行卡
		BankCard bankCard = bankCardCustomMapper.queryByBankCardId(drawingApplyCustom.getBankCardId());
		if(null == bankCard){
			throw new ServiceException(Constants.BANKCARD_NOT_EXIST);
		}
		drawingApplyCustom.setBank(bankCard.getBankName());
		DrawingApply drawingApply = new DrawingApply();
		BeanUtils.copyProperties(drawingApplyCustom, drawingApply);
		drawingApplyMapper.insertSelective(drawingApply);
	}

	@Override
	public void updateDrawingStatus(DrawingApply drawingApply) {
		if(drawingApply.getStatus()==DrawingApplyStatus.PASS.ordinal()){
			DrawingApply drawingApply2 = drawingApplyMapper.selectByPrimaryKey(drawingApply.getId());
			//更新资金余额
			Fund fund = fundCustomMapper.queryByCustomerId(drawingApply2.getCustomerId());
			if(fund.getBalance().compareTo(drawingApply2.getDrawingAmount())<0){
				throw new ServiceException(Constants.BALANCE_NOT_ENOUGH);
			}
			//更新提款申请表
			drawingApplyMapper.updateByPrimaryKeySelective(drawingApply);
			Fund fund2 = new Fund();
			fund2.setId(fund.getId());
			fund2.setBalance(fund.getBalance().subtract(drawingApply2.getDrawingAmount()).setScale(2, BigDecimal.ROUND_HALF_UP));
			fundMapper.updateByPrimaryKeySelective(fund2);
			//添加资金明细
			FundDetail fundDetail = new FundDetail();
			fundDetail.setChangeAmount(drawingApply2.getDrawingAmount());
			fundDetail.setChangeTime(new Date());
			fundDetail.setCustomerId(drawingApply2.getCustomerId());
			fundDetail.setChargeAmount(fund2.getBalance());
			fundDetail.setFlowWay(FlowWay.EXPEND.getCode());
			fundDetail.setRemark(FundRemark.DRAWING.getText());
			fundDetailMapper.insertSelective(fundDetail);
		}
	}

	@Override
	public List<DrawingApplyCustom> queryDrawingExcel(DrawingApplyCustom drawingCustom) {
		List<DrawingApplyCustom> rechargeCustomList = drawingApplyCustomMapper.queryDrawing(drawingCustom);
		return rechargeCustomList;
	}

	@Override
	public List<DrawingApplyCustom> queryDrawingBusinessExcel(
			DrawingApplyCustom drawingCustom) {
		List<DrawingApplyCustom> rechargeCustomList = drawingApplyCustomMapper.queryDrawingBusiness(drawingCustom);
		return rechargeCustomList;
	}

	@Override
	public List<DrawingApplyCustom> queryDrawingAgentExcel(
			DrawingApplyCustom drawingCustom) {
		List<DrawingApplyCustom> rechargeCustomList = drawingApplyCustomMapper.queryDrawingAgent(drawingCustom);
		return rechargeCustomList;
	}
}
