package igpp.util;

import java.io.BufferedReader;
import java.util.ArrayList;

/**
* Collects output from a thread and stores it in
* interanl buffers. Collects output from both
* the standard out (System.out) and 
* and standard error (System.err) of an application.
*
* @author Todd King
*/
public class ThreadedReader extends Thread {
	private ArrayList<String> mBuffer;
	private BufferedReader mReader;
	
	/**
	 * Create an instance of a ThreadedReader attached to a buffered reader
	 * and place all content in an String ArrayList.
	 *
	 * @param reader	either standard err or standard in
	 * @param out 	to store the output
	 **/
	public ThreadedReader(BufferedReader reader, ArrayList<String> out) 
	{
		mBuffer = out;
		mReader = reader;
	}
	
	/**
	 * Do the thread's work. Reader from the defined source and save the
	 * acquired content in the defined buffer.
	 *
	 **/
	public void run() 
	{
		String	buffer;
		
		try {
		   // hold character data in the buffer until we get to the end
			while((buffer = mReader.readLine()) != null) mBuffer.add(buffer);
		} catch (Exception e) {
			// tack the error message onto the end of the data stream
			mBuffer.add("Error reading:" + e.getMessage());
		}
	}
}