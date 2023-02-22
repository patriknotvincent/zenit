package main.java.zenit.zencodearea;

import java.util.List;
import java.util.Timer;

public class VariableTimer {
    private Timer timer;
    private final ZenCodeArea zenCodeArea;
    private final Completion completion;

    private String regex;

    public VariableTimer(ZenCodeArea zenCodeArea, Completion completion, List<String> existingClasses) {
        this.zenCodeArea = zenCodeArea;
        timer = new Timer();
        this.completion = completion;
        updateRegex(existingClasses);
    }

    public void reset() {
        if(zenCodeArea.isFocused()) {
            timer.cancel();
            timer = new Timer();
            timer.schedule(new VariableTimerTask(zenCodeArea,
                    completion, regex), 750);
        }
    }

    public void updateRegex(List<String> existingClasses) {
        StringBuilder sb = new StringBuilder("\\s*(final\\s+|static\\s+|abstract\\s+|synchronized\\s+|transient" +
                "\\s+|strictfp\\s+|native\\s+|private\\s+|public\\s+|protected\\s+|default\\s+)?(final\\s+|static\\s+|abstract\\s+|synchronized\\s+|transient\\s+|strictfp\\s+|native\\s+|private\\s+|public\\s+|protected\\s+|default\\s+)?(int\\s+|double\\s+|float\\s+|short\\s+|long\\s+|boolean\\s+|char\\s+|byte\\s+|String\\s+|Integer\\s+|Double\\s+|Character\\s+|Boolean\\s+");

        if (existingClasses != null) {
            for(String name : existingClasses) {
                sb.append("|").append(name).append("\\s+");

            }
        }

        sb.append(")([a-zA-Z_][a-zåäöA-ZÅÄÖ0-9_,\\s]*)+\\s*[;=]");

        regex = sb.toString();
    }
}
