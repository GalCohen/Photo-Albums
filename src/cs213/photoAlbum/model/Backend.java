package cs213.photoAlbum.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;


/**
 * The backend class is responsible for storing and fetching User data.
 * It contains method for saving a list of all users with their respective details,
 * a load function to retrieve an Array List of current users,
 * a function to retrieve data for a specific user,
 * a function to add a new User,
 * and a function to delete a User.  
 * This uses the Session Persistence model to determine when to save and load data.
 * 
 * @author dryu, Gal
 *
 */
public class Backend implements BackendInterface, Serializable {
	

	private static final long serialVersionUID = -2359131065060605750L;
	
	/**
	 * A hashmap of userID and their assoociated objects containing the other related fields like albums, photos, user's name etc.
	 */
	//public static HashMap<String, User> userList;
	private HashMap<String, User> userList = new HashMap<String, User>();

	
	/**
	 * This method adds takes a User Object and adds it to the list (HashMap) of users. 
	 */
	public void addUser(User user) {
		userList.put(user.getID(), user);
	}

	
	/**
	 * This method deletes a user from the userList (HashMap) using the UserID as the key.
	 */
	public void deleteUser(String id) {
		if (userList.containsKey(id))
			userList.remove(id);
	}


	/**
	 * The getUser method finds a user according to their userID and returns the User object or null if the user cannot be found.
	 */
	public User getUser(String id) {
		if (userList.containsKey(id))
			return userList.get(id);
		else
			return null;
	}

	
	/**
	 * When this method is called, it will read the user.data file from the data folder,
	 * and use it populate the list of users and their data. 
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 * @throws ClassNotFoundException 
	 */
	@SuppressWarnings("unchecked")
	public HashMap<String, User> load() throws FileNotFoundException, IOException, ClassNotFoundException {
		
		ObjectInputStream ois = new ObjectInputStream(new FileInputStream(this.storeDir + File.separator + this.storeFile));
		return (HashMap<String, User>) ois.readObject();
	}

	
	/**
	 * When this method is called, it will save the data for the application. This includes the list of users, 
	 * their albums, photos, and tags.
	 */
	public void save(HashMap<String, User> userList) throws IOException{
		//System.out.println("SAVED: " + userList.get("dennisr").getID().toString() + " and size = " + userList.size());
		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(this.storeDir + File.separator + this.storeFile));
		oos.writeObject(userList);
	}

	
	/**
	 * Returns the hashmap that contains the list of users.
	 */
	public HashMap<String, User> getUserList() {
		return this.userList;
	}
	
	/**
	 * Sets the hashmap that contains the list of users to the given hashmap.
	 */
	public void setUserList(HashMap<String, User> userList) {
		this.userList = userList;
	}
	
}
