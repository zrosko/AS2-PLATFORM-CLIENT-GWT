package hr.adriacomsoftware.inf.client.smartgwt.types;

import hr.adriacomsoftware.app.client.smartgwt.resources.AS2Resources;

import com.smartgwt.client.widgets.form.events.ItemChangedEvent;
import com.smartgwt.client.widgets.form.fields.FormItemIcon;
import com.smartgwt.client.widgets.form.fields.events.FormItemClickHandler;
import com.smartgwt.client.widgets.form.fields.events.FormItemIconClickEvent;

public class AS2FormItemIcon extends FormItemIcon {

	public static final int HR_ICON = 0; //HR
	public static final int SI_ICON = 1; //ŠIBENIK
	public static final int PB_ICON = 2; //22000
	public static final int ATTACHMENT_DOWNLOAD_ICON = 3; 
	public static final int ATTACHMENT_UPLOAD_ICON = 4;
	public static final int ATTACHMENT_DELETE_ICON = 5;
	
	// if using default icon. Location:
	// [SKIN]/images/DynamicForm/default_formItem_icon.gif
	public AS2FormItemIcon() {
		setSrc("../module/images/icons/s16/default_formItem_icon.png");
		setTabIndex(-1);
	}

	public AS2FormItemIcon(final int icon) {
		this.setTabIndex(-1);
		switch (icon) {
		case HR_ICON:
			AS2FormItemIcon.this.setSrc(AS2Resources.HR_PATH);
			break;
		case SI_ICON:
			AS2FormItemIcon.this.setSrc(AS2Resources.SI_PATH);
			break;
		case PB_ICON:
			AS2FormItemIcon.this.setSrc(AS2Resources.PB_PATH);
			break;
		case ATTACHMENT_DOWNLOAD_ICON:
			AS2FormItemIcon.this.setPrompt("Otvori dokument");
			AS2FormItemIcon.this.setSrc(AS2Resources.OPEN_DOCUMENT_ICON_PATH);
			AS2FormItemIcon.this.setDisableOnReadOnly(false);
			break;
		case ATTACHMENT_UPLOAD_ICON:
			AS2FormItemIcon.this.setPrompt("Dodaj novi dokument");
			AS2FormItemIcon.this.setSrc(AS2Resources.ATTACHEMENT_ICON);
			AS2FormItemIcon.this.setDisableOnReadOnly(false);
			break;
		case ATTACHMENT_DELETE_ICON:
			AS2FormItemIcon.this.setPrompt("Briši dokument");
			AS2FormItemIcon.this.setSrc(AS2Resources.DELETE_PATH);
			AS2FormItemIcon.this.setDisableOnReadOnly(false);
			break;
		default:
			break;
		}
		this.addFormItemClickHandler(new FormItemClickHandler() {
			@Override
			public void onFormItemClick(FormItemIconClickEvent event) {
				switch (icon) {
				case HR_ICON:
					event.getItem().setValue("HR");
					break; 
				case SI_ICON:
					event.getItem().setValue("ŠIBENIK");
					break;
				case PB_ICON:
					event.getItem().setValue("22000");
					break;
				default:
					break;
				}
				event.getItem().getForm().fireEvent(new ItemChangedEvent(event.getItem().getJsObj()));
			}
		});
			
				
	}
	
}
