package ua.nure.baranov.db;

import java.util.Calendar;
import java.util.Collection;
import org.dbunit.DatabaseTestCase;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.XmlDataSet;
import org.dbunit.ext.hsqldb.HsqldbDataTypeFactory;

import ua.nure.baranov.User;

public class HsqlUserDAOTest extends DatabaseTestCase {
	private static final int TEST_CREATE_DAY = 1;
	private static final int TEST_CREATE_MONTH = Calendar.JANUARY;
	private static final int TEST_CREATE_YEAR = 2010;
	private static final String TEST_FIND_DELETE_LASTNAME = "Elg";
	private static final String TEST_FIND_DELETE_NAME = "Glen";
	private static final int TEST_FIND_DELETE_YEAR = 1968;
	private static final int TEST_FIND_DELETE_DAY = 26;
	private static final int TEST_FIND_DELETE_MONTH = Calendar.APRIL;
	private static final long TEST_FIND_DELETE_ID = 1000L;	
	private static final String UPDATED_NAME = "Helen";
	
	private HsqldbUserDAO dao;
	private ConnectionFactory factory;

	
	
	@Override
	protected IDatabaseConnection getConnection() throws Exception {
		factory = new ConnectionFactoryImpl("org.hsqldb.jdbcDriver", "jdbc:hsqldb:file:db/usermanagement", "sa", "");
		return new DatabaseConnection(factory.createConnection());
	}

	@Override
	protected IDataSet getDataSet() throws Exception {
		return new XmlDataSet(getClass().getClassLoader().getResourceAsStream("userDataSet.xml"));
		
	}
	
	@Override
	protected void setUpDatabaseConfig(DatabaseConfig config) {
	    config.setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY, new HsqldbDataTypeFactory());
	}
	
	@Override
	public void setUp() throws Exception {
		super.setUp();
		dao = new HsqldbUserDAO(factory);
		}

	public void testCreate() {
		try {
			User user = new User();
			user.setFirstName("Yevhenii");
			user.setLastName("Baranow");
			Calendar calendar = Calendar.getInstance();
			calendar.set(TEST_CREATE_YEAR, TEST_CREATE_MONTH, TEST_CREATE_DAY);
			user.setBirthDay(calendar);
			assertNull(user.getId());
			User userToCheck = dao.create(user);
			assertNotNull(userToCheck);
			assertNotNull(userToCheck.getId());
			assertEquals(user.getFirstName(), userToCheck.getFirstName());
			assertEquals(user.getLastName(), userToCheck.getLastName());
			assertEquals(user.getBirthDay(), userToCheck.getBirthDay());
		} catch (DatabaseException e) {
			fail(e.toString());
		}
	}

	public void testFind() {
		try {
			User userToCheck = dao.find(TEST_FIND_DELETE_ID);
			assertNotNull(userToCheck);
			assertEquals(TEST_FIND_DELETE_NAME, userToCheck.getFirstName());
			assertEquals(TEST_FIND_DELETE_LASTNAME, userToCheck.getLastName());
		} catch (DatabaseException e) {
			fail(e.getMessage());
		}

	}

	public void testFindAll() {
		try {
			Collection<User> items = dao.findAll();
			assertNotNull("Collection is null", items);
			assertEquals("Collection size doesn't match.", 2, items.size());
		} catch (DatabaseException e) {
			fail(e.getMessage());
		}
	}
	
	
	
	//TODO: THIS IS LAZHA
	public void testDelete() {
		try {
		User user = new User();
		user.setFirstName(TEST_FIND_DELETE_NAME);
		user.setLastName(TEST_FIND_DELETE_LASTNAME);
		Calendar calendar = Calendar.getInstance();
		calendar.set(TEST_FIND_DELETE_YEAR, TEST_FIND_DELETE_MONTH, TEST_FIND_DELETE_DAY);
		user.setBirthDay(calendar);
		user.setId(TEST_FIND_DELETE_ID);
		
		User userToCheck = dao.find(TEST_FIND_DELETE_ID);
		assertNotNull(userToCheck);
		
		dao.delete(user);
		userToCheck = dao.find(TEST_FIND_DELETE_ID);
		assertNull(userToCheck);
		} catch (DatabaseException e) {
			fail(e.getMessage());
		}
	}
	
	public void testUpdateUser() {
        try {
            User glenElg = dao.find(TEST_FIND_DELETE_ID);
            assertNotNull(glenElg);
            glenElg.setFirstName(UPDATED_NAME);
            dao.update(glenElg);
            User updatedUser = dao.find(TEST_FIND_DELETE_ID);
            assertEquals(glenElg.getFirstName(), updatedUser.getFirstName());
        } catch (DatabaseException e) {
            e.printStackTrace();
            fail(e.toString());
}
	}
	
/*	@Override
	protected DatabaseOperation getSetUpOperation() throws Exception {
		return DatabaseOperation.UPDATE;
	}
	@Override
	protected void tearDown() throws Exception {
		// TODO Auto-generated method stub
		super.tearDown();
	}
	@Override
	protected DatabaseOperation getTearDownOperation() throws Exception {
		return DatabaseOperation.DELETE;
	}*/
}
