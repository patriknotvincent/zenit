package main.java.zenit.zencodearea;

import javafx.collections.ListChangeListener;

public class CodeChangeListener<T> implements ListChangeListener<T> {

    ZenCodeArea zenCodeArea;

    public CodeChangeListener(ZenCodeArea zenCodeArea) {
        this.zenCodeArea = zenCodeArea;
    }

    @Override
    public void onChanged(Change<? extends T> change) {
        //System.out.println("Change detected");
        while (change.next()) {
            if (change.wasAdded()) {
                //System.out.println("Added: " + change.getFrom() + "    " + change.getTo() + "     " +
                // zenCodeArea.getText(zenCodeArea.getCurrentParagraph()));
            }
            if (change.wasRemoved()) {
                //System.out.println("Removed: " + change.getRemoved());
            }
        }
    }
}
