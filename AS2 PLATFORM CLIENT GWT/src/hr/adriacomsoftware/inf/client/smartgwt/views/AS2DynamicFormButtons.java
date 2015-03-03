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

public class AS2DynamicFormButtons extends HLayout {

	protected ValuesManager _vm;
	protected ListGrid _listGrid;

	public AS2DynamicFormButtons(ValuesManager vm) {
		this.setPadding(10);
		this.setAlign(Alignment.RIGHT);
		_vm = vm;
		this.setStyleName("crm-dynamicForm");
		IButton spremi = new IButton("Spremi");
		spremi.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				_vm.saveData(new DSCallback() {
					public void execute(DSResponse response, Object rawData,
							DSRequest request) {
						_vm.editNewRecord();

					}
				});

			}
		});

		IButton novo = new IButton("Novo");
		novo.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				_vm.editNewRecord();
			}
		});

		IButton brisi = new IButton("Briši");
		brisi.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				SC.ask("Upozorenje!", "Da li ste sigurni da želite obrisati?",
						new BooleanCallback() {
							@Override
							public void execute(Boolean value) {
								// TODO Auto-generated method stub
								if (value != null && value) {
									_vm.editNewRecord();
								}
							}
						});
			}
		});

		this.setMembers(spremi, novo, brisi);

	}

	public AS2DynamicFormButtons(ListGrid listgrid,ValuesManager vm) {
		this.setPadding(10);
		this.setAlign(Alignment.RIGHT);
		_vm = vm;
		_listGrid = listgrid;

		IButton spremi = new IButton("Spremi");
		spremi.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				_vm.saveData(new DSCallback() {
					public void execute(DSResponse response, Object rawData,
							DSRequest request) {
						_listGrid.deselectAllRecords();
						_listGrid.fetchData();
						_vm.editNewRecord();

					}
				});

			}
		});

		IButton novo = new IButton("Novo");
		novo.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				_listGrid.deselectAllRecords();
				_listGrid.fetchData();
				_vm.editNewRecord();
			}
		});

		IButton brisi = new IButton("Briši");
		brisi.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				SC.ask("Upozorenje!", "Da li ste sigurni da želite obrisati?",
						new BooleanCallback() {
							@Override
							public void execute(Boolean value) {
								// TODO Auto-generated method stub
								if (value != null && value) {
									_listGrid.removeSelectedData();
									_listGrid.fetchData();
									_vm.editNewRecord();
								}
							}
						});
			}
		});

		this.setMembers(spremi, novo, brisi);

	}

}
