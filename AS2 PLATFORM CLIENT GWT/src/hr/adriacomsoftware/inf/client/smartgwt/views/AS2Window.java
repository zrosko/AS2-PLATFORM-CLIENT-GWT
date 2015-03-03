package hr.adriacomsoftware.inf.client.smartgwt.views;

import hr.adriacomsoftware.app.client.smartgwt.resources.AS2Resources;
import hr.adriacomsoftware.inf.client.smartgwt.types.AS2FormItem;
import hr.adriacomsoftware.inf.client.smartgwt.types.AS2FormItemIcon;
import hr.adriacomsoftware.inf.client.smartgwt.types.listgrid.rolloverbutton.AS2RolloverButtonFactory;
import hr.adriacomsoftware.inf.client.smartgwt.views.fileupload.AS2FileUploadDialog;
import hr.adriacomsoftware.inf.client.smartgwt.views.rosko.AS2Layout;
import hr.adriacomsoftware.inf.client.smartgwt.views.rosko.AS2View2;

import java.util.HashMap;

import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.DSOperationType;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.ImgButton;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.events.CloseClickEvent;
import com.smartgwt.client.widgets.events.CloseClickHandler;
import com.smartgwt.client.widgets.events.KeyPressEvent;
import com.smartgwt.client.widgets.events.KeyPressHandler;
import com.smartgwt.client.widgets.form.ValuesManager;
import com.smartgwt.client.widgets.form.fields.PickerIcon;
import com.smartgwt.client.widgets.form.fields.events.FormItemClickHandler;
import com.smartgwt.client.widgets.form.fields.events.FormItemIconClickEvent;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.layout.HLayout;

public abstract class AS2Window extends Window implements AS2Layout {
//	implements AsyncCallback <HashMap<String,Object>> {
//	protected static final GreetingServiceAsync server = GWT.create(GreetingService.class);
//	protected static boolean view_ready = false;
	protected static int NUMBER_OF_COLUMNS = 6;
	public DataSource _dataSource;
	protected Record _record; //selected record from parent view
	protected AS2View2 _parent_view = null;
	protected Label _window_form_title;
	public AS2DynamicForm _form;
	public AS2ListGrid _listGrid;
	//public FacetChart _chart = new FacetChart();  //TODO brisi
	public ValuesManager _vm;
	protected IButton _button_spremi;
	protected IButton _button_novo;
	protected IButton _button_brisi;
	protected IButton _button_izlaz;
	protected IButton _button_print;
	protected HLayout _buttons_layout;
	protected AS2FormItemIcon _privitakDodaj;
	protected AS2FormItemIcon _privitakOtvori;
	protected AS2FormItemIcon _privitakBrisi;
	protected PickerIcon _lookupOib;
	protected AS2FormItem _horizontal_line;
	public boolean _refresh = false;
	protected boolean _new_record = true;
//	private ToolStripButton toolbarSearch;
	private HLayout rollOverCanvas;
	protected ImgButton rolloverEditButton;
	protected ImgButton rolloverDeleteButton;


	public AS2Window(){
		//super();
		defaultWindowPreferences();	
		customWindowPreferences();
//		this.centerInPage();
		defaultWindowHandlers();
		customWindowHandlers();
	}
	
	public AS2Window(AS2View2 parent, Record record) {
		this();
		_parent_view = parent;
		_record = record;
		createComponents();
	}
	
	public AS2Window(AS2View2 parent, Record record,boolean createComponents) {
		this();
		_parent_view = parent;
		_record = record;
		if(createComponents)
			createComponents();
	}


	public void defaultWindowPreferences(){
		this.setHeight("90%");
		this.setWidth("90%");
		this.setShowMinimizeButton(false);
		this.setIsModal(true);
		this.setShowTitle(true);
		this.setTitle("");
		this.setShowShadow(true);
		this.setShowResizeBar(true);
		this.setShowModalMask(true);
//		this.centerInPage();
		this.setAutoCenter(true);
//		this._buttons_layout = new HLayout(5);
//		this._buttons_layout.setBorder("1px solid #7598c7");
//		this._buttons_layout.setAlign(Alignment.RIGHT);
//		this._buttons_layout.setStyleName("crm-dynamicForm-buttonsLayout");
		this._window_form_title = new Label();
		this._window_form_title.setBorder("1px solid #7598c7");
		this._window_form_title.setHeight(10);
		this._window_form_title.setStyleName("crm-dynamicForm-titlelabel");
		_horizontal_line = new AS2FormItem("horizontal_line",AS2FormItem.FORM_SEPARATOR,"",NUMBER_OF_COLUMNS,null,true,false,true);
	}
	public String getWindowFormTitleNew() {
		return "<b style=\"color:black;font-size:10pt;\">New record</b>";
	}
	public String getWindowFormTitleUpdate() {
		return "<b style=\"color:black;font-size:10pt;\">Update record: "
				+ "</b></br>Details: ";
	}
	public String getWindowFormTitle() {
		if(_new_record)
			return getWindowFormTitleNew();
		else
			return getWindowFormTitleUpdate();
	}
	public void customWindowPreferences(){}
	public void defaultWindowHandlers(){
		this.addCloseClickHandler(new CloseClickHandler() {
            public void onCloseClick(CloseClickEvent event) {
            	closeWindow(_refresh);
            }
        });
		this.addKeyPressHandler(new KeyPressHandler() {

			@Override
			public void onKeyPress(KeyPressEvent event) {
				if (event.getKeyName() != null) {
					if (event.getKeyName().equals("Escape")) {
						closeWindow(_refresh);
					}if (event.getKeyName().equals("Enter")) {
						onEnter();
					}
				}

			}
		});
	}
	public void customWindowHandlers(){}
	protected void onEnter(){}
	
	public void windowLayout(){
		this.addItem(_window_form_title);
		if(_form!=null)
			this.addItem(_form);
		if(_listGrid!=null){
			this.addItem(_listGrid);
			if(_listGrid.getStatusBar()!=null)
				this.addItem(_listGrid.getStatusBar());
		}
		this.addItem(getButtonsLayout());
		this.show();
	}
	public AS2DynamicForm getDynamicForm() {
		return null;
	}
	public AS2ListGrid getListGrid() {
		return null;
	}

	protected HLayout getButtonsLayout() {
		this._buttons_layout = new HLayout(5);
		this._buttons_layout.setAlign(Alignment.RIGHT);
		this._buttons_layout.setStyleName("crm-dynamicForm-buttonsLayout");
		this._buttons_layout.setWidth100();
		this._buttons_layout.setAutoHeight();
		this._buttons_layout.setShowEdges(true);
		prepareFormButtons();
		getFormButtons();
		return _buttons_layout;
	}

	protected void closeWindow(boolean refresh){
		if(refresh){
			if(_parent_view!=null && _parent_view._listGrid!=null){
					_parent_view._listGrid.refresh();
			}
		}
		this.markForDestroy();
	}
	
	public void refreshListGrid(){
		if(_listGrid!=null)
			_listGrid.refresh();
		else if(_parent_view!=null && _parent_view._listGrid!=null){
			_parent_view._listGrid.refresh();
		}
	}
	
	public DataSource getSifrarnikModel(){
		return new DataSource();
	}
	public DataSource getModel(){
		return new DataSource();
	}
	
	//Poziva AS2FileuUloadDialog iz drugog viewa
	public void fileUploadCompleteCallback(AS2FileUploadDialog fileUploadDialog, String fileName, String pk) {
		fileUploadCompleteCallbackCustom(fileUploadDialog,fileName,pk);
		if(_parent_view._listGrid!=null)
			fileUploadDialog.destroy(true);
		else{
			fileUploadDialog.destroy(false);
		}
	}

	public void fileUploadCompleteCallbackCustom(AS2FileUploadDialog fileUploadDialog, String fileName, String pk) {
	}
	
	//Poziva TraziWindow iz drugog viewa
	public void getValuesFromTrazi(Record selectedRecord) {}

	//Postavljanje vrijednosti polja kojega se editira iz drugog viewa
	public void setFieldValue(String name, Object value) {
		if(_listGrid!=null)
		_listGrid.setFieldValue(name, value);
		if(_form!=null)
		_form.setFieldValue(name, value);
	}
	protected Criteria initCriteria(){
		return new Criteria();
	}
	
	protected String getInitRemoteMethod(){
		return "";
	}
	
	public void createComponents() {
		getLookUpsAndButtons();
		_dataSource = getModel();
		_buttons_layout = getButtonsLayout();
		if (_record == null){
			_new_record=true;
			_record=new Record();
			_form = getDynamicForm();
		}else{
			_new_record=false;
			_form = getDynamicForm();
			_form.fetchData(initCriteria(),new DSCallback() {
				@Override
				public void execute(DSResponse dsResponse, Object data, DSRequest dsRequest) {
					initCallback(dsResponse,data,dsRequest);					
				}
			},new DSRequest(DSOperationType.CUSTOM,getInitRemoteMethod()));
		}
		_window_form_title.setContents(getWindowFormTitle());
		windowLayout();
	}
	
	protected void initCallback(DSResponse dsResponse, Object data,	DSRequest dsRequest) {
		_record = dsResponse.getDataAsRecordList().get(0);
		_form.editRecord(_record);
		
	}
	public void getLookUpsAndButtons() {
		_privitakDodaj = new AS2FormItemIcon(AS2FormItemIcon.ATTACHMENT_UPLOAD_ICON);
		_privitakDodaj.addFormItemClickHandler(new FormItemClickHandler() {
			@Override
			public void onFormItemClick(FormItemIconClickEvent event) {
				actionUploadFile(event);
			}
		});
		_privitakOtvori = new AS2FormItemIcon(AS2FormItemIcon.ATTACHMENT_DOWNLOAD_ICON);
		_privitakOtvori.addFormItemClickHandler(new FormItemClickHandler() {
			@Override
			public void onFormItemClick(FormItemIconClickEvent event) {
				actionDownloadFile(event);
			}
		});
		_privitakBrisi = new AS2FormItemIcon(AS2FormItemIcon.ATTACHMENT_DELETE_ICON);
		_privitakBrisi.addFormItemClickHandler(new FormItemClickHandler() {
			@Override
			public void onFormItemClick(FormItemIconClickEvent event) {
				actionDeleteFile(event);
			}
		});
		_lookupOib = new PickerIcon(PickerIcon.SEARCH,
				new FormItemClickHandler() {
					public void onFormItemClick(FormItemIconClickEvent event) {
						actionLookupOib(event);
					}
				});
	}

	protected void actionLookupOib(FormItemIconClickEvent event){		
	}
	
	protected void actionUploadFile(FormItemIconClickEvent event) {
		if(_form.validate()){
			AS2FileUploadDialog upload = new AS2FileUploadDialog();
			upload.uploadFile(this, getUploadParameters());		
		}
	}
	//TODO da li radi?
	protected void actionDownloadFile(FormItemIconClickEvent event){
		AS2FileUploadDialog download = new AS2FileUploadDialog();
		download.downloadFile(getDownloadParameters());		
	}
	protected HashMap<String, Object> getUploadParameters() {
		return new HashMap<String,Object>();
	}
	protected HashMap<String, Object> getDownloadParameters() {
		return new HashMap<String,Object>();
	}
	
	protected void actionDeleteFile(FormItemIconClickEvent event){
		//TODO
	}
	
	protected void prepareFormButtons() {
		// define Buttons
		_button_novo = new IButton("Novo");
		_button_novo.setIcon(AS2Resources.ADD_FILES_PATH);
		_button_novo.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				actionNew(event);
			}
		});
		_button_spremi = new IButton("Spremi");
		_button_spremi.setIcon(AS2Resources.SAVE_PATH);
		_button_spremi.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				actionSave(event);
			}
		});
		_button_izlaz = new IButton("Izlaz");
		_button_izlaz.setIcon(AS2Resources.CANCEL_PATH);
		_button_izlaz.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				actionClose(event);
			}
		});
	}
	protected void getFormButtons() {
		_buttons_layout.setMembers(_button_novo,_button_spremi,_button_izlaz);
	}
	protected void actionClose(ClickEvent event){
		closeWindow(_refresh);
	}
	protected void actionNew(ClickEvent event){
		_form.editNewRecord();
	}
	protected void actionSave(ClickEvent event){
		if(_form.getField("ispravno")!=null)
			_form.getField("ispravno").setValue(1);
		_form.saveData(new DSCallback() {
			@Override
			public void execute(DSResponse dsResponse, Object data, DSRequest dsRequest) {
				closeWindow(true);
				//_parent_view.getSifrarnikModel().invalidateCache();
			}
		});
		
	}
	
	/*********** ROLLOVER ************/
	protected HLayout getRollOver(final ListGrid listGrid) {
		HLayout rollover = new HLayout(3);
		rollover.setSnapTo("TR");
		rollover.setWidth(50);
		rollover.setHeight(listGrid.getCellHeight());
		prepareRollOverButtons(listGrid);
		getRollOverButtons(rollover,listGrid);
		return rollover;
	}
	//default buttons implementation
	protected void prepareRollOverButtons(ListGrid listGrid){
		rolloverEditButton = AS2RolloverButtonFactory.getRolloverButton(
				AS2RolloverButtonFactory.EDIT_BUTTON, listGrid, null);
		rolloverDeleteButton = AS2RolloverButtonFactory.getRolloverButton(
				AS2RolloverButtonFactory.DELETE_BUTTON, listGrid, null);
	}
	
	public Canvas getRolloverCanvas(Integer rowNum, Integer colNum, ListGrid listgrid) {
		AS2RolloverButtonFactory._rollOverRecord = listgrid.getRecord(rowNum);
		if(AS2RolloverButtonFactory._rollOverRecord.getAttributeAsObject("groupValue")==null){
			if (rollOverCanvas == null) {
				rollOverCanvas = getRollOver(listgrid);
			}
			return rollOverCanvas;
		}else{
			HLayout layout = new HLayout();
			layout.setWidth(1);
			layout.setHeight(1);
			return layout;
		}
		
	}
	
	protected void getRollOverButtons(HLayout rollover,ListGrid listGrid) {
		rollover.addMember(rolloverEditButton);
		rollover.addMember(rolloverDeleteButton);
	}
	
}



