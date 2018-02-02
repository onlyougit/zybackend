/**
 *  copyright(c) 2013 FuJian star-net Information Corp.Ltd
 *
 * @File name:  Base64Util.java
 * @Version : 1.0
 * @Create on:  2013-07-17
 * @Author   :  lujunming
 *
 * @ChangeList
 * ---------------------------------------------------
 * Date         Editor              ChangeReasons
 *
 *
 */ 
package com.rttmall.shopbackend.utils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.Types;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 系统常用工具类
 * 
 */
public class CtUtils {
	
	public static String getDayHour(long time1,long time2,String type) {
		long result = (time1-time2);
		long days = result / (1000 * 60 * 60 * 24);
		long hours = (result % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
		long intervalDay = 14-days;
		if (type.equals("DAY")) {
			if(intervalDay<0){
				intervalDay = 0;
			}
			return intervalDay+"";
		}else if(type.equals("HOUR")){
			return hours+"";
		}else if(type.equals("DAYANDHOUR")){
			return days+"d"+hours+"h";
		}else{
			return "";
		}
	}

	/**
	 * 判断对象是否为空
	 * 
	 * @param pObj
	 * @return
	 */
	public static boolean isEmpty(Object pObj) {
		if (pObj == null) {
			return true;
		}
		if (pObj == "") {
			return true;
		}
		if (pObj instanceof String) {
			if (((String) pObj).trim().length() == 0) {
				return true;
			}
		} else if (pObj instanceof Collection) {
			if (((Collection) pObj).size() == 0) {
				return true;
			}
		} else if ((pObj instanceof Map) && ((Map) pObj).size() == 0) {
			return true;
		}

		return false;
	}

	/**
	 * 判断是否为数字
	 * 
	 * @param s
	 * @return
	 */
	public static boolean isNumber(String s) {

		try {
			Long.parseLong(s);
		} catch (Exception ex) {
			return false;
		}
		return true;
	}

	/**
	 * 计算某个字符在字符串中出现的次数
	 * 
	 * @author sujt
	 */
	public static int count(String s, char c) {
		int count = 0;
		byte[] sBytes = s.getBytes();
		for (int i = 0; i < sBytes.length; i++) {
			if (sBytes[i] == c) {
				count++;
			}
		}
		return count;
	}

	/**
	 * 获取uuid
	 * 
	 * @return
	 */
	public static synchronized String uuid() {
		String uuid = UUID.randomUUID().toString();
		return uuid.replace("-", "");
	}
	
	public static synchronized String getAppId(){
		String CurrentTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		return CurrentTime+getRandom(6);
	}
	
	public static String getRandom(int length){
		String randString = "123456789";// 随机产生的范围字符串
		Random random = new Random();
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < length; i++) {
			String randStr = String.valueOf(randString.charAt(random.nextInt(randString.length())));
			buffer.append(randStr);
		}
		return buffer.toString();
	}
		 


	/**
	 * 获取当前时间
	 * 
	 * @return yyyyMMddHHmmss
	 */
	public static synchronized String getCurrentTime() {
		return new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
	}
	
	/**
	 * 获取当前时间
	 * 
	 * @return yyyyMMddHHmmss
	 */
	public static synchronized String getMillisecondTime() {
		 return new SimpleDateFormat("yyyyMMddhhmmssSSSSSS").format(new Date());
	}
	
	/**
	 * 根据指定格式返回当前时间
	 * 
	 * @param format
	 * @return
	 */
	public static String getCurrentTime(String format) {
		return new SimpleDateFormat(format).format(new Date());
	}

	/**
	 * 将字符从源格式转化为目标格式
	 * 
	 * @param strDate
	 * @param srcDateFormat
	 * @param dstDateFormat
	 * @return
	 */
	public static Date stringToDate(String strDate, String srcDateFormat,
									String dstDateFormat) {
		Date rtDate = null;
		Date tmpDate = (new SimpleDateFormat(srcDateFormat)).parse(strDate,
				new ParsePosition(0));
		String tmpString = null;
		if (tmpDate != null) {
			tmpString = (new SimpleDateFormat(dstDateFormat)).format(tmpDate);
		}
		if (tmpString != null) {
			rtDate = (new SimpleDateFormat(dstDateFormat)).parse(tmpString,
					new ParsePosition(0));
		}
		return rtDate;
	}
	
	
	public static String formatDate(String strDate, String srcDateFormat, String dstDateFormat) throws Exception {
		
		Date tmpDate = new SimpleDateFormat(srcDateFormat).parse(strDate);
		if(tmpDate!=null){
			return new SimpleDateFormat(dstDateFormat).format(tmpDate);
		}
		
		return null;
	}

	/**
	 * 获取对象类型
	 * 
	 * @param obj
	 * @return
	 */
	public static int getType(Object obj) {
		if(obj==null){
			obj = "";
		}
		if (obj instanceof String) {
			return Types.VARCHAR;
		} else if (obj instanceof Integer) {
			return Types.INTEGER;
		} else if (obj instanceof BigDecimal) {
			return Types.BIGINT;
		} else if (obj instanceof Date) {
			return Types.DATE;
		} else if (obj instanceof Double) {
			return Types.DOUBLE;
		} else if (obj instanceof Float) {
			return Types.FLOAT;
		} else if (obj instanceof Long) {
			return Types.BIGINT;
		} else {
			return -999;
		}
	}

	/**
	 * 根据数据数组获取类型数组
	 * 
	 * @param obj
	 * @return
	 */
	public static int[] getObjTypes(Object[] obj) {
		List list = new ArrayList();
		for (int i = 0; i < obj.length; i++) {
			list.add(getType(obj[i]));
		}
		return objectToIntArr(list.toArray());
	}

	/**
	 * 对象数组转化成Int数组
	 * 
	 * @param type
	 * @return
	 */
	public static int[] objectToIntArr(Object[] type) {
		int[] tmp_type = new int[type.length];
		for (int i = 0; i < type.length; i++) {
			tmp_type[i] = (Integer) type[i];
		}
		return tmp_type;
	}

	/**
	 * 将对象的string属性取trim
	 * @param obj
	 */
	public static void trimObject(Object obj) {
		if (obj instanceof Map) {
			Map lvf = (Map) obj;
			Iterator keyIte = lvf.keySet().iterator();
			for (; keyIte.hasNext();) {
				String key = keyIte.next().toString();
				Object value = lvf.get(key);
				if (value instanceof String) {
					value = value.toString().trim();
					lvf.put(key, value);
				}
			}
			return;
		}
		Class className = obj.getClass();
		Field[] fileds = className.getDeclaredFields();
		for (int i = 0; i < fileds.length; i++) {
			Field filed = fileds[i];
			if (filed.getType() == String.class) {
				String fieldName = filed.getName();
				try {
					Method getMethod = className.getMethod("get"
							+ fieldName.substring(0, 1).toUpperCase()
							+ fieldName.substring(1));
					Method setMethod = className.getMethod("set"
							+ fieldName.substring(0, 1).toUpperCase()
							+ fieldName.substring(1), String.class);
					Object value = getMethod.invoke(obj);
					if (value != null) {
						setMethod.invoke(obj, value.toString().trim());
					}
				} catch (SecurityException e) {
				} catch (NoSuchMethodException e) {
				} catch (IllegalArgumentException e) {
				} catch (IllegalAccessException e) {
				} catch (InvocationTargetException e) {
				}
			}
		}
	}
	
	/**
	 * 获取对象的数据，以|拼成字符串
	 * 
	 * @param obj
	 * @return
	 */
	public static String getObjct(Object obj) {
		String result = "";
		Class className = obj.getClass();
		Field[] fileds = className.getDeclaredFields();
		for (int i = 0; i < fileds.length; i++) {
			Field filed = fileds[i];
			String fieldName = filed.getName();
			try {
				Method method = className.getMethod("get"
						+ fieldName.substring(0, 1).toUpperCase()
						+ fieldName.substring(1));
				if (method != null) {
					Object filedValue = method.invoke(obj);
					if (filedValue instanceof String) {
						result += fieldName + ":"
								+ String.valueOf(filedValue).trim() + "|";
					} else {
						result += fieldName + ":" + filedValue + "|";
					}

				}
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				continue;
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (result != null && result.length() != 0) {
			result = result.length() > 1024 ? result.substring(0, 1024)
					: result;
		}
		return result;
	}

	/**
	 * 比较2个对象的差异，以|拼成字符串,最大长度1024
	 * @param old_obj
	 * @param new_obj
	 * @return
	 */
	public static String getCompareObject(Object old_obj, Object new_obj) {
		// 比较结果
		String result = "";
		Class objClass = new_obj.getClass();
		// 如果两个对象不同类,不比较,返回
		if (objClass != old_obj.getClass()) {
			return null;
		}
		// 取声明的属性列表
		Field[] fileds = objClass.getDeclaredFields();
		for (int i = 0; i < fileds.length; i++) {
			Field filed = fileds[i];
			String fieldName = filed.getName();
			try {
				// 取属性的get方法
				Method method = objClass.getMethod("get"
						+ fieldName.substring(0, 1).toUpperCase()
						+ fieldName.substring(1));
				if (method != null) {
					// 取新旧属性值
					Object oldValue = method.invoke(old_obj);
					Object newValue = method.invoke(new_obj);
					if (oldValue != newValue
							&& ((oldValue == null && newValue != null) || !oldValue
									.equals(newValue))) {
						// 如果是时间
						if (oldValue instanceof Date
								|| newValue instanceof Date) {
							SimpleDateFormat sdf = new SimpleDateFormat(
									"yyyy-MM-dd");
							String oldValueStr = oldValue == null ? "null"
									: sdf.format((Date) oldValue);
							String newValueStr = newValue == null ? "null"
									: sdf.format((Date) newValue);
							result += fieldName + ":" + oldValueStr + "-"
									+ newValueStr + "|";
						} else if (oldValue instanceof String
								|| newValue instanceof String) {
							if (!String.valueOf(oldValue).trim().equals(
									String.valueOf(newValue).trim())) {
								result += fieldName + ":"
										+ String.valueOf(oldValue).trim() + "-"
										+ String.valueOf(newValue).trim() + "|";
							}
						} else {
							result += fieldName + ":" + oldValue + "-"
									+ newValue + "|";
						}
					}
				}
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				continue;
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (result != null && result.length() != 0) {
			result = result.length() > 1024 ? result.substring(0, 1024)
					: result;
		}
		return result;
	}

	/**
	 * object转整型
	 * @param obj
	 * @param defValue
	 * @return
	 */
	public static int getInt(Object obj, int defValue) {
		
		try {
			if (obj instanceof Integer) {
				defValue = (Integer) obj;
			}else{
				defValue = Integer.valueOf(obj+"");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return defValue;
	}
	
	/**
	 * object转字符串
	 * @param obj
	 * @return
	 */
	public static String getString(Object obj){
		String defValue= "";
		try {
			if (obj==null) {
				defValue = "";
			}else{
				defValue = obj.toString();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return defValue;
	}
	
	/**
	 * 比较版本号的大小,前者大则返回一个正数,后者大返回一个负数,相等则返回0
	 * @author tengsong
	 * @param version1
	 * @param version2
	 * @return
	 */
	public static int compareVersion(String version1, String version2) throws Exception {
	    if (version1 == null || version2 == null) {
	    	throw new Exception();
	    }
	    String[] versionArray1 = version1.split("\\.");//注意此处为正则匹配，不能用.；
	    String[] versionArray2 = version2.split("\\.");
	    int idx = 0;
	    int minLength = Math.min(versionArray1.length, versionArray2.length);//取最小长度值
	    int diff = 0;
	    while (idx < minLength
	            && (diff = versionArray1[idx].length() - versionArray2[idx].length()) == 0//先比较长度
	            && (diff = versionArray1[idx].compareTo(versionArray2[idx])) == 0) {//再比较字符
	        ++idx;
	    }
	    //如果已经分出大小，则直接返回，如果未分出大小，则再比较位数，有子版本的为大；
	    diff = (diff != 0) ? diff : versionArray1.length - versionArray2.length;
	    return diff;
	}

}
