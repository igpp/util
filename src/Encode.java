package igpp.util;

import java.io.UnsupportedEncodingException;
import java.util.Date;

import java.text.SimpleDateFormat;
import java.lang.Character;

/**
 * Methods to encode a string for a particular environment. 
 * Supports encoding for SQL, URL and HTML uses.
 *
 * @author Todd King
 * @version 1.00 2006
 */
public class Encode {
	/**
	 * Convert a string with convention wild cards to
	 * SQL equivalents.
	 *
	 * @param term    the text to encode.
	 *
	 * @return the encoded text.
	 **/
	public static String sqlEncode(String term)
	{
		String buffer;

		if(term == null) return "";
		
		buffer = term.replace('*', '%');
		buffer = buffer.replace('?', '_');
		buffer = buffer.replace("'", "\\'");
		buffer = buffer.replace("\"", "\\\"");

		return buffer;
	}

	/**
	 * Convert a string with convention wild cards to
	 * Regular Expression (regex) equivalents.
	 *
	 * @param term    the text to encode.
	 *
	 * @return the encoded text.
	 **/
	public static String regexEncode(String term)
	{
		String buffer;

		if(term == null) return "";
		
		buffer = term.replace("\\.", "\\.");
		buffer = buffer.replace("?", ".");
		buffer = buffer.replace("*", ".*");

		return buffer;
	}

	/**
	 * Convert special characters in a string for use in an URL.
	 *
	 * @param term    the text to encode.
	 *
	 * @return the encoded text.
	 **/
	public static String urlEncode(String term)
	{
		if(term == null) return "";
		
		try {
			return java.net.URLEncoder.encode(term, "UTF-8");
		} catch(Exception e) {
		}
		return term;
	}


	/**
	 * Convert special characters in a string for use in an URL.
	 *
	 * @param term    the text to decode.
	 *
	 * @return the decoded text.
	 **/
	public static String urlDecode(String term)
	{
		if(term == null) return "";
		
		try {
			return java.net.URLDecoder.decode(term, "UTF-8");
		} catch(Exception e) {
		}
		return term;
	}

	/**
	 * Convert special characters in a string for use in an
	 * HTML document.
	 *
	 * @param term    the text to encode.
	 *
	 * @return the encoded text.
	 **/
	public static String htmlEncode(String term)
	{
		String	buffer;
		
		if(term == null) return "";
		
		buffer = term;
		buffer = buffer.replace("&", "&amp;");
		buffer = buffer.replace("\"", "&quot;");
		buffer = buffer.replace("'", "&apos;");
		buffer = buffer.replace("<", "&lt;");
		buffer = buffer.replace(">", "&gt;");
		
		return buffer;
	}
	
	/**
	 * Convert special characters in a string for display as 
	 * HTML document.
	 *
	 * @param term    the text to decode.
	 *
	 * @return the decoded text.
	 **/
	public static String htmlDecode(String term)
	{
		String	buffer;

		if(term == null) return "";
		
		buffer = term;
		buffer = buffer.replace("&quot;", "\"");
		buffer = buffer.replace("&apos;", "'");
		buffer = buffer.replace("&lt;", "<");
		buffer = buffer.replace("&gt;", ">");
		buffer = buffer.replace("&amp;", "&");
		
		return buffer;
	}
}
