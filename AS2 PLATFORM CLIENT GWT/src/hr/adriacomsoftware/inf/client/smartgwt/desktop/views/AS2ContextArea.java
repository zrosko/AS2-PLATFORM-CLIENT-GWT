package hr.adriacomsoftware.inf.client.smartgwt.desktop.views;

import com.google.gwt.core.client.GWT;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.layout.VLayout;

public class AS2ContextArea extends VLayout {

	private static final String CONTEXT_AREA_WIDTH = "*";
	Label label;
	Label label1;

	public AS2ContextArea() {
		super();

		GWT.log("init Context Area()...", null);

		// initialise the InvPlanView layout container
		this.setStyleName("crm-ContextArea");
		this.setWidth(CONTEXT_AREA_WIDTH);

		// add the label to the layout container
		label = new Label();
		label.setHeight(30);
		label.setPadding(10);
		label.setStyleName("crm-ContextArea-label");
		// label.setAlign(Alignment.CENTER);
		label.setValign(VerticalAlignment.CENTER);
		label.setBackgroundColor("white");
//		label.setIcon("icons/32/arrow_left.png");
		label.setWrap(false);
		// label.setShowEdges(true);
		label.setContents("Test");

		label1 = new Label();
		label1.setHeight(30);
		label1.setPadding(10);
		// label1.setAlign(Alignment.CENTER);
		label1.setValign(VerticalAlignment.CENTER);
		label1.setWrap(false);
		label1.setBackgroundColor("white");
		label1.setIcon("icons/32/arrow_left.png");
		// label1.setShowEdges(true);
		label1.setContents("Odaberite perspektivu");
//		this.addMember(label);
//		this.addMember(label1);
	}
	
}
