package hr.adriacomsoftware.inf.client.smartgwt.desktop.perspective.records;

import com.google.gwt.core.client.GWT;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.ListGridFieldType;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.events.RecordClickHandler;
import com.smartgwt.client.widgets.layout.SectionStackSection;

public class AS2NavigationPaneSection extends SectionStackSection {

    private ListGrid listGrid;
    private AS2NavigationPaneRecord[] sectionData;

    public AS2NavigationPaneSection(String sectionName, AS2NavigationPaneRecord[] sectionData, RecordClickHandler clickHandler) {
        super(sectionName);
        GWT.log("init NavigationPaneSection()...", null);
        this.sectionData = sectionData;
        // initialise the List Grid
        listGrid = new ListGrid();
        listGrid.setBaseStyle("crm-NavigationPaneGridCell");
        listGrid.setWidth("100%");
        listGrid.setHeight("100%");
        listGrid.setShowAllRecords(true);
        listGrid.setShowHeader(false);
        // initialise the Icon field
        ListGridField appIconField = new ListGridField("icon", "Icon", 27);
        appIconField.setImageSize(16);
        appIconField.setAlign(Alignment.RIGHT);
        appIconField.setType(ListGridFieldType.IMAGE);
        appIconField.setImageURLPrefix("icons/16/");
        appIconField.setImageURLSuffix(".png");
        appIconField.setCanEdit(false);
        // initialise the Name field
        ListGridField appNameField = new ListGridField("name", "Name");
        // add the fields to the list Grid
        listGrid.setFields(appIconField, appNameField);
        // set up the column data
        listGrid.setData(sectionData);
        listGrid.selectRecord(0);
        // register the click handler
        listGrid.addRecordClickHandler(clickHandler);
        // section.setItems(appList);
        this.addItem(listGrid);
        this.setExpanded(true);
    }

    public ListGrid getListGrid() {
        return listGrid;
    }

    public void selectRecord(String name) {
        for (int i = 0; i < this.sectionData.length; i++) {
            AS2NavigationPaneRecord record = this.sectionData[i];

            if (name.contentEquals(record.getName())) {
                GWT.log("selectRecord->name.contentEquals(record.getName())", null);
                getListGrid().deselectAllRecords();
                getListGrid().selectRecord(i);
            }
        }
    }

    public int getRecord(String appName) {
        int result = -1;
        for (int i = 0; i < this.sectionData.length; i++) {
            AS2NavigationPaneRecord record = this.sectionData[i];

            if (appName.contentEquals(record.getName())) {
                GWT.log("selectRecord->name.contentEquals(record.getName())", null);
                result = i;
            }
        }

        return result;
    }

    public void setSectionData(AS2NavigationPaneRecord[] sectionData) {
        this.sectionData = sectionData;
    }

    public AS2NavigationPaneRecord[] getSectionData() {
        return sectionData;
    }
}
