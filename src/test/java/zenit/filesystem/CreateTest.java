package zenit.filesystem;

import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import org.junit.jupiter.api.Test;

import zenit.FileFinder;
import zenit.ZenithTestBase;

import java.io.IOException;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CreateTest extends ZenithTestBase {

    /* <------------------------- TAGGED ELEMENTS IN FXML -------------------------> */

    private final String TREE_LIST                      = "#treeView";
    private final String MENU_FILE                      = "File";
    private final String NEW_TAB                        = "#newTab";

    private final String CREATION_MENU_CONFIRM          = "#createButton";
    private final String CREATION_MENU_CANCEL           = "#cancelButton";
    private final String CREATION_MENU_FILE_SELECTOR    = "#fileEnding";
    private final String CREATION_MENU_FILE_NAME_FIELD  = "#tfName";

    /* <------------------------ UNTAGGED ELEMENTS IN FXML ------------------------> */

    private final String SUB_MENU_FILE_NEW              = "New...";
    private final String SUB_MENU_FILE_NEW_NEW_TAB      = "New tab";
    private final String SUB_MENU_FILE_NEW_NEW_FILE     = "New file";
    private final String SUB_MENU_FILE_NEW_NEW_FOLDER   = "New folder";
    private final String SUB_MENU_FILE_NEW_NEW_PROJECT  = "New Project";
    private final String SUB_MENU_FILE_NEW_NEW_PACKAGE  = "New folder";

    /* <---------------------------------------------------------------------------> */

    private final String TEST_FILE_NAME = "CreateTest";
    private final String TEST_PACKAGE_NAME = "TestPackage";
    private String BOUNDARY_TEST_FILE_NAME;

    /**
     * Some of the below test-methods actually create files on the host computer. This method erases the created file
     * @param fileName Name of the file to be deleted.
     */
    private void deleteTestFile(String fileName) {
        try {
            rightClickOn(fileName);
            clickOn("Delete \"" + fileName + "\"");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void deleteTestPackage() {
        try {
            rightClickOn(TEST_PACKAGE_NAME);
            clickOn("Delete \"" + TEST_PACKAGE_NAME + "\"");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void getWorkspace() {

    }

    @Test
    void createJavaFile() throws IOException {
        clickOn(MENU_FILE);
        clickOn(SUB_MENU_FILE_NEW);
        moveTo(SUB_MENU_FILE_NEW_NEW_TAB);
        clickOn(SUB_MENU_FILE_NEW_NEW_FILE);
        write(TEST_FILE_NAME);
        clickOn(CREATION_MENU_CONFIRM);
        assertTrue(FileFinder.assertFileOrFolderExists(TEST_FILE_NAME + ".java"));
        deleteTestFile(TEST_FILE_NAME + ".java");
    }

    @Test
    void createTextFile() throws IOException {
        clickOn(MENU_FILE);
        clickOn(SUB_MENU_FILE_NEW);
        moveTo(SUB_MENU_FILE_NEW_NEW_TAB);
        clickOn(SUB_MENU_FILE_NEW_NEW_FILE);
        write(TEST_FILE_NAME);
        clickOn(CREATION_MENU_FILE_SELECTOR);
        type(KeyCode.UP);
        type(KeyCode.ENTER);
        clickOn(CREATION_MENU_CONFIRM);
        assertTrue(FileFinder.assertFileOrFolderExists(TEST_FILE_NAME + ".txt"));
        deleteTestFile(TEST_FILE_NAME + ".txt");
    }

    /**
     * Boundary tests for creating a new file with max length of strings.
     *
     * Test for 257 characters
     * @throws IOException from FileFinder.walkFileTree()
     */

    @Test
    void testCreateFileCharacterBoundaryOver() throws IOException {
        char[] arrChar = new char[257];
        Arrays.fill(arrChar, 'a');
        BOUNDARY_TEST_FILE_NAME = String.valueOf(arrChar);
        clickOn(MENU_FILE);
        clickOn(SUB_MENU_FILE_NEW);
        moveTo(SUB_MENU_FILE_NEW_NEW_TAB);
        clickOn(SUB_MENU_FILE_NEW_NEW_FILE);
        Node foo = find(CREATION_MENU_FILE_NAME_FIELD);
        ((TextField) foo).setText(BOUNDARY_TEST_FILE_NAME);
        clickOn(CREATION_MENU_CONFIRM);
        assertFalse(FileFinder.assertFileOrFolderExists(BOUNDARY_TEST_FILE_NAME + ".java"));
    }
    /**
     * Test for exactly 256 characters, should return false.
     * @throws IOException from FileFinder.walkFileTree()
     */
    @Test
    void testCreateFileCharacterBoundaryMiddle() throws IOException {
        char[] arrChar = new char[256];
        Arrays.fill(arrChar, 'a');
        BOUNDARY_TEST_FILE_NAME = String.valueOf(arrChar);
        clickOn(MENU_FILE);
        clickOn(SUB_MENU_FILE_NEW);
        moveTo(SUB_MENU_FILE_NEW_NEW_TAB);
        clickOn(SUB_MENU_FILE_NEW_NEW_FILE);
        Node foo = find(CREATION_MENU_FILE_NAME_FIELD);
        ((TextField) foo).setText(BOUNDARY_TEST_FILE_NAME);
        clickOn(CREATION_MENU_CONFIRM);
        assertTrue(FileFinder.assertFileOrFolderExists(BOUNDARY_TEST_FILE_NAME + ".java"));
        deleteTestFile(BOUNDARY_TEST_FILE_NAME + ".java");
    }

    /**
     * Test for 255 characters, IE below the limit of Windows max character limit for a file.
     * @throws IOException from FileFinder.walkFileTree()
     */
    @Test
    void testCreateFileCharacterBoundaryLower() throws IOException {
        char[] arrChar = new char[255];
        Arrays.fill(arrChar, 'a');
        BOUNDARY_TEST_FILE_NAME = String.valueOf(arrChar);
        clickOn(MENU_FILE);
        clickOn(SUB_MENU_FILE_NEW);
        moveTo(SUB_MENU_FILE_NEW_NEW_TAB);
        clickOn(SUB_MENU_FILE_NEW_NEW_FILE);
        Node foo = find(CREATION_MENU_FILE_NAME_FIELD);
        ((TextField) foo).setText(BOUNDARY_TEST_FILE_NAME);
        clickOn(CREATION_MENU_CONFIRM);
        assertTrue(FileFinder.assertFileOrFolderExists(BOUNDARY_TEST_FILE_NAME + ".java"));
        deleteTestFile(BOUNDARY_TEST_FILE_NAME + ".java");
    }

    /**
     * Test for empty file name.
     * @throws IOException from FileFinder.walkFileTree()
     */

    @Test
    void testCreateFileCharacterBoundaryZeroValue() throws IOException {
        char[] arrChar = new char[0];
        BOUNDARY_TEST_FILE_NAME = String.valueOf(arrChar);
        clickOn(MENU_FILE);
        clickOn(SUB_MENU_FILE_NEW);
        moveTo(SUB_MENU_FILE_NEW_NEW_TAB);
        clickOn(SUB_MENU_FILE_NEW_NEW_FILE);
        Node foo = find(CREATION_MENU_FILE_NAME_FIELD);
        ((TextField) foo).setText(BOUNDARY_TEST_FILE_NAME);
        clickOn(CREATION_MENU_CONFIRM);
        assertFalse(FileFinder.assertFileOrFolderExists(BOUNDARY_TEST_FILE_NAME + ".java"));
    }

    /**
     * Tests for illegal characters on file-creation for Windows.
     *
     * Test for question mark in file name
     * @throws IOException from FileFinder.walkFileTree()
     */

    @Test
    void testCreateFileIllegalCharactersQuestionMark() throws IOException {
        char[] arrChar = new char[10];
        Arrays.fill(arrChar, 'a');
        BOUNDARY_TEST_FILE_NAME = String.valueOf(arrChar);
        BOUNDARY_TEST_FILE_NAME += '?';
        clickOn(MENU_FILE);
        clickOn(SUB_MENU_FILE_NEW);
        moveTo(SUB_MENU_FILE_NEW_NEW_TAB);
        clickOn(SUB_MENU_FILE_NEW_NEW_FILE);
        Node foo = find(CREATION_MENU_FILE_NAME_FIELD);
        ((TextField) foo).setText(BOUNDARY_TEST_FILE_NAME);
        clickOn(CREATION_MENU_CONFIRM);
        assertFalse(FileFinder.assertFileOrFolderExists(BOUNDARY_TEST_FILE_NAME + ".java"));
    }

    /**
     * Test for backslash (\) in file name
     * @throws IOException from FileFinder.walkFileTree()
     */

    @Test
    void testCreateFileIllegalCharactersBackslash() throws IOException {
        char[] arrChar = new char[10];
        Arrays.fill(arrChar, '\\');
        BOUNDARY_TEST_FILE_NAME = String.valueOf(arrChar);
        BOUNDARY_TEST_FILE_NAME += '?';
        clickOn(MENU_FILE);
        clickOn(SUB_MENU_FILE_NEW);
        moveTo(SUB_MENU_FILE_NEW_NEW_TAB);
        clickOn(SUB_MENU_FILE_NEW_NEW_FILE);
        Node foo = find(CREATION_MENU_FILE_NAME_FIELD);
        ((TextField) foo).setText(BOUNDARY_TEST_FILE_NAME);
        clickOn(CREATION_MENU_CONFIRM);
        assertFalse(FileFinder.assertFileOrFolderExists(BOUNDARY_TEST_FILE_NAME + ".java"));
    }

    /**
     * Test for Forward slash (/) in file name
     * @throws IOException from FileFinder.walkFileTree()
     */

    @Test
    void testCreateFileIllegalCharactersForwardSlash() throws IOException {
        char[] arrChar = new char[10];
        Arrays.fill(arrChar, '/');
        BOUNDARY_TEST_FILE_NAME = String.valueOf(arrChar);
        BOUNDARY_TEST_FILE_NAME += '?';
        clickOn(MENU_FILE);
        clickOn(SUB_MENU_FILE_NEW);
        moveTo(SUB_MENU_FILE_NEW_NEW_TAB);
        clickOn(SUB_MENU_FILE_NEW_NEW_FILE);
        Node foo = find(CREATION_MENU_FILE_NAME_FIELD);
        ((TextField) foo).setText(BOUNDARY_TEST_FILE_NAME);
        clickOn(CREATION_MENU_CONFIRM);
        assertFalse(FileFinder.assertFileOrFolderExists(BOUNDARY_TEST_FILE_NAME + ".java"));
    }

    /**
     * Test for Star (*) in file name
     * @throws IOException from FileFinder.walkFileTree()
     */

    @Test
    void testCreateFileIllegalCharactersStar() throws IOException {
        char[] arrChar = new char[10];
        Arrays.fill(arrChar, '/');
        BOUNDARY_TEST_FILE_NAME = String.valueOf(arrChar);
        BOUNDARY_TEST_FILE_NAME += '*';
        clickOn(MENU_FILE);
        clickOn(SUB_MENU_FILE_NEW);
        moveTo(SUB_MENU_FILE_NEW_NEW_TAB);
        clickOn(SUB_MENU_FILE_NEW_NEW_FILE);
        Node foo = find(CREATION_MENU_FILE_NAME_FIELD);
        ((TextField) foo).setText(BOUNDARY_TEST_FILE_NAME);
        clickOn(CREATION_MENU_CONFIRM);
        assertFalse(FileFinder.assertFileOrFolderExists(BOUNDARY_TEST_FILE_NAME + ".java"));
    }

    /**
     * Test for Colon (:) in file name
     * @throws IOException from FileFinder.walkFileTree()
     */

    @Test
    void testCreateFileIllegalCharactersColon() throws IOException {
        char[] arrChar = new char[10];
        Arrays.fill(arrChar, '/');
        BOUNDARY_TEST_FILE_NAME = String.valueOf(arrChar);
        BOUNDARY_TEST_FILE_NAME += ':';
        clickOn(MENU_FILE);
        clickOn(SUB_MENU_FILE_NEW);
        moveTo(SUB_MENU_FILE_NEW_NEW_TAB);
        clickOn(SUB_MENU_FILE_NEW_NEW_FILE);
        Node foo = find(CREATION_MENU_FILE_NAME_FIELD);
        ((TextField) foo).setText(BOUNDARY_TEST_FILE_NAME);
        clickOn(CREATION_MENU_CONFIRM);
        assertFalse(FileFinder.assertFileOrFolderExists(BOUNDARY_TEST_FILE_NAME + ".java"));
    }

    /**
     * Test for Wall(???) (|) in file name
     * @throws IOException from FileFinder.walkFileTree()
     */

    @Test
    void testCreateFileIllegalCharactersWall() throws IOException {
        char[] arrChar = new char[10];
        Arrays.fill(arrChar, '/');
        BOUNDARY_TEST_FILE_NAME = String.valueOf(arrChar);
        BOUNDARY_TEST_FILE_NAME += '|';
        clickOn(MENU_FILE);
        clickOn(SUB_MENU_FILE_NEW);
        moveTo(SUB_MENU_FILE_NEW_NEW_TAB);
        clickOn(SUB_MENU_FILE_NEW_NEW_FILE);
        Node foo = find(CREATION_MENU_FILE_NAME_FIELD);
        ((TextField) foo).setText(BOUNDARY_TEST_FILE_NAME);
        clickOn(CREATION_MENU_CONFIRM);
        assertFalse(FileFinder.assertFileOrFolderExists(BOUNDARY_TEST_FILE_NAME + ".java"));
    }

    /**
     * Test for Forward (>) in file name
     * @throws IOException from FileFinder.walkFileTree()
     */

    @Test
    void testCreateFileIllegalCharactersForward() throws IOException {
        char[] arrChar = new char[10];
        Arrays.fill(arrChar, '/');
        BOUNDARY_TEST_FILE_NAME = String.valueOf(arrChar);
        BOUNDARY_TEST_FILE_NAME += '>';
        clickOn(MENU_FILE);
        clickOn(SUB_MENU_FILE_NEW);
        moveTo(SUB_MENU_FILE_NEW_NEW_TAB);
        clickOn(SUB_MENU_FILE_NEW_NEW_FILE);
        Node foo = find(CREATION_MENU_FILE_NAME_FIELD);
        ((TextField) foo).setText(BOUNDARY_TEST_FILE_NAME);
        clickOn(CREATION_MENU_CONFIRM);
        assertFalse(FileFinder.assertFileOrFolderExists(BOUNDARY_TEST_FILE_NAME + ".java"));
    }

    /**
     * Test for Backward (<) in file name
     * @throws IOException from FileFinder.walkFileTree()
     */

    @Test
    void testCreateFileIllegalCharactersBackward() throws IOException {
        char[] arrChar = new char[10];
        Arrays.fill(arrChar, '/');
        BOUNDARY_TEST_FILE_NAME = String.valueOf(arrChar);
        BOUNDARY_TEST_FILE_NAME += '>';
        clickOn(MENU_FILE);
        clickOn(SUB_MENU_FILE_NEW);
        moveTo(SUB_MENU_FILE_NEW_NEW_TAB);
        clickOn(SUB_MENU_FILE_NEW_NEW_FILE);
        Node foo = find(CREATION_MENU_FILE_NAME_FIELD);
        ((TextField) foo).setText(BOUNDARY_TEST_FILE_NAME);
        clickOn(CREATION_MENU_CONFIRM);
        assertFalse(FileFinder.assertFileOrFolderExists(BOUNDARY_TEST_FILE_NAME + ".java"));
    }

    /**
     * Test for Quote-mark (") in file name
     * @throws IOException from FileFinder.walkFileTree()
     */

    @Test
    void testCreateFileIllegalCharactersQuoteMark() throws IOException {
        char[] arrChar = new char[10];
        Arrays.fill(arrChar, '/');
        BOUNDARY_TEST_FILE_NAME = String.valueOf(arrChar);
        BOUNDARY_TEST_FILE_NAME += '"';
        clickOn(MENU_FILE);
        clickOn(SUB_MENU_FILE_NEW);
        moveTo(SUB_MENU_FILE_NEW_NEW_TAB);
        clickOn(SUB_MENU_FILE_NEW_NEW_FILE);
        Node foo = find(CREATION_MENU_FILE_NAME_FIELD);
        ((TextField) foo).setText(BOUNDARY_TEST_FILE_NAME);
        clickOn(CREATION_MENU_CONFIRM);
        assertFalse(FileFinder.assertFileOrFolderExists(BOUNDARY_TEST_FILE_NAME + ".java"));
    }

    /**
     * Tests for illegal characters on file-creation on Mac OS X.
     *
     * Test for colon (:), the only illegal character in Mac OS X file-names //TODO Är det? Och testet behövs skrivas.
     * @throws IOException from FileFinder.walkFileTree()
     */

    void testIllegalCharactersMacOsX() {
        // Something something like above methods but the test should only pass on Mac OS X computers
    }

    /**
     * Tests for creating packages with or without illegal characters
     *
     * Test for creating a package with valid input
     * @throws IOException from FileFinder.walkFileTree()
     */

    @Test
    void createPackage() throws IOException {
        clickOn(MENU_FILE);
        clickOn(SUB_MENU_FILE_NEW);
        moveTo(SUB_MENU_FILE_NEW_NEW_TAB);
        clickOn(SUB_MENU_FILE_NEW_NEW_PACKAGE);
        write(TEST_PACKAGE_NAME);
        clickOn("Create");
        assertTrue(FileFinder.assertFileOrFolderExists(TEST_PACKAGE_NAME));
        deleteTestPackage();
    }

    @Test
    void createProject() {
    }

    @Test
    void changeWorkspace() {
    }

    @Test
    void importProject() {
    }

    @Test
    void addInternalLibraries() {
    }

    @Test
    void removeInternalLibraries() {
    }

    @Test
    void addExternalLibraries() {
    }

    @Test
    void removeExternalLibraries() {
    }

    @Test
    void updateMetadata() {
    }

    @Test
    void changeDirectory() {
    }

}