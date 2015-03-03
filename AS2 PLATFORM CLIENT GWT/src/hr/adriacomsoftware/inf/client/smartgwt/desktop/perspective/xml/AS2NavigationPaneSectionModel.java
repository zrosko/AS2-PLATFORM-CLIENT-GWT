package hr.adriacomsoftware.inf.client.smartgwt.desktop.perspective.xml;

public interface AS2NavigationPaneSectionModel {
	public static final String DEFAULT_RECORD_NAME ="test";
	public static final String ICON = "icon";
	public static final String ICON_DISPLAY_NAME = "Icon";
	public static final String NAME = "name";
	public static final String NAME_DISPLAY_NAME = "Name";
	public static final String DISPLAY_NAME = "displayname";
	public static final String DISPLAY_NAME_DISPLAY_NAME = "Display Name";
	public static final String VIEW_DISPLAY_NAME = "viewdisplayname";
	public static final String VIEW_DISPLAY_NAME_DISPLAY_NAME = "View Display Name";
	public static final String DEFAULT = "default";
	public static final String DEFAULT_FUNCTION_NAME = "default_function_name";
	public static final String DEFAULT_FUNCTION_DISPLAY_NAME = "default_function_displayname";
	public static final String DEFAULT_FUNCTION_VIEW_DISPLAY_NAME = "default_function_viewdisplayname";
	
	public abstract String getSectionName();

	public abstract String getDefaultPerspectiveName();
}
