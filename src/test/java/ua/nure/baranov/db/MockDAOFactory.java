package ua.nure.baranov.db;

import com.mockobjects.dynamic.Mock;
/**
 * Mock version of DAOFactory to use it without real connection with database (e.g. when testing GUI). 
 * @author Yevhenii Baranov
 *
 */
public class MockDAOFactory extends DAOFactory {
	private Mock mockUserDAO;
	
	public MockDAOFactory() {
		mockUserDAO = new Mock(UserDAO.class);
	}
	
	public Mock getMockUserDAO() {
		return mockUserDAO;
	}
	
	@Override
	public UserDAO getUserDAO() {
		return (UserDAO)mockUserDAO.proxy();
	}

}
