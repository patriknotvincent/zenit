package main.java.zenit.zencodearea;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class Completion {
    private final HashMap<Character, ArrayList<LetterNode>> startLetters;

    public Completion() {
        startLetters = new HashMap<>();
    }

    private List<String> searchFor(String input) {
        ArrayList<String> foundWords = new ArrayList<>();

        List<LetterNode> startList = findTraverseStartList(input);
        if (startList != null && !startList.isEmpty()) {
            findWords(foundWords, input, startList);
        }

        return foundWords;
    }

    private List<LetterNode> findTraverseStartList(String input) {
        List<LetterNode> letterNodes = startLetters.get(input.charAt(0));
        input = input.substring(1);
        List<Character> chars = getChars(input);

        if(!chars.isEmpty()) {
            for (LetterNode n : letterNodes) {
                if (n.getLetter() == chars.get(0)) {
                    chars.remove(0);
                    return traverseLetterNode(n, chars);
                }
            }
            return new ArrayList<>();
        }
        return letterNodes;
    }

    private List<LetterNode> traverseLetterNode(LetterNode n, List<Character> chars){
        if(chars.isEmpty()){
            if(n.getChildren() == null){
                return new ArrayList<>();
            }
            return n.getChildren();
        }
        if(n.getChildren() == null){
            return new ArrayList<>();
        }
        for(LetterNode child : n.getChildren()){
            if(child.getLetter() == chars.get(0)){
                chars.remove(0);
                return traverseLetterNode(child, chars);
            }
        }
        return new ArrayList<>();
    }

    private void findWords(ArrayList<String> foundWords, String input, List<LetterNode> LetterNodes) {
        for(LetterNode n : LetterNodes){
            if(n.isLastLetterOfAWord()){
                foundWords.add(input + n.getLetter());
            }
            if(n.getChildren() != null){
                findWords(foundWords, input + n.getLetter(), n.getChildren());
            }
        }
    }

    public void printOut() {
        for(char c : startLetters.keySet()){
            ArrayList<LetterNode> LetterNodes = startLetters.get(c);
            for(LetterNode n : LetterNodes){
                printLetterNodes(String.valueOf(c), n);
            }
        }
    }

    private void printLetterNodes(String word, LetterNode n) {
        word += n.getLetter();
        if(n.isLastLetterOfAWord()){
            System.out.println(word);
        }
        if(n.getChildren() != null) {
            for(LetterNode child : n.getChildren()){
                printLetterNodes(word, child);
            }
        }
    }

    public void addWords(List<String> variables) {
        for(String variable : variables){
            List<Character> chars = getChars(variable);
            ArrayList<LetterNode> firstLetterList;
            if(startLetters.containsKey(chars.get(0))) {
                firstLetterList = startLetters.get(chars.get(0));
            } else {
                firstLetterList = new ArrayList<>();
                startLetters.put(chars.get(0), firstLetterList);
            }
            chars.remove(0);
            addAllLetters(firstLetterList, chars);
        }
    }

    private static List<Character> getChars(String variable) {
        char[] chars = variable.toCharArray();
        List<Character> letters = new ArrayList<>();
        for(char c : chars){
            letters.add(c);
        }
        return letters;
    }

    private void addAllLetters(List<LetterNode> siblings, List<Character> chars) {
        if(chars.isEmpty()) return;

        if(siblings.size() == 0){ //if there are no siblings
            char c = chars.get(0);
            chars.remove(0);
            if(!chars.isEmpty()){ //if there are more letters to add
                siblings.add(new LetterNode(c, false));
                addAllLetters(siblings.get(0).getChildren(), chars);
            }else{
                siblings.add(new LetterNode(c, true));
            }
        } else { //if there are siblings
            for(LetterNode sibling : siblings){ //check if the letter exists
                if(sibling.getLetter() == chars.get(0)){
                    if(sibling.getChildren() == null){
                        sibling.createChildrenList();
                    }
                    chars.remove(0);
                    addAllLetters(sibling.getChildren(), chars);
                    return;
                }
            }
            //if the letter doesn't exist
            char c = chars.get(0);
            chars.remove(0);
            if(!chars.isEmpty()) {
                LetterNode n = new LetterNode(c, false);
                siblings.add(n);
                addAllLetters(n.getChildren(), chars);
            } else {
                siblings.add(new LetterNode(c, true));
            }
        }
    }

    public void clear() {
        startLetters.clear();
    }
}
