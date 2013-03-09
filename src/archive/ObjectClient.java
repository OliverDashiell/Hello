package archive;

import java.io.*;
import java.net.*;
import java.util.Iterator;

import protocol.FrontPage;
import protocol.ListPerson;
import java.util.ArrayList;

import com.prcse.datamodel.Artist;
import com.prcse.datamodel.Billing;


public class ObjectClient {
    public static void main(String[] args) throws IOException {

        Socket echoSocket = null;
        ObjectOutputStream out = null;
        ObjectInputStream in = null;
        int clientId = 0;

        try {
            echoSocket = new Socket("localhost", 1234);
            out = new ObjectOutputStream(echoSocket.getOutputStream());
            in = new ObjectInputStream(echoSocket.getInputStream());
            clientId = ((Integer)in.readObject()).intValue();
            System.out.println("Client id recieved. [" + clientId + "]");
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host: localhost.");
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to: localhost.");
            System.exit(1);
        } catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
	String userInput;

	while ((userInput = stdIn.readLine()) != null) {
		if(echoSocket.isClosed()){
			System.out.println("Server closed.");
			break;
		}
		
		if(userInput.startsWith("list")) {
			out.writeObject(new ListPerson());
			try {
				ListPerson response = (ListPerson)in.readObject();
				if(response.getError() != null) {
					System.out.println(response.getError());
				}
				else {
					for(Iterator i = ((ArrayList)response.getResult()).iterator(); i.hasNext();) {
						System.out.println(i.next());
					}
				}
			} 
			catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if(userInput.startsWith("artists")) {
			out.writeObject(new FrontPage());
			try {
				FrontPage response = (FrontPage)in.readObject();
				if(response.getError() != null) {
					System.out.println(response.getError());
				}
				else {
					for(Iterator i = response.getArtists().iterator(); i.hasNext();) {
						Artist a = (Artist)i.next();
						System.out.println(a.getName() + " [" + a.getId() + "]");
					}
				}
			}
			catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if(userInput.startsWith("show")) {
			out.writeObject(new FrontPage());
			try {
				FrontPage response = (FrontPage)in.readObject();
				if(response.getError() != null) {
					System.out.println(response.getError());
				}
				else {
					for(Iterator i = response.getArtists().iterator(); i.hasNext();) {
						Artist a = (Artist)i.next();
						if(userInput.startsWith("show " + a.getName())) {
							System.out.println(a.getName() + " [" + a.getId() + "]");
							System.out.println("\t" + a.getBio());
							System.out.println("\t" + a.getGenres().toString());
							for(Iterator bs = a.getBillings().iterator(); bs.hasNext();) {
								Billing b = (Billing)bs.next();
								System.out.println("\t" + b.getEvent().getName());
								System.out.println("\t\t" + 
													b.getEvent().getSeatingPlan().getVenue().getName() + 
													" - " + b.getEvent().getSeatingPlan().getName());
							}
						} 
					}
				}
			}
			catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else {
			out.writeObject(new Echo(userInput));
			try {
				System.out.println(in.readObject());
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	out.close();
	in.close();
	stdIn.close();
	echoSocket.close();
    }
}
