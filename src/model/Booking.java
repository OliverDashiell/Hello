package model;


import java.util.ArrayList;
import java.util.Date;

/* Dev Notes
 * 
 * created, confirmed, cancelRequest, cancelled, cancledConfirmed
 * 
 */

public class Booking extends PersistantObject {
    
    private ArrayList<SeatingArea> seats;
	private Event event = null;
    private Date created;
    private Date confirmed;
    private Date cancelRequest;
    private Date cancelled;
    private Date cancelConfirmed;

    public Booking(Event event)
    {
    	this.event = event;
    	this.created = new Date();
    	this.confirmed = null;
        this.cancelRequest = null;
        this.cancelled = null;
        this.cancelConfirmed = null;
    }
    
    public Booking(ArrayList<SeatingArea> seats, Event event)
    {
    	this.seats = seats;
    	this.event = event;
    }
    
    public void confirm()
    {
    	this.confirmed = new Date();
    }
    
    public void requestCancelation()
    {
    	this.cancelRequest = new Date();
    }
    
    public void cancel()
    {
    	this.cancelled = new Date();
    }
    
    public void confirmCancelation()
    {
    	this.cancelConfirmed = new Date();
    }
    
    public void addSeat(SeatingArea seat)
    {
    	seat.addBooking(this);
    	this.seats.add(seat);
    }
    
    public void removeSeat(SeatingArea seat)
    {
    	seat.removeBooking(this);
    	this.seats.remove(seat);
    }
    
	public ArrayList<SeatingArea> getSeats() 
	{
		return seats;
	}

	public void setSeats(ArrayList<SeatingArea> seats) 
	{
		this.seats = seats;
	}

	public Event getEvent() 
	{
		return event;
	}

	public void setEvent(Event event) 
	{
		this.event = event;
	}

	public Date getCreated() 
	{
		return created;
	}

	public void setCreated(Date created) 
	{
		this.created = created;
	}

	public Date getConfirmed() 
	{
		return confirmed;
	}

	public void setConfirmed(Date confirmed) 
	{
		this.confirmed = confirmed;
	}

	public Date getCancelRequest() 
	{
		return cancelRequest;
	}

	public void setCancelRequest(Date cancelRequest) 
	{
		this.cancelRequest = cancelRequest;
	}

	public Date getCancelled() 
	{
		return cancelled;
	}

	public void setCancelled(Date cancelled) 
	{
		this.cancelled = cancelled;
	}

	public Date getCancelConfirmed() 
	{
		return cancelConfirmed;
	}

	public void setCancelConfirmed(Date cancelConfirmed) 
	{
		this.cancelConfirmed = cancelConfirmed;
	}
	
	//TODO add toString
}
