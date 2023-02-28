package zenit.zencodearea;

import javafx.application.Platform;
import javafx.scene.control.IndexRange;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;
import zenit.util.Tuple;

import java.util.ArrayList;
import java.util.List;

/**
 * JavaFX Popup window that shows completions.
 */
public class CompletionWindow extends Popup {

    private final ZenCodeArea zenCodeArea;
    private VBox content;
    private ArrayList<CompletionButton> completionButtons = new ArrayList<>();
    private int completionIndex = 0;
    private int inputLength = 0;

    private Tuple<Integer, Integer> absolutePosition;


    public CompletionWindow(ZenCodeArea zenCodeArea) {
        this.zenCodeArea = zenCodeArea;
        content = new VBox();
        content.setPrefWidth(200);
        content.setPrefHeight(500);
        content.setStyle("-fx-background-color: #444444;");
        getContent().add(content);

        //Adding event filter to enable arrow key navigation through the completion menu:
        addEventFilter(KeyEvent.KEY_RELEASED, event -> {
            switch (event.getCode()) {
                case LEFT:
                    zenCodeArea.moveTo(zenCodeArea.getCaretPosition() - 1);
                    break;
                case RIGHT:
                    zenCodeArea.moveTo(zenCodeArea.getCaretPosition() + 1);
                    break;
                case UP:
                    cycleCompletions(false);
                    break;
                case DOWN:
                    cycleCompletions(true);
                    break;
                default:
                    break;
            }
        });
    }

    public void updateCompletions(List<Completion> completions) {
            completionButtons.clear();
        Platform.runLater(() -> {
            content.getChildren().clear();
            for(Completion completion : completions){
                CompletionButton button = new CompletionButton(completion);
                completionButtons.add(button);
                button.setStyle("-fx-background-color: #444444; -fx-text-fill: #ffffff;");
                button.setOnAction(event -> {
                    fillInSuggestion(completion);
                    this.hide();
                });
                content.getChildren().add(button);

            }

            if (!completionButtons.isEmpty()) {
                completionIndex = 0;
                completionButtons.get(completionIndex).setStyle("-fx-background-color: #d2d0d0; -fx-text-fill: #646363;");
                completionButtons.get(completionIndex).requestFocus();
            }
        });
    }

    private void fillInSuggestion(Completion completion) {
        String completionText = completion.getName();

        int startPos = zenCodeArea.getCaretPosition() - inputLength;
        int endPos = startPos + inputLength;
        IndexRange indexRange = new IndexRange(startPos, endPos);

        zenCodeArea.replaceText(indexRange, completionText);
    }

    public void cycleCompletions(boolean cycleDown) {
        if (cycleDown) {
            completionButtons.get(completionIndex).setStyle("-fx-background-color: #444444; -fx-text-fill: #ffffff;");
            //Evaluate the result of incrementing completionIndex, if it is equal to the size of the completionButtons list, set it to 0:
            completionIndex = (completionIndex + 1) % completionButtons.size();
            CompletionButton completionButton = completionButtons.get(completionIndex);
            completionButton.setStyle("-fx-background-color: #d2d0d0; -fx-text-fill: #646363;");
            completionButton.requestFocus();
        } else {
            completionButtons.get(completionIndex).setStyle("-fx-background-color: #444444; -fx-text-fill: #ffffff;");
            //Evaluate the result of decrementing completionIndex, if it is -1, set it to the last index of the completionButtons list:
            completionIndex = completionIndex - 1 == -1 ? completionButtons.size() - 1 : completionIndex - 1;
            CompletionButton completionButton = completionButtons.get(completionIndex);
            completionButton.setStyle("-fx-background-color: #d2d0d0; -fx-text-fill: #646363;");
            completionButton.requestFocus();
        }
    }

    public void setInputLength(int inputLength) {
        this.inputLength = inputLength;
    }
}
