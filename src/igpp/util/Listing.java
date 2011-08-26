package igpp.util;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileInputStream;
import java.io.BufferedReader;
import java.util.Comparator;
import java.util.Arrays;
import java.util.ArrayList;

/**
 * Simple file and directory listing utility methods.
 *
 * @author Todd King
 * @version 1.00 2009
 **/
public class Listing {
	static String mVersion = "1.0.1";
	
	public Listing()
	{
	}
	
	public static void main(String[] args)
	{
		Listing me = new Listing();
		System.out.println("Version: " + me.mVersion);
		
		if(args.length < 1) {
			System.out.println("Usage: " + me.getClass().getName() + " {path} [extension]");
			return;
		}
		
		try {
			System.out.println("Listing: " + args[0]);
			if(args.length > 1) System.out.println("Extension: " + args[1]);
			File[] folders = me.getFolderList(args[0]);
			
			if(folders == null) {
				System.out.println("-No folders-");
			} else {
				System.out.println("Folders");
				System.out.println("----------");
				for(File item : folders) System.out.println(item.getName());
			}
			
			System.out.println("");
			
			File[] files = null;
			if(args.length > 1) files = me.getFileListByExtension(args[0], args[1]);
			else files = me.getFileList(args[0]);
			if(files == null) {
				System.out.println("-No files-");
			} else {
				System.out.println("Files");
				System.out.println("----------");
				for(File item : files) System.out.println(item.getName());
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Get the list of folders at the current path.
	 *
	 * @return Array of {@link File} items for each folder, null if no folders are present.
	 **/
	static public File[] getFolderList(String pathName) 
	{
   	File file = new File(pathName);
   	return getFolderList(file);
	}

	/**
	 * Get the list of folders located at the location specified with {@link File} object.
	 *
	 * @return Array of {@link File} items for each folder, null if no folders are present.
	 **/
	static public File[] getFolderList(File root) 
	{
   	if(root == null) return null;
   	
   	File[] list = null;
   	
      list = root.listFiles(new FileFilter()
        {
          public boolean accept(File pathname) { if(pathname.isDirectory() && ! pathname.getName().startsWith(".")) return true; else return false; }
        }
        );
      
      return list;
	}

	/**
	 * Get the list of files at the current path.
	 *
	 * @return Array of {@link File} items for each file, null if no files are present.
	 **/
	static public File[] getFileList(String pathName) 
	{
   	File file = new File(pathName);
   	return getFileList(file);
	}

	/**
	 * Get the list of files at the current path that have a particular filename extension.
	 *
	 * The comparision is case insensitive.
	 *
	 * @return Array of {@link File} items for each file, null if no files are present.
	 **/
	static public File[] getFileListByExtension(String pathName, String extension) 
	{
   	File file = new File(pathName);
   	return getFileListByExtension(file, extension);
	}

	/**
	 * Get the list of files at the current path that have a particular filename extension.
	 *
	 * The comparision is case insensitive.
	 *
	 * @return Array of {@link File} items for each file, null if no files are present.
	 **/
	static public File[] getFileListByExtension(File root, String extension) 
	{
   	if(root == null) return null;
   	if(extension == null) return null;
   	
   	extension = extension.toUpperCase();
   	
      File [] list = root.listFiles(new FileFilter()
        {
          public boolean accept(File pathname) { if(pathname.isFile()) return true; else return false; }
        }
        );
      
      if(list == null) return null;
      
   	ArrayList<File> match  = new ArrayList<File>();
      for(File item : list) {
      	if(item.getName().toUpperCase().endsWith(extension)) match.add(item);	
      }
      
      if(match.isEmpty()) return null;
      
      return match.toArray(new File[1]);
	}
	
	/**
	 * Get the list of files at the current path that match a particular pattern.
	 * The pattern is expressed using regular expressions.
	 *
	 * @return Array of {@link File} items for each file, null if no files are present.
	 **/
	static public File[] getFileList(String pathName, String pattern) 
	{
   	File file = new File(pathName);
   	return getFileList(file, pattern);
	}

	/**
	 * Get the list of files located at the location specified with {@link File} object.
	 *
	 * @return Array of {@link File} items for each file, null if no files are present.
	 **/
	static public File[] getFileList(File root, String pattern) 
	{
   	if(root == null) return null;
   	if(pattern == null) return null;
   	File[] list = null;
   	ArrayList<File> match  = new ArrayList<File>();
   	
      list = root.listFiles(new FileFilter()
        {
          public boolean accept(File pathname) { if(pathname.isFile()) return true; else return false; }
        }
        );
      
      if(list == null) return null;
      
      for(File item : list) {
      	if(item.getName().matches(pattern)) match.add(item);	
      }
      
      if(match.isEmpty()) return null;
      
      return match.toArray(new File[1]);
	}

	/**
	 * Get the list of files located at the location specified with {@link File} object.
	 *
	 * @return Array of {@link File} items for each file, null if no files are present.
	 **/
	static public File[] getFileList(File root) 
	{
   	if(root == null) return null;
   	File[] list = null;
   	
      list = root.listFiles(new FileFilter()
        {
          public boolean accept(File pathname) { if(pathname.isFile()) return true; else return false; }
        }
        );
      
      return list;
	}
	
	/**
	 * Get the most recent file placed in at the location specified with {@link File} object.
	 *
	 * @return Array of {@link File} items for each file, null if no files are present.
	 **/
	static public File getMostRecentFile(File root) 
	{
   	if(root == null) return null;
   	File[] list = getFileList(root);
   	if(list == null) return null;
   		
   	File[] sorted = getSortedList(list, FileComparator.FileLastModified, FileComparator.SortDecending);
   	
   	return sorted[0];
	}

	/**
	 * Get the first file in the sorted file list at the location specified with {@link File} object.
	 *
	 * @return Array of {@link File} items for each file, null if no files are present.
	 **/
	static public File getFirstFile(File root) 
	{
   	if(root == null) return null;
   	File[] list = getFileList(root);
   	
   	if(list == null) return null;
   		
   	File[] sorted = getSortedList(list, FileComparator.FileName, FileComparator.SortAscending);
   	
   	return sorted[0];
	}

	/**
	 * Get the last file in the sorted file list at the location specified with {@link File} object.
	 *
	 * @return Array of {@link File} items for each file, null if no files are present.
	 **/
	static public File getLastFile(File root) 
	{
   	if(root == null) return null;
   	File[] list = getFileList(root);
   	
   	if(list == null) return null;
   		
   	File[] sorted = getSortedList(list, FileComparator.FileName, FileComparator.SortDecending);
   	
   	if(sorted == null) return null;
   	
   	return sorted[0];
	}

	/**
	 * Get the {@link File} object file or folder with a given name located at the location
	 * of a {@link File} object.
	 *
	 * @return {@link File} pointing to the desired file, otherwise null.
	 **/
	static public File getFile(File root, String pathName) 
	{
		if(root == null) return null;
		if(pathName == null) return null;
		if( ! root.isDirectory()) return null;
		
		try {
	   	File file = new File(Text.concatPath(root.getCanonicalPath(), pathName));
	   	return file;
		} catch(Exception e) {
			// Do nothing - default is return null
		}
		
		return null;
	}
	
	/**
	 * Determine if a file or folder exists at the location of
	 * a {@link File} object.
	 *
	 * @return true if
	 **/
	static public boolean exists(File root, String pathName) 
	{
		if(root == null) return false;
		if(pathName == null) return false;
		if( ! root.isDirectory()) return false;
		
		File file = null;
		try {
	   	file = new File(Text.concatPath(root.getCanonicalPath(), pathName));
		} catch(Exception e) {
			return false;
		}
		
      return file.exists();
	}

	/**
	 * Sort an array of {@link File} items based on an attribute of each file.
	 * {@link FileComparator} for a list of allowed values for sortOn and direction.
	 *
	 * @return Array of {@link File} items for each file, null if no folders are present.
	 **/
	static public File[] getSortedList(File[] list, int sortOn, int direction) 
	{
   	if(list == null) return null;
      
      Arrays.sort(list, new FileComparator(sortOn, direction));
      
      return list;
	}
	
	/**
	 * Sort an array of {@link File} items based on an attribute of each file.
	 * List is sorted in ascending order.
	 *
	 * {@link FileComparator} for a list of allowed values for sortOn.
	 *
	 * @return Array of {@link File} items for each file, null if no folders are present.
	 **/
	static public File[] getSortedList(File[] list, int sortOn) 
	{
      return getSortedList(list,sortOn, FileComparator.SortAscending);
	}
	
	/**
	 * Obtain a {@link BufferedReader} for a file at the location of a {@link File} object.
	 *
	 * @return A new instance of a {@link BufferedReader} if file exists, otherwise null.
	 **/
	static public BufferedReader getReader(File root, String pathName) 
	{
		if(root == null) return null;
		if(pathName == null) return null;
		if( ! root.isDirectory()) return null;
		
		try {
			return new BufferedReader(new FileReader(Text.concatPath(root.getCanonicalPath(), pathName)));
		} catch(Exception e) {
			// Do nothing - default is return null
		}
		
		return null;
	}
	
	/**
	 * Obtain a {@link BufferedReader} for pathname string.
	 *
	 * @return A new instance of a {@link BufferedReader} if file exists, otherwise null.
	 **/
	static public BufferedReader getReader(String pathName) 
	{
		if(pathName == null) return null;
		
		try {
			return new BufferedReader(new FileReader(pathName));
		} catch(Exception e) {
			// Do nothing - default is return null
		}
		
		return null;
	}
}
