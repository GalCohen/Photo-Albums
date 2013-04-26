package cs213.photoAlbum.control;

import java.io.File;
import java.text.ParseException;
import java.util.ArrayList;

import cs213.photoAlbum.model.Album;
import cs213.photoAlbum.model.Photo;
import cs213.photoAlbum.model.User;

/**
 * 
 * The Control interface will handle all algorithmic logic, decision making, and data manipulation processes.  
 * The following methods allow full functionality of the Photo Album application.
 * @author dryu
 *
 */

public interface Control {

	User currentUser = null;
	
	/* command line mode functions */
	/**
	 * Returns the Arraylist of users.
	 */
	public ArrayList<User> listUsers();
	
	
	/**
	 * Adds a new user with a given userID and name to the list of users.
	 * @returns True if success, false if user already exists.
	 */
	public boolean addUser(String ID, String name);
	
	
	/**
	 * 	Deletes a user with a giver string userID from the use list.
	 * @returns True if success, false if user doesn't exist.
	 */
	public boolean deleteUser(String ID);
	
	
	/**
	 * Sets the global variable currentUser to the given userID if the userID exists in the list of users.
	 * @return True if user is in the list, False otherwise.
	 */
	public boolean login(String userId);
	
	public void setCurrentUser(User userId);
	
	public User getCurrentUser();
	
	/* interactive mode */
	
	/**
	 * Creates a new album with the given name and adds it to the Current user's album list.
	 * @return True if the operation is successful, false if the album already exists.
	 */
	public boolean createAlbum(String name);
	
	
	/**
	 * Deletes an album with the given name from the current user's album list.
	 * @returns True if successfully deleted, false if the user doesn't exist and cannot be deleted.
	 */
	public boolean deleteAlbum(String ID);
	
	
	/**
	 * Returns a list of all the albums that the Current User has. 
	 * @returns ArrayList of users or null if the list is empty.
	 */
	public ArrayList<Album> listAlbums();
	
	
	/**
	 * Returns a list of photos inside of the album with the given album name.
	 * @return ArrayList of photo objects or null if the album does not exist.
	 */
	public ArrayList<Photo> listPhotos(String albumName);
	
	
	/**
	 * Adds a photo with the given name and caption to the chosen album.
	 * @return 1 = photo was successfully added. 2 = photo already exists in album. 3 = album does not exists
	 */
	public int addPhoto(String filename, String caption, String albumName, File pic);
	
	
	/**
	 * Moves a photo with given filename from one album to another.
	 * @return -1 = old album doesn't exist. -2 = new album doesn't exist. -3 = photo doesn't exist in old album. -4 = photo already exists in new album. 1 = succesful move of photo.
	 * 
	 */
	public int movePhoto(String filename, String oldAlbumName, String newAlbumName);
	
	
	/**
	 * Removes a photo from a specified album.
	 * @return True if successful, false if the photo doesn't exist.
	 */
	public boolean removePhoto(String name, String album);
	
	
	/**
	 * Adds a tag with to a specified photo with a tag type and value.
	 * @return 1 = Successfully added the tag. -1 tag already exists for the file with the tag type and value. -2 = failure of other reasons.
	 */
	public int addTag(String filename, String tagType, String tagValue);
	
	
	/**
	 * Deletes a tag with a type and value from a specified photo.
	 * @return 1 = tag deleted successfully. -1 = tag does not exist for the photo with the given tag type and value. -2 = other reasons for failure. 
	 */
	public int deleteTag(String filename, String tagType, String tagValue);
	
	
	/**
	 * list the information for a photo with the given name. Information includes list of albums it is found in, list of tags, and a caption.
	 * The list is sorted by tag type in the order location, people, others, and sorted by tag value within each type) 
	 * @return a photo object or null if it does not exist.
	 */
	public Photo listPhotoInfo(String photoName);
	
	
	/**
	 * Takes a start date and end date and input and returns a  list sorted in chronological order of all the photos that have a date field between those dates 
	 */
	public ArrayList<Photo> getPhotosByDate(String startDate, String endDate) throws ParseException;
	
	
	/**
	 * Takes a list of tag values and returns a list of photos that have the specified tag values. The list is sorted in chronological order according to the date of the photos.
	 */
	public ArrayList<Photo> getPhotosByTag(ArrayList<String> tagValues);
	
	
	/**
	 * 	Attempts to save the user list containing all the information on users, albums, photos and tags, and then exists the program.
	 */
	public void logout();
	

	/**
	 * Returns the latest (most recent) date of a photo within an album of the given album name.
	 */
	public String getEndDate(Album album);
	
	
	/**
	 * Returns the earliest (oldest) date of a photo within an album of the given album name.
	 */
	public String getStartDate(Album album);

}
