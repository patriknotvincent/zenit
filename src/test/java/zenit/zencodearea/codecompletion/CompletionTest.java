package zenit.zencodearea.codecompletion;

import org.junit.jupiter.api.Test;
import zenit.ZenithTestBase;

import java.io.IOException;

class CompletionTest extends ZenithTestBase {
    private final String MENU_FILE                      = "SearchTest.java";
    private final String CREATION_MENU_CONFIRM          = "#createButton";
    private final String SUB_MENU_FILE_NEW              = "New...";
    private final String SUB_MENU_FILE_NEW_NEW_TAB      = "New tab";
    private final String SUB_MENU_FILE_NEW_NEW_FILE     = "New file";
    private final String TEST_FILE_NAME = "CreateTest";
    private final String String_Variable_Test = "String testString";
    private final String Auto_Compleate_Test = "tes";

    private final String Create_Metod = "public void testAutoComplete(){ \n \n }";


    /**
     *Mac Test
     */
    @Test
    void createJavaFile() throws IOException {
        rightClickOn("testa");
        clickOn("New...");
        clickOn("New class");
        write("testAutoComplete");
        clickOn("OK");
    }

    @Test
    void initiateVariables() throws IOException {
        clickOn();//pilen ner
        clickOn("testAutoComplete");
        clickOn("2");
        write(String_Variable_Test);
        write("\n");
        write("int Number");
        write("\n");
        write("int NumberOfTests");
        write("\n");
        write("String[] sArr");


        //assertTrue(FileFinder.assertFileOrFolderExists(TEST_FILE_NAME + ".java"));

    }
    @Test
    void createMethod(){
        clickOn();//Tryck på tom rad
        write("\n"); //Tryck space
        write(Create_Metod);
        write("\n"); //Tryck space
        write("\n"); //Tryck space
        write("\n"); //Tryck space
        write("}");
    }

    @Test
    void TestingString(){

    }

    @Test
    void TestingInt(){

    }

    @Test
    void TestingStringArray(){

    }

    @Test
    void TestingSimilarName(){

    }





}