package ua.nure.baranov.agent;

/**
 * Exception that can occur while searching for a user with the help of an
 * agent.
 * 
 * @author Yevhenii Baranov
 *
 */
@SuppressWarnings("serial")
public class SearchException extends Exception {
	public SearchException() {
		super();
	}

	public SearchException(Throwable cause) {
		super(cause);
	}

	public SearchException(String message) {
		super(message);
	}

	public SearchException(String message, Throwable cause) {
		super(message, cause);
	}
}
