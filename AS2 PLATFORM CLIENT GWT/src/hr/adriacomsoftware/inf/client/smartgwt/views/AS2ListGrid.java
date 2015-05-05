package hr.adriacomsoftware.inf.client.smartgwt.views;

import hr.adriacomsoftware.inf.client.smartgwt.desktop.views.AS2StatusBar2;
import hr.adriacomsoftware.inf.client.smartgwt.models.AS2RestJSONDataSource;
import hr.adriacomsoftware.inf.client.smartgwt.types.AS2Field;
import hr.adriacomsoftware.inf.client.smartgwt.types.AS2ListGridField;

import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.data.ResultSet;
import com.smartgwt.client.data.events.DataChangedEvent;
import com.smartgwt.client.data.events.DataChangedHandler;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.AnimationEffect;
import com.smartgwt.client.types.ListGridComponent;
import com.smartgwt.client.types.ListGridEditEvent;
import com.smartgwt.client.types.ListGridFieldType;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.types.SelectionAppearance;
import com.smartgwt.client.types.SelectionStyle;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.events.DrawEvent;
import com.smartgwt.client.widgets.events.DrawHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.ValuesManager;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.SummaryFunction;
import com.smartgwt.client.widgets.grid.events.DataArrivedEvent;
import com.smartgwt.client.widgets.grid.events.DataArrivedHandler;
import com.smartgwt.client.widgets.grid.events.FilterEditorSubmitEvent;
import com.smartgwt.client.widgets.grid.events.FilterEditorSubmitHandler;
import com.smartgwt.client.widgets.grid.events.SelectionUpdatedEvent;
import com.smartgwt.client.widgets.grid.events.SelectionUpdatedHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.LayoutSpacer;
import com.smartgwt.client.widgets.toolbar.ToolStrip;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;

public class AS2ListGrid extends ListGrid {

	protected ListGridRecord _rollOverRecord;
	protected HLayout _rollOverCanvas;
	protected DynamicForm _form;
	protected ValuesManager _vm;
	protected AS2ListGrid _lgrid;
	protected Object _formOrVm;
	protected AS2StatusBar2 _statusBar;

	public AS2ListGrid() {
		this(false, null);
	}

	public AS2ListGrid(boolean showStatusBar) {
		this(showStatusBar, null);
	}

	public AS2ListGrid(boolean showStatusBar, Object formOrVm, String id) {
		this(showStatusBar, null);
		this.setID(id);
	}	
	
	@Override
	public void setData(Record[] data) {
		super.setData(data);
	}
	
	public AS2ListGrid(boolean showStatusBar, Object formOrVm) {
		// Ako želimo filter redak prikazivati ispod headera listgrida
		// setGridComponents(new Object[] { ListGridComponent.HEADER,
		// ListGridComponent.FILTER_EDITOR, ListGridComponent.BODY, });
		_lgrid = this;
		this.setOverflow(Overflow.AUTO);// overflow:auto Layout will scroll if
										// members exceed its specified size
		// this.setHeight("200px");
		this.setHeight100();

		this.setShowRollOverCanvas(true);
		this.setCanReorderFields(true);
		// this is the default setting for the setShowFilterEditor
		this.setShowFilterEditor(false);

		// this.setHeight("200px");
		this.setShowResizeBar(true);
		// setIsGroup(true);
		// setGroupTitle("OVO JE LISTGRID");
		// this.setDataSource(dataSource);
		this.setAutoFetchData(true);
		// this.setShowShadow(true);
		// this.setLeaveScrollbarGap(false);
		// setCanDragResize(true);
		// setShowEdges(true);
		// setBackgroundColor("red");
		// setBorder("1px solid blue");
		this.setUseTouchScrolling(true);
		this.setDisableTouchScrollingForDrag(false);
		/****************** IZGLED ListGrida *******************/

		// Alternate record styles is used to create ledger effect for easier
		// reading.
		// To disable alternate record styles
		// ListGrid.setAlternateRecordStyles(false)
		// can be called.
		this.setAlternateRecordStyles(true);
		
		this.setAutoFetchDisplayMap(true);

		// setSelectionType(SelectionStyle.SINGLE);
		// setBaseStyle("simpleCell");

		/****************** IZGLED ListGrida *******************/
		// if(formOrVm!=null){
		// _formOrVm = formOrVm;
		// //TODO popraviti!,PROBLEM ako imamo tablicu bez forme,
		// this.addRecordClickHandler(new RecordClickHandler() {
		// public void onRecordClick(RecordClickEvent event) {
		// if (_formOrVm instanceof ValuesManager) {
		// _vm = (ValuesManager) _formOrVm;
		// // VIEW reset polja na formi
		// // _vm.resetValues();
		// // popuni VIEW sa odabranim poljima
		// _vm.editSelectedData(_lgrid);
		// } else if (_formOrVm instanceof DynamicForm) {
		// _form = (DynamicForm) _formOrVm;
		// // VIEW reset polja na formi
		// // _form.reset();
		// // popuni VIEW sa odabranim poljima
		// _form.editSelectedData(_lgrid);
		// }
		// }
		//
		// });

		// }

		// this.addRecordDoubleClickHandler(new RecordDoubleClickHandler() {
		//
		// @Override
		// public void onRecordDoubleClick(RecordDoubleClickEvent event) {
		// _vm = (ValuesManager) _formOrVm;
		// _vm.editSelectedData(_lgrid);
		//
		// }
		// });
		if (showStatusBar)
			initStatusBar();
	}
	
	//Postavljanje vrijednosti polja kojega se editira
	public void setFieldValue(String name, Object value) {
		if(this != null && this.getField(name)!=null){
			if(this.getField(name).getType()==ListGridFieldType.INTEGER){
				this.setEditValue(this.getEditRow(), name,Integer.parseInt(value.toString()));
			}else{
				this.setEditValue(this.getEditRow(), name, value.toString());
			}
			this.refreshRow(this.getEditRow());
		}
	}

	public void setRollover(boolean rollover){
		// RollOver, and select the rows in the grid to see rollover and
				// selection indicators fade into view.
				// This is achieved via the rollOverCanvas and selectionCanvas
				// subsystem.
				// Note that the opacity setting on the rollUnderCanvas allows true
				// color layering.

				// RollOver the rows in the grid to row-level controls buttons appear
				// embedded in the row.
				// This example utilizes the rollOverCanvas subsystem to achieve this
				// effect.
				 setShowRollOverCanvas(true);
				 setAnimateRollUnder(true);

				 Canvas rollUnderCanvasProperties = new Canvas();
				 rollUnderCanvasProperties.setAnimateFadeTime(1000);
				 rollUnderCanvasProperties.setAnimateShowEffect(AnimationEffect.FADE);
				 rollUnderCanvasProperties.setBackgroundColor("#00ffff");
				 rollUnderCanvasProperties.setOpacity(50);
//				 can also override ListGrid.getRollUnderCanvas(int rowNum, int colNum)
				 setRollUnderCanvasProperties(rollUnderCanvasProperties);

				 setShowSelectionCanvas(true);
				 setAnimateSelectionUnder(true);

				 Canvas selectionUnderCanvasProperties = new Canvas();
				 selectionUnderCanvasProperties
				 .setAnimateShowEffect(AnimationEffect.FADE);
				 selectionUnderCanvasProperties.setAnimateFadeTime(1000);
				 selectionUnderCanvasProperties.setBackgroundColor("#ffff40");
				 setSelectionUnderCanvasProperties(selectionUnderCanvasProperties);
	}
	@Override
	public void setData(RecordList data) {
		super.setData(data);
		if(_statusBar!=null){
			this.updateStatusBar();
		}
	}

	private void initStatusBar() {
		_statusBar = new AS2StatusBar2();

		// _listGrid.addSelectionChangedHandler(new SelectionChangedHandler() {
		// @Override
		// public void onSelectionChanged(SelectionEvent event) {
		// if (event.getSelection().length>0 && _statusBar!=null) {
		// updateStatusBar();
		// }
		// }
		// });
		// this.addFocusChangedHandler(new FocusChangedHandler() {
		//
		// @Override
		// public void onFocusChanged(FocusChangedEvent event) {
		// if(!event.getHasFocus())
		// _lgrid.updateStatusBar();
		//
		// }
		// });
		this.addSelectionUpdatedHandler(new SelectionUpdatedHandler() {
			@Override
			public void onSelectionUpdated(SelectionUpdatedEvent event) {
				if (_statusBar != null)
					_lgrid.updateStatusBar();

			}
		});
		this.addFilterEditorSubmitHandler(new FilterEditorSubmitHandler() {
			@Override
			public void onFilterEditorSubmit(FilterEditorSubmitEvent event) {
				if (_statusBar != null)
					_lgrid.updateStatusBar();
				_lgrid.deselectAllRecords();

			}
		});
		this.addDrawHandler(new DrawHandler() {
			@Override
			public void onDraw(DrawEvent event) {
				if (_statusBar != null)
					_lgrid.updateStatusBar();

			}
		});
		// this.addFetchDataHandler(new FetchDataHandler() {
		// @Override
		// public void onFilterData(FetchDataEvent event) {
		// updateStatusBar();
		//
		// }
		// });
		this.addDataArrivedHandler(new DataArrivedHandler() {
			@Override
			public void onDataArrived(DataArrivedEvent event) {
//				_lgrid.getResultSet().getRange(event.getStartRow(),event.getEndRow());
			
				if (_statusBar != null){
					updateStatusBar();
				}
				// _statusBar.updateStatusBar(_lgrid);

			}
		});
	}

	public void setShowPageinationInStatus(boolean show) {
		_statusBar.setShowPageination(show);
	}

	public AS2StatusBar2 getStatusBar() {
		return _statusBar;
	}

	public void updateStatusBar() {
		_statusBar.updateStatusBar(this);
	}
	
	//Kada koristimo AS2Field
	public void setFields(AS2ListGridField... fields) throws IllegalStateException {
		ListGridField[] lFields= new ListGridField[fields.length];
		for(int i=0;i<fields.length;i++){
			lFields[i]=(fields[i].getField());
		}
		super.setFields(lFields);
	}
	public void setDropDownModel(DataSource dataSource,AS2ListGridField... fields) {
		AS2RestJSONDataSource ds = (AS2RestJSONDataSource)dataSource;
		for(int i=0;i<fields.length;i++){
			Criteria criteria = null;
			if(fields[i].getAttribute(AS2Field.AS2_FILTER_CRITERIA)==null && this.getDataSource()!=null){
				DataSource listgrid_model = this.getDataSource();
				criteria =  (Criteria) listgrid_model.getField(fields[i].getField().getName()).getAttributeAsObject(AS2Field.AS2_FILTER_CRITERIA);
				fields[i].setAttribute(AS2Field.AS2_FILTER_CRITERIA,criteria);
			}else{
				criteria = (Criteria)fields[i].getAttribute(AS2Field.AS2_FILTER_CRITERIA);
			}
			criteria.getAttribute("vrsta");
			if(ds.getCache(criteria.getAttribute("vrsta"))!=null){
				if(fields[i].getEditorProperties()!=null){
					fields[i].getEditorProperties().setValueMap(ds.getCache(criteria.getAttribute("vrsta")));
					fields[i].getField().setEditorProperties(fields[i].getEditorProperties());
				}
				fields[i].getField().setValueMap(ds.getCache(criteria.getAttribute("vrsta")));
			}
		}		
		
	}
	/*//Kada koristimo AS2Field
	public void setOptionDataSource(DataSource dataSource,AS2ListGridField... fields) {
		for(int i=0;i<fields.length;i++){
			fields[i].getField().getName();
			if(fields[i].getField().getType()==null &&
					this.getDataSource()!=null && fields[i].getPickListCriteriaAs2()==null){
				DataSource ds = this.getDataSource();
//				if(ds.getField(fields[i].getField().getName()).getAttributeAsObject("editorProperties") instanceof ComboBoxItem){
//				if(ds.getField(fields[i].getField().getName()).getAttributeAsInt(AS2Field.AS2_TYPE)==AS2Field.COMBO){
//					fields[i].F_ITEM_EDITOR = AS2Field.getComboBoxItem(AS2Field.COMBO, fields[i].getField().getName(), fields[i].getField().getTitle(), fields[i].getField());
//				}else if(ds.getField(fields[i].getField().getName()).getAttributeAsInt(AS2Field.AS2_TYPE)==AS2Field.SELECT){
////					fields[i].F_ITEM_EDITOR = new SelectItem(fields[i].getField().getName());
////				}else if(ds.getField(fields[i].getField().getName()).getAttributeAsInt(AS2Field.AS2_TYPE)==AS2Field.FORM_RADIOGROUP){
////					fields[i].F_ITEM_EDITOR = new RadioGroupItem(fields[i].getField().getName());
////				}
//////					fields[i].F_ITEM_EDITOR = new FormItem(ds.getField(fields[i].getField().getName()).getAttributeAsJavaScriptObject("editorProperties"));
////				if(fields[i].F_ITEM_EDITOR instanceof ComboBoxItem){
////					
////				}
//////					}else if(ds.getField(fields[i].getField().getName()).getAttributeAsObject("editorProperties") instanceof SelectItem){
//////					
//////				}
				if(ds.getField(fields[i].getField().getName()).getAttributeAsObject("editorProperties") instanceof JavaScriptObject)
					fields[i].F_ITEM_EDITOR = new FormItem(ds.getField(fields[i].getField().getName()).getAttributeAsJavaScriptObject("editorProperties"));
				else
					fields[i].F_ITEM_EDITOR = (FormItem)ds.getField(fields[i].getField().getName()).getAttributeAsObject("editorProperties");
				fields[i].setAttribute(AS2Field.AS2_FILTER_CRITERIA, ds.getField(fields[i].getField().getName()).getAttributeAsObject(AS2Field.AS2_FILTER_CRITERIA));
				fields[i].getField().setOptionCriteria((Criteria)ds.getField(fields[i].getField().getName()).getAttributeAsObject(AS2Field.AS2_FILTER_CRITERIA));
			}
			fields[i].setOptionDataSource(dataSource);
//			fields[i].fetchDataFromCache(dataSource,this);
		}
	}
	*/
	public void setShowGridSummaryFields(boolean showGridSummary, AS2ListGridField... fields) throws IllegalStateException {
		for(int i=0;i<fields.length;i++){
			fields[i].getField().setShowGridSummary(showGridSummary); 
		}
	}
	public void setShowGroupSummaryFields(boolean showGroupSummary, AS2ListGridField... fields) throws IllegalStateException {
		for(int i=0;i<fields.length;i++){
			fields[i].getField().setShowGroupSummary(showGroupSummary);
		}
	}
	public void setSummaryFunctionFields(SummaryFunction function, AS2ListGridField... fields) throws IllegalStateException {
		for(int i=0;i<fields.length;i++){
			fields[i].getField().setSummaryFunction(function); 
		}
	}
	public void setHiddenFields(boolean hidden, AS2ListGridField... fields) throws IllegalStateException {
		for(int i=0;i<fields.length;i++){
			fields[i].getField().setHidden(hidden);
		}
	}	
	public void setAlignFields(Alignment align, AS2ListGridField... fields) throws IllegalStateException {
		for(int i=0;i<fields.length;i++){
			fields[i].getField().setAlign(align); 
		}
	}	
	public void setEnableInlineEditing(boolean inlineEdit) {
		if (inlineEdit) {
			// Master detail LISTA slogova
			/********** Layout listgrida: pozicioniranje Headera,Filtera,Body,Footera ****/

			ToolStrip gridEditControls = new ToolStrip();
			gridEditControls.setWidth100();
			gridEditControls.setHeight(24);

			final Label totalsLabel = new Label();
			totalsLabel.setPadding(5);

			LayoutSpacer spacer = new LayoutSpacer();
			spacer.setWidth("*");

			ToolStripButton saveButton = new ToolStripButton();
			saveButton.setIcon("[SKIN]/actions/save.png");
			saveButton.setTitle("Spremi sve izmjene");
			saveButton.setPrompt("Spremi sve izmjene");
			saveButton.addClickHandler(new ClickHandler() {

				@Override
				public void onClick(ClickEvent event) {
					saveAllEdits();
					redraw();
				}
			});

			ToolStripButton editButton = new ToolStripButton();
			editButton.setIcon("[SKIN]/actions/edit.png");
			editButton.setPrompt("Uredi odabrano");
			editButton.addClickHandler(new ClickHandler() {

				@Override
				public void onClick(ClickEvent event) {
					ListGridRecord record = getSelectedRecord();
					if (record == null)
						return;
					startEditing(getDataAsRecordList().indexOf(record), 0,
							false);
				}
			});

			ToolStripButton removeButton = new ToolStripButton();
			removeButton.setIcon("[SKIN]/actions/remove.png");
			removeButton.setPrompt("Obriši odabrano");
			removeButton.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					removeSelectedData();

				}
			});

			gridEditControls.setMembers(totalsLabel, spacer, saveButton,
					editButton, removeButton);

			ResultSet dataProperties = new ResultSet();
			dataProperties.addDataChangedHandler(new DataChangedHandler() {

				@Override
				public void onDataChanged(DataChangedEvent event) {
					RecordList data = getDataAsRecordList();

					if (data != null && data instanceof ResultSet
							&& ((ResultSet) data).lengthIsKnown()
							&& data.getLength() > 0) {
						totalsLabel.setContents(data.getLength() + " Records");
					} else {
						totalsLabel.setContents(" ");
					}
				}
			});
			setDataProperties(dataProperties);

			//Raspored komponenti tablice
			setGridComponents(new Object[] {ListGridComponent.FILTER_EDITOR, ListGridComponent.HEADER, ListGridComponent.BODY,gridEditControls });

			/********** Layout listgrida: pozicioniranje Headera,Filtera,Footera ****/

			this.setCanEdit(false);
			this.setEditEvent(ListGridEditEvent.NONE);
			this.setModalEditing(true);
			// _listGrid.setListEndEditAction(RowEndEditAction.NEXT);
			this.setAutoSaveEdits(false);

		}

	}
	
	public void setCheckboxSelectionAppearance(boolean enableCBX) {
		// By setting selectionAppearance to SelectionAppearance.CHECKBOX,
		// the ListGrid can use checkboxes to indicate the selected state of
		// records.
		// Only by clicking on a checkbox will the corresponding record be
		// selected or unselected.
		if (enableCBX) {
			this.setSelectionType(SelectionStyle.SIMPLE);
			this.setSelectionAppearance(SelectionAppearance.CHECKBOX);
		} else {
			// _listGrid.setSelectionType(SelectionStyle.SIMPLE);
			this.setSelectionAppearance(SelectionAppearance.ROW_STYLE);
		}

	}

	public void setShowFilterRow(boolean enableFilter, boolean onKeyPress) {
		this.updateStatusBar();
		this.deselectAllRecords();
		this.setShowFilterEditor(enableFilter);
		this.setFilterOnKeypress(onKeyPress);
	}

	public void refresh() {
		this.invalidateCache();
		if(_statusBar!=null)
			this.updateStatusBar();
	}

	//Problem kod resizea tablice
	public void setAutoHeightListGrid() {
		this.setShowAllRecords(true);  
		this.setHeight(100);
		this.setBodyOverflow(Overflow.VISIBLE);
		this.setOverflow(Overflow.VISIBLE);
		this.setAutoHeight();
	}
	
	public void setDataSource(DataSource dataSource,AS2ListGridField... fields) {
		ListGridField[] lgFields = new ListGridField[fields.length];
		for (int i = 0; i < fields.length; i++) {
			lgFields[i] = (fields[i].getField());
		}
		super.setFields(lgFields);
	}
	
	//Workaround u developementu. U produkciji bi trebalo raditi!
	//Primjer: http://forums.smartclient.com/showthread.php?t=29977
	@Override
	public int getRowHeight(ListGridRecord record, int rowNum) {
		return super.getCellHeight();
	}
	public void setFixedRecordHeights(Boolean fixedRecordHeights){
		super.setFixedRecordHeights(fixedRecordHeights);
		super.setVirtualScrolling(false); //uklanja prazan prostor na kraju listgrida koli se pojavljuje kad je fixedRecordHeights(false) 
	}
}
