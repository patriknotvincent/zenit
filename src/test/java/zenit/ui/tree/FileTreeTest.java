package zenit.ui.tree;
import javafx.scene.control.TabPane;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import org.junit.jupiter.api.Test;
import org.testfx.api.FxRobotException;
import zenit.FileFinder;
import zenit.ZenithTestBase;
import zenit.ui.FileTab;

import java.io.IOException;
import static junit.framework.Assert.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FileTreeTest extends ZenithTestBase {

    /* <------------------------- TAGGED ELEMENTS IN FXML -------------------------> */

    private final String TREE_LIST = "#treeView";
    private final String FILE = "File";
    private final String NEW_FILE = "#newFile";
    private final String NEW_TAB = "#newTab";

    /* <------------------------ UNTAGGED ELEMENTS IN FXML ------------------------> */
    private final String PERSISTENT_CLASS_FILE = "JavaTest.java";
    private final String PERSISTENT_PACKAGE_NAME = "JTest";

    private final String CLASS_NAME = "Test.java";
    private final String INTERFACE_NAME = "ITest.java";
    private final String FILE_NAME = "File name";
    private final String PACKAGE_NAME = "Package name";
    private final String DIRECTORY = "TestProject";
    private final String SOURCE = "src";
    private final String NEW = "New...";
    private final String NEW_CLASS = "New class";
    private final String NEW_INTERFACE = "New interface";
    private final String  NEW_PACKAGE = "New package";
    private final String NEW_CLASS_NAME = "NewTest";
    private final String NEW_INTERFACE_NAME = "ITest";
    private final String NEW_PACKAGE_NAME = "PTest";
    private final String CREATE = "OK";
    private final String CLASS_TO_DELETE = "Delete \"" + CLASS_NAME + "\"";
    private final String INTERFACE_TO_DELETE = "Delete \"" + INTERFACE_NAME + "\"";
    private final String PACKAGE_TO_DELETE = "Delete \"" + NEW_PACKAGE_NAME + "\"";
    private final String SIDE_BAR_MENU_NEW = "#sideBarMenuNew";

    /* <---------------------------------------------------------------------------> */

    /**
     * This test creates a new class in the project directory.
     */
    @Test
    void createClass() throws IOException {
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
        doubleClickOn(SOURCE);
        clickOn(NEW_CLASS_NAME + ".java");
    }

    /**
     * This test tests the settings window for a projekt
     */
    @Test
    void openSettingsWindow(){
        rightClickOn("TestProjekt");
        //moveTo("Properties");
        clickOn("Properties");


    }


    /**
     * This test creates a new Interface in the project directory.
     */
    @Test
    void createInterface() throws IOException {
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
        clickOn(NEW_INTERFACE_NAME + ".java");
    }
    /**
     * This test creates a new Package in the project directory.
     */
    @Test
    void createPackage(){
        doubleClickOn(DIRECTORY);
        rightClickOn(SOURCE);
        moveTo(NEW);
        moveTo(NEW_CLASS);
        moveTo(NEW_PACKAGE);
        clickOn(NEW_PACKAGE);
        doubleClickOn(PACKAGE_NAME);
        for (int i = 0; i < PACKAGE_NAME.length(); i++) {
            push(KeyCode.BACK_SPACE);
        }
        write(NEW_PACKAGE_NAME);
        clickOn(CREATE);
        doubleClickOn(SOURCE);
        clickOn(NEW_PACKAGE_NAME);
    }

    /**
     * Test for moving a file from one directory to another.
     * Make sure the Class file to be moved is in src and not in the package
     */
    @Test
    void changeFileForNodes() {
        doubleClickOn(DIRECTORY);
        doubleClickOn(SOURCE);
        drag(PERSISTENT_CLASS_FILE).moveTo(PERSISTENT_PACKAGE_NAME).drop();
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
     */
    @Test
    void openClassFile() {
        doubleClickOn(DIRECTORY);
        doubleClickOn(SOURCE);
        moveTo(PERSISTENT_CLASS_FILE);
        doubleClickOn(PERSISTENT_CLASS_FILE);
        doubleClickOn(DIRECTORY);
    }
    /**
     * This method will remove a Class file from the directory and verifies that it was removed.
     */
    @Test
    void removeClassFile() throws IOException {
        doubleClickOn(DIRECTORY);
        doubleClickOn(SOURCE);
        moveTo(CLASS_NAME);
        rightClickOn(CLASS_NAME);
        clickOn(CLASS_TO_DELETE);
        assertThrows(FxRobotException.class, () ->{
            clickOn(CLASS_NAME);
        });
    }
    /**
     * This method will remove a Interface file from the directory and verifies that it was removed.
     */
    @Test
    void removeInterfaceFile() throws IOException {
        doubleClickOn(DIRECTORY);
        doubleClickOn(SOURCE);
        moveTo(INTERFACE_NAME);
        rightClickOn(INTERFACE_NAME);
        clickOn(INTERFACE_TO_DELETE);
        assertThrows(FxRobotException.class, () -> {
            clickOn(INTERFACE_NAME);
        });
    }
    /**
     * This method will remove a Package from the directory and verifies that it was removed.
     */
    @Test
    void removePackage() throws IOException{
        doubleClickOn(DIRECTORY);
        doubleClickOn(SOURCE);
        moveTo(NEW_PACKAGE_NAME);
        rightClickOn(NEW_PACKAGE_NAME);
        clickOn(PACKAGE_TO_DELETE);
        assertThrows(FxRobotException.class, () -> {
            clickOn(NEW_PACKAGE_NAME);
        });
    }
}