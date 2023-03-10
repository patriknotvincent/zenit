package zenit.ui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import zenit.setup.SetupController;

import java.io.File;

/**
 * Class for testing the UI.
 * @author Pontus Laos, Oskar Molander
 *
 */
public class TestUI extends Application {
	private MainController controller;
	
	@Override
	public void start(Stage stage) {
		File file = new File("res/workspace/workspace.dat");
		if (file.exists()) {
			controller = new MainController(stage);
		} else {
			SetupController sc;
			sc = new SetupController();
			sc.start(stage);
		}
	}
	
	/**
	 * Exits the application by calling Platform.exit() as well as System.exit().
	 * @author Pontus Laos
	 */
	@Override
	public void stop() {
		controller.quit();
		Platform.exit();
	}
	
	/**
	 * Runs the UI.
	 * @param args
	 */
	public static void main(String[] args) {
		launch(args);
	}
}
