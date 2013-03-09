package gui;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Observable;
import java.util.Observer;
import javax.swing.table.AbstractTableModel;

import com.prcse.protocol.Request;

import archive.Person;

import protocol.DeletePerson;
import protocol.InsertPerson;
import protocol.UpdatePerson;

import client.PeopleAsyncSource;
import client.ResponseHandler;



public class PeopleTableModel extends AbstractTableModel implements Observer {

	/*   Class Variables   */
	private ArrayList people;			// List of Person objects for the table
	private PeopleAsyncSource dataSource;	// Connection to the database
	
	
	/*    Class Methods    */
	// Default constructor
	public PeopleTableModel() {
    	this.people = null;
    }
	
	// Getter for people ArrayList
    public ArrayList getPeople() {
		return people;
	}
    
    // Setter for people ArrayList
	public void setPeople(ArrayList people) {
		this.people = people;
		// Notify table of changes
		this.fireTableDataChanged();
	}
	
	// Getter for column count
    public int getColumnCount() {
    	return 2;
    }
    
    // Getter for row count
    public int getRowCount() {
    	// Check list length
    	if(people == null){
    		return 0;
    	}
      	// return size of list
    	return people.size();
    }
    
    // Getter for table cell
    public Object getValueAt(int row, int col) {
    	// Get person at given index
    	Person person = (Person)this.people.get(row);
    	
    	// Check chosen column
    	if(col == 0){
    		// Return person id
    		return new Long(person.getId());
    	}
    	else {
    		// Return person name
    		return person.getName();
    	}
    }
    
    // Setter for table cell
    public void setValueAt(Object val, int row, int col) {
    	// Check for a database connection
    	if(this.dataSource.isConnected()){
    		// Get person at given index
    		final Person person = (Person)this.people.get(row);
    		
    		// Check chosen column
    		if(col == 1){
    			// Set person name
    			person.setName((String)val);
    		}
    		
    		// Try updating or inserting person to the database
	    	try {
	    		// check if id is 0 (means unsaved person)
	    		if(person.getId() == 0){
	    			// insert person and get the id from the database
	    			this.dataSource.insertPerson(person.getName(), 
	    					new ResponseHandler(){

								public void handleResponse(Request response) {
									// update the person object
					    			person.setId(((Long)response.getResult()).longValue());
					    			// Notify table of changes
					   	    	 	fireTableDataChanged();
								}
	    			});
	    		}
	    		else{
	    			// update person
	    			this.dataSource.updatePerson(person, 
	    					new ResponseHandler(){

						public void handleResponse(Request response) {
							// output to console update successful
							System.out.println("Update successful");
						}
	    			});
	    		}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    }
    
    // Getter for column type
    public Class getColumnClass(int c) {
    	// Check given column
    	if(c == 1){
    		return String.class;
    	}
    	return String.class;
    }
    
    // Set if cell is editable
    public boolean isCellEditable(int row, int col) {
    	// Check cell is not in the id column and database is connected
    	return col == 1 && this.dataSource.isConnected();
    }
    
    // Setter for DataSource variable (database connection)
	public void setDataSource(PeopleAsyncSource dataSource) throws Exception {
		this.dataSource = dataSource;
		// Check for connection
		if(this.dataSource.isConnected()){
			// Set people list to match database
			this.dataSource.listPersons(new ResponseHandler(){

				public void handleResponse(Request response) {
					//set people list to result
					setPeople((ArrayList)response.getResult());
				}
			});
		}
		// Add an observer for this class
		this.dataSource.addObserver(new Observer(){
			public void update(Observable arg0, Object arg1) {
				// Check for connection
				if(getDataSource().isConnected()){
					try {
						// Set people list to match database
						getDataSource().listPersons(new ResponseHandler(){

							public void handleResponse(Request response) {
								//set people list to result
								setPeople((ArrayList)response.getResult());
							}
						});
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				else{
					// TODO Cancel any outstanding edits before firing change
					// Update table with changes
					fireTableDataChanged();
				}
			}
		});
	}
	
	// get the current DataSource
	public PeopleAsyncSource getDataSource(){
		return dataSource;
	}
	
	// Add a person to the table to be edited
	public int addPerson(){
		// New default person to be edited
		Person person = new Person();
		// Add to the list of people
		this.people.add(person);
		// Update table
		this.fireTableDataChanged();
		// return the index of the new person
		return this.people.size() - 1;
	}
	
	// Delete a person from the database and tables
	public void deletePerson(int row) throws Exception{
		// Check for a connection
		if(this.dataSource.isConnected()){
			// Get person to be deleted
			final Person person = (Person)this.people.get(row);
			// Check they exist in database (id is not 0)
			if(person.getId() != 0){
				// Delete selected person
				this.dataSource.deletePerson(person, new ResponseHandler(){

					public void handleResponse(Request response) {
						// output to console delete successful
						System.out.println("Update successful");
					}
				});
			}
			// Remove from table
			this.people.remove(row);
			// Update table
			this.fireTableDataChanged();
		}
	}

	// Someone else has changed the database
	public void update(Observable arg0, Object arg1) {
		if(arg1 != null) {
			System.out.println("External change recieved.");
			if(arg1 instanceof UpdatePerson) {
				UpdatePerson message = (UpdatePerson)arg1;
				Person person = getPersonById(message.getPerson().getId());
				if(person != null) {
					person.setName(message.getPerson().getName());
					this.fireTableDataChanged();
				}
			}
			else if(arg1 instanceof DeletePerson) {
				DeletePerson message = (DeletePerson)arg1;
				int row = getRowByPersonId(message.getPerson().getId());
				if(row != -1) {
					this.people.remove(row);
					this.fireTableDataChanged();
				}
			}
			else if(arg1 instanceof InsertPerson) {
				InsertPerson message = (InsertPerson)arg1;
				this.people.add(message.getPerson());
				this.fireTableDataChanged();
			}
		}
	}

	private int getRowByPersonId(long id) {
		for(int i = 0; i < people.size(); i++) {
			Person person = (Person)people.get(i);
			if(person.getId() == id) {
				return i;
			}
		}
		return -1;
	}

	private Person getPersonById(long id) {
		for(Iterator i = people.iterator(); i.hasNext();) {
			Person person = (Person)i.next();
			if(person.getId() == id) {
				return person;
			}
		}
		return null;
	}
  }