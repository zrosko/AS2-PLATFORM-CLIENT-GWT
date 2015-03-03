package hr.adriacomsoftware.inf.client.smartgwt.validators.iban;

import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.DataSourceField;
import com.smartgwt.client.types.DSDataFormat;
import com.smartgwt.client.types.FieldType;

public class CountriesLengthXMLModel extends DataSource {
	  private static final String RECORD_XPATH = "/list/country";
	  public static final String NAME = "name";
	  public static final String LENGTH = "length";
	  
	private static CountriesLengthXMLModel instance = new CountriesLengthXMLModel("CountriesLengthXMLModel");

	public static CountriesLengthXMLModel getInstance() {
		return instance;
	}

	public CountriesLengthXMLModel(String id) {
		setID(id);
	    setDataFormat(DSDataFormat.XML);
	    setRecordXPath(RECORD_XPATH);
	    DataSourceField nameField = new DataSourceField(NAME, FieldType.TEXT, NAME);
	    DataSourceField lengthField = new DataSourceField(LENGTH, FieldType.TEXT, LENGTH);
	    setFields(nameField, lengthField);
		setDataURL("models/IBAN/countries_length.xml");
	}
}
