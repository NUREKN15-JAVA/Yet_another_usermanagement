package ua.nure.baranov.gui;

import java.awt.Component;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import com.mockobjects.dynamic.Mock;
import junit.extensions.jfcunit.finder.NamedComponentFinder;
import ua.nure.baranov.User;
import ua.nure.baranov.db.DAOFactory;
import ua.nure.baranov.db.MockDAOFactory;
import ua.nure.baranov.gui.util.Messages;
import junit.extensions.jfcunit.JFCTestCase;
import junit.extensions.jfcunit.JFCTestHelper;
import junit.extensions.jfcunit.eventdata.MouseEventData;
import junit.extensions.jfcunit.eventdata.StringEventData;

public class MainFrameTest extends JFCTestCase{
	private MainFrame mainFrame;
	private Mock mockUserDAO;
	private List<User> users;
	private final User jonSnow;
	private final User expectedUser;
	private final User userInTable;
	
	private Component find(Class<?> componentClass, String name) {
		NamedComponentFinder finder = new NamedComponentFinder(componentClass, name);
		finder.setWait(0);
		Component component = finder.find(mainFrame, 0);
			assertNotNull("Could not find component " + name ,component);
		return component;
	}
	
	public void testBrowseControl() {
		
		find(JPanel.class,"browsePanel");
		JTable table = (JTable) find(JTable.class,"userTable");
		assertEquals(3, table.getColumnCount());
		assertEquals(Messages.getString("UserTableModel.id"),table.getColumnName(0));
		assertEquals(Messages.getString("UserTableModel.first_name"),table.getColumnName(1));
		assertEquals(Messages.getString("UserTableModel.last_name"),table.getColumnName(2));
		assertEquals(1,table.getRowCount());
		find(JButton.class, "addButton");
		find(JButton.class, "editButton");
		find(JButton.class, "deleteButton");
		find(JButton.class, "detailsButton");
	}
	
	public void testAddUser() {
	
		JButton addButton = (JButton) find(JButton.class, "addButton");
		
		JTable table = (JTable) find(JTable.class, "userTable"); 
		assertEquals(1, table.getRowCount());
		
		
		mockUserDAO.expectAndReturn("findAll", users);
		getHelper().enterClickAndLeave(new MouseEventData(this, addButton));
		
		find(JPanel.class, "addPanel");
	
		
		
		fillField(jonSnow.getFirstName(),jonSnow.getLastName(),jonSnow.getBirthDay());
		
		find(JButton.class, "cancelButton");
		JButton okButton = (JButton) find(JButton.class, "okButton");
		
		mockUserDAO.expectAndReturn("create", jonSnow, expectedUser);
		//emulation of creating user
		users.add(expectedUser);
		mockUserDAO.expectAndReturn("findAll", users);
		getHelper().enterClickAndLeave(new MouseEventData(this,okButton));
		
		
		find(JPanel.class,"browsePanel");
		table = (JTable) find(JTable.class,"userTable");
		assertEquals(2,table.getRowCount());
		
	
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		initDAO();
		setHelper(new JFCTestHelper());
		mainFrame = new MainFrame();
		mainFrame.setVisible(true);
	}

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

	private void fillField(String firstName, String lastName, Calendar calendar) {
		JTextField firstNameField = (JTextField) find(JTextField.class,"firstNameField");
		JTextField lastNameField = (JTextField) find(JTextField.class,"lastNameField");
		JTextField birthDayField = (JTextField) find(JTextField.class,"birthdayField");
		getHelper().sendString(new StringEventData(this,firstNameField,firstName));
		getHelper().sendString(new StringEventData(this,lastNameField,lastName));
		SimpleDateFormat format = new SimpleDateFormat("yyyy.mm.dd");
		String birthday = format.format(calendar.getTime());
		System.out.println(birthday);
		getHelper().sendString(new StringEventData(this,birthDayField, birthday));
	}
	
	
	public MainFrameTest() {
		String firstName = "Jon";
		String lastName = "Snow";
		Calendar calendar = Calendar.getInstance();
		calendar.set(1968, Calendar.JANUARY,1);
		jonSnow = new User(firstName, lastName, calendar);
		userInTable = new User(new Long(1), firstName, lastName, calendar);
		expectedUser = new User(new Long(1000), firstName, lastName, calendar);
	}
	
}

