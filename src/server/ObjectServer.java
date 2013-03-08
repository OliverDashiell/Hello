package server;


import java.io.EOFException;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Observable;
import java.util.Observer;

import com.mysql.jdbc.jdbc2.optional.MysqlConnectionPoolDataSource;

import protocol.Request;

public class ObjectServer {
	private ServerSocket servSock;
	private MysqlConnectionPoolDataSource dataSource;
	private String url = "jdbc:mysql://localhost:8889/test";
	private final int PORT = 1234;
	private ArrayList handlers;

	public ObjectServer() {
		handlers = new ArrayList();
		
		dataSource = new MysqlConnectionPoolDataSource();
		dataSource.setUrl(url);
		dataSource.setUser("test");
		dataSource.setPassword("test");
	}

	public void run() {
		System.out.println("Opening port...\n");
		try {
			servSock = new ServerSocket(PORT);
		}
		catch(IOException ioEx) {
			System.out.println("Unable to attach to port");
			System.exit(1);
		}
		do {
			handleClient();
		}
		while (true);
		//TODO check rest of while true loop structure
	}

	private void handleClient() {
		try {
			ConnectionHandler handler = new ConnectionHandler(servSock.accept(), dataSource);
			
			addHandler(handler);
			handler.addObserver(new Observer(){
				public void update(Observable arg0, Object arg1) {
					if(arg1 != null){
						broadcastRequest((Request)arg1);
					}
					else {
						removeHandler((ConnectionHandler)arg0);
					}
				}});
			
			Thread t = new Thread(handler);
			t.start();
			System.out.println("Client connected.");
		}
		catch(EOFException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch(IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public synchronized void addHandler(ConnectionHandler handler){
		handlers.add(handler);
	}
	
	public synchronized void removeHandler(ConnectionHandler handler){
		handlers.remove(handler);
		System.out.println("Client dropped.");
	}
	
	public synchronized void broadcastRequest(Request request){
		for(Iterator i = handlers.iterator(); i.hasNext();){
			ConnectionHandler handler = (ConnectionHandler)i.next();
			handler.addBroadcastRequest(request);
		}
	}
	
	public static void main(String[] args) {
		ObjectServer server = new ObjectServer();
		server.run();
	}
}