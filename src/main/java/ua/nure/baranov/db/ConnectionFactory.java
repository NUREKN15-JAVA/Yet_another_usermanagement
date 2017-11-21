package ua.nure.baranov.db;

import java.sql.Connection;
/**
 * Factory class for creating connection with database.
 * @author Yevhenii Baranov
 *
 */
public interface ConnectionFactory {
	/**
	 * Creates connection with database and returns it.
	 * @return Connection with db.
	 * @throws DatabaseException in case any troubles happen.
	 */
	Connection createConnection() throws DatabaseException;
}
