package ua.nure.baranov.db;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

public class DAOFactoryTest {

	@Test
	public void UserDAOGetterTest() {
		DAOFactory factory = DAOFactory.getInstance();
		assertNotNull(factory);
		UserDAO dao = factory.getUserDAO();
		assertNotNull(dao);
	}
}
