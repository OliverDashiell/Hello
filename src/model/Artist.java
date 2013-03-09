package model;

import java.util.ArrayList;

public class Artist extends PersistantObject {

	ArrayList<String> genres;
	ArrayList<Billing> billings;
	ArrayList<Tour> tours;
	String name;
	String bio;
	//TODO add image string data members

	public Artist(long id, String name, String bio, String genres) {
		super();
		this.id = id;
		this.genres = new ArrayList<String>();
		this.billings = new ArrayList<Billing>();
		this.tours = new ArrayList<Tour>();
		this.name = name;
		this.bio = bio;
		
		if(genres != null) {
			String[] splitGenres = genres.split(",");
			for(int i = 0; i < splitGenres.length; i++) {
				this.genres.add(splitGenres[i]);
			}
		}
	}
	
	public ArrayList<String> getGenres() {
		return genres;
	}

	public void setGenres(ArrayList<String> genres) {
		this.genres = genres;
	}

	public ArrayList<Billing> getBillings() {
		return billings;
	}

	public void setBillings(ArrayList<Billing> billings) {
		this.billings = billings;
	}

	public String getName() 
	{
		return name;
	}

	public void setName(String groupName) 
	{
		this.name = groupName;
	}
	
	public String getBio() {
		return bio;
	}

	public void setBio(String bio) {
		this.bio = bio;
	}
	
    public void addGenre(String genre)
    {
    	this.genres.add(genre);
    }
    
    public void removeGenre(String genre)
    {
    	this.genres.remove(genre);
    }
	
    public void addBilling(Billing billing) {
    	this.billings.add(billing);
    }
    
    public void removeBilling(Billing billing) {
    	this.billings.remove(billing);
    }
    
    public void addTour(Tour tour) {
    	this.tours.add(tour);
    }
    
    public void removeTour(Tour tour) {
    	this.tours.remove(tour);
    }
    
	public String toString()
	{
		return name;
	}
}
