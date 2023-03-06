package zenit.zencodearea.codecompletion;

import zenit.zencodearea.ZenCodeArea;

import java.util.List;
import java.util.Timer;

/**
 * Timer for updating variable and method completion suggestions.
 */
public class VariableTimer {
    private Timer timer;
    private final ZenCodeArea zenCodeArea;
    private final CompletionGraph completionGraph;

    private String variableRegex;

    private String methodRegex;

    public VariableTimer(ZenCodeArea zenCodeArea, CompletionGraph completionGraph, List<String> existingClasses) {
        this.zenCodeArea = zenCodeArea;
        timer = new Timer();
        this.completionGraph = completionGraph;
        updateRegex(existingClasses);
    }

    public void reset() {
        if(zenCodeArea.isFocused()) {
            timer.cancel();
            timer = new Timer();
            timer.schedule(new VariableTimerTask(zenCodeArea,
                    completionGraph, variableRegex, methodRegex), 250);
        }
    }

    /**
     * Updates the regex for finding variables and methods.
     * @param existingClasses List of existing classes in the project.
     */
    public void updateRegex(List<String> existingClasses) {
        StringBuilder variableBuilder = new StringBuilder("\\s*(final\\s+|static\\s+|abstract\\s+|synchronized\\s+|transient" +
                "\\s+|strictfp\\s+|native\\s+|private\\s+|public\\s+|protected\\s+|default\\s+)?" +
                "(final\\s+|static\\s+|abstract\\s+|synchronized\\s+|transient\\s+|strictfp\\s+|native\\s" +
                "+|private\\s+|public\\s+|protected\\s+|default\\s+)?" +
                "(int\\s+|double\\s+|float\\s+|short\\s+|long\\s+|boolean\\s+|char\\s+|byte\\s+|String\\s" +
                "+|Integer\\s+|Double\\s+|Character\\s+|Boolean\\s+");

        StringBuilder methodBuilder = new StringBuilder("\\s*(final\\s+|static\\s+|abstract\\s+|synchronized\\s+|transient" +
                "\\s+|strictfp\\s+|native\\s+|private\\s+|public\\s+|protected\\s+|default\\s+)?" +
                "(final\\s+|static\\s+|abstract\\s+|synchronized\\s+|transient\\s+|strictfp\\s+|native\\s" +
                "+|private\\s+|public\\s+|protected\\s+|default\\s+)?" +
                "(int\\s+|double\\s+|float\\s+|short\\s+|long\\s+|boolean\\s+|char\\s+|byte\\s+|String\\s" +
                "+|Integer\\s+|Double\\s+|Character\\s+|Boolean\\s+|void\\s+");

        if (existingClasses != null) {
            for(String name : existingClasses) {
                variableBuilder.append("|").append(name).append("\\s+");
                methodBuilder.append("|").append(name).append("\\s+");
            }
        }
        variableBuilder.append(")([a-zA-Z_][a-zåäöA-ZÅÄÖ0-9_]*(,\\s*[a-zA-Z_][a-zåäöA-ZÅÄÖ0-9_]*)*)+\\s*(=\\s*\\S+.*)?;");
        methodBuilder.append(")([a-zA-Z_][a-zåäöA-ZÅÄÖ0-9_]*)\\s*\\(.*\\)\\s*\\{[\\S\\s]*\\}");

        variableRegex = variableBuilder.toString();
        methodRegex = methodBuilder.toString();
    }
}
