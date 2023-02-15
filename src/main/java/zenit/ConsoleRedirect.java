package zenit;

import java.io.PrintStream;

import zenit.console.ConsoleArea;
import zenit.console.ConsoleAreaErrorStream;
import zenit.console.ConsoleAreaOutputStream;

/**
 * This class redirects the PrintStream to given ConsoleArea.
 * 
 * @author siggelabor
 *
 */
public class ConsoleRedirect {

	/**
	 * This method will set the out and error PrintStream to chosen ConsoleArea.
	 * 
	 * @param ta ConsoleArea prints are to be directed to.
	 */
	public ConsoleRedirect(ConsoleArea ta) {
		try {
		
			ConsoleAreaOutputStream socat = new ConsoleAreaOutputStream(ta);
			ConsoleAreaErrorStream tacos = new ConsoleAreaErrorStream(ta);

			PrintStream outPrintStream = new PrintStream(socat);
			PrintStream errPrintStream = new PrintStream(tacos);

			System.setOut(outPrintStream);
			System.setErr(errPrintStream);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	
}