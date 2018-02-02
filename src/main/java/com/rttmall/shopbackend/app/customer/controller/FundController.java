package com.rttmall.shopbackend.app.customer.controller;

import com.alibaba.fastjson.JSON;
import com.rttmall.shopbackend.app.customer.pojo.FundCustom;
import com.rttmall.shopbackend.app.customer.service.FundService;
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
@RequestMapping("fund")
public class FundController {

	@Value("#{configProperties['fund.excel.path']}")
	private String fundPath;
	@Value("#{configProperties['fund.excel.sheetName']}")
	private String fundSheetName;
	@Value("#{configProperties['fund.excel.exportName']}")
	private String fundExportName;
	@Autowired
	private FundService fundService;
	@Autowired
	private SessionService sessionService;
    
	@RequestMapping(method = RequestMethod.GET)
	public String findFund() {
		return "app/fund/fundList";
	}

	@RequestMapping(value = "/findFundBusiness")
	public String findFundBusiness() {
		return "app/fund/fundBusinessList";
	}

	@RequestMapping(value = "/findFundAgent")
	public String findFundAgent() {
		return "app/fund/fundAgentList";
	}

	@RequestMapping(value = "/queryFund")
	public @ResponseBody Map queryFund(
			@RequestParam(defaultValue = "") String json, Pagination grid) {
		FundCustom fundCustom = JSON.parseObject(json, FundCustom.class);
		if(null == fundCustom){
			fundCustom = new FundCustom();
		}
		Map map = fundService.queryFund(fundCustom,grid);
		return map;
	}
	@RequestMapping(value = "/queryFundBusiness")
	public @ResponseBody Map queryFundBusiness(
			@RequestParam(defaultValue = "") String json, Pagination grid,HttpSession session) {
		int userId = sessionService.getUserId(session);
		FundCustom fundCustom = JSON.parseObject(json, FundCustom.class);
		if(null == fundCustom){
			fundCustom = new FundCustom();
		}
		fundCustom.setLoginId(userId);
		Map map = fundService.queryFundBusiness(fundCustom,grid);
		return map;
	}
	@RequestMapping(value = "/queryFundAgent")
	public @ResponseBody Map queryFundAgent(
			@RequestParam(defaultValue = "") String json, Pagination grid,HttpSession session) {
		int userId = sessionService.getUserId(session);
		FundCustom fundCustom = JSON.parseObject(json, FundCustom.class);
		if(null == fundCustom){
			fundCustom = new FundCustom();
		}
		fundCustom.setLoginId(userId);
		Map map = fundService.queryFundAgent(fundCustom,grid);
		return map;
	}
	@RequestMapping(value = "/flushBalance")
	public @ResponseBody void flushBalance(Integer id) {
		fundService.flushBalance(id);
	}

	/**
	 * admin和总部导出
	 * @param queryParam
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/exportFund")
	public
	@ResponseBody
	void exportFund(String queryParam, HttpServletRequest request, HttpServletResponse response) throws Exception {
		FundCustom fundCustom = JSON.parseObject(queryParam, FundCustom.class);
		if(null == fundCustom){
			fundCustom = new FundCustom();
		}
		exportExcel(fundCustom,request,response,fundPath,fundSheetName,fundExportName,1);
	}
	/**
	 * 营业部导出
	 * @param queryParam
	 * @param request
	 * @param response
	 * @param session
	 * @throws Exception
	 */
	@RequestMapping("/exportFundBusiness")
	public
	@ResponseBody
	void exportFundBusiness(String queryParam, HttpServletRequest request, HttpServletResponse response,HttpSession session) throws Exception {
		int userId = sessionService.getUserId(session);
		FundCustom fundCustom = JSON.parseObject(queryParam, FundCustom.class);
		if(null == fundCustom){
			fundCustom = new FundCustom();
		}
		fundCustom.setLoginId(userId);
		exportExcel(fundCustom,request,response,fundPath,fundSheetName,fundExportName,2);
	}
	/**
	 * 代理导出
	 * @param queryParam
	 * @param request
	 * @param response
	 * @param session
	 * @throws Exception
	 */
	@RequestMapping("/exportCustomerAgent")
	public
	@ResponseBody
	void exportCustomerAgent(String queryParam, HttpServletRequest request, HttpServletResponse response,HttpSession session) throws Exception {
		int userId = sessionService.getUserId(session);
		FundCustom fundCustom = JSON.parseObject(queryParam, FundCustom.class);
		if(null == fundCustom){
			fundCustom = new FundCustom();
		}
		fundCustom.setLoginId(userId);
		exportExcel(fundCustom,request,response,fundPath,fundSheetName,fundExportName,3);
	}
	/**
	 * 导出功能抽取
	 * @param fundCustom
	 * @param request
	 * @param response
	 * @param path
	 * @param sheetName
	 * @param exportName
	 * @param flag
	 * @throws Exception
	 */
	public void exportExcel(FundCustom fundCustom, HttpServletRequest request, HttpServletResponse response,
			String path, String sheetName, String exportName,Integer flag) throws Exception {
		OutputStream os = null;
		Workbook wb = null;    //工作薄
		ExportExcelUtil util = new ExportExcelUtil();
		File file =util.getExcelDemoFile(path,request);
		try {
			List<FundCustom> fundCustoms;
			if(flag==1){//admin和总部导出
				fundCustoms = fundService.queryFundExcel(fundCustom);
			}else if(flag==2){//营业部导出
				fundCustoms = fundService.queryFundBusinessExcel(fundCustom);
			}else{//代理导出
				fundCustoms = fundService.queryFundAgentExcel(fundCustom);
			}
			wb = util.writeFundExcel(file, sheetName,fundCustoms);
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
