package hr.adriacomsoftware.inf.client.smartgwt.views.rosko;

import hr.adriacomsoftware.inf.client.smartgwt.models.AS2RestJSONDataSource;
import hr.adriacomsoftware.inf.client.smartgwt.types.AS2DataSourceField;
import hr.adriacomsoftware.inf.client.smartgwt.types.AS2Field;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.smartgwt.client.data.DataSourceField;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;

/**
 * @author zrosko, astrikoman
 *
 */
public class AS2SpreadSheetModel extends AS2RestJSONDataSource  {
	
	private static HashMap<String,AS2SpreadSheetModel> instanceMap =  null;
	
	// Not Singleton (each table has its own)
	public static AS2SpreadSheetModel getInstance(String tableName) {
		if (instanceMap == null)
			instanceMap = new HashMap<String,AS2SpreadSheetModel>();
														
		AS2SpreadSheetModel instance = instanceMap.get(tableName);
		if(instance == null){
			instance = new AS2SpreadSheetModel("AS2SpreadSheetModel_as2"+tableName);
			instanceMap.put(tableName, instance);
		}
		return instance;
	}
	
	public AS2SpreadSheetModel(String id) {
		super(id);
		RecordList metaData = AS2SpreadSheetMetaDataModel.getInstance().getMetaData();
		List<DataSourceField> fields = new ArrayList<>();
		for(Record record: metaData.toArray()){
			String name = record.getAttribute("_columnLabel");
			String sqlType = record.getAttribute("_columnType");
			String title = record.getAttribute("_columnLabel");
			AS2DataSourceField field;
			if(name.startsWith("id_"))
				field = new AS2DataSourceField(name,AS2Field.PRIMARY_KEY,title);
			else
				field = new AS2DataSourceField(name,AS2Field.getType(sqlType),title);
			fields.add(field.getField());
		}
		setFields(fields.toArray(new DataSourceField[fields.size()]));
	}
	
	@Override
	public String getRemoteObject() {
		return "hr.as2.inf.server.security.authorization.facade.AS2AuthorizationFacadeServer";
	}

	@Override
	public String getTransformTo() {
		return "hr.as2.inf.common.data.AS2Record";
	}

	@Override
	public HashMap<String, String> getAddOperationProperties() {
		HashMap<String,String> params = new HashMap<String,String>();
		params.put(REMOTE_METHOD,"addTableRow");
		params.put(REMOTE_OBJECT,getRemoteObject());
		params.put(TRANSFORM_TO,getTransformTo());
		return params;
	}

	@Override
	public HashMap<String, String> getUpdateOperationProperties() {
		HashMap<String,String> params = new HashMap<String,String>();
		params.put(REMOTE_METHOD,"updateTableRow");
		params.put(REMOTE_OBJECT,getRemoteObject());
		params.put(TRANSFORM_TO,getTransformTo());
		return params;
	}

	@Override
	public HashMap<String, String> getDeleteOperationProperties() {
		HashMap<String,String> params = new HashMap<String,String>();
		params.put(REMOTE_METHOD,"deleteTableRow");
		params.put(REMOTE_OBJECT,getRemoteObject());
		params.put(TRANSFORM_TO,getTransformTo());
		return params;
	}

	@Override
	public HashMap<String, String> getFetchOperationProperties() {
		System.out.println("2-900 = "+isCreated());
		HashMap<String,String> params = new HashMap<String,String>();
		params.put(REMOTE_METHOD,"fetchTable");
		params.put(REMOTE_OBJECT,getRemoteObject());
		params.put(TRANSFORM_TO,getTransformTo());
		return params;
	}
}
