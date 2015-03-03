package hr.adriacomsoftware.inf.client.smartgwt.views.rosko;

/**
 * @author msekso
 *
 */ 
import hr.adriacomsoftware.app.client.smartgwt.resources.AS2Resources;
import hr.adriacomsoftware.inf.client.smartgwt.views.AS2Window;

import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.Side;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.tab.TabSet;
import com.smartgwt.client.widgets.tab.events.TabSelectedEvent;
import com.smartgwt.client.widgets.tab.events.TabSelectedHandler;

public class AS2TabMWindow extends AS2Window {
	public boolean _novi_zahtjev;
	private TabSet _tabSet;
	private Label _window_form_title_left;
	private HLayout _window_form_title_layout;
	private Label _window_form_title_right;
//	private HLayout _buttons_layout;//TODO

	//TODO viditi zašto se javlja pogreška:
	//Nakon izmjene podataka na formi u nekom tabu, spremimo te podatke i želimo ugasiti Mwindow. Onda bi se trebao
	//napraviti refresh listgrid-a na viewu. Međutim ovo javlja grešku zbog grupiranja podataka na listgridu
	public AS2TabMWindow(AS2View2 parent, Record record) {
		super(parent,record);
	}
	
	@Override
	public void createComponents() {
		if (_record == null){
			_novi_zahtjev=true;
			_record = new Record();
		}else{
			_novi_zahtjev=false;
		}
		_window_form_title_layout = getWindowFormTitleLayout();
		_tabSet = getTabSet();
//		_buttons_layout = getButtonsLayout();//TODO
		windowLayout();
	}
	@Override
	public void windowLayout(){
		this.addItem(_window_form_title_layout);
		this.addItem(_tabSet);
//		this.addItem(_buttons_layout);//TODO za sada komentirano
		this.show();
	}
	
	private HLayout getWindowFormTitleLayout() {
		HLayout title = new HLayout();
		title.setBorder("1px solid #7598c7");
		title.setHeight(10);
		title.setStyleName("crm-dynamicForm-titlelabel");
		_window_form_title_left = new Label();
		_window_form_title_left.setWidth("50%");
		_window_form_title_left.setContents(getWindowFormTitleLeft());
		_window_form_title_right = new Label();
		_window_form_title_right.setContents(getWindowFormTitleRight());
		_window_form_title_right.setWidth("50%");
		_window_form_title_right.setAlign(Alignment.RIGHT);
		title.setMembers(_window_form_title_left,_window_form_title_right);
		return title;
	}

	private String getWindowFormTitleLeft(){
		String contents ="";
		if(_novi_zahtjev)
			contents = "NOVI ZAHTJEV";
		else{	
			contents = "<b style=\"color:black;font-size:10pt;\">Podaci za zahtjev broj: ";
//			+ _zahtjev_record.getAttributeAsString(MCARD_GR_ZAHTJEV__BROJ_ZAHTJEVA)
//			+ "</b></br>"
//			+ _zahtjev_record.getAttributeAsString(MCARD_GR_ZAHTJEV__IME_PREZIME);
					//TODO
		}
		return contents;
	}
	
	private String getWindowFormTitleRight() {
		String contents = "";
		if(!_novi_zahtjev){
				contents = "<b style=\"color:black;font-size:10pt;\">Faza zahtjeva:</b></t></br>";
//						+ 
						//AS2Field.getComboData(MCARD_GR_ZAHTJEV__FAZA_ZAHTJEVA).get(			//TODO
//						_zahtjev_record.getAttribute(MCARD_GR_ZAHTJEV__FAZA_ZAHTJEVA)
						//)
//						.toString().toUpperCase();
		}
		return contents;
	}



	protected HLayout getButtonsLayout() {
		HLayout buttons_layout = new HLayout(4);
		buttons_layout.setAlign(Alignment.RIGHT);
		buttons_layout.setStyleName("crm-dynamicForm-buttonsLayout");
		buttons_layout.setWidth100();
		buttons_layout.setAutoHeight();
		buttons_layout.setShowEdges(true);
		// define Buttons
		_button_izlaz = new IButton("Izlaz");
		_button_izlaz.setIcon(AS2Resources.CANCEL_PATH);
		_button_izlaz.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				closeWindow(_refresh);
			}
		});
		buttons_layout.setMembers(_button_izlaz);
		return buttons_layout;
	}

	protected TabSet getTabSet() {
		TabSet tabSet = new TabSet();  
		tabSet.setTabBarPosition(Side.LEFT);  
		tabSet.setTabBarAlign(Side.LEFT);  
		tabSet.setTabBarThickness(150);
		tabSet.setWidth100();  
		tabSet.setHeight100();
		tabSet.addTabSelectedHandler(new TabSelectedHandler() {
			@Override
			public void onTabSelected(TabSelectedEvent event) {
				openWindow(event.getTab().getID(),_record);
			}
		});
        return tabSet; 
	}
	
	protected void openWindow(String selectedTab, Record record) {
//		EXAMPLE
//		if (selectedTab.equals(podaciKlijent.getID())) {
//			podaciKlijent.setPane(new CrmKlijentDetaljiView(CrmPredmetMWindow.this,record));
//		} else if (selectedTab.equals(predmetPodaci.getID())) {
//			predmetPodaci.setPane(new NaplataKreditiGrDetaljiView(AS2TabMWindow.this, record));
//		}
	}
	
	//main record for tabs
	public void setRecord(Record rec) {
		this._record = rec;
	}

	public String getSelectedTabID(){
		return _tabSet.getSelectedTab().getID();
	}
	public void refreshParentView(){
		closeWindow(true);
	}
	public void refreshWindowTitle(){
		_window_form_title_left.setContents(getWindowFormTitleLeft());
		_window_form_title_right.setContents(getWindowFormTitleRight());
	}
	public void refreshWindow(Record record){
		setRecord(record);
		refreshWindowTitle();
	}
}
