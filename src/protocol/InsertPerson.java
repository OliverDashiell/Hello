package protocol;

import model.Person;

import server.PeopleSource;

public class InsertPerson extends BaseRequest {

	private String name;
	private Long result;
	private Person person;
	
	public InsertPerson(String name) {
		this.name = name;
	}
	
	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void handleRequest(PeopleSource dataSource) {
		try {
			this.result = dataSource.insertPerson(name);
			this.person = new Person(name, result.longValue());
		} catch (Exception e) {
			e.printStackTrace();
			this.error = e.getMessage();
		}
	}

	public Object getResult() {
		return this.result;
	}

	public boolean shouldBroadcast() {
		// TODO Auto-generated method stub
		return true;
	}

}
