package model;

/*
 * Dev Note: Billing order (lineup_order) number for each artist in an event need to be exclusive?
 */
public class Billing extends PersistantObject {
	
	Artist billing;
	int lineupOrder;
	
	public Billing(Artist artist)
	{
		this.billing = artist;
	}
	
	public Artist getBilling() {
		return billing;
	}

	public void setBilling(Artist artist) {
		this.billing = artist;
	}

	public int getLineupOrder() {
		return lineupOrder;
	}

	public void setLineupOrder(int lineupOrder) {
		this.lineupOrder = lineupOrder;
	}
	
	//TODO add toString
}
