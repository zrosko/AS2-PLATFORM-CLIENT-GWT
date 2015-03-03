package hr.adriacomsoftware.inf.client.smartgwt.desktop.perspective.xml;

import com.smartgwt.client.data.Record;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.RecordClickHandler;

public interface AS2NavigationPaneSectionItem {
	
	 public void selectRecord(int record);
	 public boolean selectRecord(String name);
	 public int getRecordNum(String name);
	 public Record getRecord(String name);
	 public String getSelectedAS2Record();
	 public void addRecordClickHandlerAs2(RecordClickHandler clickHandler);
	 public String getRecordViewDisplayName(String name);
	 public String getRecordDisplayName(String name);
	 public ListGridRecord getRecordByName(String name);
	 public String getDefaultPerspectiveName();

}
