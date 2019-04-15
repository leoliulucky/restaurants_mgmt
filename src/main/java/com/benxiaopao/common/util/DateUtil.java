package com.benxiaopao.common.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期工具类<br />提供一些常用操作日期方法
 * 
 * @author liupoyang
 * @since 2019-04-12
 */
public class DateUtil {
	
	/**
	 * 获取当前日期时间
	 * @return
	 */
	public static Date now() {
		return new Date();
	}
	
	/**
	 * 日期加年计算
	 * @param date
	 * @param year
	 * @return
	 */
	public static Date addYear(Date date, int year) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.YEAR, year);
		return cal.getTime();
	}

	/**
	 * 日期加月计算
	 * @param date
	 * @param month
	 * @return
	 */
	public static Date addMonth(Date date, int month) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MONTH, month);
		return cal.getTime();
	}

	/**
	 * 日期加天计算
	 * @param date
	 * @param day
	 * @return
	 */
	public static Date addDay(Date date, int day) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DAY_OF_MONTH, day);
		return cal.getTime();
	}

	/**
	 * 日期加周计算
	 * @param date
	 * @param week
	 * @return
	 */
	public static Date addWeek(Date date, int week) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.WEEK_OF_YEAR, week);
		return cal.getTime();
	}

	/**
	 * 日期加小时计算
	 * @param date
	 * @param hour
	 * @return
	 */
	public static Date addHour(Date date, int hour) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.HOUR, hour);
		return cal.getTime();
	}

	/**
	 * 日期加分钟计算
	 * @param date
	 * @param minute
	 * @return
	 */
	public static Date addMinute(Date date, int minute) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MINUTE, minute);
		return cal.getTime();
	}

	/**
	 * 日期加秒计算
	 * @param date
	 * @param second
	 * @return
	 */
	public static Date addSecond(Date date, int second) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.SECOND, second);
		return cal.getTime();
	}
	
	/**
	 * 格式化日期
	 * @param date
	 * @param format
	 * @return
	 */
	public static String formatDate(Date date, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(date);
	}

	/**
	 * 解析成日期
	 * @param dateStr
	 * @param format
	 * @return
	 * @throws Exception
	 */
	public static Date parseDate(String dateStr, String format) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.parse(dateStr);
	}
	
	/**
     * 取两个Date之间的天数差<br />
     * 例：newerDate是6月3日，olderDate是5月31日，则应返回3<br />
     * 一个更极端的列子：newerDate是6月3日 00:01，olderDate是6月2日 23:59，则应返回1，说明相差一天，即便实际上只差2分钟
     * @param date1
     * @param date2
     * @return int 天数
     */
    public static int daysBetween(Date date1, Date date2){
        Calendar cNow = Calendar.getInstance();
        Calendar cReturnDate = Calendar.getInstance();
        cNow.setTime(date1);
        cReturnDate.setTime(date2);
        setTimeToMidnight(cNow);
        setTimeToMidnight(cReturnDate);
        long todayMs = cNow.getTimeInMillis();
        long returnMs = cReturnDate.getTimeInMillis();
        long intervalMs = todayMs - returnMs;
        return millisecondsToDays(intervalMs);
    }

    /**
     * 私有方法
     * @param intervalMs
     * @return
     */
    private static int millisecondsToDays(long intervalMs){
        return (int) (intervalMs / (1000 * 86400));
    }

    /**
     * 私有方法
     * @param calendar
     */
    private static void setTimeToMidnight(Calendar calendar){
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
    }
    
    /**
     * 得到秒。格式：56<br/>
     * @param date 给定的日期
     * @return int 
     */
    public static int secondInt(Date date) {
        return Integer.parseInt(formatDate(date, "ss"));
    }
     
    /**
     * 得到分钟。格式：56<br/>
     * @param date 给定的日期
     * @return int 
     */
    public static int minuteInt(Date date) {
        return Integer.parseInt(formatDate(date, "mm"));
    }
     
    /**
     * 得到小时。格式：23<br/>
     * @param date 给定的日期
     * @return int 
     */
    public static int hourInt(Date date) {
        return Integer.parseInt(formatDate(date, "HH"));
    }
     
    /**
     * 得到日。格式：26<br/>
     * 注意：这里1日返回1,2日返回2。
     * @param date 给定的日期
     * @return int 
     */
    public static int dayInt(Date date) {
        return Integer.parseInt(formatDate(date, "dd"));
    }
     
    /**
     * 得到月。格式：5<br/>
     * 注意：这里1月返回1,2月返回2。
     * @param date 给定的日期
     * @return int 
     */
    public static int monthInt(Date date) {
        return Integer.parseInt(formatDate(date, "MM"));
    }
     
    /**
     * 得到年。格式：2013
     * @param date 给定的日期
     * @return int 
     */
    public static int yearInt(Date date) {
        return Integer.parseInt(formatDate(date, "yyyy"));
    }

}
