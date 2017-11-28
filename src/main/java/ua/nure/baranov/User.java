package ua.nure.baranov;

import java.util.Calendar;
import java.util.Date;

/**
 * @author Yevhenii Baranov
 */
public class User {
	private Long id;
	private String firstName;
	private String lastName;
	private Calendar birthDay;

	public User(User user) {
		this.id = user.id;
		this.firstName = user.firstName;
		this.lastName = user.lastName;
		this.birthDay = user.birthDay;
	}

	public User(Long id, String firstName, String lastName, Calendar birthDay) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.birthDay = birthDay;
	}

	public User(String firstName, String lastName, Calendar birthDay) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.birthDay = birthDay;
	}

	public User() {

	}

	public User(Long id, String firstName, String lastName) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
	}

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
	 * 
	 * @param firstName
	 *            - must not be empty string
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	/**
	 * Setter for the last name of the user
	 * 
	 * @param lastName
	 *            - must not be empty string
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
	
	public Date getDateOfBirth() {
		return birthDay.getTime();
	}
	
	public void setDateOfBirth(Date date) {
		birthDay.setTime(date);
	}
	
	
	
	/**
	 * Assembles and returns full name of user.
	 * 
	 * @return Full name.
	 * @throws IllegalStateException
	 *             if either first or last name is not set.
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
	 * 
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

	@Override

	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		if (this.getId() == null && ((User) obj).getId() == null) {
			return true;
		}
		return this.getId().equals(((User) obj).getId());
	}

	@Override
	public int hashCode() {
		if (id == null) {
			return 0;
		}
		return id.hashCode();
	}

}
