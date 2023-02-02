package main.java.zenit.launchers;

import javafx.stage.Stage;
import main.java.zenit.ui.MainController;

/**
 * Kommentera här
 */
public class MacOSLauncher {

    /**
     * Kommentera här
     * tog bort onödiga kommentarer
     */
	public MacOSLauncher(Stage stage) {
        System.out.println("Macos");
		System.setProperty("com.apple.mrj.application.apple.menu.about.name", "WikiTeX");
		new MainController(stage);
		
	}

}
