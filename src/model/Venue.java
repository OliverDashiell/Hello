package model;

import java.util.ArrayList;


public class Venue extends PersistantObject {
	
	//ArrayList<SeatingPlan> seatingPlan;
	ArrayList<Event> events;
	String name;
	double geoLat;
	double geoLong;
	//TODO add postcode data member with getters and setters

	public Venue(long id, String name)
	{
		this.events = new ArrayList<Event>();
		this.name = name;
		this.id = id;
		this.geoLat = 0;
		this.geoLong = 0;
	}
	
	public Venue(String name, double geoLat, double geoLong)
	{
		this.events = new ArrayList<Event>();
		this.name = name;
		this.geoLat = geoLat;
		this.geoLong = geoLong;
	}
	
	public void addEvent(Event newEvent)
	{
		if (this.events == null)
		{
			this.events = new ArrayList<Event>();
		}
		this.events.add(newEvent);
	}
	
	public void removeEventAt(int index)
	{
		this.events.remove(index);
	}
	
	public void removeEvent(Event event)
	{
		this.events.remove(event);
	}

	public ArrayList<Event> getEvents() 
	{
		return events;
	}

	public String getName() 
	{
		return name;
	}

	public void setName(String name) 
	{
		this.name = name;
	}

	public double getGeoLat() 
	{
		return geoLat;
	}

	public void setGeoLat(double geoLat) 
	{
		this.geoLat = geoLat;
	}

	public double getGeoLong() 
	{
		return geoLong;
	}

	public void setGeoLong(double geoLong) 
	{
		this.geoLong = geoLong;
	}
}