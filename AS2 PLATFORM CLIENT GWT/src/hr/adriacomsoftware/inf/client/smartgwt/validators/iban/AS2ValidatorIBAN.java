package hr.adriacomsoftware.inf.client.smartgwt.validators.iban;

import com.smartgwt.client.widgets.form.validator.CustomValidator;

public class AS2ValidatorIBAN extends CustomValidator {

	@Override
	protected boolean condition(final Object value) {
		if(value==null) return true;
		return IBAN.isCheckDigitValid(value.toString());
	}

}
