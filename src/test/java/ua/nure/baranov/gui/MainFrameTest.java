package ua.nure.baranov.gui;

import java.awt.Component;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import com.mockobjects.dynamic.Mock;

import junit.extensions.jfcunit.finder.AbstractButtonFinder;
import junit.extensions.jfcunit.finder.DialogFinder;
import junit.extensions.jfcunit.finder.NamedComponentFinder;
import ua.nure.baranov.User;
import ua.nure.baranov.db.DAOFactory;
import ua.nure.baranov.db.MockDAOFactory;
import ua.nure.baranov.gui.util.Messages;
import junit.extensions.jfcunit.JFCTestCase;
import junit.extensions.jfcunit.JFCTestHelper;
import junit.extensions.jfcunit.eventdata.JTableMouseEventData;
import junit.extensions.jfcunit.eventdata.MouseEventData;
import junit.extensions.jfcunit.eventdata.StringEventData;

/**
 * Test case for whole gui package in general and for the {@link MainFrame}
 * class as the starting point.
 * 
 * @author Yevhenii Baranov
 *
 */
public class MainFrameTest extends JFCTestCase {
	private static final int NEW_USER_ID = 1000;
	private static final int USER_IN_TABLE_ID = 1;
	private static final String TESTUSER_LASTNAME = "Cosmos";
	// Additional part of Yuri's last name
	private static final String LASTNAME_UPDATED_PART = "ov";
	private static final String TESTUSER_FIRSTNAME = "Yuri";
	private MainFrame mainFrame;
	private Mock mockUserDAO;
	private List<User> users;
	private final User jonSnow;
	private final User expectedUser;
	private final User userInTable;
	private final User updatedUser;

	/**
	 * Utility method, finds component of componentClass with given name and returns
	 * it. If there is no such component found, method fails current test.
	 * 
	 * @param componentClass
	 *            Class of the component
	 * @param name
	 *            Name of the component
	 */
	private Component find(Class<? extends Component> componentClass, String name) {
		NamedComponentFinder finder = new NamedComponentFinder(componentClass, name);
		finder.setWait(0);
		Component component = finder.find(mainFrame, 0);
		assertNotNull("Could not find component " + name, component);
		return component;
	}
	/**
	 * Tests {@link BrowsePanel} class
	 */
	public void testBrowseControl() {
		find(JPanel.class, "browsePanel");
		JTable table = (JTable) find(JTable.class, "userTable");
		assertEquals(3, table.getColumnCount());
		assertEquals(Messages.getString("UserTableModel.id"), table.getColumnName(0));
		assertEquals(Messages.getString("first_name"), table.getColumnName(1));
		assertEquals(Messages.getString("last_name"), table.getColumnName(2));
		assertEquals(1, table.getRowCount());
		find(JButton.class, "addButton");
		find(JButton.class, "editButton");
		find(JButton.class, "deleteButton");
		find(JButton.class, "detailsButton");
	}
	/**
	 * Tests {@link AddPanel} class, makes changes in table.
	 */
	public void testAddUser() {
		JButton addButton = (JButton) find(JButton.class, "addButton");
		JTable table = (JTable) find(JTable.class, "userTable");
		assertEquals(1, table.getRowCount());

		getHelper().enterClickAndLeave(new MouseEventData(this, addButton));

		find(JPanel.class, "addPanel");
		fillField(jonSnow.getFirstName(), jonSnow.getLastName(), jonSnow.getBirthDay());

		find(JButton.class, "cancelButton");
		JButton okButton = (JButton) find(JButton.class, "okButton");

		mockUserDAO.expectAndReturn("create", jonSnow, expectedUser);

		// emulation of creating user
		users.add(expectedUser);
		mockUserDAO.expectAndReturn("findAll", users);
		getHelper().enterClickAndLeave(new MouseEventData(this, okButton));

		find(JPanel.class, "browsePanel");
		table = (JTable) find(JTable.class, "userTable");
		assertEquals(2, table.getRowCount());

	}
	/**
	 * Tests {@link AddPanel} class, doesn't make any changes.
	 */
	public void testAddAndCancel() {

		JButton addButton = (JButton) find(JButton.class, "addButton");

		JTable table = (JTable) find(JTable.class, "userTable");
		assertEquals(1, table.getRowCount());

		getHelper().enterClickAndLeave(new MouseEventData(this, addButton));

		find(JPanel.class, "addPanel");

		JButton cancelButton = (JButton) find(JButton.class, "cancelButton");
		find(JButton.class, "okButton");

		mockUserDAO.expectAndReturn("findAll", users);
		getHelper().enterClickAndLeave(new MouseEventData(this, cancelButton));

		find(JPanel.class, "browsePanel");
		table = (JTable) find(JTable.class, "userTable");
		assertEquals(1, table.getRowCount());

	}
	/**
	 * Tests {@link DetailsPanel} class.
	 */
	public void testDetailsPanel() {
		JButton detailsButton = (JButton) find(JButton.class, "detailsButton");

		JTable table = (JTable) find(JTable.class, "userTable");
		assertEquals(1, table.getRowCount());

		getHelper().enterClickAndLeave(new JTableMouseEventData(this, table, 0, 0, 1));
		mockUserDAO.expectAndReturn("find", 1L, userInTable);
		getHelper().enterClickAndLeave(new MouseEventData(this, detailsButton));

		find(JPanel.class, "detailsPanel");
		JLabel firstNameLabel = (JLabel) find(JLabel.class, "firstNameLabel");
		JLabel lastNameLabel = (JLabel) find(JLabel.class, "lastNameLabel");
		JLabel birthdayLabel = (JLabel) find(JLabel.class, "birthdayLabel");

		assertEquals(TESTUSER_FIRSTNAME, firstNameLabel.getText());
		assertEquals(TESTUSER_LASTNAME, lastNameLabel.getText());
		assertEquals("01/01/1968", birthdayLabel.getText());

		JButton okButton = (JButton) find(JButton.class, "okButton");
		mockUserDAO.expectAndReturn("findAll", users);
		getHelper().enterClickAndLeave(new MouseEventData(this, okButton));

		find(JPanel.class, "browsePanel");
		table = (JTable) find(JTable.class, "userTable");
		assertEquals(1, table.getRowCount());
	}
	/**
	 * Tests delete function in the {@link BrowsePanel} class.
	 */
	public void testDeletion() {
		JButton deleteButton = (JButton) find(JButton.class, "deleteButton");

		JTable table = (JTable) find(JTable.class, "userTable");
		assertEquals(1, table.getRowCount());
		getHelper().enterClickAndLeave(new JTableMouseEventData(this, table, 0, 0, 1));
		getHelper().enterClickAndLeave(new MouseEventData(this, deleteButton));

		List<?> deleteDialog = new DialogFinder("Delete user").findAll();
		assertEquals(1, deleteDialog.size());

		mockUserDAO.expect("delete", userInTable);
		mockUserDAO.expectAndReturn("findAll", users);

		AbstractButtonFinder abf = new AbstractButtonFinder("OK");
		JButton okButton = (JButton) abf.find();
		users.remove(userInTable);
		getHelper().enterClickAndLeave(new MouseEventData(this, okButton));

		assertEquals(0, table.getRowCount());
	}
	/**
	 * Tests cancel option of deletion.
	 */
	public void testDeleteAndCancel() {
		JButton deleteButton = (JButton) find(JButton.class, "deleteButton");

		JTable table = (JTable) find(JTable.class, "userTable");
		assertEquals(1, table.getRowCount());
		getHelper().enterClickAndLeave(new JTableMouseEventData(this, table, 0, 0, 1));
		getHelper().enterClickAndLeave(new MouseEventData(this, deleteButton));

		List<?> deleteDialog = new DialogFinder("Delete user").findAll();
		assertEquals(1, deleteDialog.size());

		AbstractButtonFinder abf = new AbstractButtonFinder("Cancel");
		JButton cancelButton = (JButton) abf.find();
		getHelper().enterClickAndLeave(new MouseEventData(this, cancelButton));

		find(JTable.class, "userTable");
		assertEquals(1, table.getRowCount());
	}
	
	// TODO: rewrite with usage of another mock library.
	// It's currently impossible to test changes in table with mockobjects
	/**
	 * Tests {@link EditPanel}, makes changes in the table.
	 */
	public void testEditUser() {
		JButton editButton = (JButton) find(JButton.class, "editButton");

		JTable table = (JTable) find(JTable.class, "userTable");
		assertEquals(1, table.getRowCount());
		getHelper().enterClickAndLeave(new JTableMouseEventData(this, table, 0, 0, 1));
		getHelper().enterClickAndLeave(new MouseEventData(this, editButton));

		find(JPanel.class, "editPanel");
		// Only adding necessary parts, all data about user is already present
		fillField(null, LASTNAME_UPDATED_PART, null);
		JButton okButton = (JButton) find(JButton.class, "okButton");

		mockUserDAO.expect("update", updatedUser);
		users.remove(userInTable);
		users.add(updatedUser);
		mockUserDAO.expectAndReturn("findAll", users);

		getHelper().enterClickAndLeave(new MouseEventData(this, okButton));

		table = (JTable) find(JTable.class, "userTable");
		assertEquals(1, table.getRowCount());

	}

	/**
	 * Tests {@link EditPanel}, doesn't make any changes.
	 */
	public void testEditAndCancel() {
		JButton editButton = (JButton) find(JButton.class, "editButton");
		JTable table = (JTable) find(JTable.class, "userTable");
		assertEquals(1, table.getRowCount());
		getHelper().enterClickAndLeave(new JTableMouseEventData(this, table, 0, 0, 1));
		getHelper().enterClickAndLeave(new MouseEventData(this, editButton));

		find(JPanel.class, "editPanel");

		JButton cancelButton = (JButton) find(JButton.class, "cancelButton");

		mockUserDAO.expectAndReturn("findAll", users);

		getHelper().enterClickAndLeave(new MouseEventData(this, cancelButton));

		table = (JTable) find(JTable.class, "userTable");
		assertEquals(1, table.getRowCount());
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		initDAO();
		setHelper(new JFCTestHelper());
		mainFrame = new MainFrame();
		mainFrame.setVisible(true);
	}

	/**
	 * Gets and initializes mock userDAO for each test method.
	 */
	private void initDAO() {
		users = new ArrayList<>();
		users.add(userInTable);
		Properties daoProperties = new Properties();
		daoProperties.setProperty("dao.factory", MockDAOFactory.class.getName());
		DAOFactory.init(daoProperties);
		mockUserDAO = ((MockDAOFactory) DAOFactory.getInstance()).getMockUserDAO();
		mockUserDAO.expectAndReturn("findAll", users);
	}

	@Override
	protected void tearDown() throws Exception {
		try {
			mockUserDAO.verify();
			mainFrame.setVisible(false);
			JFCTestHelper.cleanUp(this);
			super.tearDown();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Fills all fields in the create and edit windows.
	 * 
	 * @param firstName
	 *            First name of user.
	 * @param lastName
	 *            Last name of user.
	 * @param calendar
	 *            User's birthday.
	 */
	private void fillField(String firstName, String lastName, Calendar calendar) {
		JTextField firstNameField = (JTextField) find(JTextField.class, "firstNameField");
		JTextField lastNameField = (JTextField) find(JTextField.class, "lastNameField");
		JTextField birthDayField = (JTextField) find(JTextField.class, "birthdayField");
		if (firstName != null) {
			getHelper().sendString(new StringEventData(this, firstNameField, firstName));

		}
		if (lastName != null) {
			getHelper().sendString(new StringEventData(this, lastNameField, lastName));
		}
		if (calendar != null) {
			SimpleDateFormat format = new SimpleDateFormat(Messages.format);
			String birthday = format.format(calendar.getTime());
			getHelper().sendString(new StringEventData(this, birthDayField, birthday));
		}
	}

	public MainFrameTest() {
		Calendar calendar = Calendar.getInstance();
		calendar.clear();
		calendar.set(1968, Calendar.JANUARY, 1);
		jonSnow = new User(TESTUSER_FIRSTNAME, TESTUSER_LASTNAME, calendar);
		userInTable = new User(new Long(USER_IN_TABLE_ID), TESTUSER_FIRSTNAME, TESTUSER_LASTNAME, calendar);
		expectedUser = new User(new Long(NEW_USER_ID), TESTUSER_FIRSTNAME, TESTUSER_LASTNAME, calendar);
		updatedUser = new User(userInTable.getId(), TESTUSER_FIRSTNAME, TESTUSER_LASTNAME + LASTNAME_UPDATED_PART,
				calendar);
	}

}
