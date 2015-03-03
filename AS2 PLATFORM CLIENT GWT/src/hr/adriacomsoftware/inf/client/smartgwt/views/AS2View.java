package hr.adriacomsoftware.inf.client.smartgwt.views;
//http://designingwebinterfaces.com/designing-web-interfaces-12-screen-patterns
import hr.adriacomsoftware.app.client.smartgwt.resources.AS2Resources;
import hr.adriacomsoftware.inf.client.smartgwt.core.AS2ClientContext;
import hr.adriacomsoftware.inf.client.smartgwt.desktop.AS2CacheManager;
import hr.adriacomsoftware.inf.client.smartgwt.desktop.views.AS2GwtDesktop;
import hr.adriacomsoftware.inf.client.smartgwt.desktop.views.AS2ListGridToolBar;
import hr.adriacomsoftware.inf.client.smartgwt.models.AS2ListGridRecord;
import hr.adriacomsoftware.inf.client.smartgwt.transport.rpc.GreetingService;
import hr.adriacomsoftware.inf.client.smartgwt.transport.rpc.GreetingServiceAsync;
import hr.adriacomsoftware.inf.client.smartgwt.types.AS2Field;
import hr.adriacomsoftware.inf.client.smartgwt.types.AS2FormItem;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;

import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.data.RestDataSource;
import com.smartgwt.client.types.DSDataFormat;
import com.smartgwt.client.types.DSOperationType;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.form.ValuesManager;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.toolbar.RibbonBar;
import com.smartgwt.client.widgets.toolbar.ToolStrip;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;

public abstract class AS2View extends VLayout implements AsyncCallback <HashMap<String,Object>> {
	protected static final String CONTEXT_AREA_WIDTH = "*";
	private static final String TOOLBAR_HEIGHT = "25px";
	private static final String TOOLSTRIP_WIDTH = "*";
	public ToolStripButton first;
	public ToolStripButton second;
	public ToolStripButton third;
	public ToolStripButton fourth;
	public ToolStripButton fifth;
	public ToolStripButton sixth;
	public ToolStripButton seventh;
	public ToolStripButton eight;
	public ToolStripButton ninth;
	protected static final String DOCUMENT_NEW_TOOLBAR_BUTTON = AS2Resources.INSTANCE.toolbar_document_new_icon().getSafeUri().asString();
	protected static final String DOCUMENT_EDIT_TOOLBAR_BUTTON = AS2Resources.INSTANCE.toolbar_document_edit_icon().getSafeUri().asString();
	protected static final String CREDIT_CARD_TOOLBAR_BUTTON = AS2Resources.INSTANCE.toolbar_credit_card_icon().getSafeUri().asString();
	protected static final String SCORE_TOOLBAR_BUTTON = AS2Resources.INSTANCE.toolbar_score_icon().getSafeUri().asString();
	protected static final String STATUS_TOOLBAR_BUTTON = AS2Resources.INSTANCE.toolbar_status_icon().getSafeUri().asString();
	protected static final String ATTACHEMENT_BUTTON = AS2Resources.INSTANCE.toolbar_attachement_icon().getSafeUri().asString();
	protected static final String REFRESH_TOOLBAR_BUTTON = AS2Resources.INSTANCE.toolbar_refresh_icon().getSafeUri().asString();
	protected static final String REFRESH_TOOLBAR_BUTTON_HINT = "Osvježi";
	protected static final String SEARCH_TOOLBAR_BUTTON = AS2Resources.INSTANCE.toolbar_search_icon().getSafeUri().asString();
	protected static final String SEARCH_TOOLBAR_BUTTON_HINT = "Pronađi";
	protected static final String DELETE_TOOLBAR_BUTTON = AS2Resources.INSTANCE.toolbar_delete_icon().getSafeUri().asString();
	protected static final String DELETE_TOOLBAR_BUTTON_HINT = "Briši";
	protected static final String FILTER_TOOLBAR_BUTTON = AS2Resources.INSTANCE.toolbar_filter_icon().getSafeUri().asString();
	protected static final String FILTER_TOOLBAR_BUTTON_HINT = "Filtriraj";
	protected static int number_of_columns = 4;
	protected static boolean view_ready = false;
	protected boolean use_form = true;
	protected boolean use_listgrid = true;
//	protected boolean use_sifrarnik = false;
	protected boolean use_cache_manager = false;
	protected DataSource _dataSource;
	protected ValuesManager _vm;
	public AS2ListGrid _listGrid;
	public AS2DynamicForm _form;
	protected HLayout _toolBar;
	protected RibbonBar _ribbonBar;
	protected ToolStrip _toolStrip;
	protected int numOfSelectedRecords=0;
	protected Label _window_form_title;
	protected AS2FormItem _horizontal_line;
	private AS2WaitPopup popup;
	protected static String view_name;

	protected static final GreetingServiceAsync server = GWT.create(GreetingService.class);


	public AS2View() {
		this(new Record());
		/*super();
		initializeView();
		if(false){//use_sifrarnik
			fillCache();
		}else if(use_cache_manager){
			fetchCacheData();
		}else{
			drawWidgets();
		}*/
	}	
	public AS2View(Record record) {
		super();
		getAdditionalData(record);
		initializeView();
		/*if(false){//use_sifrarnik
			fillCache();
		}else */
		if(use_cache_manager){
			fetchCacheData();
		}else{
			drawWidgets();
		}
	}
	/**
	 * Draws widgets
	 */
	protected void drawWidgets() {
		// initialise the layout container
		this.setStyleName("crm-ContextArea");
		this.setWidth(CONTEXT_AREA_WIDTH);
		// this.setMembersMargin(5);//Razmak između elemenata u layoutu forme
		// define model
		_dataSource = getModel();
		// define ValuesManager
		_vm = new ValuesManager();
		_vm.setDataSource(_dataSource);

		// define ToolBar
		// initialise the ToolStrip
		_toolStrip = getToolStrip();// TODO
		if (_toolStrip != null) {
			_toolBar = new HLayout();
			// initialise the ToolBar layout container
			_toolBar.setStyleName("crm-ToolBar");
			_toolBar.setHeight(TOOLBAR_HEIGHT);
			GWT.log("init ToolBar()...", null);
			_toolBar.setMembers(_toolStrip);
			_toolStrip.setHeight(TOOLBAR_HEIGHT);
			_toolStrip.setWidth(TOOLSTRIP_WIDTH);
		}
		// initialise the RibbonBar
		_ribbonBar = getRibbonBar();// TODO
		if (_ribbonBar != null) {
			GWT.log("init RibbonBar()...", null);
			_ribbonBar.setHeight(TOOLBAR_HEIGHT);
			_ribbonBar.setWidth(TOOLSTRIP_WIDTH);
		}
		showWidgets();

	}
	protected abstract void initializeView();
	
	protected void getAdditionalData(Record record){};
	
	public void showWidgets() {
		if (use_listgrid && !use_form) {
			initListGrid();
			if(_toolStrip!=null)
				this.addMembers(_toolBar, _listGrid,_listGrid.getStatusBar());
			else if(_ribbonBar!=null)
				this.addMembers(_ribbonBar, _listGrid,_listGrid.getStatusBar());
			else
				this.addMembers(_listGrid,_listGrid.getStatusBar());

		}
		if(use_form && !use_listgrid){
			_form = getDynamicForm();
			if(_toolStrip!=null)
				this.addMembers(_toolBar, _form);
			else if(_ribbonBar!=null)
				this.addMembers(_ribbonBar, _form);
			else
				this.addMembers(_form);

		}
		if (use_form && use_listgrid) {
			_form = getDynamicForm();
			initListGrid();
			this.addMembers(new AS2ListGridToolBar(_listGrid, _vm), _listGrid,
					_listGrid.getStatusBar(),_form, new AS2DynamicFormButtons(_listGrid, _vm));
		}

		if (!use_form && !use_listgrid) {
			_form = getDynamicForm();
		}
	}

	/**
	 * Fills client cache with data
	 */
	//ne brisati logiku
//	private void fillCache() {
//		if(getSifrarnikModel()!=null && use_cache_manager)
//			fillCacheFromSifrarnikBRISI(false);//Podaci šifrarnika se dobiju iz baze
//		if(!use_cache_manager){
//			drawWidgets();
//		}
//		if(getSifrarnikValueMaps()!=null)
//			fillCacheFromView();//Podaci šifrarnika  se dobiju lokalno iz Viewa
//		if(getSifrarnikModel()==null)
//			drawWidgets();//getSifrarnikModel()!=null -> drawWidgets se zove iz callbacka (ASYNC)
//	}


	private void initListGrid(){
		_listGrid = getListGrid();
	}
	

	private void fetchCacheData() {
		AS2CacheManager.getInstance().clear();
		popup = new AS2WaitPopup();
		popup.show(AS2GwtDesktop._messages.listGrid_loadingDataMessage());
		getSifrarnikModel().fetchData(new Criteria(), getSifrarnikServerCallback(false));
	}

	
	protected void fillCacheFromSifrarnikBRISI(boolean update) {
		AS2CacheManager.getInstance().clear();
		popup = new AS2WaitPopup();
		popup.show(AS2GwtDesktop._messages.listGrid_loadingDataMessage());
		getSifrarnikModel().fetchData(new Criteria(), getSifrarnikServerCallback(update));
	}
	
	private DSCallback getSifrarnikServerCallback(final boolean update) {
		return 	new DSCallback() {
			public void execute(DSResponse response, Object rawData,
					DSRequest request) {		
				if(response.getStatus()>=0){
					// response.getDataAsRecordList().get(0).getAttributes();
					RecordList records = response.getDataAsRecordList();
					AS2CacheManager.getInstance().fillCacheWithData(records);
					if(!update)
						drawWidgets();
					popup.hideFinal();

				}else{
					popup.hideFinal();
				}
			}
		};
	}

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

	protected void refreshSifrarnikCache(){
		AS2CacheManager.getInstance().clear();
		fillCacheFromSifrarnikBRISI(false);
	}
	
	//TODO testirati
	protected void updateSifrarnikCache(){
		AS2CacheManager.getInstance().clear();
		fillCacheFromSifrarnikBRISI(true);
	}

	protected void callServer(String component, String service, String attribute){
		HashMap<String,Object> to_server = new HashMap<String, Object>();
		to_server.put("@attribute",attribute);
		callServer(component,service,to_server);
	}
	protected void callServer(String component, String service, HashMap<String,Object> value){
		HashMap<String,Object> to_server = new HashMap<String, Object>();
		to_server.put("@component", component);
		to_server.put("@service", service);
		to_server.putAll(value);
		server.greetServer(to_server, this);
	}
	@Override
	public void onFailure(Throwable caught) {
		view_ready = false;
	}
	@Override
	public void onSuccess(HashMap<String, Object> result) {
		prepareFormData(result);
		view_ready = true;
	}
	protected void prepareFormData(HashMap<String, Object> result){
		//implement in case you have drop down, combo on your form
	}

//	@SuppressWarnings("unused")
//	public void refreshStatusBar(String numberSelected, String numberOfElements, String pageNumber) {
//		// update Selected label e.g "0 of 50 selected"
//		String selectedLabel="";
//		if(numberSelected!=null && numberSelected.length()>0)
//			selectedLabel= numberSelected;
//		if(numberOfElements!=null && numberOfElements.length()>0)
//			selectedLabel = selectedLabel + " od " + numberOfElements +" odabrano";
//
//		String pageNumberLabel = "";
//		if(pageNumber!=null && pageNumber.length()>0)
//			pageNumberLabel = "Stranica  " + pageNumber;
//	}
	protected AS2FormItem horizontalLine() {
		_horizontal_line = new AS2FormItem("horizontal_line",AS2Field.FORM_SEPARATOR,"",number_of_columns,null,true,false,true);
		return _horizontal_line;
	}
	protected ToolStrip getToolStrip(){
		return null;
	}

	protected RibbonBar getRibbonBar() {
		return null;
	}
	public DataSource getModel(){
		RestDataSource client_dataSource = new RestDataSource();
		client_dataSource
				.setDataURL(AS2ClientContext.TRANSPORT_SERVLET_URL);
		client_dataSource.setDataFormat(DSDataFormat.JSON);
		return client_dataSource;
	}

	public DataSource getSifrarnikModel(){
		return null;
	};
	
	protected abstract AS2DynamicForm getDynamicForm();

	protected abstract AS2ListGrid getListGrid();
	
	
	/******************************* JSON ******************************
	 * Dodano 21.05.2014
	 */
	
	//TODO PREBAČENO U MODEL AS2RESTJSONDATASOURCE MODEL BRIŠI??
	// Dohvaćam javascript i pretvaram u GWT Record
		public RecordList getRecordsFromServerJSON(DSResponse dsResponse) {
			boolean error = checkErrorsFromServer(dsResponse);
				if(!error){
					RecordList data = dsResponse.getDataAsRecordList();
					RecordList result = new RecordList();
					RecordList records = null;
					for (int i = 0; i < data.toArray().length; i++) {
						Record record = data.get(0);
						if (record.getAttributeAsObject("records") != null) {
							records = new RecordList();
							records = record.getAttributeAsRecordList("records");
							break;
						}
					}
					if (records != null) {
						for (Record record : records.toArray()) {
							if (record.getAttributeAsRecord("record") != null) {
								Record current = record.getAttributeAsRecord("record");
								for(String attribute:current.getAttributes()){
									if(current.getAttribute(attribute).length()!=0){
										String type = getModel().getField(attribute).getType().toString().toLowerCase();
										if(type.equals("date")){
											current.setAttribute(attribute,DateTimeFormat.getFormat(AS2ClientContext.AS2_DATE_FORMAT).parse(current.getAttribute(attribute)));
										}
										if(type.equals("float")){
											current.setAttribute(attribute,AS2ClientContext.formatNumber(current.getAttribute(attribute)));
										}
									}
								}
								current = formatRecordFromServerJSON(current); //Dodatno formatiranje prema želji
								result.add(current);
							}
						}
					}
					return result;
				}
				return null;
		}
		
		//Implement in view if record needs formatting for display
		protected Record formatRecordFromServerJSON(Record record) {
			return record;
		}
		
		
		public boolean checkErrorsFromServer(DSResponse dsResponse){
			if (dsResponse.getTotalRows() != 0) {
				boolean error=false;
				RecordList records = dsResponse.getDataAsRecordList();
				for(int i=0;i<records.toArray().length;i++){
					Record record = records.get(i);
					if(record.getAttributeAsString("error")!=null){
						error=true;
						SC.warn(AS2GwtDesktop._messages.dialog_WarnTitle(), record.getAttributeAsString("error"));
					}
				}
				if(!error){
					return false;
				}
			} else {
				 noData();
			}
			return true;

		}

		public void noData(){
			SC.warn(AS2GwtDesktop._messages.dialog_WarnTitle(),"Nema podataka");
//			if(_selectedPokazatelj!=null && _pokazateljiTreeGrid!=null)
//			_pokazateljiTreeGrid.deselectRecord(_selectedPokazatelj);
		}
		
		/******************************* JSON ******************************/
	/**
	 * Fetches single record/value object from server
	 *
	 * @param dataSource
	 * @param record
	 * 			record to send to server
	 * @param current_view
	 * 			view that called fetchSingleRecord
	 */
		
		/////////////////////////////////////////BRIIIIIIIIIIIIŠIIIIIIIIIIIIIIIIIII
	public void fetchSingleRecord(DataSource dataSource,AS2ListGridRecord record, final Object current_view){
		if(record!=null){
			DSRequest ds_request = new DSRequest();
			ds_request.setOperationId("single");
			ds_request.setOperationType(DSOperationType.FETCH);
			dataSource.fetchData(getFetchCriteria(dataSource,record),new DSCallback() {
				@Override
				public void execute(DSResponse dsResponse, Object data, DSRequest dsRequest) {
					Record[] records = dsResponse.getData();
					if((AS2ListGridRecord)records[0]==null){
						SC.warn(AS2GwtDesktop._messages.dialog_WarnTitle(),"Problem prilikom dohvaćanja podataka sa servera!");
					}
					else{
						@SuppressWarnings("unused")
						Record record =(AS2ListGridRecord)records[0];
						if(current_view instanceof AS2Window){
							((AS2Window)current_view)._record = (AS2ListGridRecord)records[0];
							((AS2Window)current_view).createComponents();
						}
					}

				}
			},ds_request);

		}else{
			//if record is null that means that we don't need to fetch data from database
			if(current_view instanceof AS2Window){
				((AS2Window)current_view).createComponents();
			}
		}



	}

	/**
	 * fetch criteria - record/value object that we want to send to server,
	 * will be transformed to criteria
	 *
	 * @param dataSource
	 * @param record
	 * @return
	 * 		Criteria to be sent to server
	 */
	private Criteria getFetchCriteria(DataSource dataSource,final AS2ListGridRecord record){
			Criteria criteria = new Criteria();
			for(String attribute:record.getAttributes()){
				if(dataSource.getField(attribute)!=null && record.getAttribute(attribute)!=null ){
					String type = dataSource.getField(attribute).getType()
							.toString();
					if (type.equalsIgnoreCase("date")) {
						if(record.getAttribute(attribute)!=null && record.getAttribute(attribute).length()>0)
							criteria.setAttribute(attribute, (Date)record.getAttributeAsDate(attribute));
					} else if (type.equalsIgnoreCase("float")) {
						criteria.addCriteria(attribute,
								record.getAttribute(attribute));
					} else if (type.equalsIgnoreCase("integer")) {
						criteria.addCriteria(attribute,
								record.getAttributeAsInt(attribute));
					} else if (type.equalsIgnoreCase("boolean")) {
						criteria.addCriteria(attribute,
								record.getAttributeAsBoolean(attribute));
					} else {
						criteria.addCriteria(attribute,
								record.getAttribute(attribute));
					}
				}else
					criteria.addCriteria(attribute,
							record.getAttribute(attribute));

			}
			return criteria;
		}

	//Poziva TraziWindow iz drugog viewa
	public void getValuesFromTrazi(Record selectedRecord) {}
	
	//Postavljanje vrijednosti polja kojega se editira iz drugog viewa
	protected void setFieldValue(String name, Object value) {
		_listGrid.setFieldValue(name, value);
		_form.setFieldValue(name, value);
	}
}

//	protected void updateStatusBar(){
////		System.out.println("RADIM updateStatusBar()");
//		int recordsTotal=0;
//		int recordsSelected=0;
//		if(_listGrid!=null){
//			if(_listGrid.getRecords()!=null)
//				recordsTotal=_listGrid.getRecords().length;
//			if(_listGrid.getSelectedRecords()!=null)
//				recordsSelected=_listGrid.getSelectedRecords().length;
//			_statusBar.getSelectedLabel().setContents("Zbroj: "+recordsTotal+" Odabrano: "+recordsSelected);
//		}
//		else
//			_statusBar.getSelectedLabel().setContents("");
//
//
//	}

//	protected void refresh() {
//		_listGrid.invalidateCache();
//		_listGrid.updateStatusBar();
////		_listGrid.markForRedraw();
//		//AutoFetchData je uključen pa netreba ponovo zvati fetch
////		_listGrid.fetchData();
//	}

