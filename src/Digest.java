package igpp.util;

import java.security.MessageDigest;
import java.security.DigestInputStream;

import java.io.File;
import java.io.FileInputStream;

/**
 * Create message digests (checksums) for phrases or files.
 *
 * @author Todd King
 * @version 1.00 05/09/14
 **/
public class Digest{
	static String mVersion = "1.0.0";
	
	public Digest()
	{
	}
	
	public static void main(String[] args)
	{
		Digest me = new Digest();
		if(args.length < 2) {
			System.out.println("Version: " + me.mVersion);
			System.out.println("Usage: " + me.getClass().getName() + "{method} {phrase}");
			return;
		}
		
		try {
			System.out.println(me.digestPhrase(args[0], args[1]));
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Determine when a file was last modified. Returns 0 if any error occurs.
	 **/
	static public long lastModified(String pathName) 
	{
		try {
			return new File(pathName).lastModified();
		} catch(Exception e) {
		}
		return 0;
	}

	/**
	 * Create a message digest for a phrase using the MD5 method.
	 **/
	static public String digestPhrase(String phrase) 
		throws Exception
	{

		return digestPhrase("MD5", phrase);
	}

	/**
	 * Create a message digest for a phrase using any supported digest method.
	 **/
	static public String digestPhrase(String method, String phrase) 
		throws Exception
	{
		MessageDigest maker = MessageDigest.getInstance(method);
	
		return byteArrayToHexString(maker.digest(phrase.getBytes()));
	}

	/**
	 * Create a message digest for a file using any supported digest method.
	 **/
	static public String digestFile(String pathName) 
		throws Exception
	{
		return digestFile("MD5", pathName);
	}
	
	/**
	 * Create a message digest for a file using any supported digest method.
	 **/
	static public String digestFile(String method, String pathName) 
		throws Exception
	{
		FileInputStream	input = new FileInputStream(pathName);
		MessageDigest maker = MessageDigest.getInstance(method);
		DigestInputStream	digest = new DigestInputStream(input, maker);
		int	max = 2048;
		byte[] b = new byte[max];
		
		while(digest.read(b, 0, max) != -1) {
			// Just toss the bytes
		}
		
		String hexString = byteArrayToHexString(maker.digest());
		
		digest.close();
		input.close();
		
		return hexString;
	}

	/**
 	 * Convert a byte[] array to readable string format. 
	 * This makes the "hex" readable!
	 *
	 * @return result String buffer in String format 
	 * @param in byte[] buffer to convert to string format
	 **/
	static public String byteArrayToHexString(byte in[]) {
	    byte ch = 0x00;
	    int i = 0; 
	    if (in == null || in.length <= 0) return null;
	        
	    String pseudo[]	= { "0", "1", "2", "3", "4", "5", "6", "7", "8",	"9", 
	    					"a", "b", "c", "d", "e", "f"
	    				  };
	    StringBuffer out = new StringBuffer(in.length * 2);
	    
	    while (i < in.length) {
	        ch = (byte) (in[i] & 0xF0);	// Strip off high nibble
	        ch = (byte) (ch >>> 4);		// shift the bits down
	        ch = (byte) (ch & 0x0F);	// must do this is high order bit is on!
	        out.append(pseudo[ (int) ch]); // convert the nibble to a String Character
	        ch = (byte) (in[i] & 0x0F); // Strip off low nibble 
	        out.append(pseudo[ (int) ch]); // convert the nibble to a String Character
	        i++;
	    }
	    String result = new String(out);
	    return result;
	}
}
