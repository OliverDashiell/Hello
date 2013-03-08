package protocol;

import java.io.Serializable;

public abstract class BaseRequest implements Request, Serializable {

	private int requestId;
	private int clientId;
	protected String error;

	public void setRequestId(int value) {
		this.requestId = value;
	}

	public int getRequestId() {
		return this.requestId;
	}

	public void setClientId(int value) {
		this.clientId = value;
	}

	public int getClientId() {
		return clientId;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}
}
