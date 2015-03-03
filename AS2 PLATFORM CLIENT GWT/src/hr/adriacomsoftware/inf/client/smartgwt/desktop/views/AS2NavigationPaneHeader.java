package hr.adriacomsoftware.inf.client.smartgwt.desktop.views;
import com.google.gwt.core.client.GWT;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.layout.HLayout;

public class AS2NavigationPaneHeader extends HLayout {
//TODO riješiti properties - treba biti isti kao u navigation pane
    protected static String WEST_WIDTH = "200";
    protected static String NAVIGATION_PANE_HEADER_HEIGHT = "27px";
    protected static String NAVIGATION_PANE_HEADER_LABEL_DISPLAY_NAME = "Izbornik";
    protected static String CONTEXT_AREA_HEADER_LABEL_DISPLAY_NAME = "";

    public Label navigationPaneHeaderLabel;
    public Label contextAreaHeaderLabel;

    public AS2NavigationPaneHeader() {
        super();
        GWT.log("init NavigationPaneHeader()...", null);
        // initialise the Navigation Pane Header layout container
        this.setStyleName("crm-NavigationPane-Header");
        this.setHeight(NAVIGATION_PANE_HEADER_HEIGHT);
        // initialise the Navigation Pane Header Label
        navigationPaneHeaderLabel = new Label();
        navigationPaneHeaderLabel.setStyleName("crm-NavigationPane-Header-Label");
        navigationPaneHeaderLabel.setWidth(WEST_WIDTH);
        navigationPaneHeaderLabel.setContents(NAVIGATION_PANE_HEADER_LABEL_DISPLAY_NAME);
        navigationPaneHeaderLabel.setAlign(Alignment.LEFT);
        navigationPaneHeaderLabel.setOverflow(Overflow.HIDDEN);
        // initialise the Context Area Header Label
        contextAreaHeaderLabel = new Label();
        contextAreaHeaderLabel.setStyleName("crm-ContextArea-Header-Label");
        contextAreaHeaderLabel.setContents(CONTEXT_AREA_HEADER_LABEL_DISPLAY_NAME);
        contextAreaHeaderLabel.setAlign(Alignment.LEFT);
        contextAreaHeaderLabel.setOverflow(Overflow.HIDDEN);
        // add the Labels to the Navigation Pane Header layout container
        this.addMember(navigationPaneHeaderLabel);
        this.addMember(contextAreaHeaderLabel);
    }
    public Label getNavigationPaneHeaderLabel() {
        return navigationPaneHeaderLabel;
    }
    public Label getContextAreaHeaderLabel() {
        return contextAreaHeaderLabel;
    }
    public void setNavigationPaneHeaderLabelContents(String contents) {
        navigationPaneHeaderLabel.setContents(contents);
    }
    public String getNavigationPaneHeaderLabelContents() {
        return navigationPaneHeaderLabel.getContents();
    }
    public void setContextAreaHeaderLabelContents(String contents) {
        contextAreaHeaderLabel.setContents(contents);
    }
    public String getContextAreaHeaderLabelContents() {
        return contextAreaHeaderLabel.getContents();
    }
}
