package zenit.ui.tree;

import javafx.scene.control.TreeView;
import org.junit.jupiter.api.Test;
import zenit.ZenithTestBase;
import zenit.ui.MainController;

import static org.testfx.api.FxAssert.verifyThat;

import static org.junit.jupiter.api.Assertions.*;

class FileTreeTest extends ZenithTestBase {
    private final String TREE_LIST = "#treeView";
    private final String FILE = "File";
    private final String NEW = "New...";
    private final String NEW_FILE= "#newFile";
    private final String NEW_TAB = "#newTab";
    private final String SIDE_BAR_MENU_NEW = "#sideBarMenuNew";

    @Test
    void createNodes() {

        clickOn("File");
        clickOn(SIDE_BAR_MENU_NEW);
        moveTo(NEW_TAB);
        clickOn(NEW_FILE);

        /*
        rightClickOn(TREE_LIST);
        clickOn("New...");
        clickOn("New class");

         */

    }

    @Test
    void createParentNode() {
    }

    @Test
    void changeFileForNodes() {
    }
    @Test
    void clickOnTreeView(){
        clickOn(TREE_LIST);
    }

    @Test
    void getTreeItemFromFile() {
        String listItem = "kuk.txt";
        doubleClickOn(listItem);
    }

    @Test
    void removeFromFile() {
    }
}