package ua.nure.baranov.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
/**
 * Factory implementation, it gives ability to connect to database.
 * @author Yevhenii Baranov
 */
public class ConnectionFactoryImpl implements ConnectionFactory {

	private String driver;
	private String url;
	private String username;
	private String password;

	/**
	 * Creates factory with given set of string parameters, that are needed to open connection with db.
	 * @param driver 	Name of class for database driver.
	 * @param url 	Location of the database.
	 * @param username	Name of db user.
	 * @param password	Password for that user.
	 */
	public ConnectionFactoryImpl(String driver, String url, String username, String password) {
		this.driver = driver;
		this.url = url;
		this.username = username;
		this.password = password;	
	}
	/**
	 * Creates factory with given Properties class, that are needed to open connection with db.
	 * @param properties Properties object with all fields set.<br>
	 * For list of fields see {@link ConnectionFactoryImpl#ConnectionFactoryImpl(String, String, String, String)}
	 */
	public ConnectionFactoryImpl(Properties properties) {
		this.username = properties.getProperty("connection.user");
		this.password = properties.getProperty("connection.password");
		this.driver = properties.getProperty("connection.driver");
		this.url = properties.getProperty("connection.url");
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Connection createConnection() throws DatabaseException {
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException();
		}
		try {
			return DriverManager.getConnection(url, username, password);
		} catch (SQLException e) {
			throw new DatabaseException(e);
		}
	}

}
