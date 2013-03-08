package protocol;

import java.io.Serializable;
import java.util.ArrayList;

import model.Person;

import server.PeopleSource;

public class DeletePerson extends BaseRequest {

	private String error;
	private Person person;

	public DeletePerson(Person person) {
		this.person = person;
	}

	public void handleRequest(PeopleSource dataSource) {
		try {
			dataSource.deletePerson(person);
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
		// TODO Auto-generated method stub
		return null;
	}

	public String getError() {
		// TODO Auto-generated method stub
		return this.error;
	}

	public boolean shouldBroadcast() {
		// TODO Auto-generated method stub
		return true;
	}

}
