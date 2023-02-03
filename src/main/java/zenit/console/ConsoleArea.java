package main.java.zenit.console;

import org.fxmisc.richtext.InlineCssTextArea;
import javafx.application.Platform;

/**
 * A console area with prints string in two different colors. All error prints in one and all other
 * in one
 * 
 * @author sigge labor , Oskar Molander
 * @editor Erik Svensson
 */
public class ConsoleArea extends InlineCssTextArea {
	
	private String ID;
	private Process process;
	private String fileName;
	
	/*
	 * README
	 * 
	 * Every console needs to be connected to an executed 'main method'. Here, each console get 
	 * an ID, but it's a shitty solution. 
	 */
	
	/**
	 * constructs a new ConsoleArea with identity "UNKNOWN".
	 */
	//remove constructor ConsoleArea. Reason: no usages

	/**
	 * constructs a new ConsoleArea with chosen identity and process. 
	 */
	public ConsoleArea(String identity, Process process, String backgroundColor) {
		this.ID = identity;
		this.process = process;
		
		// TODO maybe remove this one and add to main.css
		getStylesheets().add(getClass().getResource("/zenit/console/consoleStyle.css").toString());
		this.setStyle(backgroundColor);
		this.setEditable(false);
	}
	
	public String getFileName() {
		return this.fileName;
	}
	
	public void setFileName(String name) {
		this.fileName = name;
	}

	//remove function public String getBackgroundColor(). Reason: no usages

	public void setBackgroundColor(String color ) {
		// remove
		this.setStyle(color);
	}
	
	public Process getProcess() {
		return this.process;
	}
	
	public void setProcess(Process p) {
		this.process = p;
	}
	
	/**
	 * @return ID
	 */
	//remove function public String getID(). Reason: no usages

	
	/**
	 * prints out an error text with chosen style (red default) in the console. 
	 * @param stringToPrint string to print
	 */
	public void errPrint(String stringToPrint) {
		Platform.runLater(new Runnable() {
		    @Override
		    public void run() {    	
		    	try {
					appendText(stringToPrint);
					setStyle(getText().length() - stringToPrint.length(), getText().length(), "-fx-fill: red;");
				} catch (IndexOutOfBoundsException e) {
					// Windows bug, don't do anything with the exception.
				}
		    }
		});
	}
	
	/**
	 * prints out a regular text/string in the console.
	 * @param stringToPrint to print
	 */
	public void outPrint(String stringToPrint) {
		
		Platform.runLater(new Runnable() {
		    @Override
		    public void run() {	 
			    	try {
						appendText(stringToPrint);
						setStyle(getText().length() - stringToPrint.length(), getText().length(),
								"-fx-fill: white");
					} catch (IndexOutOfBoundsException e) {
						// Windows bug, don't do anything with the exception.
					}	   
		    }
		});
	}
	
	public void setID(String ID) {
		this.ID = ID;
	}
	
	@Override
	public String toString() {
		return this.ID;
	}
}