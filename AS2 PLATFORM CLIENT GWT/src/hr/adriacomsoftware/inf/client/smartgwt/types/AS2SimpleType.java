package hr.adriacomsoftware.inf.client.smartgwt.types;

import com.smartgwt.client.core.DataClass;
import com.smartgwt.client.data.DataSourceField;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.SimpleType;
import com.smartgwt.client.data.SimpleTypeFormatter;
import com.smartgwt.client.data.SimpleTypeParser;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.widgets.DataBoundComponent;

public class AS2SimpleType extends SimpleType{
	
	protected int type = AS2Field.AMOUNT;
	
	public SimpleType getAmountSimpleType(DataSourceField dsField, int as2_Type) {
		int decimals = AS2Field.getNumOfDecimals(as2_Type);
		type=as2_Type;
		if(SimpleType.getType("as2Amount_"+decimals)==null){
			SimpleType amount = new SimpleType("as2Amount_"+decimals, FieldType.FLOAT);
			amount.setNormalDisplayFormatter(new SimpleTypeFormatter() {
				@Override
				public String format(Object value, DataClass dataClass, DataBoundComponent dataBoundComponent, Record record) {
					if(value==null) return null;
					return (AS2Field.formatAmountValue(value,type));
				}
			});
			amount.setShortDisplayFormatter(new SimpleTypeFormatter() {
				@Override
				public String format(Object value, DataClass dataClass, DataBoundComponent dataBoundComponent, Record record) {
					if(value==null) return null;
					return (AS2Field.formatAmountValue(value, type));
				}
			});
			amount.setEditFormatter(new SimpleTypeFormatter() {
				@Override
				public String format(Object value, DataClass dataClass, DataBoundComponent dataBoundComponent, Record record) {
					if(value==null) return null;
					return (AS2Field.formatAmountValue(value, type));
				}
			});
			amount.setEditParser(new SimpleTypeParser() {
				@Override
				public Object parseInput(String value, DataClass dataClass, DataBoundComponent dataBoundComponent, Record record) {
					return (AS2Field.parseAmountValue(value,type));
				}
			});
			amount.register();
		}
		return SimpleType.getType("as2Amount_"+decimals);
		
		
	}

}
