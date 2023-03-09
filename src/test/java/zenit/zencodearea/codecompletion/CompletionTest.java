package zenit.zencodearea.codecompletion;

import javafx.scene.input.KeyCode;
import org.junit.jupiter.api.Test;
import zenit.ZenithTestBase;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class CompletionTest extends ZenithTestBase {
    private final String MENU_FILE                      = "SearchTest.java";
    private final String CREATION_MENU_CONFIRM          = "#createButton";
    private final String SUB_MENU_FILE_NEW              = "New...";
    private final String SUB_MENU_FILE_NEW_NEW_TAB      = "New tab";
    private final String SUB_MENU_FILE_NEW_NEW_FILE     = "New file";
    private final String TEST_FILE_NAME = "CreateTest";
    private final String String_Variable_Test = "String testString;";

    private final String Create_Metod = "public void testAutoComplete(){";


    /**
     *Mac Test
     */
    @Test
    void createJavaFile() throws IOException {
        rightClickOn("testa");
        clickOn("New...");
        clickOn("New class");
        write("testAutoComplete2");
        clickOn("OK");
    }

    @Test
    void setUpTest() throws IOException {
        clickOn("{");
        press(KeyCode.RIGHT);
        write("\n");
        write(String_Variable_Test);
        write("\n");
        write("int Number = 0;");
        write("\n");
        write("String[] sArr = = new String[3];;");
        write("\n");
        write(Create_Metod);


        //assertTrue(FileFinder.assertFileOrFolderExists(TEST_FILE_NAME + ".java"));

    }

    @Test
    void TestingString(){
        write("\n");
        write("tes");
        //wait
        clickOn("testString (v)");
        write(";");
        write("\n");
        write("}");
    }

    @Test
    void TestingInt(){
        clickOn("testString;");
        write("\n");
        write("Numb");
        //wait
        clickOn("Number (v)");
        write(";");

    }

    @Test
    void TestingStringArray(){
        clickOn("Number;");
        write("\n");
        write("sA");
        //wait
        clickOn("String[] sArr (v)");
        write(";");
    }

    @Test
    void TestingSimilarName(){
        clickOn("int Number;");
        write("\n");
        write("int NumberOfTests;");
        clickOn("NumberOfTests (v)");

    }

}