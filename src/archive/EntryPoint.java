package archive;

import java.util.ArrayList;
import java.util.Calendar;

import model.Artist;
import model.Customer;
import model.Venue;

public class EntryPoint {    
/** Creates a new instance of HelloWorldApp */
	public EntryPoint() {
	}  
	
	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args)
	{
		Customer customer0 = new Customer("user0@gmail.com", "password", "MR", "James", "Stidard", "01275343224", "07955558655", "Address Line 1", "Address Line 2", "Bristol", "County");
		Customer customer1 = new Customer("user1@gmail.com", "password", "MR", "Vlad", "Radu", "01275343224", "07955558655", "Address Line 1", "Address Line 2", "London", "County");
		Customer customer2 = new Customer("user2@gmail.com", "password", "MR", "Phill", "Williams", "01275343224", "07955558655", "Address Line 1", "Address Line 2", "London", "County");
		Customer customer3 = new Customer("user3@gmail.com", "password", "MR", "Hayden", "Rose", "01275343224", "07955558655", "Address Line 1", "Address Line 2", "Manchester", "County");
		Customer customer4 = new Customer("user4@gmail.com", "password", "MR", "Dash", "Bunyan", "01275343224", "07955558655", "Address Line 1", "Address Line 2", "Clevedon", "County");
		Customer customer5 = new Customer("user5@gmail.com", "password", "MR", "Rob", "Beven", "01275343224", "07955558655", "Address Line 1", "Address Line 2", "Liverpool", "County");
		Customer customer6 = new Customer("user6@gmail.com", "password", "MR", "John", "Crewe", "01275343224", "07955558655", "Address Line 1", "Address Line 2", "Blackpool", "County");
		Customer customer7 = new Customer("user7@gmail.com", "password", "MR", "Chris", "Gale", "01275343224", "07955558655", "Address Line 1", "Address Line 2", "Plymouth", "County");
		Customer customer8 = new Customer("user8@gmail.com", "password", "MR", "Patrick", "Selman", "01275343224", "07955558655", "Address Line 1", "Address Line 2", "Bristol", "County");
		Customer customer9 = new Customer("user9@gmail.com", "password", "MR", "Grant", "Law", "01275343224", "07955558655", "Address Line 1", "Address Line 2", "Bristol", "County");
		
		ArrayList<Customer> customers = new ArrayList<Customer>();
		
		customers.add(customer0);
		customers.add(customer1);
		customers.add(customer2);
		customers.add(customer3);
		customers.add(customer4);
		customers.add(customer5);
		customers.add(customer6);
		customers.add(customer7);
		customers.add(customer8);
		customers.add(customer9);
		
		for (Customer customer : customers)
		{
			System.out.println(customer.toString());
		}
		
		
		Artist artist0 = new Artist("A$AP Rocky");
		Artist artist1 = new Artist("Action Bronson");
		Artist artist2 = new Artist("Ab-Soul");
		Artist artist3 = new Artist("Big Boi");
		Artist artist4 = new Artist("Janelle Monae");
		Artist artist5 = new Artist("Justin Timberlake");
		Artist artist6 = new Artist("SchoolBoy Q");
		Artist artist7 = new Artist("Slum Village");
		Artist artist8 = new Artist("The Game");
		Artist artist9 = new Artist("Bubba Sparxxx");
		
		ArrayList<Artist> artists = new ArrayList<Artist>();
		
		artists.add(artist0);
		artists.add(artist1);
		artists.add(artist2);
		artists.add(artist3);
		artists.add(artist4);
		artists.add(artist5);
		artists.add(artist6);
		artists.add(artist7);
		artists.add(artist8);
		artists.add(artist9);
		
		for (Artist artist : artists)
		{
			System.out.println(artist.toString());
		}
		
		
		Venue venue0 = new Venue("HMV, The Ritz");
		Venue venue1 = new Venue("O2 Academy");
		Venue venue2 = new Venue("The Round House");
		
		ArrayList<Venue> venues = new ArrayList<Venue>();
		
		venues.add(venue0);
		venues.add(venue1);
		venues.add(venue2);
		/*
		Billing bill0 = new Billing();
		bill0.addArtist(artist0);
		Calendar startTime0 = Calendar.getInstance();
		startTime0.set(2013, 06, 12, 22, 00);
		Calendar endTime0 = Calendar.getInstance();
		endTime0.set(2013, 06, 12, 23, 30);
		Event event0 = new Event(bill0, "A$AP Rocky Live @ The Ritz", startTime0, endTime0);
		
		venue0.addEvent(event0);*/
		
	}
}