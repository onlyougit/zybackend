package com.rttmall.shopbackend.app.customer.controller;

import com.alibaba.fastjson.JSON;
import com.rttmall.shopbackend.app.customer.pojo.SettlementCustom;
import com.rttmall.shopbackend.app.customer.service.SettlementService;
import com.rttmall.shopbackend.app.service.SessionService;
import com.rttmall.shopbackend.pojo.Pagination;
import com.rttmall.shopbackend.utils.ExportExcelUtil;

import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("settlement")
public class SettlementController {
	@Value("#{configProperties['settlement.excel.path']}")
	private String settlementPath;
	@Value("#{configProperties['settlement.excel.sheetName']}")
	private String settlementSheetName;
	@Value("#{configProperties['settlement.excel.exportName']}")
	private String settlementExportName;

	@Autowired
	private SettlementService settlementService;
	@Autowired
	private SessionService sessionService;
    
	@RequestMapping(method = RequestMethod.GET)
	public String findSettlement() {
		return "app/settlement/settlementList";
	}

	@RequestMapping(value = "/findSettlementBusiness")
	public String findSettlementBusiness() {
		return "app/settlement/settlementBusinessList";
	}

	@RequestMapping(value = "/findSettlementAgent")
	public String findSettlementAgent() {
		return "app/settlement/settlementAgentList";
	}

	@RequestMapping(value = "/querySettlement")
	public @ResponseBody Map querySettlement(
			@RequestParam(defaultValue = "") String json, Pagination grid) {
		SettlementCustom settlementCustom = JSON.parseObject(json, SettlementCustom.class);
		if(null == settlementCustom){
			settlementCustom = new SettlementCustom();
		}
		Map map = settlementService.querySettlement(settlementCustom,grid);
		return map;
	}
	@RequestMapping(value = "/querySettlementBusiness")
	public @ResponseBody Map querySettlementBusiness(
			@RequestParam(defaultValue = "") String json, Pagination grid,HttpSession session) {
		int userId = sessionService.getUserId(session);
		SettlementCustom settlementCustom = JSON.parseObject(json, SettlementCustom.class);
		if(null == settlementCustom){
			settlementCustom = new SettlementCustom();
		}
		settlementCustom.setLoginId(userId);
		Map map = settlementService.querySettlementBusiness(settlementCustom,grid);
		return map;
	}
	@RequestMapping(value = "/querySettlementAgent")
	public @ResponseBody Map querySettlementAgent(
			@RequestParam(defaultValue = "") String json, Pagination grid,HttpSession session) {
		int userId = sessionService.getUserId(session);
		SettlementCustom settlementCustom = JSON.parseObject(json, SettlementCustom.class);
		if(null == settlementCustom){
			settlementCustom = new SettlementCustom();
		}
		settlementCustom.setLoginId(userId);
		Map map = settlementService.querySettlementAgent(settlementCustom,grid);
		return map;
	}
	/**
	 * admin和总部导出
	 * @param queryParam
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/exportSettlement")
	public
	@ResponseBody
	void exportAgentSettlement(String queryParam, HttpServletRequest request, HttpServletResponse response) throws Exception {
		SettlementCustom settlementCustom = JSON.parseObject(queryParam, SettlementCustom.class);
		if(null == settlementCustom){
			settlementCustom = new SettlementCustom();
		}
		exportExcel(settlementCustom,request,response,settlementPath,settlementSheetName,settlementExportName,1);
	}
	/**
	 * 营业部导出
	 * @param queryParam
	 * @param request
	 * @param response
	 * @param session
	 * @throws Exception
	 */
	@RequestMapping("/exportSettlementBusiness")
	public
	@ResponseBody
	void exportSettlementBusiness(String queryParam, HttpServletRequest request, HttpServletResponse response,HttpSession session) throws Exception {
		int userId = sessionService.getUserId(session);
		SettlementCustom settlementCustom = JSON.parseObject(queryParam, SettlementCustom.class);
		if(null == settlementCustom){
			settlementCustom = new SettlementCustom();
		}
		settlementCustom.setLoginId(userId);
		exportExcel(settlementCustom,request,response,settlementPath,settlementSheetName,settlementExportName,2);
	}
	/**
	 * 代理导出
	 * @param queryParam
	 * @param request
	 * @param response
	 * @param session
	 * @throws Exception
	 */
	@RequestMapping("/exportSettlementAgent")
	public
	@ResponseBody
	void exportSettlementAgent(String queryParam, HttpServletRequest request, HttpServletResponse response,HttpSession session) throws Exception {
		int userId = sessionService.getUserId(session);
		SettlementCustom settlementCustom = JSON.parseObject(queryParam, SettlementCustom.class);
		if(null == settlementCustom){
			settlementCustom = new SettlementCustom();
		}
		settlementCustom.setLoginId(userId);
		exportExcel(settlementCustom,request,response,settlementPath,settlementSheetName,settlementExportName,3);
	}
	

	/**
	 * 导出功能抽取
	 * @param queryParam
	 * @param request
	 * @param response
	 * @param path
	 * @param sheetName
	 * @param exportName
	 * @param flag
     * @throws Exception
     */
	public void exportExcel(SettlementCustom settlementCustom, HttpServletRequest request, HttpServletResponse response,
							String path, String sheetName, String exportName,Integer flag) throws Exception {
		
		OutputStream os = null;
		Workbook wb = null;
		ExportExcelUtil util = new ExportExcelUtil();
		File file =util.getExcelDemoFile(path,request);
		try {
			List<SettlementCustom> settlementCustoms;
			if(flag==1){//admin和总部导出
				settlementCustoms = settlementService.querySettlementExcel(settlementCustom);
			}else if(flag==2){//营业部导出
				settlementCustoms = settlementService.querySettlementBusinessExcel(settlementCustom);
			}else{//代理导出
				settlementCustoms = settlementService.querySettlementAgentExcel(settlementCustom);
			}
			wb = util.writeSettlementExcel(file, sheetName,settlementCustoms);
			response.reset();
			response.setContentType("application/vnd.ms-excel;charset=utf-8");
			response.setHeader("Content-Disposition", "attachment;filename="+ URLEncoder.encode(exportName, "utf-8"));
			os = response.getOutputStream();
			wb.write(os);
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			if(null!=os){
				os.flush();
				os.close();
			}
			if(null!=wb){
				wb.close();
			}
		}
	}
}
