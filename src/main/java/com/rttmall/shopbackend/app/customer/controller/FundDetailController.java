package com.rttmall.shopbackend.app.customer.controller;

import com.alibaba.fastjson.JSON;
import com.rttmall.shopbackend.app.customer.pojo.FundDetailCustom;
import com.rttmall.shopbackend.app.customer.service.FundDetailService;
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
@RequestMapping("fundDetail")
public class FundDetailController {

	@Value("#{configProperties['fundDetail.excel.path']}")
	private String fundDetailPath;
	@Value("#{configProperties['fundDetail.excel.sheetName']}")
	private String fundDetailSheetName;
	@Value("#{configProperties['fundDetail.excel.exportName']}")
	private String fundDetailExportName;
	@Autowired
	private FundDetailService fundDetailService;
	@Autowired
	private SessionService sessionService;
    
	@RequestMapping(method = RequestMethod.GET)
	public String findFundDetail() {
		return "app/fundDetail/fundDetailList";
	}

	@RequestMapping(value = "/findFundDetailBusiness")
	public String findFundDetailBusiness() {
		return "app/fundDetail/fundDetailBusinessList";
	}

	@RequestMapping(value = "/findFundDetailAgent")
	public String findFundDetailAgent() {
		return "app/fundDetail/fundDetailAgentList";
	}

	@RequestMapping(value = "/queryFundDetail")
	public @ResponseBody Map queryFundDetail(
			@RequestParam(defaultValue = "") String json, Pagination grid) {
		FundDetailCustom fundDetailCustom = JSON.parseObject(json, FundDetailCustom.class);
		if(null == fundDetailCustom){
			fundDetailCustom = new FundDetailCustom();
		}
		Map map = fundDetailService.queryFundDetail(fundDetailCustom,grid);
		return map;
	}
	@RequestMapping(value = "/queryFundDetailBusiness")
	public @ResponseBody Map queryFundDetailBusiness(
			@RequestParam(defaultValue = "") String json, Pagination grid,HttpSession session) {
		int userId = sessionService.getUserId(session);
		FundDetailCustom fundDetailCustom = JSON.parseObject(json, FundDetailCustom.class);
		if(null == fundDetailCustom){
			fundDetailCustom = new FundDetailCustom();
		}
		fundDetailCustom.setLoginId(userId);
		Map map = fundDetailService.queryFundDetailBusiness(fundDetailCustom,grid);
		return map;
	}
	@RequestMapping(value = "/queryFundDetailAgent")
	public @ResponseBody Map queryFundDetailAgent(
			@RequestParam(defaultValue = "") String json, Pagination grid,HttpSession session) {
		int userId = sessionService.getUserId(session);
		FundDetailCustom fundDetailCustom = JSON.parseObject(json, FundDetailCustom.class);
		if(null == fundDetailCustom){
			fundDetailCustom = new FundDetailCustom();
		}
		fundDetailCustom.setLoginId(userId);
		Map map = fundDetailService.queryFundDetailAgent(fundDetailCustom,grid);
		return map;
	}
	/**
	 * admin和总部导出
	 * @param queryParam
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/exportFundDetail")
	public
	@ResponseBody
	void exportFundDetail(String queryParam, HttpServletRequest request, HttpServletResponse response) throws Exception {
		FundDetailCustom fundDetailCustom = JSON.parseObject(queryParam, FundDetailCustom.class);
		if(null == fundDetailCustom){
			fundDetailCustom = new FundDetailCustom();
		}
		exportExcel(fundDetailCustom,request,response,fundDetailPath,fundDetailSheetName,fundDetailExportName,1);
	}

	/**
	 * 营业部导出
	 * @param queryParam
	 * @param request
	 * @param response
	 * @param session
	 * @throws Exception
	 */
	@RequestMapping("/exportFundDetailBusiness")
	public
	@ResponseBody
	void exportFundDetailBusiness(String queryParam, HttpServletRequest request, HttpServletResponse response,HttpSession session) throws Exception {
		int userId = sessionService.getUserId(session);
		FundDetailCustom fundDetailCustom = JSON.parseObject(queryParam, FundDetailCustom.class);
		if(null == fundDetailCustom){
			fundDetailCustom = new FundDetailCustom();
		}
		fundDetailCustom.setLoginId(userId);
		exportExcel(fundDetailCustom,request,response,fundDetailPath,fundDetailSheetName,fundDetailExportName,2);
	}
	/**
	 * 代理导出
	 * @param queryParam
	 * @param request
	 * @param response
	 * @param session
	 * @throws Exception
	 */
	@RequestMapping("/exportFundDetailAgent")
	public
	@ResponseBody
	void exportFundDetailAgent(String queryParam, HttpServletRequest request, HttpServletResponse response,HttpSession session) throws Exception {
		int userId = sessionService.getUserId(session);
		FundDetailCustom fundDetailCustom = JSON.parseObject(queryParam, FundDetailCustom.class);
		if(null == fundDetailCustom){
			fundDetailCustom = new FundDetailCustom();
		}
		fundDetailCustom.setLoginId(userId);
		exportExcel(fundDetailCustom,request,response,fundDetailPath,fundDetailSheetName,fundDetailExportName,3);
	}
	/**
	 * 导出功能抽取
	 * @param fundDetailCustom
	 * @param request
	 * @param response
	 * @param path
	 * @param sheetName
	 * @param exportName
	 * @param flag
	 * @throws Exception
	 */
	public void exportExcel(FundDetailCustom fundDetailCustom, HttpServletRequest request, HttpServletResponse response,
			String path, String sheetName, String exportName,Integer flag) throws Exception {
		OutputStream os = null;
		Workbook wb = null;    //工作薄
		ExportExcelUtil util = new ExportExcelUtil();
		File file =util.getExcelDemoFile(path,request);
		try {
			List<FundDetailCustom> fundDetailCustoms;
			if(flag==1){//admin和总部导出
				fundDetailCustoms = fundDetailService.queryFundDetailExcel(fundDetailCustom);
			}else if(flag==2){//营业部导出
				fundDetailCustoms = fundDetailService.queryFundDetailBusinessExcel(fundDetailCustom);
			}else{//代理导出
				fundDetailCustoms = fundDetailService.queryFundDetailAgentExcel(fundDetailCustom);
			}
			wb = util.writeFundDetailExcel(file, sheetName,fundDetailCustoms);
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
