package main.java.zenit.zencodearea;

import java.util.List;
import java.util.Timer;

public class VariableTimer {
    private Timer timer;
    private ZenCodeArea zenCodeArea;
    private List<String> existingClasses;

    private Completion completion;

    public VariableTimer(ZenCodeArea zenCodeArea, List<String> existingClasses, Completion completion) {
        this.zenCodeArea = zenCodeArea;
        this.existingClasses = existingClasses;
        timer = new Timer();
        this.completion = completion;
    }

    public void reset() {
        if(zenCodeArea.isFocused()) {
            timer.cancel();
            timer = new Timer();
            timer.schedule(new main.java.zenit.zencodearea.VariableTimerTask(zenCodeArea, existingClasses, completion), 500);
        }
    }
}
