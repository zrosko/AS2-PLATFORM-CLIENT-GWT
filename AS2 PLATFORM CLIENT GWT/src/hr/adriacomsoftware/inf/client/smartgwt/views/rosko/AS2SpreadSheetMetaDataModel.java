package hr.adriacomsoftware.inf.client.smartgwt.views.rosko;

import hr.adriacomsoftware.inf.client.smartgwt.models.AS2RestJSONDataSource;
import hr.adriacomsoftware.inf.client.smartgwt.types.AS2DataSourceField;
import hr.adriacomsoftware.inf.client.smartgwt.types.AS2Field;

import java.util.HashMap;

import com.smartgwt.client.data.RecordList;

/**
 * @author zrosko, astrikoman
 *
 */
public class AS2SpreadSheetMetaDataModel extends AS2RestJSONDataSource  {	
	private static AS2SpreadSheetMetaDataModel instance =  null;
	
	public final static String AS2_META__LABEL = "_columnLabel";
	public final static String AS2_META__TYPE_NAME = "_columnTypeName";
	public final static String AS2_META__TYPE = "_columnType";
	public final static String AS2_META__DISPLAY_SIZE = "_columnDisplaySize";
	public final static String AS2_META__PRECISION = "_precision";
	public final static String AS2_META__SCALE = "_scale";
	public final static String AS2_META__SEQUENCE = "_sequence";
	
	private RecordList metaData;
	//Not Singleton (each table has its own)
	public static AS2SpreadSheetMetaDataModel getInstance() {
		if(instance==null)
			instance = new AS2SpreadSheetMetaDataModel("AS2SpreadSheetMetaDataModel_as2");//as2 is just test default
		return instance;
	}
	
	public AS2SpreadSheetMetaDataModel(String tableName) {
		super("AS2SpreadSheetMetaDataModel_as2"+tableName);
		instance = this;
//		AS2DataSourceField _columnLabel = new AS2DataSourceField(AS2_META__LABEL,AS2Field.PRIMARY_KEY, "Column Label");
		AS2DataSourceField _columnLabel = new AS2DataSourceField(AS2_META__LABEL,AS2Field.TEXT, "Column Label");
		AS2DataSourceField _columnTypeName = new AS2DataSourceField(AS2_META__TYPE_NAME,AS2Field.TEXT, "Column Type Name");
		AS2DataSourceField _columnType = new AS2DataSourceField(AS2_META__TYPE,AS2Field.TEXT, "Column Type");
		AS2DataSourceField _columnDisplaySize = new AS2DataSourceField(AS2_META__DISPLAY_SIZE, AS2Field.TEXT,"Column Display Size");
		AS2DataSourceField _precision = new AS2DataSourceField(AS2_META__PRECISION, AS2Field.TEXT,"Precision");
		AS2DataSourceField _scale = new AS2DataSourceField(AS2_META__SCALE, AS2Field.TEXT,"Scale");
		AS2DataSourceField _sequence = new AS2DataSourceField(AS2_META__SEQUENCE, AS2Field.TEXT,"Sequence");		
		this.setFields(_columnLabel,_columnTypeName,_columnType,_columnDisplaySize,_precision,_scale,_sequence);
		//this.setCacheAllData(true);///////BITNO
		//this.setCacheMaxAge(24*60*60);///////BITNO
	}

	public RecordList getMetaData() {
		return metaData;
	}
	public void setMetaData(RecordList metaData) {
		this.metaData = metaData;
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
		return null;
	}

	@Override
	public HashMap<String, String> getUpdateOperationProperties() {
		return null;
	}

	@Override
	public HashMap<String, String> getDeleteOperationProperties() {
		return null;
	}

	@Override
	public HashMap<String, String> getFetchOperationProperties() {
		HashMap<String,String> params = new HashMap<String,String>();
		params.put(REMOTE_METHOD,"fetchTable");
		params.put(REMOTE_OBJECT,getRemoteObject());
		params.put(TRANSFORM_TO,getTransformTo());
		return params;
	}
}
