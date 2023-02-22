package main.java.zenit.zencodearea;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VariableTimerTask extends TimerTask {
    private final List<String> existingClasses;
    private final ZenCodeArea zenCodeArea;

    private final ArrayList<String> foundVariables;

    private final Completion completion;
    public VariableTimerTask(ZenCodeArea zenCodeArea, List<String> existingClasses, Completion completion) {
        this.zenCodeArea = zenCodeArea;
        this.existingClasses = existingClasses;
        this.completion = completion;
        foundVariables = new ArrayList<>();
    }

    @Override
    public void run() {
        String code = zenCodeArea.getText();

        //TODO: lyssna efter ändringar på existingClasses och lyft ut stringbuildern till en fältvariabel istället?
        StringBuilder regex = new StringBuilder("\\s*(final\\s+|static\\s+|abstract\\s+|synchronized\\s+|transient\\s+|strictfp\\s+|native\\s+|private\\s+|public\\s+|protected\\s+|default\\s+)?(final\\s+|static\\s+|abstract\\s+|synchronized\\s+|transient\\s+|strictfp\\s+|native\\s+|private\\s+|public\\s+|protected\\s+|default\\s+)?(int\\s+|double\\s+|float\\s+|short\\s+|long\\s+|boolean\\s+|char\\s+|byte\\s+|String\\s+|Integer\\s+|Double\\s+|Character\\s+|Boolean\\s+");

        if (existingClasses != null) {
            for(String name : existingClasses) {
                regex.append("|").append(name).append("\\s+");
            }
        }

        regex.append(")([a-zA-Z_][a-zåäöA-ZÅÄÖ0-9_,\\s]*)+\\s*[;=]");
        Pattern pattern = Pattern.compile(regex.toString());

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

        completion.clear();
        completion.addWords(foundVariables);
        System.out.println("In graph now:");
        completion.printOut();
    }
}
