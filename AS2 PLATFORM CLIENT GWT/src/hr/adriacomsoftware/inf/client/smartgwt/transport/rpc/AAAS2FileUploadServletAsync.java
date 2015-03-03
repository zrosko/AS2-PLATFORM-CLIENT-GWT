package hr.adriacomsoftware.inf.client.smartgwt.transport.rpc;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface AAAS2FileUploadServletAsync {
	void upload(String topic, String stream, String Tag, String plink, String fpath, AsyncCallback<String> callback );
}
