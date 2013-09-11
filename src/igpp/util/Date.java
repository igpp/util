package igpp.util;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import java.text.SimpleDateFormat;
import java.text.NumberFormat;

/**
 * Methods to manipulate dates.
 *
 * @author Todd King
 * @version 1.00 2007
 */
public class Date extends java.lang.Object {
	public Date()
	{
	}
	
	static String	mVersion = "1.0.1";
	static private String mDateFormat = "yyyy-MM-dd HH:mm:ss.SSS";
	
 	// Enumeration of possible value type
	/** 1/19/83 11:45:30.234 */	static public final String AMERDATE = "MM/dd/yy HH:mm:ss.SSS";      
	/** 19.1.83 11:45:30.234 */	static public final String EURODATE = "dd-MM-yy HH:mm:ss.SSS";
	/** jan 19, 1983 11:45:30.234 */ static public final String AMER = "MMM dd, yyyy HH:mm:ss.SSS";
	/** 19 jan 1983 11:45:30.234 */	static public final String EURO = "dd MMM yyyy HH:mm:ss.SSS";
	/** 1983 303 11:45:30.234 */ static public final String DOY = "yyyy DD HH:mm:ss.SSS";
	/** 83.1.19 11:45:30.234 */ static public final String JAPANDATE = "yyyy.MM.dd HH:mm:ss.SSS";
	/** 83.19.1 11:45:30.234 */ static public final String NIPPONDATE = "yyyy.dd.MM yyyy HH:mm:ss.SSS";
	/** 83 01 19 00 11 45 30.234 */ static public final String HIGHLOW = "yyyy MM dd HH:mm:ss.SSS";
	/** 83 019 JAN 19 11 45 30.234 */ static public final String ISEEDATE = "yyyy DDD MMM dd HH:mm:ss.SSS";
	/** 1989-JAN-19 11:45:30.234 */	static public final String DFS = "yyyy-MMM-dd HH:mm:ss.SSS";
	/** 1989/01/19 11:45:30.234 */ static public final String ABBRDFS = "yyyy/MM/dd HH:mm:ss.SSS";
	/** 1989-01-19T11:45:30.234 or 1989-019T11:45:30.234 with omissions */	static public final String PDS = "T";
	/** 19890119T114530.234 */ static public final String ISO = "yyyyMMddThhmmss.SSSS";
	/** 758979930.234 */ static public final String BINARY = "B1966";
	/** 19-01-1989 11:45:30.234 */ static public final String CLUSTER = "dd-MM-yyy HH:mm:ss.SSS";
	/** 1989-01-19 11:45:30.234 */ static public final String CONVENTION = "yyyy-MM-dd HH:mm:ss.SSS";
	/** 1989-01-19T11:45:30.234 or 1989-019T11:45:30.234 with omissions  */ static public final String ISO8601 = "T";

	/** 
    * Command-line interface
	 * 
    * @param args    	the arguments passed on the command-line.
    *
    * @since           1.0
    **/
	static public void main(String[] args) 
	{
		Date me = new Date();
		Boolean ceil = false;
		
		System.out.println("Version: " + me.mVersion);
		System.out.println("Usage: " + me.getClass().getName() + " [+|-] ([ISO-8601 String] | [ ISO-8601 Duration])");
		
		try {
			System.out.println("Now: " + me.now());
			for(String arg : args) {
				if(arg.equals("+")) {	// Round up
				  ceil = true;
				} else if(arg.equals("-")) {	// Round down
				  ceil = false;
				} else if(arg.startsWith("P")) {	// Duration
					System.out.println("Duration: " + me.getDateString(me.parseISO8601Duration(arg)) + "; From: " + arg + "(" + translateISO8601Duration(arg) + ")");
				} else {	// Full time string
					System.out.println("Date: " + me.getDateString(me.parseISO8601(arg, ceil)) + "; From: " + arg);
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

    /** 
     * Returns the time format specification that matches the given
     * standard format name. If no match is found the passed string is
     * returned.
 	  * 
	  * @param name		the name of the standard format.
	  *
	  * @return			a string containing the format specification that matches
	  * 					the passed name. If no match is found the passed string
	  *					is returned.
	  *
     **/
	static public String findTimePattern(String name) {
		// Placed in order of most commonly used
		if(name.compareToIgnoreCase("PDS") == 0)		return PDS;
		if(name.compareToIgnoreCase("BINARY") == 0)		return BINARY;
		if(name.compareToIgnoreCase("CLUSTER") == 0)	return CLUSTER;
		if(name.compareToIgnoreCase("DFS") == 0)		return DFS;
		if(name.compareToIgnoreCase("ABBRDFS") == 0)	return ABBRDFS;
		if(name.compareToIgnoreCase("ISO") == 0)		return ISO;
		if(name.compareToIgnoreCase("DOY") == 0)		return DOY;
		if(name.compareToIgnoreCase("AMERDATE") == 0)	return AMERDATE;      
		if(name.compareToIgnoreCase("EURODATE") == 0)	return EURODATE;
		if(name.compareToIgnoreCase("AMER") == 0) 		return AMER;
		if(name.compareToIgnoreCase("EURO") == 0)		return EURO;
		if(name.compareToIgnoreCase("JAPANDATE") == 0)	return JAPANDATE;
		if(name.compareToIgnoreCase("NIPPONDATE") == 0)	return NIPPONDATE;
		if(name.compareToIgnoreCase("HIGHLOW") == 0)	return HIGHLOW;
		if(name.compareToIgnoreCase("ISEEDATE") == 0)	return ISEEDATE;

		return name;	// Return passed string - assume its a time spec.
	}
	
	/**
	 * Create a standard format time string for the current system time.
	 *
	 * @return the current date and time in the format yyyy-MM-dd HH:mm:ss.SSS
	 **/
	static public String now()
	{
		return getDateString(new java.util.Date());
	}
	
	/**
	 * Create a standard format time string for a long (count of seconds).
	 *
	 * @param tick    the number of milliseconds since January 1, 1970, 00:00:00 GMT 
	 *                not to exceed the milliseconds representation for the year 8099.
	 *                A negative number indicates the number of milliseconds before 
	 *                January 1, 1970, 00:00:00 GMT.
	 *
	 * @return the date and time in the standard format (default: yyyy-MM-dd HH:mm:ss.SSS)
	 **/
	static public String getDateString(long tick)
	{
		return getDateString(new java.util.Date(tick));
	}

	/**
	 * Create a standard format time string for a Date
	 *
	 * @param date   a {@link Date} set with the date and time.
	 *
	 * @return the date and time in the standard format (default: yyyy-MM-dd HH:mm:ss.SSS)
	 **/
	static public String getDateString(java.util.Date date)
	{
		return new SimpleDateFormat(mDateFormat).format(date);
	}
	
	/**
	 * Create a standard format time string for a Calendar
	 *
	 * @param cal {@link Calendar} set with the date and time.
	 *
	 * @return the date and time in the standard format (default: yyyy-MM-dd HH:mm:ss.SSS)
	 **/
	static public String getDateString(Calendar cal)
	{
		return new SimpleDateFormat(mDateFormat).format(cal.getTime());
	}

	/**
	 * Create a formated time string for a long (count of seconds).
	 *
	 * @param tick    the number of milliseconds since January 1, 1970, 00:00:00 GMT 
	 *                not to exceed the milliseconds representation for the year 8099.
	 *                A negative number indicates the number of milliseconds before 
	 *                January 1, 1970, 00:00:00 GMT.
	 * @param form    the pattern to format the date string. See {@link SimpleDateFormat}.
	 *
	 * @return the date and time in the format requested
	 **/
	static public String getDateString(long tick, String form)
	{
		return getDateString(new java.util.Date(tick), form);
	}

	/**
	 * Create a formated time string for a Date.
	 *
	 * @param date   a {@link Date} set with the date and time.
	 * @param form    the pattern to format the date string. See {@link SimpleDateFormat}.
	 *
	 * @return the date and time in the format requested
	 **/
	static public String getDateString(java.util.Date date, String form)
	{
		return new SimpleDateFormat(form).format(date);
	}

	/**
	 * Create a formated time string for a Calendar.
	 *
	 * @param cal {@link Calendar} set with the date and time.
	 * @param form    the pattern to format the date string. See {@link SimpleDateFormat}.
	 *
	 * @return the date and time in the format requested
	 **/
	static public String getDateString(Calendar cal, String form)
	{
		return new SimpleDateFormat(form).format(cal.getTime());
	}

	/**
	 * Parse a time string using parseISO8601() and create a formated time string.
	 *
	 * @param date    the date and time string in the ISO8601 format.
	 * @param form    the pattern to format the date string. See {@link SimpleDateFormat}.
	 *
	 * @return the date and time in the format requested
	 **/
	static public String getDateString(String date, String form)
	{
		return getDateString(parseISO8601(date), form);
	}

	/**
	 * Parse a standard format time string into a Calendar object.
	 *
	 * @param date    the date and time string in the format yyyy-MM-dd HH:mm:ss.SSS.
	 *
	 * @return  A {@link Calendar} populated with the date and time.
	 **/
	static public Calendar getCalendar(String date)
	{
		Calendar cal = new GregorianCalendar();
		
		try {
			cal.setTime(new SimpleDateFormat(mDateFormat).parse(date));
		} catch(Exception e) {
			e.printStackTrace();
			// Do nothing
		}
		
		return cal;
	}

	/**
	 * Create a Calendar based on a the number of  milliseconds since the epoch (00:00:00 GMT, January 1, 1970).
	 *
	 * @param tick    count of the number of milliseconds since the epoch (00:00:00 GMT, January 1, 1970).
	 *
	 * @return  A {@link Calendar} populated with the date and time.
	 **/
	static public Calendar getCalendar(long tick)
	{
		Calendar cal = new GregorianCalendar();
		
		cal.setTime(new java.util.Date(tick));
		
		return cal;
	}

	/**
	 * Return a calendar object set to the current time.
	 *
	 * @return  A {@link Calendar} populated with the current date and time.
	 **/
	static public Calendar getNow()
	{
		Calendar	now = new GregorianCalendar();
		
		now.setTime(new java.util.Date());
		
		return now;
	}

	/**
	 * Return a calendar object set to the current time on tomorrow.
	 *
	 * @return  A {@link Calendar} populated with the date and time 24 hours from the present.
	 **/
	static public Calendar getTomorrow()
	{
		Calendar	now = new GregorianCalendar();
		
		now.setTime(new java.util.Date());
		
		now.add(Calendar.DAY_OF_MONTH, 1);
		
		return now;
	}

    /** 
     * Sets the time to the earliest possible time.
	 * 
	 * @return  A {@link Calendar} set the earliest possible time.
	 *
     * @since           1.0
     */
	static public Calendar  getDawn() {
		Calendar calendar = new GregorianCalendar();
		
		calendar.set(Calendar.YEAR, calendar.getMinimum(Calendar.YEAR));
		calendar.set(Calendar.MONTH, calendar.getMinimum(Calendar.MONTH));
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getMinimum(Calendar.DAY_OF_MONTH));
		calendar.set(Calendar.HOUR_OF_DAY, calendar.getMinimum(Calendar.HOUR_OF_DAY));
		calendar.set(Calendar.MINUTE, calendar.getMinimum(Calendar.MINUTE));
		calendar.set(Calendar.SECOND, calendar.getMinimum(Calendar.SECOND));
		
		return calendar;
	}
	
    /** 
     * Sets the time to the latest possible time.
	 * 
	 * @return  A {@link Calendar} set the latest possible time.
	 *
     * @since           1.0
     */
	static public Calendar  getEternity() {
		Calendar calendar = new GregorianCalendar();
		
		calendar.set(Calendar.YEAR, 9000); // Using getMaximum does not work properly
		calendar.set(Calendar.MONTH, calendar.getMaximum(Calendar.MONTH));
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getMaximum(Calendar.DAY_OF_MONTH));
		calendar.set(Calendar.HOUR_OF_DAY, calendar.getMaximum(Calendar.HOUR_OF_DAY));
		calendar.set(Calendar.MINUTE, calendar.getMaximum(Calendar.MINUTE));
		calendar.set(Calendar.SECOND, calendar.getMaximum(Calendar.SECOND));
		
		return calendar;
	}
	
	/**
	 * Given a starting time calculated the elapsed time since and
	 * for as a string.
	 *
	 * @param startTime   a {@link Calendar} containing a starting date and time.
	 *
	 * @return The number of seconds elapsed from the startTime to the present.
	 **/
	static public String elapsed(Calendar startTime)
	{
		Calendar	endTime = getNow();
		
		// Get elapsed time in milliseconds
		long elapsedTime = endTime.getTimeInMillis() - startTime.getTimeInMillis();
		
		// Get elapsed time in seconds
		double elapsedSecs = elapsedTime / 1000.0;
		
		NumberFormat format = NumberFormat.getInstance();
		format.setMaximumFractionDigits(3);
		format.setMinimumFractionDigits(3);
		return format.format(elapsedSecs);
	}

    /** 
     * Returns the number of milliseconds between a time and this time.
	  *
	  * @param startTime   a {@link Calendar} containing a starting date and time.
	  * @param endTime   a {@link Calendar} containing a ending date and time.
     **/
	static public long span(Calendar startTime, Calendar endTime) {
		return (startTime.getTimeInMillis() - endTime.getTimeInMillis());
	}

    /** 
     * Advances the time by a specified number of minutes.
 	  *
 	  * @param time      the {@link Calendar} containing the initial date and time.
	  * @param minutes	the number of minutes to advance the time by.
	  *					The value may include a fractional minute.
     **/
	static public void advance(Calendar time, double minutes) {
		int min = (int) minutes;
		double seconds = (minutes - min) * 60.0;
		int sec = (int) seconds;
		int milli = (int) ((seconds - sec) * 1000);
		
		time.add(Calendar.MINUTE, min);
		time.add(Calendar.SECOND, sec);
		time.add(Calendar.MILLISECOND, milli);
	}

    /** 
     * Compare a time to this instance for ordering.
 	  * 
	  * @param aTime	the {@link Calendar} to compare to the base time.
	  * @param anotherTime	the {@link Calendar} to compare to the base time.
	  *
	  * @return			the value 0 if the passed argument is equal to 
	  *					this instance; a value less than 0 if the this instance
	  *					is before the passed argument; and a value greater than 0 if 
	  *					the this instance is after the passed argument.
	  *
     * @since           1.0
     **/
	static public int compareTo(Calendar aTime, Calendar anotherTime) {
		return aTime.getTime().compareTo(anotherTime.getTime());
	}
	
	/**
	 * Create a standard ISO-8601 format time string for a long (count of seconds).
	 *
	 * @param tick    the number of milliseconds since January 1, 1970, 00:00:00 GMT 
	 *                not to exceed the milliseconds representation for the year 8099.
	 *                A negative number indicates the number of milliseconds before 
	 *                January 1, 1970, 00:00:00 GMT.
	 *
	 * @return the date and time in the standard format (default: yyyy-MM-ddTHH:mm:ss.SSS)
	 **/
	static public String getISO8601DateString(long tick)
	{
		return getISO8601DateString(new java.util.Date(tick));
	}
	
	/**
	 * Create a standard ISO-8601 format time string from a string.
	 *
	 * @param date    the date and time string in any allowed abbreviated ISO8601 format time string.
	 *
	 * @return  A {@link String} with the date and time formatted as an ISO-8601 time string of yyyy-MM-ddTHH:mm:ss.SSSZ.
	 **/
	static public String getISO8601DateString(String date)
	{
		return getISO8601DateString(parseISO8601(date));
	}

	/**
	 * Create a standard ISO-8601 format time string for a Calendar.
	 *
	 * @param cal {@link Calendar} set with the date and time.
	 *
	 * @return  A {@link String} with the date and time formatted as an ISO-8601 time string of yyyy-MM-ddTHH:mm:ss.SSSZ.
	 **/
	static public String getISO8601DateString(Calendar cal)
	{
		String	time = getDateString(cal);
		time = time.replace(" ", "T");	// Separator for date/time.
		time = time += "Z";
		return time;
	}

	/**
	 * Create a standard ISO-8601 format time string for a Date.
	 *
	 * @param date   a {@link Date} set with the date and time.
	 *
	 * @return  A {@link String} with the date and time formatted as an ISO-8601 time string of yyyy-MM-ddTHH:mm:ss.SSSZ.
	 **/
	static public String getISO8601DateString(java.util.Date date)
	{
		String	time = getDateString(date);
		time = time.replace(" ", "T");	// Separator for date/time.
		time = time += "Z";
		return time;
	}
	
	/**
	 * Convert a date string in one format into another format.
	 *
	 * Special format specification of "B" with return the count of seconds since 1966 (Cline Time)
	 * and "T" will return ISO8601 formatted time.
	 *
	 * @param date   a {@link Date} set with the date and time.
	 * @param toFormat  the desired output format.
	 * @param fromFormat   the current format.
	 *
	 * @return  A {@link String} with the date and time formatted as an ISO-8601 time string of yyyy-MM-ddTHH:mm:ss.SSSZ.
	 **/
	static public String convert(String date, String toFormat, String fromFormat)
	{
		Calendar cal = parse(toFormat, date);
		if(cal == null) return null;
		
		if(toFormat.compareTo("B") == 0) {
			double sec = cal.getTimeInMillis()/1000.0;
			sec += 126230400.000;	// Constant offset from 1966 Cline Time epoch to 1970 Java Time epoch.
			return Double.toString(sec);
		}
		if(toFormat.compareTo("T") == 0) return getISO8601DateString(cal);
		
		return getDateString(cal, toFormat);
	}

    /** 
     * Convert a string into a Calendar by intepreting the passed value.
     *
     * The value can have the a standard time value form of YYYY-MM-DD HH:mm:sss
     * or be a relative specification such as "X days". Supported relative units
     * are: second, minute, day, month, year. Units can be singular (day) or plural 
     * (days) and retain the same meaning.
     *
	  * @param value	the text containing the string to parse.
	  *
	  * @return  A {@link Calendar} set the parsed time value, null if unable to parse the string.
	  *
     * @since           1.0
    **/
	 static public Calendar interpret(String value) 
	 {
	 	if(igpp.util.Text.isEmpty(value)) return null;
	 	
	 	if(value.indexOf("-") != -1) return parse(value, CONVENTION);	// Treat as convention format

		// Intrept unitized values
		Calendar time = getNow();
		
		String[] part = value.split(" ", 2);
		if(part.length < 2) return null;	// No units
		int step = 0;
		
		try {
			step = Integer.parseInt(part[0]);
		} catch(Exception e) {	// Bad number format
			return null;
		}
		
		part[1] = part[1].toLowerCase();
		
		if(part[1].startsWith("second")) time.add(Calendar.SECOND, -step);
		if(part[1].startsWith("minute")) time.add(Calendar.MINUTE, -step);
		if(part[1].startsWith("day")) time.add(Calendar.DAY_OF_YEAR, -step);
		if(part[1].startsWith("month")) time.add(Calendar.MONTH, -step);
		if(part[1].startsWith("year")) time.add(Calendar.YEAR, -step);
		
		return time;
	 }
	
    /** 
     * Parses a string into a Date using the given pattern.
     * The pattern can be specified using one of the predefined
     * formats or it can be specified using the syntax used
     * by  {@link SimpleDateFormat}. The following patterns 
     * are have special interpretation:
     *<p>
     *<dt>B</dt><dd>Binary time. Count of seconds from 1966 Jan 1. (aka Cline Time).</dd>
     *<dt>T</dt><dd>ISO8601 time. Format is YYYY-MM-DDTHH:mm:SS.sss</dd>
     *<p>
     * The following values have a special meaning (pattern is ignored):
     *<dt>EOM</dt><dd>End of Mission. Sets the date to the maximum possible.</dd>
     *<dt>LAUNCH</dt><dd>The beginning of time. Sets the date to the absolute minimum.</dd>
	 * 
	 * @param value	the text containing the string to parse.
	 * @param pattern	the text containing the pattern to parse
	 *                  buffer with.
	 *
	 * @return  A {@link Calendar} set the parsed time value, null if unable to parse the string.
	 *
     * @since           1.0
     */
	static public Calendar parse(String value, String pattern) 
	{
		if(value.length() == 0) return null;
		if(value.compareToIgnoreCase("EOM") == 0) return getEternity();
		if(value.compareToIgnoreCase("LAUNCH") == 0) return getDawn();
		
		if(pattern.length() == 0) return null;
		
		try {
			if(pattern.charAt(0) == 'B') {	// Special case - binary seconds from reference year
				Calendar cal = new GregorianCalendar();
				int		year;
				
				if(pattern.length() < 5) year = 1966;	// Cline Time
				else year = Integer.parseInt(pattern.substring(1));
				
				double seconds = Double.parseDouble(value);
				long milli = (long) (seconds * 1000);
				cal.setTimeInMillis(milli);
				cal.add(Calendar.YEAR, year - 1970);
				return cal;
			} else if(pattern.charAt(0) == 'T') {	// ISO 8601 time
				return parseISO8601(value);	   
			} else {	// Parse using pattern
				Calendar cal = new GregorianCalendar();
				SimpleDateFormat parser = new SimpleDateFormat(pattern);	
				parser.setTimeZone(TimeZone.getTimeZone("GMT-0:00"));
				cal.setTime(parser.parse(value));
				int n = cal.get(Calendar.YEAR);
				if(n >= 20 && n < 100) cal.add(Calendar.YEAR, 1900);
				if(n >= 0 && n < 20) cal.add(Calendar.YEAR, 2000);
				return cal;
			}
		} catch(Exception e) {
			return null;
		}
	}
	
	/**
	 * Parses an ISO-8601 formatted duration string.
	 * ISO-8601 durations have the format is P[n]Y[n]M[n]DT[n]H[n]M[n]S or P[n]W 
	 * The duration is subtracted from the current date
	 * to determine a point in time.
	 *
	 * @param duration   the string containing the ISO-8601 duration specfication.
	 *
	 * @return a {@link Calendar} set to the appropriate time.
	 **/
	static public Calendar parseISO8601Duration(String duration)
	{
		int	value = 0;
		char	c;
		boolean	timeUnits = false;	// Date duration
		char units = ' ';
		String valueStr = "";
		Calendar event = getNow();
		
		String buffer = duration.trim();
		if(buffer.length() < 3) return event;
		
		if(buffer.charAt(0) != 'P') return event;
		
		try {
			for(int i = 1; i < buffer.length(); i++) {
				c = buffer.charAt(i);
				if(java.lang.Character.isDigit(c) || c == '.') {
					valueStr += buffer.charAt(i);
				} else {	// Parse value and convert to seconds
				   units = buffer.charAt(i);
					if(units == 'T') { timeUnits = true; valueStr = ""; continue; }
					if(units != 'S') value = Integer.parseInt(valueStr);
					if(timeUnits) {	// Time durations
						switch(units) {
							case 'H':	// Hour
								event.add(Calendar.HOUR, -value);
								break;
							case 'M':	// Minute
								event.add(Calendar.MINUTE, -value);
								break;
							case 'S':	// Seconds
							   String[] part = valueStr.split("\\.");
								event.add(Calendar.SECOND, -Integer.parseInt(part[0]));
								if(part.length > 1) {	// Milliseconds
									int n = part[1].length();
									if(n > 3) n = 3;
									part[1] = part[1].substring(0, n);
									event.add(Calendar.MILLISECOND, -Integer.parseInt(part[1]) * ((3-part[1].length()) * 10));
								}
								break;
						}
					} else {	// Date durations
						switch(units) {
							case 'Y':	// Year
								event.add(Calendar.YEAR, -value);
								break;
							case 'M':	// Month
								event.add(Calendar.MONTH, -value);
								break;
							case 'W':	// Week
								event.add(Calendar.WEEK_OF_YEAR, -value);
								break;
							case 'D':	// Day
								event.add(Calendar.DAY_OF_YEAR, -value);
								break;
						}
					}
					valueStr = "";
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
			// Error
		}
		
		return event;
	}
	
	/**
	 * Parses an ISO-8601 formatted duration string and
	 * describe it in human terms.
	 * ISO-8601 durations have the format is P[n]Y[n]M[n]DT[n]H[n]M[n]S or P[n]W 
	 *
	 * @param duration   the string containing the ISO-8601 duration specfication.
	 *
	 * @return a {@link String} that describes the duration in human terms. 
	 **/
	static public String translateISO8601Duration(String duration)
	{
		char	c;
		boolean	timeUnits = false;	// Date duration
		char	units = ' ';
		String	valueStr = "";
		String	delim = "";
		String	plural = "";
		String	phrase = "";
		
		duration = duration.trim();
		if(duration.length() < 3) return phrase;
		
		if(duration.charAt(0) != 'P') return phrase;	// Not a duration
		
		try {
			for(int i = 1; i < duration.length(); i++) {
				c = duration.charAt(i);
				if(java.lang.Character.isDigit(c) || c == '.') {
					valueStr  += duration.charAt(i);
				} else {	// Parse value and convert to seconds
				   units = duration.charAt(i);
					if(units == 'T') { timeUnits = true; valueStr = ""; continue; }
					if(valueStr.length() == 1 && valueStr.charAt(0) == '1') plural = "";
					else plural = "s";
					if(timeUnits) {	// Time durations
						switch(units) {
							case 'H':	// Hour
								phrase += delim + valueStr + " hour" + plural;
								delim = "; ";
								break;
							case 'M':	// Minute
								phrase += delim + valueStr + " minute" + plural;
								delim = "; ";
								break;
							case 'S':	// Seconds
								phrase += delim + valueStr + " second" + plural;
								delim = "; ";
								break;
						}
					} else {	// Date durations
						switch(units) {
							case 'Y':	// Year
								phrase += delim + valueStr + " year" + plural;
								delim = "; ";
								break;
							case 'M':	// Month
								phrase += delim + valueStr + " month" + plural;
								delim = "; ";
								break;
							case 'W':	// Week
								phrase += delim + valueStr + " week" + plural;
								delim = "; ";
								break;
							case 'D':	// Day
								phrase += delim + valueStr + " day" + plural;
								delim = "; ";
								break;
						}
					}
					valueStr = "";
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
			// Error
		}
		
		return phrase;
	}
	
	/**
	 * Parses an ISO-8601 formatted time string.
	 * ISO-8601 time strings have the format YYYY-MM-DDTHH:MM:SS.sss
	 * The date/time seperator "T" may also be a space.
	 * The date may be abbreviated. Any unpsecfied values
	 * are set to the minimum allowed.
	 * 
	 * @param date   the ISO-8601 time stirng
	 *
	 * @return a {@link Calendar} with set the appropriate date and time.
	 **/
	static public Calendar parseISO8601(String date)
	{
		return parseISO8601(date, false);
	}
	
	/**
	 * Parses an ISO-8601 formatted time string.
	 * ISO-8601 time strings have the format YYYY-MM-DDTHH:MM:SS.sss
	 * The date/time seperator "T" may also be a space.
	 * The date may be abbreviated. Any unpsecfied values
	 * are set to the minimum allowed.
	 * 
	 * @param date   the ISO-8601 time stirng
	 * @param ceil   indicate if maximum value is assumed for non-specified parts.
	 *               If <code>false</code> minimum (floor) value is assumed.
	 *
	 * @return a {@link Calendar} with set the appropriate date and time.
	 **/
	static public Calendar parseISO8601(String date, Boolean ceil)
	{
		Calendar cal = new GregorianCalendar();
		String	buffer;
		String	part[];
		
		cal.clear();
		buffer = date;
		if(buffer.length() == 0) return cal;	// Now
		
		// Remove time zone information
		part = buffer.split("Z", 2);
		buffer = part[0];
		
		try {
			// Year
			part = buffer.split("-", 2);
			cal.set(Calendar.YEAR, Integer.parseInt(part[0]));
			if(part.length == 1) { cal = setLimit(ceil, cal, Calendar.MONTH); return cal; }
			buffer = part[1];
			
			// Month or DOY
			part = buffer.split("-", 2);
			if(part.length == 1) { // DOY
				// Day
				part = buffer.split("[T ]", 2);	// Allow "T" or " "
				cal.set(Calendar.DAY_OF_YEAR, Integer.parseInt(part[0]));
				if(part.length == 1) { cal = setLimit(ceil, cal, Calendar.HOUR_OF_DAY); return cal; }
				buffer = part[1];				
			} else { //Month
				cal.set(Calendar.MONTH, Integer.parseInt(part[0])-1);	// January = 0
				if(part.length == 1) { cal = setLimit(ceil, cal, Calendar.DAY_OF_MONTH); return cal; }
				buffer = part[1];
				
				// Day
				part = buffer.split("[T ]", 2);	// Allow "T" or " "
				cal.set(Calendar.DAY_OF_MONTH, Integer.parseInt(part[0]));
				if(part.length == 1) { cal = setLimit(ceil, cal, Calendar.HOUR_OF_DAY); return cal; }
				buffer = part[1];
			}
			
			// Find offsets if any. Form is hh:mm:ss[+-]hh[mm]
			int offset = 0;
			int sign = 1;
			if(buffer.indexOf("-") != -1) {	// Negative offset
				part = buffer.split("[-]", 2);
				sign = -1;
			} else {	// Assume positive offset
				part = buffer.split("[+]", 2);
			}
			if(part.length > 1) {
				part[1] = part[1].replace(":", "");
				if(part[1].length() > 1) {	// convert hh portion
					offset += 60 * Integer.parseInt(part[1].substring(0, 2));
				}
				if(part[1].length() > 1) {	// convert mm portion
					offset += Integer.parseInt(part[1].substring(2, 4));
				}
				
				offset = sign * offset;
				buffer = part[0];	// hh:mm:ss portion is what remains
			}
			
			// Hour
			part = buffer.split(":", 2);
			cal.set(Calendar.HOUR, Integer.parseInt(part[0]));
			if(part.length == 1) { cal = setLimit(ceil, cal, Calendar.MINUTE); return cal; }
			buffer = part[1];
			
			// Minute
			part = buffer.split(":", 2);
			cal.set(Calendar.MINUTE, Integer.parseInt(part[0]));
			if(part.length == 1) { cal = setLimit(ceil, cal, Calendar.SECOND); return cal; }
			buffer = part[1];
			
			// Seconds
			part = buffer.split("\\.", 2);
			cal.set(Calendar.SECOND, Integer.parseInt(part[0]));
			if(part.length == 1) { cal = setLimit(ceil, cal, Calendar.MILLISECOND); return cal;}
			buffer = part[1];
			
			// Milliseconds
			cal.set(Calendar.MILLISECOND, Integer.parseInt(buffer));
			
			// Add offsets
			cal.add(Calendar.MINUTE, offset);
			
		} catch(Exception e) {
			e.printStackTrace();
			// Do nothing
		}
		
		return cal;
	}
	
	/**
	 * Set the maximum (ceil) or minimum (floor) value for a time field.
	 * The starting field is specified. All fields of lower order are also set.
	 * 
	 * @param ceil   indicate if maximum value is assumed for non-specified parts.
	 *               If <code>false</code> minimum (floor) value is assumed.
	 *        cal    the Calendar with higher order fields already set.
	 *        field  the field to begin setting values.
	 *
	 * @return the Calendar arugment is altered.
	 **/
	static public Calendar setLimit(Boolean ceil, Calendar cal, int field) 
	{
		Calendar calendar = (Calendar) cal.clone();
		
		if(field == Calendar.YEAR) {
			if(ceil) calendar.set(Calendar.YEAR, 9000); // Using getMaximum does not work properly
			else calendar.set(Calendar.YEAR, calendar.getMinimum(Calendar.YEAR));
			field = Calendar.MONTH;
		}
		
		if(field == Calendar.MONTH) {
			if(ceil) calendar.set(Calendar.MONTH, calendar.getMaximum(Calendar.MONTH));
			else calendar.set(Calendar.MONTH, calendar.getMinimum(Calendar.MONTH));
			field = Calendar.DAY_OF_MONTH;
		}
		
		if(field == Calendar.DAY_OF_MONTH) {
			calendar.set(Calendar.DAY_OF_MONTH, 1);
			System.out.println("YEAR: " + calendar.get(Calendar.YEAR));
			System.out.println("MONTH: " + calendar.get(Calendar.MONTH));
			System.out.println("DAY_OF_MONTH: " + calendar.get(Calendar.DAY_OF_MONTH));
			System.out.println("Max Day of month: " + calendar.getMaximum(Calendar.DATE));
			System.out.println("Days in month: " + getDaysInMonth(calendar));
			
			if(ceil) calendar.set(Calendar.DAY_OF_MONTH, getDaysInMonth(calendar));
			else calendar.set(Calendar.DAY_OF_MONTH, calendar.getMinimum(Calendar.DAY_OF_MONTH));
			field = Calendar.HOUR_OF_DAY;
		}
		
		if(field == Calendar.HOUR_OF_DAY) {
			if(ceil) calendar.set(Calendar.HOUR_OF_DAY, calendar.getMaximum(Calendar.HOUR_OF_DAY));
			else calendar.set(Calendar.HOUR_OF_DAY, calendar.getMinimum(Calendar.HOUR_OF_DAY));
			field = Calendar.MINUTE;
		}
		
		if(field == Calendar.MINUTE) {
			if(ceil) calendar.set(Calendar.MINUTE, calendar.getMaximum(Calendar.MINUTE));
			else calendar.set(Calendar.MINUTE, calendar.getMinimum(Calendar.MINUTE));
			field = Calendar.SECOND;
		}
		
		if(field == Calendar.SECOND) {
			if(ceil) calendar.set(Calendar.SECOND, calendar.getMaximum(Calendar.SECOND));
			else calendar.set(Calendar.SECOND, calendar.getMinimum(Calendar.SECOND));
			field = Calendar.MILLISECOND;
		}
		
		if(field == Calendar.MILLISECOND) {
			if(ceil) calendar.set(Calendar.MILLISECOND, calendar.getMaximum(Calendar.MILLISECOND));
			else calendar.set(Calendar.MILLISECOND, calendar.getMinimum(Calendar.MILLISECOND));
		}
		
		return calendar;
	}
	
	/**
	 * Determines the number of days in the month.
	 * The Calendar.GetMaximum(Calendar.DAY_OF_MONTH) always returns 31 which is
	 * not specific to the month defined in the instance of the Calendary.
	 * This method determines the month, sets the day to 28 and then advances
	 * the day until the month changes.
	 *
	 * @param cal   the Calendar with at least the year and month set.
	 *
	 * @return the the count of the number of days in the month.
	 **/
	static public int getDaysInMonth(Calendar cal) 
	{
		Calendar calendar = (Calendar) cal.clone();
		
		int month = calendar.get(Calendar.MONTH);
		int days = 28;
		calendar.set(Calendar.DAY_OF_MONTH, days);
		
		while(month == calendar.get(Calendar.MONTH)) {
			days++;
			calendar.set(Calendar.DAY_OF_MONTH, days);
		}
		
		return days-1;	// Always one more
	}
	
	/**
	 * Determines if a date string is "empty". An empty date string is one which is either
	 * "null", has a length of 0 or contains all zeros for values.
	 *
	 * @param base   the time string to test.
	 *
	 * @return <code>true</code> if the time string is empty, otherwise <code>false</code>
	 **/
	static public boolean isEmpty(String base) 
	{
		if(base == null) return true;
		if(base.length() == 0) return true;
		
		base = base.replaceAll("- :", "");	// Remove punctuation
		try {
			double d = Double.parseDouble(base);
			if(d == 0.0) return true;
		} catch(Exception e) {
			// do nothing
		}

		return false;
	}
	
	/**
	 * Checks if a start and end ISO-8601 time is within another start and end
	 * ISO-8601 time.
	 *
	 * @param compareStart   the starting time of the first time range.
	 * @param compareEnd   the ending time of the first time range.
	 * @param baseStart   the starting time of the second time range.
	 * @param baseEnd   the ending time of the second time range.
	 *
	 * @return <code>true</code> if there is overlap of the two time ranges, otherwise <code>false</code>.
	 **/
	static public boolean isInSpan(String compareStart, String compareEnd, String baseStart, String baseEnd) 
	{
		
		// If compare is in the base interval
		if(isOnOrBefore(compareStart, baseEnd) && isOnOrAfter(compareEnd, baseStart)) return true;
		
		return false;
	}
	
	/**
	 * Checks if a time is within a time span.
	 *
	 * @param compare   the starting time of the first time range.
	 * @param spanStart   the starting time of the second time range.
	 * @param spanEnd   the ending time of the second time range.
	 *
	 * @return <code>true</code> if there is overlap of the two time ranges, otherwise <code>false</code>.
	 **/
	static public boolean isInSpan(Calendar compare, Calendar spanStart, Calendar spanEnd) 
	{
		
		// If compare is in the base interval
		if(isOnOrBefore(compare, spanEnd) && isOnOrAfter(compare, spanStart)) return true;
		
		return false;
	}
	
	/**
	 * Checks if one ISO-8601 time is on or after another ISO-8601 time.
	 * If baseDate is empty it is considered the beginning of time and 
	 * any time is a match.
	 * If the compareDate is empty it is considered to be any time and is a match.
	 *
	 * @param compareDate   the date string to check.
	 * @param baseDate   the reference date string.
	 *
	 * @return <code>true</code> if the compareDate is on or after the baseDate, otherwise <code>false</code>.
	 **/
	static public boolean isOnOrAfter(String compareDate, String baseDate) 
	{
		if(isEmpty(baseDate)) return true;	// base is beginning of time
		if(isEmpty(compareDate)) return true;	// Any time
		
		Calendar baseCal = parseISO8601(baseDate);
		Calendar compareCal = parseISO8601(compareDate);
		
		if(compareCal.equals(baseCal)) return true;
		if(compareCal.after(baseCal)) return true;
		
		return false;
	}
	
	/**
	 * Checks if one time is on or after another time.
	 *
	 * If baseDate is empty it is considered the beginning of time and 
	 * any time is a match.
	 * If the compareDate is empty it is considered to be any time and is a match.
	 *
	 * @param compareDate   the date string to check.
	 * @param baseDate   the reference date string.
	 *
	 * @return <code>true</code> if the compareDate is on or after the baseDate, otherwise <code>false</code>.
	 **/
	static public boolean isOnOrAfter(Calendar compareDate, Calendar baseDate) 
	{
		if(compareDate == null) return true;	// base is beginning of time
		if(baseDate == null) return true;	// Any time
		
		if(compareDate.equals(baseDate)) return true;
		if(compareDate.after(baseDate)) return true;
		
		return false;
	}
	
	
	/**
	 * Checks if one ISO8601 time is on or after another ISO8601 time.
	 * If baseDate is empty it is considered the beginning of time and 
	 * any time is a match.
	 * If the compareDate is empty its considered to be any time and is a match.
	 *
	 * @param compareDate   the date string to check.
	 * @param baseDate   the reference date string.
	 *
	 * @return <code>true</code> if the compareDate is after the baseDate, otherwise <code>false</code>.
	 **/
	static public boolean isAfter(String compareDate, String baseDate) 
	{
		if(isEmpty(baseDate)) return true;	// base is beginning of time
		if(isEmpty(compareDate)) return true;	// Any time
		
		Calendar baseCal = parseISO8601(baseDate);
		Calendar compareCal = parseISO8601(compareDate);
		
		if(compareCal.after(baseCal)) return true;
		
		return false;
	}
	
	/**
	 * Checks if one time is on or after another time.
	 *
	 * If baseDate is empty it is considered the beginning of time and 
	 * any time is a match.
	 * If the compareDate is empty its considered to be any time and is a match.
	 *
	 * @param compareDate   the date string to check.
	 * @param baseDate   the reference date string.
	 *
	 * @return <code>true</code> if the compareDate is after the baseDate, otherwise <code>false</code>.
	 **/
	static public boolean isAfter(Calendar compareDate, Calendar baseDate) 
	{
		if(baseDate == null) return true;	// base is beginning of time
		if(compareDate == null) return true;	// Any time
		
		if(compareDate.after(baseDate)) return true;
		
		return false;
	}
	
	/**
	 * Checks if one ISO8601 time is on or before another ISO8601 time.
	 * If baseDate is empty it is considered the end of time and 
	 * any time is a match.
	 * If the compareDate is empty its considered to be any time and is a match.
	 *
	 * @param compareDate   the date string to check.
	 * @param baseDate   the reference date string.
	 *
	 * @return <code>true</code> if the compareDate is on or before the baseDate, otherwise <code>false</code>.
	 **/
	static public boolean isOnOrBefore(String compareDate, String baseDate) 
	{
		if(isEmpty(baseDate)) return true;	// base is end of time
		if(isEmpty(compareDate)) return true;	// Any time
		
		Calendar baseCal = parseISO8601(baseDate);
		Calendar compareCal = parseISO8601(compareDate);
		
		if(compareCal.equals(baseCal)) return true;
		if(compareCal.before(baseCal)) return true;
		
		return false;
	}
	
	
	/**
	 * Checks if one time is on or before another time.
	 *
	 * If baseDate is empty it is considered the end of time and 
	 * any time is a match.
	 * If the compareDate is empty its considered to be any time and is a match.
	 *
	 * @param compareDate   the date string to check.
	 * @param baseDate   the reference date string.
	 *
	 * @return <code>true</code> if the compareDate is on or before the baseDate, otherwise <code>false</code>.
	 **/
	static public boolean isOnOrBefore(Calendar compareDate, Calendar baseDate) 
	{
		if(compareDate == null) return true;	// base is beginning of time
		if(baseDate == null) return true;	// Any time

		if(compareDate.equals(baseDate)) return true;
		if(compareDate.before(baseDate)) return true;
		
		return false;
	}
	
	/**
	 * Checks if one ISO8601 time is on or before another ISO8601 time.
	 * If baseDate is empty it is considered the end of time and 
	 * any time is a match.
	 * If the compareDate is empty its considered to be any time and is a match.
	 *
	 * @param compareDate   the date string to check.
	 * @param baseDate   the reference date string.
	 *
	 * @return <code>true</code> if the compareDate is beforethe baseDate, otherwise <code>false</code>.
	 **/
	static public boolean isBefore(String compareDate, String baseDate) 
	{
		if(isEmpty(baseDate)) return true;	// base is end of time
		if(isEmpty(compareDate)) return true;	// Any time
		
		Calendar baseCal = parseISO8601(baseDate);
		Calendar compareCal = parseISO8601(compareDate);
		
		if(compareCal.before(baseCal)) return true;
		
		return false;
	}

	/**
	 * Checks if one time is on or before another time.
	 *
	 * If baseDate is empty it is considered the end of time and 
	 * any time is a match.
	 * If the compareDate is empty its considered to be any time and is a match.
	 *
	 * @param compareDate   the date string to check.
	 * @param baseDate   the reference date string.
	 *
	 * @return <code>true</code> if the compareDate is beforethe baseDate, otherwise <code>false</code>.
	 **/
	static public boolean isBefore(Calendar compareDate, Calendar baseDate) 
	{
		if(baseDate == null) return true;	// base is end of time
		if(compareDate == null) return true;	// Any time
		
		if(compareDate.before(baseDate)) return true;
		
		return false;
	}

	/**
	 * Replaces any "T" which may appear in an ISO-8601 compliant date string
	 * with a space.
	 *
	 * @param date   a ISO-8601 time string.
	 *
	 * @return   the {@link String} with the first "T" replaced with a space.
	 **/
	 static public String sanitizeDate(String date)
	 {
	 	return date.replace("T", " ");
	 }

	/**
	 * Sets the date format to use for all date manipulation.
	 *
	 * @param dateFormat   the date format to use as the "standard" format. 
	 *                     See {@link SimpleDateFormat} for how to specify the date format.
	 **/
	static public void setDateFormat(String dateFormat) { mDateFormat = dateFormat; }
	
	/**
	 * Get the current date format used for all date manipulation.
	 *
	 * @return the currently set "standard" format.
	 **/
	static public String getDateFormat() { return mDateFormat; }
	
}
	 
