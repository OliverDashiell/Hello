package tests;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import com.prcse.protocol.CustomerForm;

public class EnumTest {
	public static void main(String[] args) {
        Socket socket = null;
        ObjectOutputStream out = null;
        ObjectInputStream in = null;
        int clientId = 0;
        
        try {
        	socket = new Socket("77.99.8.110", 1234);
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
            clientId = ((Integer)in.readObject()).intValue();
            System.out.println("Client id recieved. [" + clientId + "]");
            
            CustomerForm enums = new CustomerForm();
            
            out.writeObject(enums);
            enums = (CustomerForm)in.readObject();
            
            System.out.println(enums.toString());
            
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
}
