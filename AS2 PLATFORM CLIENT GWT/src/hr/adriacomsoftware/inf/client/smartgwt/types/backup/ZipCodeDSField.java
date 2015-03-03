package hr.adriacomsoftware.inf.client.smartgwt.types.backup;

import com.smartgwt.client.data.SimpleType;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.widgets.form.validator.RegExpValidator;

public class ZipCodeDSField extends SimpleType {

	public ZipCodeDSField(String name){
		super(name, FieldType.TEXT);
		//FIELDTYPE je TEXT zbog poravnanja u lijevo
		RegExpValidator validator = new RegExpValidator("^\\d{5}$");
        setValidators(validator);
	}


}
