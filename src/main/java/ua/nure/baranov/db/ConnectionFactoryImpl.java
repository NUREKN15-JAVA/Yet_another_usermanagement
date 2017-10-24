package ua.nure.baranov.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactoryImpl implements ConnectionFactory {

	private String driver;
	private String url;
	private String username;
	private String password;

	public ConnectionFactoryImpl(String driver, String url, String username, String password) {
		this.driver = driver;
		this.url = url;
		this.username = username;
		this.password = password;	
	}

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
