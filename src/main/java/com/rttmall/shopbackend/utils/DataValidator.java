package com.rttmall.shopbackend.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DataValidator {
	/**
	 * 验证邮箱
	 * 
	 * @author Lixiaobao
	 *
	 * @param email
	 * @return
	 *
	 * @date 2017年2月20日下午2:26:12
	 */
	public static boolean checkEmail(String email) {
		boolean flag = false;
		try {
			String check = "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
			Pattern regex = Pattern.compile(check);
			Matcher matcher = regex.matcher(email);
			flag = matcher.matches();
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}

	/**
	 * 验证手机号码
	 * 
	 * @author Lixiaobao
	 *
	 * @param mobileNumber
	 * @return
	 *
	 * @date 2017年2月20日下午2:26:05
	 */
	public static boolean checkMobileNumber(String mobileNumber) {
		boolean flag = false;
		try {
			Pattern regex = Pattern
					.compile("^(((13[0-9])|(15([0-3]|[5-9]))|(18[0,5-9]))\\d{8})|(0\\d{2}-\\d{8})|(0\\d{3}-\\d{7})$");
			Matcher matcher = regex.matcher(mobileNumber);
			flag = matcher.matches();
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}

	/**
	 * 验证数字
	 * 
	 * @author Lixiaobao
	 *
	 * @param number
	 * @return
	 *
	 * @date 2017年2月20日下午2:26:05
	 */
	public static boolean checkNumber(String number) {
		boolean flag = false;
		try {
			Pattern regex = Pattern.compile("[0-9]*");
			Matcher matcher = regex.matcher(number);
			flag = matcher.matches();
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}

	/**
	 * 判断是否为空
	 * 
	 * @param map
	 * @return
	 */
	public static boolean isEmpty(Object o) {
		return o != null ? o.toString().trim().length() == 0 : true;
	}

	public static boolean isNotEmpty(Object o) {
		return !isEmpty(o);
	}

	/**
	 * 返回保留两位小数
	 * 
	 * @param math
	 * @return
	 */
	public static BigDecimal getFormatBigDecimal(BigDecimal math) {
		if (math == null) {
			math = new BigDecimal(0);
		}
		DecimalFormat df = new DecimalFormat("#.00");
		return new BigDecimal(df.format(math));
	}

	/**
	 * Double转整数
	 * 
	 * @param math
	 * @return
	 */
	public static Integer getFormatInteger(Double surplusPercent) {
		DecimalFormat df = new DecimalFormat("0");// 格式化小数
		String s = df.format(surplusPercent);
		return Integer.parseInt(s);
	}

	/**
	 * 返回保留两位小数
	 * 
	 * @param math
	 * @return
	 */
	public static BigDecimal getFormatBigDecimal(String math) {
		if (math == null || "".equals(math) || "null".equals(math)) {
			math = "0";
		}
		return getFormatBigDecimal(new BigDecimal(math));
	}

	/**
	 * 返回保留两位小数,千位用逗号隔开
	 * 
	 * @param math
	 * @return
	 */
	public static String getFormatBigDecimalString(BigDecimal math) {
		if (math == null) {
			math = new BigDecimal(0);
		}
		DecimalFormat df = new DecimalFormat(",##0.00");
		return (df.format(math));
	}

	/**
	 * 返回保留两位小数,千位用逗号隔开
	 * 
	 * @param math
	 * @return
	 */
	public static String getFormatBigDecimalString(String math) {
		if (math == null) {
			math = "0";
		}
		return getFormatBigDecimalString(getFormatBigDecimal(math));
	}

	/**
	 * 手机号码格式化
	 * 
	 * @author Lixiaobao
	 *
	 * @param phone
	 * @return 188****1234
	 *
	 * @date 2017年3月13日下午9:26:17
	 */
	public static String phoneNumberFormat(String phone) {
		if (phone == null)
			return null;
		StringBuffer str = new StringBuffer();
		if (phone.length() > 5) {//
			str.append(phone.substring(0, 2)).append("****")
					.append(phone.substring(phone.length() - 3, phone.length()));
		} else if (phone.length() > 3) {//
			str.append(phone.substring(0, 1)).append("***").append(phone.substring(phone.length() - 2, phone.length()));
		} else if (phone.length() == 3) {//
			str.append(phone.substring(0, 1)).append("***").append(phone.substring(phone.length() - 1, phone.length()));
		} else {
			str.append(phone.substring(0, 1)).append("***");
		}
		return str.toString();
	}

	/**
	 * 邮箱号码格式化
	 * 
	 * @author Lixiaobao
	 *
	 * @param email
	 * @return 56****759@qq.com
	 *
	 * @date 2017年3月13日下午9:26:17
	 */
	public static String emailNumberFormat(String email) {
		if (email == null)
			return null;
		String reStr = email.split("@")[0];
		StringBuffer str = new StringBuffer();
		if (reStr.length() > 5) {//
			str.append(reStr.substring(0, 2)).append("****")
					.append(reStr.substring(reStr.length() - 3, reStr.length()));
		} else if (reStr.length() > 3) {//
			str.append(reStr.substring(0, 1)).append("***").append(reStr.substring(reStr.length() - 2, reStr.length()));
		} else if (reStr.length() == 3) {//
			str.append(reStr.substring(0, 1)).append("***").append(reStr.substring(reStr.length() - 1, reStr.length()));
		} else {
			str.append(reStr.substring(0, 1)).append("***");
		}
		return str.append("@").append(email.split("@")[1]).toString();
	}

	/**
	 * 计算折扣
	 * 
	 * @author Lixiaobao
	 *
	 * @param bottomPrice
	 *            折扣价
	 * @param highestPrice
	 *            原价
	 * @return 折扣，四舍五入
	 *
	 * @date 2017年3月15日上午10:32:48
	 */
	public static Integer getDiscount(BigDecimal bottomPrice, BigDecimal highestPrice) {
		BigDecimal discount = new BigDecimal("1")
				.subtract(bottomPrice.divide(highestPrice, 4, BigDecimal.ROUND_HALF_UP))
				.multiply(new BigDecimal("100"));
		return discount.setScale(0, BigDecimal.ROUND_HALF_UP).intValue();
	}

	/**
	 * 获取编码字符集
	 * 
	 * @param request
	 * @param response
	 * @return String
	 */

	public static String getCharacterEncoding(HttpServletRequest request, HttpServletResponse response) {
		if (null == request || null == response) {
			return "gbk";
		}
		String enc = request.getCharacterEncoding();
		if (null == enc || "".equals(enc)) {
			enc = response.getCharacterEncoding();
		}
		if (null == enc || "".equals(enc)) {
			enc = "gbk";
		}
		return enc;
	}

}
