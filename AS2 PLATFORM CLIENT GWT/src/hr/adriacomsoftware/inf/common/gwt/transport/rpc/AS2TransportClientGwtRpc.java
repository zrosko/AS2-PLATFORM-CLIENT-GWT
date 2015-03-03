package hr.adriacomsoftware.inf.common.gwt.transport.rpc;

import hr.adriacomsoftware.inf.client.smartgwt.transport.rpc.AS2GenericGwtRpcDataSourceService;
import hr.adriacomsoftware.inf.common.gwt.dto.AS2GwtValueObject;

import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("rpc")
public interface AS2TransportClientGwtRpc extends AS2GenericGwtRpcDataSourceService<AS2GwtValueObject> {
}
