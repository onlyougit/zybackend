package com.rttmall.shopbackend.utils;

import com.rttmall.shopbackend.app.customer.pojo.*;
import com.rttmall.shopbackend.exception.ServiceException;

import org.apache.poi.ss.usermodel.*;
import org.springframework.util.StringUtils;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.List;

public class ExportExcelUtil {
	
	/**
	 * 描述：根据文件路径获取项目中的文件
	 * @param fileDir 文件路径
	 * @return
	 * @throws Exception
	 */
	public  File getExcelDemoFile(String fileDir,HttpServletRequest request) throws Exception{
		File file = null;
		ServletContext context = request.getSession().getServletContext();
        String url = context.getRealPath("/WEB-INF/");
		
		file = new File(url+fileDir);
		if(!file.exists()){
			throw new ServiceException("模板文件不存在！");
		}
		return file;
	}

	
	/**
	 * 描述：设置简单的Cell样式
	 * @return
	 */
	public  CellStyle setSimpleCellStyle(Workbook wb){
		CellStyle cs = wb.createCellStyle();
		cs.setBorderBottom(CellStyle.BORDER_THIN); //下边框
		cs.setBorderLeft(CellStyle.BORDER_THIN);//左边框
		cs.setBorderTop(CellStyle.BORDER_THIN);//上边框
		cs.setBorderRight(CellStyle.BORDER_THIN);//右边框
		cs.setAlignment(CellStyle.ALIGN_CENTER); // 居中
		return cs;
	}


	public Workbook writeRechargeExcel(File file, String sheetName,
			List<RechargeCustom> rechargeCustoms)throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Workbook wb = null;
		Row row = null;
		Cell cell = null;
		FileInputStream fis = new FileInputStream(file);
		wb = new ImportExcelUtil().getWorkbook(fis, file.getName()); // 获取工作薄
		Sheet sheet = wb.getSheet(sheetName);

		// 循环插入数据
		int lastRow = sheet.getLastRowNum() + 1; // 插入数据的数据ROW
		CellStyle cs = setSimpleCellStyle(wb); // Excel单元格样式
		for (int i = 0; i < rechargeCustoms.size(); i++) {
			row = sheet.createRow(lastRow + i); // 创建新的ROW，用于数据插入
			// 按项目实际需求，在该处将对象数据插入到Excel中
			RechargeCustom oc = rechargeCustoms.get(i);
			if (null == oc) {
				break;
			}
			
			//营业部
			cell = row.createCell(0);
			if(null != oc.getBusiness() && !StringUtils.isEmpty(oc.getBusiness().getBusinessName())){
				cell.setCellValue(oc.getBusiness().getBusinessName());
			}else{
				cell.setCellValue("");
			}
			cell.setCellStyle(cs);
			
			//代理商
			cell = row.createCell(1);
			if(null != oc.getAgent() && !StringUtils.isEmpty(oc.getAgent().getAgentName())){
				cell.setCellValue(oc.getAgent().getAgentName());
			}else{
				cell.setCellValue("");
			}
			cell.setCellStyle(cs);
			
			//代理电话
			cell = row.createCell(2);
			if(null != oc.getAgent() && !StringUtils.isEmpty(oc.getAgent().getAgentPhone())){
				cell.setCellValue(oc.getAgent().getAgentPhone());
			}else{
				cell.setCellValue("");
			}
			cell.setCellStyle(cs);
			
			//交易账号
			cell = row.createCell(3);
			if(null != oc.getCustomer() && !StringUtils.isEmpty(oc.getCustomer().getCustomerName())){
				cell.setCellValue(oc.getCustomer().getCustomerName());
			}else{
				cell.setCellValue("");
			}
			cell.setCellStyle(cs);
			
			//客户手机号
			cell = row.createCell(4);
			if(null != oc.getCustomer() && !StringUtils.isEmpty(oc.getCustomer().getCustomerPhone())){
				cell.setCellValue(oc.getCustomer().getCustomerPhone());
			}else{
				cell.setCellValue("");
			}
			cell.setCellStyle(cs);
			
			//客户姓名
			cell = row.createCell(5);
			if(null != oc.getCustomer() && !StringUtils.isEmpty(oc.getCustomer().getCustomerRealName())){
				cell.setCellValue(oc.getCustomer().getCustomerRealName());
			}else{
				cell.setCellValue("");
			}
			cell.setCellStyle(cs);
			
			//账单号
			cell = row.createCell(6);
			if(null != oc.getOrderId()){
				cell.setCellValue(oc.getOrderId());
			}else{
				cell.setCellValue("");
			}
			cell.setCellStyle(cs);
			
			//充值金额
			cell = row.createCell(7);
			if(null != oc.getRechargeAmount()){
				cell.setCellValue(oc.getRechargeAmount().doubleValue());
			}else{
				cell.setCellValue("");
			}
			cell.setCellStyle(cs);
			
			//充值方式
			cell = row.createCell(8);
			if(null != oc.getRechargeWayEnum()){
				cell.setCellValue(oc.getRechargeWayEnum().getText());
			}else{
				cell.setCellValue("");
			}
			cell.setCellStyle(cs);
			
			//充值时间
			cell = row.createCell(9);
			if(null != oc.getRechargeTime()){
				cell.setCellValue(sdf.format(oc.getRechargeTime()));
			}else{
				cell.setCellValue("");
			}
			cell.setCellStyle(cs);

			//备注
			cell = row.createCell(10);
			if(null != oc.getRemark()){
				cell.setCellValue(oc.getRemark());
			}else{
				cell.setCellValue("");
			}
			cell.setCellStyle(cs);
		}
		return wb;
	}

	public Workbook writeDrawingExcel(File file, String sheetName, List<DrawingApplyCustom> drawingCustoms) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Workbook wb = null;
		Row row = null;
		Cell cell = null;
		FileInputStream fis = new FileInputStream(file);
		wb = new ImportExcelUtil().getWorkbook(fis, file.getName()); // 获取工作薄
		Sheet sheet = wb.getSheet(sheetName);

		// 循环插入数据
		int lastRow = sheet.getLastRowNum() + 1; // 插入数据的数据ROW
		CellStyle cs = setSimpleCellStyle(wb); // Excel单元格样式
		for (int i = 0; i < drawingCustoms.size(); i++) {
			row = sheet.createRow(lastRow + i); // 创建新的ROW，用于数据插入
			// 按项目实际需求，在该处将对象数据插入到Excel中
			DrawingApplyCustom oc = drawingCustoms.get(i);
			if (null == oc) {
				break;
			}

			//营业部
			cell = row.createCell(0);
			if(null != oc.getBusiness() && !StringUtils.isEmpty(oc.getBusiness().getBusinessName())){
				cell.setCellValue(oc.getBusiness().getBusinessName());
			}else{
				cell.setCellValue("");
			}
			cell.setCellStyle(cs);

			//代理商
			cell = row.createCell(1);
			if(null != oc.getAgent() && !StringUtils.isEmpty(oc.getAgent().getAgentName())){
				cell.setCellValue(oc.getAgent().getAgentName());
			}else{
				cell.setCellValue("");
			}
			cell.setCellStyle(cs);

			//代理电话
			cell = row.createCell(2);
			if(null != oc.getAgent() && !StringUtils.isEmpty(oc.getAgent().getAgentPhone())){
				cell.setCellValue(oc.getAgent().getAgentPhone());
			}else{
				cell.setCellValue("");
			}
			cell.setCellStyle(cs);

			//交易账号
			cell = row.createCell(3);
			if(null != oc.getCustomer() && !StringUtils.isEmpty(oc.getCustomer().getCustomerName())){
				cell.setCellValue(oc.getCustomer().getCustomerName());
			}else{
				cell.setCellValue("");
			}
			cell.setCellStyle(cs);

			//客户手机号
			cell = row.createCell(4);
			if(null != oc.getCustomer() && !StringUtils.isEmpty(oc.getCustomer().getCustomerPhone())){
				cell.setCellValue(oc.getCustomer().getCustomerPhone());
			}else{
				cell.setCellValue("");
			}
			cell.setCellStyle(cs);

			//客户姓名
			cell = row.createCell(5);
			if(null != oc.getCustomer() && !StringUtils.isEmpty(oc.getCustomer().getCustomerRealName())){
				cell.setCellValue(oc.getCustomer().getCustomerRealName());
			}else{
				cell.setCellValue("");
			}
			cell.setCellStyle(cs);

			//提款金额
			cell = row.createCell(6);
			if(null != oc.getDrawingAmount()){
				cell.setCellValue(oc.getDrawingAmount().doubleValue());
			}else{
				cell.setCellValue("");
			}
			cell.setCellStyle(cs);

			//银行
			cell = row.createCell(7);
			if(null != oc.getBank()){
				cell.setCellValue(oc.getBank());
			}else{
				cell.setCellValue("");
			}
			cell.setCellStyle(cs);

			//银行卡号
			cell = row.createCell(8);
			if(null != oc.getBankCardId()){
				cell.setCellValue(oc.getBankCardId());
			}else{
				cell.setCellValue("");
			}
			cell.setCellStyle(cs);

			//状态
			cell = row.createCell(9);
			if(null != oc.getDrawingApplyStatusEnum()){
				cell.setCellValue(oc.getDrawingApplyStatusEnum().getText());
			}else{
				cell.setCellValue("");
			}
			cell.setCellStyle(cs);

			//申请时间
			cell = row.createCell(10);
			if(null != oc.getApplyTime()){
				cell.setCellValue(sdf.format(oc.getApplyTime()));
			}else{
				cell.setCellValue("");
			}
			cell.setCellStyle(cs);

			//操作时间
			cell = row.createCell(11);
			if(null != oc.getOperationTime()){
				cell.setCellValue(sdf.format(oc.getOperationTime()));
			}else{
				cell.setCellValue("");
			}
			cell.setCellStyle(cs);

			//操作人
			cell = row.createCell(12);
			if(null != oc.getUserName()){
				cell.setCellValue(oc.getUserName());
			}else{
				cell.setCellValue("");
			}
			cell.setCellStyle(cs);
		}
		return wb;
	}

	public Workbook writeAgentSettlementExcel(File file, String sheetName, List<AgentSettlementCustom> agentSettlementCustoms) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Workbook wb = null;
		Row row = null;
		Cell cell = null;
		FileInputStream fis = new FileInputStream(file);
		wb = new ImportExcelUtil().getWorkbook(fis, file.getName()); // 获取工作薄
		Sheet sheet = wb.getSheet(sheetName);

		// 循环插入数据
		int lastRow = sheet.getLastRowNum() + 1; // 插入数据的数据ROW
		CellStyle cs = setSimpleCellStyle(wb); // Excel单元格样式
		for (int i = 0; i < agentSettlementCustoms.size(); i++) {
			row = sheet.createRow(lastRow + i); // 创建新的ROW，用于数据插入
			// 按项目实际需求，在该处将对象数据插入到Excel中
			AgentSettlementCustom oc = agentSettlementCustoms.get(i);
			if (null == oc) {
				break;
			}

			//营业部
			cell = row.createCell(0);
			if(null != oc.getBusiness() && !StringUtils.isEmpty(oc.getBusiness().getBusinessName())){
				cell.setCellValue(oc.getBusiness().getBusinessName());
			}else{
				cell.setCellValue("");
			}
			cell.setCellStyle(cs);

			//代理商
			cell = row.createCell(1);
			if(null != oc.getAgent() && !StringUtils.isEmpty(oc.getAgent().getAgentName())){
				cell.setCellValue(oc.getAgent().getAgentName());
			}else{
				cell.setCellValue("");
			}
			cell.setCellStyle(cs);

			//代理电话
			cell = row.createCell(2);
			if(null != oc.getAgent() && !StringUtils.isEmpty(oc.getAgent().getAgentPhone())){
				cell.setCellValue(oc.getAgent().getAgentPhone());
			}else{
				cell.setCellValue("");
			}
			cell.setCellStyle(cs);

			//当日总手续费
			cell = row.createCell(3);
			if(null != oc.getSumDailyServiceCharge()){
				cell.setCellValue(oc.getSumDailyServiceCharge().doubleValue());
			}else{
				cell.setCellValue("");
			}
			cell.setCellStyle(cs);
			//当日总手续费成本
			cell = row.createCell(4);
			if(null != oc.getSumDailyServiceChargeCost()){
				cell.setCellValue(oc.getSumDailyServiceChargeCost().doubleValue());
			}else{
				cell.setCellValue("");
			}
			cell.setCellStyle(cs);
			//当日返佣
			cell = row.createCell(5);
			if(null != oc.getDailyRebate()){
				cell.setCellValue(oc.getDailyRebate().doubleValue());
			}else{
				cell.setCellValue("");
			}
			cell.setCellStyle(cs);

			//结算时间
			cell = row.createCell(6);
			if(null != oc.getCreateTime()){
				cell.setCellValue(sdf.format(oc.getCreateTime()));
			}else{
				cell.setCellValue("");
			}
			cell.setCellStyle(cs);
		}
		return wb;
	}

	public Workbook writeFundDetailExcel(File file, String sheetName, List<FundDetailCustom> fundDetailCustoms) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Workbook wb = null;
		Row row = null;
		Cell cell = null;
		FileInputStream fis = new FileInputStream(file);
		wb = new ImportExcelUtil().getWorkbook(fis, file.getName()); // 获取工作薄
		Sheet sheet = wb.getSheet(sheetName);

		// 循环插入数据
		int lastRow = sheet.getLastRowNum() + 1; // 插入数据的数据ROW
		CellStyle cs = setSimpleCellStyle(wb); // Excel单元格样式
		for (int i = 0; i < fundDetailCustoms.size(); i++) {
			row = sheet.createRow(lastRow + i); // 创建新的ROW，用于数据插入
			// 按项目实际需求，在该处将对象数据插入到Excel中
			FundDetailCustom oc = fundDetailCustoms.get(i);
			if (null == oc) {
				break;
			}

			//营业部
			cell = row.createCell(0);
			if(null != oc.getBusiness() && !StringUtils.isEmpty(oc.getBusiness().getBusinessName())){
				cell.setCellValue(oc.getBusiness().getBusinessName());
			}else{
				cell.setCellValue("");
			}
			cell.setCellStyle(cs);

			//代理商
			cell = row.createCell(1);
			if(null != oc.getAgent() && !StringUtils.isEmpty(oc.getAgent().getAgentName())){
				cell.setCellValue(oc.getAgent().getAgentName());
			}else{
				cell.setCellValue("");
			}
			cell.setCellStyle(cs);

			//代理电话
			cell = row.createCell(2);
			if(null != oc.getAgent() && !StringUtils.isEmpty(oc.getAgent().getAgentPhone())){
				cell.setCellValue(oc.getAgent().getAgentPhone());
			}else{
				cell.setCellValue("");
			}
			cell.setCellStyle(cs);

			//交易账号
			cell = row.createCell(3);
			if(null != oc.getCustomer() && !StringUtils.isEmpty(oc.getCustomer().getCustomerName())){
				cell.setCellValue(oc.getCustomer().getCustomerName());
			}else{
				cell.setCellValue("");
			}
			cell.setCellStyle(cs);

			//客户手机号
			cell = row.createCell(4);
			if(null != oc.getCustomer() && !StringUtils.isEmpty(oc.getCustomer().getCustomerPhone())){
				cell.setCellValue(oc.getCustomer().getCustomerPhone());
			}else{
				cell.setCellValue("");
			}
			cell.setCellStyle(cs);

			//客户姓名
			cell = row.createCell(5);
			if(null != oc.getCustomer() && !StringUtils.isEmpty(oc.getCustomer().getCustomerRealName())){
				cell.setCellValue(oc.getCustomer().getCustomerRealName());
			}else{
				cell.setCellValue("");
			}
			cell.setCellStyle(cs);

			//流向
			cell = row.createCell(6);
			if(null != oc.getFlowWayEnum()){
				cell.setCellValue(oc.getFlowWayEnum().getText());
			}else{
				cell.setCellValue("");
			}
			cell.setCellStyle(cs);

			//变动金额
			cell = row.createCell(7);
			if(null != oc.getChangeAmount()){
				cell.setCellValue(oc.getChangeAmount().doubleValue());
			}else{
				cell.setCellValue("");
			}
			cell.setCellStyle(cs);

			//网页剩余金额
			cell = row.createCell(8);
			if(null != oc.getChargeAmount()){
				cell.setCellValue(oc.getChargeAmount().doubleValue());
			}else{
				cell.setCellValue("");
			}
			cell.setCellStyle(cs);

			//变动时间
			cell = row.createCell(9);
			if(null != oc.getChangeTime()){
				cell.setCellValue(sdf.format(oc.getChangeTime()));
			}else{
				cell.setCellValue("");
			}
			cell.setCellStyle(cs);

			//备注
			cell = row.createCell(10);
			if(null != oc.getRemark()){
				cell.setCellValue(oc.getRemark());
			}else{
				cell.setCellValue("");
			}
			cell.setCellStyle(cs);

		}
		return wb;
	}

	public Workbook writeFundExcel(File file, String sheetName, List<FundCustom> fundCustoms) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Workbook wb = null;
		Row row = null;
		Cell cell = null;
		FileInputStream fis = new FileInputStream(file);
		wb = new ImportExcelUtil().getWorkbook(fis, file.getName()); // 获取工作薄
		Sheet sheet = wb.getSheet(sheetName);

		// 循环插入数据
		int lastRow = sheet.getLastRowNum() + 1; // 插入数据的数据ROW
		CellStyle cs = setSimpleCellStyle(wb); // Excel单元格样式
		for (int i = 0; i < fundCustoms.size(); i++) {
			row = sheet.createRow(lastRow + i); // 创建新的ROW，用于数据插入
			// 按项目实际需求，在该处将对象数据插入到Excel中
			FundCustom oc = fundCustoms.get(i);
			if (null == oc) {
				break;
			}

			//营业部
			cell = row.createCell(0);
			if(null != oc.getBusiness() && !StringUtils.isEmpty(oc.getBusiness().getBusinessName())){
				cell.setCellValue(oc.getBusiness().getBusinessName());
			}else{
				cell.setCellValue("");
			}
			cell.setCellStyle(cs);

			//代理商
			cell = row.createCell(1);
			if(null != oc.getAgent() && !StringUtils.isEmpty(oc.getAgent().getAgentName())){
				cell.setCellValue(oc.getAgent().getAgentName());
			}else{
				cell.setCellValue("");
			}
			cell.setCellStyle(cs);

			//代理电话
			cell = row.createCell(2);
			if(null != oc.getAgent() && !StringUtils.isEmpty(oc.getAgent().getAgentPhone())){
				cell.setCellValue(oc.getAgent().getAgentPhone());
			}else{
				cell.setCellValue("");
			}
			cell.setCellStyle(cs);

			//交易账号
			cell = row.createCell(3);
			if(null != oc.getCustomer() && !StringUtils.isEmpty(oc.getCustomer().getCustomerName())){
				cell.setCellValue(oc.getCustomer().getCustomerName());
			}else{
				cell.setCellValue("");
			}
			cell.setCellStyle(cs);

			//客户手机号
			cell = row.createCell(4);
			if(null != oc.getCustomer() && !StringUtils.isEmpty(oc.getCustomer().getCustomerPhone())){
				cell.setCellValue(oc.getCustomer().getCustomerPhone());
			}else{
				cell.setCellValue("");
			}
			cell.setCellStyle(cs);

			//客户姓名
			cell = row.createCell(5);
			if(null != oc.getCustomer() && !StringUtils.isEmpty(oc.getCustomer().getCustomerRealName())){
				cell.setCellValue(oc.getCustomer().getCustomerRealName());
			}else{
				cell.setCellValue("");
			}
			cell.setCellStyle(cs);

			//网页账户余额
			cell = row.createCell(6);
			if(null != oc.getBalance()){
				cell.setCellValue(oc.getBalance().doubleValue());
			}else{
				cell.setCellValue("");
			}
			cell.setCellStyle(cs);

			//盘中权益资金
			cell = row.createCell(7);
			if(null != oc.getDepositBalance()){
				cell.setCellValue(oc.getDepositBalance().doubleValue());
			}else{
				cell.setCellValue("");
			}
			cell.setCellStyle(cs);

			//刷新时间
			cell = row.createCell(8);
			if(null != oc.getFlushTime()){
				cell.setCellValue(sdf.format(oc.getFlushTime()));
			}else{
				cell.setCellValue("");
			}
			cell.setCellStyle(cs);

		}
		return wb;
	}

	public Workbook writeCustomerExcel(File file, String sheetName, List<CustomerCustom> customerCustoms)throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Workbook wb = null;
		Row row = null;
		Cell cell = null;
		FileInputStream fis = new FileInputStream(file);
		wb = new ImportExcelUtil().getWorkbook(fis, file.getName()); // 获取工作薄
		Sheet sheet = wb.getSheet(sheetName);

		// 循环插入数据
		int lastRow = sheet.getLastRowNum() + 1; // 插入数据的数据ROW
		CellStyle cs = setSimpleCellStyle(wb); // Excel单元格样式
		for (int i = 0; i < customerCustoms.size(); i++) {
			row = sheet.createRow(lastRow + i); // 创建新的ROW，用于数据插入
			// 按项目实际需求，在该处将对象数据插入到Excel中
			CustomerCustom oc = customerCustoms.get(i);
			if (null == oc) {
				break;
			}

			//营业部
			cell = row.createCell(0);
			if (null != oc.getBusiness() && !StringUtils.isEmpty(oc.getBusiness().getBusinessName())) {
				cell.setCellValue(oc.getBusiness().getBusinessName());
			} else {
				cell.setCellValue("");
			}
			cell.setCellStyle(cs);

			//代理商
			cell = row.createCell(1);
			if (null != oc.getAgent() && !StringUtils.isEmpty(oc.getAgent().getAgentName())) {
				cell.setCellValue(oc.getAgent().getAgentName());
			} else {
				cell.setCellValue("");
			}
			cell.setCellStyle(cs);

			//代理电话
			cell = row.createCell(2);
			if (null != oc.getAgent() && !StringUtils.isEmpty(oc.getAgent().getAgentPhone())) {
				cell.setCellValue(oc.getAgent().getAgentPhone());
			} else {
				cell.setCellValue("");
			}
			cell.setCellStyle(cs);

			//交易账号
			cell = row.createCell(3);
			if (null != oc.getCustomerName()) {
				cell.setCellValue(oc.getCustomerName());
			} else {
				cell.setCellValue("");
			}
			cell.setCellStyle(cs);

			//客户手机号
			cell = row.createCell(4);
			if (null != oc.getCustomerPhone()) {
				cell.setCellValue(oc.getCustomerPhone());
			} else {
				cell.setCellValue("");
			}
			cell.setCellStyle(cs);

			//客户姓名
			cell = row.createCell(5);
			if (null != oc.getCustomerRealName()) {
				cell.setCellValue(oc.getCustomerRealName());
			} else {
				cell.setCellValue("");
			}
			cell.setCellStyle(cs);

			//身份证
			cell = row.createCell(6);
			if (null != oc.getCustomerCardId()) {
				cell.setCellValue(oc.getCustomerCardId());
			} else {
				cell.setCellValue("");
			}
			cell.setCellStyle(cs);

			//注册时间
			cell = row.createCell(7);
			if (null != oc.getRegistTime()) {
				cell.setCellValue(sdf.format(oc.getRegistTime()));
			} else {
				cell.setCellValue("");
			}
			cell.setCellStyle(cs);
		}
		return wb;
	}


	public Workbook writeSettlementExcel(File file, String sheetName,
			List<SettlementCustom> settlementCustoms) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Workbook wb = null;
		Row row = null;
		Cell cell = null;
		FileInputStream fis = new FileInputStream(file);
		wb = new ImportExcelUtil().getWorkbook(fis, file.getName()); // 获取工作薄
		Sheet sheet = wb.getSheet(sheetName);

		// 循环插入数据
		int lastRow = sheet.getLastRowNum() + 1; // 插入数据的数据ROW
		CellStyle cs = setSimpleCellStyle(wb); // Excel单元格样式
		for (int i = 0; i < settlementCustoms.size(); i++) {
			row = sheet.createRow(lastRow + i); // 创建新的ROW，用于数据插入
			// 按项目实际需求，在该处将对象数据插入到Excel中
			SettlementCustom oc = settlementCustoms.get(i);
			if (null == oc) {
				break;
			}
			//营业部
			cell = row.createCell(0);
			if(null != oc.getBusiness() && !StringUtils.isEmpty(oc.getBusiness().getBusinessName())){
				cell.setCellValue(oc.getBusiness().getBusinessName());
			}else{
				cell.setCellValue("");
			}
			cell.setCellStyle(cs);
			
			//代理商
			cell = row.createCell(1);
			if(null != oc.getAgent() && !StringUtils.isEmpty(oc.getAgent().getAgentName())){
				cell.setCellValue(oc.getAgent().getAgentName());
			}else{
				cell.setCellValue("");
			}
			cell.setCellStyle(cs);
			
			//代理电话
			cell = row.createCell(2);
			if(null != oc.getAgent() && !StringUtils.isEmpty(oc.getAgent().getAgentPhone())){
				cell.setCellValue(oc.getAgent().getAgentPhone());
			}else{
				cell.setCellValue("");
			}
			cell.setCellStyle(cs);

			//客户姓名
			cell = row.createCell(4);
			if(null != oc.getCustomer() && !StringUtils.isEmpty(oc.getCustomer().getCustomerRealName())){
				cell.setCellValue(oc.getCustomer().getCustomerRealName());
			}else{
				cell.setCellValue("");
			}
			cell.setCellStyle(cs);

			//客户手机号
			cell = row.createCell(3);
			if(null != oc.getCustomer() && !StringUtils.isEmpty(oc.getCustomer().getCustomerPhone())){
				cell.setCellValue(oc.getCustomer().getCustomerPhone());
			}else{
				cell.setCellValue("");
			}
			cell.setCellStyle(cs);
			
			//交易账号
			cell = row.createCell(5);
			if(null != oc.getCustomer() && !StringUtils.isEmpty(oc.getCustomer().getCustomerName())){
				cell.setCellValue(oc.getCustomer().getCustomerName());
			}else{
				cell.setCellValue("");
			}
			cell.setCellStyle(cs);
			
			//当日总手续费
			cell = row.createCell(6);
			if(null != oc.getSumDailyServiceCharge()){
				cell.setCellValue(oc.getSumDailyServiceCharge().doubleValue());
			}else{
				cell.setCellValue("");
			}
			cell.setCellStyle(cs);
			//当日总手续费成本
			cell = row.createCell(7);
			if(null != oc.getSumDailyServiceChargeCost()){
				cell.setCellValue(oc.getSumDailyServiceChargeCost().doubleValue());
			}else{
				cell.setCellValue("");
			}
			cell.setCellStyle(cs);
			//当日返佣
			cell = row.createCell(8);
			if(null != oc.getDailyRebate()){
				cell.setCellValue(oc.getDailyRebate().doubleValue());
			}else{
				cell.setCellValue("");
			}
			cell.setCellStyle(cs);

			//状态
			cell = row.createCell(9);
			if(null != oc.getSettlementStatusEnum()){
				cell.setCellValue(oc.getSettlementStatusEnum().getText());
			}else{
				cell.setCellValue("");
			}
			cell.setCellStyle(cs);

			//结算时间
			cell = row.createCell(10);
			if(null != oc.getCreateTime()){
				cell.setCellValue(sdf.format(oc.getCreateTime()));
			}else{
				cell.setCellValue("");
			}
			cell.setCellStyle(cs);
		}
		return wb;
	}
}
