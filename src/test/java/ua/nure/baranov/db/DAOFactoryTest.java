package ua.nure.baranov.db;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

/**
 * Class-tester for {@link DAOFactoryImpl} class.
 * 
 * @author Yevhenii Baranov
 *
 */
public class DAOFactoryTest {
	/**
	 * Testing method for {@link DAOFactoryImpl#getUserDAO()}
	 */
	@Test
	public void UserDAOGetterTest() {
		DAOFactory factory = DAOFactory.getInstance();
		assertNotNull(factory);
		UserDAO dao = factory.getUserDAO();
		assertNotNull(dao);
	}
}
