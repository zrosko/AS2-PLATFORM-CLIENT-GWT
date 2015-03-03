package hr.adriacomsoftware.inf.client.smartgwt.views.rosko;
//http://designingwebinterfaces.com/designing-web-interfaces-12-screen-patterns
import hr.adriacomsoftware.app.client.smartgwt.resources.AS2Resources;
import hr.adriacomsoftware.inf.client.smartgwt.desktop.AS2CacheManager;
import hr.adriacomsoftware.inf.client.smartgwt.desktop.views.AS2GwtDesktop;
import hr.adriacomsoftware.inf.client.smartgwt.desktop.views.AS2ListGridToolBar;
import hr.adriacomsoftware.inf.client.smartgwt.models.AS2RestJSONDataSource;
import hr.adriacomsoftware.inf.client.smartgwt.types.AS2Field;
import hr.adriacomsoftware.inf.client.smartgwt.types.AS2FormItem;
import hr.adriacomsoftware.inf.client.smartgwt.types.listgrid.rolloverbutton.AS2RolloverButtonFactory;
import hr.adriacomsoftware.inf.client.smartgwt.views.AS2DynamicForm;
import hr.adriacomsoftware.inf.client.smartgwt.views.AS2DynamicFormButtons;
import hr.adriacomsoftware.inf.client.smartgwt.views.AS2ListGrid;
import hr.adriacomsoftware.inf.client.smartgwt.views.AS2WaitPopup;
import hr.adriacomsoftware.inf.client.smartgwt.views.fileupload.AS2FileUploadDialog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.smartgwt.client.core.Rectangle;
import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.data.RestDataSource;
import com.smartgwt.client.types.DSDataFormat;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.ImgButton;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.fields.PickerIcon;
import com.smartgwt.client.widgets.form.fields.events.FormItemClickHandler;
import com.smartgwt.client.widgets.form.fields.events.FormItemIconClickEvent;
import com.smartgwt.client.widgets.form.fields.events.KeyPressEvent;
import com.smartgwt.client.widgets.form.fields.events.KeyPressHandler;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.toolbar.RibbonBar;
import com.smartgwt.client.widgets.toolbar.ToolStrip;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;

public abstract class AS2View2 extends VLayout implements AS2Layout {
	protected static final String CONTEXT_AREA_WIDTH = "*";
	private static final String TOOLBAR_HEIGHT = "25px";
	private static final String TOOLSTRIP_WIDTH = "*";
	public ToolStripButton toolbarAdd;
	public ToolStripButton toolbarPreview;
	public ToolStripButton toolbarAttachement;
	public ToolStripButton toolbarDelete;
	public ToolStripButton toolbarFilter;
	public ToolStripButton toolbarRefresh;
	public ToolStripButton toolbarSearch;
	public ToolStripButton seventh;
	public ToolStripButton eight;
	public ToolStripButton ninth;
//	protected static final String DOCUMENT_NEW_TOOLBAR_BUTTON = AS2Resources.INSTANCE.toolbar_document_new_icon().getSafeUri().asString();
//	protected static final String DOCUMENT_EDIT_TOOLBAR_BUTTON = AS2Resources.INSTANCE.toolbar_document_edit_icon().getSafeUri().asString();
//	protected static final String CREDIT_CARD_TOOLBAR_BUTTON = AS2Resources.INSTANCE.toolbar_credit_card_icon().getSafeUri().asString();
//	protected static final String SCORE_TOOLBAR_BUTTON = AS2Resources.INSTANCE.toolbar_score_icon().getSafeUri().asString();
//	protected static final String STATUS_TOOLBAR_BUTTON = AS2Resources.INSTANCE.toolbar_status_icon().getSafeUri().asString();
//	protected static final String ATTACHEMENT_BUTTON = AS2Resources.INSTANCE.toolbar_attachement_icon().getSafeUri().asString();
//	protected static final String REFRESH_TOOLBAR_BUTTON = AS2Resources.INSTANCE.toolbar_refresh_icon().getSafeUri().asString();
//	protected static final String SEARCH_TOOLBAR_BUTTON = AS2Resources.INSTANCE.toolbar_search_icon().getSafeUri().asString();
//	protected static final String DELETE_TOOLBAR_BUTTON = AS2Resources.INSTANCE.toolbar_delete_icon().getSafeUri().asString();
//	protected static final String FILTER_TOOLBAR_BUTTON = AS2Resources.INSTANCE.toolbar_filter_icon().getSafeUri().asString();
	protected static int number_of_columns = 4;
	protected boolean use_search_form = false;
	protected boolean use_form = true;
	protected boolean use_listgrid = true;
	//	protected boolean use_sifrarnik = false;
	protected boolean use_cache_manager = false;
	protected DataSource _dataSource;
	public AS2ListGrid _listGrid;
	public AS2DynamicForm _form;
	protected AS2DynamicForm _searchForm;
	protected HLayout _toolBar;
	protected RibbonBar _ribbonBar;
	protected ToolStrip _toolStrip;
	protected Label _window_form_title;
	protected AS2FormItem _horizontal_line;
	private AS2WaitPopup popup;
	private HLayout rollOverCanvas;
	protected ImgButton rolloverEditButton;
	protected ImgButton rolloverDeleteButton;
	protected Record _record;
	//Search view part
	protected AS2FormItem search;
	protected PickerIcon _advanced_search;
	protected PickerIcon _search;

	//

	public AS2View2() {
		defaultWindowPreferences();
		customWindowPreferences();
		defaultWindowHandlers();
		customWindowHandlers();
	}
	
	public AS2View2(String name) {

		this(name, new Record());
//		this();
//		this.setID(name);
//		//TODO to remove or do the same in AS2Window
//		if(use_cache_manager){
//			fetchCacheData();
//		}else{
//			createComponents();
//		}
		
	}
	//TODO zamjeni Record sa ?
	public AS2View2(String name, Record record) {
		this();
		//this.setID(name); TODO ? javalja greško bound componente
		getAdditionalData(record);
		if(use_cache_manager){
			fetchCacheData();
		}else{
			createComponents();
		}
	}
	
	protected void getAdditionalData(Record record) {}

	@Override
	public void defaultWindowPreferences() {
		// initialise the layout container
		this.setStyleName("crm-ContextArea");
		this.setWidth(CONTEXT_AREA_WIDTH);
	}

	private void fetchCacheData() {
		AS2CacheManager.getInstance().clear();
		popup = new AS2WaitPopup();
		popup.show(AS2GwtDesktop._messages.listGrid_loadingDataMessage());
		getSifrarnikModel().fetchData(new Criteria(), getSifrarnikServerCallback(false));
	}

	private DSCallback getSifrarnikServerCallback(final boolean update) {
		return 	new DSCallback() {
			public void execute(DSResponse response, Object rawData,
					DSRequest request) {		
				if(response.getStatus()>=0){
					RecordList records = response.getDataAsRecordList();
					AS2CacheManager.getInstance().fillCacheWithData(records);
					if(!update)
						createComponents();
					popup.hideFinal();

				}else{
					popup.hideFinal();
				}
			}
		};
	}
	@Override
	public void createComponents() {
		getLookUpsAndButtons();
		_dataSource = getModel();
		// define ToolBar
		// initialise the ToolStrip
		prepareToolbarButtons();
		_toolStrip = getToolStrip();
		if (_toolStrip != null) {
			_toolBar = getToolBar();
		}
		// initialise the RibbonBar
		_ribbonBar = getRibbonBar();// TODO
		if (_ribbonBar != null) {
			GWT.log("init RibbonBar()...", null);
			_ribbonBar.setHeight(TOOLBAR_HEIGHT);
			_ribbonBar.setWidth(TOOLSTRIP_WIDTH);
		}
		showWidgets();
		windowLayout();

	}
	private HLayout getToolBar() {
		HLayout toolBar = new HLayout();
		// initialise the ToolBar layout container
		toolBar.setStyleName("crm-ToolBar");
		toolBar.setHeight(TOOLBAR_HEIGHT);
		GWT.log("init ToolBar()...", null);
		_toolStrip.setHeight(TOOLBAR_HEIGHT);
		_toolStrip.setWidth(TOOLSTRIP_WIDTH);
		toolBar.setMembers(_toolStrip);
		return toolBar;
	}

	//protected abstract void initializeView(); -> customWindowPreferences
	public void showWidgets() {
//		if (use_listgrid && !use_form) {
//			_listGrid = getListGrid();
//		}
//		if(use_form && !use_listgrid){
//			_form = getDynamicForm();
//		}
//		if (use_form && use_listgrid) {
//			_form = getDynamicForm();
//			_listGrid = getListGrid();
//		}

		if (use_search_form) {
			_searchForm = getSearchForm();		
		}
		if (use_form) {
			_form = getDynamicForm();		
		}
		if (use_listgrid) {
			_listGrid = getListGrid();
		}
		
		if (!use_form && !use_listgrid) {
			_form = getDynamicForm();
		}
	}
	
	@Override
	public void windowLayout() {
		if (use_listgrid && !use_form) {
			if(_toolStrip!=null)
				this.addMembers(_toolBar, _listGrid,_listGrid.getStatusBar());
			else if(_ribbonBar!=null)
				this.addMembers(_ribbonBar, _listGrid,_listGrid.getStatusBar());
			else
				this.addMembers(_listGrid,_listGrid.getStatusBar());

		}
		if(use_form && !use_listgrid){
			if(_toolStrip!=null)
				this.addMembers(_toolBar, _form);
			else if(_ribbonBar!=null)
				this.addMembers(_ribbonBar, _form);
			else
				this.addMembers(_form);

		}
		if (use_search_form) {
			this.addMember(_searchForm);
		}
		if (use_form && use_listgrid) {
			this.addMembers(new AS2ListGridToolBar(_listGrid, null), _listGrid,
					_listGrid.getStatusBar(),_form, new AS2DynamicFormButtons(_listGrid, null));
		}
	}
	//TODO brisati
	protected void fillCacheFromView(){
		LinkedHashMap<String,LinkedHashMap<String,String>> map = getSifrarnikValueMaps();
		for(String fieldName:map.keySet()){
			AS2CacheManager.getInstance().put(fieldName,
					map.get(fieldName));
		}
	}
	//Podatke za cache definiramo lokalno u viewu preko ove metode. Podaci se koriste za combobox ili radiogroupiteme u obliku imePolja={1-naziv}
	protected LinkedHashMap<String, LinkedHashMap<String, String>> getSifrarnikValueMaps() {
		return null;
	}

	protected AS2FormItem horizontalLine() {
		_horizontal_line = new AS2FormItem("horizontal_line",AS2Field.FORM_SEPARATOR,"",number_of_columns,null,true,false,true);
		return _horizontal_line;
	}
	protected ToolStrip getToolStrip(){
		ToolStrip toolStrip = new ToolStrip();
		toolStrip.addButton(toolbarAdd);
		toolStrip.addButton(toolbarPreview);
		toolStrip.addButton(toolbarAttachement);
		toolStrip.addSeparator();
		toolStrip.addButton(toolbarFilter);
		toolStrip.addButton(toolbarRefresh);
		toolStrip.addButton(toolbarSearch);
		return toolStrip;
	}

	protected RibbonBar getRibbonBar() {
		return null;
	}
	public DataSource getModel(){
		RestDataSource client_dataSource = new RestDataSource();
		client_dataSource.setDataURL(AS2RestJSONDataSource.DEFAULT_DATA_URL);
		client_dataSource.setDataFormat(DSDataFormat.JSON);
		return client_dataSource;
	}

	public DataSource getSifrarnikModel(){
		return null;
	};
	
	public AS2DynamicForm getDynamicForm(){
		return null;
	}

	public AS2ListGrid getListGrid(){
		return null;
	}

	@Override
	public void customWindowPreferences() {
		
	}
	@Override
	public void defaultWindowHandlers() {
		
	}
	@Override
	public void customWindowHandlers() {
		
	}

	@Override
	public void getLookUpsAndButtons() {
		
	}
	
	//Poziva TraziWindow iz drugog viewa
	public void getValuesFromTrazi(Record selectedRecord) {}
	
	//Postavljanje vrijednosti polja kojega se editira iz drugog viewa
	public void setFieldValue(String name, Object value) {
		_listGrid.setFieldValue(name, value);
		_form.setFieldValue(name, value);
	}
	
	public void downloadFile(Record record) {
		AS2FileUploadDialog download = new AS2FileUploadDialog();
		download.downloadFile(getDownloadParameters(record));
	}
	
	protected HashMap<String, Object> getDownloadParameters(Record record) {
		return new HashMap<String,Object>();
	}
	
	protected void prepareToolbarButtons() {
		// initialise the buttons
		toolbarAdd = new ToolStripButton();
		toolbarAdd.setTitle(AS2GwtDesktop._messages.toolbar_new_document());
		toolbarAdd.setIcon(AS2Resources.ADD_FILES_PATH);
		toolbarAdd.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				_listGrid.deselectAllRecords();
				actionToolbarAdd(event);
			}
		});
		toolbarPreview = new ToolStripButton();
		toolbarPreview.setTitle(AS2GwtDesktop._messages.toolbar_preview());
		toolbarPreview.setIcon(AS2Resources.TOOLBAR_DOCUMENT_EDIT_ICON_PATH);
		toolbarPreview.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				if (_listGrid.getSelectedRecord() == null) {
					SC.warn("Odaberite dokument");
				} else{
					actionToolbarPreview(event);
				}
			}
		});
		toolbarAttachement = new ToolStripButton();
		toolbarAttachement.setTitle(AS2GwtDesktop._messages.toolbar_attachement());
		toolbarAttachement.setIcon(AS2Resources.TOOLBAR_ATTACHEMENT_ICON);
		toolbarAttachement.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				if (_listGrid.getSelectedRecord() == null) {
					SC.warn("Odaberite dokument");
				}else{
					actionToolbarAttachement(event);
				}
			}
		});
		toolbarDelete = new ToolStripButton();
		toolbarDelete.setIcon(AS2Resources.TOOLBAR_DELETE_ICON_PATH);
		toolbarDelete.setTooltip(AS2GwtDesktop._messages.listGrid_deleteRecordContextMenuItemTitle());
		toolbarDelete.addClickHandler(new ClickHandler() {
			public void onClick(final ClickEvent event){
				
				String text="Da li ste sigurni da želite obrisati dokument?";
				if(_listGrid.getSelectedRecords().length!=1)
					text="Da li ste sigurni da želite obrisati označene dokumente?";
				SC.confirm("Upozorenje!", text,
						new BooleanCallback() {
							@Override
							public void execute(Boolean value) {
								if (value != null && value && _listGrid!=null) {
									actionToolbarDelete(event,value);
								}
							}
						});

			}
		});
		toolbarFilter = new ToolStripButton();
		toolbarFilter.setIcon(AS2Resources.TOOLBAR_FILTER_ICON_PATH);
		toolbarFilter.setTooltip(AS2GwtDesktop._messages.listGrid_filterButtonPrompt());
		toolbarFilter.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event){
				if(!_listGrid.getShowFilterEditor()){
					_listGrid.setShowFilterRow(true,true);
				}else{
					_listGrid.setShowFilterRow(false,false);
				}
			}
		});
		toolbarRefresh = new ToolStripButton();
		toolbarRefresh.setIcon(AS2Resources.TOOLBAR_REFRESH_ICON_PATH);
		toolbarRefresh.setTooltip(AS2GwtDesktop._messages.toolbar_refresh());
		toolbarRefresh.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event){
				_listGrid.refresh();
			}
		});
		toolbarSearch = new ToolStripButton();
		toolbarSearch.setIcon(AS2Resources.TOOLBAR_SEARCH_ICON_PATH);
		toolbarSearch.setTooltip(AS2GwtDesktop._messages.comboBoxItem_pickerSearchFieldHint());
		toolbarSearch.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event){
				_listGrid.deselectAllRecords();
				actionToolbarSearch(event);
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
	protected void getRollOverButtons(HLayout rollover,ListGrid listGrid) {
		rollover.addMember(rolloverEditButton);
		rollover.addMember(rolloverDeleteButton);
	}
	protected void actionToolbarAdd(ClickEvent event) {
	}
	protected void actionToolbarPreview(ClickEvent event) {
	}
	protected void actionToolbarAttachement(ClickEvent event) {
	}
	protected void actionToolbarDelete(ClickEvent event,boolean value) {
		Record recordToDelete = new Record();
		if(_listGrid.getSelectedRecords().length==1){
			recordToDelete.setAttribute("object0", _listGrid.getSelectedRecord().getAttribute(getRecordIdName()));
		}else{
			ListGridRecord[] selectedRecords = _listGrid.getSelectedRecords();
			int count=0;
			for(ListGridRecord record:selectedRecords){
				recordToDelete.setAttribute("object"+count++, record.getAttribute(getRecordIdName()));
			}
		}
		_listGrid.removeData(recordToDelete);
		_listGrid.invalidateCache();
	}
	protected void actionToolbarSearch(ClickEvent event) {
	}
	protected abstract String getRecordIdName();

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
	/**
	 * Search view contents.
	 */
	public AS2DynamicForm getSearchForm() {
		final AS2DynamicForm form = new AS2DynamicForm(number_of_columns);
		form.setAutoFetchData(false);
		form.setAutoHeightDynamicForm();
		form.setWrapItemTitles(false);
		form.setWidth(100);
		search = new AS2FormItem("search",AS2Field.TEXT,"Pretraživanje");
		search.setWidth("400");
		
		_advanced_search = new PickerIcon(PickerIcon.COMBO_BOX,
				new FormItemClickHandler() {
					public void onFormItemClick(FormItemIconClickEvent event) {
						Rectangle itemRect = search.getField().getPageRect();
						AS2SearchWindow trazi = new AS2SearchWindow(AS2View2.this,_listGrid,true,true);
						trazi.moveWindowTo(itemRect.getLeft()-3,itemRect.getTop()+itemRect.getHeight());
						trazi.resizeTo(itemRect.getWidth()-12,trazi.getOptimalHight());
					}
				});
		_search = new PickerIcon(PickerIcon.SEARCH, new FormItemClickHandler() {
			public void onFormItemClick(FormItemIconClickEvent event) {
				AS2SearchWindow bl = new AS2SearchWindow(AS2View2.this,_listGrid,false,true);
				bl.getButtonSearch().getField().fireEvent(new com.smartgwt.client.widgets.form.fields.events.ClickEvent(bl.getButtonSearch().getField().getJsObj()));
					}
				});
		
		
		
		
		
		search.getField().setIcons(_advanced_search,_search);
		search.getField().addKeyPressHandler(new KeyPressHandler() {
			@Override
			public void onKeyPress(KeyPressEvent event) {
				if (event.getKeyName() != null) {
					if (event.getKeyName().equals("Enter")) {
						AS2SearchWindow bl = new AS2SearchWindow(AS2View2.this,_listGrid,false,true);
						bl.getButtonSearch().getField().fireEvent(new com.smartgwt.client.widgets.form.fields.events.ClickEvent(bl.getButtonSearch().getField().getJsObj()));
					}
				}
			}
		});
	
		form.setDataSource(getModel());
		form.setFields(search);
		return form;
	}
	public String getSearchCriteria(){ 
		if(_searchForm.getField("search").getValue() == null)
			return "";
		else 
			return _searchForm.getField("search").getValue().toString();
	}
	public AS2FormItem[] getSearchFormItems() {
		List<AS2FormItem> items = new ArrayList<AS2FormItem>();
		AS2FormItem[] formItems = new AS2FormItem[items.size()];
		items.toArray(formItems);
		return formItems;
	}
}