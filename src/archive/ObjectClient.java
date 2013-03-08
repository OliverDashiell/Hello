package archive;

import java.io.*;
import java.net.*;
import java.util.Iterator;

import protocol.ListPerson;
import java.util.ArrayList;

public class ObjectClient {
    public static void main(String[] args) throws IOException {

        Socket echoSocket = null;
        ObjectOutputStream out = null;
        ObjectInputStream in = null;

        try {
            echoSocket = new Socket("localhost", 1234);
            out = new ObjectOutputStream(echoSocket.getOutputStream());
            in = new ObjectInputStream(echoSocket.getInputStream());
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host: localhost.");
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to: localhost.");
            System.exit(1);
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
