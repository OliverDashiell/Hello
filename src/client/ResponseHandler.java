package client;

import com.prcse.protocol.Request;

public interface ResponseHandler {
	
	public void handleResponse(Request response);
}
