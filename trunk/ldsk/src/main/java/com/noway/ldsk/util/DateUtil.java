package com.noway.ldsk.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Hashtable;
import java.util.Map;

import org.apache.log4j.Logger;



public class DateUtil
{
	private static Map<String, SimpleDateFormat> sdfCache = new Hashtable<String, SimpleDateFormat>();

	public static final long ONE_DAY = 24 * 60 * 60 * 1000;
	
	private static final Logger logger = Logger.getLogger(DateUtil.class);
	
	/**
	 * Convert String to date with a format
	 * @param date Input String to convert
	 * @param pattern pattern
	 * @return Date
	 */
	public static Date parse(String date, String pattern)
	{
		try
		{
			final SimpleDateFormat sdf = getSimpleDateFormat(pattern);
			// since SimpleDateFormat object isn't thread-safe, we need to synchronize
			// it.
			synchronized (sdf)
			{
				return sdf.parse(date);
			}
		}
		catch (ParseException e)
		{
//			logger.warn("Error parsing '" + date + "' using pattern '" + pattern + "'");
			return null;
		}
	}

	/**
	 * Check the date is correct 
	 * @param date Input String to check
	 * @param pattern pattern
	 * @return If check correct, return true; otherwise, return false
	 * flag: false-not right date format  true-right date format
	 */
	public static boolean checkIsDate(String date,String pattern){	
	    boolean flag = false;
	    if(date == null || "".equals(date)){
	        return flag;
	    }else{        	    
	    	final SimpleDateFormat sdf = getSimpleDateFormat(pattern);		    
		    try {
			    synchronized(sdf){		       
			    	final Date tempDate = sdf.parse(date);	    
	                final String tempStr = sdf.format(tempDate);
		                if(tempStr.equalsIgnoreCase(date)){
		                	flag = true;
		                }
			    }
		    } catch (ParseException e) {       
			logger.error("Error parsing '" + date + "' using pattern '" + pattern + "'");
		        return flag;
	        }
		    return flag;
		}
	}

	/**
	 * Convert date to String with a format
	 * @param date Input date to convert
	 * @param pattern pattern
	 * @return String
	 */
	public static String format(Date date, String pattern)
	{
		return date == null ? "" : getSimpleDateFormat(pattern).format(date);
	}

	/**
	 * Convert date to String
	 * @param date Input date to convert
	 * @return String 
	 */
	public static String formatDOB(Date date)
	{
		return format(date, "dd/MM/yyyy");
	}

	/**
	 * get today
	 * @return Returns the today
	 */
	public static Date getToday()
	{
		final Date date = new Date();
		final String s = format(date, "dd/MM/yyyy");
		return parse(s, "dd/MM/yyyy");
	}
	
	/**
	 * 
	 * Get current date without seconds
	 * 
	 * @return date without seconds
	 */
	public static Date getNewDateNoSec(){
		final Date date = new Date();
		final String sDate = format(date, "dd/MM/yyyy HH:mm");
		return parse(sDate, "dd/MM/yyyy HH:mm");
	}
	
	/**
	 * Constructs a SimpleDateFormat using the given pattern and
	 * the default date format symbols for the default locale.
	 * @param pattern pattern
	 * @return SimpleDateFormat
	 */
	private static SimpleDateFormat getSimpleDateFormat(String pattern)
	{
		SimpleDateFormat sdf = (SimpleDateFormat) sdfCache.get(pattern);
		if (sdf == null)
		{
			sdf = new SimpleDateFormat();
			sdf.applyPattern(pattern);
			sdf.setLenient(false);
			sdfCache.put(pattern, sdf);
		}
		return sdf;
	}

	/**
	 * Start Modification For BCP By Angelo on 21 Nov 2007
	 * Date Comparasion Method
	 * @param aDate
	 * @param curDate
	 * @param bDate
	 * @return If the method is correct, return true; otherwise, return false
	 */
	public static boolean compareDate(Date aDate, Date curDate, Date bDate) {
/*
  	    long aTimeInMills = aDate.getTime();
	    long bTimeInMills = bDate.getTime();
	    long curTimeInMills = curDate.getTime();

	    if (curTimeInMills >= aTimeInMills && 
	            curTimeInMills < bTimeInMills) {
	        return true;
	    } else {
	        return false;
	    }
*/
	    if ((curDate.after(aDate) || curDate.equals(aDate)) && curDate.before(bDate)) {
	    	logger.info("Typhoon notification message is active");
	        return true;
	    } else {
	        return false;
	    }
	}

	/**
	 * Date Comparasion Method
	 * @param aDate
	 * @param curDate
	 * @param bDate
	 * @return boolean
	 * @return If the method is correct, return true; otherwise, return false
	 */
	public static boolean compareDate(String aDateStr,String bDateStr,String pattern){
	    
		final Date aDate = parse(aDateStr,pattern);
		final Date bDate = parse(bDateStr,pattern);
	    if((aDate != null && bDate != null) && (aDate.before(bDate) || aDate.equals(bDate))){
		        return true;
		    }	  
	    return false;	    
	}
	
	/**
	 * date calculate
	 * @param inputDate
	 * @param field
	 * @param amount
	 * @return Date
	 */
	public static Date add(Date inputDate, int field, int amount) {
		final Calendar cal = Calendar.getInstance();
	    cal.setTime(inputDate);
	    cal.add(field, amount);
	    
	    return cal.getTime();
	}

	public static Date addDayOfYear(Date inputDate, int amount) {
		return add(inputDate, Calendar.DAY_OF_YEAR, amount);
	}

	public static Date addDate(Date inputDate, int amount) {
		return add(inputDate, Calendar.DATE, amount);
	}
	
	public static Date addMonth(Date inputDate, int amount) {
		return add(inputDate, Calendar.MONTH, amount);
	}
	
	public static Date addYear(Date inputDate, int amount) {
		return add(inputDate, Calendar.YEAR, amount);
	}
	
	public static java.sql.Date convertToSQLDate(Date date) {
		if(date == null)
			{return null;}
		
		return new java.sql.Date(date.getTime());
	}

	public static Timestamp convertToSQLTimestamp(Date date) {
		if(date == null)
			{return null;}
		
		return new Timestamp(date.getTime());
	}
	
	/**
	 * Method to truncte the input date's time to 00:00:00
	 * @param inputDate
	 * @return
	 */
	public static Date truncateTime(Date inputDate) {
		return truncateTime(inputDate, Calendar.getInstance());
	}
	
	/**
	 * Method to truncte the input date's time to 00:00:00
	 * @param inputDate
	 * @param calendar
	 * @return
	 */
	public static Date truncateTime(Date inputDate, Calendar calendar) {
	    Date date = null;
		if(inputDate != null){
		calendar.setTime(inputDate);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
			date = calendar.getTime();
		}
		return date;
	}

	/**
	 * get first day of month
	 * @param inputDate
	 * @param calendar
	 * @return Date
	 */
    public static Date getFirstDayOfMonth(Date date) {
        
        GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
        gc.setTime(date);
        gc.set(Calendar.DAY_OF_MONTH, 1);
	    return gc.getTime();
	 }

}