package igpp.util;

import java.util.ArrayList;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;

/**
 * Run a command line and collect the output in String buffers.
 * Class is threaded so that multiple instances can be executed.
 *
 * @author      Todd King
 * @since       1.0
 */
public class Process {
	ArrayList<String>	mOutput = new ArrayList<String>();
	
	/**
	 * Run a command line.
	 *
	 * Output from the command (or error messages) are available with the method
	 * {@link #getOutput()}.
	 *
	 * @param command the command line to run.
	 *
	 * @return the exit status of the command.
	 *
	 * @since 1.0
	 **/
	public int run(String command)
	{
		return run(command, null);
	}
	
	/**
	 * Run a command line with a specified path as the home directory.
	 *
	 * Output from the command (or error messages) are available with the method
	 * {@link #getOutput()}.
	 *
	 * @param command the command line to run.
	 * @param home the path to the home directory in which to run the command.
	 *
	 * @return the exit status of the command.
	 *
	 * @since 1.0
	 **/
	public int run(String command, String home)
	{
		Runtime	runtime;
		String	buffer;
		String	message;
		boolean dontParseArg;
		String	hold;
		java.lang.Process		process;
		ArrayList<String>	argList;
		InputStream	input;
		InputStream	error;
		OutputStream	outputStream;
		BufferedReader	inputReader;
		BufferedReader	errorReader;
		ThreadedReader	errorThread;
		ThreadedReader	inputThread;
		int		exitValue = -1;	// Failed to run
		File	homeDir = null;
		
		if(home != null) homeDir = new File(home);
		
		buffer = command;
		try {
			// Prepare a runtime environment
			runtime = Runtime.getRuntime();

			// Determine how we should run application
			dontParseArg = false;
			hold = System.getProperty("os.name");
			if(hold.length() >= 6) { dontParseArg = (hold.substring(0, 6).compareToIgnoreCase("WINDOW") == 0); }
			
			// Replace variables then parse
			if(dontParseArg) {	// Let OS parse it
				process = runtime.exec(buffer, null, homeDir);
			} else {
				buffer = buffer.replaceAll("\\\\n", "\\\\\\\\n"); // Preserve escapes
				buffer = buffer.replaceAll("\\\\r", "\\\\\\\\r"); // Preserve escapes
				argList = argSplit(buffer, true);
				String[] argArray = new String[argList.size()];
				argArray = (String[]) argList.toArray(argArray);
				process = runtime.exec(argArray, null, homeDir);
			}
			
			// Collect output from command
			outputStream = process.getOutputStream();
			outputStream.close();
			
			input = process.getInputStream();
			inputReader = new BufferedReader(new InputStreamReader(input));
			inputThread = new ThreadedReader(inputReader, mOutput);
			
			error = process.getErrorStream();
			errorReader = new BufferedReader(new InputStreamReader(error));
			errorThread = new ThreadedReader(errorReader, mOutput);
			
			//start the threads
			inputThread.start();
			errorThread.start();
			
			process.waitFor();	// Wait to completion
			
			//read anything still in buffers.
			//join() waits for the threads to die.
			inputThread.join();
			errorThread.join();
			
			exitValue = process.exitValue();
		} catch(Exception e) {
			buffer = "Unable to run: " + command;
			mOutput.add(buffer);
			mOutput.add(e.getMessage());
		}
		
		return exitValue;
	}
	
	/**
	 * Retrieve the output from the last run.
	 *
	 * @return  an {@link ArrayList} of {@link String}s with each line of the output.
	 **/
	public ArrayList<String> getOutput()
	{
		return mOutput;
	}
	
	/**
	 * Retrieve the output from the last run formatted for HTML.
	 * Each line of output has a "<br/>" tag appended.
	 *
	 * @return a {@link String} containing the output formatted as HTML.
	 **/
	public String getOutputHTML()
	{
		String	buffer = "";
		for(String line: mOutput) {
			buffer += line + "<br/>";
		}
		return buffer;
	}
	

	 /** 
     * Split a string into one or more substrings by parsing on
     * whitespace and treating "!" and "=" as individual arguments. 
     * Arguments in quotes are kept intack. Escaped characters are
     * converted preserved with the escape removed.
	 * 
     * @param buffer	the string to parse.
     * @param onlyWhitespace	if <code>true</code> split the string 
     *					using only whitespace as a delimiter. If <code>false</code>
     *					the string is parsed on whitespace and "!" and "=" 
     *					are considered as individual arguments.
     *
     * @return          <code>ArrayList</code> an array of arguments.
     *
     * @since           1.0
     */
	static public ArrayList<String> argSplit(String buffer, boolean onlyWhitespace) {
		ArrayList<String>	arg = new ArrayList<String>();
		int			n;
		char		c;
		String		temp = "";
		boolean		inQuote = false;
		
		n = buffer.length();
		for(int i = 0; i < n; i++) {
			c = buffer.charAt(i);
			if(c == '\\') { // Convert escaped characters
			  i++; 
			  if(i < n) {temp += buffer.charAt(i);} 
		    } else {
				if(c == '"') { if(inQuote) inQuote = false; else inQuote = true; continue; }
				if(inQuote) {
					temp += c;
				} else {
					if(onlyWhitespace) {
						if(Character.isWhitespace(c)) {	// Argument seperator
							if(temp.length() > 0) { arg.add(temp); temp = ""; }
						} else {
							temp += c;
						}
					} else {	// Use all tokens 
						if(Character.isWhitespace(c)) {	// Argument seperator
							if(temp.length() > 0) { arg.add(temp); temp = ""; }
						} else if(c == '!') {	// Seperator
							if(temp.length() > 0) { arg.add(temp); }
							arg.add("!"); 
							temp = "";
						} else if(c == '=') {	// Seperator
							if(temp.length() > 0) { arg.add(temp); }
							arg.add("="); 
							temp = "";
						} else {	// Add to buffer
							temp += c;
						}
					}
				}
			}
		}
		if(temp.length() > 0) { arg.add(temp); temp = ""; }
		
		return arg;
	}
}
