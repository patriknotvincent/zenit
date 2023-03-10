package zenit.ui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * Class for testing the UI.
 * @author Pontus Laos, Oskar Molander
 *
 */
public class TestUI extends Application {
	private MainController controller;
	
	@Override
	public void start(Stage stage) {
		controller = new MainController(stage);
		stage.getIcons().add(new Image("/zenit/setup/zenit.png"));
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
