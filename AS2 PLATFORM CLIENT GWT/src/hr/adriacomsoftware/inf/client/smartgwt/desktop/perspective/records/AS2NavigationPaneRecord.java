package hr.adriacomsoftware.inf.client.smartgwt.desktop.perspective.records;
import hr.adriacomsoftware.inf.client.smartgwt.desktop.views.AS2ContextAreaFactory;

import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.CellDoubleClickHandler;

public class AS2NavigationPaneRecord extends ListGridRecord {

    public AS2NavigationPaneRecord() {
    }
    public AS2NavigationPaneRecord(String icon, String name, Canvas view,
                                CellDoubleClickHandler clickHandler) {
        setIcon(icon);
        setName(name);
        setView(view);
        setDoubleClickHandler(clickHandler);
    }
    public void setIcon(String appIcon) {
        setAttribute("icon", appIcon);
    }
    public void setName(String appName) {
        setAttribute("name", appName);
    }
    public void setView(Canvas view) {
        setAttribute("view", view);
    }
    public void setDoubleClickHandler(CellDoubleClickHandler clickHandler) {
        setAttribute("clickHandler", clickHandler);
    }
    public String getIcon() {
        return getAttributeAsString("icon");
    }
    public String getName() {
        return getAttributeAsString("name");
    }
    public AS2ContextAreaFactory getFactory() {
        return (AS2ContextAreaFactory) getAttributeAsObject("factory");
    }
    public CellDoubleClickHandler getDoubleClickHandler() {
        return (CellDoubleClickHandler) getAttributeAsObject("clickHandler");
    }
}
