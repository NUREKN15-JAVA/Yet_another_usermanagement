package ua.nure.baranov.db;

import java.io.IOException;
import java.util.Properties;

/**
 * Abstract factory class for getting connection factory and user DAO. Singletone class, so use {@link #getInstance()} method. 
 * @author effffgen
 *
 */
public abstract class DAOFactory {
	protected static final String USER_DAO = "dao.ua.nure.baranov.db.UserDao";
	protected static Properties properties;
	private static final String DAO_FACTORY = "dao.factory";
	private static DAOFactory INSTANCE;

	protected DAOFactory() {
	}

	static {
		properties = new Properties();
		try {
			properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("settings.properties"));
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage());
		}
	}
	
	/**
	 * The only way to get instance of singletone class DAOFactory.
	 * @return instance of factory.
	 */
	public static synchronized DAOFactory getInstance() {
		if (INSTANCE == null) {
			try {
				Class<?> factoryClass = Class.forName(properties.getProperty(DAO_FACTORY));
				INSTANCE = (DAOFactory) factoryClass.newInstance();
			} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
				throw new RuntimeException(e);
			}
		}

		return INSTANCE;
	}

	/**
	 * Initializes the factory with given properties that are needed to create connection.
	 * @param properties
	 */
	public static void init(Properties properties) {
		DAOFactory.properties = properties;
		INSTANCE = null;
	}
	
	protected ConnectionFactory getConnectionFactory() {
		return new ConnectionFactoryImpl(properties);
	}
	
	/**
	 * 
	 * @return
	 */
	public abstract UserDAO getUserDAO();
}
