package hr.adriacomsoftware.inf.client.smartgwt.desktop.views;

import com.google.gwt.core.client.GWT;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.ValuesManager;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.toolbar.ToolStrip;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;
//TODO dodati još botuna
public class AS2ListGridToolBar extends HLayout {

    private static final String TOOLBAR_HEIGHT = "25px";
    private static final String TOOLSTRIP_WIDTH = "*";
    //TODO po brojevima 100
    private static final String NEW_BUTTON_DISPLAY_NAME = "Novo";
    private static final String NEW_BUTTON = "toolbar/new.png";
    private static final String DELETE_BUTTON = "toolbar/remove.png";
    private static final String FILTER_BUTTON = "toolbar/filter.png";
    private static final String CLEAR_FILTER_BUTTON = "toolbar/filterclear.png";
    private static final String PRINT_PREVIEW_BUTTON = "toolbar/printpreview.png";
    private static final String EXPORT_BUTTON = "toolbar/export.png";
    private static final String REPORTS_BUTTON = "toolbar/reports.png";
    private static final String ASSIGN_BUTTON = "toolbar/assign.png";
    private static final String EMAIL_BUTTON = "toolbar/sendemail.png";



    private ListGrid _listGrid;
    private ValuesManager _vm;

    public AS2ListGridToolBar(ListGrid listGrid, ValuesManager vm) {
        super();
        this._listGrid = listGrid;
        this._vm = vm;
        GWT.log("init ToolBar()...", null);
        // initialise the ToolBar layout container
        this.setStyleName("crm-ToolBar");
        this.setHeight(TOOLBAR_HEIGHT);
        // initialise the ToolStrip
        ToolStrip toolStrip = new ToolStrip();
        toolStrip.setHeight(TOOLBAR_HEIGHT);
        toolStrip.setWidth(TOOLSTRIP_WIDTH);
        // initialise the New button
        ToolStripButton newButton = new ToolStripButton();
        newButton.setIcon(NEW_BUTTON);
        newButton.setTitle(NEW_BUTTON_DISPLAY_NAME);
        newButton.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
            	_listGrid.deselectAllRecords();
            	_vm.editNewRecord();
            }
        }
        );
        toolStrip.addButton(newButton);

        // initialise the Delete button
        ToolStripButton deleteButton = new ToolStripButton();
        deleteButton.setIcon(DELETE_BUTTON);
        deleteButton.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
            	SC.ask("Upozorenje!", "Da li ste sigurni da želite obrisati?",
						new BooleanCallback() {
							@Override
							public void execute(Boolean value) {
								// TODO Auto-generated method stub
								if (value != null && value) {
									_listGrid.removeSelectedData(); //TODO REFRESH PERSPECTIVE
									_listGrid.refreshFields();
									//REFRESH
									_vm.editNewRecord();
								}
							}
						});
            }
        }
        );
        toolStrip.addButton(deleteButton);

        // initialise the filter button
        ToolStripButton filterButton = new ToolStripButton();
        filterButton.setIcon(FILTER_BUTTON);
        filterButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				if (_listGrid.getShowFilterEditor()){
					_listGrid.setShowFilterEditor(false);
					_listGrid.setCriteria(null);
				}
				else{
					_listGrid.setShowFilterEditor(true);
					_listGrid.setCriteria(null);
				}
			}
		});
		toolStrip.addButton(filterButton);
        toolStrip.addSeparator();

        // initialise the Print Preview button
        ToolStripButton printPreviewButton = new ToolStripButton();
        printPreviewButton.setIcon(PRINT_PREVIEW_BUTTON);
        toolStrip.addButton(printPreviewButton);

        // initialise the Export button
        ToolStripButton exportButton = new ToolStripButton();
        exportButton.setIcon(EXPORT_BUTTON);
        toolStrip.addButton(exportButton);

        // initialise the Reports button
        ToolStripButton reportsButton = new ToolStripButton();
        reportsButton.setIcon(REPORTS_BUTTON);
        toolStrip.addButton(reportsButton);

        toolStrip.addSeparator();

        // initialise the Assign button
        ToolStripButton assignButton = new ToolStripButton();
        assignButton.setIcon(ASSIGN_BUTTON);
        toolStrip.addButton(assignButton);

        // initialise the Email button
        ToolStripButton emailButton = new ToolStripButton();
        emailButton.setIcon(EMAIL_BUTTON);
        toolStrip.addButton(emailButton);

        // add the ToolStrip to the ToolBar layout container
        this.addMember(toolStrip);
    }
}