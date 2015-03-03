package hr.adriacomsoftware.inf.client.smartgwt.formatters;

import java.util.Date;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.smartgwt.client.types.DateDisplayFormat;
import com.smartgwt.client.util.DateUtil;

public class CroatianDateFormat extends AS2DateFormat{

	DateTimeFormat dtf = DateTimeFormat.getFormat("dd.MM.yyyy HH:mm:ss");
	DateTimeFormat df = DateTimeFormat.getFormat("dd.MM.yyyy");
	
	private boolean showDateOnly;

	/**
	 * 
	 * @param showDateOnly
	 *            format dd.MM.yyyy if true, else dd.MM.yyyy HH:mm:ss
	 */
	public CroatianDateFormat(boolean showDateOnly) {
		this.showDateOnly = showDateOnly;
		DateUtil.setShortDisplayFormat(DateDisplayFormat.TOEUROPEANSHORTDATE.getValue());
		if(showDateOnly){
			DateUtil.setShortDateDisplayFormatter(this);
		}else{
			DateUtil.setShortDatetimeDisplayFormatter(this);
		}
		DateUtil.setDefaultDateSeparator(".");
		DateUtil.setDateInputFormat("DMY");
	}

	public String format(Date date) {
		if (date == null) {
			return null;
		}
		String ret;
		if (isShowDateOnly()) {
			ret = df.format(date);
		} else {
			ret = dtf.format(date);
			// we do not want to show the time if it's midnight
			if (ret.endsWith(" 00:00:00")) {
				ret = ret.substring(0, ret.indexOf(" 00:00:00"));
			} else if (ret.endsWith(" 00:00")) {
				ret = ret.substring(0, ret.indexOf(" 00:00"));
			}
		}
		return ret;
	}

	public Date parse(String string) {
		if (string == null) {
			return null;
		}
		Date ret = null;
		try {
			ret = dtf.parse(string);
		} catch (Exception ex) {
			ret = df.parse(string);
		}
		return ret;
	}

	public void setShowDateOnly(boolean showDateOnly) {
		this.showDateOnly = showDateOnly;
	}

	public boolean isShowDateOnly() {
		return showDateOnly;
	}

}