package ua.nure.baranov.web;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.junit.Before;
import org.junit.Test;

import ua.nure.baranov.User;
/**
 * Test class, contains tests for {@link EditServlet}
 * 
 * @author Yevhenii Baranov
 *
 */
public class EditServletTest extends MockServletTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();
		createServlet(EditServlet.class);
	}
	
	/**
	 * Tests basic functionality
	 */
	@Test
	public void testEdit() {
		Calendar calendar = Calendar.getInstance();
		User user = new User(new Long(1000L),"John", "Doe",calendar);
		getMockUserDAO().expect("update", user);
		addRequestParameter("id","1000");
		addRequestParameter("firstName","John");
		addRequestParameter("lastName","Doe");
		SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
		addRequestParameter("birthday",format.format(calendar.getTime()));
		addRequestParameter("okButton","OK");
		doPost();
	}
	/**
	 * Test the case when the first name is invalid.
	 */
	@Test
	public void testEditEmptyFirstName() {
		Calendar calendar = Calendar.getInstance();
		addRequestParameter("id","1000");
		addRequestParameter("lastName","Doe");
		SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
		addRequestParameter("birthday",format.format(calendar.getTime()));
		addRequestParameter("okButton","OK");
		doPost();
		String errorMessage = (String) getWebMockObjectFactory().getMockRequest().getAttribute("error");
		assertNotNull(errorMessage);
		assertNotEquals(0, errorMessage.length());
	}
	/**
	 * Test the case when the last name is invalid.
	 */
	@Test
	public void testEditEmptyLastName() {
		Calendar calendar = Calendar.getInstance();
		addRequestParameter("id","1000");
		addRequestParameter("firstName","John");
		SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
		addRequestParameter("birthday",format.format(calendar.getTime()));
		addRequestParameter("okButton","OK");
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
		addRequestParameter("id","1000");
		addRequestParameter("lastName","Doe");
		addRequestParameter("firstName","John");
		addRequestParameter("birthday","1111111");
		addRequestParameter("okButton","OK");
		doPost();
		String errorMessage = (String) getWebMockObjectFactory().getMockRequest().getAttribute("error");
		assertNotNull(errorMessage);
		assertNotEquals(0, errorMessage.length());
	}
}
