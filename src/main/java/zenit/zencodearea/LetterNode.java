package main.java.zenit.zencodearea;

import java.util.ArrayList;
import java.util.List;

public class LetterNode {
    private ArrayList<LetterNode> children;

    private final char letter;

    private boolean lastLetterOfAWord;

    private CompletionType completionType;

    public LetterNode(char letter, boolean lastLetterOfAWord, CompletionType completionType) {
        this.letter = letter;
        children = lastLetterOfAWord ? null : new ArrayList<>();
        this.lastLetterOfAWord = lastLetterOfAWord;
        this.completionType = completionType;
    }

    public boolean isLastLetterOfAWord() {
        return lastLetterOfAWord;
    }

    public char getLetter() {
        return letter;
    }

    public List<LetterNode> getChildren() {
        return children;
    }

    public void createChildrenList() {
        children = new ArrayList<>();
    }

    public void setLastLetterOfAWord(boolean lastLetterOfAWord) {
        this.lastLetterOfAWord = lastLetterOfAWord;
    }

    public CompletionType getCompletionType() {
        return completionType;
    }

    public void setCompletionType(CompletionType completionType) {
        this.completionType = completionType;
    }
}
