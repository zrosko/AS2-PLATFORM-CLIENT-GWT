package hr.adriacomsoftware.inf.client.smartgwt.views.reports;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import com.google.gwt.core.client.GWT;

public class AS2Report {

	HashMap<String,String> parameters = new HashMap<String,String>();

	public AS2Report(String reportFilename){
		this(reportFilename,null,"pdf");
	}

	public AS2Report(String reportFilename,String reportSelected){
		this(reportFilename,reportSelected,"pdf");
	}

	public AS2Report(String reportFilename,String reportSelected ,String reportFormat){
		parameters.put("reportFilename", reportFilename);
		parameters.put("reportSelected", reportSelected);
		parameters.put("reportFormat", reportFormat);

	}

	public void setParameter(String key, String value){
		parameters.put(key,value);
	}

	public void setComponent(String component){
		parameters.put("Component",component);
	}

	public void setService(String service){
		parameters.put("Service",service);
	}

	public void setValueObject(String vo){
		parameters.put("transform_to",vo);
	}

	@SuppressWarnings("rawtypes")
	public void printReport(){
		String params="";
		if(parameters.size()!=0){
			params="?";
			Iterator<Entry<String, String>> it = parameters.entrySet().iterator();
		    while (it.hasNext()) {
		        Map.Entry pairs = it.next();
		        params=params+"&"+pairs.getKey() + "=" + pairs.getValue();
		        it.remove(); // avoids a ConcurrentModificationException
		    }
	//	    String url = "http://127.0.0.1:8888/karticno/report?"+params;
		}
//	    String url = GWT.getModuleBaseURL()+"report"+params;
	    String url = GWT.getModuleBaseURL()+"report"+params;
	    com.google.gwt.user.client.Window.open(url,"_blank","");
	}


}
