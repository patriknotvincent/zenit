package zenit.ui.tree;

import javafx.scene.control.TreeView;
import javafx.scene.input.KeyCode;
import org.junit.jupiter.api.Test;
import org.testfx.util.WaitForAsyncUtils;
import zenit.ZenithTestBase;
import zenit.ui.MainController;

import static org.testfx.api.FxAssert.verifyThat;

import static org.junit.jupiter.api.Assertions.*;

class FileTreeTest extends ZenithTestBase {
    // Instance variables with TAGS.
    private final String TREE_LIST = "#treeView";
    private final String FILE = "File";
    private final String NEW_FILE = "#newFile";
    private final String NEW_TAB = "#newTab";

    // Instance variables with WITHOUT TAGS.
    private final String LIST_ITEM = "Test.java";
    private final String FILE_NAME = "File name";
    private final String DIRECTORY = "ZenitTEst";
    private final String SOURCE = "src";
    private final String NEW = "New...";
    private final String NEW_CLASS = "New class";
    private final String NEW_FILE_NAME = "Test";
    private final String CREATE = "OK";
    private final String FILE_TO_DELETE = "Delete \"" + LIST_ITEM + "\"";
    private final String SIDE_BAR_MENU_NEW = "#sideBarMenuNew";

    /**
     * This test creates a new class in the project directory, badly... The reason for this is that there are GUI-
     * components that are built without FXML files which means we have no tags to make the test generic.
     */
    @Test
    void createNodes() {
        doubleClickOn(DIRECTORY);
        rightClickOn(SOURCE);
        moveTo(NEW);
        moveTo(NEW_CLASS);
        clickOn(NEW_CLASS);
        clickOn(FILE_NAME);
        for (int i = 0; i < FILE_NAME.length(); i++) {
            push(KeyCode.BACK_SPACE);
        }
        write(NEW_FILE_NAME);
        clickOn(CREATE);
    }

    @Test
    void createParentNode() {
    }

    @Test
    void changeFileForNodes() {
    }

    /**
     * This method verifies that the treeView can be clicked on.
     */
    @Test
    void clickOnTreeView() {
        clickOn(TREE_LIST);
    }

    /**
     * This method verifies that a list is shown when the treeView is clicked on.
     */
    @Test
    void rightClickOnTreeView() {
        rightClickOn(TREE_LIST);
    }

    /**
     * This method will open a list item specified in the instance variable.
     * TODO investigate why this doensn't work when entire test suite is run.
     */
    @Test
    void getTreeItemFromFile() {
        WaitForAsyncUtils.waitForFxEvents();
        doubleClickOn(DIRECTORY);
        doubleClickOn(SOURCE);
        moveTo(LIST_ITEM);
        doubleClickOn(LIST_ITEM);
    }

    /**
     * This method will remove a file from the directory.
     */
    @Test
    void removeFromFile() {
        System.out.printf(FILE_TO_DELETE);
        doubleClickOn(DIRECTORY);
        doubleClickOn(SOURCE);
        moveTo(LIST_ITEM);
        rightClickOn(LIST_ITEM);
        clickOn(FILE_TO_DELETE);
    }
}