package protocol;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;

import server.PeopleSource;


public class ListPerson extends BaseRequest {

	private ArrayList result;
	private String error;
	
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

	public void setError(String error) {
		this.error = error;
	}

	public String getError() {
		return error;
	}

	public boolean shouldBroadcast() {
		// TODO Auto-generated method stub
		return false;
	}
	
}
