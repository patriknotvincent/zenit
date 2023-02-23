package main.java.zenit.zencodearea;

import org.fxmisc.richtext.model.Paragraph;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VariableTimerTask extends TimerTask {
    private final ZenCodeArea zenCodeArea;

    private final ArrayList<String> foundVariables;
    private final Completion completion;

    private final String regex;

    public VariableTimerTask(ZenCodeArea zenCodeArea, Completion completion,
                             String regex) {
        this.zenCodeArea = zenCodeArea;
        this.completion = completion;
        foundVariables = new ArrayList<>();
        this.regex = regex;
    }

    @Override
    public void run() {
        String code = zenCodeArea.getText();

        Pattern pattern = Pattern.compile(regex);

        Matcher matcher = pattern.matcher(code);
        //System.out.println("Searching");
        while (matcher.find()) {
            String variableType = matcher.group(3);
            String variableName = matcher.group(4);

            if(variableName != null) {
                //System.out.println("Found variable declaration: " + variableType + " " + variableName);
                if (variableName.contains(",")) {
                    variableName = variableName.replaceAll("\\s", "");
                    String[] variableNames = variableName.split(",");

                    Collections.addAll(foundVariables, variableNames);
                } else {
                    foundVariables.add(variableName);
                }
            }
        }

        foundVariables.forEach(System.out::println);

        completion.clear();
        completion.addWords(foundVariables);

        String searchString = findStartOfWord();
        //System.out.println("Search string: " + searchString);
        if(searchString.length() > 0) {
            List<String> foundWords = completion.searchFor(searchString);
            zenCodeArea.updateCompletionMenu(foundWords);
        }
    }

    private String findStartOfWord() {
        StringBuilder sb = new StringBuilder();
        String currentParagraph = zenCodeArea.getParagraph(zenCodeArea.getCurrentParagraph()).getText();
        int caretPosition = zenCodeArea.getCaretPosition();
        int caretPositionInParagraph = caretPosition - zenCodeArea.getAbsolutePosition(zenCodeArea.getCurrentParagraph(), 0);
        int currentIndex = caretPositionInParagraph - 1;
        char currentChar = currentParagraph.charAt(currentIndex);
        while(currentChar != ' ' && currentChar != '(' && currentChar != '{' && currentChar != '['
            && currentChar != '.' && currentChar != ',' && currentChar != ';' && currentChar != ':'
            && currentChar != '?' && currentChar != '+' && currentChar != '-' && currentChar != '*'
            && currentChar != '/' && currentChar != '%' && currentChar != '=' && currentChar != '|'
            && currentChar != '&' && currentChar != '>' && currentChar != '~' && currentChar != '<') {
            sb.append(currentChar);
            currentChar = currentParagraph.charAt(--currentIndex);
        }
        sb.reverse();
        return sb.toString();
    }
}
