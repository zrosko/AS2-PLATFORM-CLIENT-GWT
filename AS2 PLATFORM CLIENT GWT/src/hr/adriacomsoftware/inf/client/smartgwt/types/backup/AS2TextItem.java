package hr.adriacomsoftware.inf.client.smartgwt.types.backup;

import com.smartgwt.client.widgets.form.fields.FormItemIcon;
import com.smartgwt.client.widgets.form.fields.TextItem;

public class AS2TextItem extends TextItem {

	public AS2TextItem(String name) {
		super(name);
	}

	public AS2TextItem(String name, String title) {
		super(name, title);
	}

	public AS2TextItem(String name, String title,int colSpan) {
		this(name,title,colSpan,null,false,false,false);

	}

	public AS2TextItem(String name, String title, int colSpan,FormItemIcon icon){
		this(name,title,colSpan,icon,false,false,false);
	}


	public AS2TextItem(String name, String title, int colSpan,FormItemIcon icon,boolean endRow) {
		this(name,title,colSpan,icon,endRow,false,false);
	}

	public AS2TextItem(String name, String title, int colSpan,FormItemIcon icon,boolean endRow, boolean required) {
		this(name,title,colSpan,icon,endRow,required,false);
	}
	public AS2TextItem(String name, String title, int colSpan,FormItemIcon icon,boolean endRow, boolean required, boolean disabled) {
		super(name, title);
		this.setRequired(required);
		this.setDisabled(disabled);
		this.setEndRow(endRow);
		this.setColSpan(colSpan);
		this.setWidth("*");
		if(icon!=null)
			this.setIcons(icon);

	}

}
