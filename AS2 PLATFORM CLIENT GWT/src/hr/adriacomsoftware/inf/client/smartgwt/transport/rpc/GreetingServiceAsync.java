package hr.adriacomsoftware.inf.client.smartgwt.transport.rpc;

import java.util.HashMap;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface GreetingServiceAsync {
	void greetServer(HashMap<String, Object> input, AsyncCallback<HashMap<String, Object>> callback)
			throws IllegalArgumentException;
}
