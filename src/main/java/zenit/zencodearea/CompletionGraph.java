package zenit.zencodearea;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Represents a graph of all variables and methods in an open tab to efficiently add and find
 * them. Uses a HashMap to store the first letter of each variable/method name and a list of
 * LetterNodes to store the rest of the name. The LetterNodes are connected to each other
 * in a linked list structure.
 */
public class CompletionGraph {
    private final HashMap<Character, ArrayList<LetterNode>> startLetters;

    public CompletionGraph() {
        startLetters = new HashMap<>();
    }

    /**
     * Searches for an input string in the graph.
     * @param input what the user has written so far. See VariableTimerTask for how input is defined.
     * @return a list of all found completions.
     */
    public List<Completion> searchFor(String input) {
        if(input.isEmpty()){
            return new ArrayList<>();
        }
        ArrayList<Completion> foundWords = new ArrayList<>();

        List<List<LetterNode>> startLists = findStartLists(input);
        findWords(input, foundWords, startLists, input.charAt(0));

        //Find opposite case to allow for case-insensitive search. For example, if the user has
        //written "a" and the graph contains "A", the opposite case of "a" is "A" and vice versa.
        char oppositeCase = Character.isUpperCase(input.charAt(0)) ? Character.toLowerCase(input.charAt(0)) : Character.toUpperCase(input.charAt(0));
        startLists = findStartLists(oppositeCase + input.substring(1));
        findWords(input, foundWords, startLists, oppositeCase);

        return foundWords;
    }

    /**
     * From the startLists, finds all words and adds them to the foundWords list.
     * @param input what the user has written of the word so far
     * @param foundWords the list of found words
     * @param startLists the list of lists of LetterNodes that the search should start from
     * @param firstLetter the first letter of the word, case-sensitive
     */
    private void findWords(String input, ArrayList<Completion> foundWords, List<List<LetterNode>> startLists,
                           char firstLetter) {
        if(input.length() != 1) {
            startLists.forEach(list -> {
                if (list != null && !list.isEmpty()) {
                    StringBuilder base;
                    base = findBase(list.get(0).getParent(), "");
                    base.append(firstLetter);
                    base.reverse();

                    buildWords(foundWords, list, base.toString());
                }
            });
        }else if(!startLists.isEmpty()) {
            buildWords(foundWords, startLists.get(0), String.valueOf(firstLetter));
        }
    }

    /**
     * Finds lists of LetterNodes from where later searches should start.
     * @param input what the user has written so far
     * @return a list of startpoints
     */
    private List<List<LetterNode>> findStartLists(String input) {

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

    /**
     * Recursively traverses the graph to find a point from where the search should start
     * based on the user input.
     * @param n the current LetterNode
     * @param chars the remaining characters of the user input
     * @param startPoints the list of startpoints
     */
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
                    //startPoints.add(n.getChildren()); could be used to show completion even when written all
                    // letters
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

    /**
     * Recursively builds words from the startpoints.
     * @param foundWords the list of found words
     * @param startPoint the current startpoint
     * @param baseOfWord the part of the word that has been built so far
     */
    private void buildWords(ArrayList<Completion> foundWords, List<LetterNode> startPoint, String baseOfWord) {
        if(startPoint == null){
            return;
        }

        for(LetterNode n : startPoint){
            if(n.isLastLetterOfAWord()){
                foundWords.add(new Completion(baseOfWord + n.getLetter(), n.getCompletionType()));
            }
            if(n.getChildren() != null){
                buildWords(foundWords, n.getChildren(), baseOfWord + n.getLetter());
            }
        }
    }

    /**
     * Finds the base of all words that start from the given LetterNode.
     * @param n the LetterNode
     * @param wordSoFar the part of the word that has been built so far
     * @return the base of the word so far
     */
    private StringBuilder findBase(LetterNode n, String wordSoFar) {
        StringBuilder baseBuilder = new StringBuilder(wordSoFar);
        baseBuilder.append(n.getLetter());
        if(n.getParent() != null){
            baseBuilder = findBase(n.getParent(), baseBuilder.toString());
        }
        return baseBuilder;
    }

    /**
     * Prints out all words in the graph.
     */
    public void printOut() {
        for(char c : startLetters.keySet()){
            ArrayList<LetterNode> letterNodes = startLetters.get(c);
            for(LetterNode n : letterNodes){
                printLetterNodes(String.valueOf(c), n);
            }
        }
    }

    /**
     * Recursively finds all words in the graph and builds them.
     * @param word the part of the word that has been built so far
     * @param n the current LetterNode
     */
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

    /**
     * Adds a list of words to the graph.
     * @param completions the list of words
     */
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

    /**
     * Creates an array of characters from a string.
     * @param word the string
     * @return the array of characters
     */
    private static List<Character> getChars(String word) {
        char[] chars = word.toCharArray();
        List<Character> letters = new ArrayList<>();
        for(char c : chars){
            letters.add(c);
        }
        return letters;
    }

    /**
     * Recursively adds all letters to the graph.
     * @param siblings the siblings of the current LetterNode
     * @param chars the remaining characters of the word
     * @param completionType the type of completion
     * @param parent the parent of the current LetterNode
     */
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

    /**
     * Clears the graph.
     */
    public void clear() {
        startLetters.clear();
    }
}
