package hr.adriacomsoftware.inf.client.smartgwt.views;

import hr.adriacomsoftware.inf.client.smartgwt.models.AS2RestJSONDataSource;
import hr.adriacomsoftware.inf.client.smartgwt.types.AS2Field;
import hr.adriacomsoftware.inf.client.smartgwt.types.AS2FormItem;

import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.events.DrawEvent;
import com.smartgwt.client.widgets.events.DrawHandler;
import com.smartgwt.client.widgets.events.KeyPressEvent;
import com.smartgwt.client.widgets.events.KeyPressHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.ValuesManager;
import com.smartgwt.client.widgets.form.fields.ButtonItem;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.layout.VLayout;

public class AS2DynamicForm extends DynamicForm {

	protected DynamicForm _form;
	protected ValuesManager _vm;
	protected VLayout _formLayout;
	protected AS2FormItem[] _fields;

	public AS2DynamicForm() {
		this(null,1, false, "");
	}
	public AS2DynamicForm(int numCols) {
		this(null,numCols, false, "");
	}

	public AS2DynamicForm(ValuesManager vm, int numCols) {
		this(vm, numCols, false, "");
	}

	public AS2DynamicForm(ValuesManager vm,int numCols, boolean isGroup,String groupTitle) {
		this._form=this;
		this.setOverflow(Overflow.AUTO);//overflow:auto Layout will scroll if members exceed its specified size
		this.setHeight100();
//		this.setWidth("90%");

		//Datasource se namjesti u FORM klasi
		this.setIsGroup(isGroup);
		this.setGroupTitle(groupTitle);
		this.setNumCols(numCols);
		this.setRequiredTitleSuffix("<b style=\"color:red;\">*</b>");
		this.setSaveOnEnter(false);
		this.setAutoFocus(true);
		if(vm!=null)
		this.setValuesManager(vm);
	}
	public void setStyleNameGray(){
		this.setStyleName("crm-dynamicForm");
	}
	public static String getStyleNameGray(){
		return "crm-dynamicForm";
	}
	public void setAutoHeightDynamicForm() {
		this.setHeight("100");
		this.setAutoHeight();
		this.setOverflow(Overflow.VISIBLE);
	}

	public AS2FormItem getFieldAs2(String name) {
		AS2FormItem item = null;
		for(AS2FormItem field: _fields){
			if(field.getField().getName().equals(name)){
				item=field;
				break;
			}
		}
		return item;
	}
	
	public AS2FormItem[] getFieldsAs2() {
		return _fields;
	}
	
	//Postavljanje vrijednosti polja kojega se editira
	public void setFieldValue(String name, Object value) {
		if(this != null && this.getField(name)!=null)
			this.getField(name).setValue(value);
	}
	
	//Kada koristimo AS2ItemFactory TODO brisi kada nestane AS2FormItem1
//	public void setFields(AS2FormItem1... fields) throws IllegalStateException {
//		_fields=fields;
//		FormItem[] fItems= new FormItem[fields.length];
//		for(int i=0;i<fields.length;i++){
//			fItems[i]=(fields[i].getField());
//		}
//		super.setFields(fItems);
//	}
	//Kada koristimo AS2ItemFactory
		public void setFields(AS2FormItem... fields) throws IllegalStateException {
			_fields=fields;
			FormItem[] fItems= new FormItem[fields.length];
			for(int i=0;i<fields.length;i++){
				fItems[i]=(fields[i].getField());
			}
			super.setFields(fItems);
		}
	
	public void setRequiredFields(FormItem... fields) {
		for(FormItem field:fields){
			field.setAttribute("required", true);
		}
	}
	
	public void setDropDownModel(DataSource dataSource,AS2FormItem... fields) {
		AS2RestJSONDataSource ds = (AS2RestJSONDataSource)dataSource;
		for(int i=0;i<fields.length;i++){
			Criteria criteria = null;
			if(fields[i].getAttribute(AS2Field.AS2_FILTER_CRITERIA)==null && this.getDataSource()!=null){
				DataSource listgrid_model = this.getDataSource();
				criteria =  (Criteria) listgrid_model.getField(fields[i].getField().getName()).getAttributeAsObject(AS2FormItem.AS2_FILTER_CRITERIA);
				fields[i].setAttribute(AS2FormItem.AS2_FILTER_CRITERIA,criteria);
			}else{
				criteria = (Criteria)fields[i].getAttribute(AS2FormItem.AS2_FILTER_CRITERIA);
			}
			criteria.getAttribute("vrsta");
			if(ds.getCache(criteria.getAttribute("vrsta"))!=null){
				if(fields[i].getEditorProperties()!=null){
					fields[i].getEditorProperties().setValueMap(ds.getCache(criteria.getAttribute("vrsta")));
					fields[i].getField().setEditorProperties(fields[i].getEditorProperties());
				}
				fields[i].getField().setValueMap(ds.getCache(criteria.getAttribute("vrsta")));
			}
			if( ds.getCache(criteria.getAttribute("vrsta"))!=null)
				fields[i].getField().setValueMap(ds.getCache(criteria.getAttribute("vrsta")));
		}		
		
	}
	
	/*
	public void setOptionDataSource(DataSource dataSource,AS2FormItem1... fields) {
		for(int i=0;i<fields.length;i++){
			fields[i].getField().getName();
			if(fields[i].getField().getType()==null &&
					this.getDataSource()!=null && fields[i].getPickListCriteriaAs2()==null){
				DataSource ds = this.getDataSource();
				if(ds.getField(fields[i].getField().getName()).getAttributeAsObject("editorProperties") instanceof JavaScriptObject)
					fields[i].F_ITEM_EDITOR = new FormItem(ds.getField(fields[i].getField().getName()).getAttributeAsJavaScriptObject("editorProperties"));
				else
					fields[i].F_ITEM_EDITOR = (FormItem)ds.getField(fields[i].getField().getName()).getAttributeAsObject("editorProperties");
				fields[i].setAttribute(AS2ItemFactory.AS2_FILTER_CRITERIA, ds.getField(fields[i].getField().getName()).getAttributeAsObject(AS2ItemFactory.AS2_FILTER_CRITERIA));
				fields[i].getField().setOptionCriteria((Criteria)ds.getField(fields[i].getField().getName()).getAttributeAsObject(AS2ItemFactory.AS2_FILTER_CRITERIA));
				
			}
			if(fields[i].F_ITEM_EDITOR instanceof RadioGroupItem){
				fields[i].fetchValueMapFromCache(dataSource,this.getID());
			}else{
//				if smartgwt
				fields[i].F_ITEM_EDITOR.setOptionDataSource(dataSource);
				fields[i].getField().setEditorProperties(fields[i].F_ITEM_EDITOR);
				fields[i].getField().setOptionDataSource(dataSource);
//				fields[i].setOptionDataSource(dataSource);
				
			}
		}
	}*/
	
	//Novi način
	public void setRequiredFields(AS2FormItem... fields) {
		for(AS2FormItem field:fields){
			field.getField().setAttribute("required", true);
		}
	}
	
	public void focusInItem(final AS2FormItem formItem) {
		this.addDrawHandler(new DrawHandler() {
			@Override
			public void onDraw(DrawEvent event) {
				AS2DynamicForm.this.focusInItem(formItem.getField());
			}
		});
	}
	
	public void setVisibleFields(boolean visible, AS2FormItem... fields) {
		for(AS2FormItem field:fields){
			field.getField().setVisible(visible);
		}
	}
	public void setCanEditFields(boolean canEdit, AS2FormItem... fields) {
		for(AS2FormItem field:fields){
			field.getField().setCanEdit(canEdit);
		}
	}
	
	public void setDefaultButton(final String name){
		this.addKeyPressHandler(getKeyPressHandler(_form.getField(name)));
	}
	
	public void setDefaultButton(final IButton button){
		this.addKeyPressHandler(getKeyPressHandler(button));
	}
	
	private com.smartgwt.client.widgets.events.KeyPressHandler  getKeyPressHandler(final Object button) {
		return new KeyPressHandler() {
			@Override
			public void onKeyPress(KeyPressEvent event) {
				if (event.getKeyName() != null) {
					if (event.getKeyName().equals("Enter")) {
						if(button instanceof IButton)
							((IButton)button).fireEvent(new com.smartgwt.client.widgets.events.ClickEvent(((IButton)button).getJsObj()));
						else if(button instanceof FormItem){//TODO testirarti
							((ButtonItem)button).fireEvent(new com.smartgwt.client.widgets.events.ClickEvent(((ButtonItem)button).getJsObj()));
						}else{
							
						}
					}
				}
				
			}
		};
	}
}
