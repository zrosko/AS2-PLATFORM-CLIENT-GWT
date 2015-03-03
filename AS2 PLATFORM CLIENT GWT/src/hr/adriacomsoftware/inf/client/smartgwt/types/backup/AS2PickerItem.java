package hr.adriacomsoftware.inf.client.smartgwt.types.backup;

import com.smartgwt.client.widgets.form.fields.FormItemIcon;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.IconClickEvent;
import com.smartgwt.client.widgets.form.fields.events.IconClickHandler;

public class AS2PickerItem extends TextItem {

	private String _title;


    public AS2PickerItem(String name, String title, String type) {
        //use default trigger icon here. User can customize.
        //[SKIN]/DynamicForm/default_formItem_icon.gif
    	_title=title;
    	setName(name);
        FormItemIcon formItemIcon = new FormItemIcon();
//        formItemIcon.setSrc(ICON_URL_PREFIX+icon);
        setIcons(formItemIcon);

        addIconClickHandler(new IconClickHandler() {
            public void onIconClick(IconClickEvent event) {
//                AS2PickerItem.currentEditor = AS2PickerItem.this;
            	AS2PickerItem currentEditor = AS2PickerItem.this;
                currentEditor.setValue(_title);
            }
        });
    }
}
