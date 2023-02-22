package main.java.zenit.zencodearea;

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

        completion.clear();
        completion.addWords(foundVariables);
        System.out.println("In graph now:");
        completion.printOut();
    }
}
