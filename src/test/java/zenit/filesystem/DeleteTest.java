package zenit.filesystem;

import zenit.ZenithTestBase;
import org.junit.jupiter.api.Test;

import zenit.FileFinder;
import zenit.ZenithTestBase;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.testfx.api.FxAssert.verifyThat;

public class DeleteTest extends ZenithTestBase {

    /* <------------------------- TAGGED ELEMENTS IN FXML -------------------------> */

    private final String TREE_LIST = "#treeView";
    private final String MENU_FILE = "File";
    private final String NEW_TAB = "#newTab";

    /* <------------------------ UNTAGGED ELEMENTS IN FXML ------------------------> */

    private final String SUB_MENU_FILE_NEW = "New...";
    private final String SUB_MENU_FILE_NEW_NEW_TAB      = "New tab";
    private final String SUB_MENU_FILE_NEW_NEW_FILE     = "New file";
    private final String SUB_MENU_FILE_NEW_NEW_FOLDER   = "New folder";
    private final String SUB_MENU_FILE_NEW_NEW_PROJECT  = "New Project";

    private final String CREATION_MENU_CONFIRM = "Create";

    private final String FILE_NAME = "File name";
    private final String DIRECTORY = "ZenitTEst";
    private final String SOURCE = "src";
    private final String NEW_CLASS = "New class";
    private final String NEW_INTERFACE = "New interface";
    private final String NEW_CLASS_NAME = "Test";
    private final String NEW_INTERFACE_NAME = "ITest";
    private final String SIDE_BAR_MENU_NEW = "#sideBarMenuNew";

    /* <---------------------------------------------------------------------------> */

    void deleteTestFileJava() {

    }

    void deleteTestFileText() {

    }

}
