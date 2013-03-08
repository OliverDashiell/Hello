package server;

import java.util.Observer;

public interface Connectable {
	public abstract void connect() throws Exception;
	
	public abstract void disconnect() throws Exception;

	public abstract boolean isConnected();

	public abstract void addObserver(Observer observer);
}
