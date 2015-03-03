//package hr.adriacomsoftware.inf.client.smartgwt.views;
//
//import hr.adriacomsoftware.inf.client.smartgwt.desktop.views.AS2ListGridToolBar;
//import hr.adriacomsoftware.inf.client.smartgwt.desktop.views.AS2StatusBar;
//import hr.adriacomsoftware.inf.client.smartgwt.transport.rpc.GreetingService;
//import hr.adriacomsoftware.inf.client.smartgwt.transport.rpc.GreetingServiceAsync;
//
//import java.util.HashMap;
//
//import com.google.gwt.core.client.GWT;
//import com.google.gwt.user.client.rpc.AsyncCallback;
//import com.smartgwt.client.data.DataSource;
//import com.smartgwt.client.types.Overflow;
//import com.smartgwt.client.widgets.form.ValuesManager;
//import com.smartgwt.client.widgets.form.fields.ButtonItem;
//import com.smartgwt.client.widgets.layout.VLayout;
//
//public abstract class AS2ViewOnlyListGrid extends VLayout implements AsyncCallback <HashMap<String,String>> {
//	protected static final String CONTEXT_AREA_WIDTH = "*";
//	protected static int number_of_columns = 4;
//	protected static boolean view_ready = false;
//	protected DataSource _dataSource;
//	protected AS2ListGrid _listGrid;
//	protected ButtonItem _horizontal_line;
//	protected static final GreetingServiceAsync server = GWT.create(GreetingService.class);
//
//	public AS2ViewOnlyListGrid() {
//		super();
//		initializeView();
//
//		// define datasource
//		_dataSource = getDataSource();
//
//		// initialise the InvPlanView layout container
//		this.setStyleName("crm-ContextArea");
//		this.setWidth(CONTEXT_AREA_WIDTH);
//		this.setOverflow(Overflow.AUTO);//overflow:auto Layout will scroll if members exceed its specified size
//		//this.setMembersMargin(5);//Razmak između elemenata u layoutu forme
//
//		// define listgrid and form
//		_listGrid = getListGrid();
//
//		// add the Tool Bar, List Grid, Status Bar, DynamicForm and Jump Bar to the InvPlanView layout container
//		this.addMember(new AS2ListGridToolBar(_listGrid,null));
//		this.addMember(_listGrid);
//		this.addMember(new AS2StatusBar());
//		//this.addMember(new AS2JumpBar()); //TODO u backup-u
//
//	}
//	protected void initializeView(){
//		//TODO
//	}
//	protected void callServer(String component, String service, String attribute){
//		HashMap<String,String> to_server = new HashMap<String, String>();
//		to_server.put("@attribute",attribute);
//		callServer(component,service,to_server);
//	}
//	protected void callServer(String component, String service, HashMap<String,String> value){
//		HashMap<String,String> to_server = new HashMap<String, String>();
//		to_server.put("@component", component);
//		to_server.put("@service", service);
//		to_server.putAll(value);
//		server.greetServer(to_server, this);
//	}
//	@Override
//	public void onFailure(Throwable caught) {
//		view_ready = false;
//	}
//	@Override
//	public void onSuccess(HashMap<String, String> result) {
//		prepareFormData(result);
//		view_ready = true;
//	}
//	protected void prepareFormData(HashMap<String, String> result){
//		//implement in case you have drop down, combo on your form
//	}
//	protected abstract DataSource getDataSource();
//
//	protected abstract AS2ListGrid getListGrid();
////	public void onSuccess( <HashMap<String,String>> results) {
////	_oblik_vlasnistva_list = results;
////	for(AS2GwtValueObject result: results){
////		HashMap<String, String> dva = result.getAllAttributes();
////		Set<?> keys  = dva.entrySet();
////        Iterator<?> E = keys.iterator();
////        while (E.hasNext()) {
////        	Map.Entry<?, ?> m =(Map.Entry<?, ?>)E.next();
////        	String name =(String) m.getKey();
////        	if(name.equals("oblik_vlasnistva")){
////        		String value = dva.get(name);
////        		valueMap.put(name, value);
////        	}else
////        		break;
////        }
////	}
////	oblik_vlasnistva.setValueMap(valueMap);
////	viewReady = true;
////	System.out.println("to je to--"+valueMap);
////
////}
//}
