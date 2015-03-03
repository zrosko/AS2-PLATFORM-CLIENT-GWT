package hr.adriacomsoftware.inf.client.smartgwt.types.listgrid.rolloverbutton;

import hr.adriacomsoftware.app.client.smartgwt.resources.AS2Resources;

import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.ImgButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridRecord;

public class AS2RolloverButtonFactory extends ImgButton {

	public static final int EDIT_BUTTON = 0;
	public static final int DELETE_BUTTON = 1;
	public static final int CUSTOM_BUTTON = 2;
	public static ListGridRecord _rollOverRecord;
	
	public static ImgButton getRolloverButton(int button,ListGrid listGrid, ClickHandler clickHandler) {
		ImgButton img = new ImgButton();
		img.setHeight(16);
		img.setWidth(16);
		img.setShowDown(false);
		img.setShowRollOver(false);
		img.setLayoutAlign(Alignment.CENTER);
		switch (button) {
		case EDIT_BUTTON:
			getEditButton(img,listGrid,clickHandler);
			break;
		case DELETE_BUTTON:
			getDeleteButton(img,listGrid,clickHandler);
			break;
		default:
			break;
		}
		return img;
	}

	public static ImgButton getRolloverCustomButton(int button,final ListGrid listGrid, String icon, String prompt,
			ClickHandler clickHandler) {
		ImgButton img = new ImgButton();
		img.setHeight(16);
		img.setWidth(16);
		img.setShowDown(false);
		img.setShowRollOver(false);
		img.setLayoutAlign(Alignment.CENTER);
		img.setSrc(icon);
//		img.setSrc(AS2Resources.INSTANCE.delete_icon().getSafeUri().asString());
		img.setPrompt(prompt);
		img.addClickHandler(clickHandler);
		return img;
	}
	
	private static ImgButton getEditButton(ImgButton img,final ListGrid listGrid, ClickHandler clickHandler) {
		img.setSrc(AS2Resources.INSTANCE.edit_icon().getSafeUri().asString());
		img.setPrompt("Uredi");
		if(clickHandler==null){
			img.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					final Record record = _rollOverRecord;
					listGrid.setCanEdit(true);
					listGrid.startEditing(listGrid.getDataAsRecordList().indexOf(record));
				}
			});
		}else{
			img.addClickHandler(clickHandler);
		}
		return img;
	}

	private static ImgButton getDeleteButton(ImgButton img,final ListGrid listGrid, ClickHandler clickHandler) {
		img.setSrc(AS2Resources.INSTANCE.delete_icon().getSafeUri().asString());
		img.setPrompt("Briši");
		if(clickHandler==null){
			img.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					final Record record = _rollOverRecord;
					SC.ask("Je ste li sigurni da želite obrisati redak?", new BooleanCallback() {
						@Override
						public void execute(Boolean value) {
							if(value){
								listGrid.removeData(record);
							}
						}
					});
				}
			});
		}else{
			img.addClickHandler(clickHandler);
		}
		return img;
	}
	

	
//	protected ClickHandler getEditHandler(final ListGrid listGrid) {
//	return new ClickHandler() {
//		public void onClick(ClickEvent event) {
//			listGrid.setCanEdit(true);
//			listGrid.startEditing(listGrid.getDataAsRecordList().indexOf(rollOverRecord));
//		}
//	};
//}

}
