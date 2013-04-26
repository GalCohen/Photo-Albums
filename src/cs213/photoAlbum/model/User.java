package cs213.photoAlbum.model;

import java.io.Serializable;
import java.util.HashMap;


/**
 * The User class contains the information relating to a single user. The fields include
 * the user's ID, the user's full name, and the user's albums. 
 * The User class also contains methods for adding, deleting and renaming albums.
 * 
 * @author Gal
 */
public class User implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8890571856837001849L;
	private String ID;
	private String fullName;
	private HashMap<String, Album> albumList;
	
	
	/**
	 * Creates a new user given a userID and a name and instantiates a hashmap of albums for the user.
	 * 
	 * @param id - the userID
	 * @param fname - the user's full name
	 */
	public User(String id, String fname){
		this.ID = id;
		this.fullName = fname;
		albumList = new HashMap<String, Album>();
	}
	
	
	/* set methods */
	/**
	 * set the userID to a given ID.
	 */
	public void setID(String id){
		this.ID = id;
	}
	
	
	/**
	 * set the user's name to a given name.
	 */
	public void setFullName(String fullname){
		this.fullName = fullname;
	}
	
	
	/* get methods */
	/**
	 * returns the userID
	 */
	public String getID(){
		return this.ID;
	}
	
	
	/**
	 * returns the userID
	 */
	public String getFullName(){
		return this.fullName;
	}
	
	
	/**
	 * returns the list of albums of the user.
	 */
	public HashMap<String, Album> getAlbumList() {
		return this.albumList;
	}
	
	
	/* album manipulation methods */
	/**
	 * Adds a new album to the user's list of albums given an Album Object 
	 */
	public void addAlbum(Album album){
		albumList.put(album.getName(), album);
	}
	
	
	/**
	 * removes an album from the user's list of albums given the album's name
	 */
	public void removeAlbum(String name){
		if (albumList.containsKey(name))
			albumList.remove(name);
	}
	
	
	/**
	 * Renames an album from the User's album list to a new album name.
	 * 
	 * @param oldName - the original name of the album
	 * @param newName - the new name you wish the album to have.
	 */
	public void renameAlbum(String oldName, String newName){
		if (albumList.containsKey(oldName))
			albumList.get(oldName).setName(newName);
	}
	
	
}
