package main.java.zenit.ui.tree;

import java.io.File;
import java.io.Serializable;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Extension of the TreeItem class with the ability to save a corresponding File-object
 * in the instance.
 * @author Alexander Libot

 */
public class FileTreeItem implements Serializable {

	private String name;
	private File file;
	private int type;
	
	public static final int WORKSPACE = 100;
	public static final int PROJECT = 101;
	public static final int PACKAGE = 102;
	public static final int CLASS = 103;
	public static final int SRC = 104;
	public static final int FOLDER = 105;
	public static final int FILE = 106;
	public static final int INCOMPATIBLE = 107;


    private transient ImageView icon;
    
	
	/**
	 * @param file Corresponding file
	 */
	public FileTreeItem(File file, int type) {
		this.name = file.getName();
		this.file = file;
		this.type = type;
		
		setIcon();
	}

	public ImageView getIcon() {
		return icon;
	}

	public void setIcon() {
		String url = null;
		switch(type) {
		case PROJECT: url = "/zenit/ui/tree/project.png"; break;
		case PACKAGE: url = "/zenit/ui/tree/package.png"; break;
		case CLASS: url = "/zenit/ui/tree/class.png"; break;
		case SRC: url = "/zenit/ui/tree/src.png"; break;
		case FOLDER: url = "/zenit/ui/tree/folder.png"; break;
		case FILE: url = "/zenit/ui/tree/file.png"; break;
		case INCOMPATIBLE: url = "/zenit/ui/tree/incompatible.png"; break;
		}
		
		if (url != null) {
			icon = new ImageView(new Image(getClass().getResource(url).toExternalForm()));
			icon.setFitHeight(16);
			icon.setFitWidth(16);
			icon.setSmooth(true);
		}
	}
	
	/**
	 * Set the corresponding file
	 */
	public void setFile(File file) {
		this.file = file;
	}
	
	/**
	 * Get the corresponding file
	 */
	public File getFile() {
		return file;
	}
	
	public int getType() {
		return type;
	}
	
	public String getStringType() {
		String stringType;
		switch (type) {
		case PROJECT: stringType = "project"; break;
		case PACKAGE: stringType = "package"; break;
		case CLASS: stringType = "class"; break;
		case SRC: stringType = "src-folder"; break;
		case FOLDER: stringType = "folder"; break;
		case FILE: stringType = "file"; break;
		case INCOMPATIBLE: stringType = "incompatible"; break;
		default: stringType = null;
		}
		
		return stringType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}