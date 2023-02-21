package main.java.zenit.zencodearea;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.List;
import java.util.Timer;

public class VariableTimer {
    private Timer timer;
    private ZenCodeArea zenCodeArea;
    private List<String> existingClasses;

    public VariableTimer(ZenCodeArea zenCodeArea, List<String> existingClasses) {
        this.zenCodeArea = zenCodeArea;
        this.existingClasses = existingClasses;
        timer = new Timer();
    }

    public void reset() {
        if(zenCodeArea.isFocused()) {
            timer.cancel();
            timer = new Timer();
            timer.schedule(new VariableTimerTask(zenCodeArea, existingClasses), 500);
        }
    }
}
