package ua.nure.baranov;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.Calendar;

import org.junit.Before;
import org.junit.Test;

public class UserTest  {
	private User user;
	
	@Before
	public void setUp() throws Exception {
		user=new User();
		Calendar calendar=Calendar.getInstance();
	}
	
	@Test
	public void getFullNameTest() {
		user.setFirstName("Yevhenii");
		user.setLastName("Baranov");
		assertEquals("Yevhenii Baranov", user.getFullName());
	}
	
	@Test(expected=IllegalStateException.class)
	public void getFullNameFirstNameUnsettedTest() {
		user.setLastName("Yevhenii");
		String fullName=user.getFullName();
		fail();
	}
	
	@Test(expected=IllegalStateException.class)
	public void getFullNameLastNameUnsettedTest() {
		user.setLastName("Baranov");
		String fullName=user.getFullName();
		fail();
	}
	
}
