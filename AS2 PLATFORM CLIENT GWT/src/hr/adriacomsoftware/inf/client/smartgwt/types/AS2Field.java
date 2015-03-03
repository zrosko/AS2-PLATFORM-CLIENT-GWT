package hr.adriacomsoftware.inf.client.smartgwt.types;

import hr.adriacomsoftware.inf.client.smartgwt.desktop.AS2CacheManager;
import hr.adriacomsoftware.inf.client.smartgwt.validators.iban.AS2ValidatorIBAN;

import java.math.BigDecimal;
import java.util.LinkedHashMap;

import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.types.MultipleAppearance;
import com.smartgwt.client.types.TextMatchStyle;
import com.smartgwt.client.types.TimeDisplayFormat;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.FormItemValueFormatter;
import com.smartgwt.client.widgets.form.FormItemValueParser;
import com.smartgwt.client.widgets.form.fields.ButtonItem;
import com.smartgwt.client.widgets.form.fields.CanvasItem;
import com.smartgwt.client.widgets.form.fields.ComboBoxItem;
import com.smartgwt.client.widgets.form.fields.DateItem;
import com.smartgwt.client.widgets.form.fields.DateRangeItem;
import com.smartgwt.client.widgets.form.fields.DateTimeItem;
import com.smartgwt.client.widgets.form.fields.FloatItem;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.FormItemIcon;
import com.smartgwt.client.widgets.form.fields.IntegerItem;
import com.smartgwt.client.widgets.form.fields.LinkItem;
import com.smartgwt.client.widgets.form.fields.RadioGroupItem;
import com.smartgwt.client.widgets.form.fields.RichTextItem;
import com.smartgwt.client.widgets.form.fields.SectionItem;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.TextAreaItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.TimeItem;
import com.smartgwt.client.widgets.form.fields.ViewFileItem;

public abstract class AS2Field implements AS2FieldInterface {
	public int _type = TEXT;
	public FormItem _editor = null;
	
	public abstract void setEditorProperties(FormItem editorProperties);	
	public abstract void setAttribute(String attribute, Object value);
	public abstract Object getAttribute(String attribute);
	public abstract void setValueMap(LinkedHashMap<String, Object> valueMap);
	public abstract void setValueMap(String... valueMap);
	
	public void setEditor(FormItem editor){
		//used just at F_ITEM_EDITOR initialization time
		_editor = editor;
		setEditorProperties(_editor);
		setAttribute(AS2_TYPE,_type);
	}
	public void setEditorValueMap(LinkedHashMap<String,Object> valueMap) {
		if (_editor instanceof RadioGroupItem) {
			((RadioGroupItem) _editor).setValueMap(valueMap);
		} else if (_editor instanceof ComboBoxItem) {
			((ComboBoxItem) _editor).setValueMap(valueMap);
		} else if (_editor instanceof SelectItem) {
			((SelectItem) _editor).setValueMap(valueMap);
		}else if(_editor!=null)
			_editor.setValueMap(valueMap);
		if(_editor!=null)
			setEditorProperties(_editor);
		setValueMap(valueMap);		
	}
	public void setEditorValueMap(String... valueMap) {
		if (_editor instanceof RadioGroupItem) {
			((RadioGroupItem) _editor).setValueMap(valueMap);
		} else if (_editor instanceof ComboBoxItem) {
			((ComboBoxItem) _editor).setValueMap(valueMap);
		} else if (_editor instanceof SelectItem) {
			((SelectItem) _editor).setValueMap(valueMap);
		}else if(_editor!=null)
			_editor.setValueMap(valueMap);
		if(_editor!=null)
			setEditorProperties(_editor);
		setValueMap(valueMap);		
	}
	public FormItem getEditorProperties(){
		return _editor;
	}
	public void setTitle(String title) {
		setAttribute("title", title);
	}
	
	public void setWidth(Object width){
		//DataSource field does not have width attribute
		setAttribute("width", width);
	}
	/**************** TEXT field ******************/
	protected void createTextField(String name) {
		setEditor(new TextItem(name));
	}
	/**************** TEXTAREA field ******************/
	protected void createTextAreaField(String name) {
		setEditor(new TextAreaItem(name));
	}
	
	/**************** RICHTEXT field ******************/
	protected void createRichTextField(String name) {
		setEditor(new RichTextItem(name));
	}
	/**************** AMOUNT field ******************/
	protected void createAmountField(String name) {
//		/**
//		 * POSTAVLJANJE NUMBERFORMATA!
//		 */
//		final NumberFormat nf = NumberFormat.getCurrencyInstance("",true);
//		nf.setGroupingSeparator(LocaleInfo.getCurrentLocale().getNumberConstants().groupingSeparator());
//		nf.setInputDecimalSeparator(LocaleInfo.getCurrentLocale().getNumberConstants().decimalSeparator());
//		nf.setDecimalSeparator(LocaleInfo.getCurrentLocale().getNumberConstants().decimalSeparator());
//		nf.setCurrencySymbol("");
//		/**
//		 * POSTAVLJANJE GWT NUMBERFORMATA
//		 */
		
		// Formatter for show currency
		FormItemValueFormatter valueFormatter = new FormItemValueFormatter() {
			@Override
			public String formatValue(Object value, Record record,
					DynamicForm form, FormItem item) {
				String formattedValue = null;
				if (value != null) {
					try {
						formattedValue = formatAmountValue(value,_type);
					} catch (final Exception e) {
						formattedValue = value.toString();
					}
				}
				return formattedValue;
			}

		};

		//parser for parse currency
		FormItemValueParser valueParser = new FormItemValueParser() {
			@Override
			public Object parseValue(String value, DynamicForm form,
					FormItem item) {
				if (value != null && value.length() > 0) {
					try {
						return parseAmountValue(value,_type);
					} catch (final Exception e) {
						return value;
					}
				}
				return "";
			}
		};

		FloatItem item = new  FloatItem(name);
		item.setType(FieldType.CUSTOM.getValue());
		item.setTextAlign(Alignment.RIGHT);
		item.setKeyPressFilter("[0-9,-]");
		item.setValueFormatter(valueFormatter);
		item.setEditorValueFormatter(valueFormatter);
		item.setEditorValueParser(valueParser);
		item.setValidateOnExit(true);
		item.setValidateOnChange(false);
		setEditor(item);
//		if(type==LISTGRID_FIELD){ //TODO test
////			//FORMATTER ZA CURRENCY
//			CellFormatter amountCellFormatter = new CellFormatter() {
//				
//				@Override
//				public String format(Object value, ListGridRecord record, int rowNum,
//						int colNum) {
//					String formattedValue = null;
//					if (value != null) {
//						try {
//							formattedValue = AS2ItemFactory.formatAmountValue(value,TYPE);
//	
//						} catch (final Exception e) {
//							formattedValue = value.toString();
//						}
//					}
//					return formattedValue;
//				}
//			};
//			//parser za currency
//			CellEditValueParser amountCellEditValueParser = new CellEditValueParser() {
//				@Override
//				public Object parse(Object value, ListGridRecord record, int rowNum,
//						int colNum) {
//					if (value != null) {
//						try {
//							return AS2ItemFactory.parseAmountValue(value.toString(),TYPE);
//						} catch (final Exception e) {
//							return value.toString();
//						}
//					}
//					return "";
//				}
//			};
//	
//			CellEditValueFormatter amountCellEditValueFormatter = new CellEditValueFormatter() {
//				@Override
//				public Object format(Object value, ListGridRecord record, int rowNum,
//						int colNum) {
//					String formattedValue = null;
//					if (value != null) {
//						try {
//							formattedValue = AS2ItemFactory.formatAmountValue(value,TYPE);
//						} catch (final Exception e) {
//							formattedValue = value.toString();
//						}
//					}
//					return formattedValue;
//				}
//			};
//			((ListGridField) field).setEditValueFormatter(amountCellEditValueFormatter);
//			((ListGridField) field).setEditValueParser(amountCellEditValueParser);
//			((ListGridField) field).setCellFormatter(amountCellFormatter);
//			((ListGridField) field).setValidateOnChange(false);
//		}
	}
	
	public static Object parseAmountValue(String value, int as2_Type) {
		return com.google.gwt.i18n.client.NumberFormat.getDecimalFormat().overrideFractionDigits(getNumOfDecimals(as2_Type)).parse(value);
	}

	public static String formatAmountValue(Object value, int as2_Type) {
		 return com.google.gwt.i18n.client.NumberFormat.getDecimalFormat().overrideFractionDigits(getNumOfDecimals(as2_Type)).format(new BigDecimal(value.toString()));
	}
	
	public static int getNumOfDecimals(int as2_Type) {
		int decimals=2;
		switch (as2_Type) {
		case AMOUNT:
			decimals=2;
			break;
		case AMOUNT_3:
			decimals=3;
			break;
		case AMOUNT_4:
			decimals=4;
			break;
		case AMOUNT_6:
			decimals=6;
			break;
		case AMOUNT_8:
			decimals=8;
			break;
		case AMOUNT_0:
			decimals=0;
			break;
		default:
			decimals=as2_Type;
			break;
		}
		return decimals;
	}
	/**************** INTEGER field ******************/
	protected void createIntegerField(String name) {
		IntegerItem item = new IntegerItem(name);
		item.setKeyPressFilter("[-0-9]");
		setEditor(item);
	}
	/**************** BIG INTEGER field ******************/
	protected void createBigIntegerField(String name) {
		IntegerItem item = new IntegerItem(name);
		item.setKeyPressFilter("[-0-9]");
		item.setTextAlign(Alignment.RIGHT);
		setEditor(item);
	}
	/**************** Link field ******************/
	protected void createLinkField(String name) {
		setEditor(new LinkItem(name));
	}

	/**************** DATE field ******************/
	protected void createDateItem(String name) {
		DateItem item = new DateItem(name);
		item.setUseMask(true);
		item.setUseTextField(true);
		item.setValidateOnExit(true);
		item.setValidateOnChange(true);
		FormItemIcon picker = new FormItemIcon();
		picker.setTabIndex(-1);
		item.setPickerIconProperties(picker); //Tab index
		setEditor(item);
	}
	/**************** DATETIME field ******************/
	protected void createDateTimeItem(String name) {
		//TODO testirati da li radi!
		DateTimeItem item = new DateTimeItem(name);
		item.setUseMask(true);
		item.setUseTextField(true);
		item.setValidateOnExit(true);
		item.setValidateOnChange(true);
		FormItemIcon picker = new FormItemIcon();
		picker.setTabIndex(-1);
		item.setPickerIconProperties(picker); //Tab index
		setEditor(item);
	}
	
	/**************** DATERANGE field ******************/
	protected void createDateRangeItem(String name) {
		DateRangeItem item = new DateRangeItem(name);
		item.setValidateOnExit(true);
		item.setValidateOnChange(true);
		setEditor(item);
	}
	
	/**************** TIME field ******************/
	protected void createTimeItem(String name) {
		TimeItem item = new TimeItem(name);
		item.setUse24HourTime(true);
		item.setUseMask(true);
		item.setUseTextField(true);
		item.setTimeFormatter(TimeDisplayFormat.TOSHORTPADDED24HOURTIME);
		item.setTabIndex(-1);//TODO da li radi
//		((TimeItem)F_ITEM_EDITOR).setValidateOnExit(true);
//		((TimeItem)F_ITEM_EDITOR).setValidateOnChange(true);
//		FormItemIcon picker = new FormItemIcon();
//		picker.setTabIndex(-1);
//		((TimeItem)F_ITEM_EDITOR).setPickerIconProperties(picker); //Tab index
		setEditor(item);
	}
	/**************** COMBO field ******************/
	protected void createComboItem(String name) {		
		ComboBoxItem item = new ComboBoxItem(name);
		item.setCompleteOnTab(true);
		item.setAddUnknownValues(false);
		item.setTextMatchStyle(TextMatchStyle.SUBSTRING);
		item.setFilterLocally(true);
		Criteria criteria = new Criteria("vrsta",name);
		item.setAttribute(AS2_FILTER_CRITERIA, criteria);
		setAttribute(AS2_FILTER_CRITERIA,criteria);
		setEditor(item);
		setEditorValueMap(new LinkedHashMap<String, Object>());
	}
	/**************** Select field ******************/
	protected void createSelectItem(String name) {
		SelectItem item = new SelectItem(name);
		item.setAddUnknownValues(false);
		item.setTextMatchStyle(TextMatchStyle.SUBSTRING);
		item.setFilterLocally(true);
		Criteria criteria = new Criteria("vrsta",name);
		item.setAttribute(AS2_FILTER_CRITERIA, new Criteria("vrsta",name));
		setAttribute(AS2_FILTER_CRITERIA,criteria);
		setEditor(item);
		setEditorValueMap(new LinkedHashMap<String, Object>());
	}
	/**************** OIB field ******************/
	protected void createOibField(String name) {
		TextItem item = new TextItem(name);
		item.setKeyPressFilter("[0-9]");
		item.setWidth(100);
		item.setLength(11);
		setEditor(item);
	}
	/**************** JMBG field ******************/
	protected void createJmbgField(String name) {
		TextItem item = new TextItem(name);
		item.setKeyPressFilter("[0-9]");
		item.setWidth(100);
		item.setLength(13);
		setEditor(item);
	}
	/**************** IBAN field ******************/
	protected void createIbanField(String name) {
		TextItem item = new TextItem(name);
		//http://snipplr.com/view/15322/iban-regex-all-ibans/
//		item.setKeyPressFilter("[a-zA-Z][0-9]");
		item.setWidth(300);
		item.setLength(31);
		AS2ValidatorIBAN iban_validator = new AS2ValidatorIBAN();
		item.setValidators(iban_validator);
		setEditor(item);
	}
	/**************** IMAGE FILE field ******************/
	protected void createImageFileField(String name) {
		//TODO looks like can not be used without smartgwt server
		ViewFileItem item = new ViewFileItem(name);
		item.setShowFileInline(true);
		setEditor(item);
	}
	/**************** OPERATIONS ***********************/
	//TextAreaItem
	public void setHeight(int height) {
		if (_editor instanceof TextAreaItem) {
			((TextAreaItem) _editor).setHeight(height);

		}else{
			_editor.setHeight(height);
		}
		setEditorProperties(_editor);
		setAttribute("height", height);
	}
	
	@SuppressWarnings("unchecked")
	public static LinkedHashMap<String, String> getCacheManagerData(String name){
		if(!AS2CacheManager.getInstance().isEmpty() && AS2CacheManager.getInstance().get(name)!=null)
			return (LinkedHashMap<String, String>) AS2CacheManager.getInstance().get(name);
		else
			return new LinkedHashMap<String, String>();
	}
	//SelectItem //Testirati za DS_FIELD i LG_FIELD
	public void setMultiple(Boolean multiple) {
		if (_editor instanceof SelectItem) {
			((SelectItem) _editor).setMultiple(multiple);
			setEditorProperties(_editor);
			setAttribute("multiple", multiple);

		}
	}		
	//SectionItem  //Testirati za DS_FIELD i LG_FIELD
	public void setSectionItems(String... itemIds) {
		if (_editor instanceof SectionItem) {
			((SectionItem) _editor).setItemIds(itemIds);
			setEditorProperties(_editor);
		}
	}
	public void setDropDownList(String name) {
		Criteria criteria = new Criteria("vrsta",name);
		if(_editor!=null)
			_editor.setAttribute(AS2_FILTER_CRITERIA,criteria);
		setAttribute(AS2_FILTER_CRITERIA,criteria);
	}
	//Just for radio buttons
	public void setVertical(Boolean vertical) {
		if (_editor!=null && _editor instanceof RadioGroupItem) {
			((RadioGroupItem) _editor).setVertical(vertical);
			setEditorProperties(_editor);
		}
	}
	
	// Testirati za DS_FIELD i LG_FIELD
	public void setDefaultValue(String defaultValue) {
		 _editor.setDefaultValue(defaultValue);
		 setAttribute("defaultValue", defaultValue);
		 setEditorProperties(_editor);
	}
	//Testirati za DS_FIELD i LG_FIELD
	public void setMultipleAppearance(MultipleAppearance multipleAppearance) {
		if (_editor instanceof SelectItem) {
			((SelectItem) _editor).setMultipleAppearance(multipleAppearance);
			setEditorProperties(_editor);
			//setAttribute("multiple", multiple);
			setAttribute("multipleAppearance",multipleAppearance == null ? null : multipleAppearance.getValue());
		}
	}
	public void setIcons(FormItemIcon... icons) {
		if(_editor!=null){
			_editor.setIcons(icons);
			setEditorProperties(_editor);
		}
		setAttribute("icons", icons);
	}
	
	 //ButtonItem
	 public void setIcon(String icon) {
		 if(_editor instanceof ButtonItem){
			 setAttribute("icon", icon);
	    }
	 }

	 public void setAutoFit(Boolean autoFit) {
		 if(_editor instanceof ButtonItem){
			 _editor.setAttribute("autoFit", autoFit);
			 setEditorProperties(_editor);
			 setAttribute("autoFit", autoFit);
	    }
	 }

	 //CanvasItem //Testirati za DS_FIELD i LG_FIELD
	 public void setCanvas(Canvas canvas) {
		 if(_editor instanceof CanvasItem){
		        ((CanvasItem)_editor).setCanvas(canvas);
		       setEditorProperties(_editor);
		    }
	   }
	 
	 //LinkItem
	 public void setLinkTitle(String linkTitle) {
		 if(_editor instanceof LinkItem){
			 _editor.setAttribute("linkTitle", linkTitle);
			 setEditorProperties(_editor);
			 setAttribute("linkTitle", linkTitle);
	    }
	 }

	 public void setTarget(String target) {
		 if(_editor instanceof LinkItem){
			 _editor. setAttribute("target", target);
			 setEditorProperties(_editor);
			 setAttribute("target", target);
	    }
	 }
	 
	 public static int getType(String sqlType) {
//		 if(sqlType.equalsIgnoreCase(""+java.sql.Types.INTEGER))
//		 	return INTEGER;
//		 else 
			 return TEXT;
		 //TODO
	}
}
