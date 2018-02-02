package com.rttmall.shopbackend.app.customer.controller;

import com.alibaba.fastjson.JSON;
import com.rttmall.shopbackend.app.customer.pojo.Customer;
import com.rttmall.shopbackend.app.customer.pojo.CustomerCustom;
import com.rttmall.shopbackend.app.customer.service.CustomerService;
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
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("customer")
public class CustomerController {


	@Value("#{configProperties['customer.excel.path']}")
	private String customerPath;
	@Value("#{configProperties['customer.excel.sheetName']}")
	private String customerSheetName;
	@Value("#{configProperties['customer.excel.exportName']}")
	private String customerExportName;
	@Autowired
	private CustomerService customerService;

    @Autowired
    private SessionService sessionService;
    
	@RequestMapping(method = RequestMethod.GET)
	public String headCustomer() {
		return "app/customer/customerHeadList";
	}
	@RequestMapping(value = "/agentCustomerList")
	public String agentCustomerList() {
		return "app/customer/customerAgentList";
	}
	@RequestMapping(value = "/businessCustomerList")
	public String businessCustomerList() {
		return "app/customer/customerBusinessList";
	}
	@RequestMapping(value = "/queryHeadCustomer")
	public @ResponseBody Map queryHeadCustomer(
			@RequestParam(defaultValue = "") String json, Pagination grid) {
		CustomerCustom customerCustom = JSON.parseObject(json, CustomerCustom.class);
		if(null == customerCustom){
			customerCustom = new CustomerCustom();
		}
		Map map = customerService.queryHeadCustomer(customerCustom,grid);
		return map;
	}
	@RequestMapping(value = "/queryBusinessCustomer")
	public @ResponseBody Map queryBusinessCustomer(
			@RequestParam(defaultValue = "") String json, Pagination grid,HttpSession session) {
		int userId = sessionService.getUserId(session);
		CustomerCustom customerCustom = JSON.parseObject(json, CustomerCustom.class);
		if(null == customerCustom){
			customerCustom = new CustomerCustom();
		}
		customerCustom.setLoginId(userId);
		Map map = customerService.queryBusinessCustomer(customerCustom,grid);
		return map;
	}
	@RequestMapping(value = "/queryAgentCustomer")
	public @ResponseBody Map queryAgentCustomer(
			@RequestParam(defaultValue = "") String json, Pagination grid,HttpSession session) {
		int userId = sessionService.getUserId(session);
		CustomerCustom customerCustom = JSON.parseObject(json, CustomerCustom.class);
		if(null == customerCustom){
			customerCustom = new CustomerCustom();
		}
		customerCustom.setLoginId(userId);
		Map map = customerService.queryAgentCustomer(customerCustom,grid);
		return map;
	}
	
	@RequestMapping("/editCustomer")
	public @ResponseBody void editCustomer(String customerRealName,String customerCardId,String customerId,HttpSession session){
		Customer customer = new Customer();
		int userId = sessionService.getUserId(session);
		customer.setCustomerRealName(customerRealName);
		customer.setCustomerCardId(customerCardId);
		customer.setId(Integer.parseInt(customerId));
		customer.setEditor(userId);
		customer.setEditTime(new Date());
		customerService.editCustomer(customer);
	}

	/**
	 * admin和总部导出
	 * @param queryParam
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/exportCustomer")
	public
	@ResponseBody
	void exportCustomer(String queryParam, HttpServletRequest request, HttpServletResponse response) throws Exception {
		CustomerCustom customerCustom = JSON.parseObject(queryParam, CustomerCustom.class);
		if(null == customerCustom){
			customerCustom = new CustomerCustom();
		}
		exportExcel(customerCustom,request,response,customerPath,customerSheetName,customerExportName,1);
	}

	/**
	 * 营业部导出
	 * @param queryParam
	 * @param request
	 * @param response
	 * @param session
	 * @throws Exception
	 */
	@RequestMapping("/exportCustomerBusiness")
	public
	@ResponseBody
	void exportCustomerBusiness(String queryParam, HttpServletRequest request, HttpServletResponse response,HttpSession session) throws Exception {
		int userId = sessionService.getUserId(session);
		CustomerCustom customerCustom = JSON.parseObject(queryParam, CustomerCustom.class);
		if(null == customerCustom){
			customerCustom = new CustomerCustom();
		}
		customerCustom.setLoginId(userId);
		exportExcel(customerCustom,request,response,customerPath,customerSheetName,customerExportName,2);
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
		CustomerCustom customerCustom = JSON.parseObject(queryParam, CustomerCustom.class);
		if(null == customerCustom){
			customerCustom = new CustomerCustom();
		}
		customerCustom.setLoginId(userId);
		exportExcel(customerCustom,request,response,customerPath,customerSheetName,customerExportName,3);
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
	public void exportExcel(CustomerCustom customerCustom, HttpServletRequest request, HttpServletResponse response,
			String path, String sheetName, String exportName,Integer flag) throws Exception {
		OutputStream os = null;
		Workbook wb = null;    //工作薄
		ExportExcelUtil util = new ExportExcelUtil();
		File file =util.getExcelDemoFile(path,request);
		try {
			List<CustomerCustom> customerCustoms;
			if(flag==1){//admin和总部导出
				customerCustoms = customerService.queryCustomerExcel(customerCustom);
			}else if(flag==2){//营业部导出
				customerCustoms = customerService.queryCustomerBusinessExcel(customerCustom);
			}else{//代理导出
				customerCustoms = customerService.queryCustomerAgentExcel(customerCustom);
			}
			wb = util.writeCustomerExcel(file, sheetName,customerCustoms);
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
	//查询所有代理商
	@RequestMapping("/findAllAgent")
    public
    @ResponseBody
    List findAllAgent() {
        List list = customerService.findAllAgent();
        return list;
    }
	//查询所有运营商
	@RequestMapping("/findAllBusiness")
    public
    @ResponseBody
    List findAllBusiness() {
        List list = customerService.findAllBusiness();
        return list;
    }
}
