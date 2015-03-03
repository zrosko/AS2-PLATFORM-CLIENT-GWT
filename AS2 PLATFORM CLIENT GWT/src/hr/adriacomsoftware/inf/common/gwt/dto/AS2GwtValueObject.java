/*
 * RABA (Reference Architecture for Business Applications)
 * Copyright 2013 and beyond, Adriacom Software, Inc.
 *
 * RABA is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License version 3
 * as published by the Free Software Foundation.  RABA is also
 * available under typical commercial license terms - see
 * http://www.rosko.hr/license
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 */
package hr.adriacomsoftware.inf.common.gwt.dto;

import hr.adriacomsoftware.inf.client.smartgwt.models.AS2ListGridRecord;
//import hr.as2.inf.common.data.AS2MetaData;
import hr.as2.inf.common.data.AS2MetaData;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.rpc.IsSerializable;
import com.smartgwt.client.data.DataSourceField;
import com.smartgwt.client.types.FieldType;
/**
 * Osnovna Data Transfer Object (DTO) klasa za prijenos podataka
 * sa klijenta na server. Podaci se čuvaju u obliku "String"-a za
 * vrijeme transporta te se pretvaraju u/od specifične objekte npr.
 * Date korištenjem GET i SET metoda.
 *
 * (C) 2012 - Adriacom Software d.o.o.
 * @author zrosko@gmail.com
 * @version 1.0
 */
public class AS2GwtValueObject implements IsSerializable, AS2GwtData {

	private LinkedHashMap<String, String> attributes = new LinkedHashMap<String, String>();
	protected LinkedHashMap<String, AS2MetaData> _metaData;
//	protected Vector<String> _columnNames;
//	protected HashMap<String, String> _columnSize;

	public AS2GwtValueObject(){}

	public void setAttributesFromRecord(AS2ListGridRecord rec, DataSourceField dataSourceField){
		if(dataSourceField.getType()==FieldType.INTEGER)
				this.setAttribute(dataSourceField.getName(),rec.getAttributeAsIntOrBlank(dataSourceField.getName()));
			else if(dataSourceField.getType()==FieldType.TEXT)
				this.setAttribute(dataSourceField.getName(),rec.getAttributeAsStringOrBlank(dataSourceField.getName()));
			else if(dataSourceField.getType()==FieldType.FLOAT)
				this.setAttribute(dataSourceField.getName(),rec.getAttribute(dataSourceField.getName()));
			else if(dataSourceField.getType()==FieldType.DATE || dataSourceField.getType()==FieldType.DATETIME || dataSourceField.getType()==FieldType.TIME)
				this.setAttribute(dataSourceField.getName(),rec.getAttributeAsDateOrBlank(dataSourceField.getName()));
			else
				this.setAttribute(dataSourceField.getName(),rec.getAttribute(dataSourceField.getName()));
	}

    @SuppressWarnings("unchecked")
	public AS2GwtValueObject(AS2GwtValueObject value) {
        this();
        if (value != null) {
        	attributes = (LinkedHashMap<String, String>) value.attributes.clone();
        }
    }
	public HashMap<String, String> getAllAttributes(){
		return attributes;
	}
    public void setService(String value) {
    	if(value!=null)
    		setAttribute(__SERVICE, value);
    }
    public void setComponent(String value) {
        setAttribute(__COMPONENT, value);
    }
    public void setMessage(String value) {
        setAttribute(__MESSAGE, value);
    }
    public void setStatus(String value) {
        setAttribute(__STATUS, value);
    }
	public void setAllAttributes(LinkedHashMap<String, String> value){
		attributes = value;
	}
	public void setAttribute(String property, String value) {
   		attributes.put(property, value);
    }
    public void setAttribute(String property, int value) {
   		attributes.put(property, new Integer(value).toString());
    }
    public void setAttribute(String property, double value) {
   		attributes.put(property, new Double(value).toString());
    }
    public void setAttribute(String property, long value) {
   		attributes.put(property, new Long(value).toString());
    }
    public void setAttribute(String property, float value) {
   		attributes.put(property, new Float(value).toString());
    }
    public void setAttribute(String property, boolean value) {
   		attributes.put(property, new Boolean(value).toString());
    }

    public void setAttribute(String property, Integer value) {
    	if(property!=null && value!=null){
//    		int value_int = value;
//    		attributes.put(property, value_int+"");
    		attributes.put(property, value.toString());

    	}

    }
    public void setAttribute(String property, Double value) {
    	if(property!=null && value!=null)
    		attributes.put(property, value.toString());
    }
    public void setAttribute(String property, Long value) {
    	if(property!=null && value!=null)
    		attributes.put(property, value.toString());
    }
    public void setAttribute(String property, Float value) {
    	if(property!=null && value!=null)
    		attributes.put(property, value.toString());
    }
    public void setAttribute(String property, Boolean value) {
    	if(property!=null && value!=null)
    		attributes.put(property, value.toString());
    }
    public void setAttribute(String property, Date value) {
    	if(property!=null && value!=null)
    		attributes.put(property, value.getTime()+"");
    }
//    public void setAttribute(String property, byte[] value) {
//		if(property!=null && value!=null)
//    		attributes.put(property,Arrays.toString(value));
//	}
//    /**
//	 * @gwt.typeArgs property<java.lang.String>,value<java.lang.Object>
//	 */
	public void setAttribute(String property, Object value) {
		if (property != null && value != null) {
			if (value instanceof String) {
				setAttribute(property, (String) value);
			} else if (value instanceof Integer) {
				setAttribute(property, ((Integer) value).toString());
			} else if (value instanceof Float) {
				setAttribute(property, ((Float) value).toString());
			} else if (value instanceof Double) {
				setAttribute(property, ((Double) value).toString());
			} else if (value instanceof Long) {
				setAttribute(property, ((Long) value).toString());
			} else if (value instanceof Boolean) {
				setAttribute(property, ((Boolean) value).toString());
			} else if (value instanceof Date) {
				setAttribute(property, ((Date) value).getTime()+"");
			} else if (value instanceof JavaScriptObject) {
				System.out.println("TODO: AS2ValueObject:(value instanceof JavaScriptObject)");
				setAttribute(property, ((JavaScriptObject) value));
			}else {
				System.out.println("TODO: AS2ValueObject:(Object.toString())");
				//J2EEUser
				//setAttribute(property, value.toString());
			}
		}
	}
    //getters
    public String getService() {
        return getAttributeAsString(__SERVICE);
    }
    public String getComponent() {
        return getAttributeAsString(__COMPONENT);
    }
    public String getMessage() {
        return getAttributeAsString(__MESSAGE);
    }
    public String getStatus() {
        return getAttributeAsString(__STATUS);
    }
    public String getAttribute(String property) {
        return (String)attributes.get(property);
    }
    public Object getAttributeAsObject(String property) {
        return attributes.get(property);
    }
    public String getAttributeAsString(String property) {
    	return (String)attributes.get(property);
    }
//    @Override
//	public byte[] getAttributeAsByteArrayOrNull(String property) {
//    	String attribute =  getAttribute(property);
//    	if (attribute == null)
//			return null;
//    	else{
//    	String[] byteValues = attribute.substring(1, attribute.length() - 1).split(",");
//    	byte[] bytes = new byte[byteValues.length];
//
//    	for (int i=0, len=bytes.length; i<len; i++) {
//    	   bytes[i] = Byte.valueOf(byteValues[i].trim());
//    	}
//
//    	return bytes;
//
//    	}
//	}
    @Override
	public String getAttributeAsStringOrBlank(String property) {
		Object o = getAttribute(property);
		if (o == null)
			return ""; //$NON-NLS-1$
		else
			return o.toString();
	}

    public Integer getAttributeAsInt(String property) {
    	Integer value = new Integer(0);
    	try {
    		value = new Integer((String)attributes.get(property));
    	}catch(Exception e){
    		return value;
    	}
    	return value;
    }

	@Override
	public Object getAttributeAsIntOrBlank(String property) {
		Integer value = new Integer(0);
		try {
    		value = new Integer((String)attributes.get(property).toString());
    	}catch(Exception e){
    		return "";
    	}
		return value;
	}

	@Override
	public Integer getAttributeAsIntOrZero(String property) {
		Integer value = new Integer(0);
		try {
    		value = new Integer((String)attributes.get(property).toString());
    	}catch(Exception e){
    		return 0;
    	}
		return value;
	}

    public Boolean getAttributeAsBoolean(String property) {
    	return new Boolean((String)attributes.get(property).toString());
    }
    public Double getAttributeAsDouble(String property) {
    	Double value = new Double(0);
    	try {
    		value = new Double((String)attributes.get(property).toString());
    	}catch(Exception e){
    		return value;
    	}
    	return value;
    }
    public double getAttributeAsDoubleNative(String property) {
    	Double value = new Double(0);
    	try {
    		value = new Double((String)attributes.get(property).toString());
    	}catch(Exception e){
    		return value.doubleValue();
    	}
    	return value.doubleValue();
    }
    public Long getAttributeAsLong(String property) {
        Double value = this.getAttributeAsDouble(property);
        return value == null ? null : value.longValue();
    }
    public Float getAttributeAsFloat(String property) {
    	return new Float((String)attributes.get(property).toString());
    }
    public Date getAttributeAsDate(String property) {
    	return new Date(getAttributeAsLong(property).longValue());
    }

	public Object getAttributeAsDateOrBlank(String property) {
//		long longDate = 0;
//		DateUtil.setDefaultDateSeparator(".");
//		DateUtil.setDateParser(new DateParser() {
//			public Date parse(String dateString) {
//				// final DateTimeFormat format = DateTimeFormat
//				// .getFormat("dd.MM.yyyy");
//				Date date = dateFormatter.parse(dateString);
//				return date;
//			}
//		});
		property = property.trim();
		String value = getAttributeAsString(property);
		if (property == null || value == null || value.equals("") )
			return "";
//		Long value = getAttributeAsLong(property);
//		if (property == null || value == null)
//			return "";
		else {
//			java.util.Date date= null;
//
//			try{
//				date = dateFormatter.parse(value);
//				longDate=date.getTime();
//
//			}
//			catch (Exception e){
//				return "";
//			}

//			java.sql.Date mDate=null;

//			try
//			{
//				mDate = java.sql.Date.valueOf(value);
//			}
//			catch (Exception e)
//			{
//				//db - changed to utilize our date formatter
//				//		mDate = new java.sql.Date(java.util.Date.parse(aDate));
//				mDate = new java.sql.Date(java.sql.Date.valueOf(value).getTime());
//				//bd
//			}
//			Date longValue = getAttributeAsDate(property);
//			java.sql.Date sql = (java.sql.Date) dateFormatter.parse(value);
//			java.util.Date date = dateFormatter.parse(value);

			try {
//				date = new java.text.SimpleDateFormat("dd.MM.yyyy").parse(value);
//				getFormat("yyyy-MM-dd HH:mm:ss.S")
				Date date = DateTimeFormat.getFormat("yyyy-MM-dd HH:mm:ss.S").parse(value);
//				Date formatedDate = new Date();
//				DateTimeFormat dateformatter = DateTimeFormat.getFormat("dd.MM.yyyy");
//				formatedDate = dateformatter.format(date);
//				longDate = date.getTime();
				return new Date(date.getTime());
//				return new Date();

			} catch (Exception e) {
				e.printStackTrace();
			}
//			java.util.Date date = J2EEDate.convertDateOrTimestamp(value);
//			DateUtil.setDefaultDateSeparator(".");
//			java.util.Date date = new java.util.Date();
//			date.set
//			longDate = date.getTime();
//			try{

//				java.util.Date date = dateFormatter.parse(value);
////				return new
////			}catch (Exception e){
////				java.sql.Date _date = (java.sql.Date) dateFormatter.parse(property);
////				longDate= _date.getTime();
////			}
//			longDate = date.getTime();
//			return new Date(longDate);
//			return new Date(longValue);
			return "";
		}

	}

    public String[] getAttributes() {
        return attributes.keySet().toArray(new String[getSize()]);//TODO attributes.values().toArray();
    }
    public int getSize(){
    	return attributes.size();
    }
    public Iterator<String> keys() {
        if (attributes != null){
        	Set<String> _keys = attributes.keySet();
        	return _keys.iterator();
            //return attributes.keys();
        }else
            return null;
    }
    public String toString() {
        StringBuffer buf = new StringBuffer(9000);
        buf.append("\nAS2=");
        buf.append("\nComponent="+getComponent());
        buf.append("\nService="+getService());
        buf.append("\n");
        String name;
        Set<?> keys  = attributes.entrySet();
        Iterator<?> E = keys.iterator();
        while (E.hasNext()) {
        	Map.Entry<?, ?> m =(Map.Entry<?, ?>)E.next();
        	name =(String) m.getKey();
            Object value = getAttribute(name);
            if (value instanceof java.lang.String) {
                //if(get(name).getClass().getName().equals("java.lang.String")){//$NON-NLS-1$
                if (buf.length() < 9000) {
                    buf.append(name);
                    buf.append("="); //$NON-NLS-1$
                    buf.append(value.toString());
                    buf.append("\n"); //$NON-NLS-1$
                }
            }
        }
        return buf.toString();
    }
	@Override
	public Object getAttributeAsObjectOrBlank(String property) {
		Object o = getAttribute(property);
		if (o == null)
			return "";
		else
			return o;
	}

//	public void setColumnNames(Vector<String> value){
//		_columnNames = value;
//	}
//	public void setColumnSizes(HashMap<String, String> value){
//		_columnSize= value;
//	}
    public void setMetaData(LinkedHashMap<String, AS2MetaData> value) {
        _metaData = value;
    }

    public LinkedHashMap<String, AS2MetaData>  getMetaData() {
        if (_metaData == null)
            _metaData = new LinkedHashMap<String, AS2MetaData>();
        return _metaData;
    }

//    public Vector<String> getColumnNames(){
//    	if (_columnNames==null)
//    		_columnNames = new Vector<String>();
//
//    	return _columnNames;
//    }
//    public HashMap<String, String> getColumnSizes(){
//    	if (_columnSize==null)
//    		_columnSize = new HashMap<String, String>();
//
//    	return _columnSize;
//    }
}

