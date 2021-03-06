package igpp.util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import java.lang.NullPointerException;
import java.lang.SecurityException;
/**
 * File utilities that augment the standard java.io.File class.
 *
 * @author Todd King
 * @author UCLA/IGPP
 * @version     1.0.0
 * @since     1.0.0
 **/
public class File extends java.lang.Object {
	
	public File()
	{
	}
	
	/**
	 * Copy a file from one location to another. 
	 * The copy is failure safe in that if the copy fails to complete
	 * the destination is not over written.
	 * 
	 * @param source	the name of the source file.
	 * @param destination	the name of the file to copy the source file to.
	 
	 * @return the total number of bytes copied.
	 */
	 static public long copy(String source, String destination) 
	 	throws NullPointerException, IOException, SecurityException 
	 {
	 	String	path = "";
	 	long		copied = 0;

		java.io.File dir = new java.io.File (".");
		path = dir.getCanonicalPath();

		if(destination.charAt(0) != dir.separatorChar) {
			path += dir.separator + destination;
		} else {
			path = destination;
		}
		
		java.io.File outFile = new java.io.File(path);
		outFile.delete();	// Remove file if it exists
		
		// copy to file
		FileInputStream reader = new FileInputStream(source);
		FileOutputStream writer =	new FileOutputStream(path);
		
		// Now copy the bytes
		byte[]	buffer = new byte[1024];
		int		n;
		
		while ((n = reader.read(buffer, 0, 1024)) > 0) {
			writer.write(buffer, 0, n);
			copied += n;
		}
		reader.close();
		writer.close();

		return copied;
	}

	/**
	 * Remove a file from the file system
	 * 
	 * @param source	the name of the source file.
	 *
	 * @return		<code>true</code> if the delete is successful.
	 *				<code>false</code> otherwise.
	 */
	 static public boolean delete(String source) 
	 {
		java.io.File outFile = new java.io.File(source);
		return outFile.delete();	// Remove file if it exists
	}
	
	/**
	 * Rename a file. If the destination file name is
	 * not absolute the file name become relative to the
	 * location of the source file name. The destination
	 * file is removed if it exists.
	 * 
	 * @param source	the name of the source file.
	 *
	 * @return		<code>true</code> if the rename is successful.
	 *				<code>false</code> otherwise.
	 *
	 * @throws IOException If an I/O error occurs, which is possible because 
	 *                 the construction of the canonical pathname may require file system queries 
	 * @throws NullPointerException If the either pathname argument is null
	 * @throws SecurityException If a security manager exists and its 
	 *                SecurityManager.checkWrite(java.lang.String) method denies 
	 *                write access to either the old or new pathnames
	 */
	 static public boolean rename(String source, String destination) 
	 	throws NullPointerException, SecurityException, IOException
    {
	 	String	path = "";
		java.io.File dir = new java.io.File (".");
		path = dir.getCanonicalPath();

		if(destination.charAt(0) != dir.separatorChar) {
			path += dir.separator + destination;
		} else {
			path = destination;
		}
		
		java.io.File outFile = new java.io.File(path);
		outFile.delete();	// Remove file if it exists
		
		java.io.File inFile = new java.io.File(source);
		
		return inFile.renameTo(outFile);
	}
		
	/**
	 * Determines if a given pathname is to a directory.
	 * 
	 * @param pathname	the name of the file system object.
	 *
	 * @return		<code>true</code> if the pathname points to a directory.
	 *				<code>false</code> otherwise.
	 *
	 */
	 static public boolean isDirectory(String pathname) 
    {
		java.io.File item = new java.io.File (pathname);
		
		return item.isDirectory();
	}
		
	/**
	 * Determines if a given pathname is to a file.
	 * 
	 * @param pathname	the name of the filesystem object.
	 *
	 * @return		<code>true</code> if the pathname points to a directory.
	 *				<code>false</code> otherwise.
	 *
	 */
	 static public boolean isFile(String pathname) 
    {
		java.io.File item = new java.io.File (pathname);
		
		return item.isFile();
	}

	/**
	 * Extract the name of the file from a pathname.
	 * 
	 * @param pathname	the name of the file system object.
	 *
	 * @return		the file name portion of pathname.
	 *
	 */
	static public String getName(String pathname) 
	{
		java.io.File file = new java.io.File (pathname);		
			
		return file.getName();
	}
	
	/**
	 * Extract the parent of the file from a pathname.
	 * 
	 * @param pathname	the name of the filesystem object.
	 *
	 * @return		the parent portion of pathname.
	 *
	 */
	static public String getParent(String pathname) 
	{
		java.io.File file = new java.io.File (pathname);		
			
		return file.getParent();
	}
		
	/**
	 * Extract the base name of the file from a pathname.
	 * 
	 * @param pathname	the name of the file system object.
	 *
	 * @return		the base name of the file portion of pathname.
	 *
	 */
	static public String getBaseName(String pathname) 
	{
		java.io.File file = new java.io.File (pathname);		
		String base = file.getName();
		int n = base.lastIndexOf(".");
		if(n != -1) base = base.substring(0, n);
		
		return base;
	}
	
	/**
	 * Extract the extension of the file from a pathname.
	 * 
	 * @param pathname	the name of the file system object.
	 *
	 * @return		the extension portion of pathname.
	 *
	 */
	static public String getExtension(String pathname) 
	{
		java.io.File file = new java.io.File (pathname);		
		String ext = file.getName();
		int n = ext.lastIndexOf(".");
		if(n != -1) ext = ext.substring(n);
		else ext = "";
		
		return ext;
	}
	
	/**
	 * Set the owner and group for a file.
	 * Runs the systems "chown" or "chgrp" command with the correct arguments.
	 * If user is null and group is set then "chgrp" is called, otherwise
	 * "chown" is called.
	 * 
	 * @param pathname	the name of the file system object.
	 * @param user the user name in the system.
	 * @param group	the group name in the system
	 *
	 * @return		true if successful, otherwise false.
	 *
	 */
	static public boolean setOwner(String pathname, String user, String group) 
	{
	   try 
	   {
		   String cmd = "chown";
		   String option = null;
		   
		   if(user == null) { cmd = "chgrp"; } else { option = user; }
		   if(group != null) { 
			   if(option != null) option += ".";
			   option += group; 
		   }
		   
		   String command = cmd + " " + option + " " + pathname;
		   Runtime.getRuntime().exec(command);
	   } catch (IOException e) {
		   e.printStackTrace();
		   return false;
	   }
	   
	   return true;
	}
	
	/**
	 * Return the size of the file in bytes.
	 * 
	 * @param pathname	the name of the file system object.
	 *
	 * @return		the size of the file in bytes.
	 *
	 */
	 static public long getSize(String pathname)
	 {
		 java.io.File file = new java.io.File (pathname);
		 
		 return file.length();
	 }

	/**
	 * Deterines if a file exists.
	 * 
	 * @param pathname	the name of the file system object.
	 *
	 * @return		true if the file exists, false otherwise.
	 *
	 */
	 static public boolean exists(String pathname)
	 {
		 java.io.File file = new java.io.File (pathname);
		 
		 return file.exists();
	 }

	/**
	 * Return the MD5 digest for a file.
	 * This is a convenience function and calls igpp.util.Digest.digestFile() 
	 * 
	 * @param pathname	the name of the filesystem object.
	 *
	 * @return		a String containing the MD5 digest for a file. If the digest cannot be calculated an empty string is returned.
	 *
	 */
	 static public String getMD5(String pathname)
	 {
		 String digest = "";
		 try {
			 digest = igpp.util.Digest.digestFile(pathname);
		 } catch(Exception e) {
			 digest = "";
		 }
		 
		 return digest;
	 }	
	 
	/**
	 * Return the SHA-1 digest for a file.
	 * This is a convenience function and calls igpp.util.Digest.digestFile() 
	 * 
	 * @param pathname	the name of the filesystem object.
	 *
	 * @return		a String containing the SHA-1 digest for a file. If the digest cannot be calculated an empty string is returned.
	 *
	 */
	 static public String getSHA1(String pathname)
	 {
		 String digest = "";
		 try {
			 digest = igpp.util.Digest.digestFile(pathname, "SHA-1");
		 } catch(Exception e) {
			 digest = "";
		 }
		 
		 return digest;
	 }	
	 
	/**
	 * Return the SHA-256 digest for a file.
	 * This is a convenience function and calls igpp.util.Digest.digestFile() 
	 * 
	 * @param pathname	the name of the filesystem object.
	 *
	 * @return		a String containing the SHA-256 digest for a file. If the digest cannot be calculated an empty string is returned.
	 *
	 */
	 static public String getSHA256(String pathname)
	 {
		 String digest = "";
		 try {
			 digest = igpp.util.Digest.digestFile(pathname, "SHA-256");
		 } catch(Exception e) {
			 digest = "";
		 }
		 
		 return digest;
	 }	 
	 
	/**
	 * Return the SHA-512 digest for a file.
	 * This is a convenience function and calls igpp.util.Digest.digestFile() 
	 * 
	 * @param pathname	the name of the filesystem object.
	 *
	 * @return		a String containing the SHA-512 digest for a file. If the digest cannot be calculated an empty string is returned.
	 *
	 */
	 static public String getSHA512(String pathname)
	 {
		 String digest = "";
		 try {
			 digest = igpp.util.Digest.digestFile(pathname, "SHA-512");
		 } catch(Exception e) {
			 digest = "";
		 }
		 
		 return digest;
	 }	 
}

