package hr.adriacomsoftware.inf.client.smartgwt.core;

import java.math.BigInteger;
import java.util.Date;

public class AS2GwtDate {
	public static int getCurrentYear(){
		long time = System.currentTimeMillis();
		long milisPerYear = new BigInteger("31536000000").longValue();
		String currentYear = String.valueOf((int) Math.floor(time / milisPerYear) + 1970);
		return Integer.valueOf(currentYear);
		/* 	GregorianCalendar cal = new GregorianCalendar();
			int end_year = cal.get(Calendar.YEAR); -*/
	}
	public static int getCurrentMonth(){
		long time = System.currentTimeMillis();
		Date date = new Date(time);
		return date.getMonth();
	}
	public static Date addDays(Date dateIn, int numDays) {
	   long milisPerDay = 86400000;
	   // convert the dateIn to milliseconds
	   long dateInMilis = dateIn.getTime();
	   // add numDays to the date
	   dateInMilis = dateInMilis + (numDays * milisPerDay);
	   return new Date(dateInMilis);
	}
}
