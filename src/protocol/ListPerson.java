package protocol;

import java.util.ArrayList;

import com.prcse.protocol.BaseRequest;
import com.prcse.utils.Connectable;

import server.PeopleSource;


public class ListPerson extends BaseRequest {

	private ArrayList result;
	
	public ListPerson(){
		
	}
	
	public void handleRequest(Connectable dataSource) {
		try {
			this.setResult(((PeopleSource)dataSource).listPersons());
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
