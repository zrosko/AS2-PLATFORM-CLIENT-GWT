package hr.adriacomsoftware.inf.client.smartgwt.views;

import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.ValuesManager;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.layout.HLayout;

public class AS2Buttons extends HLayout {

	protected ValuesManager _vm;
	protected ListGrid _listGrid;
	private IButton button100;
	private IButton button200;
	private IButton button300;
	private IButton button400;
	//private IButton button500;


	private void init(){
		this.setPadding(10);
		this.setAlign(Alignment.RIGHT);
		this.setStyleName("crm-dynamicForm");

		button100 = new IButton("Spremi");
		button100.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				_vm.saveData(new DSCallback() {
					public void execute(DSResponse response, Object rawData,
							DSRequest request) {
						if(_listGrid!=null){
							_listGrid.deselectAllRecords();
							_listGrid.fetchData();
						}
						_vm.editNewRecord();

					}
				});

			}
		});

		button200 = new IButton("Novo");
		button200.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				if(_listGrid!=null){
					_listGrid.deselectAllRecords();
					_listGrid.fetchData();
				}
				_vm.editNewRecord();
			}
		});

		button300 = new IButton("Briši");
		button300.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				SC.ask("Upozorenje!", "Da li ste sigurni da želite obrisati?",
						new BooleanCallback() {
							@Override
							public void execute(Boolean value) {
								// TODO Auto-generated method stub
								if (value != null && value) {
									if(_listGrid!=null){
										_listGrid.deselectAllRecords();
										_listGrid.fetchData();
									}
									_vm.editNewRecord();
								}
							}
						});
			}
		});

		button400 = new IButton("Izlaz");
		button400.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub

			}
		});



	}

	public AS2Buttons(ValuesManager vm) {
		_vm = vm;
		_listGrid=null;
		init();
		this.setMembers(button100, button200, button300,button400);

	}

	public AS2Buttons(ListGrid listgrid,ValuesManager vm) {
		_vm = vm;
		_listGrid = listgrid;
		init();
		this.setMembers(button100, button200, button300,button400);

	}

}
