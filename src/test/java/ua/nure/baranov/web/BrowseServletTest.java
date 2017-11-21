package ua.nure.baranov.web;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;

import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import ua.nure.baranov.User;
/**
 * Test class, contains tests for {@link BrowseServlet}
 * @author Yevhenii Baranov
 *
 */
public class BrowseServletTest extends MockServletTestCase{
	
	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();
		createServlet(BrowseServlet.class);
	}
	/**
	 * Tests basic functionality of browsing user list
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void testBrowse() {
		User user = new User(new Long(1000L),"John", "Doe",Calendar.getInstance());
		List<User> userList = Collections.singletonList(user);
		getMockUserDAO().expectAndReturn("findAll", userList);
		service();
		Collection<User> collection = (Collection<User>) getWebMockObjectFactory().getMockSession().getAttribute("users");
		assertNotNull(collection);
		assertSame(userList, collection);
	}
	/**
	 * Tests interaction between {@link BrowseServlet} and {@link EditServlet}
	 */
	@Test
	public void testUserGettingForEdit() {
		User user = new User(new Long(1000L),"John", "Doe",Calendar.getInstance());
		getMockUserDAO().expectAndReturn("find",new Long(1000L),user);
		addRequestParameter("editButton","edit");
		addRequestParameter("id","1000");
		doPost();
		User userToCheck = (User) getWebMockObjectFactory().getMockSession().getAttribute("user");
		assertNotNull(userToCheck);
		assertSame(user,userToCheck);
	}
	/**
	 * Tests interaction between {@link BrowseServlet} and {@link DetailsServlet}
	 */
	@Test
	public void testUserGettingForDetails() {
		User user = new User(new Long(1000L),"John", "Doe",Calendar.getInstance());
		getMockUserDAO().expectAndReturn("find",new Long(1000L),user);
		addRequestParameter("detailsButton","Details");
		addRequestParameter("id","1000");
		doPost();
		User userToCheck = (User) getWebMockObjectFactory().getMockSession().getAttribute("user");
		assertNotNull(userToCheck);
		assertSame(user,userToCheck);
	}
	
}