package ua.nure.baranov.db;

import java.io.IOException;
import java.util.Properties;

public class DAOFactory {
	private final Properties properties;
    private final static DAOFactory INSTANCE = new DAOFactory();
 
	private DAOFactory() {
		try {
			properties = new Properties();
			properties.load(getClass().getClassLoader().getResourceAsStream("settings.properties"));
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage());
		}
	}
   
    public static DAOFactory getInstance() {
        return INSTANCE;
    }
	
	
	private ConnectionFactory getConnectionFactory() {
		String user = properties.getProperty("connection.user");
		String password = properties.getProperty("connection.password");
		String driver = properties.getProperty("connection.driver");
		String url = properties.getProperty("connection.url");
		return new ConnectionFactoryImpl(driver, url, user, password);
	}

	public UserDAO getUserDAO() {
		UserDAO userDAO;
		try {
			Class clazz = Class
					.forName(properties.getProperty("dao.ua.nure.baranov.db.UserDao"));
			userDAO = (UserDAO) clazz.newInstance();
			ConnectionFactory factory = getConnectionFactory();
			userDAO.setConnectionFactory(factory);
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
		return userDAO;

	}
}
