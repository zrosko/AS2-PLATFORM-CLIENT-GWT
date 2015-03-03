package hr.adriacomsoftware.inf.client.smartgwt.types;


public interface AS2FieldInterface {
	public static final String AS2_TYPE = "as2_type";
	public static final int TEXT=0;
	public static final int AMOUNT=1; //2 decimals
	public static final int AMOUNT_3=100001;//3 decimals
	public static final int AMOUNT_4=100002;//4 decimals
	public static final int AMOUNT_6=100004;//6 decimals
	public static final int AMOUNT_8=100003;//8 decimals
	public static final int AMOUNT_0=100000;//0 decimals
	public static final int INTEGER=2;
	public static final int DATE=3;
	public static final int DATETIME=4;
	public static final int COMBO=5;
	public static final int PRIMARY_KEY=6;
	public static final int BINARY=7;
	public static final int FLOAT=8;
	public static final int TEXTAREA=9;
	public static final int IMAGE=10;
	public static final int BIGINT=11;
	public static final int TIME=12;
	public static final int SELECT=13;
	public static final int LINK=14;
	public static final int RICHTEXT=15;
	public static final int IMAGE_FILE=16;
	//Custom fields
	public static final int OIB=50;
	public static final int JMBG=51;
	public static final int IBAN=52;
	public static final int FORM_DATERANGE=109;
	public static final int FORM_BUTTON=110;
	public static final int FORM_RESETBUTTON=111;
	public static final int FORM_CANCELBUTTON=112;
	public static final int FORM_SUBMITBUTTON=113;
	public static final int FORM_SEPARATOR=114;
	public static final int FORM_LABEL=115;
	public static final int FORM_SECTION=116;
	public static final int FORM_MULTICOMBOBOXITEM=117;
	public static final int FORM_SELECT=118;
	public static final int FORM_CANVAS=119;
	public static final int FORM_RADIOGROUP=120;
	public static final int FORM_UPLOAD=121;
	public static final int FORM_CHECKBOX=122;
	public static final int FORM_STATIC_TEXT=123;
	public static final int DATASOURCE_FIELD = 0;
	public static final int FORM_ITEM = 1;
	public static final int LISTGRID_FIELD = 2;
	public static final String SIFRARNIK_DISPLAY_FIELD = "naziv_sifre";
	public static final String SIFRARNIK_VALUE_FIELD = "id_sifre";
	public static final String AS2_FILTER_CRITERIA = "as2_filter_criteria";
}
