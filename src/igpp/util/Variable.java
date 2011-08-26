package igpp.util;

/** 
 * Variable aids in the defnition of ruleset variables.
 *
 * @author      Todd King
 * @author      Planetary Data System
 * @version     1.0, 04/21/03
 * @since       1.0
 */
public class Variable {
	/** The list of elements in the label */
 	public String		mName = "";
 	public String		mValue = "";
 	
    /** 
     * Creates an instance of a variable.
	 *
     * @param name		the name for the variable.
     * @param value		the value for the variable. 
     *
     * @since           1.0
     */
 	public Variable(String name, String value) {
 		mName = name;
 		mValue = value;
	}
		
    /** 
     * Prints out a variable.
	 * 
     * @since           1.0
     */
	public void dump() {
		System.out.println(mName + " = " + mValue);
	}
	
}