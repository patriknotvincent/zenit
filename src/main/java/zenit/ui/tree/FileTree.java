package zenit.ui.tree;

import java.io.File;
import java.util.ArrayList;

import javafx.scene.control.TreeItem;
import zenit.filesystem.ProjectFile;

/**
 * Handles the nodes of a filetree
 * @author Alexander Libot
 *
 */
public class FileTree {
	
	/**
	 * Initiates all the nodes from a parent folder (excluding parent node itself. 
	 * Recursively adds all children and with corresponding file.
	 * Currently excludes bin-folders and hidden files (with '.'-prefix).
	 * For initiation including parent-node, use {@link #createParentNode(TreeItem, File) createParentNode} method
	 * instead.
	 * @param parent The parent node to create children nodes to
	 * @param file The file corresponding to the node, might be unnecessary due to changes
	 * in FileTreeItem-structure.
	 */
	public static void createNodes(TreeItem<FileTreeItem> parent, File file) {
		int type = 0;
		if (file.listFiles() == null) {
			return;
		}
		
		var items = new ArrayList<TreeItem<FileTreeItem>>();
		
		File[] files = file.listFiles();
		String itemName;
		if(files != null) {
			for (File value : files) {
				itemName = value.getName();
				if (!itemName.startsWith(".") && !itemName.equals("bin") && !itemName.endsWith(".class")) { //Doesn't include hidden files
					type = calculateType(parent, value);
					TreeItem<FileTreeItem> item = new TreeItem<>(new FileTreeItem(value, type));
					items.add(item);

					if (value.isDirectory()) {
						createNodes(item, value);
					}
				}
			}
		}
		
		items.sort((a, b) -> a.getValue().getFile().getName().compareToIgnoreCase(b.getValue().getFile().getName()));
		
		for (var item : items) {
			parent.getChildren().add(item);
		}
	}
	
	/**
	 * Initiates a parent node and all children with corresponding files using recursion.
	 * For initiation without parent node, use {@link #createNodes(TreeItem, File) createNodes}
	 * method instead.
	 * @param parent The parent node to initiate and create children nodes to
	 * @param file The file corresponding to the node, might be unnecessary due to changes
	 * in FileTreeItem-structure
	 */
	public static void createParentNode(TreeItem<FileTreeItem> parent, File file) {
		if (parent == null || file == null) {
			return;
		}
		
		if (fileExistsInTree(file, parent)) {
			return;
		}
		
		int type = calculateType(parent, file);

		TreeItem<FileTreeItem> item = new TreeItem<>(new FileTreeItem(file, type));
		parent.getChildren().add(item);
		parent.getChildren().sort((a, b) -> {
			try {
				var fa = (TreeItem<FileTreeItem>) a;
				var fb = (TreeItem<FileTreeItem>) b;
				
				return fa.getValue().getFile().getName().compareToIgnoreCase(fb.getValue().getFile().getName());
			}
			catch (ClassCastException ex) {
				return 0;
			}
		});
		
		if (file.isDirectory()) {
			createNodes(item, file);
		}
	}
	
	/**
	 * Updates the corresponding file to all the children of a node.
	 * @param parent The parent node to update children nodes
	 * @param file The new corresponding file
	 */
	public static void changeFileForNodes(TreeItem<FileTreeItem> parent, File file) {
		if (file.listFiles() == null) {
			return;
		}
		File[] files = file.listFiles();
		
		for (TreeItem<FileTreeItem> item : parent.getChildren()) {
			if(files != null) {
				for (File entry : files) {
					if (entry.getName().equals(item.getValue().getName())) {
						item.getValue().setFile(entry);
					}
				}
				if (item.getValue().getFile().isDirectory()) {
					changeFileForNodes(item, item.getValue().getFile());
				}
			}
		}
	}
	
	/**
	 * Traverses the specified root and returns the first tree item containing the given file.
	 * @param root The root tree item to begin searching through.
	 * @param file The file to search for.
	 * @return The {@link TreeItem<FileTreeItem>} that contains {@link file}, null if none was found.
	 * @author Pontus Laos
	 */
	public static TreeItem<FileTreeItem> getTreeItemFromFile(TreeItem<FileTreeItem> root, File file) {
		if (root == null || file == null) {
			return null;
		}
		
		if (root.getValue().getFile().getAbsolutePath().equals(file.getAbsolutePath())) {
			return root;
		}
		
		for (var foo : root.getChildren()) {
			var bar = getTreeItemFromFile(foo, file);
			
			if (bar != null && bar.getValue().getFile().getAbsolutePath().equals(file.getAbsolutePath())) {
				return bar;
			}
		}
		
		return null;
	}
	
	/**
	 * Searches the children of the given root and removes the one that contains the given file.
	 * @param root The root element from which to begin searching.
	 * @param file The file to search for.
	 * @return True if an element was removed, else false.
	 * @author Pontus Laos
	 */
	public static boolean removeFromFile(TreeItem<FileTreeItem> root, File file) {
		if (file == null) {
			return false;
		}
		
		var item = getTreeItemFromFile(root, file);
		
		if (item != null) {
			boolean removed = root.getChildren().remove(item);
			System.out.println("removed: " + removed);
			return removed;
		}
		
		System.out.println("not removed");
		return false;
	}
	
	/**
	 * Calculates the type of the tree node; workspace, package, class or source-folder.
	 * @param parent Nodes parent node
	 * @return An integer representing the node type.
	 */
	private static int calculateType(TreeItem<FileTreeItem> parent, File file) {
		int type = 0;
		
		String itemName = file.getName();
		ProjectFile projectFile = new ProjectFile(file);
		
		//Project
		if (projectFile.getMetadata() != null) {
			type = FileTreeItem.PROJECT;
		}
		//Package
		else if (parent.getValue().getName().equals("src") && file.isDirectory()) {
			type = FileTreeItem.PACKAGE;
		}
		else if (itemName.equals("src")) {
			type = FileTreeItem.SRC;
		}
		//Folder
		else if (projectFile.getMetadata() == null && file.isDirectory()) {
			type = FileTreeItem.FOLDER;
		}

		//Java-file
		else if (itemName.endsWith(".java")) {
			type = FileTreeItem.CLASS;
		} 
		//Text-file
		else if (itemName.endsWith(".txt")) {
			type = FileTreeItem.FILE;
		}
		else if (file.isFile() && itemName.indexOf('.') == -1) {
			type = FileTreeItem.FILE;
		} else {
			type = FileTreeItem.INCOMPATIBLE;
		}
		
		return type;
	}
	
	/**
	 * Checks if the given file is present in the tree hierarchy from the given root.
	 * @param file The file to check if it exists.
	 * @param root The root tree item to search through.
	 * @return True if any tree item under root contains the given file, else false.
	 * @author Pontus Laos
	 */
	private static boolean fileExistsInTree(File file, TreeItem<FileTreeItem> root) {
		if (root == null || file == null) {
			return false;
		}

		File rootFile = root.getValue().getFile();
		
		if (rootFile.getAbsolutePath().contentEquals(file.getAbsolutePath())) {
			return true;
		}
		
		for (var child : root.getChildren()) {

			if (fileExistsInTree(file, child)) {
				return true;
			}
		}
		
		return false;
	}
}
