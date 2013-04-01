package protocol;

import com.prcse.protocol.BaseRequest;
import com.prcse.utils.Connectable;
import archive.Person;
import server.PeopleSource;

public class UpdatePerson extends BaseRequest {

	private Person person;
	
	public UpdatePerson(Person person) {
		this.person = person;
	}

	public void handleRequest(Connectable dataSource) {
		try {
			((PeopleSource)dataSource).updatePerson(person);
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

	public boolean shouldBroadcast() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean shouldSync() {
		// TODO Auto-generated method stub
		return false;
	}

}
