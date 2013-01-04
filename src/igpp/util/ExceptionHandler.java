package igpp.util;

public class ExceptionHandler {
	static String	mVersion = "1.0.0";
	/** 
    * Command-line interface
	* 
    * @param args    	the arguments passed on the command-line.
    *
    * @since           1.0
    **/
	static public void main(String[] args) 
	{
		try {
			String buffer = null;
			
			buffer.compareTo("Nothing");	// With cause an exception
		} catch(Exception e) {
			printCause(e);
		}
	}
	
	/** 
    * Scans the stack of an exception and prints the first line and any line which begins with "Cause".
    * This provides an easier to read error message than simply printing the stack. 
    * 
    * @param t	A Throwable containing the stack trace. 
    *
    * @since           1.0
    **/
	static public void printCause(Throwable t)
	{
		if(t == null) return;
		
		String buffer;
		boolean first = true;
		
		StackTraceElement stack[] = t.getStackTrace();
		for(StackTraceElement element : stack) {
			buffer = element.toString();
			if(first) { System.err.println(buffer); first = false; continue; }
			if(buffer.startsWith("Cause")) System.err.println(buffer);
		}
	}
}
