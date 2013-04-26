package cs213.photoAlbum.model;

import java.io.Serializable;

/**
 * 
 * The tag object is used to represent an appearnce of a location or a person or common things in
 * multiple photos.
 * The Tag object has an ID field, a value and type. 
 * The value field is the different value a tag could be used for, such as the name of a location of where the photo was taken
 * or a name of the person tagged in the photo.
 * The type field is the different usages of a tag such as "location" or "person"
 * The tagID field is an ID unique to every tag to prevent duplicates.
 * 
 * @author Gal
 *
 */
public class Tag implements Serializable  {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3223704828835351425L;
	private String tagType;	
	private String value;

	
	
	/**
	 * Creates a new tag given a type of tag and the value for it.
	 * @param type - a type of a tag, such as Location, People, etc.
	 * @param value - the value of the tag, such as New York, or John
	 */
	public Tag(String type, String value) {
		this.tagType = type;
		this.value = value;
	}
	
	
	/* get methods */
	/**
	 * returns the Tag type.
	 * 
	 */
	public String getType(){
		return tagType;
	}
	
	
	/**
	 * returns the Tag value.
	 * 
	 */
	public String getValue(){
		return value;
	}
	
	
	/* set methods */
	/**
	 * set the tag's type to the given string t.
	 */
	public void setType(String t){
		this.tagType = t;
	}
	
	
	/**
	 * set the tag's value to the given string s.
	 */
	public void setValue(String s){
		this.value = s;
	}
}
