package hr.adriacomsoftware.inf.client.smartgwt.views;

import hr.adriacomsoftware.app.client.smartgwt.resources.AS2Resources;
import hr.adriacomsoftware.inf.client.smartgwt.desktop.views.AS2GwtDesktop;

import java.util.Stack;

import com.smartgwt.client.types.ImageStyle;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.Img;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.LayoutSpacer;
import com.smartgwt.client.widgets.layout.VLayout;

public class AS2WaitPopup {
	private final Stack<String> stack = new Stack<String>();
	private final Canvas canvas = createModalMessage();
	private Label label;

	public void show(String actionName) {
		if (actionName == null)
			actionName = "";
		stack.push(actionName);
		setMessage();
		canvas.show();
	}

	private void setMessage() {
		String msg = AS2GwtDesktop._messages.listGrid_loadingDataMessage();//"Učitavanje podataka…";
		for (String action : stack) {
			if (!action.equals("")) {
				msg = action;
				break;
			}
		}
		msg = msg + "<br>"+AS2GwtDesktop._messages.waitPopup_please_wait();
		label.setContents(msg);
	}

	public void hide() {
		if (!stack.isEmpty()) {
			stack.pop();
			if (stack.isEmpty())
				canvas.hide();
		}
	}

	public void hideFinal() {
		stack.removeAllElements();
		canvas.hide();
	}

	private Canvas createModalMessage() {
		Img loadingIcon = new Img(AS2Resources.INSTANCE.loading().getSafeUri().asString(), 16, 16);
		loadingIcon.setShowEdges(false);
		loadingIcon.setImageType(ImageStyle.NORMAL);
		loadingIcon.setLeft(-50);

		label = new Label();
		label.setWidth(120);
		label.setHeight100();
		label.setValign(VerticalAlignment.CENTER);

		HLayout hLayout = new HLayout();
		hLayout.setLayoutMargin(5);
		hLayout.setMembersMargin(5);

		VLayout vLayout = new VLayout();
		vLayout.setMembers(new LayoutSpacer(), loadingIcon, new LayoutSpacer());
		hLayout.setMembers(new LayoutSpacer(), vLayout, label, new LayoutSpacer());

		Window window = new Window();
		window.setShowHeader(false);
		window.setShowHeaderBackground(false);
		window.setShowHeaderIcon(false);
		window.setIsModal(true);
		window.setShowModalMask(true);
		window.setWidth(180);
		window.setHeight(60);
		window.addItem(hLayout);
		window.setModalMaskOpacity(2);
		window.centerInPage();
		return window;
	}

}