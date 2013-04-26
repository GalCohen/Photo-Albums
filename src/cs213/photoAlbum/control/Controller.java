package cs213.photoAlbum.control;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

import cs213.photoAlbum.GuiView.GuiView;
import cs213.photoAlbum.model.Album;
import cs213.photoAlbum.model.Photo;
import cs213.photoAlbum.model.Tag;
import cs213.photoAlbum.model.User;
import cs213.photoAlbum.util.DateComparator;
import cs213.photoAlbum.util.TagComparator;

/**
 * The Controller class implements the control interface and will handle all algorithmic logic, decision making, and data manipulation processes.  
 * The following methods allow full functionality of the Photo Album application.
 * @author dryu
 */

public class Controller implements Control{
	
	private User currentUser;

	/* command line mode functions */
	/**
	 * Returns the Arraylist of users.
	 */
	public ArrayList<User> listUsers() {
		ArrayList<User> users = new ArrayList<User>(GuiView.backend.getUserList().values());
		return users;
	}
	
	
	/**
	 * Adds a new user with a given userID and name to the list of users.
	 * @returns True if success, false if user already exists.
	 */
	public boolean addUser(String ID, String name) {
		if (!GuiView.backend.getUserList().containsKey(ID)){
			User newUser = new User(ID, name);
			GuiView.backend.addUser(newUser);
			return true;
		}else{
			
			return false;
		}
		
		//return newUser;
	}
	
	/**
	 * 	Deletes a user with a giver string userID from the use list.
	 * @returns True if success, false if user doesn't exist.
	 */
	public boolean deleteUser(String ID) {
		if (GuiView.backend.getUserList().containsKey(ID)){
			GuiView.backend.deleteUser(ID);
			return true;
		}else{
			return false;
		}
		
	}
	
	/**
	 * Sets the global variable currentUser to the given userID if the userID exists in the list of users.
	 * @return True if user is in the list, False otherwise.
	 */
	public boolean login(String userId) {
		if (GuiView.backend.getUserList().containsKey(userId)){
			GuiView.control.setCurrentUser(GuiView.backend.getUserList().get(userId));
			//System.out.println("current user = " + currentUser.getID().toString());
			return true;
		}else{
			GuiView.control.setCurrentUser(null);
			return false;
		}
	}
	
	public void setCurrentUser(User userId) {
		this.currentUser = userId;
	}
	
	public User getCurrentUser() {
		return this.currentUser;
	}
	
	/* interactive mode */
	/**
	 * Creates a new album with the given name and adds it to the Current user's album list.
	 * @return True if the operation is successful, false if the album already exists.
	 */
	public boolean createAlbum(String name) {
		if (!GuiView.control.getCurrentUser().getAlbumList().containsKey(name)) {
			Album newAlbum = new Album(name, currentUser.getFullName(), currentUser.getID());
			GuiView.control.getCurrentUser().addAlbum(newAlbum);
		}
		else {
			return false;
		}
		return true;
	}
	
	
	/**
	 * Deletes an album with the given name from the current user's album list.
	 * @returns True if successfully deleted, false if the user doesn't exist and cannot be deleted.
	 */
	public boolean deleteAlbum(String albumName) {
		if (GuiView.control.getCurrentUser().getAlbumList().containsKey(albumName)) {
			GuiView.control.getCurrentUser().removeAlbum(albumName);
			return true;
		}
		else{
			return false;
		}
	}
	
	
	/**
	 * Returns a list of all the albums that the Current User has. 
	 * @returns ArrayList of users or null if the list is empty.
	 */
	public ArrayList<Album> listAlbums() {
		if (GuiView.control.getCurrentUser().getAlbumList().size() != 0) {
			ArrayList<Album> albums = new ArrayList<Album>(GuiView.control.getCurrentUser().getAlbumList().values());
			return albums;
		}
		return null;
	}
	
	
	/**
	 * Returns a list of photos inside of the album with the given album name.
	 * @return ArrayList of photo objects or null if the album does not exist.
	 */
	public ArrayList<Photo> listPhotos(String albumName) {
		if (GuiView.control.getCurrentUser().getAlbumList().get(albumName) != null){
			ArrayList<Photo> photos = new ArrayList<Photo>(GuiView.control.getCurrentUser().getAlbumList().get(albumName).getPhotoList().values());
			return photos;
		}
		return null;
	}
	
	
	/**
	 * Adds a photo with the given name and caption to the chosen album.
	 * @return 1 = photo was successfully added. 2 = photo already exists in album. 3 = album does not exists
	 */
	@SuppressWarnings("unchecked")
	public int addPhoto(String filename, String caption, String albumName, File pic) {
		Photo p = new Photo(filename, GuiView.control.getCurrentUser().getID(), caption, albumName, pic);
		if (GuiView.control.getCurrentUser().getAlbumList().containsKey(albumName)) {
			
			Iterator it = GuiView.control.getCurrentUser().getAlbumList().entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry<String, Album> pairs = (Map.Entry<String, Album>) it.next();
				if (pairs.getValue().getPhotoList().containsKey(filename)) {
					for (int x = 0; x < pairs.getValue().getPhotoList().get(filename).getAlbums().size(); x++) {
						if (!p.getAlbums().contains(pairs.getValue().getPhotoList().get(filename).getAlbums().get(x)))
							p.addAlbum(pairs.getValue().getPhotoList().get(filename).getAlbums().get(x));
					}
					if (!pairs.getValue().getPhotoList().get(filename).getAlbums().contains(albumName))
						pairs.getValue().getPhotoList().get(filename).getAlbums().add(albumName);
					if (!p.getAlbums().contains(albumName))
						p.addAlbum(albumName);
				}
			}
			
			if (!GuiView.control.getCurrentUser().getAlbumList().get(albumName).getPhotoList().containsKey(filename)) {
				GuiView.control.getCurrentUser().getAlbumList().get(albumName).addPhoto(p);
				return 1; // photo has been successfully added
			}
			else{
				return 2; //photo already exists in album
			}
		}else{
			return 3; // album does not exist
		}
	}
	
	@SuppressWarnings("unchecked")
	/**
	 * Moves a photo with given filename from one album to another.
	 * @return -1 = old album doesn't exist. -2 = new album doesn't exist. -3 = photo doesn't exist in old album. -4 = photo already exists in new album. 1 = succesful move of photo.
	 * 
	 */
	public int movePhoto(String filename, String oldAlbumName, String newAlbumName) {
		Photo p;
		
		if (GuiView.control.getCurrentUser().getAlbumList().containsKey(oldAlbumName) == false) {
			return -1; // old album doesnt exist
		}
		
		if (GuiView.control.getCurrentUser().getAlbumList().containsKey(newAlbumName) == false) {
			return -2; // new album doesnt exist
		}
		
		if (GuiView.control.getCurrentUser().getAlbumList().get(oldAlbumName).getPhotoList().containsKey(filename)) {
			
			if (GuiView.control.getCurrentUser().getAlbumList().get(newAlbumName).getPhotoList().containsKey(filename)){
				return -4; // photo already exists in new album
			}
			
			Iterator it = GuiView.control.getCurrentUser().getAlbumList().entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry<String, Album> pairs = (Map.Entry<String, Album>) it.next();
				if (pairs.getValue().getPhotoList().containsKey(filename)) {
					pairs.getValue().getPhotoList().get(filename).removeAlbum(oldAlbumName);
					if (!pairs.getValue().getPhotoList().get(filename).getAlbums().contains(newAlbumName))
						pairs.getValue().getPhotoList().get(filename).addAlbum(newAlbumName);
				}
			}
			
			p = GuiView.control.getCurrentUser().getAlbumList().get(oldAlbumName).getPhotoList().get(filename);
			GuiView.control.getCurrentUser().getAlbumList().get(newAlbumName).addPhoto(p);
			GuiView.control.getCurrentUser().getAlbumList().get(oldAlbumName).deletePhoto(filename);
			
			
			return 1; //"Moved photo " + filename + ": " + filename + " - From album " + oldAlbumName + " to album " + newAlbumName;
		}
		else {
			return -3; //filename + " does not exist in " + oldAlbumName;
		}
	}
	
	
	/**
	 * Removes a photo from a specified album.
	 * @return True if successful, false if the photo doesn't exist.
	 */
	@SuppressWarnings("unchecked")
	public boolean removePhoto(String filename, String album) {
		if (GuiView.control.getCurrentUser().getAlbumList().get(album).getPhotoList().containsKey(filename)) {
			
			Iterator it = GuiView.control.getCurrentUser().getAlbumList().entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry<String, Album> pairs = (Map.Entry<String, Album>) it.next();
				if (pairs.getValue().getPhotoList().containsKey(filename))
					pairs.getValue().getPhotoList().get(filename).removeAlbum(album);
			}
			
			GuiView.control.getCurrentUser().getAlbumList().get(album).deletePhoto(filename);
			return true;
		}
		else{
			return false;
		}
	}
	
	
	/**
	 * Adds a tag with to a specified photo with a tag type and value.
	 * @return 1 = Successfully added the tag. -1 tag already exists for the file with the tag type and value. -2 = failure of other reasons.
	 */
	@SuppressWarnings("unchecked")
	public int addTag(String filename, String tagType, String tagValue) {
		Tag t = new Tag(tagType, tagValue);
		Iterator it = GuiView.control.getCurrentUser().getAlbumList().entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<String, Album> pairs = (Map.Entry<String, Album>) it.next();
			if (pairs.getValue().getPhotoList().containsKey(filename)) {
				if (!pairs.getValue().getPhotoList().get(filename).getTagList().containsKey(tagValue)) {
					pairs.getValue().getPhotoList().get(filename).addTag(t);
					//return 1;//"Added tag: " + filename + " " + tagType + ":" + tagValue;
				}
				else if (pairs.getValue().getPhotoList().get(filename).getTagList().containsKey(tagValue) && pairs.getValue().getPhotoList().get(filename).getTagList().containsValue(tagType)) {
					return -1;
				}
				else
					continue;
				//else
					//return -1; //"Tag already exists for " + filename + " " + tagType + ":" + tagValue;
			}
		}
		return 1; //"";
	}
	
	
	/**
	 * Deletes a tag with a type and value from a specified photo.
	 * @return 1 = tag deleted successfully. -1 = tag does not exist for the photo with the given tag type and value. -2 = other reasons for failure. 
	 */
	@SuppressWarnings("unchecked")
	public int deleteTag(String filename, String tagType, String tagValue) {
		int count = 0;
		Iterator it = GuiView.control.getCurrentUser().getAlbumList().entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<String, Album> pairs = (Map.Entry<String, Album>) it.next();
			if (pairs.getValue().getPhotoList().containsKey(filename)) {
				if (pairs.getValue().getPhotoList().get(filename).getTagList().containsKey(tagValue)) {
					pairs.getValue().getPhotoList().get(filename).removeTag(tagValue);
					count++;
					//return 1;//"Deleted tag: " + filename + " " + tagType + ":" + tagValue;
				}
				else
					continue;
					//return -1;//"Tag does not exist for " + filename + " " + tagType + ":" + tagValue;
			}
		}
		if (count == 0)
			return -1;
		return 1;//"";
	}
	
	
	/**
	 * list the information for a photo with the given name. Information includes list of albums it is found in, list of tags, and a caption.
	 * The list is sorted by tag type in the order location, people, others, and sorted by tag value within each type) 
	 * @return a photo object or null if it does not exist.
	 */
	@SuppressWarnings("unchecked")
	public Photo listPhotoInfo(String filename) {
		Iterator it = GuiView.control.getCurrentUser().getAlbumList().entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<String, Album> pairs = (Map.Entry<String, Album>) it.next();
			if (pairs.getValue().getPhotoList().containsKey(filename)) {
				Collection<Tag> tags = pairs.getValue().getPhotoList().get(filename).getTagList().values();
				ArrayList<Tag> t = new ArrayList<Tag>(tags);
				TagComparator tc = new TagComparator();
				Collections.sort(t, tc);
				pairs.getValue().getPhotoList().get(filename).setSortedTags(t);
				return pairs.getValue().getPhotoList().get(filename);
			}
		}
		return null;
	}
	
	
	/**
	 * Takes a start date and end date and input and returns a  list sorted in chronological order of all the photos that have a date field between those dates 
	 */
	public ArrayList<Photo> getPhotosByDate(String startDate, String endDate) throws ParseException {
		Date start = new SimpleDateFormat("MM/dd/yyyy-HH:mm:ss").parse(startDate);
		Date end = new SimpleDateFormat("MM/dd/yyyy-HH:mm:ss").parse(endDate);
		ArrayList<Photo> photos = new ArrayList<Photo>();
		Collection<Album> albums = GuiView.control.getCurrentUser().getAlbumList().values();
		ArrayList<Album> a = new ArrayList<Album>(albums);
		for (int x = 0; x < a.size(); x++) {
			Collection<Photo> photoCollection = a.get(x).getPhotoList().values();
			ArrayList<Photo> p = new ArrayList<Photo>(photoCollection);
			for (int y = 0; y < p.size(); y++) {
				if (p.get(y).getDate().after(start) && p.get(y).getDate().before(end))
					photos.add(p.get(y));
			}
		}
		DateComparator dc = new DateComparator();
		Collections.sort(photos, dc);
		return photos;
	}
	
	
	/**
	 * Takes a list of tag values and returns a list of photos that have the specified tag values. The list is sorted in chronological order according to the date of the photos.
	 */
	public ArrayList<Photo> getPhotosByTag(ArrayList<String> tagValues) {
		ArrayList<Photo> photos = new ArrayList<Photo>();
		Collection<Album> albums = GuiView.control.getCurrentUser().getAlbumList().values();
		ArrayList<Album> a = new ArrayList<Album>(albums);
		for (int x = 0; x < a.size(); x++) {
			Collection<Photo> photoCollection = a.get(x).getPhotoList().values();
			ArrayList<Photo> p = new ArrayList<Photo>(photoCollection);
			for (int y = 0; y < p.size(); y++) {
				for (int z = 0; z < tagValues.size(); z++) {
					if (p.get(y).getTagList().containsKey(tagValues.get(z)) && !photos.contains(p.get(y)))
						photos.add(p.get(y));
				}
			}
		}
		DateComparator dc = new DateComparator();
		Collections.sort(photos, dc);
		return photos;
	}
	
	
	/**
	 * 	Attempts to save the user list containing all the information on users, albums, photos and tags, and then exists the program.
	 */
	public void logout() {
		try {
			GuiView.backend.save(GuiView.backend.getUserList());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
		//System.exit(0);
	}
	
	
	/**
	 * Returns the latest (most recent) date of a photo within an album of the given album name.
	 */
	public String getEndDate(Album album) {
		Collection<Photo> photos = album.getPhotoList().values();
		ArrayList<Photo> p = new ArrayList<Photo>(photos);
		DateComparator dc = new DateComparator();
		Collections.sort(p, dc);
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy-HH:mm:ss");
		return df.format(p.get(p.size()-1).getDate());
	}
	
	
	/**
	 * Returns the earliest (oldest) date of a photo within an album of the given album name.
	 */
	public String getStartDate(Album album) {
		Collection<Photo> photos = album.getPhotoList().values();
		ArrayList<Photo> p = new ArrayList<Photo>(photos);
		DateComparator dc = new DateComparator();
		Collections.sort(p, dc);
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy-HH:mm:ss");
		return df.format(p.get(0).getDate());
	}
}