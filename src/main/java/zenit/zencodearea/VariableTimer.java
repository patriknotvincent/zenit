package main.java.zenit.zencodearea;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Timer;

public class VariableTimer {
    private Timer timer;
    private ZenCodeArea zenCodeArea;

    public VariableTimer(ZenCodeArea zenCodeArea) {
        this.zenCodeArea = zenCodeArea;
        timer = new Timer();
    }

    public void reset() {
        if(zenCodeArea.isFocused()) {
            timer.cancel();
            timer = new Timer();
            timer.schedule(new VariableTimerTask(zenCodeArea), 500);
        }
    }
}
