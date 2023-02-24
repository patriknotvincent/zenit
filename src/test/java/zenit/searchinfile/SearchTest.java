package zenit.searchinfile;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.api.FxRobotException;
import zenit.ZenithTestBase;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.testfx.api.FxAssert.verifyThat;

class SearchTest extends ZenithTestBase {

    /* <------------------------- TAGGED ELEMENTS IN FXML ------------------------->*/

    private final String FIELD_SEARCH_INPUT = "#textFieldInputWord";
    private final String FIELD_REPLACE_INPUT = "#textFieldReplacementWord";
    private final String BUTTON_SCROLL_UP = "#btnUp";
    private final String BUTTON_SCROLL_DOWN = "#btnDown";
    private final String BUTTON_ESCAPE = "#btnEsc";
    private final String BUTTON_REPLACE_SELECTED = "#btnReplaceOne";
    private final String BUTTON_REPLACE_ALL = "#btnReplaceAll";
    private final String LABEL_SEARCH_OCCURRENCES = "#lblOccurrences";
    private final String TEXT_EDITOR_AREA = "#editorArea";

    /* <------------------------ UNTAGGED ELEMENTS IN FXML ------------------------>*/

    private final String JAVA_TEST_FILE = "SearchTest.java";
    private final String TEXT_TEST_FILE = "SearchTest.txt";

    /* <--------------------------------------------------------------------------->*/

    @BeforeEach
    void clearTextEditor() {
        // TODO The editor needs to reset before each test
    }

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
    void testJumpDownBorderJava() {
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
    void testJumpDownBorderLoopbackJava() {
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
    void testJumpDownSingleText() {
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
    void testJumpDownBorderText() {
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
    void testJumpDownBorderFurtherText() {
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
    void testJumpUpSingleJava() {
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
    void testJumpUpBorderJava() {
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
    void testJumpUpBorderLoopJava() {
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
    void testJumpUpSingleText() {
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
    void jestJumpUpBorderText() {
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
    void testJumpUpBorderFurtherText() {
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
    void testReplaceManyWithOneJava() {
        char query = ';';
        String replace = " BATMAIN";
        String replaceQuery = "BATMAIN";
        doubleClickOn(JAVA_TEST_FILE);
        push(KeyCode.CONTROL, KeyCode.F);
        clickOn(FIELD_SEARCH_INPUT);
        write(query);
        clickOn(FIELD_REPLACE_INPUT);
        write(replace);
        clickOn(BUTTON_REPLACE_ALL);
        clickOn(FIELD_REPLACE_INPUT);
        push(KeyCode.CONTROL, KeyCode.BACK_SPACE);
        clickOn(FIELD_SEARCH_INPUT);
        push(KeyCode.CONTROL, KeyCode.BACK_SPACE);
        write(replaceQuery);
        moveTo(TEXT_EDITOR_AREA);
        verifyThat(LABEL_SEARCH_OCCURRENCES, (Label label) -> {
            String foo = label.getText();
            //TODO
            return foo.contains("1/5");
        });
    }

    @Test
    void testReplaceManyWithOneText() {
        char query = ';';
        String replace = " BATMAIN";
        String replaceQuery = "BATMAIN";
        doubleClickOn(TEXT_TEST_FILE);
        push(KeyCode.CONTROL, KeyCode.F);
        clickOn(FIELD_SEARCH_INPUT);
        write(query);
        clickOn(FIELD_REPLACE_INPUT);
        write(replace);
        clickOn(BUTTON_REPLACE_ALL);
        clickOn(FIELD_REPLACE_INPUT);
        push(KeyCode.CONTROL, KeyCode.BACK_SPACE);
        clickOn(FIELD_SEARCH_INPUT);
        push(KeyCode.CONTROL, KeyCode.BACK_SPACE);
        write(replaceQuery);
        moveTo(TEXT_EDITOR_AREA);
        verifyThat(LABEL_SEARCH_OCCURRENCES, (Label label) -> {
            String foo = label.getText();
            //TODO
            return foo.contains("1/5");
        });
    }

    /**
     * Unit tests for replacing one word, checking each index in a multi-word search.
     */

    @Test
    void testReplaceFirstWithOneJava() {
        char query = ';';
        String replace = " BATMAIN";
        doubleClickOn(JAVA_TEST_FILE);
        push(KeyCode.CONTROL, KeyCode.F);
        clickOn(FIELD_SEARCH_INPUT);
        write(query);
        clickOn(FIELD_REPLACE_INPUT);
        write(replace);
        clickOn(BUTTON_REPLACE_SELECTED);
        clickOn(FIELD_SEARCH_INPUT);
        push(KeyCode.CONTROL, KeyCode.BACK_SPACE);
        write(replace);
        moveTo(TEXT_EDITOR_AREA);
        verifyThat(LABEL_SEARCH_OCCURRENCES, (Label label) -> {
            String foo = label.getText();
            // TODO Needs comparison to check whether the right word on the right row was replaced.
            return foo.contains("1/1");
        });
    }

    @Test
    void testReplaceSecondWithOneJava() {
        char query = ';';
        String replace = " BATMAIN";
        doubleClickOn(JAVA_TEST_FILE);
        push(KeyCode.CONTROL, KeyCode.F);
        clickOn(FIELD_SEARCH_INPUT);
        write(query);
        clickOn(BUTTON_SCROLL_DOWN);
        clickOn(FIELD_REPLACE_INPUT);
        write(replace);
        clickOn(BUTTON_REPLACE_SELECTED);
        clickOn(FIELD_SEARCH_INPUT);
        push(KeyCode.CONTROL, KeyCode.BACK_SPACE);
        write(replace);
        moveTo(TEXT_EDITOR_AREA);
        verifyThat(LABEL_SEARCH_OCCURRENCES, (Label label) -> {
            String foo = label.getText();
            // TODO Needs comparison to check whether the right word on the right row was replaced.
            return foo.contains("1/1");
        });
    }

    @Test
    void testReplaceThirdWithOneJava() {
        char query = ';';
        String replace = " BATMAIN";
        doubleClickOn(JAVA_TEST_FILE);
        push(KeyCode.CONTROL, KeyCode.F);
        clickOn(FIELD_SEARCH_INPUT);
        write(query);
        clickOn(BUTTON_SCROLL_DOWN);
        clickOn(BUTTON_SCROLL_DOWN);
        clickOn(FIELD_REPLACE_INPUT);
        write(replace);
        clickOn(BUTTON_REPLACE_SELECTED);
        clickOn(FIELD_SEARCH_INPUT);
        push(KeyCode.CONTROL, KeyCode.BACK_SPACE);
        write(replace);
        moveTo(TEXT_EDITOR_AREA);
        verifyThat(LABEL_SEARCH_OCCURRENCES, (Label label) -> {
            String foo = label.getText();
            // TODO Needs comparison to check whether the right word on the right row was replaced.
            return foo.contains("1/1");
        });
    }

    @Test
    void testReplaceFourthWithOneJava() {
        char query = ';';
        String replace = " BATMAIN";
        doubleClickOn(JAVA_TEST_FILE);
        push(KeyCode.CONTROL, KeyCode.F);
        clickOn(FIELD_SEARCH_INPUT);
        write(query);
        clickOn(BUTTON_SCROLL_DOWN);
        clickOn(BUTTON_SCROLL_DOWN);
        clickOn(BUTTON_SCROLL_DOWN);
        clickOn(FIELD_REPLACE_INPUT);
        write(replace);
        clickOn(BUTTON_REPLACE_SELECTED);
        clickOn(FIELD_SEARCH_INPUT);
        push(KeyCode.CONTROL, KeyCode.BACK_SPACE);
        write(replace);
        moveTo(TEXT_EDITOR_AREA);
        verifyThat(LABEL_SEARCH_OCCURRENCES, (Label label) -> {
            String foo = label.getText();
            // TODO Needs comparison to check whether the right word on the right row was replaced.
            return foo.contains("1/1");
        });
    }

    @Test
    void testReplaceFifthWithOneJava() {
        char query = ';';
        String replace = " BATMAIN";
        doubleClickOn(JAVA_TEST_FILE);
        push(KeyCode.CONTROL, KeyCode.F);
        clickOn(FIELD_SEARCH_INPUT);
        write(query);
        clickOn(BUTTON_SCROLL_DOWN);
        clickOn(BUTTON_SCROLL_DOWN);
        clickOn(BUTTON_SCROLL_DOWN);
        clickOn(BUTTON_SCROLL_DOWN);
        clickOn(FIELD_REPLACE_INPUT);
        write(replace);
        clickOn(BUTTON_REPLACE_SELECTED);
        clickOn(FIELD_SEARCH_INPUT);
        push(KeyCode.CONTROL, KeyCode.BACK_SPACE);
        write(replace);
        moveTo(TEXT_EDITOR_AREA);
        verifyThat(LABEL_SEARCH_OCCURRENCES, (Label label) -> {
            String foo = label.getText();
            // TODO Needs comparison to check whether the right word on the right row was replaced.
            return foo.contains("1/1");
        });
    }

    /**
     * Same tests, but with a text-file instead.
     */

    @Test
    void testReplaceFirstWithOneText() {
        char query = ';';
        String replace = " BATMAIN";
        doubleClickOn(TEXT_TEST_FILE);
        push(KeyCode.CONTROL, KeyCode.F);
        clickOn(FIELD_SEARCH_INPUT);
        write(query);
        clickOn(FIELD_REPLACE_INPUT);
        write(replace);
        clickOn(BUTTON_REPLACE_SELECTED);
        clickOn(FIELD_SEARCH_INPUT);
        push(KeyCode.CONTROL, KeyCode.BACK_SPACE);
        write(replace);
        moveTo(TEXT_EDITOR_AREA);
        verifyThat(LABEL_SEARCH_OCCURRENCES, (Label label) -> {
            String foo = label.getText();
            // TODO Needs comparison to check whether the right word on the right row was replaced.
            return foo.contains("1/1");
        });
    }

    @Test
    void testReplaceSecondWithOneText() {
        char query = ';';
        String replace = " BATMAIN";
        doubleClickOn(TEXT_TEST_FILE);
        push(KeyCode.CONTROL, KeyCode.F);
        clickOn(FIELD_SEARCH_INPUT);
        write(query);
        clickOn(BUTTON_SCROLL_DOWN);
        clickOn(FIELD_REPLACE_INPUT);
        write(replace);
        clickOn(BUTTON_REPLACE_SELECTED);
        clickOn(FIELD_SEARCH_INPUT);
        push(KeyCode.CONTROL, KeyCode.BACK_SPACE);
        write(replace);
        moveTo(TEXT_EDITOR_AREA);
        verifyThat(LABEL_SEARCH_OCCURRENCES, (Label label) -> {
            String foo = label.getText();
            // TODO Needs comparison to check whether the right word on the right row was replaced.
            return foo.contains("1/1");
        });
    }

    @Test
    void testReplaceThirdWithOneText() {
        char query = ';';
        String replace = " BATMAIN";
        doubleClickOn(TEXT_TEST_FILE);
        push(KeyCode.CONTROL, KeyCode.F);
        clickOn(FIELD_SEARCH_INPUT);
        write(query);
        clickOn(BUTTON_SCROLL_DOWN);
        clickOn(BUTTON_SCROLL_DOWN);
        clickOn(FIELD_REPLACE_INPUT);
        write(replace);
        clickOn(BUTTON_REPLACE_SELECTED);
        clickOn(FIELD_SEARCH_INPUT);
        push(KeyCode.CONTROL, KeyCode.BACK_SPACE);
        write(replace);
        moveTo(TEXT_EDITOR_AREA);
        verifyThat(LABEL_SEARCH_OCCURRENCES, (Label label) -> {
            String foo = label.getText();
            // TODO Needs comparison to check whether the right word on the right row was replaced.
            return foo.contains("1/1");
        });
    }

    @Test
    void testReplaceFourthWithOneText() {
        char query = ';';
        String replace = " BATMAIN";
        doubleClickOn(TEXT_TEST_FILE);
        push(KeyCode.CONTROL, KeyCode.F);
        clickOn(FIELD_SEARCH_INPUT);
        write(query);
        clickOn(BUTTON_SCROLL_DOWN);
        clickOn(BUTTON_SCROLL_DOWN);
        clickOn(BUTTON_SCROLL_DOWN);
        clickOn(FIELD_REPLACE_INPUT);
        write(replace);
        clickOn(BUTTON_REPLACE_SELECTED);
        clickOn(FIELD_SEARCH_INPUT);
        push(KeyCode.CONTROL, KeyCode.BACK_SPACE);
        write(replace);
        moveTo(TEXT_EDITOR_AREA);
        verifyThat(LABEL_SEARCH_OCCURRENCES, (Label label) -> {
            String foo = label.getText();
            // TODO Needs comparison to check whether the right word on the right row was replaced.
            return foo.contains("1/1");
        });
    }

    @Test
    void testReplaceFifthWithOneText() {
        char query = ';';
        String replace = " BATMAIN";
        doubleClickOn(TEXT_TEST_FILE);
        push(KeyCode.CONTROL, KeyCode.F);
        clickOn(FIELD_SEARCH_INPUT);
        write(query);
        clickOn(BUTTON_SCROLL_DOWN);
        clickOn(BUTTON_SCROLL_DOWN);
        clickOn(BUTTON_SCROLL_DOWN);
        clickOn(BUTTON_SCROLL_DOWN);
        clickOn(FIELD_REPLACE_INPUT);
        write(replace);
        clickOn(BUTTON_REPLACE_SELECTED);
        clickOn(FIELD_SEARCH_INPUT);
        push(KeyCode.CONTROL, KeyCode.BACK_SPACE);
        write(replace);
        moveTo(TEXT_EDITOR_AREA);
        verifyThat(LABEL_SEARCH_OCCURRENCES, (Label label) -> {
            String foo = label.getText();
            // TODO Needs comparison to check whether the right word on the right row was replaced.
            return foo.contains("1/1");
        });
    }

    /**
     * Tests for testing state-transitions for the search window
     */

    @Test()
    void testDestroyWindowWithEscapeKey() {
        assertThrows(FxRobotException.class, () -> {
            doubleClickOn(TEXT_TEST_FILE);
            push(KeyCode.CONTROL, KeyCode.F);
            clickOn(FIELD_SEARCH_INPUT);
            push(KeyCode.ESCAPE);
            clickOn(LABEL_SEARCH_OCCURRENCES);
        });
    }

    @Test()
    void testDestroyWindowWithEscapeButton() {
        assertThrows(FxRobotException.class, () -> {
            doubleClickOn(TEXT_TEST_FILE);
            push(KeyCode.CONTROL, KeyCode.F);
            clickOn(FIELD_SEARCH_INPUT);
            clickOn(BUTTON_ESCAPE);
            clickOn(LABEL_SEARCH_OCCURRENCES);
        });
    }

    @Test()
    void testWindowStateTransition() {
        doubleClickOn(TEXT_TEST_FILE);
        push(KeyCode.CONTROL, KeyCode.F);
        clickOn(FIELD_SEARCH_INPUT);
        clickOn(BUTTON_ESCAPE);
        push(KeyCode.CONTROL, KeyCode.F);
        clickOn(LABEL_SEARCH_OCCURRENCES);
        verifyThat(FIELD_SEARCH_INPUT, (TextField field) -> {
            String foo = field.getText();
            return foo.isEmpty();
        });
    }

    @Test()
    void testWindowStateTransitionWithText() {
        doubleClickOn(TEXT_TEST_FILE);
        push(KeyCode.CONTROL, KeyCode.F);
        clickOn(FIELD_SEARCH_INPUT);
        write("Hello!");
        clickOn(BUTTON_ESCAPE);
        push(KeyCode.CONTROL, KeyCode.F);
        clickOn(LABEL_SEARCH_OCCURRENCES);
        verifyThat(FIELD_SEARCH_INPUT, (TextField field) -> {
            String foo = field.getText();
            return foo.isEmpty();
        });
    }

    @Test()
    void testErroneousWindowStateTransition() {
        doubleClickOn(TEXT_TEST_FILE);
        push(KeyCode.CONTROL, KeyCode.F);
        clickOn(FIELD_SEARCH_INPUT);
        write("Hello!");
        clickOn(BUTTON_ESCAPE);
        push(KeyCode.CONTROL, KeyCode.F);
        clickOn(LABEL_SEARCH_OCCURRENCES);
        push(KeyCode.CONTROL, KeyCode.F);
        verifyThat(FIELD_SEARCH_INPUT, (TextField field) -> {
            String foo = field.getText();
            return foo.isEmpty();
        });
    }
}