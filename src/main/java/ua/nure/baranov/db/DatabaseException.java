package ua.nure.baranov.db;

public class DatabaseException extends Exception {
	
	public DatabaseException(Throwable e) {
		super(e);
	}

	public DatabaseException(String string) {
		super(string);
	}

}
