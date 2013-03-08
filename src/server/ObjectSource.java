package server;

/*   Class Imports   */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Observable;
import model.Person;

public class ObjectSource extends Observable implements PeopleSource {
	
	/*   Class Variables   */
	private String url;				// URL for the database
	private String username;		// UserName for database account
	private String password;		// Password for database account
	private Connection connection;	// Connection to the database
	
	/*    Class Methods    */
	// Default constructor
	public ObjectSource(String url, String username, String password) {
		super();
		this.url = url;
		this.username = username;
		this.password = password;
	}
	
	public ObjectSource(Connection connection) {
		super();
		this.connection = connection;
	}

	// Set up and open connection
	public void connect() throws SQLException{
		this.connection = DriverManager.getConnection(url, username, password);
		changed();
		System.out.println("Connection successfully opened.");
	}
	
	// Close connection
	public void disconnect() throws SQLException{
		this.connection.close();
		this.connection = null;
		changed();
		System.out.println("Connection returned to pool/closed.");
	}
	
	// Insert a person
	/* (non-Javadoc)
	 * @see server.PeopleSource#insertPerson(java.lang.String)
	 */
	public Long insertPerson(String name) throws SQLException {
		long result = 0;
		
		PreparedStatement stmt = this.connection.prepareStatement("insert into person (name) values (?)", Statement.RETURN_GENERATED_KEYS);
		stmt.setString(1, name);
		stmt.executeUpdate();
		ResultSet rs = stmt.getGeneratedKeys();
		if (rs.next()){
			 result=rs.getLong(1);
		}
		rs.close();
		stmt.close();
		
		return new Long(result);
	}
	
	// Update a person
	/* (non-Javadoc)
	 * @see server.PeopleSource#updatePerson(model.Person)
	 */
	public void updatePerson(Person person) throws SQLException {
		
		PreparedStatement stmt = this.connection.prepareStatement("update person set name=? where id=?");
		stmt.setString(1, person.getName());
		stmt.setLong(2, person.getId());
		stmt.executeUpdate();
		stmt.close();
		
	}
	
	// Get a person
	/* (non-Javadoc)
	 * @see server.PeopleSource#getPerson(long)
	 */
	public Person getPerson(long id) throws SQLException {
		
		Person result = null;
		
		PreparedStatement stmt = this.connection.prepareStatement("select * from person where id=?");
		stmt.setLong(1, id);
		ResultSet rs = stmt.executeQuery();
		if(rs.next()){
			result = new Person(rs.getString("name"), rs.getLong("id"));
		}
		
		rs.close();
		stmt.close();

		return result;
	}
	
	// Delete a person
	/* (non-Javadoc)
	 * @see server.PeopleSource#deletePerson(model.Person)
	 */
	public void deletePerson(Person person) throws SQLException {
		
		PreparedStatement stmt = this.connection.prepareStatement("delete from person where id=?");
		stmt.setLong(1, person.getId());
		stmt.executeUpdate();
		stmt.close();
		
	}
	
	// Get all people
	/* (non-Javadoc)
	 * @see server.PeopleSource#listPersons()
	 */
	public ArrayList listPersons() throws SQLException {
		ArrayList result = new ArrayList();
		
		PreparedStatement stmt = this.connection.prepareStatement("select * from person");
		
		ResultSet rs = stmt.executeQuery();
		
		while (rs.next()){
			Person p = new Person(rs.getString("name"), rs.getLong("id"));
			result.add(p);
		}
		
		rs.close();
		stmt.close();
		
		return result;
	}
	
	// Check for a connection
	public boolean isConnected() {
		return this.connection != null;
	}
	
	// Update observers
	protected void changed() {
		setChanged();
		notifyObservers();
		clearChanged();
	}
}
