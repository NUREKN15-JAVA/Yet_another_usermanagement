package ua.nure.baranov.db;
/**
 * Exception class that serves as a wrapper for any problem that can occur during work with database.
 * @author Yevhenii Baranov
 *
 */
@SuppressWarnings("serial")
public class DatabaseException extends Exception {
	
	public DatabaseException(Throwable e) {
		super(e);
	}

	public DatabaseException(String string) {
		super(string);
	}

}
