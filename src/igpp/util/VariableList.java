package igpp.util;

import java.util.ArrayList;
import java.util.Iterator;

/** 
 * VariableList manages one or more PPIVariable object.
 *
 * @author      Todd King
 * @author      Planetary Data System
 * @version     1.0, 09/16/05
 * @since       1.0
 */
public class VariableList {
	/** The list of elements in the label */
 	public ArrayList		mVariable = new ArrayList();
 	
    /** 
     * Creates an instance of a variable.
	 *
     * @since           1.0
     */
 	public VariableList() 
 	{
	}

	/** 
     * Removes all variables from list.
	 * 
     * @since           1.0
     */
	public void clear() 
	{
		mVariable.clear();
	}
	
	/** 
     * Finds a variable in the variable list and set its value.
     * If the variable does not exist one is added.
	 * 
     * @param variable	the name of the variable to set.
     *
     * @return          <code>true</code> if an variable was set or added;
     *                  <code>false</code> if unable to add the variable.
     *
     * @since           1.0
     */
	public boolean findAndSet(Variable variable) 
	{
		return findAndSet(variable.mName, variable.mValue);
	}
	
	/** 
     * Finds eavery variable in a list and sets it in this list.
     * If the variable does not exist one is added.
	 * 
     * @param list	the PPIVariableList of variables to add.
     *
     * @return          <code>true</code> if an variable was set or added;
     *                  <code>false</code> if unable to add the variable.
     *
     * @since           1.0
     */
	public boolean findAndSet(VariableList list) 
	{
		Iterator it = list.iterator();
		while(it.hasNext()) findAndSet((Variable) it.next());
		return true;
	}
	
    /** 
     * Finds an item in an array list and set its value.
     * If the item does not exist one is added.
	 * 
     * @param name		the name of the variable to set.
     * @param value		the value to set for the variable.
     *
     * @return          <code>true</code> if an variable was set or added;
     *                  <code>false</code> if unable to add the variable.
     *
     * @since           1.0
     */
	public boolean findAndSet(String name, String value) 
	{
		return findAndSet(name, value, true);
	}
	
    /** 
     * Finds an item in an array list and set its value.
     * If the item does not exist one is added.
	  * 
     * @param name		the name of the variable to set.
     * @param value		the value to set for the variable.
     * @param resolve	a flag indicating whether to resolve variables in the
     *					value. 
     *
     * @return          <code>true</code> if an variable was set or added;
     *                  <code>false</code> if unable to add the variable.
     *
     * @since           1.0
     */
	public boolean findAndSet(String name, String value, boolean resolve) 
	{
		Variable		variable;
		
		// Clean-up and unquote value
		if(value != null) {
			value = value.trim();
			if(value.startsWith("\"")) value = value.substring(1);
			if(value.endsWith("\"")) value = value.substring(0, value.length()-1);
		}
		
		// Replace variables - if needed
		if(resolve) value = replaceVariable(value, false);
		
		// Search for variable
		for(int i = 0; i < mVariable.size(); i++) {
			variable = (Variable) mVariable.get(i);
			if(variable.mName.compareTo(name) == 0) {
				variable.mValue = value;
				return true;
			}
		}
		
		// If we reach here we need to define the variable
		mVariable.add(new Variable(name, value));
		return true;	
	}
	
    /** 
     * Finds a variable in the variable list and returns its value.
     * If the variable does not exist null is returned.
	 * 
     * @param name		the name of the variable to set.
     *
     * @return          <code>null</code> if an variable does not exist;
     *                  the value assigned to the variable if it does.
     *
     * @since           1.0
     */
	public String getValue(String name) 
	{
		String	value;
		
		value = getValue(name, false);
		
		return value;
	}	
	
    /** 
     * Finds a variable in the variable list and returns its value.
     * If the variable does not exist null is returned.
	 * 
     * @param name		the name of the variable to set.
     * @param blank		indicates whether to return a blank string if not
     *					the variable is not found. If <code>true</code> a
     *					blank is returned. 
     *
     * @return          The variable <code>null</code> if a variable does not exist and
     *					<code>blank</code> is <code>false</code>. Returns a blank string
     *					if <code>blank</code> is true;
     *                  the value assigned to the variable if it does.
     *
     * @since           1.0
     */
	public String getValue(String name, boolean blank) 
	{
		Variable		variable;
		
		for(int i = 0; i < mVariable.size(); i++) {
			variable = (Variable) mVariable.get(i);
			if(variable.mName.compareTo(name) == 0) {
				return variable.mValue;
			}
		}
		
		if(blank) return "";
		
		return null;
	}
	
    /** 
     * Finds an item in a variable list and returns its value as an integer.
     * If the variable does not exist 0 is returned. Since 0 is a valid integer
     * it can not be considered and indication that a variable does not exist.
	 * 
     * @param name		the name of the variable to set.
     *
     * @return          integer value assigned to the variable.
     *
     * @since           1.0
     */
	public int getIntValue(String name) 
	{
		String	buffer;
		
		buffer = getValue(name, true);
		return igpp.util.Text.toInt(buffer);
	}
	
    /** 
     * Finds an item in a variable list and returns its value as an boolean.
     * If the variable does not exist false is returned. 
	 * 
     * @param name		the name of the variable to set.
     *
     * @return          boolean value assigned to the variable.
     *
     * @since           1.0
     */
	public boolean getBooleanValue(String name) 
	{
		String	buffer;
		
		buffer = getValue(name, true);
		return igpp.util.Text.isTrue(buffer);
	}
	
    /** 
     * Searches the passed string for variables and replaces each 
     * variable with its current value. Variables start with a dollar 
     * sign ($) followed by the name of the variable. Variable names can 
     * not contain white space. The string is passed through
     * only once so to nest variables within the values of variables requires
     * multiple calls. Replaced variables are unadorned (not enclosed in 
     * quotation marks).
     *
     * @param buffer	the string to search for variables.
     *
     * @return			the string with all variables replaced with
     *					the current value.
	 * 
     * @since           1.0
     */
	public String replaceVariable(String buffer) {
		return replaceVariable(buffer, true);
	}
	
    /** 
     * Searches the passed string for variables and replaces each 
     * variable with its current value. Variables start with a dollar 
     * sign ($) followed by the name of the variable. Variable names can 
     * not contain white space. The string is passed through
     * only once so to nest variables within the values of variables requires
     * multiple calls.
     *
     * @param buffer	the string to search for variables.
     * @param plain		indicates whether the replaced value of a vaiable should be 
     *					enclosed in quotation marks. If true, the value will be unadorned.
     *
     * @return			the string with all variables replaced with
     *					the current value.
	 * 
     * @since           1.0
     */
	public String replaceVariable(String buffer, boolean plain) {
		String 	output = "";
		String	name;
		String	value;
		int		start = 0;
		int		i, n;
		int		len;
		char	c;
		
		if(buffer == null) return buffer;
		len = buffer.length();
		
		while((n = buffer.indexOf('$', start)) != -1) {
			if(n > start) output += buffer.substring(start, n);
			n++;
			name = "";
			while(n < len) {	// Get variable name
				c = (char) buffer.charAt(n);
				if(!Character.isLetterOrDigit(c) && c != '_') break;
				name += c;
				n++;
			}
			// Find variable - if not found in variable list - check option list
			value = getValue(name, true);
			if(!plain) {	// Escape the quotation marks
			   value = value.replaceAll("\"", "\\\"");
			}
			output += value;
			start = n;
		}
		if(start < len) output += buffer.substring(start, len);
		
		return output;
	}
		
    /** 
     * Returns an Iterator for the variables in the list.
     *
     * @return			an Iterator for the variables in the list.
	 * 
     * @since           1.0
     */
	public Iterator iterator() {
		return mVariable.iterator();
	}
	
    /** 
     * Prints out all variables in the variable list.
	 * 
     * @since           1.0
     */
	public void dump() 
	{
		Variable var;
		
		Iterator it = mVariable.iterator();
		while(it.hasNext()) {
			var = (Variable) it.next();
			var.dump();
		}
	}
	
}