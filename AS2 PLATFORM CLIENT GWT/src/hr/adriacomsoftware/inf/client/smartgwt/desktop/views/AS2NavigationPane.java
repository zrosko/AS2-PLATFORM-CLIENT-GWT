package hr.adriacomsoftware.inf.client.smartgwt.desktop.views;
import hr.adriacomsoftware.inf.client.smartgwt.desktop.perspective.records.AS2NavigationPaneRecord;
import hr.adriacomsoftware.inf.client.smartgwt.desktop.perspective.records.AS2NavigationPaneSection;
import hr.adriacomsoftware.inf.client.smartgwt.desktop.perspective.xml.AS2NavigationPaneSectionXml;
import hr.adriacomsoftware.inf.client.smartgwt.desktop.perspective.xml.AS2NavigationPaneSectionXmlModel;

import com.google.gwt.core.client.GWT;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.VisibilityMode;
import com.smartgwt.client.widgets.grid.events.RecordClickHandler;
import com.smartgwt.client.widgets.layout.SectionStack;
import com.smartgwt.client.widgets.layout.SectionStackSection;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.layout.events.SectionHeaderClickEvent;
import com.smartgwt.client.widgets.layout.events.SectionHeaderClickHandler;

public class AS2NavigationPane extends VLayout {

    private static final int WEST_WIDTH = 200; //TODO isto kao i navigation pane header
    private SectionStack sectionStack;
//	public Record _selectedRecord;
    public AS2NavigationPane() {
        super();
        GWT.log("init NavigationPane()...", null);
        // initialise the Navigation Pane layout container
        this.setStyleName("crm-NavigationPane");
        this.setWidth(WEST_WIDTH);
        // this.setShowResizeBar(true);
        // initialise the Section Stack
        sectionStack = new SectionStack();
        sectionStack.setID("AS2NavigationPaneSectionStack");
        sectionStack.setWidth100();
//        sectionStack.setVisibilityMode(VisibilityMode.MUTEX);
        sectionStack.setVisibilityMode(VisibilityMode.MULTIPLE);
        sectionStack.setShowExpandControls(true);
//        sectionStack.setCanResizeSections(true);
        sectionStack.setAnimateSections(true);

        sectionStack.addSectionHeaderClickHandler(getSectionHeaderClickHandler());
        // add the Section Stack to the Navigation Pane layout container
        this.addMember(sectionStack);
    }

    public SectionStack getSectionStack(){
    	return sectionStack;
    }
    
    public void selectRecord(String name) {

    	SectionStackSection[] sections = sectionStack.getSections();

    	// Log.debug("Number of sections: " + sections.length);

    	for (int i = 0; i < sections.length; i++) {
    		SectionStackSection sectionStackSection = sections[i];

    		if (((AS2NavigationPaneSectionXml) sectionStackSection).getRecordNum(name) != -1) {

    			if (!sectionStack.sectionIsExpanded(i)) {
    				sectionStack.expandSection(i);
    			}
    			if(((AS2NavigationPaneSectionXml) sectionStackSection).selectRecord(name))
    				break;
    			//OLD
//    			((AS2NavigationPaneSectionXml) sectionStackSection).selectRecord(name);
////    			_selectedRecord = ((AS2NavigationPaneSectionXml) sectionStackSection).getRecord(name);
//    			break;
    		}
    	}
    }

    public void add(String sectionName, AS2NavigationPaneRecord[] sectionData, RecordClickHandler clickHandler) {
        sectionStack.addSection(new AS2NavigationPaneSection(sectionName, sectionData, clickHandler));
    }
    public void addSection(String sectionName, DataSource dataSource) {
        sectionStack.addSection(new AS2NavigationPaneSectionXml(sectionName, dataSource));
    }
    
    public SectionStackSection getSection(String sectionID){
    	return sectionStack.getSection(sectionID);
    }

    public void addSection(SectionStackSection section) {
        sectionStack.addSection(section);
      }
    public SectionStackSection[] getSections() {
    	return sectionStack.getSections();
  	}
    public void expandSection(int section) {
		sectionStack.expandSection(section);
	}

    public void expandSection(int section,String recordName) {
		sectionStack.expandSection(section);
		selectRecord(recordName);
	}

	public void expandSection(String name, String recordName) {
		sectionStack.expandSection(name);
		selectRecord(recordName);
	}

    public void addSectionHeaderClickHandler(SectionHeaderClickHandler clickHandler) {
        sectionStack.addSectionHeaderClickHandler(clickHandler);
      }
    public void addRecordClickHandler(String sectionName, RecordClickHandler clickHandler) {

        // Log.debug("addRecordClickHandler(sectionName, clickHandler) - " + sectionName);

        SectionStackSection[] sections = sectionStack.getSections();

        for (int i = 0; i < sections.length; i++) {
          SectionStackSection sectionStackSection = sections[i];

          if (sectionName.contentEquals(sections[i].getTitle())) {
            ((AS2NavigationPaneSectionXml) sectionStackSection).addRecordClickHandler(clickHandler);
          }
        }
      }
    
    protected SectionHeaderClickHandler getSectionHeaderClickHandler() {
    	return new SectionHeaderClickHandler() {
			@Override
			public void onSectionHeaderClick(SectionHeaderClickEvent event) {
		    	SectionStackSection sec = event.getSection();
		    	if(sec instanceof AS2NavigationPaneSectionXml){
		    		AS2NavigationPaneSectionXml section = (AS2NavigationPaneSectionXml)sec;
		    		if(((AS2NavigationPaneSectionXml) section).getSelectedRecord()!=null){		    			
		    		
		    		String name = ((AS2NavigationPaneSectionXml) section).getSelectedRecord();
//			    	 If there is no selected record (e.g. the data hasn't finished loading)
			    	// then getSelectedRecord() will return an empty string.
//			    	System.out.println("Section " + section.getID()+ " is expanded = "+ section.getSectionStack().sectionIsExpanded(section.getID()));
		    		if(section.getListGrid()!=null){	
		    			if(!sectionStack.sectionIsExpanded(section.getID())){
	//			    		name= section.getDefaultPerspectiveName();
				    		name= "";
				    		Record record = null;
				    		if(name.equals("")){
				    			record =  section.getListGrid().getRecord(0);
				    		}
				    		else{
				    			if(section.selectRecord(name))
				    				record = section.getListGrid().getSelectedRecord();
				    			else
				    				record =  section.getListGrid().getRecord(0);
	
				    		}
				    		if(record!=null){
				    			section.getListGrid().deselectAllRecords();
				    			section.getListGrid().selectRecord(record);
				    			AS2GwtDesktop.getInstance().setContextAreaViewXml(record.getAttribute(AS2NavigationPaneSectionXmlModel.NAME), record.getAttribute(AS2NavigationPaneSectionXmlModel.VIEW_DISPLAY_NAME));
				    		}
				    	}else{
				    		AS2GwtDesktop.getInstance().setContextAreaViewXml("","");
				    	}
			    	}
		    		}
		    	}
			}
		};
    }
    

//	public void setSelectedRecord(String name) {
//		SectionStackSection[] sections = sectionStack.getSections();
//    	for (int i = 0; i < sections.length; i++) {
//    		SectionStackSection sectionStackSection = sections[i];
//    		if (((AS2NavigationPaneSectionXml) sectionStackSection).getRecordNum(name) != -1) {
//    			_selectedRecord = ((AS2NavigationPaneSectionXml) sectionStackSection).getRecord(name);
//    			break;
//    		}
//    	}
//	}
//	
//	public Record getSelectedRecord() {
//    	return	_selectedRecord;
//	}

}
