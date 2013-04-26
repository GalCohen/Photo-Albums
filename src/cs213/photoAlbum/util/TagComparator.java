package cs213.photoAlbum.util;

import java.util.Comparator;

import cs213.photoAlbum.model.Tag;


/**
 * A comparator that compares between two tags according to their types and values.
 */
public class TagComparator implements Comparator<Tag>{

	public int compare(Tag t1, Tag t2) {
		if (t1.getType().equals(t2.getType()))
			return t1.getValue().compareTo(t2.getValue());
		return t1.getType().compareTo(t2.getType());
	}

}
