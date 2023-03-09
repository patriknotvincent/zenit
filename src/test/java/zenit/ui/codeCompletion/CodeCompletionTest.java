package zenit.ui.codeCompletion;

import javafx.animation.PauseTransition;
import javafx.geometry.VerticalDirection;
import javafx.scene.input.KeyCode;
import org.junit.jupiter.api.Test;
import org.testfx.util.WaitForAsyncUtils;
import zenit.ZenithTestBase;

import java.util.concurrent.TimeUnit;

public class CodeCompletionTest extends ZenithTestBase {
    private final String ClASS_FILE = "TestFile.java";
    private final String TESTPHRASE1 = "in";
    private final String TESTPHRASE2 = "dou";
    private final String TESTPHRASE3 = "str";

    @Test
    void dropDown() {
        /**
         * Make sure the file CLASS_FILE exists in the workspace at the top level, not in any folder or package
         * The sleep is called to make it easier to see that the dropdown appears correctly
         */
        doubleClickOn(ClASS_FILE);
        write(TESTPHRASE1);
        WaitForAsyncUtils.sleep(2, TimeUnit.SECONDS);
        for (int i = 0; i < TESTPHRASE1.length(); i++) {
            push(KeyCode.BACK_SPACE);
        }
        write(TESTPHRASE2);
        WaitForAsyncUtils.sleep(2, TimeUnit.SECONDS);
        for (int i = 0; i < TESTPHRASE2.length(); i++) {
            push(KeyCode.BACK_SPACE);
        }
        write(TESTPHRASE3);
        WaitForAsyncUtils.sleep(2, TimeUnit.SECONDS);
        for (int i = 0; i < TESTPHRASE3.length(); i++) {
            push(KeyCode.BACK_SPACE);
        }
    }

    @Test
    void chooseCompletion() {
        /**
         * Make sure the file CLASS_FILE exists in the workspace at the top level, not in any folder or package
         * the sleep is called to make it easier to see what is happening
         */
        doubleClickOn(ClASS_FILE);
        write(TESTPHRASE1);
        WaitForAsyncUtils.sleep(1, TimeUnit.SECONDS);
        push(KeyCode.ENTER);
        push(KeyCode.ENTER);
        write(TESTPHRASE2);
        WaitForAsyncUtils.sleep(1, TimeUnit.SECONDS);
        push(KeyCode.DOWN);
        WaitForAsyncUtils.sleep(1, TimeUnit.SECONDS);
        push(KeyCode.ENTER);
        push(KeyCode.ENTER);
        write(TESTPHRASE3);
        WaitForAsyncUtils.sleep(1, TimeUnit.SECONDS);
        push(KeyCode.ENTER);
        push(KeyCode.ENTER);
        WaitForAsyncUtils.sleep(1, TimeUnit.SECONDS);
    }

    /**
     * Make sure the file CLASS_FILE exists in the workspace at the top level, not in any folder or package
     * the sleep is called to make it easier to see what is happening
     */
    @Test
    void cycleSuggestions(){
        doubleClickOn(ClASS_FILE);
        write(TESTPHRASE1);
        WaitForAsyncUtils.sleep(1, TimeUnit.SECONDS);
        push(KeyCode.DOWN);
        WaitForAsyncUtils.sleep(1, TimeUnit.SECONDS);
        push(KeyCode.UP);
        WaitForAsyncUtils.sleep(1, TimeUnit.SECONDS);
        scroll(VerticalDirection.DOWN);
        WaitForAsyncUtils.sleep(1, TimeUnit.SECONDS);
        scroll(VerticalDirection.UP);
        WaitForAsyncUtils.sleep(1, TimeUnit.SECONDS);
    }
}
