package igpp.util;

import java.io.File;
import java.util.Comparator;

/**
 * A {@link Comparator} for {@link File} objects.
 * Can sort on different attributes of a file and perform
 * both ascending and descending sorts.
 *
 * @author Todd King
 * @version 1.00 2009
 */
public class FileComparator implements Comparator 
{
	public static final int FileName = 0;
	public static final int FileLastModified = 1;
	
	public static final int SortAscending = 0;
	public static final int SortDecending = 1;
	
	static String mVersion = "1.0.0";
	
	int mSortOn = FileName;
	int mDirection = SortAscending;
	
	public FileComparator()
	{
	}
	
	public FileComparator(int sortOn)
	{
		mSortOn = sortOn;
	}
	
	public FileComparator(int sortOn, int direction)
	{
		mSortOn = sortOn;
		mDirection = direction;
	}
	
	public int compare(Object o1, Object o2) {
		File f1 = (File) o1;
		File f2 = (File) o2;
		if(mDirection == SortAscending) {
			switch(mSortOn) {
				case FileName:
			      return (f1.getName().compareTo(f2.getName()));
			   case FileLastModified:
			      return (int) (f1.lastModified() - f2.lastModified());		      	
			}
		} else { // SortDecending
			switch(mSortOn) {
				case FileName:
			      return (f2.getName().compareTo(f1.getName()));
			   case FileLastModified:
			      return (int) (f2.lastModified() - f1.lastModified());		      	
			}
		}
		return 0;	// Same
   }
}
