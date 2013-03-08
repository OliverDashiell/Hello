package gui;

import java.awt.EventQueue;

import client.PeopleAsyncSource;
import client.ResponseHandler;

import protocol.Request;

public class SwingPeopleAsyncSource extends PeopleAsyncSource {

	public SwingPeopleAsyncSource(String host, int port) {
		super(host, port);
	}
	
	protected void changed(final Object arg1) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				SwingPeopleAsyncSource.this.swingChanged(arg1);
			}
		});
	}

	protected void swingChanged(Object arg1) {
		super.changed(arg1);
	}
	
	protected void handleResponse(final Request response, Integer key) {
		final ResponseHandler handler = (ResponseHandler)this.requestCallbacks.get(key);
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				handler.handleResponse(response);
			}
		});
	}
}
