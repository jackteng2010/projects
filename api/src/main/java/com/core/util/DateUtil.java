package com.core.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
	
	private static final String format = "yyyy/MM/dd HH:mm:ss";
	
	public static Long getLongValueFromDateString(String ds, String formatString){
		SimpleDateFormat sdf = new SimpleDateFormat(formatString);
		Date d;
		try {
			d = sdf.parse(ds);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			d = new Date();
		}
		return d.getTime();
	}
	
	public static String getDateString(Date date){
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(date);
	}
	
	public static String toStr(Date date){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(date);
	}
	
	public static Date getDateFull(String date) throws ParseException{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if(date == null){
			return null;
		}
		return sdf.parse(date);
	}
	
	public static Date getDate(String date) throws ParseException{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		return sdf.parse(date);
	}
	
	public static Date getDate(String date,String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		try {
			return sdf.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static Integer getSimpleDay(Long times) {
		Date date = new Date();
		if(times != null) {
			date = new Date(times);
		}
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		String str = format.format(date);
		return Integer.valueOf(str);
	}
	
	public static long getDateByNum(Integer times) {
		long lo = 0;
		try {
			if(times != null){
				Date date = new SimpleDateFormat("yyyyMMdd").parse(String.valueOf(times));
				lo = date.getTime();
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return lo;
	}
	
	public static Integer getNextDay(Integer day){
		Integer nextDay = 0;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			Date date = sdf.parse(String.valueOf(day));
			Calendar cal=Calendar.getInstance();
			cal.setTime(date);
			cal.add(Calendar.DAY_OF_YEAR, 1);
			nextDay = Integer.valueOf(sdf.format(cal.getTime()));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return nextDay;
	}
	
	public static Date getDate(Long times){
		return new Date(times);
	}
	
	public static String getDateStringByLong(Long times){
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
	    return sdf.format(new Date(times));
	}
	
	public static Date getJustYearNow(){
	    Date now = new Date();
	    Date year = null;
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	    String nowString = sdf.format(now);
	    try {
            year = sdf.parse(nowString);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
	    return year;
	}
	
    public static Date getDateByYearOfDay(int day){
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_YEAR, day);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		return cal.getTime();
	}

    public static String getYYYYMMDD(){
    	return getYYYYMMDD(new Date());
    }
    public static String getYYYYMMDD(Date date){
    	if(date == null){
    		date = new Date();
    	}
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	    return sdf.format(date);
    }
    
	public static String getStringByLong(Long times){
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    return sdf.format(new Date(times));
	}
	
    public static String getYYYYMMDDHHMMSS(Date date){
    	if(date == null){
    		date = new Date();
    	}
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
	    return sdf.format(date);
    }
	

	public static void main(String[] args) throws ParseException{
//	    getJustYearNow();
//	    System.out.println(getDate("2012/12/21"));
//		System.out.println(getDateString(new Date()));
//		System.out.println(getDate("1990/12/12").getTime()); //660931200000
	}
}
