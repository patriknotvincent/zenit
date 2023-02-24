package zenit.zencodearea;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CompletionGraph {
    private final HashMap<Character, ArrayList<LetterNode>> startLetters;

    public CompletionGraph() {
        startLetters = new HashMap<>();
    }

    public List<Completion> searchFor(String input) {
        if(input.isEmpty()){
            return new ArrayList<>();
        }
        ArrayList<Completion> foundWords = new ArrayList<>();

        List<List<LetterNode>> startLists = findTraverseStartList(input);
        if(input.length() != 1) {
            startLists.forEach(list -> {
                if (list != null && !list.isEmpty()) {

                    StringBuilder base;
                    base = findBase(list.get(0).getParent(), "");
                    base.append(input.charAt(0));
                    base.reverse();

                    findWords(foundWords, list, base.toString());
                }
            });
        }else if(!startLists.isEmpty()) {
            findWords(foundWords, startLists.get(0), input);
        }

        //Find opposite case
        char oppositeCase = Character.isUpperCase(input.charAt(0)) ? Character.toLowerCase(input.charAt(0)) : Character.toUpperCase(input.charAt(0));
        startLists = findTraverseStartList(oppositeCase + input.substring(1));
        if(input.length() != 1){
            startLists.forEach(list -> {
                if (list != null && !list.isEmpty()) {
                    StringBuilder base;
                    base = findBase(list.get(0).getParent(), "");
                    base.append(oppositeCase);
                    base.reverse();

                    findWords(foundWords, list, base.toString());
                }
            });
        }else if(!startLists.isEmpty()) {
            findWords(foundWords, startLists.get(0), String.valueOf(oppositeCase));
        }

        return foundWords;
    }

    private List<List<LetterNode>> findTraverseStartList(String input) {

        List<List<LetterNode>> startPoints = new ArrayList<>();
        List<LetterNode> letterNodes = startLetters.get(input.charAt(0));
        if(letterNodes == null){
            return new ArrayList<>();
        }

        input = input.substring(1);

        List<Character> chars = getChars(input);

        if(!chars.isEmpty()) {
            char currentChar = chars.get(0);
            for (LetterNode n : letterNodes) {
                char oppositeCase = Character.isUpperCase(currentChar) ? Character.toLowerCase(currentChar) : Character.toUpperCase(currentChar);
                if(n.getLetter() != currentChar && n.getLetter() != oppositeCase){
                    continue;
                }
                chars.remove(0);
                traverseLetterNode(n, chars, startPoints);
                chars.add(0, currentChar);
            }
        }else{
            startPoints.add(letterNodes);
        }
        return startPoints;
    }

    private void traverseLetterNode(LetterNode n, List<Character> chars,
                                    List<List<LetterNode>> startPoints) {
        if(chars.isEmpty()){
            if(n.getChildren() == null){
                return;
            }
            startPoints.add(n.getChildren());
            return;
        }
        if(n.getChildren() == null){
            return;
        }
        for(LetterNode child : n.getChildren()){
            char oppositeCase = Character.isUpperCase(chars.get(0)) ? Character.toLowerCase(chars.get(0)) : Character.toUpperCase(chars.get(0));
            if(child.getLetter() == chars.get(0) || child.getLetter() == oppositeCase){
                if(child.getChildren() == null){
                    startPoints.add(new ArrayList<>());
                }else{
                    char c = chars.get(0);
                    chars.remove(0);
                    traverseLetterNode(child, chars, startPoints);
                    chars.add(0, c);
                }
            }

        }
    }

    private void findWords(ArrayList<Completion> foundWords, List<LetterNode> startPoint, String baseOfWord) {
        if(startPoint == null){
            return;
        }

        for(LetterNode n : startPoint){
            if(n.isLastLetterOfAWord()){
                System.out.println(baseOfWord + n.getLetter());
                foundWords.add(new Completion(baseOfWord + n.getLetter(), n.getCompletionType()));
            }
            if(n.getChildren() != null){
                findWords(foundWords, n.getChildren(), baseOfWord + n.getLetter());
            }
        }
    }

    private StringBuilder findBase(LetterNode n, String wordSoFar) {
        StringBuilder baseBuilder = new StringBuilder(wordSoFar);
        baseBuilder.append(n.getLetter());
        if(n.getParent() != null){
            baseBuilder = findBase(n.getParent(), baseBuilder.toString());
        }
        return baseBuilder;
    }

    public void printOut() {
        for(char c : startLetters.keySet()){
            ArrayList<LetterNode> letterNodes = startLetters.get(c);
            for(LetterNode n : letterNodes){
                printLetterNodes(String.valueOf(c), n);
            }
        }
    }

    private void printLetterNodes(String word, LetterNode n) {
        word += n.getLetter();
        if(n.isLastLetterOfAWord()){
            //System.out.println(word);
        }
        if(n.getChildren() != null) {
            for(LetterNode child : n.getChildren()){
                printLetterNodes(word, child);
            }
        }
    }

    public void addWords(List<Completion> completions) {
        for(Completion completion : completions){
            List<Character> chars = getChars(completion.getName());
            ArrayList<LetterNode> secondLetterList;
            if(startLetters.containsKey(chars.get(0))) {
                secondLetterList = startLetters.get(chars.get(0));
            } else {
                secondLetterList = new ArrayList<>();
                startLetters.put(chars.get(0), secondLetterList);
            }
            chars.remove(0);
            addAllLetters(secondLetterList, chars, completion.getCompletionType(), null);
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

    private void addAllLetters(List<LetterNode> siblings, List<Character> chars, CompletionType completionType,
                               LetterNode parent) {
        if(chars.isEmpty()) return;

        if(siblings.size() == 0){ //if there are no siblings
            char c = chars.get(0);
            chars.remove(0);
            if(!chars.isEmpty()){ //if there are more letters to add
                LetterNode newNode = new LetterNode(c, false, null, parent);
                siblings.add(newNode);
                addAllLetters(siblings.get(0).getChildren(), chars, completionType, newNode);
            }else{
                siblings.add(new LetterNode(c, true, completionType, parent));
            }
        } else { //if there are siblings
            for(LetterNode sibling : siblings){ //check if the letter exists
                if(sibling.getLetter() == chars.get(0)){
                    if(sibling.getChildren() == null){
                        sibling.createChildrenList();
                    }else{
                        if(chars.size() == 1){
                            sibling.setLastLetterOfAWord(true);
                            sibling.setCompletionType(completionType);
                        }
                    }
                    chars.remove(0);
                    addAllLetters(sibling.getChildren(), chars, completionType, sibling);
                    return;
                }
            }
            //if the letter doesn't exist
            char c = chars.get(0);
            chars.remove(0);
            if(!chars.isEmpty()) {
                LetterNode n = new LetterNode(c, false, null, parent);
                siblings.add(n);
                addAllLetters(n.getChildren(), chars, completionType, n);
            } else {
                siblings.add(new LetterNode(c, true, completionType, parent));
            }
        }
    }

    public void clear() {
        startLetters.clear();
    }
}
