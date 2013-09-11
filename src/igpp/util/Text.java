package igpp.util;

import java.io.File;

import java.util.ArrayList;

import java.lang.Character;

/**
 * Methods to parse, interpret and manipulate strings.
 *
 * @author Todd King
 * @version 1.00 2006
 */
public class Text {
	
	/**
 	 * Determine if a string is empty or not defined.
 	 *
 	 * @param value     The string to test.
 	 *
 	 * @return   <code>true</code> if the string has zero length or is null.
 	 *           <code>false</code> otherwise.
	 **/
	static public boolean isEmpty(String value) 
	{
		if(value == null) return true;
		if(value.length() == 0) return true;
		
		return false;
	}
	
	/**
 	 * Determine if an ArrayList is empty or not defined.
 	 *
  	 * @param list     a list of values.
 	 *
 	 * @return   <code>true</code> if the list has zero length elements or is null.
 	 *           <code>false</code> otherwise.
    **/
	static public boolean isListEmpty(ArrayList<String> list) 
	{
		if(list == null) return true;
		if(list.size() == 0) return true;
		
		return false;
	}
	
	/**
 	 * Return a string representing the boolean state.
 	 *
 	 * @param state   A boolean set to true or false.
 	 *
 	 * @return      "Yes" for true and "No" for false.
	 **/
	static public String getYesNo(boolean state) 
	{
		if(state) return "Yes";
		
		return "No";
	}
	
	/**
	 * Compares a value to a sample string. If they match the String "checked" is returned.
	 * Case is ignored. Useful for HTML web form generation.
	 *
	 * @param base   The value to compare to the "value" argument.
	 * @param value  The value to test.
	 *
	 * @return    The string "checked" is the values matched.
	 *            Otherwise a blank string.
	 **/
	static public String isChecked(String base, String value)
	{
		if(isSetMatch(base, value)) return "checked";
		
		return "";
	}
	
	/**
	 * Returns the index of the matching item in a list.
	 *
	 * @param value     the value to search for in the list.
	 * @param list      An array of strings to search.
	 *
	 * @return -1 if no match is found.
	 **/
	static public int getToken(String value, String[] list)
	{
		if(value == null) return -1;
		if(list == null) return -1;
		
		for(int i = 0; i < list.length; i++) {
			if(isMatch(value, list[i])) return i;
		}
		
		return -1;
	}
	
	/**
	 * Returns the non-mask portion of a string.
	 * The mask is a simple pattern with a star (*) at the variable
	 * location. Only one star (*) is allowed in the pattern.
	 *
	 *  @param mask the mask pattern to use. A star (*) identifies indicates the desired non-mask portion.
	 *  @param value	the value to mask and extract the value from.
	 *
	 *  @return the non-mask portion of the value, null if no value is found.
	 **/
	static public String getMaskedValue(String mask, String value)
	{
		if(value == null) return null;
		if(mask == null) return null;
		if(mask.indexOf("*") == -1) return null;	// No pattern
		
		String[] part = mask.split("*", 2);

		String	buffer = null;
				
		if(value.startsWith(part[0]) && value.endsWith(part[1])) {	// Get the middle
			buffer = value.substring(0, value.length() - part[1].length()).substring(part[0].length());
		}
		return buffer;
	}
	
	/**
	 * Returns a value if set (non-null) or the default if not set.
	 *
	 * @param value  the preferred value if set.
	 * @param defaultValue   the default value.
	 *
	 * @return value if value is not null, otherwise return the defaultValue.
	 **/
	static public String getValue(String value, String defaultValue)
	{
		if(value == null) return defaultValue;
		
		return value;
	}
	
	/**
	 * Returns a value if set (non-null) or the default if not set.
	 * 
	 * @param value   the text string for a boolean value.
	 * @param defaultValue  the default value.
	 *
	 * @return value if value is not null, otherwise return the defaultValue.
	 **/
	static public boolean getValue(String value, boolean defaultValue)
	{
		if(value == null) return defaultValue;
		
		return isTrue(value);
	}
	
	/**
 	 * Determine if a string is equivalent to a true/false state.
 	 * Supported tokens are y/n, yes/no, true/false, 0/1
 	 *
 	 * @param value   the value to determine its boolean equivalent.
 	 *
 	 * @return  the boolean equivalent or false if value is null.
	 **/
	static public boolean isTrue(String value) 
	{
		return isTrue(value, false);
	}
	
	/**
 	 * Determine is a string is equivalent to a true/false state.
 	 * Supported tokens are y/n, yes/no, true/false, 0/1
 	 *
 	 * @param value   the value to determine its boolean equivalent.
 	 * @param defaultValue   the default value.
 	 *
 	 * @return   the defaultValue if value is null.
	 **/
	static public boolean isTrue(String value, boolean defaultValue) 
	{
		if(value == null) return defaultValue;
		
		if(value.compareToIgnoreCase("Y") == 0) return true;
		if(value.compareToIgnoreCase("YES") == 0) return true;
		if(value.compareToIgnoreCase("TRUE") == 0) return true;
		if(value.compareToIgnoreCase("1") == 0) return true;
		
		if(value.compareToIgnoreCase("N") == 0) return false;
		if(value.compareToIgnoreCase("NO") == 0) return false;
		if(value.compareToIgnoreCase("FALSE") == 0) return false;
		if(value.compareToIgnoreCase("0") == 0) return false;
		
		return false;
	}
	
	/**
 	 * Convert a string to proper case by capitalizing the first letter.
 	 *
 	 * @param value    the string to convert to proper case.
 	 *
 	 * @return the string converted to proper case.
	 **/
	static public String toProperCase(String value) 
	{
		String	buffer;
		
		if(value == null) return value;
		if(value.length() == 0) return value;
		if(value.length() == 1) return value.toUpperCase();
		
		buffer = value.substring(0, 1).toUpperCase() + value.substring(1);
		
		return buffer;
	}
	
	/**
 	 * Convert a string to lower case.
 	 * 
     * This is a static interface to java.lang.String.toLowerCase()
 	 *
 	 * @param value    the string to convert to proper case.
 	 *
 	 * @return the string converted to proper case.
	 **/
	static public String toLowerCase(String value) 
	{
		return value.toLowerCase();
	}
	
	/**
 	 * Convert a string to upper case.
 	 * 
     * This is a static interface to java.lang.String.toUpperCase()
 	 *
 	 * @param value    the string to convert to proper case.
 	 *
 	 * @return the string converted to proper case.
	 **/
	static public String toUpperCase(String value) 
	{
		return value.toUpperCase();
	}
	
	/**
 	 * Convert a string to "improper" case by converting the
 	 * first letter to lower case.
 	 *
 	 * @param value    the string to convert to improper case.
 	 *
 	 * @return the string converted to improper case.
	 **/
	static public String toImproperCase(String value) 
	{
		String	buffer;
		
		if(value == null) return value;
		if(value.length() == 0) return value;
		if(value.length() == 1) return value.toUpperCase();
		
		buffer = value.substring(0, 1).toLowerCase() + value.substring(1);
		
		return buffer;
	}
	
	/**
	 * Pad a number with leading zeros to the count given.
	 *
 	 * @param base    the string containing the value.
 	 * @param max     the maximum length for the return string.
 	 *
 	 * @return the base string with zeros added to the beginning so that its length is equal to "max".
	 **/
	static public String zeroPad(String base, int max)
	{
		while(base.length() < max) base = "0" + base;
		
		return base;
	}
	
	/**
	 * Pad a string with spaces to a fixed width.
	 *
 	 * @param base    the string containing the value.
 	 * @param max     the maximum length for the return string.
 	 *
 	 * @return the base string with spaces added to the beginning so that its length is equal to "max".
	 **/
	static public String pad(String base, int max)
	{
		while(base.length() < max) base += " ";
		
		return base;
	}
	
	/**
	 * Compares one string to another and returns true if they match.
	 * Case is ignored. Both strings must be defined (non-null).
	 *
	 * @param base     the string to compare to the test value.
	 * @param test     the value to compare to the base.
	 *
	 * @return <code>true</code> if the two strings match, otherwise <code>false</code>
	 **/
	static public boolean isSetMatch(String base, String test)
	{
		if(base == null && test == null) return true;	// anything matches
		if(test == null) return false;
		if(base == null) return false;
		
		if(base.compareToIgnoreCase(test) == 0) return true;
		
		return false;
	}
	
	/**
	 * Compares a list of strings to another and returns true if they match.
	 * Case is ignored.  Both arrays must be defined (non-null).
	 *
	 * @param base     the array of strings to compare to the test values.
	 * @param test     the array of values to compare to the base.
	 *
	 * @return <code>true</code> if the two strings match, otherwise <code>false</code>
	 **/
	static public boolean isSetMatch(ArrayList<String> base, ArrayList<String> test)
	{
		if(base == null && test == null) return true;	// Anything matches
		if(test == null) return false;
		if(base == null) return false;
		
		for(String word : base)
		{
			if(!isInList(word, test)) return false;
		}
		return true;
	}
	
	/**
	 * Compares one string to another and returns true if they match.
	 * Case is ignored. <code>null</code> is considered a wild card.
	 *
	 * @param base     the string to compare to the test value.
	 * @param test     the value to compare to the base.
	 *
	 * @return <code>true</code> if the two strings match, otherwise <code>false</code>
	 **/
	static public boolean isMatch(String base, String test)
	{
		if(base == null || test == null) return true;	// anything matches
		
		if(base.compareToIgnoreCase(test) == 0) return true;
		
		return false;
	}
	
	/**
	 * Compares a list of strings to another and returns true if they match.
	 * Case is ignored.  <code>null</code> is considered a wild card.
	 *
	 * @param base     the array of strings to compare to the test values.
	 * @param test     the array of values to compare to the base.
	 *
	 * @return <code>true</code> if the two strings match, otherwise <code>false</code>
	 **/
	static public boolean isMatch(ArrayList<String> base, ArrayList<String> test)
	{
		if(base == null && test == null) return true;	// Anything matches
		
		for(String word : base)
		{
			if(!isInList(word, test)) return false;
		}
		return true;
	}
	
	/**
	 * Parse a comma separated list into an array of Strings, trimming extra white space.
	 *
	 * @param text   the string to parse.
	 *
	 * @return    An array of {@link String} values.
	 **/
	static public String[] parseList(String text)
	{
		String[] list = text.split(",");
		if(list != null) {
			for(int i = 0; i < list.length; i++) {
				list[i] = list[i].trim();
			}
		}
		return list;
	}
	
	/**
	 * Construct a comma separated list of values from an array of Strings.
	 * Values are not quoted.
	 *
	 * @param list     An array of {@link String} values.
	 *
	 * @return    a {@link String} containing a comma separated list of values. 
	 **/
	static public String makeList(String[] list)
	{
		return makeList(list, false);
	}
	
	/**
	 * Construct a comma separated list of values from an array of Strings.
	 * Values can be quoted.
	 *
	 * @param list     An array of {@link String} values.
	 * @param quoted    determined if each value should be enclosed in double quotes (").
	 *
	 * @return    a {@link String} containing a comma separated list of values. 
	 **/
	static public String makeList(String[] list, boolean quoted)
	{
		String	buffer = "";
		String	delim = "";
		String	quote = "";
		
		if(quoted) quote = "\"";
		
		if(list != null) {
			for(int i = 0; i < list.length; i++) {
				buffer += delim + quote + list[i].trim() + quote;
				delim = ",";
			}
		}
		return buffer;
	}
	
	/**
	 * Construct a comma separated list of values from an ArrayList of Strings.
	 * Values are not quoted.
	 *
	 * @param list     An array of {@link String} values.
	 *
	 * @return A string containing a comma separated list of values.
	 **/
	static public String makeList(ArrayList<String> list)
	{
		return makeList(list, false);
	}
	
	/**
	 * Construct a comma separated list of values from an ArrayList of Strings.
	 *
	 * @param list     An array of {@link String} values.
	 *
	 * @return A string containing a comma separated list of values.
	 **/
	static public String makeList(ArrayList<String> list, boolean quoted)
	{
		String	buffer = "";
		String	delim = "";
		String	quote = "";
		
		if(quoted) quote = "\"";
		
		for(int i = 0; i < list.size(); i++) {
			buffer += delim + quote + list.get(i) + quote;
			delim = ",";
		}
		return buffer;
	}
	
	/**
	 * Wrap a string of text so that no line is longer than a given width.
	 * Also prefix each wrapped line with passed text.
	 *
	 * @param text    the text string to word wrap.
	 * @param width   the maximum width for a line of text.
	 * @param prefix  text to add at the beginning of each line.
	 *
	 * @return   a String containing the word wrapped text. Each line is terminated with a new line (\n) character.
	 */
	static public String wordWrap (String text, int width, String prefix)
	{
		int n = 0;
		String buffer = prefix;
		
		for(int i = 0; i < text.length(); i++) {
			if(n > width && Character.isWhitespace(text.charAt(i))) {
				buffer += "\n" + prefix;
				n = 0;
			} else {
				buffer += text.charAt(i);
				n++;
			}
		}
		return buffer; 
	}   

	/**
	 * Create an ArrayList of strings containing unique values.
	 * Optional exclude null strings.
	 *
	 * @param list    the list of values to scan.
	 * @param dropNull   flag indicating whether null values should be dropped.
	 *
	 * @return  an ArrayList containing unique values.
	 **/
	static public ArrayList<String> uniqueList(ArrayList<String> list, boolean dropNull) 
	{
		ArrayList<String> newList = new ArrayList<String>();
		
		for(String text : list) {
			if(text == null && dropNull) continue;
			if( ! isInList(text, newList)) newList.add(text);
		}

		return newList;
	}
	
	/**
	 * Create an ArrayList of strings containing the intersection of two lists.
	 *
	 * @param listA    a list of values to scan.
	 * @param listB    a list of values to scan.
	 *
	 * @return  an ArrayList containing those items in both listA and listB.
	 **/
	static public ArrayList<String> intersection(ArrayList<String> listA, ArrayList<String> listB) 
	{
		ArrayList<String> newList = new ArrayList<String>();
		
		for(String text : listA) {
			if(isInList(text, listB)) newList.add(text);
		}

		return newList;
	}
	

	/**
	 * Create an ArrayList of strings containing the complement of two lists.
	 *
	 * @param listA    a list of values to scan.
	 * @param listB    a list of values to scan.
	 *
	 * @return  an ArrayList containing those items in listA, but not in listB.
	 **/
	static public ArrayList<String> complement(ArrayList<String> listA, ArrayList<String> listB) 
	{
		ArrayList<String> newList = new ArrayList<String>();
		
		for(String text : listA) {
			if( ! isInList(text, listB)) newList.add(text);
		}

		return newList;
	}
	

	/**
	 * Check if an item is in a list 
	 *
	 * @param term   the term (phrase) to search for in the list.
	 * @param list   the ArrayList of Strings to search.
	 *
	 * @return <code>true</code> if the term is in the list, <code>false</code> otherwise.
	 **/
	static public boolean isInList(String term, ArrayList<String> list) 
	{
		for(int i = 0; i < list.size(); i++) {
			if(term.compareToIgnoreCase((String) list.get(i)) == 0) return true;
		}	
		return false;
	}
	
	/**
	 * Check if an item is in a list 
	 *
	 * @param term   the term (phrase) to search for in the list.
	 * @param list   the array of Strings to search.
	 *
	 * @return <code>true</code> if the term is in the list, <code>false</code> otherwise.
	 **/
	static public boolean isInList(String term, String[] list) 
	{
		for(int i = 0; i < list.length; i++) {
			if(term.compareToIgnoreCase((String) list[i]) == 0) return true;
		}	
		return false;
	}
	
	/**
	 * Check if the term starts with any item in the list. 
	 *
	 * @param term   the term (phrase) to search for in the list.
	 * @param list   the array of Strings to search.
	 *
	 * @return <code>true</code> if the term starts with any item in the list, <code>false</code> otherwise.
	 **/
	static public boolean isInPrefixList(String term, String[] list) 
	{
		for(int i = 0; i < list.length; i++) {
			if(term.startsWith(list[i])) return true;
		}	
		return false;
	}
	
	/**
	 * Check if the term starts with any item in the list. 
	 *
	 * @param term   the term (phrase) to search for in the list.
	 * @param list   the array of Strings to search.
	 *
	 * @return <code>true</code> if the term starts with any item in the list, <code>false</code> otherwise.
	 **/
	static public boolean isInPrefixList(String term, ArrayList<String> list) 
	{
		for(String item : list) {
			if(term.startsWith(item)) return true;
		}	
		return false;
	}
	
	/**
	 * Create an ArrayList of all words in an array that match words in a list.
	 *
	 * @param terms  the array of phrases to search for in the list.
	 * @param list   the ArrayList of Strings to search.
	 *
	 * @return the ArrayList of matching items.
	 **/
	static public ArrayList<String> getMatchList(String[] terms, ArrayList<String> list) 
	{
		ArrayList<String> matchList = new ArrayList<String>();
		
		for(int i = 0; i < terms.length; i++) {
			if(isInList(terms[i], list)) matchList.add(terms[i]);
		}	
		return matchList;
	}
	
	/**
	 * Create an ArrayList of all words in a list that match words in a list.
	 *
	 * @param terms  the {@link ArrayList} of phrases to search for in the list.
	 * @param list   the {@link ArrayList} of {@link String}s to search.
	 *
	 * @return the ArrayList of matching items.
	 **/
	static public ArrayList<String> getMatchList(ArrayList<String> terms, ArrayList<String> list) 
	{
		ArrayList<String> matchList = new ArrayList<String>();
		
		for(String term : terms) {
			if(isInList(term, list)) matchList.add(term);
		}	
		return matchList;
	}
	
	/**
	 * Convert an {@link String} array to an array list of strings.
	 *
	 * @param list an array of strings to convert to an {@link ArrayList}
	 *
	 * @return   an {@link ArrayList} of {@link String}s. 
	 **/
	static public ArrayList<String> toArrayList(String[] list) 
	{
		if(list == null) return null;
		
		ArrayList<String> arrayList = new ArrayList<String>();
		for(int i = 0; i < list.length; i++) arrayList.add(list[i]);
		
		return arrayList;
	}
	
	/**
	 * Convert a string with a unitized to a byte count. 
	 * The string format is "#[.#] units". Supported units
	 * are the same as for {@link #toBytes(String bytes, String units)}.
	 *
	 * @param value a string containing a unitized count.
	 *
	 * @return number of bytes. 
	 **/
	static public long toBytes(String value) 
	{
		String	number = "0";
		String	units = "";
		char	c;
		
		for(int i = 0; i < value.length(); i++) {
			c = value.charAt(i);
			if(Character.isDigit(c) || c == '.') {
				number += value.charAt(i);
			} else {
				units = value.substring(i).trim();	// Treat as units	
			}
		}
		return toBytes(number, units);
	}
	
	/**
	 * Convert a string and units to a byte count. Supported units
	 * The string format is "#[.#] units". Supported units are
	 * bytes, B, KB, MB, GB, TB, and PB. Case is ignored. 
	 *
	 * @param bytes a string a count.
	 * @param units a string the units to apply to the count.
	 *
	 * @return number of bytes. 
	 **/
	static public long toBytes(String bytes, String units) 
	{
		double	count = 0.0;
		long	factor = 1;
		
		try {
			count = Double.parseDouble(bytes);
		} catch(Exception e) {
		}
		
		// Determine multiplier
		if(isMatch(units, "bytes")) factor = 1;
		if(isMatch(units, "b")) factor = 1;
		if(isMatch(units, "kb")) factor = 1024;
		if(isMatch(units, "gb")) factor = 1024*1024;
		if(isMatch(units, "tb")) factor = 1024*1024*1024;
		if(isMatch(units, "pb")) factor = 1024*1024*1024*1024;
		
		return (long) (count * factor);
	}
	
	/**
	 * Converts a count of bytes into a concise count string with
	 * units like KB, MB, GB. Rounds up to second decimal place.
	 *
	 * @param bytes  a count.
	 *
	 * @return a formatted string containing the shortest possible value
	 *         followed with the appropriate units.
	 **/
	static public String toUnitizedBytes(long bytes)
	{
		int		i;
		long	kbyte = 1024;
		double	multiple;
		String	buffer;
		ArrayList<String>	units = new ArrayList<String>();
		
		units.add(" KB");
		units.add(" MB");
		units.add(" GB");
		units.add(" TB");
		
		multiple = kbyte;
		for(i = 0; i < units.size(); i++) {
			if(bytes < multiple*kbyte) break;
			multiple *= kbyte;
		}
		
		buffer = Double.toString((int)(((bytes/multiple) * 100) + 0.5) / 100.0) + (String) units.get(i);
		return buffer;
	}

	 
	/**
 	 * Get the base portion of a file name.
 	 * The extension which is the text that follows the last dot (.) and the dot itself
 	 * is removed from the name.
 	 *
 	 * @param fileName the name of the file to parse.
 	 *
 	 * @return  the base name of the file.
	 **/
	static public String getFileBase(String fileName)
	{
		if(fileName == null) return "";
		
		int n = fileName.lastIndexOf('.');
		
		String baseName = fileName;
		if(n != -1) baseName = fileName.substring(0, n);
		
		return baseName;
	}
	 
	/**
 	 * Get the file name portion of a pathname.
 	 *
 	 * @param pathName   the path to a file. Full or partial path is permited.
 	 *
 	 * @return   the filename portion of the pathname.
	 **/
	static public String getFile(String pathName)
	{
		if(pathName == null) return "";
		
		File file = new File(pathName);
		return file.getName();
	}
	 
	/**
 	 * Get the path name portion of a pathname.
 	 *
 	 * @param pathName   the path to a file. Full or partial path is permited.
 	 *
 	 * @return   the path portion of the pathname.
	 **/
	static public String getPath(String pathName)
	{
		if(pathName == null) return "";
		
		File file = new File(pathName);
		return file.getParent();
	}

	/**
 	 * Get the path name portion of a URI. Leaving the tailing delimiter in place.
 	 *
 	 * @param pathName   the path to a file. Full or partial path is permited.
 	 *
 	 * @return   the path portion of the pathname.
	 **/
	static public String getURIPath(String pathName)
	{
		if(pathName == null) return "";
		
		int n = pathName.lastIndexOf('/');
		if(n == -1) n = pathName.lastIndexOf('\\');	// Try in Microsoft mode (backslash).
		if(n != -1) pathName = pathName.substring(0, n + 1);	// Include trailing slash
		
		return pathName;
	}


	/**
 	 * Get the path name portion of a URL.
 	 *
 	 * @param pathName   the path to a file. Full or partial path is permitted.
 	 *
 	 * @return   the path portion of the pathname.
	 **/
	static public String getURLPath(String pathName)
	{
		if(pathName == null) return "";
		
		int n = pathName.lastIndexOf('/');
		if(n == -1) n = pathName.lastIndexOf('\\');	// Try in Microsoft mode (backslash).
		if(n != -1) pathName = pathName.substring(0, n);
		
		return pathName;
	}

	/**
	 * Concatenate two paths ensuring that a file separator exists between each part.
	 *
	 * @param path1   the first part of the path.
	 * @param path2   the second part of the path.
	 * @param separator   the string to place between the first and second part of the path.
	 *
	 * @return   the joining of the first and second part of the path.
	 **/
	static public String concatPath(String path1, String path2, String separator)
	{
		String path = path1;
		if( ! path.endsWith(separator)) path += separator;
		if( path2.startsWith(separator)) path2 = path2.substring(1);
		path += path2;
		
		// Remove any embedded self references (i.e. "/./")
		path = path.replaceAll(separator + "\\." + separator, separator);
		
		return path;
	}

	/**
	 * Concatenate two paths ensuring that a file separator exists between each part.
	 * The file separator (File.separator) for the current operating system is used.
	 *
	 * @param path1   the first part of the path.
	 * @param path2   the second part of the path.
	 *
	 * @return   the joining of the first and second part of the path.
	 **/
	static public String concatPath(String path1, String path2)
	{
		return concatPath(path1, path2, File.separator);
	}

   /**
    * Divide a string on capital letters that follow lowercase letters
    *	
    * @param text    the string to split on capital letters.
    *
    * @return an array of strings containing each word.
    *
    * @since           1.0
    **/
   public static String[] splitMixedCase(String text)
   {
   	boolean isLower = false;
   	int	n = 0;
   	char	c;
   	ArrayList<String> list = new ArrayList<String>();
   	
   	for(int i = 0; i < text.length(); i++) {
   		c = text.charAt(i);
   		if(isLower && Character.isUpperCase(c)) {
   			list.add(text.substring(n, i));
   			n = i;
   		}
   		if(Character.isLowerCase(c)) isLower = true;
   		else isLower = false;
   	}
   	if(n < text.length()) list.add(text.substring(n));
   	
   	return list.toArray(new String[0]);
   }
   
    /** 
     * Converts a string to a int.
     *
	  * @param value		a string representation of a value.
	  *
	  * @return			the converted value.
	  *
     * @since           1.0
     **/
	static public int toInt(String value) {
		return Integer.parseInt(value);
	}
	
    /** 
     * Converts a string to a double.
     *
	  * @param value		a string representation of a value.
	  *
	  * @return			the converted value.
	  *
     * @since           1.0
     **/
	static public double toDouble(String value) {
		return Double.parseDouble(value);
	}

    /** 
     * Replace patterns in a string and return the result.
     *
     * This is a static interface to java.lang.String.replaceAll()
     * 
     * @param regex	regular expression for pattern to find.
     * @param value The value to replace each found pattern.
	 * @param opon		a string to operate on.
	 *
	 * @return			the string with the pattern replaced with value.
	 *
     * @since           1.0
     **/
	static public String replaceAll(String regex, String value, String opon) {
		if(regex == null) return opon;
		if(value == null) return opon;
		if(opon == null) return opon;
		
		return opon.replaceAll(regex, value);
	}
	
    /** 
     * Trim the leading and trailing spaces from a string and return the result.
     *
     * This is a static interface to java.lang.String.trim()
     * 
	 * @param opon		a string to operate on.
	 *
	 * @return			the string with the pattern replaced with value.
	 *
     * @since           1.0
     **/
	static public String trim(String opon) {
		if(opon == null) return opon;
		
		return opon.trim();
	}


}