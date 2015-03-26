package server;

/*   Class Imports   */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;

import com.prcse.datamodel.Artist;
import com.prcse.datamodel.Billing;
import com.prcse.datamodel.Event;
import com.prcse.datamodel.SeatingPlan;
import com.prcse.datamodel.Tour;
import com.prcse.datamodel.Venue;
import com.prcse.protocol.CustomerBooking;
import com.prcse.protocol.CustomerForm;
import com.prcse.protocol.CustomerInfo;

import archive.Person;


public class PeopleDataSource extends Observable implements PeopleSource {
	
	/*   Class Variables   */
	private String url;				// URL for the database
	private String username;		// UserName for database account
	private String password;		// Password for database account
	private Connection connection;	// Connection to the database
	private static String eventQuery = "select e.id as event_id, e.name as event_name, e.start_time, e.end_time, b.id as billing_id, b.artist_id as artist_id, b.lineup_order, v.id as venue_id, v.name as venue_name, t.id as tour_id, t.name as tour_name, sp.id as seating_plan_id, sp.name as seating_plan_name from event e join seating_plan sp on e.seating_plan_id = sp.id join venue v on sp.venue_id = v.id join billing b on b.event_id = e.id left outer join tour t on b.tour_id = t.id;";
	private static String artistQuery = "select a.*, GROUP_CONCAT(g.name) as genres from artist a join artist_genres_genre agg on agg.genres_id = a.id join genre g on agg.genre_id = g.id group by a.id;";
	
	/*    Class Methods    */
	// Default constructor
	public PeopleDataSource(String url, String username, String password) {
		super();
		this.url = url;
		this.username = username;
		this.password = password;
	}
	
	public PeopleDataSource(Connection connection) {
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

	public ArrayList getFrontPage() throws Exception {
		ArrayList result = new ArrayList();
		HashMap<Long, Artist> artists = new HashMap<Long, Artist>();
		HashMap<Long, Event> events = new HashMap<Long, Event>();
		HashMap<Long, Venue> venues = new HashMap<Long, Venue>();
		HashMap<Long, Tour> tours = new HashMap<Long, Tour>();
		HashMap<Long, SeatingPlan> seatingPlans = new HashMap<Long, SeatingPlan>();
		Artist a = null;
		
		PreparedStatement stmt = this.connection.prepareStatement(artistQuery);
		
		ResultSet rs = stmt.executeQuery();
		
		while (rs.next()){
			a = new Artist(rs.getLong("id"), 
									rs.getString("name"), 
									rs.getString("bio"), 
									rs.getString("genres"),
									rs.getString("thumb_image"));
			result.add(a);
			artists.put(new Long(a.getId()), a);
		}
		
		rs.close();
		stmt.close();
		
		stmt = this.connection.prepareStatement(eventQuery);
		
		rs = stmt.executeQuery();
		
		while (rs.next()){
			Venue v = venues.get(new Long(rs.getLong("venue_id")));
			if(v == null) {
				v = new Venue(rs.getLong("venue_id"), 
								rs.getString("venue_name"));
				venues.put(new Long(v.getId()), v);
			}
			
			SeatingPlan sp = seatingPlans.get(new Long(rs.getLong("venue_id")));
			if(sp == null) {
				sp = new SeatingPlan(rs.getLong("seating_plan_id"), 
								rs.getString("seating_plan_name"),
								v);
				seatingPlans.put(new Long(sp.getId()), sp);
			}
			
			Event e = events.get(new Long(rs.getLong("event_id")));
			if(e == null) {
				e = new Event(rs.getLong("event_id"), 
								rs.getString("event_name"), 
								rs.getDate("start_time"),
								rs.getDate("end_time"));
				events.put(new Long(e.getId()), e);
				sp.addEvent(e);
				e.setSeatingPlan(sp);
			}
			
			a = artists.get(new Long(rs.getLong("artist_id")));
			
			if(rs.getLong("tour_id") != 0){
				Tour t = tours.get(new Long(rs.getLong("tour_id")));
				if(t == null) {
					t = new Tour(rs.getLong("tour_id"), 
									rs.getString("tour_name"), 
									a);
					tours.put(new Long(t.getId()), t);
					a.addTour(t);
				}
			}
			
			Billing b = new Billing(rs.getLong("billing_id"), 
												a,
												e,
												rs.getInt("lineup_order"));
			
			a.addBilling(b);
			e.addBilling(b);
		}
		
		rs.close();
		stmt.close();
		
		return result;
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
