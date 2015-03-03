package hr.adriacomsoftware.inf.client.smartgwt.types.backup;

/**
 *
 * Koristi se kao item u dynmaicformama
 */

import hr.adriacomsoftware.inf.client.smartgwt.formatters.NumberFormat;

import java.math.BigDecimal;

import com.google.gwt.i18n.client.LocaleInfo;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.FormItemValueFormatter;
import com.smartgwt.client.widgets.form.FormItemValueParser;
import com.smartgwt.client.widgets.form.fields.FloatItem;
import com.smartgwt.client.widgets.form.fields.FormItem;

public class AS2ValueItem extends FloatItem {

	private NumberFormat nf;
	private com.google.gwt.i18n.client.NumberFormat nfGWT;

	public AS2ValueItem(String name) {
		this(name, name);
	}

	public AS2ValueItem(String name, String title) {
		super(name, title);

		/**
		 * POSTAVLJANJE NUMBERFORMATA!
		 */
		nf = NumberFormat.getCurrencyInstance("", true);
		nf.setGroupingSeparator(LocaleInfo.getCurrentLocale()
				.getNumberConstants().groupingSeparator());
		nf.setInputDecimalSeparator(LocaleInfo.getCurrentLocale()
				.getNumberConstants().decimalSeparator());
		nf.setDecimalSeparator(LocaleInfo.getCurrentLocale()
				.getNumberConstants().decimalSeparator());
		nf.setCurrencySymbol("");

		/**
		 * POSTAVLJANJE GWT NUMBERFORMATA
		 */
		nfGWT = com.google.gwt.i18n.client.NumberFormat.getDecimalFormat()
				.overrideFractionDigits(2);

		// Formatter za currency
		FormItemValueFormatter valueFormatter = new FormItemValueFormatter() {

			@Override
			public String formatValue(Object value, Record record,
					DynamicForm form, FormItem item) {
				String formattedValue = null;
				if (value != null) {
					try {
						formattedValue = nfGWT.format(new BigDecimal(value
								.toString()));

					} catch (final Exception e) {
						formattedValue = value.toString();
					}
				}
				return formattedValue;
			}

		};

		setTextAlign(Alignment.RIGHT);
		setKeyPressFilter("[0-9,-]");
		setValueFormatter(valueFormatter);
		setEditorValueFormatter(valueFormatter);
		setEditorValueParser(new FormItemValueParser() {

			@Override
			public Object parseValue(String value, DynamicForm form,
					FormItem item) {
				if (value != null && value.length() > 0) {
					try {
						System.out.println("Parse Value" + nfGWT.parse(value));
						return nfGWT.parse(value);

					} catch (final Exception e) {
						return value;
					}
				}
				return "";
			}
		});
	}
}
