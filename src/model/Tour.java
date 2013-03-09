package model;

import java.util.ArrayList;

/*
 * use decorator pattern?
 */
public class Tour extends PersistantObject {

	ArrayList<Billing> bills;
	String name;
	Artist artist;
	
	public Tour(long id, String name, Artist artist)
	{
		bills = new ArrayList<Billing>();
		this.id = id;
		this.name = name;
		this.artist = artist;
	}
	
	public void addBill(Billing bill)
	{
		this.bills.add(bill);
	}
	
	public void removeBillAt(int index)
	{
		this.bills.remove(index);
	}
	
	public void removeBill(Billing bill)
	{
		this.bills.remove(bill);
	}
	
	public ArrayList<Billing> getBills() 
	{
		return bills;
	}
	
	public String getName() 
	{
		return name;
	}
	
	public void setName(String name) 
	{
		this.name = name;
	}
}
