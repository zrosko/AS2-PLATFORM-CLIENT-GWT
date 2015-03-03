package hr.adriacomsoftware.inf.client.smartgwt.desktop.views;

import com.smartgwt.client.widgets.Canvas;

public interface AS2ContextAreaFactory {
    Canvas create();
    String getID();
    String getDescription();
}