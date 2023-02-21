package zenit.ui.tree;

import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.*;
import javafx.util.Callback;
import zenit.ui.FileTab;
import zenit.ui.MainController;

import java.io.File;

public class FileCellFactory implements Callback<TreeView<FileTreeItem>, TreeCell<FileTreeItem>> {

    private TreeCell<FileTreeItem> dropZone;
    private TreeItem<FileTreeItem> draggedItem;

    private static DataFormat JAVA_FORMAT = new DataFormat("application/x-java-serialized-object");
    private static final String DROP_HINT_STYLE = "-fx-border-color: #eea82f; -fx-border-width: 2 2 2 2; " +
            "-fx-padding: 3 3 3 3";

    private MainController controller;

    public FileCellFactory(MainController controller) {
        this.controller = controller;
    }
    @Override
    public TreeCell<FileTreeItem> call(TreeView<FileTreeItem> treeView) {
        TreeCell<FileTreeItem> cell = new TreeCell<>() {
            @Override
            protected void updateItem(FileTreeItem item, boolean empty) {
                super.updateItem(item, empty);

                setGraphic(null);
                setText(null);
                setContextMenu(null);
                setTooltip(null);

                if(!empty) {
                    setGraphic(item.getIcon());
                    getGraphic();
                    setText(item.getName());
                }
            }
        };
        cell.setOnDragDetected((MouseEvent event) -> dragDetected(event, cell, treeView));
        cell.setOnDragOver((DragEvent event) -> dragOver(event, cell, treeView));
        cell.setOnDragDropped((DragEvent event) -> drop(event, cell, treeView));
        cell.setOnDragDone((DragEvent event) -> clearDropLocation());

        return cell;
    }

    private void dragDetected(MouseEvent event, TreeCell<FileTreeItem> treeCell, TreeView<FileTreeItem> treeView) {
        draggedItem = treeCell.getTreeItem();

        // root can't be dragged
        if (draggedItem.getParent() == null) return;
        if(draggedItem.getValue().getType() == FileTreeItem.PROJECT) return;
        if(draggedItem.getValue().getType() == FileTreeItem.PACKAGE) return;
        if(draggedItem.getValue().getType() == FileTreeItem.SRC) return;
        Dragboard db = treeCell.startDragAndDrop(TransferMode.MOVE);

        ClipboardContent content = new ClipboardContent();
        content.put(JAVA_FORMAT, draggedItem.getValue());
        db.setContent(content);
        db.setDragView(treeCell.snapshot(null, null));
        event.consume();
    }

    private void dragOver(DragEvent event, TreeCell<FileTreeItem> treeCell, TreeView<FileTreeItem> treeView) {
        if (!event.getDragboard().hasContent(JAVA_FORMAT)) return;
        TreeItem<FileTreeItem> thisItem = treeCell.getTreeItem();

        // can't drop on itself
        if (draggedItem == null || thisItem == null || thisItem == draggedItem) return;
        //can't drop on parent
        if(thisItem.getValue().equals(draggedItem.getParent().getValue())){
            clearDropLocation();
            return;
        }

        event.acceptTransferModes(TransferMode.MOVE);
        if(thisItem.getValue().getFile().isDirectory()){
            clearDropLocation();
            this.dropZone = treeCell;
            dropZone.setStyle(DROP_HINT_STYLE);
        }else{
            clearDropLocation();
            dropZone = null;
        }
    }

    private void drop(DragEvent event, TreeCell<FileTreeItem> treeCell, TreeView<FileTreeItem> treeView) {
        Dragboard db = event.getDragboard();
        boolean success = false;
        if (!db.hasContent(JAVA_FORMAT)) return;

        TreeItem<FileTreeItem> droppedOn = treeCell.getTreeItem();
        TreeItem<FileTreeItem> droppedItemParent = draggedItem.getParent();

        if(!droppedOn.getValue().getFile().isDirectory()){
            return;
        }

        // remove from previous location
        droppedItemParent.getChildren().remove(draggedItem);

        droppedOn.getChildren().add(draggedItem);
        FileTab tab = controller.getTabFromFile(draggedItem.getValue().getFile());
        String destPath = controller.moveFile(draggedItem.getValue().getFile(), droppedOn.getValue().getFile());
        //Update file path for dragged item and tab if open
        if(destPath != null){
            draggedItem.getValue().setFile(new File(destPath));
            //if tab is open
            if(tab != null && !draggedItem.getValue().getFile().isDirectory()){
                tab.setFile(draggedItem.getValue().getFile(), false);
                    controller.changePackageForOpenFile(tab);
            }else if(!draggedItem.getValue().getFile().isDirectory()){
                controller.changePackageForClosedFile(draggedItem.getValue().getFile());
            }
            success = true;
        }
        droppedOn.setExpanded(true);

        treeView.getSelectionModel().select(draggedItem);
        event.setDropCompleted(success);
    }

    private void clearDropLocation() {
        if (dropZone != null) dropZone.setStyle("");
    }
}
