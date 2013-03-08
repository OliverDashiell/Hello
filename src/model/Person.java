package model;

public class Person extends PersistantObject {
	
	/*   Class Variables   */
	private String name;
	
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
