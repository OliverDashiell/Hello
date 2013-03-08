package model;

import java.util.ArrayList;

public class Artist extends PersistantObject {

	ArrayList<Genre> genres;
	String name;
	String bio;
	
	public Artist(String name)
	{
		this.name = name;
	}

	public String getName() 
	{
		return name;
	}

	public void setName(String groupName) 
	{
		this.name = groupName;
	}
	
	public String getBio() {
		return bio;
	}

	public void setBio(String bio) {
		this.bio = bio;
	}
	
    public void addGenre(Genre genre)
    {
    	if (this.genres == null)
    	{
    		this.genres = new ArrayList<Genre>();
    	}
    	genre.addArtist(this);
    	this.genres.add(genre);
    }
    
    public void removeGenre(Genre genre)
    {
    	genre.removeArtist(this);
    	this.genres.remove(genre);
    }
	
	public String toString()
	{
		return name;
	}
}
