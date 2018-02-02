package com.rttmall.shopbackend.app.customer.mapper;

import java.util.List;

import com.rttmall.shopbackend.app.customer.pojo.DrawingApplyCustom;

public interface DrawingApplyCustomMapper {

	List<DrawingApplyCustom> queryDrawing(DrawingApplyCustom drawingApplyCustom);

	List<DrawingApplyCustom> queryDrawingBusiness(
			DrawingApplyCustom drawingApplyCustom);

	List<DrawingApplyCustom> queryDrawingAgent(
			DrawingApplyCustom drawingApplyCustom);
}