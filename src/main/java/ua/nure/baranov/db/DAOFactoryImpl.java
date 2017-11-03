package ua.nure.baranov.db;


/**
 * Implementation of DAOFactory that allows to dynamically load UserDAO classes.
 * 
 * @author Yevhenii Baranov
 *
 */
public class DAOFactoryImpl extends DAOFactory {


	@Override
	public UserDAO getUserDAO() {
		UserDAO userDAO;
		try {
			Class<?> clazz = Class
					.forName(properties.getProperty(USER_DAO));
			userDAO = (UserDAO) clazz.newInstance();
			ConnectionFactory factory = getConnectionFactory();
			userDAO.setConnectionFactory(factory);
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
		return userDAO;


	}

}
