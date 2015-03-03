package hr.adriacomsoftware.inf.client.smartgwt.transport.rpc;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface AS2GenericGwtRpcDataSourceServiceAsync<D> {

	void fetch(AsyncCallback<D> callback);

	void fetch(D data, AsyncCallback<D> callback);

	void add(D data, AsyncCallback<D> callback);

	void remove(D data, AsyncCallback<Void> callback);

	void update(D data, AsyncCallback<D> callback);







}
