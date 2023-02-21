package main.java.zenit.zencodearea;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VariableTimerTask extends TimerTask {
    private ZenCodeArea zenCodeArea;
    public VariableTimerTask(ZenCodeArea zenCodeArea) {
        this.zenCodeArea = zenCodeArea;
    }

    @Override
    public void run() {
        HashMap<Character, ArrayList<LetterNode>> startLetters = new HashMap<>();

        String code = zenCodeArea.getText();

        Pattern pattern = Pattern.compile("^\\s*(final\\s+)?(\\w+\\s+)*(\\w+)\\s+(\\w+)(\\s*=.*)?;$", Pattern.MULTILINE);
        Matcher matcher = pattern.matcher(code);
        while (matcher.find()) {
            String variableType = matcher.group(4);
            String variableName = matcher.group(5);
            if(variableType != null && variableType != "" && variableType != "package" && variableType != "import") {
                System.out.println("Found variable declaration: " + variableType + " " + variableName);
            }
        }
    }
}
