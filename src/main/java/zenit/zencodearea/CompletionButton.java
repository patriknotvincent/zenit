package zenit.zencodearea;

import javafx.scene.control.Button;

public class CompletionButton extends Button {
    private Completion completion;

    public CompletionButton(Completion completion) {
        super(completion.getName() + " (" + completion.getCompletionType().toString() + ")");
        this.completion = completion;
    }
}
