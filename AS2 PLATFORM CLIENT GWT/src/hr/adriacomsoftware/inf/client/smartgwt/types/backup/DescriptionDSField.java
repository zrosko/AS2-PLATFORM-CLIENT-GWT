package hr.adriacomsoftware.inf.client.smartgwt.types.backup;

import com.smartgwt.client.data.SimpleType;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.widgets.form.fields.TextAreaItem;

public class DescriptionDSField extends SimpleType {
	public DescriptionDSField(String name) {
		super(name, FieldType.TEXT);

		TextAreaItem _textAreaItem = new TextAreaItem(name);
        _textAreaItem.setHeight(100);
        _textAreaItem.setColSpan(3);
        _textAreaItem.setWidth("100%");
		setEditorProperties(_textAreaItem);
	}

}
