package zenit;

import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationTest;
import zenit.ui.TestUI;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.concurrent.TimeoutException;

/**
 * Abstract base class for all test classes. Contains necessary methods in order to test methods contained in the GUI,
 * or pure logic classes that are interlinked to GUI-related operations (IE JavaFX)
 */

public abstract class ZenithTestBase extends ApplicationTest {

    /**
     * @BeforeAll Annotation causes the method to be run once before all test cases are run.
     * @throws Exception Exceptions that can be caused when running the application
     *
     * Launches the application once
     *
     */
    @BeforeAll
    public static void setUp() throws Exception {
        ApplicationTest.launch(TestUI.class);
    }

    /**
     *
     * @param stage Part of the GUI to test and / or display
     * @throws Exception Exceptions associated with running the application
     */
    @Override
    public void start(Stage stage) throws Exception {
        stage.show();
    }

    /**
     * @AfterEach Annotation causes the method to be run after every test-case in one class
     * @throws TimeoutException See JavaDoc
     *
     * Makes sure to clear all key presses and mouse-presses that are run by the TestFXRobot
     *
     */
    @AfterEach
    public void afterEachTest() throws TimeoutException {
        FxToolkit.hideStage();
        release(new KeyCode[]{});
        release(new MouseButton[]{});
    }

    /**
     *
     * @param query Object to be found in the .fxml files
     * @param <T> Object to be returned
     * @return An object (or node) associated with .fxml files found in the project.
     */
    public <T extends Node> T find(final String query) {
        return lookup(query).query();
    }
}
