package hr.adriacomsoftware.inf.client.smartgwt.types;

import java.util.LinkedHashMap;

import com.smartgwt.client.data.DataSourceField;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.ViewFileItem;

public class AS2DataSourceField extends AS2Field {
	public DataSourceField _field = null;

	public AS2DataSourceField(String name) {
		this(name,AS2Field.TEXT,"",0,false);
	}

	public AS2DataSourceField(String name, int type) {
		this(name,type,"",0,false);
	}
	
	public AS2DataSourceField(String name, int type,String title) {
		this(name,type,title,0,false);
	}
	
	public AS2DataSourceField(String name, int type,String title, int length) {
		this(name,type,title,length,false);
	}
	
	public AS2DataSourceField(String name,  int type, String title, int length, boolean required) {
		_field = new DataSourceField();
		_field.setName(name);
		_field.setTitle(title);
		_field.setRequired(required);
		if(length!=0){
			_field.setLength(length);
		}
		_type=type;
		switch (type) {
			case TEXT:		
				_field.setType(FieldType.TEXT);
				createTextField(name);
				break;
			case TEXTAREA:		
				_field.setType(FieldType.TEXT);
				createTextAreaField(name);
				break;
			case RICHTEXT:		//TESTIRATI
				_field.setType(FieldType.TEXT);
				createRichTextField(name);
				break;
			case AMOUNT:
			case AMOUNT_0:
			case AMOUNT_3:
			case AMOUNT_4:
			case AMOUNT_6:
			case AMOUNT_8:
				AS2SimpleType simpleType = new AS2SimpleType();
				_field.setType(simpleType.getAmountSimpleType(_field,type));
				createAmountField(name);
				break;	
			case INTEGER:
				_field.setType(FieldType.INTEGER);
				createIntegerField(name);
				break;
			case DATE:
				_field.setType(FieldType.DATE);
				createDateItem(name);
				break;
			case DATETIME:
				_field.setType(FieldType.DATETIME);
				createDateTimeItem(name);
				break;
			case TIME:
				_field.setType(FieldType.TIME);
				createTimeItem(name);
				break;
			case COMBO:
				_field.setType(FieldType.TEXT);
				createComboItem(name);
				break;
			case SELECT:
				_field.setType(FieldType.TEXT);
				createSelectItem(name);
				break;
			case PRIMARY_KEY:		
				_field.setType(FieldType.INTEGER);
				_field.setHidden(true);
				_field.setPrimaryKey(true);
				break;
			case FLOAT:
				_field.setType(FieldType.FLOAT);
				break;
			case BIGINT:
				_field.setType(FieldType.INTEGER);
				createBigIntegerField(name);
				break;
			case LINK:
				_field.setType(FieldType.LINK);
				createLinkField(name);
				break;
			case BINARY:
				_field.setType(FieldType.BINARY);
				break;
			case IMAGE:
				_field.setType(FieldType.IMAGE);
				break;
			case IMAGE_FILE:
				_field.setType(FieldType.IMAGEFILE);
				ViewFileItem item = new ViewFileItem(name);
				item.setShowFileInline(false);
				setEditor(item);
				break;
				//CUSTOM FIELDS BEGIN
			case OIB:
				_field.setType(FieldType.TEXT);
				createOibField(name);
				break;
			case JMBG:
				_field.setType(FieldType.TEXT);
				createJmbgField(name);
				break;
			case IBAN:
				_field.setType(FieldType.TEXT);
				createIbanField(name);
				break;
			//CUSTOM FIELDS END
		default:
			break;
		}
	}
	
	public DataSourceField getField() {
		return _field;
	}

	public void setLength(int length) {
		_field.setLength(length);
	}

	public void setHidden(boolean hidden) {
		_field.setHidden(hidden);
	}

	public void setCanFilter(Boolean canFilter) {
		_field.setCanFilter(canFilter);
	}

	@Override
	public void setAttribute(String attribute, Object value) {
		_field.setAttribute(attribute, value);
	}

	@Override
	public Object getAttribute(String attribute) {
		return _field.getAttributeAsObject(attribute);
	}

	@Override
	public void setEditorProperties(FormItem editorProperties) {
		_field.setEditorProperties(editorProperties);
	}

	@Override
	public void setValueMap(LinkedHashMap<String, Object> valueMap) {
		if(_editor!=null){
			_editor.setValueMap(valueMap);
			_field.setEditorProperties(_editor);
		}
		_field.setValueMap(valueMap);
	}
	
	@Override
	public void setValueMap(String... valueMap) {
		if(_editor!=null){
			_editor.setValueMap(valueMap);
			_field.setEditorProperties(_editor);
		}
		_field.setValueMap(valueMap);
	}
}
