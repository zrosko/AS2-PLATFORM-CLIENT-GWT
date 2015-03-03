package hr.adriacomsoftware.inf.client.smartgwt.types.backup;

import com.smartgwt.client.data.SimpleType;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.widgets.form.fields.IntegerItem;
import com.smartgwt.client.widgets.form.validator.MaskValidator;
import com.smartgwt.client.widgets.form.validator.RegExpValidator;

public class YearDSField extends SimpleType {

	public YearDSField(String name) {
		super(name, FieldType.TEXT);
		//FIELDTYPE je TEXT zbog poravnanja u lijevo
		IntegerItem _broj = new IntegerItem(name);
//		_broj.setKeyPressFilter("[0-9].");
		_broj.setLength(5);
		_broj.setMask("####");
		MaskValidator maskValidator = new MaskValidator();
		RegExpValidator validator = new RegExpValidator("^\\d{4}");
//		broj_.setAlign(Alignment.RIGHT);
//		setValidators(new IsIntegerValidator());
		setEditorProperties(_broj);
	}

}
