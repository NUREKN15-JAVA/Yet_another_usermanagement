package ua.nure.baranov.web;

import java.util.Properties;

import com.mockobjects.dynamic.Mock;
import com.mockrunner.servlet.BasicServletTestCaseAdapter;

import ua.nure.baranov.db.DAOFactory;
import ua.nure.baranov.db.MockDAOFactory;
/**
 * Abstract test case for servlet testing with the usage of Mock 
 * @author Yevhenii Baranov
 *
 */
public abstract class MockServletTestCase extends BasicServletTestCaseAdapter {
	
	private Mock mockUserDAO;
	
	
	public Mock getMockUserDAO() {
		return mockUserDAO;
	}

	public void setMockUserDAO(Mock mockUserDAO) {
		this.mockUserDAO = mockUserDAO;
	}

	@Override
	public void setUp() throws Exception {
		super.setUp();
		Properties properties = new Properties();
		properties.setProperty("dao.factory", "ua.nure.baranov.db.MockDAOFactory");
		DAOFactory.init(properties);
		setMockUserDAO(((MockDAOFactory)DAOFactory.getInstance()).getMockUserDAO());
	}
	
	@Override
	public void tearDown() throws Exception {
		getMockUserDAO().verify();
		super.tearDown();
	}
}
