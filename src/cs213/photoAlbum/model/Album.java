package cs213.photoAlbum.model;

import java.io.Serializable;
import java.util.HashMap;

/**
 * 
 * The Album class contains:
 * 	<br> - Unique album name
 * 	<br> - The name of the user the album belongs to
 *  <br> - An Array List containing photos
 * @author dryu
 *
 */

public class Album implements Serializable  {

	 
	private static final long serialVersionUID = 932748968830155235L;
	private String name;
	private String username;
	private String userId;
	private HashMap<String, Photo> photos;
	
	
	/**
	 * Creates a new Album given the album name, the album's user, and the album's userID
	 * @param name
	 * @param username
	 * @param userId
	 */
	public Album(String name, String username, String userId) {
		this.name = name;
		this.username = username;
		this.userId = userId;
		photos = new HashMap<String, Photo>();
	}
	
	
	/**
	 * set the name of the album to the given name.
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	
	/**
	 * returns the name of the album.
	 * @return
	 */
	public String getName() {
		return this.name;
	}
	
	
	/**
	 * set the album's username and userID to the given values.
	 * @param username
	 * @param userId
	 */
	public void setUser(String username, String userId) {
		this.username = username;
		this.userId = userId;
	}
	
	
	/**
	 * returns the album's user's name.
	 * @return
	 */
	public String getUserName() {
		return this.username;
	}
	
	
	/**
	 * returns the userID of the album's user.
	 * @return
	 */
	public String getUserId() {
		return this.userId;
	}
	
	
	/**
	 * returns the list of photos of the album.
	 * @return
	 */
	public HashMap<String, Photo> getPhotoList() {
		return photos;
	}
	
	public void setPhotoList(HashMap<String, Photo> photos) {
		this.photos = photos;
	}
	
	
	/**
	 * adds a photo object P to the album.
	 * @param p
	 */
	public void addPhoto(Photo p) {
		photos.put(p.getName(), p);
	}
	
	
	/**
	 * deletes a photo from the album given the photo's name.
	 * @param name
	 */
	public void deletePhoto(String name) {
		if (photos.containsKey(name))
			photos.remove(name);
	}

}
