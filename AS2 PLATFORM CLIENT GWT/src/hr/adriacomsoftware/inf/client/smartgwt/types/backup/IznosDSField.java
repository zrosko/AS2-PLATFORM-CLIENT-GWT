package hr.adriacomsoftware.inf.client.smartgwt.types.backup;

import hr.adriacomsoftware.inf.client.smartgwt.formatters.NumberFormat;

import java.math.BigDecimal;

import com.google.gwt.i18n.client.LocaleInfo;
import com.smartgwt.client.core.DataClass;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.SimpleType;
import com.smartgwt.client.data.SimpleTypeFormatter;
import com.smartgwt.client.data.SimpleTypeParser;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.widgets.DataBoundComponent;
import com.smartgwt.client.widgets.form.fields.FloatItem;

public class IznosDSField extends SimpleType {

//	private static final String MONEY_INPUT_VALIDATION_REGEX = "^[+-]?[0-9]{1,3}(?:[0-9]*(?:[.,][0-9]{2})?|(?:,[0-9]{3})*(?:\\.[0-9]{2})?|(?:\\.[0-9]{3})*(?:,[0-9]{2})?)";
//	private static String MONEY_INPUT_REGEX = "^[+-]?[0-9]{1,3}(?:[0-9]*(?:[.,][0-9]{2})";
//	private static String MONEY_INPUT_REGEXEU = "^\\s*-?((\\d{1,3}(\\.(\\d){3})*)|\\d*)(,\\d{1,2})?\\s?(\\u20AC)?\\s*$";
//	private static String MONEY_INPUT_REGEXEUB = "^[-]?([1-9]{1}[0-9]{0,}(\\,[0-9]{0,2})?|0(\\,[0-9]{0,2})?|\\,[0-9]{1,2})$";
//	private static String MONEY_INPUT_REGEXEUC = "^[-]?([1-9]{1}[0-9]{0,}(\\,[0-9]{1,2})?|0(\\,[0-9]{1,2})?|\\,[0-9]{1,2})$";
	private NumberFormat nf;
	private com.google.gwt.i18n.client.NumberFormat nfGWT;

	public IznosDSField(String name) {
		super(name, FieldType.FLOAT);

		/**
		 * POSTAVLJANJE NUMBERFORMATA!
		 */
		nf = NumberFormat.getCurrencyInstance("",true);
//		nf.setDecimalSeparator(LocaleInfo.getCurrentLocale().getNumberConstants().decimalSeparator());
		nf.setGroupingSeparator(LocaleInfo.getCurrentLocale().getNumberConstants().groupingSeparator());
//		nf.setGroupingSeparator(NumberFormat.PERIOD);
		nf.setInputDecimalSeparator(LocaleInfo.getCurrentLocale().getNumberConstants().decimalSeparator());
		nf.setDecimalSeparator(LocaleInfo.getCurrentLocale().getNumberConstants().decimalSeparator());
//		nf.setTruncate(true);
		nf.setCurrencySymbol("");

		/**
		 * POSTAVLJANJE GWT NUMBERFORMATA
		 */
		nfGWT = com.google.gwt.i18n.client.NumberFormat.getDecimalFormat().overrideFractionDigits(2);


//		DecimalFormat
//		nfText = java.text.NumberFormat.getCurrencyInstance();
//		DecimalFormatSymbols decimalFormatSymbols = ((DecimalFormat) nfText).getDecimalFormatSymbols();
//		decimalFormatSymbols.setCurrencySymbol("");
////		nfText.setMaximumFractionDigits(2);
////		nfText.setGroupingUsed(true);
////		String groupingSeparator = LocaleInfo.getCurrentLocale().getNumberConstants().groupingSeparator();
////		String decimalSeparator = LocaleInfo.getCurrentLocale().getNumberConstants().decimalSeparator();
////		if(decimalSeparator != null || decimalSeparator.length()>0)
////			decimalFormatSymbols.setDecimalSeparator(decimalSeparator.charAt(0));
////		decimalFormatSymbols.setGroupingSeparator(if(LocaleInfo.getCurrentLocale().getNumberConstants().groupingSeparator());
////
//		((DecimalFormat) nfText).setDecimalFormatSymbols(decimalFormatSymbols);


		// Formatter za currency
		SimpleTypeFormatter simpletypeformatter = new SimpleTypeFormatter() {

			@Override
			public String format(Object value, DataClass field,
					DataBoundComponent component, Record record) {
				// TODO Auto-generated method stub
				String formattedValue = null;
				if (value != null) {
					try {
//						formattedValue = nfGWT.format(value.toString());
//						formattedValue = nfGWT.format(Double.parseDouble(value.toString()));

						formattedValue = nfGWT.format(new BigDecimal(value.toString()));

					} catch (final Exception e) {
						formattedValue = value.toString();
					}
				}
				return formattedValue;

			}
		};

		// Formatiranje iznosa u listgridu!
		setShortDisplayFormatter(simpletypeformatter);
		setNormalDisplayFormatter(simpletypeformatter);

		FloatItem _iznos = new FloatItem(name);
		_iznos.setTextAlign(Alignment.RIGHT);
		//		_iznos.setAlign(Alignment.RIGHT);
		_iznos.setKeyPressFilter("[0-9,-]");
//		_iznos.setInputFormat("#.##0,0#");
//		_iznos.setEmptyDisplayValue("0,00");
		// Formatiranje iznosa u formitem editoru
		// _iznos.setEditorValueFormatter(new FormItemValueFormatter() {
		// @Override
		// public String formatValue(Object value, Record record,
		// DynamicForm form, FormItem item) {
		// // TODO Auto-generated method stub
		// String formattedValue = null;
		// if (value != null) {
		// try {
		// com.google.gwt.i18n.client.NumberFormat nf =
		// com.google.gwt.i18n.client.NumberFormat
		// .getCurrencyFormat();
		// formattedValue = nf.format(new Double(value.toString()));
		// return formattedValue;
		// } catch (final Exception e) {
		// formattedValue = value.toString();
		// }
		// }
		// return formattedValue;
		// }
		//
		// });
		// Parsiranje iznosa u formitem editoru
		// _iznos.setEditorValueParser(new FormItemValueParser() {
		// @Override
		// public Object parseValue(String value, DynamicForm form,
		// FormItem item) {
		// // TODO Auto-generated method stub
		// if (value != null) {
		// try {
		// // NumberFormat nf = NumberFormat.getFormat("#.##0,0#");
		// NumberFormat nf = NumberFormat.getCurrencyInstance("",
		// true);
		// System.out.println("Parse Value" + nf.parse(value));
		// return nf.parse(value);
		//
		// } catch (final Exception e) {
		// return value;
		// }
		// }
		// return 0;
		// }
		// });


		setEditFormatter(simpletypeformatter);
		setEditParser(new SimpleTypeParser() {

			@Override
			public Object parseInput(String value, DataClass field,
					DataBoundComponent component, Record record) {
				// TODO Auto-generated method stub
				if (value != null && value.length()>0) {
//					String text = value.replace(",", ".");
					try {

//						 NumberFormat nf = NumberFormat.getFormat("#.##0,0#");
						 System.out.println("Parse Value" + nfGWT.parse(value));
////							return nf.parse("+"+text);
//						}
//						else
							return nfGWT.parse(value);

					} catch (final Exception e) {
						return value;
					}
				}
				return "";
			}
		});


		// setEditorProperties(_iznos);
		// FloatRangeValidator rangeValidator = new FloatRangeValidator();
		// rangeValidator.setMin(0);
		// rangeValidator.setErrorMessage("Iznos mora biti pozitivan");
//		RegExpValidator regExpValidator = new RegExpValidator();
//		regExpValidator.setExpression(MONEY_INPUT_REGEXEUC);
//		regExpValidator.setErrorMessage("Morate unijeti ispravno!");
		// MaskValidator maskValidator = new MaskValidator();
		// maskValidator.setMask("^\\s*(1?)\\s*\\(?\\s*(\\d{3})\\s*\\)?\\s*-?\\s*(\\d{3})\\s*-?\\s*(\\d{4})\\s*$");
		// maskValidator.setTransformTo("$1($2) $3 - $4");
//		FloatPrecisionValidator precisionValidator = new FloatPrecisionValidator();
//		precisionValidator.setPrecision(5);
//		precisionValidator.setErrorMessage("Dozvoljene su 2 decimale");
//		// iznos.setValidators(regExpValidator,rangeValidator,
		// precisionValidator);
		// iznos.setValidators(regExpValidator, precisionValidator);
		_iznos.setValidateOnExit(true);
		_iznos.setValidateOnChange(false);
//		setValidators(regExpValidator/*,precisionValidator*/);
		setEditorProperties(_iznos);

		// setValidators(regExpValidator,precisionValidator);

	}


}
