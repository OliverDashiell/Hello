package protocol;

import server.PeopleSource;

public interface Request {
	public void handleRequest(PeopleSource dataSource);
	public Object getResult();
	public String getError();
	public boolean shouldBroadcast();
	public void setRequestId(int value);
	public int getRequestId();
	public void setClientId(int value);
	public int getClientId();
}
