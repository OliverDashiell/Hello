package model;

import java.util.ArrayList;

public class SeatingArea extends PersistantObject {

	private ArrayList<Booking> booked;
	private String name;
	private int capacity;
	private SeatingArea parent;
	private SeatingPlan plan;
	
	public SeatingArea(String name, int capacity, SeatingArea parent, SeatingPlan plan)
	{
		this.booked = new ArrayList<Booking>();
		this.name = name;
		this.capacity = capacity;
		this.parent = parent;
		this.plan = plan;
	}
	
	public void addBooking(Booking booked)
	{
		this.booked.add(booked);
	}
	
	public void removeBooking(Booking booked)
	{
		this.booked.remove(booked);
	}

	public ArrayList<Booking> getBooked() {
		return booked;
	}

	public void setBooked(ArrayList<Booking> booked) {
		this.booked = booked;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public SeatingArea getParent() {
		return parent;
	}

	public void setParent(SeatingArea parent) {
		this.parent = parent;
	}

	public SeatingPlan getPlan() {
		return plan;
	}

	public void setPlan(SeatingPlan plan) {
		this.plan = plan;
	}
}
