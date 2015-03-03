package hr.adriacomsoftware.inf.client.smartgwt.types.backup;

import hr.adriacomsoftware.inf.client.smartgwt.formatters.NumberFormat;

import com.smartgwt.client.core.DataClass;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.SimpleType;
import com.smartgwt.client.data.SimpleTypeFormatter;
import com.smartgwt.client.data.SimpleTypeParser;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.widgets.DataBoundComponent;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.validator.RegExpValidator;

public class AS2MoneyFieldDS extends SimpleType {

	private static final String MONEY_INPUT_VALIDATION_REGEX = "^[+-]?[0-9]{1,3}(?:[0-9]*(?:[.,][0-9]{2})?|(?:,[0-9]{3})*(?:\\.[0-9]{2})?|(?:\\.[0-9]{3})*(?:,[0-9]{2})?)";
	private static String MONEY_INPUT_REGEX = "^[+-]?[0-9]{1,3}(?:[0-9]*(?:[.,][0-9]{2})";
	private static String MONEY_INPUT_REGEXEU = "^\\s*-?((\\d{1,3}(\\.(\\d){3})*)|\\d*)(,\\d{1,2})?\\s?(\\u20AC)?\\s*$";
	private static String MONEY_INPUT_REGEXEUB = "^[-]?([1-9]{1}[0-9]{0,}(\\,[0-9]{0,2})?|0(\\,[0-9]{0,2})?|\\,[0-9]{1,2})";


	public AS2MoneyFieldDS(String name) {
		super(name, FieldType.FLOAT);

		// Formatter za currency
		SimpleTypeFormatter simpletypeformatter = new SimpleTypeFormatter() {

			@Override
			public String format(Object value, DataClass field,
					DataBoundComponent component, Record record) {
				// TODO Auto-generated method stub
				String formattedValue = null;
				if (value != null) {
					try {
						com.google.gwt.i18n.client.NumberFormat nf = com.google.gwt.i18n.client.NumberFormat
								.getCurrencyFormat();
						formattedValue = nf
								.format(new Double(value.toString()));
						return formattedValue;
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
		TextItem _iznos = new TextItem();
		_iznos.setTextAlign(Alignment.RIGHT);
		_iznos.setKeyPressFilter("[0-9,-]");
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
				if (value != null) {
					try {
						// NumberFormat nf = NumberFormat.getFormat("#.##0,0#");
						NumberFormat nf = NumberFormat.getCurrencyInstance("",
								true);
						System.out.println("Parse Value" + nf.parse(value));
						return nf.parse(value);

					} catch (final Exception e) {
						return value;
					}
				}
				return 0;
			}
		});
		// setEditorProperties(_iznos);
		// FloatRangeValidator rangeValidator = new FloatRangeValidator();
		// rangeValidator.setMin(0);
		// rangeValidator.setErrorMessage("Iznos mora biti pozitivan");
		RegExpValidator regExpValidator = new RegExpValidator();
		regExpValidator.setExpression(MONEY_INPUT_REGEXEUB);
		regExpValidator.setErrorMessage("Morate unijeti ispravno!");
		// MaskValidator maskValidator = new MaskValidator();
		// maskValidator.setMask("^\\s*(1?)\\s*\\(?\\s*(\\d{3})\\s*\\)?\\s*-?\\s*(\\d{3})\\s*-?\\s*(\\d{4})\\s*$");
		// maskValidator.setTransformTo("$1($2) $3 - $4");
//		FloatPrecisionValidator precisionValidator = new FloatPrecisionValidator();
//		precisionValidator.setPrecision(5);
//		precisionValidator.setErrorMessage("Dozvoljene su 2 decimale");
//		// iznos.setValidators(regExpValidator,rangeValidator,
		// precisionValidator);
		// iznos.setValidators(regExpValidator, precisionValidator);
		_iznos.setValidateOnChange(false);
		_iznos.setValidateOnExit(true);

		setValidators(regExpValidator/*,precisionValidator*/);
		setEditorType(_iznos);
		// setValidators(regExpValidator,precisionValidator);

	}

}
