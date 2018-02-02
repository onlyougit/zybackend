package com.rttmall.shopbackend.app.customer.controller;

import com.alibaba.fastjson.JSON;
import com.rttmall.shopbackend.app.customer.pojo.AgentSettlementCustom;
import com.rttmall.shopbackend.app.customer.pojo.SettlementCustom;
import com.rttmall.shopbackend.app.customer.service.AgentSettlementService;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.File;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("agentSettlement")
public class AgentSettlementController {
	@Value("#{configProperties['agent.settlement.excel.path']}")
	private String agentSettlementPath;
	@Value("#{configProperties['agent.settlement.excel.sheetName']}")
	private String agentSettlementSheetName;
	@Value("#{configProperties['agent.settlement.excel.exportName']}")
	private String agentSettlementExportName;

	@Autowired
	private AgentSettlementService agentSettlementService;
	@Autowired
	private SessionService sessionService;
    
	@RequestMapping(method = RequestMethod.GET)
	public String findAgentSettlement() {
		return "app/agentSettlement/agentSettlementList";
	}

	@RequestMapping(value = "/findAgentSettlementBusiness")
	public String findAgentSettlementBusiness() {
		return "app/agentSettlement/agentSettlementBusinessList";
	}

	@RequestMapping(value = "/queryAgentSettlement")
	public @ResponseBody Map queryAgentSettlement(
			@RequestParam(defaultValue = "") String json, Pagination grid) {
		AgentSettlementCustom agentSettlementCustom = JSON.parseObject(json, AgentSettlementCustom.class);
		if(null == agentSettlementCustom){
			agentSettlementCustom = new AgentSettlementCustom();
		}
		Map map = agentSettlementService.queryAgentSettlement(agentSettlementCustom,grid);
		return map;
	}
	@RequestMapping(value = "/queryAgentSettlementBusiness")
	public @ResponseBody Map queryAgentSettlementBusiness(
			@RequestParam(defaultValue = "") String json, Pagination grid,HttpSession session) {
		int userId = sessionService.getUserId(session);
		AgentSettlementCustom agentSettlementCustom = JSON.parseObject(json, AgentSettlementCustom.class);
		if(null == agentSettlementCustom){
			agentSettlementCustom = new AgentSettlementCustom();
		}
		agentSettlementCustom.setLoginId(userId);
		Map map = agentSettlementService.queryAgentSettlementBusiness(agentSettlementCustom,grid);
		return map;
	}
	@RequestMapping(value = "/querySettlement")
	public @ResponseBody Map querySettlement(Integer agentId, Pagination grid) {
		SettlementCustom settlementCustom = new SettlementCustom();
		settlementCustom.setLoginId(agentId);
		Map map = agentSettlementService.querySettlement(settlementCustom,grid);
		return map;
	}

	/**
	 * admin和总部导出
	 * @param queryParam
	 * @param request
	 * @param response
	 * @throws Exception
     */
	@RequestMapping("/exportAgentSettlement")
	public
	@ResponseBody
	void exportAgentSettlement(String queryParam, HttpServletRequest request, HttpServletResponse response) throws Exception {
		AgentSettlementCustom agentSettlementCustom = JSON.parseObject(queryParam, AgentSettlementCustom.class);
		if(null == agentSettlementCustom){
			agentSettlementCustom = new AgentSettlementCustom();
		}
		exportExcel(agentSettlementCustom,request,response,agentSettlementPath,agentSettlementSheetName,agentSettlementExportName,1);
	}

	/**
	 * 营业部角色导出
	 * @param queryParam
	 * @param request
	 * @param response
	 * @throws Exception
     */
	@RequestMapping("/exportAgentSettlementBusiness")
	public
	@ResponseBody
	void exportAgentSettlementBusiness(String queryParam, HttpServletRequest request, HttpServletResponse response,HttpSession session) throws Exception {
		int userId = sessionService.getUserId(session);
		AgentSettlementCustom agentSettlementCustom = JSON.parseObject(queryParam, AgentSettlementCustom.class);
		if(null == agentSettlementCustom){
			agentSettlementCustom = new AgentSettlementCustom();
		}
		agentSettlementCustom.setLoginId(userId);
		exportExcel(agentSettlementCustom,request,response,agentSettlementPath,agentSettlementSheetName,agentSettlementExportName,2);
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
	public void exportExcel(AgentSettlementCustom agentSettlementCustom, HttpServletRequest request, HttpServletResponse response,
							String path, String sheetName, String exportName,Integer flag) throws Exception {
		OutputStream os = null;
		Workbook wb = null;
		ExportExcelUtil util = new ExportExcelUtil();
		File file =util.getExcelDemoFile(path,request);
		try {
			List<AgentSettlementCustom> agentSettlementCustoms;
			if(flag==1){//admin和总部导出
				agentSettlementCustoms = agentSettlementService.queryAgentSettlementExcel(agentSettlementCustom);
			}else{//营业部导出
				agentSettlementCustoms = agentSettlementService.queryAgentSettlementBusinessExcel(agentSettlementCustom);
			}
			wb = util.writeAgentSettlementExcel(file, sheetName,agentSettlementCustoms);
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
