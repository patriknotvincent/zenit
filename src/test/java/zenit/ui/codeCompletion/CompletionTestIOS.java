package zenit.ui.codeCompletion;

import javafx.scene.input.KeyCode;
import org.junit.jupiter.api.Test;
import org.testfx.util.WaitForAsyncUtils;
import zenit.ZenithTestBase;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

class CompletionTestIOS extends ZenithTestBase {
    private final String String_Variable_Test = "String testString = null;";
    private final String Create_Metod = "public void testAutoComplete(){";


    /**
     * Mac Test
     * Kräver att det finns en folder vid namn testa
     */
    @Test
    void createJavaFile() throws IOException {
        rightClickOn("testa");
        clickOn("New...");
        clickOn("New class");
        write("testAutoComplete1");
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
        write("String[] sArr = new String[3];");
        write("\n");
        write(Create_Metod);
    }

    @Test
    void TestingString(){
        write("\n");
        write("tes");
        WaitForAsyncUtils.sleep(1, TimeUnit.SECONDS);
        clickOn("testString (v)");
        write(";");
    }

    @Test
    void TestingInt() {
        write("\n");
        write("Num");
        WaitForAsyncUtils.sleep(1, TimeUnit.SECONDS);
        clickOn("Number (v)");
        write(";");
    }

    @Test
    void TestingStringArray(){
        write("\n");
        write("sA");
        WaitForAsyncUtils.sleep(1, TimeUnit.SECONDS);
        clickOn("String[] sArr (v)");
        write(";");
    }

    @Test
    void TestingSimilarName(){
        clickOn("String[] sArr = new String[3];");
        press(KeyCode.RIGHT);
        write("\n");
        write("int NumberOfTests;");
        WaitForAsyncUtils.sleep(1, TimeUnit.SECONDS);
        clickOn("NumberOfTests (v)");
        write(";");
        write("}");

    }

}