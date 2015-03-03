package hr.adriacomsoftware.inf.client.smartgwt.types.backup;

/**
 *
 * Koristi se kao item u dynmaicformama
 */
import java.util.Date;

import com.google.gwt.i18n.shared.DateTimeFormat;
import com.smartgwt.client.util.DateDisplayFormatter;
import com.smartgwt.client.util.DateParser;
import com.smartgwt.client.util.DateUtil;
import com.smartgwt.client.widgets.form.fields.DateItem;

public class AS2DateItem extends DateItem {

	DateTimeFormat dateFormatter;

	public AS2DateItem(String name) {
		this(name, name);
	}

	public AS2DateItem(String name, String title) {
		super(name, title);

		dateFormatter = DateTimeFormat.getFormat("dd.MM.yyyy");
		// Format Datuma
		DateUtil.setShortDateDisplayFormatter(new DateDisplayFormatter() {
			public String format(Date date) {
				if (date == null)
					return null;
				String format = dateFormatter.format(date);
				return format;
			}
		});
		DateUtil.setDefaultDateSeparator(".");
		DateUtil.setDateParser(new DateParser() {
			public Date parse(String dateString) {
				Date date = dateFormatter.parse(dateString);
				return date;
			}
		});
		setInputFormat("DMY");
		setUseMask(true);
		setEnforceDate(true);
		setUseTextField(true);
		setValidateOnExit(true);
		setValidateOnChange(true);

	}

}
