package hr.adriacomsoftware.inf.client.smartgwt.services;

import hr.adriacomsoftware.inf.client.smartgwt.models.AS2RestJSONDataSource;

import java.util.HashMap;

import com.google.gwt.core.client.GWT;
import com.smartgwt.client.widgets.Canvas;

public class AS2ReportingServices {
	
	public static final String AS2_RESPONSE_TYPE = "as2_response_type";
	public static final String REPORT_FORMAT = "as2_report_format";
	public static final String AS2_REPORT_TYPE = "as2_report_type";
	public static final String REMOTE_METHOD = "remotemethod";
	public static final String REMOTE_OBJECT = "remoteobject";
	public static final String TRANSFORM_TO = "transform_to";
	public static final String AS2_RECORD = "hr.as2.inf.common.data.AS2Record";	
	//Jasper
	public static final String REPORT_FILE_NAME = "report_file_name";
	HashMap<String,String> parameters = new HashMap<String,String>();
	Canvas layout;
	
	public AS2ReportingServices(Canvas layout,String remote_object,String remote_method) {
		this.layout = layout;
		setRemoteObject(remote_object);
		setRemoteMethod(remote_method);
		setValueObject(AS2_RECORD);
	}
	
	public AS2ReportingServices(Canvas layout,String remote_object,String remote_method,String transform_to) {
		this.layout = layout;
		setRemoteObject(remote_object);
		setRemoteMethod(remote_method);
		setValueObject(transform_to);
	}
	
	public AS2ReportingServices(Canvas layout,String remote_object,String remote_method,HashMap<String, String> parameters) {
		this.layout = layout;
		setRemoteObject(remote_object);
		setRemoteMethod(remote_method);
		setValueObject(AS2_RECORD);
		for(String key:parameters.keySet()){
			setParameter(key,parameters.get(key));
		}
	}
	
	public AS2ReportingServices(Canvas layout,String remote_object,String remote_method,String transform_to, HashMap<String, String> parameters) {
		this.layout = layout;
		setRemoteObject(remote_object);
		setRemoteMethod(remote_method);
		setValueObject(transform_to);
		for(String key:parameters.keySet()){
			setParameter(key,parameters.get(key));
		}
	}
	
	public void setParameter(HashMap<String, String> parameters){
		for(String key:parameters.keySet()){
			setParameter(key,parameters.get(key));
		}
	}
	
	public void setParameter(String key, String value){
		parameters.put(key,value);
	}


	public void setRemoteObject(String component){
		parameters.put(REMOTE_OBJECT,component);
	}

	public void setRemoteMethod(String service){
		parameters.put(REMOTE_METHOD,service);
	}

	public void setValueObject(String vo){
		parameters.put(TRANSFORM_TO,vo);
	}
	
	public void getJasperReport(String report_file_name){
		setParameter(REPORT_FORMAT, "pdf");
		setParameter(AS2_RESPONSE_TYPE, "bytes");
		setParameter(REPORT_FILE_NAME, report_file_name);
		printReportInNewWindow();
	}
	
	public void getExcelReport(){
		getExcelReport("xlsx");
	}
	public void getExcelReport(String as2_report_format){
		setParameter(REPORT_FORMAT, as2_report_format);
		setParameter(AS2_RESPONSE_TYPE, "bytes");
		printReportInNewWindow();
//		DynamicForm _downloadForm = new DynamicForm();
//		_downloadForm.setEncoding(Encoding.MULTIPART);
//		_downloadForm.setAction(actionUrl);
//		_downloadForm.setVisible(false);
//		layout.addChild(_downloadForm);
//		_downloadForm.submitForm();
//		_downloadForm.destroy();
	}
	
	public void printReportInNewWindow(){
		String actionUrl=GWT.getHostPageBaseURL()+AS2RestJSONDataSource.DEFAULT_DATA_URL+"?";
		for(String key:parameters.keySet()){
			actionUrl=actionUrl+key+"="+parameters.get(key)+"&";
		}
		actionUrl = actionUrl.substring(0,actionUrl.length()-1);//Deletes last & in actionURL
		com.google.gwt.user.client.Window.open(actionUrl,"_blank","");
	}

}
