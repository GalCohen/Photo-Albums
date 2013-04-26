package cs213.photoAlbum.model;

import java.io.File;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;

import javax.swing.text.DateFormatter;

/**
 * 
 * The Photo class contains:
 * 	</br>- Unique file name
 * 	</br>- Name of the user photo belongs to
 * 	</br>- Caption
 * 	</br>- Timestamp
 * @author dryu
 * 
 */
public class Photo implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3523110845175594843L;
	private String name;
	private String user;
	private String caption;
	private Calendar cal;
	private Date date;
	private File pic;
	private HashMap<String, Tag> tagList;
	private ArrayList<Tag> sortedTags;
	private ArrayList<String> albums;
	
	
	
	/**
	 * creates a new photo given the photo name, the user name, and the caption.
	 * @param name - name of the photo
	 * @param user - user's name
	 * @param caption - caption for the photo.
	 */
	public Photo(String name, String user, String caption, String albumName, File pic) {
		this.name = name;
		this.user = user;
		this.caption = caption;
		albums = new ArrayList<String>();
		albums.add(albumName);
		cal = new GregorianCalendar();
		cal.set(Calendar.MILLISECOND, 0);
		this.date = cal.getTime();
		this.pic = pic;
		this.tagList = new HashMap<String, Tag>();
	}
	
	/**
	 * set the name of the photo to the given name.
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * return the name of the photo.
	 * @return
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * set the user of the photo to the given User Object.
	 * @param user
	 */
	public void setUser(String user) {
		this.user = user;
	}
	
	/**
	 * return the user Object of the user of the Photo.
	 * @return
	 */
	public String getUser() {
		return this.user;
	}
	
	/**
	 * set the caption for the photo using the given string caption.
	 */
	public void setCaption(String caption) {
		this.caption = caption;
	}
	
	/**
	 * returns the caption of the photo.
	 */
	public String getCaption() {
		return this.caption;
	}
	
	
	/**
	 * Adds an album with a given album name to the list of albums the photo is found in.
	 * @param album
	 */
	public void addAlbum(String album) {
		this.albums.add(album);
	}
	
	
	/**
	 * Removes an album with the given album name from the list of albums the photo is found in.
	 * @param album
	 */
	public void removeAlbum(String album) {
		this.albums.remove(album);
	}
	
	
	/**
	 * Sets the list of albums that the Photo is found in to the given album list.
	 * @param albums
	 */
	public void setAlbums(ArrayList<String> albums) {
		this.albums = albums;
	}
	
	/**
	 * Returns the list of albums that the photo is found in.
	 * @return
	 */
	public ArrayList<String> getAlbums() {
		return this.albums;
	}
	
	
	/**
	 * returns the time stamp of the photo.
	 */
	public Date getDate() {
		return this.date;
	}
	
	public void setPic(File pic) {
		this.pic = pic;
	}
	
	public File getPic() {
		return this.pic;
	}
	
	
	/**
	 * add a tag given a Tag Object t to the taglist of the Photo.
	 * @param t
	 */
	public void addTag(Tag t) {
		tagList.put(t.getValue(), t);
	}
	
	
	/**
	 * removes a tag from the list of tags of the Photo.
	 * @param value
	 */
	public void removeTag(String value) {
		if (tagList.containsKey(value))
			tagList.remove(value);
	}
	
	
	/**
	 * Returns the list of tags of the photo.
	 * @return
	 */
	public HashMap<String, Tag> getTagList() {
		return tagList;
	}
	
	public void setTagList(HashMap<String, Tag> tagList) {
		this.tagList = tagList;
	}
	
	/**
	 * Returns the date in the appropriate string format.
	 */
	public String getDateString(){
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy-HH:mm:ss");
		return df.format(this.date);
	}

	public void setStringDate(String date) {
		SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy-HH:mm:ss");
		try {
			Date d = (Date) df.parse(date);
			this.date = d;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Sets the list of sorted tags to the given list of tags.
	 */
	public void setSortedTags(ArrayList<Tag> t) {
		this.sortedTags = t;
	}
	
	
	/**
	 * returns the list of tags previously sorted by type and value.
	 * @return
	 */
	public ArrayList<Tag> getSortedTags() {
		return this.sortedTags;
	}
}
