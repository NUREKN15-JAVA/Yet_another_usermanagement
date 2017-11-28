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
/**
 * Class-tester for {@link HsqldbUserDAO} class.
 * @author Yevhenii Baranov
 *
 */
public class HsqlUserDAOTest extends DatabaseTestCase {
	private static final String PROPERTIES_PASSWORD = "";
	private static final String PROPERTIES_USER = "sa";
	private static final String PROPERTIES_URL = "jdbc:hsqldb:file:db/usermanagement";
	private static final String PROPERTIES_DRIVER = "org.hsqldb.jdbcDriver";
	private static final int TEST_CREATE_DAY = 1;
	private static final int TEST_CREATE_MONTH = Calendar.JANUARY;
	private static final int TEST_CREATE_YEAR = 2010;
	private static final String TEST_LASTNAME = "Elg";
	private static final String TEST_NAME = "Glen";
	private static final long TEST_FIND_DELETE_ID = 1000L;
	private static final String UPDATED_NAME = "Helen";
	private static final Long WRONG_ID = 10000L;

	private HsqldbUserDAO dao;
	private ConnectionFactory factory;

	@Override
	protected IDatabaseConnection getConnection() throws Exception {
		factory = new ConnectionFactoryImpl(PROPERTIES_DRIVER, PROPERTIES_URL, PROPERTIES_USER, PROPERTIES_PASSWORD);
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

	/**
	 * Testing method for {@link HsqldbUserDAO#create(User)}
	 */
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

	/**
	 * Testing method for {@link HsqldbUserDAO#find(Long)}
	 */
	public void testFind() {
		try {
			User userToCheck = dao.find(TEST_FIND_DELETE_ID);
			assertNotNull(userToCheck);
			assertEquals(TEST_NAME, userToCheck.getFirstName());
			assertEquals(TEST_LASTNAME, userToCheck.getLastName());
		} catch (DatabaseException e) {
			fail(e.getMessage());
		}
	}

	/**
	 * Testing method for {@link HsqldbUserDAO#find(Long) for user with id that is
	 * not present in database.}
	 */
	public void testFindMissingUser() {
		try {
			User userToCheck = dao.find(WRONG_ID);
			assertNull(userToCheck);
		} catch (DatabaseException e) {
			fail(e.getMessage());
		}
	}

	/**
	 * Testing method for {@link HsqldbUserDAO#findAll()}
	 */
	public void testFindAll() {
		try {
			Collection<User> items = dao.findAll();
			assertNotNull("Collection is null", items);
			assertEquals("Collection size doesn't match.", 2, items.size());
		} catch (DatabaseException e) {
			fail(e.getMessage());
		}
	}
	
	
	/**
	 * Testing method for {@link HsqldbUserDAO#delete(User)}
	 */
	public void testDelete() {
		try {
			User userToCheck = dao.find(TEST_FIND_DELETE_ID);
			assertNotNull(userToCheck);
			dao.delete(userToCheck);
			userToCheck = dao.find(TEST_FIND_DELETE_ID);
			assertNull(userToCheck);
		} catch (DatabaseException e) {
			fail(e.getMessage());
		}
	}

	/**
	 * Testing method for {@link HsqldbUserDAO#update(User)}
	 */
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
	
	
	public void testFindByName() {
		try {
			Collection<User> users = dao.find(TEST_NAME, TEST_LASTNAME);
			assertNotNull(users);
			assertEquals(1, users.size());
		} catch (DatabaseException e) {
			e.printStackTrace();
			fail(e.toString());
		}
	}

}
