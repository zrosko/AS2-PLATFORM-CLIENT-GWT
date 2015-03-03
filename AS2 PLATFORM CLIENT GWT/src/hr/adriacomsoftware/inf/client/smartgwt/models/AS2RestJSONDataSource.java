package hr.adriacomsoftware.inf.client.smartgwt.models;

import hr.adriacomsoftware.inf.client.smartgwt.desktop.AS2CacheManager;
import hr.adriacomsoftware.inf.client.smartgwt.types.AS2DataSourceField;

import java.util.HashMap;
import java.util.LinkedHashMap;

import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.DataSourceField;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.data.RestDataSource;
import com.smartgwt.client.types.DSDataFormat;
import com.smartgwt.client.types.DSOperationType;
import com.smartgwt.client.util.SC;

/**
 * 
 * @author astrikom
 *
 */
public abstract class AS2RestJSONDataSource extends RestDataSource {

	public static final String SERVICE = "Service";
	public static final String COMPONENT = "Component";
	public static final String REMOTE_OBJECT = "remoteobject";
	public static final String REMOTE_METHOD = "remotemethod";
	public static final String TRANSFORM_TO = "transform_to";
	public static final String APLIKACIJA = "aplikacija";
	public static final String DEFAULT_DATA_URL = "json_servlet";
	public static final String CHARTS_DATA_URL = "json_servlet_charts";
	public static final String MODEL = "model";
	public static final String AS2_RECORD_X_PATH = "response/data/record";
	protected AS2CacheManager cache_manager;
	public LinkedHashMap<String, Object> _cache = new LinkedHashMap<String, Object>();
	
	public AS2RestJSONDataSource(String id) {
		this.setDataFormat(DSDataFormat.JSON);
		this.setID(id);
		this.setDataURL(DEFAULT_DATA_URL);
		this.setRecordXPath(AS2_RECORD_X_PATH);
	}
	
	public AS2RestJSONDataSource(String id, String dataUrl) {
		this(id,dataUrl,AS2_RECORD_X_PATH);
	}
	
	public AS2RestJSONDataSource(String id, String dataUrl, String recordXPath) {
		this.setDataFormat(DSDataFormat.JSON);
		this.setID(id);
		this.setDataURL(dataUrl);
		this.setRecordXPath(recordXPath);
	}
	
	protected Object getRecordType(){
		return new Record();
	};
	
	/**
	 * @return the cache
	 */
	public LinkedHashMap<String, Object> getCache() {
		return _cache;
	}
	
	@SuppressWarnings("unchecked")
	public LinkedHashMap<String, Object>  getCache(String name) {
		if(_cache.get(name)==null){
			return null;
		}else
			return (LinkedHashMap<String, Object>) _cache.get(name);
	}
	
	@SuppressWarnings("unchecked")
	public String getCacheDisplayValue(String map_name,String name) {
		if(_cache.get(map_name)==null && 
				 ((LinkedHashMap<String, Object>) _cache.get(map_name)).get(name)==null){
			return null;
		}else
			return ((LinkedHashMap<String, Object>) _cache.get(map_name)).get(name).toString();
	}

	//Todo zamjeniti _cache sa ovime
	@Override
	public Record[] getCacheData() {
		return super.getCacheData();
	}

	/**
	 * @param cache the cache to set
	 */
	public void setCache(LinkedHashMap<String, Object> cache) {
		this._cache = cache;
	}
	
//	TODO
	@Override
	protected void transformResponse(DSResponse dsResponse,
			DSRequest dsRequest, Object data) {
		//ERROR HANDLING - using SmartGwt centralized error handling
		//metaData
//		RecordList metaData = RecordList.getOrCreateRef(getAttributeAsJavaScriptObject("meta"));
//		if(XMLTools.selectObjects(data, "/response/data/meta")!=null){
//			JSONArray json_meta_array = XMLTools.selectObjects(data, "/response/data/meta");
//			RecordList meta_records = new RecordList(); 
//			for(int i=0;i<json_meta_array.size();i++){
//				JSONObject json_meta_object = (JSONObject)json_meta_array.get(i);
//				Record record = new Record();
//				record.setAttribute("_columnLabel",json_meta_object.get("_columnLabel").toString());
//				record.setAttribute("_columnTypeName",json_meta_object.get("_columnTypeName").toString());
//				record.setAttribute("_columnType",json_meta_object.get("_columnType").toString());
//				record.setAttribute("_columnDisplaySize",json_meta_object.get("_columnDisplaySize").toString());
//				meta_records.add(record);
//			}
//			dsResponse.setAttribute("meta",meta_records);
//		}
		RecordList records = null;
		if (dsResponse.getDataAsRecordList() != null) {
			records = dsResponse.getDataAsRecordList();
			records = getRecordsFromServerJSON(records);//Client customization of data from server
		}
		if(records != null){
			dsResponse.setAttribute("data",records.toArray());//Avoids ListGridRecord error
		}
		//prije
//		JSONArray statusJSON = XMLTools.selectObjects(data, "/response/status");
//		if(((JSONString) statusJSON.get(0))!=null){//AS2LOGIKA
//			String status=((JSONString) statusJSON.get(0)).stringValue();
//			dsResponse.setStatus(Integer.parseInt(status));
//			if (dsResponse.getStatus() < RPCResponse.STATUS_SUCCESS){// ERROR HANDLING
//				String dataString;
//				if(dsResponse.getStatus() == RPCResponse.STATUS_LOGIN_REQUIRED){
//					AS2ClientContext.showLoginForm();
//					dataString = AS2GwtDesktop._messages.user_loginRequired();
//				}else{
//					JSONArray dataJSON = XMLTools.selectObjects(data, "/response/data");
//					dataString = ((JSONString) dataJSON.get(0)).stringValue();
//				
//				}
//				dsResponse.setAttribute("data", dataString);
//			} else {
//				// String jsoText1 = JSON.encode((JavaScriptObject)data);
//				if (dsResponse.getDataAsRecordList() != null) {
//					RecordList records = dsResponse.getDataAsRecordList();
//					records = getRecordsFromServerJSON(records);
//					dsResponse.setAttribute("data",records.toArray());
//				} else {
//					noData();
//				}
//			}
//		}
		super.transformResponse(dsResponse, dsRequest, data);
	}

	@Override
	protected Object transformRequest(DSRequest dsRequest) {
		dsRequest.getStartRow();
		dsRequest.getEndRow();
		if(getDefaultProperties()!=null){
			setDefaultParams(getDefaultProperties());
		}
		if (dsRequest.getOperationType().equals(DSOperationType.ADD)) {
			dsRequest.setParams(getAddOperationProperties());
		} else if (dsRequest.getOperationType().equals(DSOperationType.UPDATE)) {
			dsRequest.setParams(getUpdateOperationProperties());
		} else if (dsRequest.getOperationType().equals(DSOperationType.REMOVE)) {
			dsRequest.setParams(getDeleteOperationProperties());
		} else if (dsRequest.getOperationType().equals(DSOperationType.FETCH)) {
			dsRequest.setParams(getFetchOperationProperties());
		} else if (dsRequest.getOperationType().equals(DSOperationType.CUSTOM)) {
			dsRequest.setParams(getCustomOperationProperties(
					dsRequest.getOperationId(),
					dsRequest.getAttribute(REMOTE_OBJECT),
					dsRequest.getAttribute(TRANSFORM_TO)));
		}
		if (dsRequest.getOldValues() != null) {// Mora biti na kraju transformRequest
			dsRequest.setParams(getOldValuesFromRequest(dsRequest));
		}
		dsRequest.getAttributes();
		dsRequest.getAttribute("izvor");
		return super.transformRequest(dsRequest);
	}

	// If autofetchdata is enabled on databound component
	private HashMap<String, String> getOldValuesFromRequest(DSRequest dsRequest) {
		HashMap<String, String> params = new HashMap<String, String>();
		Record oldValues = dsRequest.getOldValues();
		for (String oldValue : oldValues.getAttributes())
			params.put("_old_" + oldValue, oldValues.getAttribute(oldValue));
		Record operationProperties = dsRequest.getAttributeAsRecord("params");
		for (String attribute : operationProperties.getAttributes())
			params.put(attribute, operationProperties.getAttribute(attribute));
		return params;
	}

	public abstract String getRemoteObject();// Component

	public abstract String getTransformTo();// ValueObject on server

	// operationId = remotheMethod
	public HashMap<String, String> getCustomOperationProperties(
			String remoteMethod, String remoteObject, String transformTo) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put(AS2RestJSONDataSource.SERVICE, remoteMethod);
		params.put(AS2RestJSONDataSource.COMPONENT,
				remoteObject == null ? getRemoteObject() : remoteObject);
		params.put(AS2RestJSONDataSource.TRANSFORM_TO,
				transformTo == null ? getTransformTo() : transformTo);
		return params;
	}
	public HashMap<String, String> getDefaultProperties(){return null;}

	public abstract HashMap<String, String> getAddOperationProperties();

	public abstract HashMap<String, String> getUpdateOperationProperties();

	public abstract HashMap<String, String> getDeleteOperationProperties();

	public abstract HashMap<String, String> getFetchOperationProperties();

	public Criteria getOperationParamsAsCriteria(HashMap<String, String> params) {
		Criteria criteria = new Criteria();
		for (String key : params.keySet()) {
			criteria.addCriteria(key, params.get(key));
		}
		return criteria;
	}

	// Kad koristimo AS2ItemFactory
	public void setFields(AS2DataSourceField... fields) throws IllegalStateException {
		DataSourceField[] dsFields = new DataSourceField[fields.length];
		for (int i = 0; i < fields.length; i++) {
			dsFields[i] = (fields[i].getField());
		}
		super.setFields(dsFields);
	}
	
	// public RecordList getRecordsFromServerJSON(RecordList records,DSRequest
	// dsRequest) {
	// RecordList formattedRecords = new RecordList();
	// if (records != null) {
	// for (Record record : records.toArray()) {
	// for (String attribute : record.getAttributes()) {
	// if (record.getAttributeAsObject(attribute) != null
	// && record.getAttribute(attribute).length() != 0) {
	// if (this.getField(attribute) != null) {
	// String type = this.getField(attribute).getType()
	// .toString().toLowerCase();
	// // if(type.equals("date")){
	// // record.setAttribute(attribute,record.getAttributeAsDate(attribute));
	// // }
	// if (type.equals("float")) {
	// record.setAttribute(attribute,
	// AS2ClientContext.formatNumber(record.getAttribute(attribute)));
	// }
	// }
	// }
	// }
	// record = formatRecordFromServerJSON(record); // Dodatno formatiranje
	// prema želji
	// if (!(record instanceof ListGridRecord)) {
	// ListGridRecord lrecord = new ListGridRecord();
	// Record.copyAttributesInto(lrecord, record,
	// record.getAttributes());
	// formattedRecords.add(lrecord);
	// } else
	// formattedRecords.add(record);
	// }
	// }
	// return formattedRecords;
	// }

	public RecordList getRecordsFromServerJSON(RecordList records) {
		RecordList formattedRecords = null;
		if (records != null) {
			formattedRecords = new RecordList();
			for (Record record : records.toArray()) {
				for (String attribute : record.getAttributes()) {
					if (record.getAttributeAsObject(attribute) != null
							&& record.getAttribute(attribute).length() != 0) {
						if (this.getField(attribute) != null) {
							// TODO da li treba?
							// String fieldType="text";
							// fieldType =
							// this.getField(attribute).getType().toString().toLowerCase();
							// if(type.equals("date")){
							// record.setAttribute(attribute,record.getAttributeAsDate(attribute));
							// }
							// if (fieldType.equals("float")) {
							// record.setAttribute(attribute,
							// AS2ClientContext.formatNumber(record.getAttribute(attribute)));
							// }
						}
					}
				}
				// Dodatno formatiranje prema želji
				record = formatRecordFromServerJSON(record);
				// //Provjera tipa Recorda (ListGridRecord ili Record)
				// Record _ref=null;
				// record.getAttributes();
				// if(record.getAttributeAsRecord("_ref")==null){
				// if(record.getAttributeAsRecord("_old__ref")!=null){
				// _ref = record.getAttributeAsRecord("_old__ref");
				// }
				// }else{
				// _ref = record.getAttributeAsRecord("_ref");
				// }
				// if(_ref!=null){
				// if(_ref instanceof ListGridRecord){
				// ListGridRecord lrecord = new ListGridRecord();
				// Record.copyAttributesInto(lrecord, record,
				// record.getAttributes());
				// formattedRecords.add(lrecord);
				// } else
				// formattedRecords.add(record);
				//}else{
//				record.getAttributes();
//				if (getRecordType() instanceof ListGridRecord) {
//					ListGridRecord lrecord = new ListGridRecord();
//					Record.copyAttributesInto(lrecord, record,
//							record.getAttributes());
//					formattedRecords.add(lrecord);
//				} else if (getRecordType() instanceof TreeNode) {
//					TreeNode treeNode = new TreeNode();
//					Record.copyAttributesInto(treeNode, record,
//							record.getAttributes());
//					formattedRecords.add(treeNode);
//				} else
				formattedRecords.add(record);
			}
		}
		return formattedRecords;
	}

	// Koristimo kod polja koji nisu definirani u datasourceu. Njima moramo
	// definirati tipove
//	protected HashMap<String, String> getFieldTypes() {
//		return null;
//	}

	// Implement in model if record needs formatting for display
	protected Record formatRecordFromServerJSON(Record record) {
		return record;
	}

	// Implement in model if record needs formatting for server
	protected Record formatRecordForServerJSON(Record record) {
		return record;
	}

	public boolean checkErrorsFromServer(RecordList records) {
		boolean error = false;
		Record record = records.get(0);
		record.getAttributes();
		if (record.getAttributeAsObject("error") != null) {
			error = true;
			SC.warn(record.getAttributeAsString("error"));
		}
		if (!error) {
			return false;
		}
		return true;
	}

	public void noData() {
		SC.warn("Nema podataka");
	}

	//Sifrarnik
	public void putData(RecordList records) {
		cache_manager = new AS2CacheManager();
		LinkedHashMap<String, Object> valueMap = new LinkedHashMap<String, Object>();
		String temp = null;
		for (int i = 0; i < records.getLength(); i++) {
			if (records.get(i) != null) {
				if (i == 0)
					temp = records.get(i).getAttribute("vrsta");

				if (!temp.equals(records.get(i)
						.getAttributeAsString("vrsta"))) {
					cache_manager.put(temp,valueMap);
					temp = records.get(i).getAttributeAsString(	"vrsta");
					valueMap = new LinkedHashMap<String, Object>();
				}
				if (temp.equals(records.get(i).getAttributeAsString("vrsta"))) {
					valueMap.put(records.get(i).getAttributeAsString("id_sifre"),records.get(i).getAttributeAsString("naziv_sifre"));
				}
				if (i == (records.getLength() - 1))
					cache_manager.put(temp,valueMap);
			}
		}

	}
}
