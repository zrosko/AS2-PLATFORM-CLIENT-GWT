package hr.adriacomsoftware.inf.client.smartgwt.transport.rpc;

import com.google.gwt.user.client.rpc.RemoteService;

/**
 * The client side stub for the RPC service.
 */
public interface AS2GenericGwtRpcDataSourceService <D> extends RemoteService {

	D fetch ();
    D fetch (D data);
    D add (D data);
    D update (D data);
    void remove (D data);

}

