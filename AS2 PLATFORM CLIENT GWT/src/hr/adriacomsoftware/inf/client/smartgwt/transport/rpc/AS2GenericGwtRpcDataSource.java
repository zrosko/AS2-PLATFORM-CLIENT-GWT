package hr.adriacomsoftware.inf.client.smartgwt.transport.rpc;

import hr.adriacomsoftware.inf.client.smartgwt.models.AS2ListGridRecord;
import hr.adriacomsoftware.inf.common.gwt.dto.AS2GwtConstants;
import hr.adriacomsoftware.inf.common.gwt.dto.AS2GwtData;
import hr.adriacomsoftware.inf.common.gwt.dto.AS2GwtResultSet;
import hr.adriacomsoftware.inf.common.gwt.dto.AS2GwtValueObject;
import hr.adriacomsoftware.inf.common.gwt.transport.rpc.AS2TransportClientGwtRpc;
import hr.adriacomsoftware.inf.common.gwt.transport.rpc.AS2TransportClientGwtRpcAsync;
import hr.as2.inf.common.data.AS2MetaData;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

import com.google.gwt.core.client.Duration;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.IsSerializable;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.DataSourceField;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.rpc.RPCResponse;
import com.smartgwt.client.util.JSOHelper;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.tree.TreeNode;
import com.smartgwt.client.widgets.viewer.DetailViewerRecord;

/**
 * Generic abstract {@link AS2GwtRpcDataSource} implementation, based on the
 * {@link AS2GwtRpcDataSource} example provided in the smartgwt-extensions.
 * Extend this class if you want to create a GwtRpcDataSource for SmartGWT.
 *
 * In order to use this class, you have to implement both
 * {@link AS2GenericGwtRpcDataSourceService} and
 * {@link AS2GenericGwtRpcDataSourceServiceAsync} provided in the same package.
 *
 * @param <D>
 *            type of the transfer object holding the data (will most likely be
 *            a simple POJO), must implement {@link Serializable} or
 *            {@link IsSerializable}.
 * @param <R>
 *            any extension of {@link Record}, such as {@link ListGridRecord},
 *            {@link DetailViewerRecord} or {@link TreeNode} to use with your
 *            SmartGWT widget.
 * @param <SA>
 *            the asynchronous version of your service. Extend
 *            {@link AS2GenericGwtRpcDataSourceService} and then
 *            {@link AS2GenericGwtRpcDataSourceServiceAsync} to implement it.
 *
 * @see AS2GwtRpcDataSource
 * @see AS2GenericGwtRpcDataSourceService
 * @see AS2GenericGwtRpcDataSourceServiceAsync
 *
 * @author Francois Marbot
 * @author Aleksandras Novikovas
 * @author Zdravko Rosko
 * @author from version 2.0 Ante Strikoman
 * @author System Tier
 * @version 2.0
 */
public abstract class AS2GenericGwtRpcDataSource<D,R extends Record, SA extends AS2GenericGwtRpcDataSourceServiceAsync<D>>
        extends AS2GwtRpcDataSource implements AS2GwtConstants {
    public AS2GenericGwtRpcDataSource() {
        super();
        List<DataSourceField> fields = getDataSourceFields();
        if (fields != null) {
            for (DataSourceField field : fields) {
                addField(field);
            }
        }

    }
    /**
     * @return a list of {@link DataSourceField}, used to define the fields of
     *         your {@link DataSource}. NOTE: Make sure to set a primary key, as
     *         some problems might occur if it's omitted.
     */

    protected abstract List<DataSourceField> getDataSourceFields();

    /**
     * Copies values from the {@link Record} to the data object.
     *
     * @param rec
     *            the {@link Record} to copy from.
     * @param vo
     *            the data object to copy to.
     * @param operationId
     *            just an information - which CRUD operation is copying values
     */
    public void copyValuesFromRecordtoVo(AS2ListGridRecord rec, AS2GwtValueObject vo, String operationId) {
    	String[] _fieldAttributes = rec.getAttributes();
        DataSource dataSource=this;
     	for(String field:_fieldAttributes){
     		//Edit data on client side
     		List<String> changedFields = copyClientData(rec, vo,operationId,field);
     		//If field is changed in copyClientData then we need to skip that field
 			if(!(changedFields.size()!=0 && changedFields.contains(field))){
 				//Check if the field is in dataSource and check its type
 				if(dataSource.getField(field)!=null ){
 					vo.setAttributesFromRecord(rec,dataSource.getField(field));
 				}else{//if not in dataSource then do default setter
 					vo.setAttribute(field,rec.getAttribute(field));
 				}
 			}
 		}
     	vo.setAttribute(__COMPONENT, getServerFacadeName());
     	vo.setAttribute(__SERVICE, rec.getAttribute(__SERVICE));
     	vo.setAttribute(__TRANSFORM_TO, getServerValueObjectName());
    }

    /**
     * Copies values from the data object to the {@link Record}.
     *
     * @param vo
     *            the data object to copy from.
     * @param rec
     *            the {@link Record} to copy to.
     * @param operationId
     *            just an information - current CRUD operation that is copying values
     * @param columnNames
     *            columnNames of table returned from database
     */
    public void copyValuesFromVoToRecord(AS2GwtValueObject vo, AS2ListGridRecord rec, String operationId, String[] columnNames) {
    	String[]_fieldAttributes = columnNames;
//    	vo.getAttributes()
    	LinkedHashMap<String,AS2MetaData>_metaData = vo.getMetaData();
    	DataSource dataSource=this;
    	for(String field:_fieldAttributes){
      		//Edit data on client side
      		List<String> changedFields = copyClientData(vo, rec,operationId,field);
      		//If field is changed in copyClientData then we need to skip that field
			if(!(changedFields.size()!=0 && changedFields.contains(field))){
				//Check if the field is in dataSource and check its type
				if(dataSource.getField(field)!=null){
					rec.setAttributesFromVo(vo,dataSource.getField(field));
				}else if(_metaData.get(field)!=null){//if not in dataSource check the field type in metadata
					rec.setAttributesFromVo(vo,field,_metaData.get(field).getColumnTypeName());//TODO TEST getColumnTypeName
				}else{//if not in dataSource and metadata then do default setter
					rec.setAttribute(field,vo.getAttribute(field));
				}
			}
    	}
    }

    /**
     * User can implement this method on client side to edit
     * data for sending/receiving to/from server or client
     * @param from
     *            the data object/{@link Record} to copy from.
     * @param to
     *            the data object/{@link Record} to copy to.
     * @param operationId
     *            just an information - current CRUD operation that is copying values
     * @param field
     *            current field
     * @param metaData
     *            meta data of table returned from database
     */
    protected List<String> copyClientData(AS2GwtData from, AS2GwtData to, String operationId,String fieldAttributes){
		return new ArrayList<String>();
	}

    /**
     * @return the {@link AS2GenericGwtRpcDataSourceServiceAsync} to use,
     *         created using
     *         <code>GWT.create(YourGenericGwtRpcDataSourceService.class)</code>
     *         .
     *
     *         This is unfortunately necessary as <code>GWT.create()</code> only
     *         allows class literal as argument. We cannot create a class
     *         literal from a parameterized type because it has no exact runtime
     *         type representation, which is due to type erasure at compile
     *         time.
     */
    protected AS2TransportClientGwtRpcAsync getServiceAsync() {
        return GWT.create(AS2TransportClientGwtRpc.class);
    }

    /**
     * @return a new instance of your {@link Record}, such as
     *         <code>new Record()</code> or <code>new ListGridRecord()</code>.
     *
     *         This method is needed because we cannot instantiate parameterized
     *         types at runtime. It also increases flexibility as we can pass
     *         more complex default objects.
     */
    protected AS2ListGridRecord getNewRecordInstance() {
        return new AS2ListGridRecord();
    }

    /**
     * @return a new instance of your data object, such as
     *         <code>new YourDataObject()</code>.
     *
     *         This method is needed because we cannot instantiate parameterized
     *         types at runtime. It also increases flexibility as we can pass
     *         more complex default objects.
     */
    protected AS2GwtValueObject getNewDataObjectInstance() {
        AS2GwtValueObject dto = new AS2GwtValueObject();
        return dto;
    }

    @Override
    protected void executeFetch(final String requestId,
            final DSRequest request, final DSResponse response) {
		// These can be used as parameters to create paging.
		// request.setStartRow(0);
		// request.setEndRow(100);
		// request.getSortBy ();
        AS2ListGridRecord newRec = getNewRecordInstance();
        newRec.setJsObj(request.getData());
        AS2GwtValueObject data = getNewDataObjectInstance();
        ((Record) newRec).setAttribute(AS2GwtValueObject.__SERVICE,
                getExecuteFetchServiceName(request.getOperationId()));
        Duration fetchDuration = new Duration();
        copyValuesFromRecordtoVo(newRec, data, "fetch");
        System.out.println("copyValuesFromRecordtoVo DURATION " + fetchDuration.elapsedMillis());
        getServiceAsync().fetch(data,new AsyncCallback<AS2GwtValueObject>() {
                    public void onFailure(Throwable caught) {
                        response.setStatus(RPCResponse.STATUS_FAILURE);
                        processResponse(requestId, response);

                    }

                    public void onSuccess(AS2GwtValueObject result) {
                    	//OLD
//                    	String[] columnNames = ((AS2GwtResultSet)result).getColumnNames().toArray(new String[((AS2GwtResultSet)result).getColumnNames().size()]);
//                    	List<AS2ListGridRecord> records = new ArrayList<AS2ListGridRecord>();
//                    	Duration fetchedDuration = new Duration();
//                        for (Iterator<AS2GwtValueObject> iter = ((AS2GwtResultSet)result).returnList().iterator(); iter.hasNext(); ) {
//                        	AS2GwtValueObject vo = iter.next();
//                          	AS2ListGridRecord newRec = getNewRecordInstance();
////                          	Duration fetchedVoDuration = new Duration();
//                          	copyValuesFromVoToRecord(vo, newRec,"fetchedRS",columnNames);
////                         	System.out.println("copyValuesFromVoToRecord ONE VO DURATION " + fetchedVoDuration.elapsedMillis());
//                          	records.add(newRec);
//                         }
//                         System.out.println("copyValuesFromVoToRecord DURATION " + fetchedDuration.elapsedMillis());
//                         response.setData(records.toArray(new Record[records.size()]));
//                    	  processResponse(requestId, response);
                    	 //NEW
                    	 processResponse(requestId, prepareResponse(response,result,"fetched"));
                    }
                });
    }

    @Override
    protected void executeAdd(final String requestId, final DSRequest request,
            final DSResponse response) {
        // Retrieve record which should be added.
        AS2ListGridRecord newRec = getNewRecordInstance();
        newRec.setJsObj(request.getData());
        AS2GwtValueObject data = getNewDataObjectInstance();
        ((Record) newRec).setAttribute(AS2GwtValueObject.__SERVICE,
                getExecuteAddServiceName());
        copyValuesFromRecordtoVo(newRec, data,"add");
        getServiceAsync().add(data, new AsyncCallback<AS2GwtValueObject>() {
            public void onFailure(Throwable caught) {
                response.setStatus(RPCResponse.STATUS_FAILURE);
                processResponse(requestId, response);
            }

            public void onSuccess(AS2GwtValueObject result) {
            	//OLD
//            	AS2ListGridRecord newRec = getNewRecordInstance();
//                String[] columnNames = ((AS2GwtResultSet)result).getColumnNames().toArray(new String[((AS2GwtResultSet)result).getColumnNames().size()]);
//                copyValuesFromVoToRecord(result, newRec,"added",columnNames);
//            	response.setData(new Record[] { newRec });
//            	 processResponse(requestId, response);
            	//NEW
                processResponse(requestId, prepareResponse(response,result,"added"));
            }
        });
    }

    @Override
    protected void executeUpdate(final String requestId,
            final DSRequest request, final DSResponse response) {
        // Retrieve record which should be updated.
        AS2ListGridRecord editedRec = getEditedRecord(request);
        AS2GwtValueObject data = getNewDataObjectInstance();
        ((Record) editedRec).setAttribute(AS2GwtValueObject.__SERVICE,
                getExecuteUpdateServiceName());
        copyValuesFromRecordtoVo(editedRec, data,"update");
        getServiceAsync().update(data, new AsyncCallback<AS2GwtValueObject>() {
            public void onFailure(Throwable caught) {
                response.setStatus(RPCResponse.STATUS_FAILURE);
                processResponse(requestId, response);
            }

            public void onSuccess(AS2GwtValueObject result) {
            	//OLD
//            	AS2ListGridRecord updatedRec = getNewRecordInstance();
//            	String[] columnNames = ((AS2GwtResultSet)result).getColumnNames().toArray(new String[((AS2GwtResultSet)result).getColumnNames().size()]);
//                copyValuesFromVoToRecord(result, updatedRec,"updated",columnNames);
//            	  response.setData(new Record[] { updatedRec });
//            	 processResponse(requestId, response);
              	//NEW
                processResponse(requestId, prepareResponse(response,result,"updated"));
            }
        });
    }

    @Override
    protected void executeRemove(final String requestId,
            final DSRequest request, final DSResponse response) {
        // Retrieve record which should be removed.
        final AS2ListGridRecord rec = getNewRecordInstance();
        rec.setJsObj(request.getData());
        AS2GwtValueObject data = getNewDataObjectInstance();
        data.setAttribute(AS2GwtValueObject.__SERVICE,
              getExecuteRemoveServiceName());
        ((Record) rec).setAttribute(AS2GwtValueObject.__SERVICE,
                getExecuteRemoveServiceName());
        //Record contains data
        copyValuesFromRecordtoVo(rec, data,"remove");
//        AS2GwtResultSet to_server = new AS2GwtResultSet();
//        if(data instanceof AS2GwtResultSet)
//            to_server=(AS2GwtResultSet)data;
//        else
//            to_server.addRow(data);
        getServiceAsync().remove(data, new AsyncCallback<Void>() {
            public void onFailure(Throwable caught) {
                response.setStatus(RPCResponse.STATUS_FAILURE);
                processResponse(requestId, response);
            }

            public void onSuccess(Void v) {
                // We do not receive removed record from server.
                // Return record from request.
                response.setData(new Record[] { rec });
                processResponse(requestId, response);
            }

        });
    }
    protected void executeValidate(final String requestId,
            final DSRequest request, final DSResponse response) {
    }

    protected void executeCustom(final String requestId,
            final DSRequest request, final DSResponse response) {

    }

    private DSResponse prepareResponse(DSResponse response,AS2GwtValueObject result, String operationId){
    	//Retrieving columnNames so we can copy vo to record by this names
    	String[] columnNames = ((AS2GwtResultSet)result).getColumnNames().toArray(new String[((AS2GwtResultSet)result).getColumnNames().size()]);
    	List<AS2ListGridRecord> records = new ArrayList<AS2ListGridRecord>();
    	Duration crudDuration = new Duration();
    	//Getting ValuObject from ResultSet that was returned from server
        for (Iterator<AS2GwtValueObject> iter = ((AS2GwtResultSet)result).returnList().iterator(); iter.hasNext(); ) {
           	AS2GwtValueObject vo = iter.next();
          	AS2ListGridRecord newRec = getNewRecordInstance();
//          Duration fetchedVoDuration = new Duration();
          	//Copying current VO to a Record
          	copyValuesFromVoToRecord(vo, newRec,operationId,columnNames);
//         	System.out.println("copyValuesFromVoToRecord ONE VO DURATION " + fetchedVoDuration.elapsedMillis());
          	//Adding newly created record (with data copied from VO) to a list of records
          	records.add(newRec);
         }
         System.out.println("copyValuesFromVoToRecord DURATION FOR "+operationId.toUpperCase()+" "+ crudDuration.elapsedMillis());
         //Adding List of RECORDs to a DS response and then sending to a databound component
         response.setData(records.toArray(new Record[records.size()]));
         return response;
    }

    private AS2ListGridRecord getEditedRecord(DSRequest request) {
        // Retrieving values before edit
        JavaScriptObject oldValues = request.getOldValues().getJsObj();
        // Creating new record for combining old values with changes
        AS2ListGridRecord newRecord = getNewRecordInstance();
        // Copying properties from old record
        JSOHelper.apply(oldValues, newRecord.getJsObj());
        // Retrieving changed values
        JavaScriptObject changedData = request.getData();
        // Apply changes
        JSOHelper.apply(changedData, newRecord.getJsObj());
        return newRecord;
    }

    public abstract String getServerValueObjectName();

    public abstract String getServerFacadeName();

    public abstract String getExecuteFetchServiceName(String operation_id);

    public abstract String getExecuteAddServiceName();

    public abstract String getExecuteUpdateServiceName();

    public abstract String getExecuteRemoveServiceName();



//  public static <T> boolean contains( final T[] array, final T v ) {
//      for ( final T e : array )
//          if ( e == v || v != null && v.equals( e ) )
//              return true;
//
//      return false;
//  }


}
