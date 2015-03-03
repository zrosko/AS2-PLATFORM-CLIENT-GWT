package hr.adriacomsoftware.inf.client.smartgwt.models;

import hr.adriacomsoftware.inf.common.gwt.dto.AS2GwtData;
import hr.adriacomsoftware.inf.common.gwt.dto.AS2GwtValueObject;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.smartgwt.client.data.DataSourceField;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.widgets.grid.ListGridRecord;

public class AS2ListGridRecord extends ListGridRecord implements AS2GwtData {

    public AS2ListGridRecord() {
    }

    public void setAttributesFromVo(AS2GwtValueObject vo, DataSourceField dataSourceField){
				if(dataSourceField.getType()==FieldType.INTEGER)
					this.setAttribute(dataSourceField.getName(),vo.getAttributeAsIntOrBlank(dataSourceField.getName()));
				else if(dataSourceField.getType()==FieldType.TEXT)
					this.setAttribute(dataSourceField.getName(),vo.getAttributeAsStringOrBlank(dataSourceField.getName()));
				else if(dataSourceField.getType()==FieldType.FLOAT)
					this.setAttribute(dataSourceField.getName(),vo.getAttribute(dataSourceField.getName()));
				else if(dataSourceField.getType()==FieldType.DATE || dataSourceField.getType()==FieldType.DATETIME || dataSourceField.getType()==FieldType.TIME)
					this.setAttribute(dataSourceField.getName(),vo.getAttributeAsDateOrBlank(dataSourceField.getName()));
				else
					this.setAttribute(dataSourceField.getName(),vo.getAttribute(dataSourceField.getName()));
    }

    public void setAttributesFromVo(AS2GwtValueObject vo,String field,String field_metadata){
				if(field_metadata.contains("int") || field_metadata.contains("int")){
					this.setAttribute(field,vo.getAttributeAsIntOrBlank(field));
				}else if(field_metadata.contains("varchar")){
					this.setAttribute(field,vo.getAttributeAsStringOrBlank(field));
				}else if(field_metadata.contains("decimal")){
					this.setAttribute(field,vo.getAttribute(field));
				}else if(field_metadata.contains("date") || field_metadata.contains("datetime")){
					this.setAttribute(field,vo.getAttributeAsDateOrBlank(field));
				}else
					this.setAttribute(field,vo.getAttribute(field));


//        to.setAttribute(__COMPONENT, getServerFacadeName());
//        to.setAttribute(__SERVICE, from.getAttribute(__SERVICE));
//        to.setAttribute(__TRANSFORM_TO, getServerValueObjectName());

    }

    @Override
	public String getAttributeAsStringOrBlank(String property) {
		Object o = getAttribute(property);
		if (o == null)
			return ""; //$NON-NLS-1$
		else
			return o.toString();
	}
    public String getAttributeAsStringOrZero(String property) {
        Object o = getAttribute(property);
        if (o != null) {
            String s = o.toString().trim();
            if (s.length() > 0)
                return o.toString();
            else
                return "0"; //$NON-NLS-1$
        } else
            return "0"; //$NON-NLS-1$
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
	@Override
	public Object getAttributeAsDateOrBlank(String property) {
		property = property.trim();
		String value = getAttributeAsStringOrBlank(property);
		if (property == null || value == null  || value.equals("") )
			return "";
		else {
			java.util.Date date;
			String result ="";
			try {
				date = getAttributeAsDate(property);
				result = DateTimeFormat.getFormat("yyyy-MM-dd HH:mm:ss.S").format(date);

			} catch (Exception e) {
				date=DateTimeFormat.getFormat("yyyy-MM-dd HH:mm:ss.S").parse(property);
//				result = datetimeformat.getformat("yyyy-mm-dd hh:mm:ss.s").format(date);
//				return value;
//				e.printStackTrace();
//				AS2GwtValueObject tempVo = new AS2GwtValueObject();
//				tempVo.setAttribute(property, value);
//				date = (Date)getAttributeAsDateOrBlank(property);
//				return new Date(date.getTime());
			}
			return result;
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

//	 @Override
//		public byte[] getAttributeAsByteArrayOrNull(String property) {
//	    	String attribute =  getAttribute(property);
//	    	if (attribute == null)
//				return null;
//	    	else{
//	    	String[] byteValues = attribute.substring(1, attribute.length() - 1).split(",");
//	    	byte[] bytes = new byte[byteValues.length];
//
//	    	for (int i=0, len=bytes.length; i<len; i++) {
//	    	   bytes[i] = Byte.valueOf(byteValues[i].trim());
//	    	}
//
//	    	return bytes;
//
//	    	}
//		}



}
