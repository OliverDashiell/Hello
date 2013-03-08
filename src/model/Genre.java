package model;

import java.util.ArrayList;

	public class Genre extends PersistantObject {

	private ArrayList<Artist> artists;
	private String name;
	
	public Genre(String name)
	{
		this.artists = new ArrayList<Artist>();
		this.name = name;
	}

	public ArrayList<Artist> getAccounts() {
		return artists;
	}
	
	public void addArtist(Artist artist)
	{
		if (artists == null)
		{
			artists = new ArrayList<Artist>();
		}
		
		artists.add(artist);
	}
	
	public void removeArtist(Artist account)
	{
		artists.remove(account);
	}

	public void setArtists(ArrayList<Artist> artists) {
		this.artists = artists;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
