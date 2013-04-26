package cs213.photoAlbum.util;

import java.util.Comparator;

import cs213.photoAlbum.model.Photo;

/**
 * A comparator that compares two photo objects by their date fields.
 */
public class DateComparator implements Comparator<Photo>{

	public int compare(Photo p1, Photo p2) {
		return p1.getDate().compareTo(p2.getDate());
	}

}
