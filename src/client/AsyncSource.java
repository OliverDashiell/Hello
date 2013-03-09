package client;

import java.io.BufferedInputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;

import com.prcse.protocol.Request;
import com.prcse.utils.Connectable;



public class AsyncSource extends Observable implements Connectable, Runnable {

	protected HashMap requestCallbacks;
	protected int requestIdSeed;
	protected String host;
	protected int port;
	private Socket socket = null;
	protected ArrayList outputQueue;
	private int clientId = 0;
	private boolean shouldDisconnect;

	public AsyncSource(String host, int port) {
		super();
		requestCallbacks = new HashMap();
		requestIdSeed = 0;
		this.host = host;
		this.port = port;
		outputQueue = new ArrayList();
	}

	public synchronized boolean isShouldDisconnect() {
		return shouldDisconnect;
	}

	public synchronized void setShouldDisconnect(boolean shouldDisconnect) {
		this.shouldDisconnect = shouldDisconnect;
	}

	public int nextRequestId() {
		requestIdSeed ++;
		return requestIdSeed;
	}

	public void connect() throws Exception {
		socket = new Socket(this.host, this.port);
	    this.shouldDisconnect = false;
	    changed(null);
	}

	public void disconnect() throws Exception {
		this.socket.close();
		this.socket = null;
		this.clientId = 0;
		changed(null);
	}

	public boolean isConnected() {
		return this.socket != null;
	}

	protected void changed(Object arg1) {
		setChanged();
		notifyObservers(arg1);
		clearChanged();
	}
	
	protected void changed() {
		this.changed(null);
	}

	private synchronized Request GetNextOutput() {
		Request result = null;
		if(outputQueue.size() > 0){
			result = (Request)outputQueue.get(0);
			outputQueue.remove(0);
			System.out.println("Request removed.");
		}
		return result;
	}

	protected synchronized void addToOutput(Request request) {
		request.setClientId(clientId);
		outputQueue.add(request);
		System.out.println("Request added.");
	}

	public void run() {
		try {
			this.connect();
	
			ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
	    	BufferedInputStream buff = new BufferedInputStream(socket.getInputStream());
			ObjectInputStream input = new ObjectInputStream(buff);
	    	
			this.clientId = ((Integer)input.readObject()).intValue();
			System.out.println("ID recieved. [" + clientId + "]");
	
			while(true) {
				Request request = GetNextOutput();
				// try output request
				if(request != null) {
					output.writeObject(request);
					output.flush();
					System.out.println("Request sent.");
					
					request = null;
				}
				//test input for something to receive
				if(buff.available() > 0) {
					//get request id, callback and invoke later in swing thread
					final Request response = (Request)input.readObject();
					System.out.println("Response received.");
					if(response.getClientId() == this.clientId) {
						Integer key = new Integer(response.getRequestId());
						if(this.requestCallbacks.containsKey(key)) {
							handleResponse(response, key);
						}
					}
					else {
						changed(response);
					}
				}
				if(isShouldDisconnect() == true){
					this.disconnect();
					break;
				}
				Thread.sleep(10);
			}
		} 
		catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	protected void handleResponse(final Request response, Integer key) {
		ResponseHandler handler = (ResponseHandler)this.requestCallbacks.get(key);
		handler.handleResponse(response);
	}

}