package protocol;

import java.util.ArrayList;

import server.PeopleSource;


public class ListPerson extends BaseRequest {

	private ArrayList result;
	
	public ListPerson(){
		
	}
	
	public void handleRequest(PeopleSource dataSource) {
		try {
			this.setResult(dataSource.listPersons());
		} catch (Exception e) {
			e.printStackTrace();
			this.error = e.getMessage();
		}
	}

	public void setResult(ArrayList result) {
		this.result = result;
	}

	public Object getResult() {
		return result;
	}

	public boolean shouldBroadcast() {
		// TODO Auto-generated method stub
		return false;
	}
	
}
