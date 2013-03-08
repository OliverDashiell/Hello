package model;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;


public class Account extends PersistantObject {
    
	private ArrayList<Permission> permissions;
	private String email;
    private String token;		// the 'password' sent to to the server for login authentication
    private String preferences;
    
    public Account()
    {
        email = "";
        token = "";
    }
    
    public Account(String email, String password)
    {
        this.email = email;

        this.token = salt(password, email);
        this.token = hash(this.token);
    }
    
    private static String salt(String message, String salt)
    {
    	String saltedMessage = null;
    	
    	if (message == null || salt == null)
    	{
    		return null;
    	}
    	
    	// remove whitespace
    	salt.replaceAll("\\s", "");
    	// remove non-word characters (e.g. punctuation)
    	salt.replaceAll("\\W", "");
    	
    	// salt the message
    	saltedMessage = message + salt;
    	
    	return saltedMessage;
    }
    
    private static String hash(String message)
    {
    	String hashedMessage = null;
    	
    	if (message == null)
    	{
    		return null;
    	}
    	
    	try
    	{
    		MessageDigest digest = MessageDigest.getInstance("MD5");
    		
    		digest.update(message.getBytes(), 0, message.length());
    		
    		hashedMessage = new BigInteger(1, digest.digest()).toString(16);
    	}
    	catch(NoSuchAlgorithmException e)
    	{
    		e.printStackTrace();
    	}
    	
    	return hashedMessage;
    }

    public String getEmail() 
    {
        return email;
    }

    public String getPassword() 
    {
        return token;
    }

    public void setEmail(String email) 
    {
        this.email = email;
    }

    public void setPassword(String password) 
    {
        this.token = salt(password, this.email);
        this.token = hash(this.token);
    }
    
    public void addPermission(Permission permission)
    {
    	if (this.permissions == null)
    	{
    		this.permissions = new ArrayList<Permission>();
    	}
    	permission.addAccount(this);
    	this.permissions.add(permission);
    }
    
    public void removePermission(Permission permission)
    {
    	permission.removeAccount(this);
    	this.permissions.remove(permission);
    }

	public ArrayList<Permission> getPermission() {
		return permissions;
	}

	public void setPermission(ArrayList<Permission> permission) {
		this.permissions = permission;
	}

	public String getPreferences() {
		return preferences;
	}

	public void setPreferences(String preferences) {
		this.preferences = preferences;
	}
}
