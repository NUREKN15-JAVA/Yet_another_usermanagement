/*package ua.nure.baranov.gui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.awt.Component;
import java.awt.Container;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.text.DateFormatter;

import com.mockobjects.dynamic.Mock;

import javafx.scene.input.MouseEvent;
import junit.extensions.jfcunit.finder.NamedComponentFinder;
import ua.nure.baranov.User;
//import junit.extensions.jfcunit.JFCTestCase;
//import junit.extensions.jfcunit.JFCTestHelper;
public class MainFrameTest{// extends JFCTestCase{
	private Container mainFrame;
	private Mock mockUserDAO;
	private List<User> users;
	private Component find(Class<?> componentClass, String name) {
		NamedComponentFinder finder = new NamedComponentFinder(componentClass, name);
	//	finder.setWait(0);
		Component component = null;//finder.find(mainFrame, 0);
		assertNotNull(component);
		return component;
	}
	
	public void testBrowseControl() {
		find(JPanel.class,"browsePanel");
		JTable table = (JTable) find(JTable.class,"userTable");
		assertEquals(3, table.getColumnCount());
		assertEquals("ID",table.getColumnName(0));
		assertEquals("First name",table.getColumnName(1));
		assertEquals("Last name",table.getColumnName(2));
		assertEquals(2,table.getRowCount());
		find(JButton.class, "addButton");
		find(JButton.class, "editButton");
		find(JButton.class, "deleteButton");
		find(JButton.class, "detailsButton");
	}
	
	public void testAddUser() {
		String firstName = "Jon";
		String lastName = "Snow";
		Calendar calendar = Calendar.getInstance();
		calendar.set(1, Calendar.JANUARY,1968);
		User jonSnow = new User(firstName, lastName, calendar);
		User expectedUser = new User(new Long(1), firstName, lastName, calendar);
		
		mockUserDAO.expectAndReturn("create", jonSnow, expectedUser);
		
		ArrayList<User> users = new ArrayList<>(this.users);
		users.add(expectedUser);
		mockUserDAO.expectAndReturn("findAll", users);
		
		JTable table = (JTable) find(JTable.class, "userTable"); 
		assertEquals(1, table.getRowCount());
		
		JButton addButton = (JButton) find(JButton.class, "addButton");
	//	getHelper().enterClickAndLEave(new MouseEventData(this,addButton));
		
		find(JPanel.class, "addPanel");
		fillField(firstName,lastName,calendar);
		
		find(JButton.class, "cancelButton");
		JButton okButton = (JButton) find(JButton.class, "addButton");
//		getHelper().enterClickAndLeave(new MouseEventData(this,okButton));
		
		find(JPanel.class,"browsePanel");
		table = (JTable) find(JTable.class,"userTable");
		assertEquals(2,table.getRowCount());
		mockUserDAO.verify();
		
	}

	private void fillField(String firstName, String lastName, Calendar calendar) {
		JTextField firstNameField = (JTextField) find(JTextField.class,"firstNameField");
		JTextField lastNameField = (JTextField) find(JTextField.class,"lastNameField");
		JTextField birthDayField = (JTextField) find(JTextField.class,"birthDayField");
	//	getHelper().sendString(new StringEventData(this,firstNameField,firstName));
		//getHelper().sendString(new StringEventData(this,LastNameField,lastName));
//		String birthday = 
	//	getHelper().sendString(new StringEventData(this,birthDayField,));
	}
	

	
	
	
	
	
}

*/