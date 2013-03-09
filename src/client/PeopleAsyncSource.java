package client;

import archive.Person;
import protocol.DeletePerson;
import protocol.GetPerson;
import protocol.InsertPerson;
import protocol.ListPerson;
import protocol.Request;
import protocol.UpdatePerson;

public class PeopleAsyncSource extends AsyncSource {
	
	public PeopleAsyncSource(String host, int port) {
		super(host, port);
	}

	// Insert a person
	public void insertPerson(String name, ResponseHandler callBack) throws Exception {
		Request request = new InsertPerson(name);
		request.setRequestId(this.nextRequestId());
		this.requestCallbacks.put(new Integer(request.getRequestId()), callBack);
		addToOutput(request);
	}

	// Update a person
	public void updatePerson(Person person, ResponseHandler callBack) throws Exception {
		Request request = new UpdatePerson(person);
		request.setRequestId(this.nextRequestId());
		this.requestCallbacks.put(new Integer(request.getRequestId()), callBack);
		addToOutput(request);
	}

	// Get a person
	public void getPerson(long id, ResponseHandler callBack) throws Exception {
		Request request = new GetPerson(id);
		request.setRequestId(this.nextRequestId());
		this.requestCallbacks.put(new Integer(request.getRequestId()), callBack);
		addToOutput(request);
	}

	// Delete a person
	public void deletePerson(Person person, ResponseHandler callBack) throws Exception {
		Request request = new DeletePerson(person);
		request.setRequestId(this.nextRequestId());
		this.requestCallbacks.put(new Integer(request.getRequestId()), callBack);
		addToOutput(request);
	}

	// Get all people
	public void listPersons(ResponseHandler callBack) throws Exception {
		Request request = new ListPerson();
		request.setRequestId(this.nextRequestId());
		this.requestCallbacks.put(new Integer(request.getRequestId()), callBack);
		addToOutput(request);
	}
}