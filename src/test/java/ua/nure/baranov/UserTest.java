package ua.nure.baranov;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.Calendar;

import org.junit.Before;
import org.junit.Test;

public class UserTest  {
	private static final int TEST_DAY = 23;
	private static final int TEST_MONTH = Calendar.SEPTEMBER;
	private static final int TEST_YEAR = 1997;
	
	private User user;
	private Calendar today;
	@Before
	public void setUp() throws Exception {
		user=new User();
		today=Calendar.getInstance();
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
		user.getFullName();
		fail();
	}
	
	@Test(expected=IllegalStateException.class)
	public void getFullNameLastNameUnsettedTest() {
		user.setLastName("Baranov");
		user.getFullName();
		fail();
	}
	
	
	//This test is valid until the September of 2k18, lol
	@Test
	public void getAgeTest() {
		Calendar userBirthDay=Calendar.getInstance();
		userBirthDay.set(TEST_YEAR, TEST_MONTH, TEST_DAY);
		user.setBirthDay(userBirthDay);
		assertEquals(20,user.getAge());
	}
	
	
	@Test(expected=IllegalStateException.class)
	public void testAgeWithIllegalDate() {
		Calendar illegalBirthDay=Calendar.getInstance();
		int invalidYear=today.get(Calendar.YEAR)+20;
		illegalBirthDay.set(invalidYear, TEST_MONTH, TEST_DAY);
		user.setBirthDay(illegalBirthDay);
		user.getAge();
	}
	
}
