package ua.nure.baranov.web;

import java.util.Calendar;

import org.junit.Before;
import org.junit.Test;

import ua.nure.baranov.User;
/**
 * Test class, tests {@link DeleteServlet}
 * @author Yevhenii Baranov
 */
public class DeleteServletTest extends MockServletTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();
		createServlet(DeleteServlet.class);
	}
	
	/**
	 * Tests case when user confirms the deletion
	 */
	@Test
	public void testDeletion() {
		Calendar calendar = Calendar.getInstance();
		User user = new User(new Long(1000L), "John", "Doe", calendar);
		getWebMockObjectFactory().getMockSession().setAttribute("user", user);
		addRequestParameter("okButton","ok");
		getMockUserDAO().expect("delete", user);
		doPost();
	}
	/**
	 * Tests case when user cancels the deletion
	 */
	@Test
	public void testCancel() {
		Calendar calendar = Calendar.getInstance();
		User user = new User(new Long(1000L), "John", "Doe", calendar);
		getWebMockObjectFactory().getMockSession().setAttribute("user", user);
		addRequestParameter("cancelButton", "Cancel");
		doPost();
	}

}
