package hr.adriacomsoftware.inf.client.smartgwt.views.rosko;

import hr.adriacomsoftware.inf.client.smartgwt.views.AS2ListGrid;
import hr.adriacomsoftware.inf.client.smartgwt.views.AS2Window;

import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.widgets.Canvas;

public class AS2SpreadSheetMWindow extends AS2Window {
	String tableName;
	String schemaName;
	String databaseName;
	Criteria criteria;

	public AS2SpreadSheetMWindow(String databaseName, String schemaName,String tableName, Criteria criteria) {
		super();
		this.criteria = criteria;
		this.tableName = tableName;
		this.schemaName = schemaName;
		this.databaseName = databaseName;
		getMetaDataModel();
	}
	
	@Override
	protected Criteria initCriteria() {
		criteria.addCriteria("tableName",tableName);
		criteria.addCriteria("schemaName",schemaName);
		criteria.addCriteria("databaseName",databaseName);
		return criteria;
	}
	public void createComponents() {
		_dataSource = getModel();
		_window_form_title.setContents(getWindowFormTitle());	
		_dataSource.fetchData(initCriteria(),new DSCallback() {
			@Override
			public void execute(DSResponse dsResponse, Object data, DSRequest dsRequest) {
				_listGrid = getListGrid();
				windowLayout();
			}
		}); 
	}
	
	public String getWindowFormTitleNew() {
		return "<b style=\"color:black;font-size:10pt;\">"+databaseName+"."+schemaName+"."+tableName+"</b>";
	}
	
	public AS2ListGrid getListGrid() {
		final AS2ListGrid listGrid = new AS2ListGrid(true){			
			@Override
			protected Canvas getRollOverCanvas(Integer rowNum, Integer colNum) {
				return getRolloverCanvas(rowNum,colNum,this);
			}
		};
		listGrid.setDataSource(_dataSource);
//		listGrid.setAutoFetchData(false);
		listGrid.setWidth100();
		listGrid.setHeight100();
		listGrid.setUseAllDataSourceFields(true);
		listGrid.setShowResizeBar(false);
		listGrid.setCanEdit(true);
		listGrid.setModalEditing(true);
//		AS2ListGridField polje_1 = new AS2ListGridField("polje1",AS2Field.TEXT,"Polje 1");
//		AS2ListGridField polje_2 = new AS2ListGridField("polje2",AS2Field.TEXT,"Polje 2");		
//		Criteria meta_criteria = new Criteria();
//		meta_criteria.addCriteria("@meta","true");
//		criteria.addCriteria("tableName",tableName);
		listGrid.setCriteria(criteria);
//		listGrid.setFields(polje_1,polje_2);
//		getModel().fetchData(meta_criteria, new DSCallback() {
//			
//			@Override
//			public void execute(DSResponse dsResponse, Object data, DSRequest dsRequest) {
//				RecordList recordList = dsResponse.getDataAsRecordList();
//				List<DataSourceField> fields = new ArrayList<>();
//				for(Record record: recordList.toArray()){
//					String name = record.getAttribute("_columnLabel");
//					String sqlType = record.getAttribute("_columnType");
//					String title = record.getAttribute("_columnLabel");
//					AS2DataSourceField field;
//					if(name.startsWith("id_"))
//						field = new AS2DataSourceField(name,AS2Field.PRIMARY_KEY,title);
//					else
//						field = new AS2DataSourceField(name,AS2Field.getType(sqlType),title);
//					fields.add(field.getField());
//				}
//				getModel().setFields(fields.toArray(new DataSourceField[fields.size()]));
//				criteria.addCriteria("tableName",tableName);
//				listGrid.setCriteria(criteria);
//				listGrid.invalidateCache();
////				listGrid.fetchData(criteria);
////				listGrid.markForRedraw();
//				
//			}
//		});
//		listGrid.fetchData(meta_criteria,new DSCallback() {
//			
//			@Override
//			public void execute(DSResponse dsResponse, Object data, DSRequest dsRequest) {
//				if(dsResponse.getStatus()>=0 &&  dsResponse.getDataAsRecordList().get(0)!=null){
////					if(has_meta){
////						criteria.addCriteria("tableName",tableName);
////						listGrid.fetchData(criteria);
////					}else{
////						has_meta=true;
//						RecordList recordList = dsResponse.getDataAsRecordList();
//						List<AS2ListGridField> fields = new ArrayList<>();
//						for(Record record: recordList.toArray()){
//							String name = record.getAttribute("_columnLabel");
//							String sqlType = record.getAttribute("_columnType");
//							String title = record.getAttribute("_columnLabel");
//							AS2ListGridField field;
//							if(name.startsWith("id_"))
//								field = new AS2ListGridField(name,AS2Field.PRIMARY_KEY,title);
//							else
//								field = new AS2ListGridField(name,AS2Field.getType(sqlType),title);
//							fields.add(field);
//						}
//						listGrid.setFields(fields.toArray(new AS2ListGridField[fields.size()]));
//						criteria.addCriteria("tableName",tableName);
//						listGrid.fetchData(criteria);
//						listGrid.markForRedraw();
////					}
//				}
//			}
//		});
		listGrid.addDataArrivedHandler(new com.smartgwt.client.widgets.grid.events.DataArrivedHandler() {
			
			@Override
			public void onDataArrived(
					com.smartgwt.client.widgets.grid.events.DataArrivedEvent event) {
				listGrid.markForRedraw();
			}
		});
		return listGrid;
	}
	
	@Override
	protected void getFormButtons() {
		_buttons_layout.setMembers(_button_izlaz);
	}
	
	@Override
	public DataSource getModel() {
		return AS2SpreadSheetModel.getInstance(databaseName,schemaName,tableName);
	}
	
	public void getMetaDataModel() {
		DataSource dataSource = new AS2SpreadSheetMetaDataModel(tableName);
		Criteria meta_criteria = new Criteria();
		meta_criteria.addCriteria("@meta","true");
		meta_criteria.addCriteria("tableName",tableName);
		meta_criteria.addCriteria("schemaName",schemaName);
		meta_criteria.addCriteria("databaseName",databaseName);
		dataSource.fetchData(meta_criteria, new DSCallback() {			
			@Override
			public void execute(DSResponse dsResponse, Object data, DSRequest dsRequest) {
				RecordList metaData = dsResponse.getDataAsRecordList();
				AS2SpreadSheetMetaDataModel.getInstance().setMetaData(metaData);
				createComponents();
			}
		});	
	}
	
	@Override
	public void customWindowPreferences(){
		this.setWidth(600);
		this.setHeight(400);
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

}
