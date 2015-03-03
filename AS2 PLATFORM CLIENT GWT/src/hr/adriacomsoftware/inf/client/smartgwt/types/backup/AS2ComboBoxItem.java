package hr.adriacomsoftware.inf.client.smartgwt.types.backup;

import com.smartgwt.client.types.TextMatchStyle;
import com.smartgwt.client.widgets.form.fields.ComboBoxItem;

public class AS2ComboBoxItem extends ComboBoxItem {
	private boolean _uSifrarniku = true;

	public AS2ComboBoxItem(String name, boolean uSifrarniku) {
		this(name,name,uSifrarniku);
	}

	public AS2ComboBoxItem(String name, String title, boolean uSifrarniku) {
		super(name, title);
		this._uSifrarniku= uSifrarniku;
		setCompleteOnTab(true);
		setAddUnknownValues(false);
		//TODO Criteria za pretraživanje
		setTextMatchStyle(TextMatchStyle.SUBSTRING);
//		setOptionCriteria(new Criteria("vrsta",this.getName());


	}

	public boolean getUSifrarniku() {
		if (_uSifrarniku)
			return true;
		else
			return false;

	}

}
