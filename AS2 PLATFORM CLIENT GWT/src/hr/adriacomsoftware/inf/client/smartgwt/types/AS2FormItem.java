package hr.adriacomsoftware.inf.client.smartgwt.types;

import java.util.LinkedHashMap;

import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.types.MultiComboBoxLayoutStyle;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.form.fields.ButtonItem;
import com.smartgwt.client.widgets.form.fields.CancelItem;
import com.smartgwt.client.widgets.form.fields.CanvasItem;
import com.smartgwt.client.widgets.form.fields.CheckboxItem;
import com.smartgwt.client.widgets.form.fields.ComboBoxItem;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.FormItemIcon;
import com.smartgwt.client.widgets.form.fields.HeaderItem;
import com.smartgwt.client.widgets.form.fields.MultiComboBoxItem;
import com.smartgwt.client.widgets.form.fields.RadioGroupItem;
import com.smartgwt.client.widgets.form.fields.ResetItem;
import com.smartgwt.client.widgets.form.fields.SectionItem;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.StaticTextItem;
import com.smartgwt.client.widgets.form.fields.SubmitItem;
import com.smartgwt.client.widgets.form.fields.UploadItem;

public class AS2FormItem extends AS2Field {
	public FormItem _field = null;
	
	//will take properties from Data Source field, using name at the time of draw
	public AS2FormItem(String name) {
		_field = new FormItem(name);
		_editor=_field;
//		setEditor(_field);
		_field.setWidth("*");
	}

	public AS2FormItem(String name, int type) {
		this(name, type, "", 1, null, false, false, false);
	}

	public AS2FormItem(String name, int type, String title) {
		this(name, type, title, 1, null, false, false, false);

	}

	public AS2FormItem(String name, int type, String title, Object colSpan) {
		this(name, type, title, colSpan, null, false, false, false);

	}

	public AS2FormItem(String name, int type, String title, Object colSpan,
			FormItemIcon icon) {
		this(name, type, title, colSpan, icon, false, false, false);
	}

	public AS2FormItem(String name, int type, String title, Object colSpan,
			FormItemIcon icon, boolean endRow) {
		this(name, type, title, colSpan, icon, endRow, false, false);
	}

	public AS2FormItem(String name, int type, String title, Object colSpan,
			FormItemIcon icon, boolean endRow, boolean required) {
		this(name, type, title, colSpan, icon, endRow, required, false);
	}
	//will take properties from Data Source but will override field editor 
	public AS2FormItem(String name, int type, String title, Object colSpan,
			FormItemIcon icon, boolean endRow, boolean required,
			boolean disabled) {
		_field = new FormItem(name);
		if (title != null && !title.equals(""))
			_field.setTitle(title);
		if (colSpan != null) {
			if (colSpan instanceof Integer)
				_field.setColSpan((Integer) colSpan);
			else if (colSpan instanceof String)
				_field.setColSpan((String) colSpan);
		}
		if (icon != null)
			_field.setIcons(icon);
		_field.setEndRow(endRow);
		_field.setRequired(required);
		_field.setDisabled(disabled);
		_field.setWidth("*");
		_type = type;
		FormItem item = null;
		switch (type) {
		case TEXT:
			_field.setType("text");
			createTextField(name);
			break;
		case TEXTAREA:
			_field.setType("text");
			createTextAreaField(name);
			break;
		case RICHTEXT:		//TESTIRATI
			_field.setType("canvas");
			createRichTextField(name);
			break;
		case AMOUNT:
		case AMOUNT_0:
		case AMOUNT_3:
		case AMOUNT_4:
		case AMOUNT_6:
		case AMOUNT_8:
			_field.setType("float");
			createAmountField(name);
			break;
		case INTEGER:
			_field.setType("integer");
			createIntegerField(name);
			break;
		case DATE:
			_field.setType("date");
			createDateItem(name);
			break;
		case DATETIME:
			_field.setType("datetime");
			createDateTimeItem(name);
			break;
		case TIME:
			_field.setType("time");
			createTimeItem(name);
			break;
		case COMBO:
			_field.setType("text");
			createComboItem(name);
			break;
		case FLOAT:
			_field.setType("float");
			break;
		case BIGINT:
			_field.setType("integer");
			createBigIntegerField(name);
			break;
		case LINK:
			_field.setType("LinkItem");
			createLinkField(name);
			break;
		case IMAGE_FILE:
			_field.setType("imageFile");
			createImageFileField(name);
			break;
		//CUSTOM FIELDS BEGIN
		case OIB:
			_field.setType("text");
			createOibField(name);
			break;
		case JMBG:
			_field.setType("text");
			createJmbgField(name);
			break;
		case IBAN:
			_field.setType("text");
			createIbanField(name);
			break;
		//CUSTOM FIELDS END
		case FORM_DATERANGE:
			_field.setType("dateRange");
			createDateRangeItem(name);
			break;
		case FORM_BUTTON:
			_field.setType("button");
			setEditor(new ButtonItem(name));
			break;
		case FORM_RESETBUTTON:
			_field.setType("resetButton");
			item = new ResetItem(name);
			item.setTitle(title);
			setEditor(item);
			break;
		case FORM_CANCELBUTTON:
			_field.setType("cancelButton");
			item = new CancelItem(name);
			item.setTitle(title);
			setEditor(item);
			break;
		case FORM_SUBMITBUTTON:
			_field.setType("submitButton");
			item = new SubmitItem(name);
			item.setTitle(title);
			setEditor(item);
			break;
		case FORM_SEPARATOR:
			_field.setType("separator");
			item = new ButtonItem(name);
			item.setHeight(1);
			item.setStartRow(true);
			setEditor(item);
			break;
		case FORM_LABEL:
			_field.setType("label");
			item = new HeaderItem(name);
			item.setTextBoxStyle("crm-Form-Label");
			item.setStartRow(false);
			item.setValue(title);
			setEditor(item);
			break;
		case FORM_SECTION:
			_field.setType("section");
			SectionItem item_si = new SectionItem(name);
			item_si.setSectionExpanded(true);
			item_si.setOverflow(Overflow.AUTO);
			item_si.setDefaultValue(title);
			item_si.setStartRow(true);
			item_si.setEndRow(true);
			item_si.setColSpan("*");
			setEditor(item_si);
			break;
		case FORM_MULTICOMBOBOXITEM:
			_field.setType("MultiComboBoxItem");
			// TODO PROVJERITI DA LI RADI!
			MultiComboBoxItem item_mcb = new MultiComboBoxItem(name);
			item_mcb.setLayoutStyle(MultiComboBoxLayoutStyle.VERTICAL);
//			item_mcb.setValueMap(getCacheManagerData(name));
			item_mcb.setFilterLocally(true);
			Criteria criteria = new Criteria("vrsta",name);
			item_mcb.setAttribute(AS2_FILTER_CRITERIA, criteria);
			setAttribute(AS2_FILTER_CRITERIA,criteria);
			setEditor(item_mcb);
			break;
		case SELECT:
		case FORM_SELECT:
			_field.setType("select");
			createSelectItem(name);
			break;
		case FORM_CANVAS:
			_field.setType("canvas");
			// TODO nekad ne radi
			setEditor(new CanvasItem(name));
			break;
		case FORM_RADIOGROUP:
			_field.setType("radioGroup");
			item = new RadioGroupItem(name);
			item.setFilterLocally(true);
			Criteria criteria_rg = new Criteria("vrsta",name);
			item.setAttribute(AS2_FILTER_CRITERIA, criteria_rg);
			setAttribute(AS2_FILTER_CRITERIA,criteria_rg);
			setEditor(item);
			break;
		case FORM_UPLOAD:
			_field.setType("upload");
			setEditor(new UploadItem(name));
			break;
		case FORM_CHECKBOX:
			_field.setType("checkbox");
			setEditor( new CheckboxItem(name));
			break;
		case FORM_STATIC_TEXT:
			_field.setType("staticText");
			setEditor( new StaticTextItem(name));
			break;
		default:
			break;
		}
	}

	public void setStartRow(boolean startRow) {
		_field.setStartRow(startRow);
	}

	public void setEndRow(boolean endRow) {
		_field.setEndRow(endRow);
	}

	public FormItem getField() {
		return _field;
	}

	public void setKeyPressFilter(String keyPressFilter) {
		setAttribute("keyPressFilter", keyPressFilter);
	}

	public void setMask(String mask) {
		setAttribute("mask", mask);// TODO testirati
	}

	// Pripaziti ako se nasljeđuju svojstva formitema iz datasourcea
	public void setDefaultToFirstOption(Boolean defaultToFirstOption) {
		if (_editor instanceof SelectItem) {
			((SelectItem) _editor).setDefaultToFirstOption(true);
		} else if (_editor instanceof ComboBoxItem) {
			((ComboBoxItem) _editor).setDefaultToFirstOption(true);
		}
		if(_editor!=null){
			setEditorProperties(_editor);
		}
		setAttribute("defaultToFirstOption", defaultToFirstOption);
	}

	public void setAddUnknownValues(boolean addUnknownValues) {
		if (_editor instanceof ComboBoxItem) {
			((ComboBoxItem) _editor).setAddUnknownValues(addUnknownValues);
		}else if(_editor instanceof SelectItem){
			((SelectItem) _editor).setAddUnknownValues(addUnknownValues);
		}
		setEditorProperties(_editor);
		setAttribute("addUnknownValues", addUnknownValues);
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
	
	public void setCanEdit(boolean canEdit) {
		_field.setAttribute("canEdit", canEdit);
		if(canEdit==false)
			_field.setAttribute("titleStyle", "crm-Form-CanEditFalse");
	}
	public void setTextBoxStyle(String textBoxStyle) {
		if (_editor instanceof HeaderItem)
			_editor.setTextBoxStyle(textBoxStyle);
		setEditorProperties(_editor);
		setAttribute("textBoxStyle", textBoxStyle);
	}
}
