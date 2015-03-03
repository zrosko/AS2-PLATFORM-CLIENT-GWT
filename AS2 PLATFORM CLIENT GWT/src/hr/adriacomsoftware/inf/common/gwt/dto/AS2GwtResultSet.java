package hr.adriacomsoftware.inf.common.gwt.dto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class AS2GwtResultSet extends AS2GwtValueObject{
	private List<AS2GwtValueObject> _rows = new ArrayList<AS2GwtValueObject>();
	private ArrayList<String> _columnNames;
	private HashMap<String, Integer> _columnSize;

	public AS2GwtResultSet(){}

	public AS2GwtResultSet(AS2GwtResultSet set)	{
		super();
	}
	public AS2GwtResultSet(List<AS2GwtValueObject> rows) {
		super();
		_rows=rows;
	}
	public void addRow(AS2GwtValueObject row) {
	    if (row != null)
	        getRows().add(row);
	}
	public List<AS2GwtValueObject>getRows() {
		if (_rows==null)
			_rows = new ArrayList<AS2GwtValueObject>();
		return _rows;
	}
	public void setRows(List<AS2GwtValueObject> value) {
		_rows = value;
	}
    public List<AS2GwtValueObject> returnList(){
    	return _rows;
    }
    public void deleteRow(int rowIdx) {
    	if(rowIdx <= _rows.size())
    		getRows().remove(rowIdx);
    }

    public String toString() {

    	StringBuffer buf = new StringBuffer(100);
        int size = 0;
        if(_rows!=null){
        	Iterator<AS2GwtValueObject> E = _rows.iterator();
        	while (E.hasNext()) {
    	        buf.append("-----------------------------\n");
    	        buf.append(E.next().toString());
    	    }
    	    size = _rows.size();
        }
        buf.append("-----------------------------\n");
        buf.append("rows ");
        buf.append(size);
        return buf.toString();
    }
    public void setColumnNames(ArrayList<String> value){
		_columnNames = value;
	}
    public ArrayList<String> getColumnNames(){
    	if (_columnNames==null)
    		_columnNames = new ArrayList<String>();

    	return _columnNames;
    }

	public void setColumnSizes(HashMap<String, Integer> value){
		_columnSize= value;
	}

    public HashMap<String, Integer> getColumnSizes(){
    	if (_columnSize==null)
    		_columnSize = new HashMap<String, Integer>();

    	return _columnSize;
    }
}
