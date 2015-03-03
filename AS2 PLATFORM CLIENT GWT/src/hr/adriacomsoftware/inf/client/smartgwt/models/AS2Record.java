package hr.adriacomsoftware.inf.client.smartgwt.models;

import hr.adriacomsoftware.inf.common.gwt.dto.AS2GwtData;

import java.util.Date;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.smartgwt.client.data.Record;

public class AS2Record extends Record implements AS2GwtData {
	/**
	 *
	 *
	 * TODO
	 * USKLADITI sa AS2LISTGRIDRECORD
	 * @param value
	 */

    public AS2Record(Record value) {
    	Record.copyAttributesInto(this, value, value.getAttributes());
    }

    public AS2Record() {
    }

    @Override
	public String getAttributeAsStringOrBlank(String propertyName) {
		Object o = getAttribute(propertyName);
		if (o == null)
			return ""; //$NON-NLS-1$
		else
			return o.toString();
	}

    @Override
    public Integer getAttributeAsInt(String property) {
    	try{
    		Integer value =  super.getAttributeAsInt(property);
    		if(value==null)
    			return new Integer(0);
    		return value;
    	}catch(Exception e){
    		return new Integer(0);
    	}
    }

    @Override
	public Object getAttributeAsIntOrBlank(String property) {
		Integer value = new Integer(0);
		try {
    		value = new Integer(super.getAttribute(property));
    	}catch(Exception e){
    		return "";
    	}
		return value;
	}

    @Override
	public Integer getAttributeAsIntOrZero(String property) {
		Integer value = new Integer(0);
		try {
    		value = new Integer(super.getAttribute(property));
    	}catch(Exception e){
    		return 0;
    	}
		return value;
	}

    @Override
    public Double getAttributeAsDouble(String property) {
    	try {
    		Double value = super.getAttributeAsDouble(property);
    		if(value==null)
    			return new Double(0);
    		return value;
    	}catch(Exception e){
    		return new Double(0);
    	}
    }
	@Override
	public void setAttribute(String property, float value) {
		super.setAttribute(property, value);
	}
	@Override
	public void setAttribute(String property, Long value) {
		super.setAttribute(property, value);
	}
//	@Override
//	public void setAttribute(String property, byte[] value) {
//		super.setAttribute(property, value+"");
//	}
	@Override
	public double getAttributeAsDoubleNative(String property) {
    	try {
    		Double value = super.getAttributeAsDouble(property);
    		if(value==null)
    			return 0;
    		return value.doubleValue();
    	}catch(Exception e){
    		return 0;
    	}
	}

	public String toString(){
		StringBuffer to_string = new StringBuffer();
		String[] properties=getAttributes();
		for (String property : properties) {
	            if (property == null) continue;
	            else if (property.equals("__ref")) continue;
	            else if (property.equals("__module")) continue;
	            Object attr = getAttributeAsObject(property);
	        	if(attr!=null)
	           		to_string.append(property+"="+attr+",");
	    }
		return to_string.toString();
	}
	@Override
	public Object getAttributeAsDateOrBlank(String property) {
		property = property.trim();
		String value = getAttributeAsString(property);
		if (property == null || value == null)
			return "";
		else {
			java.util.Date date;
			try {
				date = DateTimeFormat.getFormat("yyy-MM-dd HH:mm:ss.S").parse(value);
				return new Date(date.getTime());
			} catch (Exception e) {
				e.printStackTrace();
			}
			return "";
		}
	}
	@Override
	public Object getAttributeAsObjectOrBlank(String property) {
		Object o = getAttributeAsObject(property);
		if (o == null)
			return ""; //$NON-NLS-1$
		else
			return o.toString();
	}
//	@Override
//	public byte[] getAttributeAsByteArrayOrNull(String property) {
//		byte[] o = getAttribute(property).getBytes();
//		if (o == null)
//			return null; //$NON-NLS-1$
//		else
//			return o;
//	}
}
