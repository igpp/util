package igpp.util;

// import java.util.*;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Utility class to store and use lists of terms.
 *
 * @author Todd King
 * @version 1.00 2006
 */
public class TermList {
	ArrayList<TermDef> mList = new ArrayList<TermDef>();
	
	public TermList()
	{
	}
	
	public void add(String term, String definition)
	{
		TermDef item = new TermDef(term, definition);
		mList.add(item);	
	}
	
	public Iterator iterator()
	{
		return mList.iterator();
	}
	
	public void unselectAll()
	{
		Iterator<TermDef> i = mList.iterator();
		TermDef	item;

		while(i.hasNext()) {
			item = i.next();
			item.mSelected = false;
		}
	}
	
	public void selectItem(String term)
	{
		Iterator<TermDef> i = mList.iterator();
		TermDef	item;
		
		while(i.hasNext()) {
			item = i.next();
			if(item.mTerm.compareTo(term) == 0) item.mSelected = true;
		}
	}
}