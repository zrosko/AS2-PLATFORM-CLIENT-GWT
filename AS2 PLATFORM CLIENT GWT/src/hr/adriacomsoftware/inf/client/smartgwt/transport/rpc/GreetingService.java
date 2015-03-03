package hr.adriacomsoftware.inf.client.smartgwt.transport.rpc;

import java.util.HashMap;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
/**
 * The client side stub for the RPC service.
 */
@RemoteServiceRelativePath("rpc2")
public interface GreetingService extends RemoteService {
	HashMap<String, Object> greetServer(HashMap<String, Object> input);
}