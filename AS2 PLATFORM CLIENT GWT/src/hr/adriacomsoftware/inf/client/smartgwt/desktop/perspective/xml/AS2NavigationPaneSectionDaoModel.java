package hr.adriacomsoftware.inf.client.smartgwt.desktop.perspective.xml;

import java.util.HashMap;

import hr.adriacomsoftware.inf.client.smartgwt.core.AS2ClientContext;
import hr.adriacomsoftware.inf.client.smartgwt.models.AS2RestJSONDataSource;

import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.DataSourceField;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.FieldType;

public abstract class AS2NavigationPaneSectionDaoModel extends AS2RestJSONDataSource implements AS2NavigationPaneSectionModel {

	public static String DEFAULT_PERSPECTIVE_NAME="zahtjevi";
	public static String DEFAULT_PERSPECTIVE_DISPLAY_NAME="Zahtjevi";
	public static String DEFAULT_PERSPECTIVE_VIEW_DISPLAY_NAME="Zahtjevi za MasterCard kreditnom karticom";

	public AS2NavigationPaneSectionDaoModel(String id) {
		super(id,DEFAULT_DATA_URL);
		DataSourceField iconField = new DataSourceField(ICON, FieldType.TEXT, ICON_DISPLAY_NAME);
		DataSourceField nameField = new DataSourceField(NAME, FieldType.TEXT, NAME_DISPLAY_NAME);
		DataSourceField displayNameField = new DataSourceField(DISPLAY_NAME, FieldType.TEXT, DISPLAY_NAME_DISPLAY_NAME);
		DataSourceField viewDisplayNameField = new DataSourceField(VIEW_DISPLAY_NAME, FieldType.TEXT, VIEW_DISPLAY_NAME_DISPLAY_NAME);
		DataSourceField defaultNameField = new DataSourceField(DEFAULT_FUNCTION_NAME, FieldType.TEXT, DEFAULT_FUNCTION_NAME);
//		DataSourceField defaultDisplayNameField = new DataSourceField(DEFAULT_FUNCTION_DISPLAY_NAME, FieldType.TEXT, DEFAULT_FUNCTION_DISPLAY_NAME);
//		DataSourceField defaultViewDisplayNameField = new DataSourceField(DEFAULT_FUNCTION_VIEW_DISPLAY_NAME, FieldType.TEXT, DEFAULT_FUNCTION_VIEW_DISPLAY_NAME);
		setFields(iconField, nameField, displayNameField,viewDisplayNameField,defaultNameField);
	}
	
	@Override
	protected Record formatRecordFromServerJSON(Record record) {
		if(record.getAttributeAsObject(ICON)==null)
			record.setAttribute(ICON, "default");
		return record;
	}

	@Override
	protected void transformResponse(DSResponse dsResponse,
			DSRequest dsRequest, Object data) {
		super.transformResponse(dsResponse, dsRequest, data);
	}
	
	@Override
	public String getRemoteObject() {
		return "hr.as2.inf.server.security.authorization.facade.AS2AuthorizationFacadeServer";
	}
	
	public String getRemoteMethod() {
		return "procitajKomponenteKorisnika";
	}

	@Override
	public String getTransformTo() {
		return "hr.as2.inf.common.security.dto.AS2KomponentaVo";
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
		params.put(REMOTE_METHOD,getRemoteMethod());
		params.put(REMOTE_OBJECT,getRemoteObject());
		params.put(TRANSFORM_TO,getTransformTo());
		params.put(MODEL, this.getID());
		params.put(APLIKACIJA, AS2ClientContext.getSessionValue(AS2ClientContext.APPLICATION_ID));
		return params;
	}

	
}
