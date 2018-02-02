package com.rttmall.shopbackend.app.customer.controller;

import com.alibaba.fastjson.JSON;
import com.rttmall.shopbackend.app.customer.pojo.DrawingApply;
import com.rttmall.shopbackend.app.customer.pojo.DrawingApplyCustom;
import com.rttmall.shopbackend.app.customer.service.DrawingApplyService;
import com.rttmall.shopbackend.app.service.SessionService;
import com.rttmall.shopbackend.enums.DrawingApplyStatus;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("drawing")
public class DrawingApplyController {

	@Value("#{configProperties['drawing.excel.path']}")
	private String drawingPath;
	@Value("#{configProperties['drawing.excel.sheetName']}")
	private String drawingSheetName;
	@Value("#{configProperties['drawing.excel.exportName']}")
	private String drawingExportName;
	@Autowired
	private DrawingApplyService drawingApplyService;
	@Autowired
	private SessionService sessionService;
    
	@RequestMapping(method = RequestMethod.GET)
	public String findDrawing() {
		return "app/drawing/drawingList";
	}

	@RequestMapping(value = "/findDrawingHead")
	public String findDrawingHead() {
		return "app/drawing/drawingHeadList";
	}
	@RequestMapping(value = "/findDrawingBusiness")
	public String findDrawingBusiness() {
		return "app/drawing/drawingBusinessList";
	}

	@RequestMapping(value = "/findDrawingAgent")
	public String finDrawingAgent() {
		return "app/drawing/drawingAgentList";
	}
	@RequestMapping(value = "/addDrawingPage")
	public String addDrawingPage() {
		return "app/drawing/drawingAdd";
	}
	@RequestMapping(value = "/auditDrawingPage")
	public String auditDrawingPage() {
		return "app/drawing/drawingAudit";
	}

	@RequestMapping(value = "/queryDrawing")
	public @ResponseBody Map queryDrawing(
			@RequestParam(defaultValue = "") String json, Pagination grid) {
		DrawingApplyCustom drawingApplyCustom = JSON.parseObject(json, DrawingApplyCustom.class);
		if(null == drawingApplyCustom){
			drawingApplyCustom = new DrawingApplyCustom();
		}
		Map map = drawingApplyService.queryDrawing(drawingApplyCustom,grid);
		return map;
	}
	@RequestMapping(value = "/queryDrawingBusiness")
	public @ResponseBody Map queryDrawingBusiness(
			@RequestParam(defaultValue = "") String json, Pagination grid,HttpSession session) {
		int userId = sessionService.getUserId(session);
		DrawingApplyCustom drawingApplyCustom = JSON.parseObject(json, DrawingApplyCustom.class);
		if(null == drawingApplyCustom){
			drawingApplyCustom = new DrawingApplyCustom();
		}
		drawingApplyCustom.setLoginId(userId);
		Map map = drawingApplyService.queryDrawingBusiness(drawingApplyCustom,grid);
		return map;
	}
	@RequestMapping(value = "/queryDrawingAgent")
	public @ResponseBody Map queryDrawingAgent(
			@RequestParam(defaultValue = "") String json, Pagination grid,HttpSession session) {
		int userId = sessionService.getUserId(session);
		DrawingApplyCustom drawingApplyCustom = JSON.parseObject(json, DrawingApplyCustom.class);
		if(null == drawingApplyCustom){
			drawingApplyCustom = new DrawingApplyCustom();
		}
		drawingApplyCustom.setLoginId(userId);
		Map map = drawingApplyService.queryDrawingAgent(drawingApplyCustom,grid);
		return map;
	}
	
	@RequestMapping(value = "/insertDrawingSubmit", method = RequestMethod.POST)
	public
	@ResponseBody
	void insertDrawingSubmit(DrawingApplyCustom drawingApplyCustom) throws Exception {
		drawingApplyCustom.setStatus(DrawingApplyStatus.HANDING.ordinal());
		drawingApplyCustom.setApplyTime(new Date());
		drawingApplyService.insertDrawing(drawingApplyCustom);
	}
	@RequestMapping(value = "/auditSubmit", method = RequestMethod.POST)
	public
	@ResponseBody
	void auditSubmit(DrawingApply drawingApply,HttpSession session) throws Exception {
		int userId = sessionService.getUserId(session);
		drawingApply.setOperationTime(new Date());
		drawingApply.setOperator(userId);
		drawingApplyService.updateDrawingStatus(drawingApply);
	}
	
	@RequestMapping("/getDrawingApplyStatus")
    public
    @ResponseBody
    List getDrawingApplyStatus() {
        List list = new ArrayList<>();
        for (DrawingApplyStatus e : DrawingApplyStatus.values()) {
        	if(e.getCode()!=0){
                list.add(e);
        	}
        }
        return list;
    }
	/**
	 * admin和总部导出
	 * @param queryParam
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/exportDrawing")
	public
	@ResponseBody
	void exportDrawing(String queryParam, HttpServletRequest request, HttpServletResponse response) throws Exception {
		DrawingApplyCustom drawingCustom = JSON.parseObject(queryParam, DrawingApplyCustom.class);
		if(null == drawingCustom){
			drawingCustom = new DrawingApplyCustom();
		}
		exportExcel(drawingCustom,request,response,drawingPath,drawingSheetName,drawingExportName,1);
	}

	/**
	 * 营业部导出
	 * @param queryParam
	 * @param request
	 * @param response
	 * @param session
	 * @throws Exception
	 */
	@RequestMapping("/exportDrawingBusiness")
	public
	@ResponseBody
	void exportCustomerBusiness(String queryParam, HttpServletRequest request, HttpServletResponse response,HttpSession session) throws Exception {
		int userId = sessionService.getUserId(session);
		DrawingApplyCustom drawingApplyCustom = JSON.parseObject(queryParam, DrawingApplyCustom.class);
		if(null == drawingApplyCustom){
			drawingApplyCustom = new DrawingApplyCustom();
		}
		drawingApplyCustom.setLoginId(userId);
		exportExcel(drawingApplyCustom,request,response,drawingPath,drawingSheetName,drawingExportName,2);
	}
	/**
	 * 代理导出
	 * @param queryParam
	 * @param request
	 * @param response
	 * @param session
	 * @throws Exception
	 */
	@RequestMapping("/exportDrawingAgent")
	public
	@ResponseBody
	void exportCustomerAgent(String queryParam, HttpServletRequest request, HttpServletResponse response,HttpSession session) throws Exception {
		int userId = sessionService.getUserId(session);
		DrawingApplyCustom drawingApplyCustom = JSON.parseObject(queryParam, DrawingApplyCustom.class);
		if(null == drawingApplyCustom){
			drawingApplyCustom = new DrawingApplyCustom();
		}
		drawingApplyCustom.setLoginId(userId);
		exportExcel(drawingApplyCustom,request,response,drawingPath,drawingSheetName,drawingExportName,3);
	}
	/**
	 * 导出功能抽取
	 * @param customerCustom
	 * @param request
	 * @param response
	 * @param path
	 * @param sheetName
	 * @param exportName
	 * @param flag
	 * @throws Exception
	 */
	public void exportExcel(DrawingApplyCustom drawingCustom, HttpServletRequest request, HttpServletResponse response,
			String path, String sheetName, String exportName,Integer flag) throws Exception {
		OutputStream os = null;
		Workbook wb = null;    //工作薄
		ExportExcelUtil util = new ExportExcelUtil();
		File file =util.getExcelDemoFile(path,request);
		try {
			List<DrawingApplyCustom> drawingCustoms;
			if(flag==1){//admin和总部导出
				drawingCustoms = drawingApplyService.queryDrawingExcel(drawingCustom);
			}else if(flag==2){//营业部导出
				drawingCustoms = drawingApplyService.queryDrawingBusinessExcel(drawingCustom);
			}else{//代理导出
				drawingCustoms = drawingApplyService.queryDrawingAgentExcel(drawingCustom);
			}
			wb = util.writeDrawingExcel(file, sheetName,drawingCustoms);
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
