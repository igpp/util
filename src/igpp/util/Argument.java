package igpp.util;

// import java.util.*;
import java.util.ArrayList;

/**
 * Argument is a class that contains methods for parsing options
 * and finding values within those options. It includes support for
 * parsing strings into binary values.
 *
 * @author      Todd King
 * @author      Planetary Data System
 * @version     1.0, 04/21/03
 * @since       1.0
 */
public class Argument {
	/* Nested class */
	static class Item {
		String		mName;
		int			mID;
		
	 	public Item() {
		}
	}
	
	/** List of option names and id*/ 
	ArrayList<Item>	mItemList = new ArrayList<Item>();
	
    /** 
     * Creates an instance.
	 *
     * @since           1.0
     */
 	public Argument() {
 	}
		
    /** 
     * Adds an option definition to the list of known options.
     *
	 * @param name		the name of the option.
	 * @param id		the integer ID of the option.
	 *
	 * @return			the count of the number of items in the list;
	 *
     * @since           1.0
     */
	public int add(String name, int id) {
		Item item = new Item();
		
		item.mName = name;
		item.mID = id;
		
		mItemList.add(item);
		
		return mItemList.size();
	}
	
    /** 
     * Adds an option definition to the list of known options.
     * Automatcially assigns a unique ID number to the added option
     * and returns the selected ID.
     *
	 * @param name		the name of the option.
	 *
	 * @return			the ID assigned to the added option.
	 *
     * @since           1.0
     */
	public int add(String name) {
		int n = mItemList.size();
		n++;
		return add(name, n);
	}
	
    /** 
     * Searches the list of options and returns the ID of the first match found.
     * The search ignores case.
     *
	 * @param name		the name of the option.
	 *
	 * @return			the ID of the first match found. If no match is found
	 *					<code>-1</code> is returned.
	 *
     * @since           1.0
     */
	public int token(String name) {
		Item	item;
		int		i;
		int		len;
		String	partial;
		
		len = name.length();
		for(i = 0; i < mItemList.size(); i++) {
			item = (Item) mItemList.get(i);
			if(item.mName.length() > len) partial = item.mName.substring(0, len);
			else partial = item.mName;
			if(partial.compareToIgnoreCase(name) == 0) return item.mID;
		}
		
		return -1;
	}
	
    /** 
     * Searches a list contain options specified in a KEYWORD=VALUE
     * format and locates the option with a given keyword. If the
     * keyword is found the value is returned. If the keyword in not
     * found a null value is returned.
     *
	 * @param options	the array of options. Each options must be specified in
	 *					the form KEYWORD=VALUE. If an option is specified as just
	 *					KEYWORD, then a value of 1 is assumed.
	 * @param name		the keyword to locate within the list of options.
	 *					Partial matches are permitted.
	 *
	 * @return			the value of the options that matches the given keyword.
	 *					otherwise the default value which is passed.
	 *
     * @since           1.0
     */
	static public String find(String options[], String name) {
		return find(options, name, null, 0);
	}
	
    /** 
     * Searches a list contain options specified in a KEYWORD=VALUE
     * format and locates the option with a given keyword. If the
     * keyword is found the value is returned. If the keyword in not
     * found the value passed as the default is returned.
     *
	 * @param options	the array of options. Each options must be specified in
	 *					the form KEYWORD=VALUE. If an option is specified as just
	 *					KEYWORD, then a value of 1 is assumed.
	 * @param name		the keyword to locate within the list of options.
	 *					Partial matches are permitted.
	 * @param defaultValue		the default value to return if an options with the 
	 * 					the given keyword is not found.
	 *
	 * @return			the value of the options that matches the given keyword.
	 *					otherwise the default value which is passed.
	 *
     * @since           1.0
     */
	static public String find(String options[], String name, String defaultValue) {
		return find(options, name, defaultValue, 0);
	}
	
    /** 
     * Searches a list contain options specified in a KEYWORD=VALUE
     * format and locates the option with a given keyword. If the
     * keyword is found the value is returned. If the keyword in not
     * found the value passed as the default is returned.
     *
	 * @param options	the array of options. Each options must be specified in
	 *					the form KEYWORD=VALUE. If an option is specified as just
	 *					KEYWORD, then a value of 1 is assumed.
	 * @param name		the keyword to locate within the list of options.
	 *					Partial matches are permitted.
	 * @param defaultValue		the default value to return if an options with the 
	 * 					the given keyword is not found.
	 * @param start		the index of the element within options to begin the search.
	 *
	 * @return			the value of the options that matches the given keyword.
	 *					otherwise the default value which is passed.
	 *
     * @since           1.0
     */
	static public String find(String options[], String name, String defaultValue, int start) {
		int		i, n;
		int		cnt = options.length;
		String	buffer;
		String	keyword;
		String	value;
		String	temp;
		
		for(i = start; i < cnt; i++) {
			buffer = options[i];
			n = buffer.indexOf('=');
			if(n == -1) {
				keyword = buffer;
				value = "1";
			} else {
				keyword = buffer.substring(0, n);
				value = buffer.substring(n+1);
			}
			// Truncate name to match keyword length, then compare
			if(name.length() > keyword.length()) temp = name.substring(0, keyword.length());
			else temp = name;
			if(keyword.compareToIgnoreCase(temp) == 0) return value;
		}
		
		return defaultValue;
	}
}