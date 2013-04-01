package server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;

import com.prcse.datamodel.Artist;
import com.prcse.datamodel.Billing;
import com.prcse.datamodel.Event;
import com.prcse.datamodel.SeatingPlan;
import com.prcse.datamodel.Tour;
import com.prcse.datamodel.Venue;
import com.prcse.utils.PrcseSource;

public class PrcseDataSource extends Observable implements PrcseSource {
	
	/*   Class Variables   */
	private String url;				// URL for the database
	private String username;		// UserName for database account
	private String password;		// Password for database account
	private Connection connection;	// Connection to the database
	private static String eventQuery = "select e.id as event_id, e.name as event_name, e.start_time, e.end_time, b.id as billing_id, b.artist_id as artist_id, b.lineup_order, v.id as venue_id, v.name as venue_name, t.id as tour_id, t.name as tour_name, sp.id as seating_plan_id, sp.name as seating_plan_name from event e join seating_plan sp on e.seating_plan_id = sp.id join venue v on sp.venue_id = v.id join billing b on b.event_id = e.id left outer join tour t on b.tour_id = t.id;";
	private static String artistQuery = "select a.*, GROUP_CONCAT(g.name) as genres from artist a join artist_genres_genre agg on agg.genres_id = a.id join genre g on agg.genre_id = g.id group by a.id;";
	
	public PrcseDataSource(String url, String username, String password) {
		super();
		this.url = url;
		this.username = username;
		this.password = password;
	}

	public PrcseDataSource(Connection connection) {
		super();
		this.connection = connection;
	}
	
	@Override
	public void connect() throws Exception {
		this.connection = DriverManager.getConnection(url, username, password);
		changed();
		System.out.println("Connection successfully opened.");
	}

	@Override
	public void disconnect() throws Exception {
		this.connection.close();
		this.connection = null;
		changed();
		System.out.println("Connection returned to pool/closed.");
	}

	@Override
	public boolean isConnected() {
		return this.connection != null;
	}
	
	// Update observers
	protected void changed() {
		setChanged();
		notifyObservers();
		clearChanged();
	}

	@Override
	public ArrayList<Object> getFrontPage() throws Exception {
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
									rs.getString("thumb_image"),
									rs.getString("header_image"));
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

}
