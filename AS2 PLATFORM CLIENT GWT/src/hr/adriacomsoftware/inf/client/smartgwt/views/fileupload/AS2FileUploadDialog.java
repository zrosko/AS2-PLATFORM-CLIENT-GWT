package hr.adriacomsoftware.inf.client.smartgwt.views.fileupload;

import hr.adriacomsoftware.app.client.smartgwt.resources.AS2Resources;
import hr.adriacomsoftware.inf.client.smartgwt.views.AS2DynamicForm;
import hr.adriacomsoftware.inf.client.smartgwt.views.AS2Window;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.NamedFrame;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.Encoding;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.events.CloseClickEvent;
import com.smartgwt.client.widgets.events.CloseClickHandler;
import com.smartgwt.client.widgets.form.fields.UploadItem;
import com.smartgwt.client.widgets.form.fields.events.ChangeEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangeHandler;
import com.smartgwt.client.widgets.layout.HLayout;

public class AS2FileUploadDialog extends Canvas {
	private static int DEFAULT_HEIGHT = 80;
	private static int DEFAULT_WIDTH = 350;
	private static final String DEFAULT_FILE_UPLOAD_SERVLET_URL = "json_servlet_fileupload";
	public static final String TARGET = "uploadTarget";
	private Window _uploadDialog;
	private boolean change=false;
	private AS2Window _parent;
//	HashMap<String,Object> parameters = new HashMap<String,Object>();
	private DataSource _dataSource;
	private AS2DynamicForm _uploadForm;
	private HLayout _buttons_layout;
	private Object _downloadForm;
//	private List<HiddenItem> hiddenItems;
	public static enum Mode {
		DEFAULT, CONVERSIONS
	};

	public AS2FileUploadDialog() {
	}

	/**********************************UPLOAD FILE******************************/
//	public void uploadFile(AS2Window parent,hr.adriacomsoftware.inf.client.smartgwt.views.fileupload.AS2FileUpload.Mode default1) {//U slučaju konverzija
	public void uploadFile(AS2Window parent, HashMap<String,Object> parameters) {
		this._parent=parent;
		initComplete(this);
		initFailed(this);
		parameters.put("servlet_service", "upload");
		_uploadDialog = new Window();
		_uploadDialog.setWidth(DEFAULT_WIDTH);
		_uploadDialog.setHeight(DEFAULT_HEIGHT);
		_uploadDialog.centerInPage();
		_uploadDialog.setIsModal(true);
		_uploadForm = getUploadForm(parameters);
		_buttons_layout = getButtonsLayout();
		NamedFrame _frame = new NamedFrame(TARGET);
		_frame.setWidth("1");
		_frame.setHeight("1");
		_frame.setVisible(false);

		// TODO Treba omogučiti konverziju u servletu
//		if (mode == Mode.CONVERSIONS) {
//			CheckboxItem unzip = new CheckboxItem("unzip");
//			unzip.setDefaultValue(true);
//			unzip.setTitle("Unzip .zip file");
//			_items.add(unzip);
//			CheckboxItem overwrite = new CheckboxItem("overwrite");
//			overwrite.setDefaultValue(false);
//			overwrite.setTitle("Overwrite existing file");
//			_items.add(overwrite);
//			CheckboxItem convertpdf = new CheckboxItem("convertpdf");
//			convertpdf.setDefaultValue(true);
//			convertpdf.setTitle("Convert Word document to PDF");
//			_items.add(convertpdf);
//			CheckboxItem streaming = new CheckboxItem("streaming");
//			streaming.setDefaultValue(true);
//			streaming.setTitle("Convert video file to streaming format(flv)");
//			_items.add(streaming);
//			CheckboxItem thumbnail = new CheckboxItem("thumbnail");
//			thumbnail.setDefaultValue(true);
//			thumbnail.setTitle("Make thumbnail(48x48) from image");
//			_items.add(thumbnail);
//		}
		_uploadDialog.addCloseClickHandler(new CloseClickHandler() {
			@Override
			public void onCloseClick(CloseClickEvent event) {
				destroy(true);
			}
		});
		_uploadDialog.addMember(_uploadForm);
		_uploadDialog.addMember(_buttons_layout);
		_uploadDialog.addMember(_frame);
		_uploadDialog.draw();
	}

	private AS2DynamicForm getUploadForm(HashMap<String, Object> parameters) {
		AS2DynamicForm form = new AS2DynamicForm();
		form.setEncoding(Encoding.MULTIPART);
		form.setTarget(TARGET);
		form.setStyleName("");
		form.setHeight(DEFAULT_HEIGHT / 2);
		form.setWidth100();
		form.setUseAllDataSourceFields(true);
		form.setAction(getActionUrl(parameters));
		UploadItem fileItem = new UploadItem("privitak","Privitak");
		fileItem.setWidth("*");
		fileItem.addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {
				change=true;
			}
		});
//		fileItem.setMultiple(true);
		form.setItems(fileItem);
		form.focusInItem(fileItem);
		return form;
	}
	
	//Primjer slanja preko hidden itema
	//	String actionUrl=GWT.getModuleBaseURL()+DEFAULT_UPLOAD_SERVLET_URL+"?Component="+parameters.get("Component")+"&Service="+parameters.get("Service")+"&transform_to="+parameters.get("transform_to")+"&service="+parameters.get("service");
	//	form.setAction(actionUrl);
	//	fileItem.setWidth(DEFAULT_WIDTH);
	//	_items.add(fileItem);
	//	if (parameters != null) {
	//		hiddenItems = new ArrayList<HiddenItem>();
	//		for (String key : parameters.keySet()) {
	//			//Service, Component and VO will be sent to servlet in URL
	//			if(key.equalsIgnoreCase("service") || key.equalsIgnoreCase("component") || key.equalsIgnoreCase("transform_to")) continue;
	//			HiddenItem item = new HiddenItem(key);
	//			item.setValue(parameters.get(key));
	//			_items.add(item);
	//			hiddenItems.add(item);
	//		}
	//	}
	//	FormItem[] fitems = new FormItem[_items.size()];
	//	_items.toArray(fitems);

	private HLayout getButtonsLayout() {
		HLayout _layout = new HLayout();
		_layout.setWidth100();
		_layout.setHeight(DEFAULT_HEIGHT / 2);
		_layout.setMembersMargin(10);
		_layout.setAlign(Alignment.RIGHT);
//		_layout.setDefaultLayoutAlign(Alignment.CENTER);

		IButton _button_upload = new IButton("U redu");
//		_button_upload.setLayoutAlign(Alignment.RIGHT);
		_button_upload.setIcon(AS2Resources.ADD_FILES_PATH);
		_button_upload.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent e) {
				if (change) {
					change=false;
					_uploadForm.submitForm();
				} else
					SC.say("Odaberite datoteku.");
			}
		});
		IButton _button_close = new IButton("Odustani");
//		_button_close.setLayoutAlign(Alignment.RIGHT);
		_button_close.setIcon(AS2Resources.CANCEL_PATH );
		_button_close.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				destroy(false);
			}
		});
		_layout.addMember(_button_upload);
		_layout.addMember(_button_close);
		return _layout;
	}
//	
//	public void setHiddenItem(String name, String value) {
//		for (HiddenItem item : hiddenItems)
//			if (item.getName().equals(name)) {
//				item.setValue(value);
//				return;
//			}
//	}

	public void uploadComplete(String fileName, String pk) {
//		if(_parent._listGrid!=null)
//			destroy(true);
//		else{
//			destroy(false);
//		}
		_parent.fileUploadCompleteCallback(AS2FileUploadDialog.this,fileName,pk);
		// Ne koristi se
		// if (_listener != null)
		// _listener.uploadComplete(fileName);
	}

	public void uploadFailed(String fileName, String reason) {
		destroy(false);
		SC.warn("Dodavanje privitka",
				"Problem prilikom dodavanja datoteke!<br>Razlog: " + reason);
		// Ne koristi se
		// if (_listener != null)
		// _listener.uploadFailed(fileName,reason);
	}

	private native void initComplete(AS2FileUploadDialog upload)/*-{
	$wnd.uploadComplete = function(fileName,pk) {
		upload.@hr.adriacomsoftware.inf.client.smartgwt.views.fileupload.AS2FileUploadDialog::uploadComplete(Ljava/lang/String;Ljava/lang/String;)(fileName,pk);
		};
	}-*/;
	
	private native void initFailed(AS2FileUploadDialog upload) /*-{
		$wnd.uploadFailed = function(fileName, reason) {
			upload.@hr.adriacomsoftware.inf.client.smartgwt.views.fileupload.AS2FileUploadDialog::uploadFailed(Ljava/lang/String;Ljava/lang/String;)(fileName,reason);
		};
	}-*/;

	/**********************************DOWNLOAD FILE***************************/
	public void downloadFile(HashMap<String,Object> parameters){
		parameters.put("servlet_service", "download");
		String url = getActionUrl(parameters);
		getDownloadedFile(url);
	}

	private void getDownloadedFile(String url) {
		com.google.gwt.user.client.Window.open(url,"_blank","");

	}

//	private DynamicForm getDownloadForm() {
//		DynamicForm form = new DynamicForm();
//		form.setEncoding(Encoding.MULTIPART);
//		form.setAction(getActionUrl());
//		form.draw();
//		form.setVisible(false);
//		form.submitForm();
//		form.destroy();
//		return form;
//	}

	public static String getActionUrl(HashMap<String,Object> parameters) {
		String actionUrl=GWT.getHostPageBaseURL()+DEFAULT_FILE_UPLOAD_SERVLET_URL+"?";
		Iterator<Entry<String, Object>> it = parameters.entrySet().iterator();
		while (it.hasNext()) {
			Entry<String, Object> pairs = (Entry<String, Object>) it.next();
			actionUrl=actionUrl+(String)pairs.getKey()+"="+pairs.getValue()+"&";
			it.remove(); // avoids a ConcurrentModificationException
		}
		actionUrl = actionUrl.substring(0,actionUrl.length()-1);//Deletes last & in actionURL
		return actionUrl;
	}
	
//	public void setParameter(String key, Object value) {
//		parameters.put(key, value);
//	}
//
//	public void setParameters(HashMap<String, Object> args) {
//		parameters.putAll(args);
//	}
//	
//	public void addParameters(Record record) {
//		for(String attribute:record.getAttributes()){
//			setParameter(attribute, record.getAttributeAsObject(attribute));
//		}
//	}
//	
//	public void addParameters(HashMap<String, Object> args) {
//		for(String attribute:args.keySet().toArray(new String[args.size()])){
//			setParameter(attribute, args.get(attribute));
//		}
//	}
//
//	public void setComponent(String component) {
//		parameters.put("Component", component);
//	}
//
//	public void setService(String service) {
//		parameters.put("Service", service);
//	}
//
//	public void setValueObject(String vo) {
//		parameters.put("transform_to", vo);
//	}

	public void destroy(boolean refresh) {
		if(refresh){
			_parent.refreshListGrid();
		}
		_uploadDialog.markForDestroy();
	}
}

//private UploadListener _listener;
//
//public void setAction(String url) {
//	_uploadForm.setAction(url);
//}
//
//// Ne koristi se
//public void setUploadListener(UploadListener listener) {
//	this._listener = listener;
//}
