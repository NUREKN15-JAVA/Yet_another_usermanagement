package ua.nure.baranov;

import java.util.Calendar;

/**
 * @author Yevhenii Baranov
 */
public class User {
	private Long id;
	private String firstName;
	private String lastName;
	private Calendar birthDay;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}
	
	/**
	 * Setter for the first name of the user
	 * @param firstName - must not be empty string
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public String getLastName() {
		return lastName;
	}

	/**
	 * Setter for the last name of the user
	 * @param lastName - must not be empty string
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Calendar getBirthDay() {
		return birthDay;
	}

	public void setBirthDay(Calendar birthDay) {
		this.birthDay = birthDay;
	}
	
	/**
	 * Assembles and returns full name of user. 
	 * @return Full name.
	 * @throws IllegalStateException if either first or last name is not set.
	 */
	public String getFullName() throws IllegalStateException {
		StringBuilder fullName = new StringBuilder();
		if (firstName == null || lastName == null) {
			throw new IllegalStateException("something is missing");
		}
		fullName.append(firstName).append(' ').append(lastName);
		return fullName.toString();
	}

	/**
	 * Calculates current age of user, depending on his birthday.
	 * @return Age of user.
	 * @throws IllegalStateException
	 */
	public int getAge() throws IllegalStateException {
		Calendar currentDate = Calendar.getInstance();
		if (birthDay.compareTo(currentDate) >= 0) {
			throw new IllegalStateException("User has not been born yet");
		}
		int age = currentDate.get(Calendar.YEAR) - birthDay.get(Calendar.YEAR);
		if (currentDate.get(Calendar.MONTH) < birthDay.get(Calendar.MONTH)
				|| (currentDate.get(Calendar.MONTH) == birthDay.get(Calendar.MONTH)
				&& currentDate.get(Calendar.DAY_OF_MONTH) <= birthDay.get(Calendar.DAY_OF_MONTH))) {
			age--;
		}
		return age;
	}

}
