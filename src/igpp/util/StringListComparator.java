package igpp.util;

import java.io.File;
import java.util.Comparator;
import java.util.ArrayList;

/**
 * A {@link Comparator} for {@link ArrayList} of {@link String} objects.
 * Can sort on specified "fields" in the array list and perform
 * both ascending and descending sorts.
 *
 * @author Todd King
 * @version 1.00 2009
 */
public class StringListComparator implements Comparator 
{
	public static final int SortAscending = 0;
	public static final int SortDecending = 1;
	
	static String mVersion = "1.0.0";
	
	int mSortOn = 0;
	int mDirection = SortAscending;
	
	public StringListComparator()
	{
	}
	
	public StringListComparator(int sortOn)
	{
		mSortOn = sortOn;
	}
	
	public StringListComparator(int sortOn, int direction)
	{
		mSortOn = sortOn;
		mDirection = direction;
	}
	
	public int compare(Object o1, Object o2) {
		ArrayList<String> f1 = (ArrayList<String>) o1;
		ArrayList<String> f2 = (ArrayList<String>) o2;
		
		if(f1.size() < mSortOn) return 0;	// nothing to do, treat as same.
		if(f2.size() < mSortOn) return 0;	// nothing to do, treat as same.
		
		if(mDirection == SortAscending) {
	      return (f1.get(mSortOn).compareTo(f2.get(mSortOn)));
		} else { // SortDecending
	      return (f2.get(mSortOn).compareTo(f1.get(mSortOn)));
		}
   }
}