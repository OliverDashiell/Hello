package protocol;

import java.io.Serializable;

public abstract class BaseRequest implements Request, Serializable {

	private int requestId;
	private int clientId;

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
}
