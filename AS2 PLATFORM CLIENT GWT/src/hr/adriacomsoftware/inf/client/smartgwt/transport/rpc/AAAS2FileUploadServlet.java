package hr.adriacomsoftware.inf.client.smartgwt.transport.rpc;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
/**
 * The client side stub for the RPC service.
 */
@RemoteServiceRelativePath("upload")
public interface AAAS2FileUploadServlet extends RemoteService {
	String upload(String topic, String stream, String Tag, String plink, String fpath );
	}