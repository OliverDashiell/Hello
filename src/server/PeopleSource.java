package server;

import java.util.ArrayList;

import com.prcse.utils.Connectable;
import com.prcse.utils.PrcseSource;

import archive.Person;

public interface PeopleSource extends PrcseSource {

	// Insert a person
	public abstract Long insertPerson(String name) throws Exception;

	// Update a person
	public abstract void updatePerson(Person person) throws Exception;

	// Get a person
	public abstract Person getPerson(long id) throws Exception;

	// Delete a person
	public abstract void deletePerson(Person person) throws Exception;

	// Get all people
	public abstract ArrayList listPersons() throws Exception;

}