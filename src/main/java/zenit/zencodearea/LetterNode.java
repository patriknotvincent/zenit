package main.java.zenit.zencodearea;

import java.util.ArrayList;
import java.util.List;

public class LetterNode {
    private ArrayList<LetterNode> children;

    private char letter;

    private boolean lastLetterOfAWord;

    public LetterNode(char letter, boolean lastLetterOfAWord) {
        this.letter = letter;
        children = lastLetterOfAWord ? null : new ArrayList<>();
        this.lastLetterOfAWord = lastLetterOfAWord;
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
}
