package zenit.zencodearea.codecompletion;

import zenit.zencodearea.ZenCodeArea;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Timer task that searches for variables and methods in the code and
 * updates the completion menu.
 */
public class VariableTimerTask extends TimerTask {
    private final ZenCodeArea zenCodeArea;

    private final ArrayList<Completion> foundVariables;

    private final ArrayList<Completion> foundMethods;
    private final CompletionGraph completionGraph;

    private final String variableRegex;
    private final String methodRegex;

    public VariableTimerTask(ZenCodeArea zenCodeArea, CompletionGraph completionGraph,
                             String variableRegex, String methodRegex) {
        this.zenCodeArea = zenCodeArea;
        this.completionGraph = completionGraph;
        foundVariables = new ArrayList<>();
        foundMethods = new ArrayList<>();
        this.variableRegex = variableRegex;
        this.methodRegex = methodRegex;
    }

    @Override
    public void run() {
        String code = zenCodeArea.getText();

        findVariables(code);
        findMethods(code);

        completionGraph.clear();
        completionGraph.addWords(foundVariables);
        completionGraph.addWords(foundMethods);

        String searchString = findStartOfWord();
        List<Completion> foundWords = completionGraph.searchFor(searchString);
        zenCodeArea.updateCompletionMenu(foundWords, searchString.length());
    }

    /**
     * Finds all variables in the code.
     * @param code The code to search in.
     */
    private void findVariables(String code) {
        Pattern pattern = Pattern.compile(variableRegex);

        Matcher matcher = pattern.matcher(code);
        while (matcher.find()) {
            String variableName = matcher.group(4);

            if(variableName != null) {
                if (variableName.contains(",")) {
                    variableName = variableName.replaceAll("\\s", "");
                    String[] variableNames = variableName.split(",");

                    for(String s : variableNames) {
                        Completion c = new Completion(s, CompletionType.VARIABLE);
                        foundVariables.add(c);
                    }
                } else {
                    foundVariables.add(new Completion(variableName, CompletionType.VARIABLE));
                }
            }
        }
    }

    /**
     * Finds all methods in the code.
     * @param code The code to search in.
     */
    private void findMethods(String code) {
        Pattern pattern = Pattern.compile(methodRegex);

        Matcher matcher = pattern.matcher(code);
        while (matcher.find()) {
            String methodName = matcher.group(4);

            if(methodName != null) {
                foundMethods.add(new Completion(methodName, CompletionType.METHOD));
            }
        }
    }

    /**
     * Finds the start of the word that the caret is currently in.
     * @return The start of the word.
     */
    private String findStartOfWord() {
        StringBuilder sb = new StringBuilder();
        String currentParagraph = zenCodeArea.getParagraph(zenCodeArea.getCurrentParagraph()).getText();
        if(currentParagraph.isEmpty()) {
            return "";
        }
        int caretPosition = zenCodeArea.getCaretPosition();
        int caretPositionInParagraph = caretPosition - zenCodeArea.getAbsolutePosition(zenCodeArea.getCurrentParagraph(), 0);
        int currentIndex = caretPositionInParagraph - 1;
        if(currentIndex < 0) {
            return "";
        }
        char currentChar = currentParagraph.charAt(currentIndex);
        while(currentChar != ' ' && currentChar != '(' && currentChar != '{' && currentChar != '['
            && currentChar != '.' && currentChar != ',' && currentChar != ';' && currentChar != ':'
            && currentChar != '?' && currentChar != '+' && currentChar != '-' && currentChar != '*'
            && currentChar != '/' && currentChar != '%' && currentChar != '=' && currentChar != '|'
            && currentChar != '&' && currentChar != '>' && currentChar != '~' && currentChar != '<') {
            sb.append(currentChar);
            if(currentIndex == 0) {
                break;
            }
            currentChar = currentParagraph.charAt(--currentIndex);
        }
        sb.reverse();
        return sb.toString();
    }
}
