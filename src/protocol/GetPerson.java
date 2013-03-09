package protocol;

import archive.Person;
import server.PeopleSource;


public class GetPerson extends BaseRequest {

	private long id;
	private Person result;
	
	public GetPerson(long id) {
		this.id = id;
	}

	public void handleRequest(PeopleSource dataSource) {
		try {
			this.setResult(dataSource.getPerson(this.getId()));
		} catch (Exception e) {
			e.printStackTrace();
			this.error = e.getMessage();
		}
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getId() {
		return id;
	}

	public void setResult(Person result) {
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
