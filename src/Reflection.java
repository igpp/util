package igpp.util;

import java.lang.reflect.Method;
import java.lang.reflect.Field;

/**
 * A utility class that provides methods for self-aware actions.
 * Methods support the calling set*() methods using keyword/value
 * arguments which includes using "keyword=value" syntax.
 * Additional methods support dumping the value of all fields of an object.
 *
 * @author Todd King
 * @version 1.00 2007
 */
public class Reflection
{
	static private String mVersion = "1.0.1";
	
	static public void main(String[] args)
	{
		Reflection me = new Reflection();
		System.out.println("version: " + mVersion);
		System.out.println(me.getClass().getName());
	}

	/**
	 * Set a member variable using the syntax "name=value".
	 * The passed {@link Object} is searched for a "setName" method
	 * to call to set the member varaible.
	 *
	 * @param obj  the {@link Object} to inspect for the setName method.
	 * @param statement   the string containing "name=value".
	 *
	 * @return <code>true</code> if the statement was parsed correctly, otherwise <code>false</code> 
	 **/
   static public boolean setMember(Object obj, String statement)
    {
    	String[] part;
    	
    	part = statement.split("=", 2);
    	if(part.length != 2) return false;	// No equal sign
    	setMember(obj, part[0], part[1]);
    	
    	return true;
    }
    
   /** 
    * Call the set() method with a given name suffix and a {@link String} array
    * as an argument. Calls the appropriate set method for each value.
    *
	 * @param obj  the {@link Object} to inspect for the setName method.
    * @param name       the name of the item to set.
    * @param value      the {@link String} array of values to set.
    *
	 * @return <code>true</code> 
	 *
    * @since           1.0
    **/
    static public boolean setMember(Object obj, String name, String[] value)
    {
		 for(int i = 0; i < value.length; i++) {
		 	setMember(obj, name, value[i]);
		 }
       return true;
    }

	/** 
	* Call the set() method with a given name suffix and a {@link String}
	* as an argument.
	*
	* @param obj  the {@link Object} to inspect for the setName method.
	* @param name       the name of the item to set.
	* @param value      the {@link String} value to set.
	*
	* @return <code>true</code> if the set method was called, otherwise <code>false</code> 
	*
	* @since           1.0
	**/
	static public boolean setMemberString(Object obj, String name, String value)
	{
		String      member;
		String      methodName = "";
		Object[] passParam = new Object[1];
		Method   method;
		boolean	setFound = false;
		
		try {
			// Signature and parameters for "set" methods
			Class[]  argSig = new Class[1];
			argSig[0] = Class.forName("java.lang.String");
			
			methodName = "set" + igpp.util.Text.toProperCase(name);
			method = getMethod(obj, methodName, argSig);
			if(method != null) {
				passParam[0] = value;
				method.invoke(obj, passParam);
	       	setFound = true;
			}
       } catch(Exception e) {
       	// Ignore errors.
       }
       
      return setFound;
    }

	/** 
	* Call the set() method with a given name suffix passing one argument.
	* Type conversion is attempted for the passed argument based on the 
	* signature of the set method.
	*
	* @param obj  the {@link Object} to inspect for the setName method.
	* @param name       the name of the item to set.
	* @param value      the {@link String} value to set.
	*
	* @return <code>true</code> if the set method was called, otherwise <code>false</code> 
	*
	* @since           1.0
	**/
	static public boolean setMember(Object obj, String name, String value)
	{
		boolean	setFound = false;
		String methodName = "set" + igpp.util.Text.toProperCase(name);
		Object[] passParam = new Object[1];
		int ival;
		boolean bval;
		
		try {
			Method[] methods = obj.getClass().getDeclaredMethods();
			for(Method method : methods) {
				if(methodName.compareToIgnoreCase(method.getName()) == 0) {
					Class<?>[] argList = method.getParameterTypes();
					if(argList.length != 1) continue;
					String cname = argList[0].getCanonicalName();
					if(igpp.util.Text.isMatch(cname, "int")) {
						ival = Integer.parseInt(value);
						passParam[0] = ival;
					} else if(igpp.util.Text.isMatch(cname, "boolean")) {
						bval = igpp.util.Text.isTrue(value);
						passParam[0] = bval;
					} else {	// Assume string
						passParam[0] = value;				
					}
					method.invoke(obj, passParam);
					setFound = true;
					break;
				}
			}
		} catch(Exception e) {
			// Do nothing
		}
       
      return setFound;
    }


	/** 
	* Call the get() method with a given name suffix and return the value as {@link String}.
	*
	* @param obj  the {@link Object} to inspect for the setName method.
	* @param name       the name of the item to set.
	*
	* @return  the returned String value from the get method.
	*
	* @since           1.0
	**/
	static public String getMemberValue(Object obj, String name)
	{
		Method   method;
		String   methodName = "";
		String	value = "";
		try {
			methodName = "get" + igpp.util.Text.toProperCase(name);
			method = getMethod(obj, methodName);
			if(method != null) value = (String) method.invoke(obj);
       } catch(Exception e) {
	     	// Do nothing
       }
       
      return value;
    }
	
	/** 
	* Returns the first Method with a given name regardless of 
	* the full signature (arguments) of the method. 
	* The name of the method is case-insensitve. 
	* Most useful for retrieving methods with no arguments.
	* Returns null if no method is found.
	*
	* @param obj  the {@link Object} to inspect for the get method.
	* @param name       the name of the item to set.
	*
	* @return the {@link Method} is a match is found, otherwise <code>null</code>
	*
	* @since           1.0
	**/
	static public Method getMethod(Object obj, String name)
	{
		try {
			Method[] methods = obj.getClass().getDeclaredMethods();
			for(Method method : methods) {
				if(name.compareToIgnoreCase(method.getName()) == 0) return method;
			}
		} catch(Exception e) {
			// Do nothing
		}
		return null;
	}

	
	/** 
	* Returns the first Method with a given name which matches the 
	* the full signature (arguments) of the method. 
	* The name of the method is case-insensitve. 
	* Returns null if no method is found.
	*
	* @param obj  the {@link Object} to inspect for the get method.
	* @param name       the name of the item to set.
	* @param argSig     an array of {@link Class} instances which specify the argument types for the method.
	*
	* @return the {@link Method} is a match is found, otherwise <code>null</code>
	*
	* @since           1.0
	**/
	static public Method getMethod(Object obj, String name, Class[] argSig)
	{
		try {
			Method[] methods = obj.getClass().getDeclaredMethods();
			for(Method method : methods) {
				if(name.compareToIgnoreCase(method.getName()) == 0) {
					// Check if signature matches
					Method meth = obj.getClass().getMethod(method.getName(), argSig);
					if(meth != null) return meth;
				}
			}
		} catch(Exception e) {
			// Do nothing
		}
		return null;
	}

	/** 
	* Print the value of all member variables to System.out.
	*
	* @param obj  the {@link Object} to inspect for the member varaibles.
	*
	* @since           1.0
	**/
	static public void dump(Object obj)
	{
		try {
			Field[] fields = obj.getClass().getDeclaredFields();
			for(Field field : fields) {
	         field.setAccessible( true );
				System.out.println(field.getName() + ": " + field.get(obj));
			}
		} catch(Exception e) {
			System.out.println("Unable to access all members.");
		}
	}
	
	/** 
	* Print the value of all member variables to System.out.
	*
	* @param obj  the {@link Object} to inspect for the member varaibles.
	*
	* @since           1.0
	**/
	static public void dumpMethods(Object obj)
	{
		try {
			Method[] methods = obj.getClass().getDeclaredMethods();
			for(Method method : methods) {
				System.out.println(method.getName());
			}
		} catch(Exception e) {
			System.out.println("Unable to access all methods.");
		}
	}
	
}