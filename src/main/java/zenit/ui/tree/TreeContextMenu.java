package zenit.ui.tree;

import java.io.File;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import zenit.filesystem.ProjectFile;
import zenit.filesystem.helpers.CodeSnippets;
import zenit.ui.MainController;

/**
 * Class that extends {@link ContextMenu} with static menu items with dynamic
 * text. Also contains event handler.
 * @author Alexander Libot
 *
 */
public class TreeContextMenu extends ContextMenu implements EventHandler<ActionEvent>{
	
	private MainController controller;
	private TreeView<FileTreeItem> treeView;
	
	private Menu createItem = new Menu("New...");
	private MenuItem createClass = new MenuItem("New Class");
	private MenuItem createInterface = new MenuItem("New Interface");
	private MenuItem createPackage = new MenuItem("New Package");
	private MenuItem renameItem = new MenuItem("Rename");
	private MenuItem deleteItem = new MenuItem("Delete");
	private MenuItem importJar = new MenuItem("Import .jar");
	private MenuItem properties = new MenuItem("Properties");
	
	/**
	 * Creates a new {@link TreeContextMenu} that can manipulate a specific {@link
	 * TreeView TreeView} instance and call methods in a specific
	 * {@link zenit.ui.MainController MainController}
	 * @param controller The {@link zenit.ui.MainController MainController} instance where methods
	 * will be called
	 * @param treeView The {@link TreeView TreeView} instance which will
	 * be manipulated
	 */
	public TreeContextMenu(MainController controller, TreeView<FileTreeItem> treeView) {
		super();
		this.controller = controller;
		this.treeView = treeView;

		initContextMenu();
	}
	
	/**
	 * Updates the menu items with dynamic text.
	 * @param selectedNode The name of the node in the tree to be inserted dynamically
	 */
	private void setContext(String selectedNode) {
		String renameItemTitle = String.format("Rename \"%s\"", selectedNode);
		String deleteItemTitle = String.format("Delete \"%s\"", selectedNode);
		renameItem.setText(renameItemTitle);
		deleteItem.setText(deleteItemTitle);
				
		if (selectedNode.equals("src") && !createItem.getItems().contains(createPackage)) {
			createItem.getItems().add(createPackage);
		} else {
			createItem.getItems().remove(createPackage);
		}
		TreeItem<FileTreeItem> selectedItem = treeView.getSelectionModel().getSelectedItem();
		if (selectedItem.getValue().getType() == FileTreeItem.PROJECT) {
			getItems().add(importJar);
			getItems().add(properties);
		} else {
			getItems().remove(importJar);
			getItems().remove(properties);
		}
	}
	
	/**
	 * Overrides {@link ContextMenu#show(Node, double, double) show(...)} in
	 * {@link ContextMenu ContextMenu}. Dynamically updates the menu
	 * items before showing the context menu.
	 */
	@Override
	public void show(Node node, double x, double y) {
		TreeItem<FileTreeItem> selectedItem = treeView.getSelectionModel().getSelectedItem();
		
		if (selectedItem != null) {
			setContext(selectedItem.getValue().getName());
		}
		
		super.show(node, x, y);
	}
	
	/**
	 * Initializes the context menu by adding all menus and menu items and setting
	 * action listeners.
	 */
	private void initContextMenu() {
		createItem.getItems().add(createClass);
		createItem.getItems().add(createInterface);
		getItems().addAll(createItem, renameItem, deleteItem);
		createClass.setOnAction(this);
		createInterface.setOnAction(this);
		renameItem.setOnAction(this);
		deleteItem.setOnAction(this);
		createPackage.setOnAction(this);
		importJar.setOnAction(this);
		properties.setOnAction(this);
	}
	
	/**
	 * To create a new file, calls {@link zenit.ui.MainController#createFile(File, int)}
	 * @param typeCode The type of item to be created. Use constants from {@link 
	 * zenit.filesystem.helpers.CodeSnippets CodeSnippets}
	 */
	private void newFile(int typeCode) {
		TreeItem<FileTreeItem> parent = treeView.getSelectionModel().getSelectedItem();
		File newFile = controller.createFile(parent.getValue().getFile(), typeCode);
		if (newFile != null) {
			TreeItem<FileTreeItem> newItem = new TreeItem<>(new FileTreeItem(newFile,
					FileTreeItem.CLASS));
			parent.getChildren().add(newItem);
		}
	}

	/**
	 * Event handler for TreeContextMenu. Calls different methods in {@link main.java.zenit.ui.MainController
	 * MainController} depending on input.
	 */
	@Override
	public void handle(ActionEvent actionEvent) {
		TreeItem<FileTreeItem> selectedItem = treeView.getSelectionModel().getSelectedItem();
		File selectedFile = selectedItem.getValue().getFile();
		
		if (actionEvent.getSource().equals(createClass)) {
			newFile(CodeSnippets.CLASS);
		} else if (actionEvent.getSource().equals(createInterface)) {
			newFile(CodeSnippets.INTERFACE);
		} else if (actionEvent.getSource().equals(renameItem)) {
			File newFile = controller.renameFile(selectedFile);
			if (newFile != null) {
				selectedItem.getValue().setFile(newFile);
				selectedItem.getValue().setName(newFile.getName());
				FileTree.changeFileForNodes(selectedItem, selectedItem.getValue().getFile());
			}
		} else if (actionEvent.getSource().equals(deleteItem)) {
			controller.deleteFile(selectedFile);
			selectedItem.getParent().getChildren().remove(selectedItem);
		} else if (actionEvent.getSource().equals(createPackage)) {
			File packageFile = controller.newPackage(selectedFile);
			if (packageFile != null) {
				TreeItem<FileTreeItem> packageNode = new TreeItem<>(new FileTreeItem(packageFile,
						FileTreeItem.PACKAGE));
				selectedItem.getChildren().add(packageNode);
			}
		} else if (actionEvent.getSource().equals(importJar)) {
			ProjectFile projectFile = new ProjectFile(selectedFile.getPath());
			controller.chooseAndImportLibraries(projectFile);
		} else if (actionEvent.getSource().equals(properties) && selectedItem.getValue().getType() == FileTreeItem.PROJECT) {
			ProjectFile projectFile = new ProjectFile(selectedFile.getPath());
			controller.showProjectProperties(projectFile);
		}
	}
}
