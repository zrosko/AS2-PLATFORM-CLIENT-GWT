package hr.adriacomsoftware.inf.client.smartgwt.views.rosko;

import hr.adriacomsoftware.app.client.smartgwt.resources.AS2Resources;
import hr.adriacomsoftware.inf.client.smartgwt.types.AS2Field;
import hr.adriacomsoftware.inf.client.smartgwt.types.AS2FormItem;
import hr.adriacomsoftware.inf.client.smartgwt.views.AS2DynamicForm;
import hr.adriacomsoftware.inf.client.smartgwt.views.AS2ListGrid;
import hr.adriacomsoftware.inf.client.smartgwt.views.AS2Window;

import java.util.ArrayList;
import java.util.List;

import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.form.fields.FormItem;
/**
 * Works the same as AS2SeachMWindw, will be a candidate for replacement.
 *
 */
public class AS2SearchWindow extends AS2Window {

	private static int DEFAULT_HEIGHT = 375;
	private static int DEFAULT_WIDTH = 500;
	private AS2DynamicForm _searchForm;
	private List<FormItem> _formItems;
	private AS2FormItem _buttonSearch;
	private AS2FormItem _buttonExit;
	private boolean _draw = true;
	private boolean _plainWindow = false;
	
	public AS2SearchWindow(AS2View2 parent,AS2ListGrid listGrid, boolean draw, boolean plainWindow) {		
		this(parent,listGrid,listGrid.getDataSource(),draw,plainWindow);
	}
	
	public AS2SearchWindow(AS2View2 parent,AS2ListGrid listGrid, DataSource dataSource,boolean draw, boolean plainWindow) {
		this._parent_view = parent;
		this._listGrid = listGrid;
		this._dataSource = dataSource;
		this._draw  = draw;
		this._plainWindow  = plainWindow;
		this._formItems = new ArrayList<FormItem>();
		createComponents();
		windowLayout();
	}

	@Override
	protected void onEnter() {
		search();

	}

	public AS2FormItem getButtonSearch(){
		return _buttonSearch;
	}
	
/*
	1. 	pronaci pojedinacno svaku rijec
	2. 	provjeriti da li se unutar rijeci nalazi znak ':'
		ako da: onda tekst lijevo predstavlja key, a desno value
		ako ne: onda se cijela rijec dopisuje u polju ostalo
 */
	public static Record createFormRecord(String searchCriteria) {
		Record form = new Record();
		form.setAttribute("trazi_sve", "");// da ne bude null
		boolean hasWords = true;
		String searchField = searchCriteria;
		if(searchField.length()==0)
			hasWords = false;
		String word;
		String key;
		String value;
		int index;
		int begin = 0;
		int end = searchField.indexOf(" ");
		int word_begin = 0;
		int word_end;

		if (end == -1) {
			end = searchField.length();
		}

		while (hasWords) {
			word = searchField.substring(begin, end);
			index = word.indexOf(":");
			word_end = word.length();
			if (index == -1) {
				value = " " + word;
				form.setAttribute("trazi_sve", (form.getAttribute("trazi_sve") + value).trim());
			} else {
				key = word.substring(word_begin, index);
				value = word.substring(index + 1, word_end);
				form.setAttribute(key, value);
			}

			begin = 1 + searchField.lastIndexOf(" ", end);

			if (searchField.lastIndexOf(" ", end) < end)
				hasWords = false;

			end = searchField.indexOf(" ", end + 1);

			if (end == -1)
				end = searchField.length();
		}
			// create item
		return form;
	}

	@Override
	public void createComponents() {
		_searchForm = getDynamicForm();
	}

	@Override
	public void windowLayout() {
		if(_plainWindow){
			this.setCanDragReposition(false);	
//			this.setShowShadow(false);
			this.setShowHeader(false);
			this.setShowStatusBar(false);
			this.setLayoutMargin(0); // to remove 4px gray border
			this.setAttribute("styleName", "", true); // to remove 1px (rounded) outer border
			this.setBodyStyle(""); // to remove 1px body border
//			setBodyColor(null); // to make background transparent
		}
		this.setTitle("Pretraživanje");
		this.setHeight(DEFAULT_HEIGHT);
		this.setWidth(DEFAULT_WIDTH);
		this.addItem(_searchForm);
		if(_draw)
			this.draw();
	}
	
	public void moveWindowTo(int left, int right){
		this.moveTo(left,right);
	}

	public int getOptimalHight(){
		int hight = _formItems.size();
		if(hight==0)
			return 280;
		else
			return hight*40;
	}
	public AS2DynamicForm getDynamicForm() {
		final AS2DynamicForm form = new AS2DynamicForm(null, 4);
		form.setAutoFocus(false);
		form.setSaveOnEnter(false);
		_buttonExit = new AS2FormItem("button_exit", AS2Field.FORM_BUTTON, "X",4, null, false);
		_buttonExit.setWidth(18);
		_buttonExit.setHeight(18);
		_buttonExit.getField().setAlign(Alignment.RIGHT);
		_buttonSearch = new AS2FormItem("button_trazi", AS2Field.FORM_BUTTON, "Pretraži",4, null, false);
		_buttonSearch.setIcon(AS2Resources.SEARCH_PATH);
		_buttonSearch.setWidth(120);
		_buttonSearch.getField().setTop(10);
		_buttonSearch.getField().setAlign(Alignment.LEFT);
		
		if(_plainWindow){
			_formItems.add(_buttonExit.getField());
		}
		Record record = createFormRecord(_parent_view.getSearchCriteria());
		for (AS2FormItem item : _parent_view.getSearchFormItems()) {
			if(record.getAttribute(item.getAttribute("name").toString())!=null)
				item.getField().setValue(record.getAttribute(item.getAttribute("name").toString()));
//				item.setDefaultValue(record.getAttribute(item.getAttribute("name").toString()));
			_formItems.add(item.getField());
		}
		_formItems.add(_buttonSearch.getField());
		
		form.setDataSource(_dataSource);
		form.setFields((FormItem[])_formItems.toArray(new FormItem[_formItems.size()]));
		form.setMargin(6);
		form.focusInItem(_buttonSearch);
		
		//handlers
		_buttonExit.getField().addClickHandler(new com.smartgwt.client.widgets.form.fields.events.ClickHandler() {
			@Override
			public void onClick(com.smartgwt.client.widgets.form.fields.events.ClickEvent event) {
				closeWindow(false);
			}
		});
		_buttonSearch.getField().addClickHandler(new com.smartgwt.client.widgets.form.fields.events.ClickHandler() {
			@Override
			public void onClick(com.smartgwt.client.widgets.form.fields.events.ClickEvent event) {
				search();
			}
		});
		
		return form;
	}
	
	private void search() {
		Criteria criteria = _searchForm.getValuesAsCriteria();
		_listGrid.invalidateCache();
		_listGrid.fetchData(criteria);
		closeWindow(false);
	}

	protected void setFocusInItem(String item){
		if(item != null || !"".equals(item)){
			_searchForm.focusInItem(item);
		}
	}
}
