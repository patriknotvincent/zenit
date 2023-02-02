package main.java.zenit.exceptions;

/**
 * To be thrown when CodeSnippets are being accessed with a non-existing TypeCode.
 * @author Alexander Libot
 *
 */

/**
 * Är denna klassen verkligen nödvändig??
 * Det är väl egentligen bara att ha en vanlig Exception????!!
 * @author Kristoffer
 */
@SuppressWarnings("serial")
public class TypeCodeException extends Exception {
	
	public TypeCodeException() {
		super();
	}

	public TypeCodeException(String message) {
		super(message);
	}
}
