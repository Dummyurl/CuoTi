package com.chinastis.cuoti.util;

import java.util.Calendar;

/********************************************
 * 文件名称: DateUtil.java
 * 系统名称:
 * 模块名称: 工具
 * 软件版权: 苏州伽利工程技术有限公司
 * 功能说明: 日期工具
 * 系统版本: v1.0
 * 开发人员: 孟祥龙
 * 开发时间: 2017/8/23 上午11:32
 * 审核人员:
 * 相关文档:
 * 修改记录:
 *********************************************/

public class DateUtil {
	private static int year;
	private static int month;
	private static int day;
	private static int hour;
	private static int minute;
	private static int second;
	private static int milliSecond;

	private static void init() {
		Calendar cal = Calendar.getInstance();// 使用日历类
		year = cal.get(Calendar.YEAR);// 得到年
		month = cal.get(Calendar.MONTH) + 1;// 得到月，因为从0开始的，所以要加1
		day = cal.get(Calendar.DAY_OF_MONTH);// 得到天
		hour = cal.get(Calendar.HOUR_OF_DAY);// 得到小时
		minute = cal.get(Calendar.MINUTE);// 得到分钟
		second = cal.get(Calendar.SECOND);// 得到秒
		milliSecond = cal.get(Calendar.MILLISECOND);//微妙
	}

	/**
	 * 获取当前时间，格式为：2017-8-19
	 */
	public static String getCurrentDate() {
		init();
		return year + "-" + month + "-" + day;
	}

	/**
	 * 获取当前时间，格式为：2017-08-19
	 */
	public static String getCurrentTime() {
		init();
		return year + "-" + addZero(month) + "-" + addZero(day);
	}

	/**
	 * 获取当前时间，格式为：2017-08-19 13:30:20
	 */
	public static String getCurrentTimeComplete() {
		init();
		return year + "-" + addZero(month) + "-" + addZero(day) +getCurrentClockTime();
	}

	/**
	 * 获取当前时间，格式为：20170819133020
	 */
	public static String getCurrentTime2() {
		init();
		return "" + year + addZero(month) + addZero(day)
				+ addZero(hour) + addZero(minute)
				+ addZero(second);
//				+addZero(milliSecond);
	}

	/**
	 * 获取当前时间，格式为：13:30:20
	 */
	public static String getCurrentClockTime() {
		init();

		return " "+addZero(hour)+":"+addZero(minute)+":"+addZero(second);
	}

	private static String addZero(int value) {
		if (value < 10) {
			return "0" + value;
		}
		return value + "";
	}
}
