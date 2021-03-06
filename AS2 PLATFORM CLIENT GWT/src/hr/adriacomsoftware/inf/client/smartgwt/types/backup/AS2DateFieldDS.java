package hr.adriacomsoftware.inf.client.smartgwt.types.backup;
import java.util.Date;

import com.google.gwt.i18n.shared.DateTimeFormat;
import com.smartgwt.client.data.SimpleType;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.util.DateDisplayFormatter;
import com.smartgwt.client.util.DateParser;
import com.smartgwt.client.util.DateUtil;
import com.smartgwt.client.widgets.form.fields.DateItem;
import com.smartgwt.client.widgets.form.validator.DateRangeValidator;

public class AS2DateFieldDS extends SimpleType {
	public AS2DateFieldDS(String name) {
        super(name, FieldType.DATE);

		// Format Datuma
		DateUtil.setShortDateDisplayFormatter(new DateDisplayFormatter() {
    	    public String format(Date date) {
    	        if(date == null) return null;
    	        //you'll probably want to create the DateTimeFormat outside this method.
    	        //here for illustration purposes
    	        DateTimeFormat dateFormatter = DateTimeFormat.getFormat("dd.MM.yyyy");
    	        String format = dateFormatter.format(date);
    	        return format;
    	    }
    	});

		 DateUtil.setDefaultDateSeparator(".");
////
//    	DateUtil.setShortDatetimeDisplayFormatter(new DateDisplayFormatter() {
//    	     public String format(Date date) {
//    	         if(date == null) return null;
//    	         final DateTimeFormat dateFormatter = DateTimeFormat.getFormat("dd.MM.yyyy");
//    	         String format = dateFormatter.format(date);
//    	         return format;
//    	     }
//    	 });
////
//    	DateUtil.setNormalDateDisplayFormatter(new DateDisplayFormatter() {
//    	     public String format(Date date) {
//    	         if(date == null) return null;
//    	         final DateTimeFormat dateFormatter = DateTimeFormat.getFormat("dd.MM.yyyy");
//    	         String format = dateFormatter.format(date);
//    	         return format;
//    	     }
//    	 });
//

    	 DateUtil.setDateParser(new DateParser() {
    		    public Date parse(String dateString) {
    		       final DateTimeFormat format = DateTimeFormat.getFormat("dd.MM.yyyy");
    		       Date date = format.parse(dateString);
    		       return date;
    		    }
    		 });

//    	 DateUtil.setDateInputFormat("dd.MM.yyyy");



    	DateItem _dateItem = new DateItem();
		_dateItem.setInputFormat("DMY");
		_dateItem.setUseMask(true);
//		_dateItem.setMaskDateSeparator(".");
//		_dateItem.setTitle("Date");
		_dateItem.setUseTextField(true);
//		_dateItem.setHint("<nobr>Unesite datum :)</nobr>");
		_dateItem.setEnforceDate(false);
//		_dateItem.setDefaultValue(true);


		Date maxDate = new Date();
		/**
		 * Postavljen MAX datum kako bi se validirao uneseni datum!
		 */
		maxDate.setYear(3000);
		_dateItem.setEndDate(maxDate);
		DateRangeValidator dateRangeValidator = new DateRangeValidator();
		dateRangeValidator.setMax(maxDate);
		dateRangeValidator.setErrorMessage("Unijeli ste pogrešan datum");
		_dateItem.setValidators(dateRangeValidator);
		_dateItem.setValidateOnExit(true);
		_dateItem.setValidateOnChange(true);
		setEditorProperties(_dateItem);
	}


}