package hr.adriacomsoftware.inf.client.smartgwt.types.backup;

import hr.adriacomsoftware.inf.client.smartgwt.transport.rpc.GreetingService;
import hr.adriacomsoftware.inf.client.smartgwt.transport.rpc.GreetingServiceAsync;

import com.google.gwt.core.client.GWT;
import com.smartgwt.client.data.SimpleType;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.widgets.form.fields.ComboBoxItem;

public class ComboDSField extends SimpleType{
	protected static final GreetingServiceAsync server = GWT.create(GreetingService.class);

	public ComboDSField(String name)
	{
		super(name, FieldType.TEXT);
		ComboBoxItem _cbItem = new ComboBoxItem(name);

//		LinkedHashMap<String, String> lista = new LinkedHashMap<String, String>();
//		lista.put("kg", "kg");
//		lista.put("kom", "KOM");
//		lista.put("l", "l");
//		lista.put("m2", "m^2");
//		_cbItem.setValueMap(lista);

		setEditorProperties(_cbItem);
	}


}
