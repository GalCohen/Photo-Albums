package cs213.photoAlbum.model;

import java.io.IOException;
import java.util.HashMap;

/** 
 * This interface is used to store and fetch User data.
 * The following interface contains method for saving a list of all users with their respective details,
 * a load function to retrieve an Array List of current users,
 * a function to retrieve data for a specific user,
 * a function to add a new User,
 * and a function to delete a User.  
 * This will implement Session Persistence.
 * @author dryu
 *
 */
public interface BackendInterface {

	public static final String storeDir = "data";
	public static final String storeFile = "users";
	
	/**
	 * When this method is called, it will save the data for the application. This includes the list of users, 
	 * their albums, photos, and tags.
	 */
	public void save(HashMap<String, User> userList) throws IOException;
	
	
	/**
	 * When this method is called, it will read the user.data file from the data folder,
	 * and use it populate the list of users and their data. 
	 */
	public HashMap<String, User> load() throws IOException, ClassNotFoundException;
	
	
	
	/**
	 * The getUser method finds a user according to their userID and returns the User object or null if the user cannot be found.
	 */
	public User getUser(String id);
	
	
	
	/**
	 * This method adds takes a User Object and adds it to the list of users. 
	 */
	public void addUser(User user);
	
	
	
	/**
	 * This method deletes a user from the userList (HashMap) using the UserID as the key.
	 */
	public void deleteUser(String id);
	
	
	/**
	 * Returns the hashmap that contains the list of users.
	 */
	public HashMap<String, User> getUserList();
	
	
	/**
	 * Sets the hashmap that contains the list of users to the given hashmap.
	 */
	public void setUserList(HashMap<String, User> userList);

}
