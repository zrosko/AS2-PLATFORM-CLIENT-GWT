package hr.adriacomsoftware.inf.client.smartgwt.views.rosko;

import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.Record;

import hr.adriacomsoftware.inf.client.smartgwt.views.AS2DynamicForm;
import hr.adriacomsoftware.inf.client.smartgwt.views.AS2ListGrid;
/**
 * 
 */

public interface AS2Layout {
	//TODO defaultLayoutPreferences
	public abstract void defaultWindowPreferences();
	public abstract void customWindowPreferences();
	public abstract void defaultWindowHandlers();
	public abstract void customWindowHandlers();
	public abstract void createComponents();
	public abstract void windowLayout();
	public abstract AS2DynamicForm getDynamicForm();
	public abstract AS2ListGrid getListGrid();
	public abstract DataSource getModel();
	//TODO rename
	public abstract DataSource getSifrarnikModel();
	public abstract void getValuesFromTrazi(Record selectedRecord);
	public abstract void setFieldValue(String name, Object value);
	public abstract void getLookUpsAndButtons();
}
