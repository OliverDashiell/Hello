package test;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

import model.Person;

public class Hello {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		System.out.println("hello world");
		
		try {
			
			Class.forName("com.mysql.jdbc.Driver");
			
			String url =
				 "jdbc:mysql://localhost:8889/test";
			
			Connection con =
				 DriverManager.getConnection(
				 url,"test", "test");
			
			insertPerson(con, "name");
			
			Person p = getPerson(con, 2l);
			if(p != null){
				p.setName("Peter");
				updatePerson(con, p);
			}
			
			deletePerson(con, p);
			
			for(Iterator i = listPersons(con).iterator(); i.hasNext();) {
				System.out.println(i.next());
			}
			
			
			con.close();
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void insertPerson(Connection con, String name) throws SQLException {
		
		PreparedStatement stmt = con.prepareStatement("insert into person (name) values (?)");
		stmt.setString(1, name);
		stmt.executeUpdate();
		stmt.close();
		
	}
	
	private static void updatePerson(Connection con, Person person) throws SQLException {
		
		PreparedStatement stmt = con.prepareStatement("update person set name=? where id=?");
		stmt.setString(1, person.getName());
		stmt.setLong(2, person.getId());
		stmt.executeUpdate();
		stmt.close();
		
	}
	
	private static Person getPerson(Connection con, long id) throws SQLException {
		
		Person result = null;
		
		PreparedStatement stmt = con.prepareStatement("select * from person where id=?");
		stmt.setLong(1, id);
		ResultSet rs = stmt.executeQuery();
		if(rs.next()){
			result = new Person(rs.getString("name"), rs.getLong("id"));
		}
		
		rs.close();
		stmt.close();

		return result;
	}
	
	private static void deletePerson(Connection con, Person person) throws SQLException {
		
		PreparedStatement stmt = con.prepareStatement("delete from person where id=?");
		stmt.setLong(1, person.getId());
		stmt.executeUpdate();
		stmt.close();
		
	}

	private static ArrayList listPersons(Connection con) throws SQLException {
		ArrayList result = new ArrayList();
		
		PreparedStatement stmt = con.prepareStatement("select * from person");
		
		ResultSet rs = stmt.executeQuery();
		
		while (rs.next()){
			Person p = new Person(rs.getString("name"), rs.getLong("id"));
			result.add(p);
		}
		
		rs.close();
		stmt.close();
		
		return result;
	}
}
