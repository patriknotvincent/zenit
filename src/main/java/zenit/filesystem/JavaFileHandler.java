package zenit.filesystem;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import zenit.exceptions.TypeCodeException;
import zenit.filesystem.helpers.CodeSnippets;
import zenit.filesystem.helpers.FileNameHelpers;
import zenit.ui.DialogBoxes;

/**
 * Methods for creating, reading, writing, renaming and deleting .java files in file system.
 * Only to be accessed using {@link FileController FileController} methods.
 * 
 * @author Alexander Libot
 *
 */
public class JavaFileHandler extends FileHandler {
	
	/**
	 * Tries to create a new .java file in file system. Adds .java to file name if not already 
	 * added. If {@code content} is {@code null}, adds a code snippet from 
	 * {@link zenit.filesystem.helpers.CodeSnippets CodeSnippets} using {@code typeCode}
	 * parameter. If content is not {@code null}, writes the data from {@code content} to file.
	 * 
	 * @param file File to be created
	 * @param content Content to be written to file. May be null.
	 * @param typeCode If {@code content} is null, writes content from {@link 
	 * zenit.filesystem.helpers.CodeSnippets CodeSnippets} using this parameter.
	 * @return Created file if created, otherwise {@link java.io.IOException IOException} is thrown
	 * @throws IOException Throws IOException if file already exists or it couldn't
	 * be created.
	 */
	protected static File createFile(File file, String content, int typeCode) throws IOException {
		try {
			String fileName = file.getName();
			//Adds .java if not already added
			if (!fileName.endsWith(".java")) {
				String filepath = file.getPath();
				filepath += ".java";
				file = new File(filepath);
			}
			//Checks if file already exists
			if (file.exists()) {
				DialogBoxes.errorDialog("Error: Files", "Couldn't create file", "File name exists in this folder. Choose another name");
				return null;
			}
			//Tries to create file
			file.createNewFile();
			
			//Write content to file
			if (content == null) {
				String pckgName = FileNameHelpers.getPackageNameFromFile(file);
				try {
					if(pckgName != null) {
						content = CodeSnippets.newSnippet(typeCode, file.getName(), pckgName);
					} else {
						content = CodeSnippets.newSnippet(typeCode, file.getName());
					}
				} catch (TypeCodeException ex) {
					ex.printStackTrace();
				}
			}
			
			try (BufferedWriter br = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), textEncoding))) {
				br.write(content);
				br.flush();
			}

			return file;
		} catch (IOException e) {
			DialogBoxes.errorDialog("Error: Files", "Couldn't create file", "File name exists in this folder. Choose another name");
			return null;
		}
	}
	
	/**
	 * Tries to read file from disk.
	 * @param file The file to read
	 * @return Returns String-object with content from {@code file} if read, otherwise
	 * throws a {@link java.io.IOException IOException}
	 * @throws IOException Throw IOException if file could be read.
	 */
	protected static String readFile(File file) throws IOException {
		try (BufferedReader br = new BufferedReader(new InputStreamReader(
				new FileInputStream(file), textEncoding))) {
			
			String currentString = br.readLine();
			String stringBuilder = "";

			while (currentString != null) {
				stringBuilder += currentString + "\n";
				currentString = br.readLine();
			}
			return stringBuilder;
			
		} catch (IOException ex) {
			throw new IOException("File couldn't be read");
		}
	}
	
	/**
	 * Tries to save {@code content} to disk in {@code file}.
	 * @param file The file to write over with {@code content}.
	 * @param content The new content of the file
	 * @throws IOException Throws {@link java.io.IOException IOException} if file
	 * can't be saved.
	 */
	protected static void saveFile(File file, String content) throws IOException {
		try (BufferedWriter br = new BufferedWriter(new OutputStreamWriter(
				new FileOutputStream(file), textEncoding))) {
			br.write(content);
			br.flush();
		} catch (IOException ex) {
			throw new IOException(ex.getMessage());
		}
	}
	
	/**
	 * Tries to rename the file.
	 * @param oldFile File to be renamed
	 * @param newFilename The new filename
	 * @return The renamed file
	 * @throws IOException Throws IOException if file already exists with same name or 
	 * if file couldn't be renamed
	 */
	protected static File renameFile(File oldFile, String newFilename) throws IOException {
		
		File tempFile = FileNameHelpers.getFilepathWithoutTopFile(oldFile); //Removes file name
		System.out.println(tempFile);
		//Create new file with new name
		String newFilepath = tempFile.getPath() + "/" + newFilename;
		System.out.println(newFilepath);
		File newFile = new File(newFilepath);
		
		if (newFile.exists()) {
			DialogBoxes.errorDialog("Error: Java File Renaming", "Couldn't rename file", "File name already exists in this folder. Choose another name");
			throw new IOException("File already exists");
		}

		boolean success = oldFile.renameTo(newFile);
		
		if (!success) {
			throw new IOException("Couldn't rename file");
		}
		
		return newFile;
	}
	
	/**
	 * Tries to delete the file.
	 * @param file The file to delete
	 * @throws IOException Throws {@link java.io.IOException IOException} if file can't
	 * be deleted.
	 */
	protected static void deleteFile(File file) throws IOException {
		if (!file.delete()) {
			throw new IOException("Failed to delete " + file);
		}
	}
}