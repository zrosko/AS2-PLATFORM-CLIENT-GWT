/**
 * (C) Copyright 2010, 2011 upTick Pty Ltd
 *
 * Licensed under the terms of the GNU General Public License version 3
 * as published by the Free Software Foundation. You may obtain a copy of the
 * License at: http://www.gnu.org/copyleft/gpl.html
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */

package hr.adriacomsoftware.inf.client.smartgwt.desktop.perspective.xml;



import hr.adriacomsoftware.inf.client.smartgwt.desktop.views.AS2GwtDesktop;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.History;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.ListGridFieldType;
import com.smartgwt.client.types.SelectionStyle;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.DataArrivedEvent;
import com.smartgwt.client.widgets.grid.events.DataArrivedHandler;
import com.smartgwt.client.widgets.grid.events.RecordClickHandler;

public class AS2NavigationPaneSectionXmlListGrid extends ListGrid implements AS2NavigationPaneSectionItem {

//  private static final String URL_PREFIX = "icons/perspectives/";
  private static final String URL_PREFIX = "../"+GWT.getModuleName()+"/images/icons/perspectives/";
  private static final String URL_SUFFIX = ".png";
  private static final int CELL_HEIGHT = 30;
  private static final int ICON_FIELD_WIDTH = 30;
  private AS2NavigationPaneSectionXml _section;

  public AS2NavigationPaneSectionXmlListGrid(DataSource dataSource, AS2NavigationPaneSectionXml section) {
    super();
    this._section = section;
    // initialise the List Grid
    this.setBaseStyle("crm-NavigationPaneGridCell");
    this.setWidth100();
    this.setHeight100();
    this.setShowAllRecords(true);
    this.setShowHeader(false);
    this.setCellHeight(CELL_HEIGHT);
    this.setWrapCells(true);
    this.setSelectionType(SelectionStyle.SINGLE);
    // initialise the Icon field
    ListGridField appIconField = new ListGridField(AS2NavigationPaneSectionXmlModel.ICON);
    appIconField.setWidth(ICON_FIELD_WIDTH);
    appIconField.setImageSize(24);
    appIconField.setAlign(Alignment.RIGHT);
    appIconField.setType(ListGridFieldType.IMAGE);
    appIconField.setImageURLPrefix(URL_PREFIX);
    appIconField.setImageURLSuffix(URL_SUFFIX);
    appIconField.setCanEdit(false);

    // initialise the Display Name field
    //ListGridField appDisplayNameField = new ListGridField(AS2NavigationPaneSectionXmlModel.NAME,
    //AS2NavigationPaneSectionXmlModel.NAME_DISPLAY_NAME);
    ListGridField appDisplayNameField = new ListGridField(AS2NavigationPaneSectionXmlModel.DISPLAY_NAME);
    
    ListGridField appDefaultFunctionNameField = new ListGridField(AS2NavigationPaneSectionXmlModel.DEFAULT_FUNCTION_NAME);
    appDefaultFunctionNameField.setHidden(true);
    // set the fields into the List Grid
    this.setDataSource(dataSource,new ListGridField[] {appIconField, appDisplayNameField,appDefaultFunctionNameField});
    // populate the List Grid
    this.setAutoFetchData(true);
    //add handler
    this.addDataArrivedHandler(getDataArrivedHandler());
  }
  
  
  public AS2NavigationPaneSectionXml getSection() {
	  return _section;
  }

  public boolean selectRecord(String name) {
	  ListGridRecord[] records = this.getRecords();
	  ListGridRecord record = null;
	  String recordName = "";

	  for (int i = 0; i < records.length; i++) {

		  record = this.getRecord(i);
		  recordName = record.getAttributeAsString(AS2NavigationPaneSectionModel.NAME);

		  if (name.contentEquals(recordName)) {
			  this.deselectAllRecords();
			  this.selectRecord(i);
			  return true;
		  }
	  }

	  return false;
  }


  public DataArrivedHandler getDataArrivedHandler(){
	  return new DataArrivedHandler() {
	      @Override
	      public void onDataArrived(DataArrivedEvent event) {
	    	  AS2NavigationPaneSectionXmlListGrid listGrid = AS2NavigationPaneSectionXmlListGrid.this;
	    	  String name ="";
	    	  String view_display_name="";
	    	  Record record = null;
	    	  Record default_record = listGrid.getRecord(0);
	    	  if(listGrid.getDataSource() instanceof AS2NavigationPaneSectionDaoModel){
	    		  if(History.getToken().length()>0){
	    			  name =  History.getToken();
	    			  record = listGrid.getRecord(name);
	    		  }else{
	    			  record = listGrid.getRecord(0);
	    		  }
	    	  }else if(!listGrid.getDefaultPerspectiveName().equals("")){
	    		  record = getRecord(listGrid.getDefaultPerspectiveName());
	    	  }else if(_section.getDefaultPerspectiveName().equals("")){
	    		  record = default_record;
	    	  }
	    	  if(record!=null){
	    		  if(name.equals("") && 
	    				  record.getAttributeAsObject(AS2NavigationPaneSectionModel.NAME)!=null){
	    			  name = record.getAttribute(AS2NavigationPaneSectionModel.NAME);
	    		  }
	    		  listGrid.selectRecord(record);
	    		  _section.getSectionStack().expandSection(_section.getID());
	    		  if( record.getAttributeAsObject(AS2NavigationPaneSectionModel.VIEW_DISPLAY_NAME)!=null)
	    			  view_display_name = record.getAttribute(AS2NavigationPaneSectionModel.VIEW_DISPLAY_NAME);
	    		  AS2GwtDesktop.getInstance().setDefaultViewName(default_record.getAttribute(AS2NavigationPaneSectionModel.NAME));
	    		  AS2GwtDesktop.getInstance().setDefaultViewDisplayName(default_record.getAttribute(AS2NavigationPaneSectionModel.VIEW_DISPLAY_NAME));
	    		  AS2GwtDesktop.getInstance().setContextAreaViewXml(name, view_display_name);
	    	  }
	    	  
	    	  
	    	  
	    	  
	    	  
	    	  
	    	  
	    	  
	    	  
	    	  
	    	  
	    	  //old
//	    	  AS2NavigationPaneSectionXmlListGrid listGrid = AS2NavigationPaneSectionXmlListGrid.this;
//	    	  if(listGrid.getDataSource() instanceof AS2NavigationPaneSectionDaoModel){
//	    		  Record default_record = listGrid.getRecords()[0];
//	    		  if(default_record!=null && getRecord(default_record.getAttribute(AS2NavigationPaneSectionXmlModel.DEFAULT_FUNCTION_NAME))!=null){
//	    			  default_record= getRecord(default_record.getAttribute(AS2NavigationPaneSectionXmlModel.DEFAULT_FUNCTION_NAME));
//	    			  listGrid.selectRecord(default_record);
//	    			  String name = default_record.getAttribute(AS2NavigationPaneSectionModel.NAME);
//	    			  String view_display_name = default_record.getAttribute(AS2NavigationPaneSectionModel.VIEW_DISPLAY_NAME);
//	    			  AS2GwtDesktop.getInstance().setDefaultViewName(name);
//	    			  AS2GwtDesktop.getInstance().setDefaultViewDisplayName(view_display_name);
//	    			  AS2GwtDesktop.getInstance().setContextAreaViewXml(name, view_display_name);
//	    		  }
//	    	  }else if(!_section.getDefaultPerspectiveName().equals(""))
//	    		  listGrid.selectRecord(getRecord(_section.getDefaultPerspectiveName()));
//	    	  else if(_section.getDefaultPerspectiveName().equals("")){
//	    		  listGrid.selectRecord(listGrid.getRecords()[0]);
//	    	  }
	      }
	    };
  }
  public int getRecordNum(String name) {
	  int result = -1;
	  ListGridRecord[] records = this.getRecords();
	  ListGridRecord record = null;
	  String recordName = "";

	  for (int i = 0; i < records.length; i++) {

		  record = this.getRecord(i);
		  // as per NavigationPaneSectionListGrid
		  recordName = record.getAttributeAsString(AS2NavigationPaneSectionModel.NAME);

		  if (name.contentEquals(recordName)) {
			  result = i;
			  break;
		  }
	  }

	  return result;
  }

  public Record getRecord(String name) {
	  int result = -1;
	  ListGridRecord[] records = this.getRecords();
	  if(records.length==0)
		  return null;
	  ListGridRecord record = null;
	  String recordName = "";

	  for (int i = 0; i < records.length; i++) {

		  record = this.getRecord(i);
		  // as per NavigationPaneSectionListGrid
		  recordName = record.getAttributeAsString(AS2NavigationPaneSectionModel.NAME);

		  if (name.contentEquals(recordName)) {
			  result = i;
			  break;
		  }
	  }
	  if(result==-1)
		  return null;
	  if(this.getRecords()[result]==null)
		  return null;
	  return this.getRecords()[result];
  }
  
  
  //prebačeno sa AS2NavigationPaneSection....
//  public Record getRecord(String name){
//	  int result = -1;
//	  ListGridRecord[] records = listGrid.getRecords();
//	  if(records.length==0)
//		  return null;
//	  ListGridRecord record = null;
//	  String recordName = "";
//
//	  for (int i = 0; i < records.length; i++) {
//
//		  record = listGrid.getRecord(i);
//		  // as per NavigationPaneSectionListGrid
//		  recordName = record.getAttributeAsString(AS2NavigationPaneSectionModel.NAME);
//
//		  if (name.contentEquals(recordName)) {
//			  result = i;
//			  break;
//		  }
//	  }
//	  if(result==-1)
//		  return null;
//	  if(listGrid.getRecords()[result]==null)
//		  return null;
//	  return listGrid.getRecords()[result];
//  }
  
  
  
  public String getSelectedAS2Record() {
	  String name = "";
	  ListGridRecord[] records = this.getSelectedRecords();
	  if (records.length != 0) {
		  // get the name of the first selected record e.g. "Activities"
		  name = records[0].getAttributeAsString(AS2NavigationPaneSectionModel.NAME);
	  } else {
		  ListGridRecord record = this.getRecord(0);
		  // see NavigationPaneSectionListGrid - DataArrivedHandler()
		  if (record != null) {
			  // get the name of the first record in the ListGrid e.g. "Activities"
			  name = record.getAttributeAsString(AS2NavigationPaneSectionModel.NAME);
		  }
	  }
	  return name;
  }
  
  public void addRecordClickHandlerAs2(RecordClickHandler clickHandler) {
      this.addRecordClickHandler(clickHandler);
  }
  
  public String getRecordViewDisplayName(String name) {
	  String view_display_name = "";
	  ListGridRecord[] records = this.getRecords();
	  for(ListGridRecord record:records){
		  if(record.getAttributeAsString(AS2NavigationPaneSectionModel.NAME).equals(name)){
			  view_display_name=record.getAttributeAsString(AS2NavigationPaneSectionModel.VIEW_DISPLAY_NAME);
		  }
	  }
	  return view_display_name;
  }

  public String getRecordDisplayName(String name) {
	  String display_name = "";
	  ListGridRecord[] records = this.getRecords();
	  for(ListGridRecord record:records){
		  if(record.getAttributeAsString(AS2NavigationPaneSectionModel.NAME).equals(name)){
			  display_name=record.getAttributeAsString(AS2NavigationPaneSectionModel.DISPLAY_NAME);
		  }
	  }
	  return display_name;
  }
  
  public ListGridRecord getRecordByName(String name) {
	  ListGridRecord return_record = null;
	  ListGridRecord[] records = this.getRecords();
	  for(ListGridRecord record:records){
		  if(record.getAttributeAsString(AS2NavigationPaneSectionModel.NAME).equals(name)){
			  return_record=record;
		  }
	  }
	  return return_record;
  }
  
  //Return default record/perspective in stack
  public String getDefaultPerspectiveName() {
	return ((AS2NavigationPaneSectionModel)this.getDataSource()).getDefaultPerspectiveName();
  }
}
