package model;

/*
 * Dev Note: Billing order (lineup_order) number for each artist in an event need to be exclusive?
 */
public class Billing extends PersistantObject {
	
	Artist artist;
	Event event;
	int lineupOrder;
	
	public Billing(long id, Artist artist, Event event, int lineupOrder)
	{
		this.id = id;
		this.artist = artist;
		this.event = event;
		this.lineupOrder = lineupOrder;
	}
	

	public Artist getArtist() {
		return artist;
	}


	public void setArtist(Artist artist) {
		this.artist = artist;
	}


	public Event getEvent() {
		return event;
	}


	public void setEvent(Event event) {
		this.event = event;
	}


	public int getLineupOrder() {
		return lineupOrder;
	}

	public void setLineupOrder(int lineupOrder) {
		this.lineupOrder = lineupOrder;
	}
	
	//TODO add toString
}
