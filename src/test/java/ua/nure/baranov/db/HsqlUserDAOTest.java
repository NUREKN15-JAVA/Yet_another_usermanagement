package ua.nure.baranov.db;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.util.Calendar;

import org.dbunit.DBTestCase;
import org.dbunit.DatabaseTestCase;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.XmlDataSet;
import org.junit.Before;
import org.junit.Test;

import ua.nure.baranov.User;

public class HsqlUserDAOTest extends DatabaseTestCase{
	private HsqldbUserDAO dao;
	private ConnectionFactory factory;
	
	@Override
	protected IDatabaseConnection getConnection() throws Exception {
		return new DatabaseConnection(factory.createConnection());
	}
	
	@Override
	protected IDataSet getDataSet() throws Exception {
		return new XmlDataSet(getClass().getClassLoader().getResourceAsStream("userDataSet.xml"));
       
	}

	
	@Override
	public void setUp() throws Exception {
		factory = new ConnectionFactoryImpl("org.hsqldb.jdbcDriver","jdbc:hsqldb:file:db/usermanagement","sa","");
		dao = new HsqldbUserDAO(factory); 
	
	}

	public void testCreate() {
		try {
		User user = new User();
		user.setFirstName("Yevhenii");
		user.setLastName("Baranow");
		Calendar calendar = Calendar.getInstance();
		calendar.set(2010, Calendar.JANUARY, 1);
		user.setBirthDay(calendar);
		assertNull(user.getId());
		User userToCheck = dao.create(user);
		assertNotNull(userToCheck);
		assertNotNull(userToCheck.getId());
		assertEquals(user.getFirstName(),userToCheck.getFirstName());
		assertEquals(user.getLastName(),userToCheck.getLastName());
		assertEquals(user.getBirthDay(), userToCheck.getBirthDay());
		} catch (DatabaseException e) {
			fail(e.toString());
		}
	}
	
	/*@Test
	public void findTest() {
		try {
			User userToCheck = dao.find(1000L);
			assertNotNull(userToCheck);
			assertEquals("Glen",userToCheck.getFirstName());
			assertEquals("Elg", userToCheck.getLastName());
		} catch (DatabaseException e) {
			fail(e.getMessage());
		}
		
	}*/
}
