package hr.adriacomsoftware.inf.client.smartgwt.types.backup;

import com.smartgwt.client.data.SimpleType;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.widgets.form.fields.TextItem;

public class OibDSField extends SimpleType {

	public OibDSField(String name) {
		super(name, FieldType.TEXT);
		//FIELDTYPE je TEXT zbog poravnanja u lijevo
		TextItem _broj = new TextItem(name);
//		_broj.setKeyPressFilter("[0-9]");
		_broj.setMask("00000000000");
//		broj_.setAlign(Alignment.RIGHT);
//		setValidators(new IsIntegerValidator());
		setEditorProperties(_broj);
	}

}
