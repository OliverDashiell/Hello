package archive;

import java.io.Serializable;

import protocol.BaseRequest;

import server.PeopleSource;


public class Echo extends BaseRequest {
	
	public Echo(String message) {
		this.message = message;
	}

	private String message;

	public void setMessage(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void handleRequest(PeopleSource dataSource) {
		// Do nothing
	}
	
	public String toString(){
		return "Echo:" + message;
		
	}

	public Object getResult() {
		return message;
	}

	public String getError() {
		return null;
	}

	public boolean shouldBroadcast() {
		// TODO Auto-generated method stub
		return false;
	}
}
