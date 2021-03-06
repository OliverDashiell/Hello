package archive;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Observable;

import com.prcse.protocol.CustomerBooking;
import com.prcse.protocol.CustomerForm;
import com.prcse.protocol.CustomerInfo;
import com.prcse.protocol.FrontPage;
import com.prcse.protocol.Request;

import protocol.DeletePerson;
import protocol.GetPerson;
import protocol.InsertPerson;
import protocol.ListPerson;
import protocol.UpdatePerson;

import server.PeopleSource;

public class PeopleClient extends Observable implements PeopleSource {
	
	private String host;
	private int port;
	private Socket socket = null;
	private ObjectOutputStream out = null;
	private ObjectInputStream in = null;

	public PeopleClient(String host, int port) {
		this.host = host;
		this.port = port;
	}

	public Long insertPerson(String name) throws Exception {
		out.writeObject(new InsertPerson(name));
		Request response = handleResponse();
		return (Long)response.getResult();
	}

	public void updatePerson(Person person) throws Exception {
		out.writeObject(new UpdatePerson(person));
		Request response = handleResponse();
	}

	public Person getPerson(long id) throws Exception {
		out.writeObject(new GetPerson(id));
		Request response = handleResponse();
		return (Person)response.getResult();
	}

	public void deletePerson(Person person) throws Exception {
		out.writeObject(new DeletePerson(person));
		Request response = handleResponse();
	}

	public ArrayList listPersons() throws Exception {
		out.writeObject(new ListPerson());
		Request response = handleResponse();
		return (ArrayList)response.getResult();
	}
	
	public void connect() throws Exception {
		socket = new Socket(this.host, this.port);
        out = new ObjectOutputStream(socket.getOutputStream());
        in = new ObjectInputStream(socket.getInputStream());
	}

	public void disconnect() throws Exception{
		this.in.close();
		this.out.close();
		this.socket.close();
		this.socket = null;
	}

	public boolean isConnected() {
		return this.socket != null;
	}
	
	protected Request handleResponse() throws Exception {
		Request response = (Request)in.readObject();
		if(response.getError() != null) {
			throw new Exception(response.getError());
		}
		return response;
	}

	public ArrayList getFrontPage() throws Exception {
		out.writeObject(new FrontPage());
		FrontPage response = (FrontPage)handleResponse();
		return response.getArtists();
	}

	@Override
	public CustomerInfo login(CustomerInfo request) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CustomerInfo syncCustomer(CustomerInfo request) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CustomerForm getCustomerFormData(CustomerForm request)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CustomerBooking createBooking(CustomerBooking request)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CustomerBooking cancelBooking(CustomerBooking request)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
}
