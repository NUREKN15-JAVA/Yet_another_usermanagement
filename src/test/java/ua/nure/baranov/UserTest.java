package ua.nure.baranov;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import java.util.Calendar;
import org.junit.Before;
import org.junit.Test;

public class UserTest {
	private static final int TEST_AGE = 20;
	private static final int TEST_DAY = 23;
	private static final int TEST_MONTH = Calendar.SEPTEMBER;
	private static final int TEST_YEAR = 1997;
	/**
	 * Variable that is used to calculate date in future in order to send wrong data to the function
	 */
	private static final int FUTURE_YEAR_SHIFT = 20;
	
	private User user;
	private Calendar today;

	@Before
	public void setUp() throws Exception {
		user = new User();
		today = Calendar.getInstance();
	}

	@Test
	public void getFullNameTest() {
		user.setFirstName("Yevhenii");
		user.setLastName("Baranov");
		assertEquals("Yevhenii Baranov", user.getFullName());
	}

	@Test(expected = IllegalStateException.class)
	public void getFullNameFirstNameUnsettedTest() {
		user.setLastName("Yevhenii");
		user.getFullName();
		fail();
	}

	@Test(expected = IllegalStateException.class)
	public void getFullNameLastNameUnsettedTest() {
		user.setLastName("Baranov");
		user.getFullName();
		fail();
	}

	/**
	 * Test for age calculator, is valid only from September of 2017 to September of 2018.
	 * TODO: rewrite if it starts to fail.
	 */
	@Test
	public void getAgeTest() {
		Calendar userBirthDay = Calendar.getInstance();
		userBirthDay.set(TEST_YEAR, TEST_MONTH, TEST_DAY);
		user.setBirthDay(userBirthDay);
		assertEquals(TEST_AGE, user.getAge());
	}

	@Test(expected = IllegalStateException.class)
	public void testAgeWithIllegalDate() {
		Calendar illegalBirthDay = Calendar.getInstance();
		int invalidYear = today.get(Calendar.YEAR) + FUTURE_YEAR_SHIFT;
		illegalBirthDay.set(invalidYear, TEST_MONTH, TEST_DAY);
		user.setBirthDay(illegalBirthDay);
		user.getAge();
		fail();
	}


}
