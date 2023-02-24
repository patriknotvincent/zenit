package main.java.zenit.zencodearea;

import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;

import java.util.List;

public class CompletionWindow extends Popup {

    private VBox content;

    public CompletionWindow() {
        content = new VBox();
        content.setPrefWidth(200);
        content.setPrefHeight(500);
        content.setStyle("-fx-background-color: #444444;");
        getContent().add(content);
    }

    public void updateCompletions(List<Completion> completions) {
        Platform.runLater(() -> {
            content.getChildren().clear();
            for(Completion completion : completions){
                Button button = new Button(completion.getName() + " (" + completion.getCompletionType().toString() + ")");
                button.setStyle("-fx-background-color: #444444; -fx-text-fill: #ffffff;");
                content.getChildren().add(button);
            }
        });
    }

}
