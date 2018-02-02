package com.rttmall.shopbackend.app.customer.service;

import com.rttmall.shopbackend.app.customer.pojo.DrawingApply;
import com.rttmall.shopbackend.app.customer.pojo.DrawingApplyCustom;
import com.rttmall.shopbackend.pojo.Pagination;

import java.util.List;
import java.util.Map;

public interface DrawingApplyService {

	Map queryDrawing(DrawingApplyCustom drawingApplyCustom, Pagination grid);

	Map queryDrawingBusiness(DrawingApplyCustom drawingApplyCustom,
			Pagination grid);

	Map queryDrawingAgent(DrawingApplyCustom drawingApplyCustom, Pagination grid);

	void insertDrawing(DrawingApplyCustom drawingApplyCustom);

	void updateDrawingStatus(DrawingApply drawingApply);

	List<DrawingApplyCustom> queryDrawingExcel(DrawingApplyCustom drawingCustom);

	List<DrawingApplyCustom> queryDrawingBusinessExcel(
			DrawingApplyCustom drawingCustom);

	List<DrawingApplyCustom> queryDrawingAgentExcel(
			DrawingApplyCustom drawingCustom);
}
