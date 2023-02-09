package main.java.zenit.filesystem.helpers;

import java.io.File;

/**
 * Static classes for manipulating filenames and structures
 * @author Alexander Libot
 *
 */
public class FileNameHelpers {
	
	/**
	 * Returns the package name from a filepath, if the project contains a src-folder and
	 * the package is put in that src-folder.
	 * @param file Filepath to search through
	 * @return Returns the name of the package if found, otherwise null
	 */
	public static String getPackageNameFromFile(File file) {
		String packagename = null;
		
		if (file != null) {

			String[] folders = getFoldersAsStringArray(file);
			
			int srcIndex = getSrcFolderIndex(folders);

			if (srcIndex != -1 && folders.length > srcIndex+2) { //Filepath is deeper that src-folder, +2 to account for the file at the end of the path and the fact that the array starts at 0
				packagename = folders[srcIndex + 1]; //Package folder is one step down from src-folder
			}
		}
		
		return packagename;
	}
	

	
	/**
	 * Removes the last file/folder in a filepath
	 * @param filepath The filepath to alter
	 * @return The altered file
	 */

    /**
     * Utveckla kommentaren lite kanske?
     */
	public static File getFilepathWithoutTopFile(File filepath) {
		File newFilepath;
		
		String[] folders = getFoldersAsStringArray(filepath);
		String newFilepathString = "";
		
		for (int index = 0; index < folders.length-1; index++) {
			newFilepathString += folders[index] + "/";
		}
		
		newFilepath = new File(newFilepathString);
		
		return newFilepath;
	}

	
	/**
	 * Returns the index of the src-folder inside a String-array
	 * @param folders The array of folders to search through.
	 * @return Returns index of src-folder if found, otherwise -1
	 */
	public static int getSrcFolderIndex(String[] folders) {
		int srcIndex = -1; //Indicates how deep in the filestructure the src-folder is
		int counter = 0;
		
		for (String folder : folders) {
			if (folder.equals("src")) {
				srcIndex = counter;
				break;
			}
			counter++;
		}
		return srcIndex;
	}
	
	/**
	 * Converts a filepath into a string-array of folder names
	 * @param file The filepath to convert
	 * @return A string-array of folder names
	 */
	public static String[] getFoldersAsStringArray(File file) {
		String[] folders;
		String filepath = file.getAbsolutePath(); //Get the path in string
		folders = filepath.split("\\\\"); //Split path into the different folders
		
		return folders;
	}
}
