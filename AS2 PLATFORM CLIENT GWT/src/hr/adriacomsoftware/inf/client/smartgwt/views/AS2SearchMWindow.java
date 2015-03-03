package hr.adriacomsoftware.inf.client.smartgwt.views;

import hr.adriacomsoftware.app.client.smartgwt.resources.AS2Resources;
import hr.adriacomsoftware.inf.client.smartgwt.types.AS2Field;
import hr.adriacomsoftware.inf.client.smartgwt.types.AS2FormItem;
import hr.adriacomsoftware.inf.client.smartgwt.views.rosko.AS2View2;

import java.util.ArrayList;
import java.util.List;

import com.smartgwt.client.data.Criterion;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.events.KeyPressEvent;
import com.smartgwt.client.widgets.form.fields.events.KeyPressHandler;

public abstract class AS2SearchMWindow extends AS2Window {

	private static int DEFAULT_HEIGHT = 375;
	private static int DEFAULT_WIDTH = 375;
	private AS2DynamicForm _searchForm;
	private List<FormItem> _formItems;
	private AS2FormItem _buttonSearch;

	public AS2SearchMWindow(AS2View parent,AS2ListGrid listGrid) {
		this._listGrid = listGrid;
//		this._parent_view = parent;
		this._dataSource = _listGrid.getDataSource();
		this._formItems = new ArrayList<FormItem>();
		for(AS2FormItem item : getSearchFormItems()){
			this._formItems.add(item.getField());
		}
		createComponents();
		windowLayout();
	}
	
	public AS2SearchMWindow(AS2View parent,AS2ListGrid listGrid, DataSource dataSource) {
		this._listGrid = listGrid;
//		this._parent_view = parent;
		this._dataSource = dataSource;
		this._formItems = new ArrayList<FormItem>();
		for(AS2FormItem item : getSearchFormItems()){
			this._formItems.add(item.getField());
		}
		createComponents();
		windowLayout();
	}
	
	public AS2SearchMWindow(AS2View2 parent,AS2ListGrid listGrid) {
		this._listGrid = listGrid;
		this._parent_view = parent;
		this._dataSource = _listGrid.getDataSource();
		this._formItems = new ArrayList<FormItem>();
		for(AS2FormItem item : getSearchFormItems()){
			this._formItems.add(item.getField());
		}
		createComponents();
		windowLayout();
	}
	
	public AS2SearchMWindow(AS2View2 parent,AS2ListGrid listGrid, DataSource dataSource) {
		this._listGrid = listGrid;
		this._parent_view = parent;
		this._dataSource = dataSource;
		this._formItems = new ArrayList<FormItem>();
		for(AS2FormItem item : getSearchFormItems()){
			this._formItems.add(item.getField());
		}
		createComponents();
		windowLayout();
	}

	protected abstract AS2FormItem[] getSearchFormItems();

	@Override
	public void createComponents() {
		_searchForm = getDynamicForm();
	}

	@Override
	public void windowLayout() {
		this.setTitle("Pretraživanje");
		this.setHeight(DEFAULT_HEIGHT);
		this.setWidth(DEFAULT_WIDTH);
		this.centerInPage();
		this.addItem(_searchForm);
		this.draw();
	}

	public AS2DynamicForm getDynamicForm() {
		final AS2DynamicForm form = new AS2DynamicForm(null, 4);
		form.setAutoFocus(false);
		_buttonSearch = new AS2FormItem("button_trazi", AS2Field.FORM_BUTTON, "Pretraži",4, null, false);
		_buttonSearch.setIcon(AS2Resources.SEARCH_PATH);
		_buttonSearch.setWidth(120);
		_buttonSearch.getField().setAlign(Alignment.RIGHT);
		_buttonSearch.getField().addClickHandler(new com.smartgwt.client.widgets.form.fields.events.ClickHandler() {
					@Override
					public void onClick(
							com.smartgwt.client.widgets.form.fields.events.ClickEvent event) {
						search();
					}
				});
		_formItems.add(_buttonSearch.getField());
		this.addKeyPressHandler(new com.smartgwt.client.widgets.events.KeyPressHandler() {

			@Override
			public void onKeyPress(
					com.smartgwt.client.widgets.events.KeyPressEvent event) {
				if (event.getKeyName() != null) {
					if (event.getKeyName().equals("Enter")) {
						_buttonSearch.getField().setShowFocused(true);
						search();
					}
				}

			}
		});
		form.addKeyPressHandler(new com.smartgwt.client.widgets.events.KeyPressHandler() {

			@Override
			public void onKeyPress(
					com.smartgwt.client.widgets.events.KeyPressEvent event) {
				if (event.getKeyName() != null) {
					if (event.getKeyName().equals("Enter")) {
						_buttonSearch.getField().setShowFocused(true);
						search();
					}
				}

			}
		});
		form.setDataSource(_dataSource,(FormItem[])_formItems.toArray(new FormItem[_formItems.size()]));
		KeyPressHandler enter = new KeyPressHandler() {
			@Override
			public void onKeyPress(KeyPressEvent event) {
				if (event.getKeyName() != null) {
					if (event.getKeyName().equals("Enter")) {
						_buttonSearch.getField().setShowFocused(true);
						search();
					}
				}
			}
		};
		for (FormItem field : form.getFields()) {
			field.addKeyPressHandler(enter);
		}
		if(_listGrid.getCriteria()!=null ){
			Criterion crit = new Criterion();
			crit.addCriteria(_listGrid.getCriteria());
			form.setValuesAsCriteria(crit);
		}
		return form;
	}
	
	private void search() {
		_listGrid.deselectAllRecords();
		_listGrid.filterData(_searchForm.getValuesAsCriteria());
		closeWindow(false);
		
	}

	protected void setFocusInItem(String item){
		if(item != null || !"".equals(item)){
			_searchForm.focusInItem(item);
		}

	}

}
