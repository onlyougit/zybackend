package com.rttmall.shopbackend.app.other.controller;

import com.alibaba.fastjson.JSON;
import com.rttmall.shopbackend.app.customer.pojo.Customer;
import com.rttmall.shopbackend.app.customer.pojo.CustomerCustom;
import com.rttmall.shopbackend.app.customer.service.CustomerService;
import com.rttmall.shopbackend.app.other.pojo.Notice;
import com.rttmall.shopbackend.app.other.pojo.NoticeCustom;
import com.rttmall.shopbackend.app.other.service.NoticeService;
import com.rttmall.shopbackend.app.service.SessionService;
import com.rttmall.shopbackend.enums.BankCardStatus;
import com.rttmall.shopbackend.enums.NoticeStatus;
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
import java.util.Arrays;
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
	public String noticePage() {
		return "app/notice/noticeList";
	}

	@RequestMapping(value = "/noticeAddPage")
	public String noticeAddPage() {
		return "app/notice/noticeAdd";
	}


	@RequestMapping(value = "/queryNotice")
	public @ResponseBody Map queryNotice(String title, Pagination grid) {
		NoticeCustom noticeCustom =  new NoticeCustom();
		noticeCustom.setTitle(title);
		Map map = noticeService.queryNotice(noticeCustom,grid);
		return map;
	}

	/**
	 * 添加公告
	 * @param notice
	 * @param session
	 * @throws Exception
	 */
	@RequestMapping(value = "/insertNoticeSubmit", method = RequestMethod.POST)
	public
	@ResponseBody
	void insertNoticeSubmit(Notice notice,HttpSession session) throws Exception {
		int userId = sessionService.getUserId(session);
		notice.setCreateTime(new Date());
		notice.setStatus(NoticeStatus.VALID.ordinal());
		notice.setUserId(userId);
		noticeService.insertNoticeSubmit(notice);
	}

	/**
	 * 批量删除公告
	 * @param ids
	 */
	@RequestMapping("/batchDeleteNotice")
	public
	@ResponseBody
	void batchDeleteNotice(String ids) {
		List idList = Arrays.asList(ids.split(","));
		noticeService.batchDeleteNotice(idList);
	}
}
