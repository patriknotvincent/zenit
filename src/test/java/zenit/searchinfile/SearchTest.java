package zenit.searchinfile;

import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import org.junit.jupiter.api.Test;
import zenit.ZenithTestBase;

import static org.testfx.api.FxAssert.verifyThat;

class SearchTest extends ZenithTestBase {

    private final String FIELD_SEARCH_INPUT = "#textFieldInputWord";
    private final String FIELD_REPLACE_INPUT = "#textFieldReplacementWord";
    private final String BUTTON_SCROLL_UP = "#btnUp";
    private final String BUTTON_SCROLL_DOWN = "#btnDown";
    private final String BUTTON_ESCAPE = "#btnEsc";
    private final String BUTTON_REPLACE_FIRST = "#btnReplaceOne";
    private final String BUTTON_REPLACE_ALL = "#btnReplaceAll";
    private final String LABEL_SEARCH_OCCURRENCES = "#lblOccurrences";
    private final String FIELD_FILE_TREE = "#treeView";

    private final String JAVA_TEST_FILE = "SearchTest.java";
    private final String TEXT_TEST_FILE = "SearchTest.txt";

    @Test
    void searchInJavaFile() {
        String query = "main";
        doubleClickOn(JAVA_TEST_FILE);
        push(KeyCode.CONTROL, KeyCode.F);
        clickOn(FIELD_SEARCH_INPUT);
        write(query);
        verifyThat(LABEL_SEARCH_OCCURRENCES, (Label label) -> {
            String foo = label.getText();
            return foo.contains("1/1");
        });
    }

    @Test
    void searchInJavaFileFail() {
        String query = "shoop di woop, poop, di scoop di di woop";
        doubleClickOn(JAVA_TEST_FILE);
        push(KeyCode.CONTROL, KeyCode.F);
        clickOn(FIELD_SEARCH_INPUT);
        write(query);
        verifyThat(LABEL_SEARCH_OCCURRENCES, (Label label) -> {
            String foo = label.getText();
            return foo.contains("0/0");
        });
    }


    @Test
    void searchInJavaFileMultiple() {
        String query = ";";
        doubleClickOn(JAVA_TEST_FILE);
        push(KeyCode.CONTROL, KeyCode.F);
        clickOn(FIELD_SEARCH_INPUT);
        write(query);
        verifyThat(LABEL_SEARCH_OCCURRENCES, (Label label) -> {
            String foo = label.getText();
            return foo.contains("1/5");
        });
    }


    @Test
    void searchInTextFile() {
        String query = "main";
        doubleClickOn(TEXT_TEST_FILE);
        push(KeyCode.CONTROL, KeyCode.F);
        clickOn(FIELD_SEARCH_INPUT);
        write(query);
        verifyThat(LABEL_SEARCH_OCCURRENCES, (Label label) -> {
            String foo = label.getText();
            return foo.contains("1/1");
        });
    }

    @Test
    void searchInTextFileFail() {
        String query = "shoop di woop, poop, di scoop di di woop";
        doubleClickOn(TEXT_TEST_FILE);
        push(KeyCode.CONTROL, KeyCode.F);
        clickOn(FIELD_SEARCH_INPUT);
        write(query);
        verifyThat(LABEL_SEARCH_OCCURRENCES, (Label label) -> {
            String foo = label.getText();
            return foo.contains("0/0");
        });
    }


    @Test
    void searchInTextFileMultiple() {
        String query = ";";
        doubleClickOn(TEXT_TEST_FILE);
        push(KeyCode.CONTROL, KeyCode.F);
        clickOn(FIELD_SEARCH_INPUT);
        write(query);
        verifyThat(LABEL_SEARCH_OCCURRENCES, (Label label) -> {
            String foo = label.getText();
            return foo.contains("1/5");
        });
    }

    @Test
    void jumpDownSingleJava() {
        String query = ";";
        doubleClickOn(TEXT_TEST_FILE);
        push(KeyCode.CONTROL, KeyCode.F);
        clickOn(FIELD_SEARCH_INPUT);
        write(query);
        clickOn(BUTTON_SCROLL_DOWN);
        verifyThat(LABEL_SEARCH_OCCURRENCES, (Label label) -> {
            String foo = label.getText();
            return foo.contains("2/5");
        });
    }

    @Test
    void jumpDownBorderJava() {
        String query = ";";
        doubleClickOn(JAVA_TEST_FILE);
        push(KeyCode.CONTROL, KeyCode.F);
        clickOn(FIELD_SEARCH_INPUT);
        write(query);
        clickOn(BUTTON_SCROLL_DOWN);
        clickOn(BUTTON_SCROLL_DOWN);
        clickOn(BUTTON_SCROLL_DOWN);
        clickOn(BUTTON_SCROLL_DOWN);
        clickOn(BUTTON_SCROLL_DOWN);
        verifyThat(LABEL_SEARCH_OCCURRENCES, (Label label) -> {
            String foo = label.getText();
            return foo.contains("1/5");
        });
    }

    @Test
    void jumpDownBorderFurtherJava() {
        String query = ";";
        doubleClickOn(JAVA_TEST_FILE);
        push(KeyCode.CONTROL, KeyCode.F);
        clickOn(FIELD_SEARCH_INPUT);
        write(query);
        clickOn(BUTTON_SCROLL_DOWN);
        clickOn(BUTTON_SCROLL_DOWN);
        clickOn(BUTTON_SCROLL_DOWN);
        clickOn(BUTTON_SCROLL_DOWN);
        clickOn(BUTTON_SCROLL_DOWN);
        clickOn(BUTTON_SCROLL_DOWN);
        verifyThat(LABEL_SEARCH_OCCURRENCES, (Label label) -> {
            String foo = label.getText();
            return foo.contains("2/5");
        });
    }

    @Test
    void jumpDownSingleText() {
        String query = ";";
        doubleClickOn(TEXT_TEST_FILE);
        push(KeyCode.CONTROL, KeyCode.F);
        clickOn(FIELD_SEARCH_INPUT);
        write(query);
        clickOn(BUTTON_SCROLL_DOWN);
        verifyThat(LABEL_SEARCH_OCCURRENCES, (Label label) -> {
            String foo = label.getText();
            return foo.contains("2/5");
        });
    }

    @Test
    void jumpDownBorderText() {
        String query = ";";
        doubleClickOn(TEXT_TEST_FILE);
        push(KeyCode.CONTROL, KeyCode.F);
        clickOn(FIELD_SEARCH_INPUT);
        write(query);
        clickOn(BUTTON_SCROLL_DOWN);
        clickOn(BUTTON_SCROLL_DOWN);
        clickOn(BUTTON_SCROLL_DOWN);
        clickOn(BUTTON_SCROLL_DOWN);
        clickOn(BUTTON_SCROLL_DOWN);
        verifyThat(LABEL_SEARCH_OCCURRENCES, (Label label) -> {
            String foo = label.getText();
            return foo.contains("1/5");
        });
    }

    @Test
    void jumpDownBorderFurtherText() {
        String query = ";";
        doubleClickOn(TEXT_TEST_FILE);
        push(KeyCode.CONTROL, KeyCode.F);
        clickOn(FIELD_SEARCH_INPUT);
        write(query);
        clickOn(BUTTON_SCROLL_DOWN);
        clickOn(BUTTON_SCROLL_DOWN);
        clickOn(BUTTON_SCROLL_DOWN);
        clickOn(BUTTON_SCROLL_DOWN);
        clickOn(BUTTON_SCROLL_DOWN);
        clickOn(BUTTON_SCROLL_DOWN);
        verifyThat(LABEL_SEARCH_OCCURRENCES, (Label label) -> {
            String foo = label.getText();
            return foo.contains("2/5");
        });
    }

    @Test
    void jumpUpSingleJava() {
        String query = ";";
        doubleClickOn(TEXT_TEST_FILE);
        push(KeyCode.CONTROL, KeyCode.F);
        clickOn(FIELD_SEARCH_INPUT);
        write(query);
        clickOn(BUTTON_SCROLL_DOWN);
        clickOn(BUTTON_SCROLL_UP);
        verifyThat(LABEL_SEARCH_OCCURRENCES, (Label label) -> {
            String foo = label.getText();
            return foo.contains("1/5");
        });
    }

    @Test
    void jumpUpBorderJava() {
        String query = ";";
        doubleClickOn(JAVA_TEST_FILE);
        push(KeyCode.CONTROL, KeyCode.F);
        clickOn(FIELD_SEARCH_INPUT);
        write(query);
        clickOn(BUTTON_SCROLL_UP);
        verifyThat(LABEL_SEARCH_OCCURRENCES, (Label label) -> {
            String foo = label.getText();
            return foo.contains("5/5");
        });
    }

    @Test
    void jumpUpBorderFurtherJava() {
        String query = ";";
        doubleClickOn(JAVA_TEST_FILE);
        push(KeyCode.CONTROL, KeyCode.F);
        clickOn(FIELD_SEARCH_INPUT);
        write(query);
        clickOn(BUTTON_SCROLL_UP);
        clickOn(BUTTON_SCROLL_UP);
        clickOn(BUTTON_SCROLL_UP);
        clickOn(BUTTON_SCROLL_UP);
        clickOn(BUTTON_SCROLL_UP);
        verifyThat(LABEL_SEARCH_OCCURRENCES, (Label label) -> {
            String foo = label.getText();
            return foo.contains("1/5");
        });
    }

    @Test
    void jumpUpSingleText() {
        String query = ";";
        doubleClickOn(TEXT_TEST_FILE);
        push(KeyCode.CONTROL, KeyCode.F);
        clickOn(FIELD_SEARCH_INPUT);
        write(query);
        clickOn(BUTTON_SCROLL_DOWN);
        verifyThat(LABEL_SEARCH_OCCURRENCES, (Label label) -> {
            String foo = label.getText();
            return foo.contains("2/5");
        });
    }

    @Test
    void jumpUpBorderText() {
        String query = ";";
        doubleClickOn(TEXT_TEST_FILE);
        push(KeyCode.CONTROL, KeyCode.F);
        clickOn(FIELD_SEARCH_INPUT);
        write(query);
        clickOn(BUTTON_SCROLL_DOWN);
        clickOn(BUTTON_SCROLL_DOWN);
        clickOn(BUTTON_SCROLL_DOWN);
        clickOn(BUTTON_SCROLL_DOWN);
        clickOn(BUTTON_SCROLL_DOWN);
        verifyThat(LABEL_SEARCH_OCCURRENCES, (Label label) -> {
            String foo = label.getText();
            return foo.contains("1/5");
        });
    }

    @Test
    void jumpUpBorderFurtherText() {
        String query = ";";
        doubleClickOn(TEXT_TEST_FILE);
        push(KeyCode.CONTROL, KeyCode.F);
        clickOn(FIELD_SEARCH_INPUT);
        write(query);
        clickOn(BUTTON_SCROLL_DOWN);
        clickOn(BUTTON_SCROLL_DOWN);
        clickOn(BUTTON_SCROLL_DOWN);
        clickOn(BUTTON_SCROLL_DOWN);
        clickOn(BUTTON_SCROLL_DOWN);
        clickOn(BUTTON_SCROLL_DOWN);
        verifyThat(LABEL_SEARCH_OCCURRENCES, (Label label) -> {
            String foo = label.getText();
            return foo.contains("2/5");
        });
    }




    /*
    @Test
    void clearZen() {
    }

    @Test
    void cleanZen() {
    }

    @Test
    void replaceAll() {
    }

    @Test
    void replaceOne() {
    }

    @Test
    void jumpUp() {
    }

    @Test
    void searchWindowLabelsTest() {

    }
    */
}