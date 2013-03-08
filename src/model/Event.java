package model;

import java.util.ArrayList;
import java.util.Calendar;

public class Event extends PersistantObject {
	
	ArrayList<Billing> billings;
	String name;
	Calendar startTime;
	Calendar endTime;
	SeatingPlan seatingPlan;
	
	public Event(String name, Calendar startTime, Calendar endTime)
	{
		this.name = name;
		this.startTime = startTime;
		this.endTime = endTime;
	}
	
	public Event(ArrayList<Billing> billings, String name, Calendar startTime, Calendar endTime)
	{
		this.billings = billings;
		this.name = name;
		this.startTime = startTime;
		this.endTime = endTime;
	}

	public ArrayList<Billing> getBillings() 
	{
		return billings;
	}

	public void setBillings(ArrayList<Billing> billings) 
	{
		this.billings = billings;
	}

	public String getName() 
	{
		return name;
	}

	public void setName(String name) 
	{
		this.name = name;
	}

	public Calendar getStartTime() 
	{
		return startTime;
	}

	public void setStartTime(Calendar startTime) 
	{
		this.startTime = startTime;
	}

	public Calendar getEndTime() 
	{
		return endTime;
	}

	public void setEndTime(Calendar endTime) 
	{
		this.endTime = endTime;
	}

	public SeatingPlan getSeatingPlan() {
		return seatingPlan;
	}

	public void setSeatingPlan(SeatingPlan seatingPlan) {
		this.seatingPlan = seatingPlan;
	}
}
