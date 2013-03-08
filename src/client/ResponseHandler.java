package client;

import protocol.Request;

public interface ResponseHandler {
	
	public void handleResponse(Request response);
}
