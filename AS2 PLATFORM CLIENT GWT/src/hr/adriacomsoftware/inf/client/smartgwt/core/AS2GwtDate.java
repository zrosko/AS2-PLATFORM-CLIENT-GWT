package hr.adriacomsoftware.inf.client.smartgwt.core;

import java.math.BigInteger;

public class AS2GwtDate {
	public static int getCurrentYear(){
		long time = System.currentTimeMillis();
		long milisPerYear = new BigInteger("31536000000").longValue();
		String currentYear = String.valueOf((int) Math.floor(time / milisPerYear) + 1970);
		return Integer.valueOf(currentYear);
		/* 	GregorianCalendar cal = new GregorianCalendar();
			int end_year = cal.get(Calendar.YEAR); */
	}
}
