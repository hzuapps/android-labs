package com.example.intelligent_restranant_boss.budget;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateUtil {

	/** 获取当前时间 */
	public static String getCurrentDateAndTime() {
		Date now = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		String date = dateFormat.format(now);

		
		return date;
	}
	
	/** 获取当前时间 */
	public static String getCurrentDate() {
		Date now = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"yyyy年MM月");
		String date = dateFormat.format(now);
		
		
		return date;
	}
	/** 获取当前时间 */
	public static String getCurrentTime() {
		Date now = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"HH:mm:ss");
		String date = dateFormat.format(now);
		
		
		return date;
	}
	
	/**获取距离今天i天的日期*/
	public static String getDate(int i) {
		Date date=new Date();//取时间
		 Calendar calendar = new GregorianCalendar();
		 calendar.setTime(date);
		 calendar.add(calendar.DATE,i);//把日期往后增加一天.整数往后推,负数往前移动
		 date=calendar.getTime(); //这个时间就是日期往后推一天的结果 
		 SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月");
		 String dateString = formatter.format(date);
		 
		return dateString;

	}
	/**获取距离今天i天的日期*/
	public static String getMonth(int i) {
		Date date=new Date();//取时间
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.add(calendar.MONTH,i);//把日期往后增加一天.整数往后推,负数往前移动
		date=calendar.getTime(); //这个时间就是日期往后推一天的结果 
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月");
		String dateString = formatter.format(date);
		
		return dateString;
		
	}
	
	/**判断某个时间是否在某个时间段内*/
	public static boolean isBetweenTheScheduledTime(String dateInput,String startDate,String endDate){
		
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		Date start;
		try {
			start = sdf.parse(startDate);
			Date end = sdf.parse(endDate);
			Date input = sdf.parse(dateInput);// test >= start &&
															// test <=
			if((input.equals(start) || input.after(start))
					&& (input.equals(end) || input.before(end))){
				return true;
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
		
	}
	
	/**获取当前时间*/
	public static Date getCurDate(){
		return new Date(System.currentTimeMillis());
	}

	/**获取时间差*/
	public static long getTheTimeDelay(Date curDate,Date endDate){
		return endDate.getTime()-curDate.getTime();
	}
	
	/** 获取传入时间是星期几 */
	public static String getWeekDay(String dateInput) {

		final String dayNames[] = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五",
				"星期六" };

		SimpleDateFormat sdfInput = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		Calendar calendar = Calendar.getInstance();
		Date date = new Date();

		try {
			date = sdfInput.parse(dateInput);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		calendar.setTime(date);
		int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
		if (dayOfWeek < 0)
			dayOfWeek = 0;
		return dayNames[dayOfWeek];
	}
	

}
