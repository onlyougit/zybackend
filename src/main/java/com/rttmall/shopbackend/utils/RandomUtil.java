package com.rttmall.shopbackend.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class RandomUtil {

	/**
	 * 获取随机验证码
	 * 
	 * @author Lixiaobao
	 *
	 * @param length
	 * @return
	 *
	 * @date 2017年2月20日下午3:35:11
	 */
	public static String getRandom(Integer length) {
		if (length == 6) {
			return getRandom6();
		} else if (length == 12) {
			return getRandom12();
		}
		return getRandom6();
	}

	/**
	 * 生成随机字符串
	 * 
	 * @author Lixiaobao
	 *
	 * @param length
	 * @return
	 *
	 * @date 2017年3月7日下午8:56:52
	 */
	public static String getRandomString(int length) { // length表示生成字符串的长度
		String base = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			int number = random.nextInt(base.length());
			sb.append(base.charAt(number));
		}
		return sb.toString();
	}

	static String getRandom6() {
		Random random = new Random();
		return random.nextInt(899999) + 100000 + "";
	}

	static String getRandom12() {
		SimpleDateFormat sf = new SimpleDateFormat("yyMMdd");
		return sf.format(new Date()) + getRandom6();
	}
}
