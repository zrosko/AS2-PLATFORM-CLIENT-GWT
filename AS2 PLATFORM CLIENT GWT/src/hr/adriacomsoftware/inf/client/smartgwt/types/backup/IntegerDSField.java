package hr.adriacomsoftware.inf.client.smartgwt.types.backup;

import com.smartgwt.client.data.SimpleType;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.widgets.form.fields.IntegerItem;

public class IntegerDSField extends SimpleType {

	public IntegerDSField(String name) {
		super(name, FieldType.INTEGER);
		//FIELDTYPE je TEXT zbog poravnanja u lijevo
		IntegerItem _broj = new IntegerItem(name);
		_broj.setKeyPressFilter("[0-9]");
//		broj_.setAlign(Alignment.RIGHT);
//		setValidators(new IsIntegerValidator());
		setEditorProperties(_broj);
	}

}
