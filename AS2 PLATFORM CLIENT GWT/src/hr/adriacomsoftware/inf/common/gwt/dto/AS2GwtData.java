package hr.adriacomsoftware.inf.common.gwt.dto;


import java.util.Date;

public interface AS2GwtData extends AS2GwtConstants {
	public void setAttribute(String property, String value);
    public void setAttribute(String property, int value);
    public void setAttribute(String property, double value);
    public void setAttribute(String property, long value);
    public void setAttribute(String property, float value);
    public void setAttribute(String property, boolean value);
    public void setAttribute(String property, Integer value);
    public void setAttribute(String property, Double value);
    public void setAttribute(String property, Long value);
    public void setAttribute(String property, Float value);
    public void setAttribute(String property, Boolean value);
    public void setAttribute(String property, Date value);
	public void setAttribute(String property, Object value);
//	public void setAttribute(String property, byte[] value);
	//getters
	public Object getAttributeAsObjectOrBlank(String property);
	public String getAttribute(String property);
    public String getAttributeAsString(String property);
    public String getAttributeAsStringOrBlank(String propertyName);
    public Double getAttributeAsDouble(String property);
    public double getAttributeAsDoubleNative(String property);
    public Long getAttributeAsLong(String property);
    public Float getAttributeAsFloat(String property);
    public Boolean getAttributeAsBoolean(String property);
    public Integer getAttributeAsInt(String property);
    public Object getAttributeAsIntOrBlank(String property);
    public Integer getAttributeAsIntOrZero(String property);
    public Date getAttributeAsDate(String property);
    public Object getAttributeAsDateOrBlank(String property);
    public String[] getAttributes();
//    public  byte[] getAttributeAsByteArrayOrNull(String property);


}
