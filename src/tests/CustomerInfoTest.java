package tests;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Date;

import com.prcse.datamodel.Customer;
import com.prcse.protocol.CustomerInfo;

public class CustomerInfoTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
        Socket socket = null;
        ObjectOutputStream out = null;
        ObjectInputStream in = null;
        int clientId = 0;
        
        // mysql
//        String email = "dashb@me.com";
//        String pass = "password";
        
        // oracle
        String email = "dash@gmail.com";
        String pass = "admin";
        
        CustomerInfo cust = new CustomerInfo();
        int printCount = 0;
        
        try {
        	socket = new Socket("77.99.8.110", 1234);
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
            clientId = ((Integer)in.readObject()).intValue();
            System.out.println("Client id recieved. [" + clientId + "]");
            
            // test login
//			cust = login(out, in, cust, email, pass);
//			printCustomer(cust, printCount);
			
			// check for admin push
//			while(true) {
//				Object o = in.readObject();
//				System.out.println("recieved push.");
//				break;
//			}
            
//			cust = insert(out, in, cust);
//			printCustomer(cust, printCount);
            
            cust = login(out, in, cust, "brt@email.com", "Wijoubo$30585");
			printCustomer(cust, printCount);
            
			// test edit
			cust = edit(out, in, cust);
			printCustomer(cust, printCount);
            
			// test create favourites
//			cust = createFavourites(out, in, cust);
//			printCustomer(cust, printCount);
//            
//			// test edit favourite
//			cust = editFavourites(out, in, cust);
//			printCustomer(cust, printCount);
            
            out.close();
        	in.close();
        	socket.close();
            
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host: 77.99.8.110.");
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to: 77.99.8.110.");
            System.exit(1);
        } catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
        
	}
	
	private static void printCustomer(CustomerInfo cust, int count) throws IOException, ClassNotFoundException {
		
		if(cust.getCustomer() != null && cust.getError() == null) {
		    System.out.println(cust.getCustomer().toString());
		    if(cust.getFavourites() != null) {
		        for(int i=0; i < cust.getFavourites().size(); i++) {
		        	System.out.println(cust.getFavourites().get(i).toString());
		        }
		    }
		}
		else {
			System.out.println("\n" + cust.getError());
		}
	}
	
	private static CustomerInfo login(ObjectOutputStream out, ObjectInputStream in, CustomerInfo cust, String email, String pass) throws IOException, ClassNotFoundException {
		
		cust = new CustomerInfo(email, pass);
		System.out.println("\nLogging in with: " + cust.getEmail() + ", " + cust.getPassword());
		
		out.writeObject(cust);
		cust = (CustomerInfo)in.readObject();
		
		return cust;
	}
	
	private static CustomerInfo insert(ObjectOutputStream out, ObjectInputStream in, CustomerInfo cust) throws IOException, ClassNotFoundException {
		// build registration info
		cust = new CustomerInfo();
		cust.setCustomer(new Customer("brt@email.com",
										"Wijoubo$30585",
										"Mr",
										"Bert",
										"Smith",
										null,
										null,
										"64 Woodland Road",
										null,
										null,
										null,
										"W86 4PJ",
										null,
										null,
										null,
										true));
		
		// create customer in db
		out.writeObject(cust);
		
		return cust;
	}
	
	private static CustomerInfo edit(ObjectOutputStream out, ObjectInputStream in, CustomerInfo cust) throws IOException, ClassNotFoundException {
		
		// edit customer
		cust.getCustomer().getAccount().setEmail("Brt@gmail.com", "Wijoubo$30585");
		cust.getCustomer().setTown("Hanwell");
		cust.getCustomer().setCounty("London");
		cust.getCustomer().setTelephone("07832456782");
		
		// save changes
		out.writeObject(cust);
		
		return cust;
	}
	
	private static CustomerInfo createFavourites(ObjectOutputStream out, ObjectInputStream in, CustomerInfo cust) throws IOException, ClassNotFoundException {
		// add favourites
		cust.addFavourite(cust.getCustomer().getId(), (long)1, (long)0, (long)0, (long)0);
		cust.addFavourite(cust.getCustomer().getId(), (long)0, (long)1, (long)0, (long)0);
		
		// save changes
		out.writeObject(cust);
		
		return cust;
	}
	
	private static CustomerInfo editFavourites(ObjectOutputStream out, ObjectInputStream in, CustomerInfo cust) throws IOException, ClassNotFoundException {
		// change favourites
		cust.getFavourites().remove(1);
		cust.addFavourite(cust.getCustomer().getId(), (long)0, (long)0, (long)1, (long)0);
		
		// save changes
		out.writeObject(cust);
		
		return cust;
	}
}
