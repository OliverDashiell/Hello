package archive;

import java.io.Serializable;

import com.prcse.protocol.BaseRequest;
import com.prcse.utils.Connectable;

public class Echo extends BaseRequest {
	
	public Echo(String message) {
		this.message = message;
	}

	private String message;

	public void setMessage(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void handleRequest(Connectable dataSource) {
		// Do nothing
	}
	
	public String toString(){
		return "Echo:" + message;
		
	}

	public Object getResult() {
		return message;
	}

	public String getError() {
		return null;
	}

	public boolean shouldBroadcast() {
		// TODO Auto-generated method stub
		return false;
	}
}
