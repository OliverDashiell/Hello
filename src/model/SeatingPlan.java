package model;

public class SeatingPlan extends PersistantObject {
	
	private Venue venue;
	private String name;
	
	public SeatingPlan(Venue venue, String name)
	{
		this.venue = venue;
		this.name = name;
	}

	public Venue getVenue() {
		return venue;
	}

	public void setVenue(Venue venue) {
		this.venue = venue;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
