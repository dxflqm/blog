package org.panzhi.blog.common.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期转换
 *
 *
 * @创建者：yilin @创建时间：2016-10-13 上午10:43:38
 */
public class DateFormatUtil {

	public static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
	public static final String DATE_LINCENG = "yyyy-MM-dd 00:00:00";
	public static final String DATE_WANSHANG = "yyyy-MM-dd 23:59:59";
	public static final String DATE_BIRTHDAY_FORMAT = "yyyy-MM-dd";
	public static final String DATE_FORMAT_MONTH = "yyyy-MM";
	public static final String DATE_BIRTHDAY = "yyyyMMdd";
	public static final String DATE_ALLIANZ_FORMAT = "yyyyMMddHHmmss";
	public static final String DATE_FORMAT_TIMA = "HH:mm:ss";
	public static final String DATE_FORMAT_FORMAT2 = "yyyy/MM/dd 00:00:00";
	public static final String DATE_FORMAT_FORMAT3 = "yyyy/MM/dd 23:59:59";
	
	/**
	 * 时间比较
	 * 开始时间大于结束时间，返回true
	 * 开始时间小于等于结束时间，返回false
	 * @param startTime 开始时间
	 * @param endTime 结束时间
	 * @return boolean
	 */
	public static boolean compareDateTime(Date startTime,Date endTime){
		if(startTime.compareTo(endTime) > 0){
			return true;
		}
		return false;
	}

	/**
	 * 字符串格式化日期
	 * @param str 需要格式化的字符传
	 * @param style 需要格式化的样式
	 * @return 
	 * @throws ParseException
	 */
	public static Date strFormatDate(String str, String style) throws ParseException {
		return new SimpleDateFormat(style).parse(str);
	}

	/**
	 * 日期转字符串
	 *
	 * @param date
	 *            需要转换的日期
	 * @param type
	 *            转换样式
	 * @return
	 */
	public static String dateFormatStr(Date date, String type) {
		return new SimpleDateFormat(type).format(date);
	}

	/**
	 * 日期转字符串
	 *
	 * @param date
	 *            需要转换的日期
	 * @param type
	 *            转换样式
	 * @return
	 * @throws ParseException
	 */
	public static Date dateFormatDate(Date date, String type) throws ParseException {
		return new SimpleDateFormat(DATE_FORMAT).parse(dateFormatStr(date, type));
	}

	/**
	 * 获取后几天的日期
	 *
	 * @param day
	 *            天数
	 * @return
	 */
	public static String afterDay(int day, String type) {
		Calendar now = Calendar.getInstance();
		now.setTime(new Date());
		now.set(Calendar.DATE, now.get(Calendar.DATE) + day);
		Date date = now.getTime();
		return dateFormatStr(date, type);
	}	
	
	/**
	 * 获取后几天的日期
	 * @param day 天数
	 * @return
	 * @throws ParseException 
	 */
	public static String afterDayByDate(String dateSource , int day , String type) throws ParseException {
		Calendar now = Calendar.getInstance();
		now.setTime(strFormatDate(dateSource, DATE_BIRTHDAY_FORMAT));
		now.set(Calendar.DATE, now.get(Calendar.DATE) + day);
		Date date = now.getTime();
		return dateFormatStr(date, type);
	}

	public static String beforeDay(int day, String type) {
		Calendar now = Calendar.getInstance();
		now.setTime(new Date());
		now.set(Calendar.DATE, now.get(Calendar.DATE) - day);
		Date date = now.getTime();
		return dateFormatStr(date, type);
	}

	public static String strFormatDateToStr(String date, String type) throws ParseException{
		return dateFormatStr(strFormatDate(date, type), type);
	}

	/**
	 * 字符串转换成日期
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
	 * 给传入的day添加"addMinute"分钟
	 * 
	 * @param day
	 *            需要操作的日期
	 * @param addMinute
	 *            需要增加的分钟
	 * @param dateType
	 *            时间类型
	 * @return
	 */
	public static String addDateMinut(String day, int addMinute, String dateType) {
		SimpleDateFormat format = new SimpleDateFormat(dateType);
		Date date = null;
		try {
			date = format.parse(day);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		if (date == null)
			return "";
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		// 24小时制
		cal.add(Calendar.MINUTE, addMinute);
		date = cal.getTime();
		cal = null;
		return format.format(date);
	}

	/**
	 *
	 * @Title : findYearMonth
	 * @Type : YearAndMonth
	 * @date : 2014年4月3日 下午10:48:52
	 * @Description : 获取年月
	 * @return
	 */
	public static String findYearMonth() {
		int year, month;
		String date;
		Calendar calendar = Calendar.getInstance();
		year = calendar.get(Calendar.YEAR);
		month = calendar.get(Calendar.MONTH) + 1;
		date = year + "." + (month < 10 ? "0" + month : month);
		return date;
	}

	/**
	 * 两个时间之间相差距离多少天
	 * 
	 * @param date1
	 *            时间参数 1：
	 * @param date2
	 *            时间参数 2：
	 * @return 相差天数
	 */
	public static long getDistanceDaysForDate(Date date1, Date date2, String dateType) throws Exception {
		return getDistanceDaysForStr(dateFormatStr(date1, dateType), dateFormatStr(date2, dateType), dateType);
	}

	/**
	 * 两个字符串之间相差距离多少天
	 * 
	 * @param date1
	 *            时间参数 1：
	 * @param date2
	 *            时间参数 2：
	 * @return 相差天数
	 */
	public static long getDistanceDaysForStr(String date1, String date2, String dateType) throws Exception {
		DateFormat df = new SimpleDateFormat(dateType);
		Date one, two;
		long days = 0;
		try {
			one = df.parse(date1);
			two = df.parse(date2);
			long time1 = one.getTime();
			long time2 = two.getTime();
			long diff;
			if (time1 < time2) {
				diff = time2 - time1;
			} else {
				diff = time1 - time2;
			}
			days = diff / (1000 * 60 * 60 * 24);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return days;
	}

	/**
	 * 两个时间相差距离多少天多少小时多少分多少秒
	 * 
	 * @param str1
	 *            时间参数 1 格式：
	 * @param str2
	 *            时间参数 2 格式：
	 * @return long[] 返回值为：{天, 时, 分, 秒}
	 */
	public static long[] getDistanceTimes(String str1, String str2, String dateType) {
		DateFormat df = new SimpleDateFormat(dateType);
		Date one, two;
		long day = 0, hour = 0, min = 0, sec = 0;
		try {
			one = df.parse(str1);
			two = df.parse(str2);
			long time1 = one.getTime();
			long time2 = two.getTime();
			long diff;
			if (time1 < time2) {
				diff = time2 - time1;
			} else {
				diff = time1 - time2;
			}
			day = diff / (24 * 60 * 60 * 1000);
			hour = (diff / (60 * 60 * 1000) - day * 24);
			min = ((diff / (60 * 1000)) - day * 24 * 60 - hour * 60);
			sec = (diff / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		long[] times = { day, hour, min, sec };
		return times;
	}

	/**
	 * 与当前时间对比
	 * 
	 * @param DATE
	 * @return 0:大于当前时间 1:小于当前时间
	 * @throws ParseException
	 */
	public static int compareDate(String DATE, String dateType) {
		DateFormat df = new SimpleDateFormat(dateType);
		try {
			Date dt1 = df.parse(DATE);
			Date dt2 = df.parse(DateFormatUtil.dateFormatStr(new Date(), dateType));
			if ((dt1.getTime() - dt2.getTime()) > 0) {
				return 1;
			} else {
				return 0;
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return 0;
	}

	/**
	 * 计算两个日期之间相差的天数
	 * 
	 * @param smdate 较小的时间
	 * @param bdate 较大的时间
	 * @return 相差天数
	 * @throws ParseException
	 */
	public static int daysBetween(Date smdate, Date bdate) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		smdate = sdf.parse(sdf.format(smdate));
		bdate = sdf.parse(sdf.format(bdate));
		Calendar cal = Calendar.getInstance();
		cal.setTime(smdate);
		long time1 = cal.getTimeInMillis();
		cal.setTime(bdate);
		long time2 = cal.getTimeInMillis();
		long between_days = (time2 - time1) / (1000 * 3600 * 24);
		return Integer.parseInt(String.valueOf(between_days));
	}

	/**
	 * 计算两个日期(字符串)之间相差的天数
	 * 
	 * @param smdate 较小的时间
	 * @param bdate 较大的时间
	 * @return 相差天数
	 * @throws ParseException
	 */
	public static int daysBetween(String smdate, String bdate) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		cal.setTime(sdf.parse(smdate));
		long time1 = cal.getTimeInMillis();
		cal.setTime(sdf.parse(bdate));
		long time2 = cal.getTimeInMillis();
		long between_days = (time2 - time1) / (1000 * 3600 * 24);
		return Integer.parseInt(String.valueOf(between_days));
	}
	
	 /**
	  * 将date转换成数值
	  * @param date
	  * @return
	  */
	 public static long getTimestampByDate(Date date){
		 if (date != null) {
			return date.getTime();
		}
		 return 0L;
	 }
}
