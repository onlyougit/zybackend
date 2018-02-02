package com.rttmall.shopbackend.utils;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;


/**
 * 日期处理类
 * 
 * @author Administrator
 *
 */
public class DateUtil {
	public static String getExpiryDate(Date date1,Date date2){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(date1)+"--"+sdf.format(date2);
	}
	/**
	 * 获得当前系统日期
	 *
	 *
	 * @author Huangwenjie
	 *
	 * @return
	 * @return Time
	 *
	 * @date 2017年3月7日 下午4:05:54
	 */
	public static Time getNowTime() {
		Calendar c = new GregorianCalendar();
		Time now = Time
				.valueOf(c.get(Calendar.HOUR_OF_DAY) + ":" + c.get(Calendar.MINUTE) + ":" + c.get(Calendar.SECOND));
		return now;
	}

	/**
	 * 是否在合法时间内
	 * 
	 * @param beginTime
	 * @param endTime
	 * @return
	 */
	public static boolean isTimeLimit(Time beginTime, Time endTime) {
		Time now = getNowTime();

		if (beginTime.before(now) && endTime.after(now)) {
			return true;
		}
		return false;
	}

	/**
	 * 判断当前Time是否在两个日期的时间之内
	 *
	 *
	 * @author Huangwenjie
	 *
	 * @param beginDate
	 * @param endDate
	 * @return
	 * @return boolean true 是 false 不是
	 *
	 * @date 2017年3月7日 下午4:18:40
	 */
	public static boolean isDateTimeLimit(Date beginDate, Date endDate) {

		SimpleDateFormat sf = new SimpleDateFormat("HH:mm:ss");

		Time beginTime = Time.valueOf(sf.format(beginDate));
		Time endTime = Time.valueOf(sf.format(endDate));

		return isTimeLimit(beginTime, endTime);

	}

	/**
	 * 判断当前时间是否是在开始日期和结束日期之间
	 *
	 *
	 * @author Huangwenjie
	 *
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @return
	 * @return boolean 是：true 不是：false
	 *
	 * @date 2017年2月25日 下午2:39:30
	 */
	public static boolean isDateLimit(Date beginDate, Date endDate) {

		Date currentDate = new Date();
		if (currentDate.compareTo(beginDate) == 1 && currentDate.compareTo(endDate) == -1) {
			return true;
		}

		return false;

	}

	/**
	 * 获得当前日期的String日期 yyyy.MM.dd
	 *
	 *
	 * @author Huangwenjie
	 *
	 * @return
	 * @return String
	 *
	 * @date 2017年2月27日 下午2:11:20
	 */
	public static String getDateYyyyMMDd() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
		Date date = new Date();
		return sdf.format(date);
	}

	/**
	 * 获得传入的日期的String日期 yyyy.MM.dd
	 *
	 *
	 * @author Huangwenjie
	 *
	 * @param date
	 * @return
	 * @return String
	 *
	 * @date 2017年2月27日 下午2:11:53
	 */
	public static String DateToDateYyyyMMDd(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
		return sdf.format(date);
	}

	/**
	 * 获得传入的日期的String日期yyyy.MM.dd HH:mm:ss
	 *
	 *
	 * @author Huangwenjie
	 *
	 * @param date
	 * @return
	 * @return String
	 *
	 * @date 2017年2月27日 下午5:07:31
	 */
	public static String dateToStrYyyyMMDdHHMmSs(Date date) {
		DateFormat sdf = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
		return sdf.format(date);
	}

	/**
	 * 获得传入的日期的String日期yyyy/MM/dd HH:mm:ss
	 *
	 *
	 * @author Huangwenjie
	 *
	 * @param date
	 * @return
	 * @return String
	 *
	 * @date 2017年2月27日 下午5:07:31
	 */
	public static String dateToStrYyyyMMDdHHMmSs2(Date date) {
		DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		return sdf.format(date);
	}

	/**
	 * 获得传入的日期的String日期yyyy.MM.dd
	 *
	 *
	 * @author Huangwenjie
	 *
	 * @param date
	 * @return
	 * @return String
	 *
	 * @date 2017年2月27日 下午5:07:31
	 */
	public static String dateToStrYyyyMMDd(Date date) {
		DateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
		return sdf.format(date);
	}


	/**
	 * 判断是否超过两小时
	 * 
	 * @author Lixiaobao
	 *
	 * @param time
	 * @return 超过返回true，未超过返回false
	 *
	 * @date 2017年2月25日下午9:42:58
	 */
	public static boolean timeOut(Date time) {
		Date now = new Date();
		long diff = now.getTime() - time.getTime();
		long f = diff / (1000 * 60 * 60);
		if (f > 2) {
			return true;
		}
		return false;
	}

	/**
	 * 显示英文日期:入参类型：日期
	 *
	 *
	 * @author Huangwenjie
	 *
	 * @param date
	 * @return
	 * @return String
	 *
	 * @date 2017年2月28日 下午5:19:06
	 */
	public static String getEnglishDate(Date date) {

		SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy", Locale.ENGLISH);

		return sdf.format(date);

	}

	/**
	 * 显示英文日期:入参类型：字符串
	 *
	 *
	 * @author Huangwenjie
	 *
	 * @param str
	 * @return
	 * @return String
	 *
	 * @date 2017年3月2日 上午9:49:59
	 */
	public static String getEnglishDate(String str) {
		Date date = StrToDate(str);
		return getEnglishDate(date);
	}

	/**
	 * 字符串转换成日期yyyy-MM-dd HH:mm:ss
	 * 
	 * @param str
	 * @return date
	 */
	public static Date StrToDate(String str) {

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = null;
		try {
			date = format.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * 字符串转换成日期yyyyMMddHHmmss
	 * 
	 * @param str
	 * @return date
	 */
	public static Date StrToDate2(String str) {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		Date date = null;
		try {
			date = format.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * 
	 * 根date获得字符串类型的时分秒。如：10:17:12
	 *
	 * @author Huangwenjie
	 *
	 * @param date
	 * @return
	 * @return String
	 *
	 * @date 2017年3月7日 上午10:19:28
	 */
	public static String getStrTime(Date date) {
		DateFormat df = DateFormat.getTimeInstance();// 可以精确到时分秒
		return df.format(date);
	}

	/**
	 * 根据时分秒的时间得到时分
	 *
	 *
	 * @author Huangwenjie
	 *
	 * @param hourMinuteSecond
	 *            10:00:00
	 * @return
	 * @return 10:00
	 *
	 * @date 2017年3月7日 下午2:16:35
	 */
	public static String getHourMinute(String hourMinuteSecond) {

		String[] times = hourMinuteSecond.split(":");

		String hourMinute = "";

		if (times.length == 3) {
			hourMinute = times[0] + ":" + times[1];
		} else {
			System.out.println("时间格式不正确");
		}

		return hourMinute;

	}

	// /**
	// * 判断结束时间是否大于当前时间
	// *
	// * @author Huangwenjie
	// *
	// * @param endDate
	// * @return
	// * @return boolean
	// *
	// * @date 2017年3月7日 下午4:03:34
	// */
	// public static boolean endTimeIfBigNowTime(Date endDate){
	//
	// Time endTime = new Time(endDate.getTime());
	// Time now = getNowTime();
	// if (endTime.getTime() - now.getTime() > 1) {
	// return false;
	// }else{
	// return true;
	// }
	//
	// }
	/**
	 * 
	 * 判断结束日期时间是否大于当前日期时间
	 *
	 * @author Huangwenjie
	 *
	 * @param endDate
	 * @return
	 * @return boolean
	 *
	 * @date 2017年3月23日 下午2:49:13
	 */
	public static boolean endDateBigNowDate(Date endDate) {
		if (endDate.compareTo(new Date()) == -1) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * 将日期转化为特定的字符串形式，当格式不对的时候，会抛出异常
	 * 
	 * @param date
	 *            可为空，需要转化字符串的日期
	 * @param pattern
	 *            可为空，转化字符串的形式：如：yyyy-MM-dd,默认为：yyyy-MM-dd HH:mm:ss
	 * @return 返回日期转化后的字符串
	 */
	public static String format(Object date, String pattern) {
		if (date == null) {
			return null;
		}
		SimpleDateFormat formatter;
		try {
			if (DataValidator.isEmpty(pattern)) {
				pattern = "yyyy-MM-dd HH:mm:ss";
			}
			formatter = new SimpleDateFormat(pattern);
		} catch (RuntimeException e) {
			throw new RuntimeException("字符串格式错误！");
		}
		try {
			String dateString = formatter.format(date);
			return dateString;
		} catch (RuntimeException e) {
			throw new RuntimeException("要转化的对象必须是日期型对象！");
		}
	}

	/**
	 * 日期加减
	 * 
	 * @author Lixiaobao
	 *
	 * @param date
	 * @param day
	 *            增加天数,负数往前移动
	 * @return
	 *
	 * @date 2017年3月23日下午6:02:44
	 */
	public static Date addDay(Date date, int day) {
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.add(Calendar.DATE, day);// 把日期往后增加一天.整数往后推,负数往前移动
		return calendar.getTime(); // 这个时间就是日期往后推一天的结果
	}

	/**
	 * 时间戳转Date
	 * 
	 * @author huangwenjie
	 *
	 * @return
	 *
	 * @date 2016-8-23 下午7:12:20
	 */
	public static Date timestamptoDate(String value) {
		// 时间戳转Date
		Timestamp tt = new Timestamp(System.currentTimeMillis());
		tt = Timestamp.valueOf(value);
		Date date = tt;
		return date;
	}

	/**
	 * 获得一周之前的日期
	 *
	 *
	 * @author Huangwenjie
	 *
	 * @return
	 * @return Date
	 *
	 * @date 2017年3月28日 下午6:01:21
	 */
	public static Date getAWeekBeforeDate() {
		// 当前日期往前推七天
		Calendar c = Calendar.getInstance();
		c.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
		c.add(Calendar.DAY_OF_MONTH, -7);
		Date endDate = c.getTime();
		return endDate;
	}

	/**
	 * 获取当前时间 格式为yyyyMMdd 的字符串
	 * 
	 * @return String 当前时间yyyyMMdd
	 */
	public static String getStrDateShort() {
		return format(new Date(), "yyyyMMdd");
	}

	/**
	 * 返回秒数级的北京时间
	 * @author Administrator
	 *
	 * @return
	 * @date Jan 13, 2016 2:43:14 PM
	 */
	public static String getBeiJingTime(){
		TimeZone tz = TimeZone.getTimeZone("GMT+8:00");
		Calendar calendar = Calendar.getInstance(tz);
		String time = calendar.getTime().getTime()+"";
		return time.substring(0, 10);
	}
}
