package protocol;

import java.io.Serializable;

import model.Person;
import server.PeopleSource;

public class UpdatePerson extends BaseRequest {

	private Person person;
	private String error;
	
	public UpdatePerson(Person person) {
		this.person = person;
	}

	public void handleRequest(PeopleSource dataSource) {
		try {
			dataSource.updatePerson(person);
		} catch (Exception e) {
			e.printStackTrace();
			this.error = e.getMessage();
		}
	}
	
	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public Object getResult() {
		return null;
	}

	public String getError() {
		return this.error;
	}

	public boolean shouldBroadcast() {
		// TODO Auto-generated method stub
		return true;
	}

}