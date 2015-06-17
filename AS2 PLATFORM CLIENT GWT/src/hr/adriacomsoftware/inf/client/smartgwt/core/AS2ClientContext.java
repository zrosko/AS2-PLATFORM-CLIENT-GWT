package hr.adriacomsoftware.inf.client.smartgwt.core;

import hr.adriacomsoftware.inf.client.smartgwt.formatters.NumberFormat;

import java.math.BigDecimal;
import java.util.Date;
import java.util.LinkedHashMap;

import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.LocaleInfo;
import com.google.gwt.i18n.shared.DateTimeFormat;
import com.google.gwt.user.client.Window;
import com.smartgwt.client.data.Record;

public class AS2ClientContext {
	//session constants
	public static final String USER_FULL_NAME = "user_full_name";
	public static final String AS2_USERNAME = "as2_username";
	public static final String APPLICATION_ID = "application_id";
	public static final String APPLICATION = "application";
	public static final String ROLE_ID = "role_id";
	public static final String ROLE = "role";
	public static final String LOGIN_TIME = "login_time";
	public static final String TIMEOUT = "timeout";
	//session container
	private static final Record _session = new Record();
	
	//enviroment = "test", "prod"...
	public static String ENVIROMENT = "prod";
	
	public static final String AS2_DATE_FORMAT_DEFAULT = "dd.MM.yyyy";
	public static final String AS2_DATE_TIME_FORMAT_DEFAULT = "dd.MM.yyyy HH:mm:ss.S";
	public static final String AS2_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss.S";
	public static final String AS2_DATE_FORMAT = "yyyy-MM-dd";

	
	public static void setSessionValue(String key, String value){
		_session.setAttribute(key, value);
	}
	public static String getSessionValue(String key){
		return _session.getAttribute(key);
	}
	
	public static Record getSession() {
		return _session;
	}

	//Formatters Hrvatski amount formatter
	public static NumberFormat getValueNumberFormat(){
		/**
		 * POSTAVLJANJE NUMBERFORMATA!
		 */
		NumberFormat valueNf = NumberFormat.getCurrencyInstance("", true);
		valueNf.setGroupingSeparator(LocaleInfo.getCurrentLocale()
				.getNumberConstants().groupingSeparator());
		valueNf.setInputDecimalSeparator(LocaleInfo.getCurrentLocale()
				.getNumberConstants().decimalSeparator());
		valueNf.setDecimalSeparator(LocaleInfo.getCurrentLocale()
				.getNumberConstants().decimalSeparator());
		valueNf.setCurrencySymbol("");
		return valueNf;
	}
	//POSTAVLJANJE GWT NUMBERFORMATA
	public static com.google.gwt.i18n.client.NumberFormat nfGWT = com.google.gwt.i18n.client.NumberFormat.getDecimalFormat().overrideFractionDigits(2);
	public static com.google.gwt.i18n.client.NumberFormat nf2Decimals = com.google.gwt.i18n.client.NumberFormat.getDecimalFormat().overrideFractionDigits(2);
	public static com.google.gwt.i18n.client.NumberFormat nfNoDecimals = com.google.gwt.i18n.client.NumberFormat.getDecimalFormat().overrideFractionDigits(0);
	public static com.google.gwt.i18n.client.NumberFormat nf4Decimals = com.google.gwt.i18n.client.NumberFormat.getDecimalFormat().overrideFractionDigits(4);
	
	public static DateTimeFormat  _defaultDateFormatter = DateTimeFormat.getFormat(AS2_DATE_FORMAT_DEFAULT);

	public static final String TRANSPORT_SERVLET_URL = "json_servlet";

	public static LinkedHashMap<String, Object>  getProfitniCentriValueMap() {
		LinkedHashMap<String, Object> profitni_centri = new LinkedHashMap<String, Object>();
		profitni_centri.put("0", "Svi");
		profitni_centri.put("22000", "Šibenik");
		profitni_centri.put("22999", "Šibenik - SME");
		profitni_centri.put("10000", "Zagreb");
		profitni_centri.put("21000", "Split");
		return profitni_centri;
	}

	 public static String formatNumber(String value) {
		 //TODO provjeriti da li je value broj
		 if(value!=null && !value.equals(""))
			 return com.google.gwt.i18n.client.NumberFormat.getDecimalFormat().overrideFractionDigits(2).format(new BigDecimal(value));
		 return "0";
		 
	 }
	 
	 public static String formatDouble(double value) {
			 return com.google.gwt.i18n.client.NumberFormat.getDecimalFormat().overrideFractionDigits(2).format(new BigDecimal(value));
	 }
	 
	 public static String formatDouble4Decimals(double value) {
		 return com.google.gwt.i18n.client.NumberFormat.getDecimalFormat().overrideFractionDigits(4).format(new BigDecimal(value));
 }
	 
	 public static String formatDoubleNoDecimals(double value) {
		 return com.google.gwt.i18n.client.NumberFormat.getDecimalFormat().overrideFractionDigits(0).format(new BigDecimal(value));
	 }

	public static Date formatStringDateTimeToDate(String stringDate) {
		return DateTimeFormat.getFormat(AS2_DATE_TIME_FORMAT).parse(stringDate);
	}
	
	public static String formatDateTimeToString(Date date) {
		return DateTimeFormat.getFormat(AS2_DATE_TIME_FORMAT).format(date);
	}
	
	public static Date formatStringDateToDate(String stringDate) {
		return DateTimeFormat.getFormat(AS2_DATE_FORMAT).parse(stringDate);
	}
	
	public static String formatDateToString(Date date) {
		return DateTimeFormat.getFormat(AS2_DATE_FORMAT).format(date);
	}
	public static void showLoginForm() {
		Window.Location.replace(GWT.getHostPageBaseURL()+"module/login/login.jsp");
	}


}
