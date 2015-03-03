package hr.adriacomsoftware.inf.client.smartgwt.types.backup;

import com.smartgwt.client.data.SimpleType;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.widgets.form.validator.RegExpValidator;

public class EmailDSField extends SimpleType {

	public EmailDSField(String name){
		super(name, FieldType.TEXT);
		RegExpValidator  regExpValidator = new RegExpValidator();
		regExpValidator.setExpression("^([a-zA-Z0-9_.\\-+])+@(([a-zA-Z0-9\\-])+\\.)+[a-zA-Z0-9]{2,4}$");
        setValidators(regExpValidator);

	}


}
