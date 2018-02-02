package com.rttmall.shopbackend.app.customer.controller;

import com.alibaba.fastjson.JSON;
import com.rttmall.shopbackend.app.customer.pojo.RechargeCustom;
import com.rttmall.shopbackend.app.customer.service.RechargeService;
import com.rttmall.shopbackend.app.service.SessionService;
import com.rttmall.shopbackend.enums.RechargeWay;
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
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("recharge")
public class RechargeController {
	
	@Value("#{configProperties['recharge.excel.path']}")
    private String rechargePath;
    @Value("#{configProperties['recharge.excel.sheetName']}")
    private String rechargeSheetName;
    @Value("#{configProperties['recharge.excel.exportName']}")
    private String rechargeExportName;
	@Autowired
	private RechargeService rechargeService;
	@Autowired
	private SessionService sessionService;
    
	@RequestMapping(method = RequestMethod.GET)
	public String findRecharge() {
		return "app/recharge/rechargeList";
	}

	@RequestMapping(value = "/findRechargeBusiness")
	public String findRechargeBusiness() {
		return "app/recharge/rechargeBusinessList";
	}

	@RequestMapping(value = "/findRechargeAgent")
	public String finRechargeAgent() {
		return "app/recharge/rechargeAgentList";
	}
	@RequestMapping(value = "/addRechargePage")
	public String addRechargePage() {
		return "app/recharge/rechargeAdd";
	}

	@RequestMapping(value = "/queryRecharge")
	public @ResponseBody Map queryRecharge(
			@RequestParam(defaultValue = "") String json, Pagination grid) {
		RechargeCustom rechargeCustom = JSON.parseObject(json, RechargeCustom.class);
		if(null == rechargeCustom){
			rechargeCustom = new RechargeCustom();
		}
		Map map = rechargeService.queryRecharge(rechargeCustom,grid);
		return map;
	}
	@RequestMapping(value = "/queryRechargeBusiness")
	public @ResponseBody Map queryRechargeBusiness(
			@RequestParam(defaultValue = "") String json, Pagination grid,HttpSession session) {
		int userId = sessionService.getUserId(session);
		RechargeCustom rechargeCustom = JSON.parseObject(json, RechargeCustom.class);
		if(null == rechargeCustom){
			rechargeCustom = new RechargeCustom();
		}
		rechargeCustom.setLoginId(userId);
		Map map = rechargeService.queryRechargeBusiness(rechargeCustom,grid);
		return map;
	}
	@RequestMapping(value = "/queryRechargeAgent")
	public @ResponseBody Map queryRechargeAgent(
			@RequestParam(defaultValue = "") String json, Pagination grid,HttpSession session) {
		int userId = sessionService.getUserId(session);
		RechargeCustom rechargeCustom = JSON.parseObject(json, RechargeCustom.class);
		if(null == rechargeCustom){
			rechargeCustom = new RechargeCustom();
		}
		rechargeCustom.setLoginId(userId);
		Map map = rechargeService.queryRechargeAgent(rechargeCustom,grid);
		return map;
	}
	@RequestMapping("/insertRechargeSubmit")
	public
	@ResponseBody
	void insertRechargeSubmit(RechargeCustom rechargeCustom) throws Exception {
		rechargeCustom.setRechargeWay(RechargeWay.BACKEND.getCode());
		rechargeCustom.setRechargeTime(new Date());
		rechargeService.insertRecharge(rechargeCustom);
	}
	/**
	 * admin和总部导出
	 * @param queryParam
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/exportRecharge")
	public
	@ResponseBody
	void exportRecharge(String queryParam,HttpServletRequest request,HttpServletResponse response) throws Exception {
		RechargeCustom rechargeCustom = JSON.parseObject(queryParam, RechargeCustom.class);
		if(null == rechargeCustom){
			rechargeCustom = new RechargeCustom();
		}
		exportExcel(rechargeCustom,request,response,rechargePath,rechargeSheetName,rechargeExportName,1);
	}
	/**
	 * 营业部导出
	 * @param queryParam
	 * @param request
	 * @param response
	 * @param session
	 * @throws Exception
	 */
	@RequestMapping("/exportRechargeBusiness")
	public
	@ResponseBody
	void exportRechargeBusiness(String queryParam, HttpServletRequest request, HttpServletResponse response,HttpSession session) throws Exception {
		int userId = sessionService.getUserId(session);
		RechargeCustom rechargeCustom = JSON.parseObject(queryParam, RechargeCustom.class);
		if(null == rechargeCustom){
			rechargeCustom = new RechargeCustom();
		}
		rechargeCustom.setLoginId(userId);
		exportExcel(rechargeCustom,request,response,rechargePath,rechargeSheetName,rechargeExportName,2);
	}
	/**
	 * 代理导出
	 * @param queryParam
	 * @param request
	 * @param response
	 * @param session
	 * @throws Exception
	 */
	@RequestMapping("/exportRechargeAgent")
	public
	@ResponseBody
	void exportRechargeAgent(String queryParam, HttpServletRequest request, HttpServletResponse response,HttpSession session) throws Exception {
		int userId = sessionService.getUserId(session);
		RechargeCustom rechargeCustom = JSON.parseObject(queryParam, RechargeCustom.class);
		if(null == rechargeCustom){
			rechargeCustom = new RechargeCustom();
		}
		rechargeCustom.setLoginId(userId);
		exportExcel(rechargeCustom,request,response,rechargePath,rechargeSheetName,rechargeExportName,3);
	}
	/**
	 * 导出功能抽取
	 * @param rechargeCustom
	 * @param request
	 * @param response
	 * @param path
	 * @param sheetName
	 * @param exportName
	 * @param flag
	 * @throws Exception
	 */
	public void exportExcel(RechargeCustom rechargeCustom, HttpServletRequest request, HttpServletResponse response,
			String path, String sheetName, String exportName,Integer flag) throws Exception {
		OutputStream os = null;
		Workbook wb = null;    //工作薄
		ExportExcelUtil util = new ExportExcelUtil();
		File file =util.getExcelDemoFile(path,request);
		try {
			List<RechargeCustom> rechargeCustoms;
			if(flag==1){//admin和总部导出
				rechargeCustoms = rechargeService.queryRechargeExcel(rechargeCustom);
			}else if(flag==2){//营业部导出
				rechargeCustoms = rechargeService.queryRechargeBusinessExcel(rechargeCustom);
			}else{//代理导出
				rechargeCustoms = rechargeService.queryRechargeAgentExcel(rechargeCustom);
			}
			wb = util.writeRechargeExcel(file, sheetName,rechargeCustoms); 
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
