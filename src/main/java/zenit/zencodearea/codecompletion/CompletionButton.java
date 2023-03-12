package zenit.zencodearea.codecompletion;

import javafx.scene.control.Button;

public class CompletionButton extends Button {
    private Completion completion;

    public CompletionButton(Completion completion) {
        super(completion.getName() + " (" + completion.getCompletionType().toString() + ")");
        this.completion = completion;
        setPrefHeight(20);
    }

    public Completion getCompletion() {
        return completion;
    }
}
