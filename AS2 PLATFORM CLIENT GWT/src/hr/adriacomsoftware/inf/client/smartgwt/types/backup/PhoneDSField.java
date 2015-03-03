package hr.adriacomsoftware.inf.client.smartgwt.types.backup;

import com.smartgwt.client.data.SimpleType;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.widgets.form.fields.IntegerItem;

public class PhoneDSField extends SimpleType {

	public PhoneDSField(String name) {
		super(name, FieldType.TEXT);
		//FIELDTYPE je TEXT zbog poravnanja u lijevo
		IntegerItem _phone = new IntegerItem(name);
		_phone.setKeyPressFilter("[0-9]");
//		_phone.setMask("(999) ###-###9");
//		MaskValidator maskValidator = new MaskValidator();
//		maskValidator.setMask("^\\s*(1?)\\s*\\(?\\s*(\\d{3})\\s*\\)?\\s*-?\\s*(\\d{3})\\s*-?\\s*(\\d{4})\\s*$");
//		maskValidator.setTransformTo("$1($2) $3 - $4");
//		_phone.setValidators(maskValidator);
		setEditorProperties(_phone);

	}

}
