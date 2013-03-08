package model;

import java.io.Serializable;

public class Person implements Serializable {
	
	/*   Class Variables   */
	private String name;
	private long id;
	
	/*    Class Methods    */
	// Default Constructor
	public Person() {
		this.name = null;
		this.id = 0;
	}
	
	// Custom constructor takes a name and id of a person
	public Person(String name, long id) {
		this.name = name;
		this.id = id;
	}
	
	// Custom constructor takes a name of a person
	public Person(String name) {
		this.name = name;
		this.id = 0;
	}
	
	// Setter for id variable
	public void setId(long id) {
		this.id = id;
	}
	
	// Getter for id variable
	public long getId() {
		return id;
	}
	
	// Setter for name variable
	public void setName(String name) {
		this.name = name;
	}
	
	// Getter for name variable
	public String getName() {
		return name;
	}
	
	// Override of toString method
	public String toString() {
		return "Person <name=" + name + ", id=" + id + ">";
	}
	
}
