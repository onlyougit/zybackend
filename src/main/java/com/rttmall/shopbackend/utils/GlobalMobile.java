package com.rttmall.shopbackend.utils;

import java.net.URLEncoder;

/**
 * 美联软通国际短信
 * 
 * @author Administrator
 * 
 */
public class GlobalMobile {

	/**
	 * 发送短信
	 * 
	 * @author Administrator
	 * 
	 * @param mobile
	 *            手机号码
	 * @param content
	 *            短信内容
	 * @return
	 * @date Feb 1, 2016 11:33:59 AM
	 */
	public static String sendMobileMsg(String mobile, String content) {
		try {
			String url = "http://m.5c.com.cn/api/send/?";
			String username = "rttmall";
			String password = "rttmall";
			String apikey = "ab0a575cf2543072b1a615e406111d45";
			StringBuffer parame = new StringBuffer();
			parame.append("username=").append(username).append("&");
			parame.append("password=").append(password).append("&");
			parame.append("apikey=").append(apikey).append("&");
			parame.append("mobile=").append(mobile).append("&");
			parame.append("content=").append(URLEncoder.encode(content, "GBK")).append("&");
			// parame.append("content=").append(content).append("&");
			// 定时发送，UNIX时间戳，为空实时发送
			System.out.println("完整路径:" + url + parame.toString());
			String result = SendPost.doPost(url, parame.toString());
			return result;
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 绑定ip
	 * 
	 * @author Administrator
	 * 
	 * @param ip
	 * @return
	 * @date Feb 1, 2016 11:56:42 AM
	 */
	public static String bindIP(String ip) {
		try {
			String url = "http://m.5c.com.cn/api/bind/?";
			String username = "";
			String password = "";
			String apikey = "";
			StringBuffer parame = new StringBuffer();
			parame.append("username=").append(username).append("&");
			parame.append("password=").append(password).append("&");
			parame.append("apikey=").append(apikey).append("&");
			parame.append("ip=").append(ip).append("&");
			parame.append("action=0");// 0 为绑定，1 为查询，2 为清空
			System.out.println("完整路径:" + url + parame.toString());
			String result = SendPost.doPost(url, parame.toString());
			return result;
		} catch (Exception e) {
			return null;
		}
	}

	public static void main(String[] args) {
		System.out.println("begin");
		String result = bindIP("222.76.239.73");
		System.out.println(result);
		System.out.println("end");
	}

}
