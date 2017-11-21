package ua.nure.baranov.web;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.junit.Before;
import org.junit.Test;

import ua.nure.baranov.User;

/**
 * Test class, contains tests for {@link AddServlet}
 * 
 * @author Yevhenii Baranov
 *
 */
public class AddServletTest extends MockServletTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();
		createServlet(AddServlet.class);
	}

	/**
	 * Test for adding feature.
	 */
	@Test
	public void testAdd() {
		Calendar calendar = Calendar.getInstance();
		User userToAdd = new User("John", "Doe", calendar);
		User createdUser = new User(new Long(1000L), "John", "Doe", calendar);
		getMockUserDAO().expectAndReturn("create", userToAdd, createdUser);
		addRequestParameter("firstName", "John");
		addRequestParameter("lastName", "Doe");
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		addRequestParameter("birthday", format.format(calendar.getTime()));
		addRequestParameter("okButton", "OK");
		doPost();
	}

	/**
	 * Test the case when the first name is invalid.
	 */
	@Test
	public void testAddEmptyFirstName() {
		Calendar calendar = Calendar.getInstance();
		addRequestParameter("lastName", "Doe");
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		addRequestParameter("birthday", format.format(calendar.getTime()));
		addRequestParameter("okButton", "OK");
		doPost();
		String errorMessage = (String) getWebMockObjectFactory().getMockRequest().getAttribute("error");
		assertNotNull(errorMessage);
		assertNotEquals(0, errorMessage.length());
	}

	/**
	 * Test the case when the last name is invalid.
	 */
	@Test
	public void testAddEmptyLastName() {
		Calendar calendar = Calendar.getInstance();
		addRequestParameter("firstName", "John");
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		addRequestParameter("birthday", format.format(calendar.getTime()));
		addRequestParameter("okButton", "OK");
		doPost();
		String errorMessage = (String) getWebMockObjectFactory().getMockRequest().getAttribute("error");
		assertNotNull(errorMessage);
		assertNotEquals(0, errorMessage.length());
	}

	/**
	 * Tests the case when the date of birth is invalid.
	 */
	@Test
	public void testInvalidDate() {
		addRequestParameter("lastName", "Doe");
		addRequestParameter("firstName", "John");
		addRequestParameter("birthday", "1111111");
		addRequestParameter("okButton", "OK");
		doPost();
		String errorMessage = (String) getWebMockObjectFactory().getMockRequest().getAttribute("error");
		assertNotNull(errorMessage);
		assertNotEquals(0, errorMessage.length());
	}

}
