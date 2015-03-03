package hr.adriacomsoftware.inf.client.smartgwt.types.backup;

import java.util.HashMap;
import java.util.Map;

import com.smartgwt.client.core.Rectangle;
import com.smartgwt.client.widgets.Dialog;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.form.fields.FormItemIcon;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.IconClickEvent;
import com.smartgwt.client.widgets.form.fields.events.IconClickHandler;

public class AS2PickerItemDialog extends TextItem {

	private String title;
    private static Dialog dialog;
    private static AS2PickerItemDialog currentEditor;

    private void makeDialog(String value) {
        dialog = new Dialog();
        dialog.setAutoCenter(true);
        dialog.setIsModal(true);
        dialog.setShowHeader(false);
        dialog.setShowEdges(false);
        dialog.setEdgeSize(10);
        dialog.setWidth(500);
        dialog.setHeight(400);

        dialog.setShowToolbar(false);
        dialog.setWidth(130);
        dialog.setHeight(110);

        Map bodyDefaults = new HashMap();
        bodyDefaults.put("layoutLeftMargin", 5);
        bodyDefaults.put("membersMargin", 10);
        dialog.setBodyDefaults(bodyDefaults);

        final IButton button = new IButton(value);
        button.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {
            public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
            	setCurrentValue(button.getTitle());
            }
        });

//        final IButton no = new IButton("NO");
//        no.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {
//            public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
//                YesNoMaybeItem.setCurrentValue(no.getTitle());
//            }
//        });
//
//        final IButton maybe = new IButton("MAYBE");
//        maybe.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {
//            public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
//                YesNoMaybeItem.setCurrentValue(maybe.getTitle());
//            }
//        });

        dialog.addItem(button);
//        dialog.addItem(no);
//        dialog.addItem(maybe);
    }

    // set the specified value and dismiss the picker dialog
    private void setCurrentValue(String value) {
        currentEditor.setValue(value);
//        dialog.hide();
        dialog.destroy();
    }

    // show the picker dialog at the specified position
    private void showDialog(int left, int top) {
        dialog.show();
        dialog.moveTo(left, top);
    }

    public AS2PickerItemDialog(String name, String value) {
        //use default trigger icon here. User can customize.
        //[SKIN]/DynamicForm/default_formItem_icon.gif
    	title=value;
    	setName(name);
        FormItemIcon formItemIcon = new FormItemIcon();
        setIcons(formItemIcon);

        addIconClickHandler(new IconClickHandler() {
            public void onIconClick(IconClickEvent event) {
                // get global coordinates of the clicked picker icon
                Rectangle iconRect = getIconPageRect(event.getIcon());

                // lazily create the picker dialog the first time a  editor is clicked
//                if (CustomPickerItem.dialog == null) {
                	makeDialog(title);
//                }
                // remember what editor is active, so we can set its value from the picker dialog
                AS2PickerItemDialog.currentEditor = AS2PickerItemDialog.this;

                // show the picker dialog
                showDialog(iconRect.getLeft(), iconRect.getTop());
            }
        });
    }
}
