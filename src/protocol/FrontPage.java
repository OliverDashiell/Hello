package protocol;

import java.util.ArrayList;

import server.PeopleSource;

public class FrontPage extends BaseRequest {
	
	private ArrayList artists;
	private ArrayList events;
	private boolean shouldBroadcast;
	
	
	public void handleRequest(PeopleSource dataSource) {
		// TODO Auto-generated method stub
		
	}

	public Object getResult() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean shouldBroadcast() {
		// TODO Auto-generated method stub
		return this.shouldBroadcast;
	}
	
	public void setShouldBroadcast(boolean value) {
		this.shouldBroadcast = value;
	}
}
