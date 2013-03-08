package model;

import java.util.ArrayList;
import java.util.Date;

/*
 * DEV NOTES: It may be better to use a decorator for customer
 */
public class Customer extends PersistantObject {
    
    private Account account;
	private ArrayList<Booking> bookings = null;
    private String title;
    private String forename;
    private String surname;
    private String telephone;
    private String mobile;
    private String addr1;
    private String addr2;
    private String town;
    private String county;
    private Date created;
    
    public Customer()
    {
        super();
        this.title = null;
        this.forename = "";
        this.surname = "";
        this.telephone = "";
        this.mobile = "";
        this.addr1 = "";
        this.addr2 = "";
        this.town = "";
        this.county = "";
        this.created = null;
    }

    public Customer(String email, String password, String title, String forename, String surname, String telephone, String mobile, String addr1, String addr2, String town, String county) 
    {
        this.account = new Account(email, password);
        this.title = title;
    	this.forename = forename;
        this.surname = surname;
        this.telephone = telephone;
        this.mobile = mobile;
        this.addr1 = addr1;
        this.addr2 = addr2;
        this.town = town;
        this.county = county;
        this.bookings = new ArrayList<Booking>();
        this.created = new Date();
    }
    
    @Override
    public String toString()
    {
    	return 		"\n username: " + this.account.getEmail()
    			+	"\n password: " + this.account.getPassword()
    			+	"\n forename: " + this.getForename()
    			+	"\n surname: " 	+ this.getSurname();
    }
    
    public ArrayList<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(ArrayList<Booking> bookings) 
    {
        this.bookings = bookings;
    }
    
    
    public Account getAccount() {
		return account;
	}

	public String getFullName()
    {
    	return title + " " + forename + " " + surname;
    }

    public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getForename() 
    {
        return forename;
    }

    public void setForename(String forename) 
    {
        this.forename = forename;
    }

    public String getSurname() 
    {
        return surname;
    }

    public void setSurname(String surname) 
    {
        this.surname = surname;
    }

    public String getTelephone() 
    {
        return telephone;
    }

    public void setTelephone(String telephone) 
    {
        this.telephone = telephone;
    }

    public String getMobile() 
    {
        return mobile;
    }

    public void setMobile(String mobile) 
    {
        this.mobile = mobile;
    }

    public String getAddr1() 
    {
        return addr1;
    }

    public void setAddr1(String addr1) 
    {
        this.addr1 = addr1;
    }

    public String getAddr2() 
    {
        return addr2;
    }

    public void setAddr2(String addr2) 
    {
        this.addr2 = addr2;
    }

    public String getTown() 
    {
        return town;
    }

    public void setTown(String town) 
    {
        this.town = town;
    }

    public String getCounty() 
    {
        return county;
    }

    public void setCounty(String county) 
    {
        this.county = county;
    }
    

    public Date getCreated() {
		return created;
	}
}
