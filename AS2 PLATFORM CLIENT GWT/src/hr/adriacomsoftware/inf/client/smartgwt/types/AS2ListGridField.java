package hr.adriacomsoftware.inf.client.smartgwt.types;

import java.util.LinkedHashMap;

import com.smartgwt.client.types.ListGridFieldType;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.grid.ListGridField;

public class AS2ListGridField extends AS2Field {
	public ListGridField _field = null;
	
	public AS2ListGridField(String name) {
		_field = new ListGridField(name);
		setEditor(new FormItem(name));
	}

	public AS2ListGridField(String name, Object width) {
		_field = new ListGridField(name);
		setEditor(new FormItem(name));
		if(width instanceof Integer){
			_field.setWidth((Integer)width);
		}else
			_field.setWidth((String)width);
	}
	
	public AS2ListGridField(String name, int type) {
		this(name,type,"",null,false);
	}
	
	public AS2ListGridField(String name, int type,String title) {
		this(name,type,title,null,false);
	}

	public AS2ListGridField(String name, int type,String title, int width) {
		this(name,type,title,width,false);
	}
	public AS2ListGridField(String name, int type,String title, Object width, boolean required){
		_field = new ListGridField(name);
		if(title!=null && !title.equals(""))
			_field.setTitle(title);
		if(width!=null){
			if(width instanceof Integer){
				_field.setWidth((Integer)width);
			}else
				_field.setWidth((String)width);
		}
		_field.setRequired(required);
		_type=type;
		switch (type) {
		case TEXT:
			_field.setType(ListGridFieldType.TEXT);
			createTextField(name);
			break;
		case TEXTAREA:
			_field.setType(ListGridFieldType.TEXT);
			createTextAreaField(name);
			break;
		case RICHTEXT:		//TESTIRATI
			_field.setType(ListGridFieldType.TEXT);
			createRichTextField(name);
			break;
		case AMOUNT:
		case AMOUNT_0:
		case AMOUNT_3:
		case AMOUNT_4:
		case AMOUNT_6:
		case AMOUNT_8:
			_field.setType(ListGridFieldType.FLOAT);
			createAmountField(name);
			break;
		case INTEGER:
			_field.setType(ListGridFieldType.INTEGER);
			createIntegerField(name);
			break;
		case DATE:
			_field.setType(ListGridFieldType.DATE);
			createDateItem(name);
			//TODO AS2ListGridField ima neke formattere i parsere
			break;
		case DATETIME:
			_field.setType(ListGridFieldType.DATETIME);
			createDateTimeItem(name);
			break;
		case TIME:
			_field.setType(ListGridFieldType.TIME);
			createTimeItem(name);
			break;
		case COMBO:
			_field.setType(ListGridFieldType.TEXT);
			createComboItem(name);
			break;
		case SELECT:
			_field.setType(ListGridFieldType.TEXT);
			createSelectItem(name);
			break;
		case FLOAT:
			_field.setType(ListGridFieldType.FLOAT);
			break;
		case BIGINT:
			_field.setType(ListGridFieldType.INTEGER);
			createBigIntegerField(name);
			break;
		case LINK:
			_field.setType(ListGridFieldType.LINK);
			createLinkField(name);
			break;
		case IMAGE:
			_field.setType(ListGridFieldType.IMAGE);
			break;
			//CUSTOM FIELDS BEGIN
		case OIB:
			_field.setType(ListGridFieldType.TEXT);
			createOibField(name);
			break;
		case JMBG:
			_field.setType(ListGridFieldType.TEXT);
			createJmbgField(name);
			break;
		case IBAN:
			_field.setType(ListGridFieldType.TEXT);
			createIbanField(name);
			break;
		//CUSTOM FIELDS END
		default:
			break;
		}		
	}
	
	public ListGridField getField(){
		return _field;
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
