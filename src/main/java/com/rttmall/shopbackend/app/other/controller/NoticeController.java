package com.rttmall.shopbackend.app.other.controller;

import com.alibaba.fastjson.JSON;
import com.rttmall.shopbackend.app.customer.pojo.Customer;
import com.rttmall.shopbackend.app.customer.pojo.CustomerCustom;
import com.rttmall.shopbackend.app.customer.service.CustomerService;
import com.rttmall.shopbackend.app.other.service.NoticeService;
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
@RequestMapping("notice")
public class NoticeController {


	@Autowired
	private NoticeService noticeService;

    @Autowired
    private SessionService sessionService;
    
	@RequestMapping(method = RequestMethod.GET)
	public String headCustomer() {
		return "app/customer/customerHeadList";
	}
}
