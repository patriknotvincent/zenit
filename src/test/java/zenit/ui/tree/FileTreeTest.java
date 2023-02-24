package zenit.ui.tree;

import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.input.KeyCode;
import org.junit.jupiter.api.Test;
import org.testfx.util.WaitForAsyncUtils;
import zenit.ZenithTestBase;
import static org.testfx.api.FxAssert.verifyThat;

class FileTreeTest extends ZenithTestBase {
    // Instance variables with TAGS.
    private final String TREE_LIST = "#treeView";
    private final String FILE = "File";
    private final String NEW_FILE = "#newFile";
    private final String NEW_TAB = "#newTab";

    // Instance variables with WITHOUT TAGS.
    private final String CLASS_NAME = "Test.java";
    private final String FILE_NAME = "File name";
    private final String DIRECTORY = "ZenitTEst";
    private final String SOURCE = "src";
    private final String NEW = "New...";
    private final String NEW_CLASS = "New class";
    private final String NEW_INTERFACE = "New interface";
    private final String NEW_CLASS_NAME = "Test";
    private final String NEW_INTERFACE_NAME = "ITest";
    private final String CREATE = "OK";
    private final String FILE_TO_DELETE = "Delete \"" + CLASS_NAME + "\"";
    private final String SIDE_BAR_MENU_NEW = "#sideBarMenuNew";

    /**
     * This test creates a new class in the project directory, badly... The reason for this is that there are GUI-
     * components that are built without FXML files which means we have no tags to make the test generic.
     */
    @Test
    void createClass() {
        doubleClickOn(DIRECTORY);
        rightClickOn(SOURCE);
        moveTo(NEW);
        moveTo(NEW_CLASS);
        clickOn(NEW_CLASS);
        clickOn(FILE_NAME);
        for (int i = 0; i < FILE_NAME.length(); i++) {
            push(KeyCode.BACK_SPACE);
        }
        write(NEW_CLASS_NAME);
        clickOn(CREATE);
        verifyThat(CLASS_NAME, (Label label) ->{
            String test = label.getText();
            test.contains(CLASS_NAME);
        });
        doubleClickOn(DIRECTORY);
    }
    @Test
    void createInterface(){
        doubleClickOn(DIRECTORY);
        rightClickOn(SOURCE);
        moveTo(NEW);
        moveTo(NEW_CLASS);
        moveTo(NEW_INTERFACE);
        clickOn(NEW_INTERFACE);
        clickOn(FILE_NAME);
        for (int i = 0; i < FILE_NAME.length(); i++) {
            push(KeyCode.BACK_SPACE);
        }
        write(NEW_INTERFACE_NAME);
        clickOn(CREATE);
        doubleClickOn(DIRECTORY);
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
     * This method will open a Class file specified in the instance variable.
     * TODO investigate why this doensn't work when entire test suite is run.
     */
    @Test
    void openClassFile() {
        WaitForAsyncUtils.waitForFxEvents();
        doubleClickOn(DIRECTORY);
        doubleClickOn(SOURCE);
        moveTo(CLASS_NAME);
        doubleClickOn(CLASS_NAME);
        doubleClickOn(DIRECTORY);

    }

    /**
     * This method will remove a Class file from the directory.
     */
    @Test
    void removeClassFile() {
        System.out.printf(FILE_TO_DELETE);
        doubleClickOn(DIRECTORY);
        sleepMe();
        doubleClickOn(SOURCE);
        sleepMe();
        moveTo(CLASS_NAME);
        sleepMe();
        rightClickOn(CLASS_NAME);
        sleepMe();
        clickOn(FILE_TO_DELETE);
    }

    void sleepMe(){
        sleep(1000);
    }
}