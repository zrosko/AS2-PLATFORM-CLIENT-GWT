package hr.adriacomsoftware.inf.client.smartgwt.views.fileupload;

import hr.adriacomsoftware.app.client.smartgwt.resources.AS2Resources;
import hr.adriacomsoftware.inf.client.smartgwt.models.AS2RestJSONDataSource;
import hr.adriacomsoftware.inf.client.smartgwt.types.AS2Field;
import hr.adriacomsoftware.inf.client.smartgwt.types.AS2ListGridField;
import hr.adriacomsoftware.inf.client.smartgwt.views.AS2ListGrid;
import hr.adriacomsoftware.inf.client.smartgwt.views.AS2View;
import hr.adriacomsoftware.inf.client.smartgwt.views.AS2Window;
import hr.adriacomsoftware.inf.client.smartgwt.views.rosko.AS2View2;

import java.util.HashMap;

import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.events.DoubleClickEvent;
import com.smartgwt.client.widgets.events.DoubleClickHandler;
import com.smartgwt.client.widgets.grid.events.SelectionChangedHandler;
import com.smartgwt.client.widgets.grid.events.SelectionEvent;
import com.smartgwt.client.widgets.layout.HLayout;


public abstract class AS2FileUploadMWindow extends AS2Window{

	private static int DEFAULT_HEIGHT = 300;
	private static int DEFAULT_WIDTH = 475;
	private AS2ListGridField naziv_dokumentaGrid;
	private AS2ListGridField tipDokumentaGrid;
	private IButton _button_otvori;
	public AS2FileUploadDialog upload;
	public AS2FileUploadDialog download;
	public Record _record;

	public AS2FileUploadMWindow(AS2View parent,Record record) {
//		this._parent_view = parent;
		this._record = record;
		this._dataSource = getListGridModel();
		this.setHeight(DEFAULT_HEIGHT);
		this.setWidth(DEFAULT_WIDTH);
		this.setShowResizeBar(true);
		this.centerInPage();
		this.setCanDragResize(true);
		createComponents();
	}
	
	public AS2FileUploadMWindow(AS2View2 parent,Record record) {
		this._parent_view = parent;
		this._record = record;
		this._dataSource = getListGridModel();
		this.setHeight(DEFAULT_HEIGHT);
		this.setWidth(DEFAULT_WIDTH);
		this.setShowResizeBar(true);
		this.centerInPage();
		this.setCanDragResize(true);
		createComponents();
	}

	@Override
	public void createComponents() {
		_window_form_title.setContents(getWindowFormTitle());
		_listGrid = getListGrid();
		Criteria criteria = new Criteria();
		if(getListGridFetchDataCriteria()!=null){
			for(String attribute:getListGridFetchDataCriteria().keySet().toArray(new String[getListGridFetchDataCriteria().size()])){
				criteria.addCriteria(attribute,getListGridFetchDataCriteria().get(attribute));
			}
		}
		_listGrid.setCriteria(criteria);
		_buttons_layout=getButtonsLayout();
		windowLayout();
	}

	@Override
	public void windowLayout() {
		this.addItem(_window_form_title);
		this.addItem(_listGrid);
		this.addItem(_listGrid.getStatusBar());
		this.addItem(_buttons_layout);
		this.show();
	}

	public AS2ListGrid getListGrid() {
		tipDokumentaGrid = new AS2ListGridField("tip_dokumenta", AS2Field.IMAGE, "Tip",50);
		tipDokumentaGrid.getField().setAlign(Alignment.CENTER);
		tipDokumentaGrid.getField().setImageSize(32);
		tipDokumentaGrid.getField().setImageURLPrefix(AS2Resources.FILE_UPLOAD_EXTENSION_32_PATH);
		tipDokumentaGrid.getField().setImageURLSuffix(".png");
		tipDokumentaGrid.getField().setDefaultValue("default");
		naziv_dokumentaGrid = new AS2ListGridField("naziv_dokumenta",AS2Field.TEXT,"Naziv Dokumenta");
		final AS2ListGrid listGrid = new AS2ListGrid(true);
		listGrid.setCellHeight(35);
		listGrid.setWrapCells(true);
		listGrid.setFetchOperation("multiple");
		listGrid.setDataSource(_dataSource);
		listGrid.setAlternateRecordStyles(true);
		listGrid.addSelectionChangedHandler(new SelectionChangedHandler() {
			@Override
			public void onSelectionChanged(SelectionEvent event) {
				if(_listGrid.getSelectedRecord()==null)
					_button_otvori.setDisabled(true);
				else
					_button_otvori.setDisabled(false);
			}
		});
		listGrid.setDoubleClickDelay(100);
		listGrid.addDoubleClickHandler(new DoubleClickHandler() {
			@Override
			public void onDoubleClick(DoubleClickEvent event) {
				if(_listGrid.getSelectedRecord()!=null)
					downloadFile();

			}
		});
		listGrid.setFields(tipDokumentaGrid,naziv_dokumentaGrid);
		return listGrid;
	}

	public HLayout getButtonsLayout() {
		// define Buttons
		IButton _button_dodaj_privitak = new IButton("Dodaj Privitak");
		_button_dodaj_privitak.setIcon(AS2Resources.ADD_FILES_PATH);
		_button_dodaj_privitak.setAutoFit(true);
		_button_dodaj_privitak.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				uploadFile();
			}
		});

		_button_otvori = new IButton("Otvori");
		_button_otvori.setDisabled(true);
		_button_otvori.setIcon(AS2Resources.OPEN_DOCUMENT_ICON_PATH);
		_button_otvori.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				downloadFile();
			}
		});

		_button_brisi = new IButton("Briši");
		_button_brisi.setIcon(AS2Resources.DELETE_PATH);
		_button_brisi.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				String text="Da li ste sigurni da želite obrisati privitak?";
				if(_listGrid.getSelectedRecords().length!=1)
					text="Da li ste sigurni da želite obrisati označene privitke?";
				SC.confirm("Upozorenje!", text,
						new BooleanCallback() {
					@Override
					public void execute(Boolean value) {
						if (value != null && value) {
							if(_listGrid!=null){
								for(Record record: _listGrid.getSelectedRecords()){
									_listGrid.removeData(record,new DSCallback() {
										@Override
										public void execute(DSResponse dsResponse, Object data, DSRequest dsRequest) {
											_listGrid.invalidateCache();
										}
									});
								}
							}
						}
					}
				});
			}
		});

		_button_izlaz = new IButton("Odustani");
		_button_izlaz.setIcon(AS2Resources.CANCEL_PATH);
		_button_izlaz.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				closeWindow(false);
			}
		});
		_buttons_layout.setMembers(_button_dodaj_privitak, _button_otvori,_button_brisi,
				_button_izlaz);
		return _buttons_layout;
	}

	public void uploadFile(){
		if(upload!=null)upload.destroy();
		upload = new AS2FileUploadDialog();
		HashMap<String,Object> parameters = new HashMap<String,Object>();
		parameters.put("Service",  getUploadService());
		parameters.put("Component",getUploadComponent());
		parameters.put("transform_to", getUploadVo());
		if(getUploadParameters()!=null){
			for(String key:getUploadParameters().keySet()){
				parameters.put(key, getUploadParameters().get(key));
			}
		}
		upload.uploadFile(this,parameters);
	}

	public void downloadFile(){
		if(download!=null)download.destroy();
		download = new AS2FileUploadDialog();
		HashMap<String,Object> parameters = new HashMap<String,Object>();
		parameters.put("Service",  getDownloadService());
		parameters.put("Component",getDownloadComponent());
		parameters.put("transform_to", getDownloadVo());
		for(String attribute: _listGrid.getSelectedRecord().getAttributes()){
			parameters.put(attribute,  _listGrid.getSelectedRecord().getAttributeAsObject(attribute));
		}
		if(getDownloadParameters()!=null){
			for(String key:getDownloadParameters().keySet()){
				parameters.put(key, getDownloadParameters().get(key));
			}
		}
		download.downloadFile(parameters);
		download.destroy();

	}

	public String getWindowFormTitle() {
		return "";
	}

	protected DataSource getListGridModel(){
		return new DataSource();
	}

	protected String getUploadComponent(){
		return ((AS2RestJSONDataSource) getListGridModel()).getRemoteObject();
	}

	protected abstract String getUploadService();

	protected abstract String getDownloadService();

	protected String getUploadVo(){
		return ((AS2RestJSONDataSource) getListGridModel()).getTransformTo();
	}

	protected String getDownloadVo() {
		return getUploadVo();
	}

	protected String getDownloadComponent() {
		return getUploadComponent();
	}

	protected HashMap<String,Object> getListGridFetchDataCriteria(){
		HashMap<String,Object> params = new HashMap<>();
		if(_record!=null){
			for(String attribute: _record.getAttributes()){
				params.put(attribute, _record.getAttributeAsObject(attribute));
			}
		}
		return params;
	}
	protected abstract HashMap<String,Object> getUploadParameters();
	protected abstract HashMap<String,Object> getDownloadParameters();

}
