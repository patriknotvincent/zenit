package zenit.ui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.image.Image;
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
		stage.getIcons().add(new Image("/zenit/setup/zenit.png"));
		File file = new File("res/workspace/workspace.dat");
		File jdk = new File("res/jdk/jdk.dat");
		File defaultJdk = new File("res/jdk/defaultJDK.dat");
		if (!file.exists() || !jdk.exists() || !defaultJdk.exists()){
			SetupController sc;
			sc = new SetupController();
			sc.start(stage);
		} else {
			controller = new MainController(stage);
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
